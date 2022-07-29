package com.isxcode.acorn.plugin.service;

import com.isxcode.acorn.common.constant.FileConstants;
import com.isxcode.acorn.common.constant.UrlConstants;
import com.isxcode.acorn.common.exception.AcornException;
import com.isxcode.acorn.common.exception.AcornExceptionEnum;
import com.isxcode.acorn.common.pojo.AcornRequest;
import com.isxcode.acorn.common.pojo.dto.AcornData;
import com.isxcode.acorn.common.pojo.dto.JobLogDto;
import com.isxcode.acorn.common.pojo.dto.JobStatusDto;
import com.isxcode.acorn.common.pojo.dto.JobStatusResultDto;
import com.isxcode.acorn.common.properties.AcornPluginProperties;
import com.isxcode.acorn.common.utils.CommandUtils;
import com.isxcode.acorn.common.utils.HttpUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AcornBizService {

    private final AcornService acornService;

    private final AcornPluginProperties acornPluginProperties;

    public AcornData executeFlinkSql(AcornRequest acornRequest) {

        Assert.notNull(acornRequest.getName(), "name be empty");
        Assert.notNull(acornRequest.getSql(), "sql be empty");
        Assert.notNull(acornRequest.getExecuteId(), "executeId be empty");

        // 初始化代码
        String javaCode = acornService.getJavaCode(acornRequest);

        // 创建临时项目位置
        String projectPath = acornService.getProjectPath(acornRequest);

        // 初始化日志位置
        String logPath = acornService.getLogPath(acornRequest);

        // 将java代码写入项目中
        acornService.initJavaFile(projectPath, javaCode);

        // 将pom代码写入项目中
        String pomFile = acornService.initPomFile(projectPath);

        // 编译部署项目
        acornService.deployJob(projectPath, pomFile, logPath);

        // 返回executeId
        return AcornData.builder().executeId(acornRequest.getExecuteId()).build();
    }

    public AcornData getDeployLog(AcornRequest acornRequest) {

        Assert.notNull(acornRequest.getExecuteId(), "executeId be empty");

        String logPath = acornService.getLogPath(acornRequest);
        log.debug("logPath {}", logPath);

        Path path = Paths.get(logPath);
        Resource resource;
        try {
            resource = new UrlResource(path.toUri());
            String logStr = new BufferedReader(new InputStreamReader(resource.getInputStream())).lines().collect(Collectors.joining("\n"));
            log.debug("logStr {}", logStr);

            return AcornData.builder().deployLog(logStr).build();
        } catch (IOException e) {
            log.debug("AcornBizService.getJobLog.getMessage" + e.getMessage());
            throw new AcornException(AcornExceptionEnum.READ_LOG_FILE_ERROR);
        }
    }

    public AcornData getJobId(AcornRequest acornRequest) {

        String logPath = acornService.getLogPath(acornRequest);

        return AcornData.builder().jobId(acornService.getJobId(logPath)).build();
    }

    public AcornData stopJob(AcornRequest acornRequest) {

        String stopFlinkCommand = "flink cancel " + acornRequest.getJobId();
        try {
            CommandUtils.executeNoBackCommand(stopFlinkCommand);
        } catch (Exception e) {
            log.debug("AcornBizService.stopJob.getMessage" + e.getMessage());
            throw new AcornException(AcornExceptionEnum.EXECUTE_COMMAND_ERROR);
        }
        return AcornData.builder().build();
    }

    public AcornData getJobInfo(AcornRequest acornRequest) {

        JobStatusResultDto jobStatusResultDto;
        try {
            jobStatusResultDto = HttpUtils.doGet("http://" + acornPluginProperties.getFlinkHost() + ":" + acornPluginProperties.getFlinkPort() + UrlConstants.FLINK_JOBS_OVERVIEW, JobStatusResultDto.class);
        } catch (Exception e) {
            throw new AcornException(AcornExceptionEnum.FLINK_SERVICE_ERROR);
        }

        if (jobStatusResultDto.getJobs() == null) {
            return null;
        }
        for (JobStatusDto metaJob : jobStatusResultDto.getJobs()) {
            if (metaJob.getJid().equals(acornRequest.getJobId())) {
                return AcornData.builder().jobInfo(metaJob).build();
            }
        }
        return null;
    }

    public AcornData getJobLog(AcornRequest acornRequest) {

        Assert.notNull(acornRequest.getJobId(), "jobId be empty");

        JobLogDto jobLogDto;
        try {
            jobLogDto = HttpUtils.doGet("http://" + acornPluginProperties.getFlinkHost() + ":" + acornPluginProperties.getFlinkPort() + "/jobs/" + acornRequest.getJobId() + "/exceptions?maxExceptions=10", JobLogDto.class);
        } catch (Exception e) {
            throw new AcornException(AcornExceptionEnum.FLINK_SERVICE_ERROR);
        }

        return AcornData.builder().jobLog(jobLogDto.getRootException()).build();
    }

    public AcornData queryJobStatus() {

        JobStatusResultDto jobStatusResultDto = HttpUtils.doGet("http://" + acornPluginProperties.getFlinkHost() + ":" + acornPluginProperties.getFlinkPort() + UrlConstants.FLINK_JOBS_OVERVIEW, JobStatusResultDto.class);
        if (jobStatusResultDto.getJobs() == null) {
            return null;
        }
        return AcornData.builder().jobInfoList(jobStatusResultDto.getJobs()).build();
    }
}
