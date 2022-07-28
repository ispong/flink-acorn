package com.isxcode.acorn.plugin.service;

import com.alibaba.fastjson.JSON;
import com.isxcode.acorn.common.constant.FileConstants;
import com.isxcode.acorn.common.constant.UrlConstants;
import com.isxcode.acorn.common.exception.AcornException;
import com.isxcode.acorn.common.exception.AcornExceptionEnum;
import com.isxcode.acorn.common.pojo.AcornRequest;
import com.isxcode.acorn.common.pojo.dto.AcornData;
import com.isxcode.acorn.common.pojo.dto.JobStatusDto;
import com.isxcode.acorn.common.pojo.dto.JobStatusResultDto;
import com.isxcode.acorn.common.properties.AcornPluginProperties;
import com.isxcode.acorn.common.utils.CommandUtils;
import com.isxcode.acorn.common.utils.FileUtils;
import com.isxcode.acorn.common.utils.FreemarkerUtils;
import com.isxcode.acorn.common.utils.HttpUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AcornBizService {

    private final AcornPluginProperties acornPluginProperties;

    public AcornData executeFlinkJob(AcornRequest acornRequest) {

        // 关键字检测
        if (acornRequest.getExecuteId() == null) {
            throw new AcornException(AcornExceptionEnum.REQUEST_VALUE_EMPTY);
        }

        // 初始化代码
        String flinkJobJavaCode = "";
        if (acornRequest.getJava() != null) {
            flinkJobJavaCode = executeJava(acornRequest);
        }

        // 项目临时文件路径
        String projectPath = acornPluginProperties.getTmpDir() + File.separator + acornRequest.getExecuteId() + File.separator + FileConstants.JOB_PROJECT_NAME + File.separator;

        // 创建FlinkJob.java文件
        String flinkJobPath = projectPath + FileConstants.JOB_TMP_PATH + File.separator + FileConstants.JOB_FILE_NAME;
        FileUtils.StringToFile(flinkJobJavaCode, flinkJobPath, StandardOpenOption.WRITE);

        // 创建pom.xml文件
        String flinkPomFilePath = projectPath + FileConstants.POM_XML;
        FileUtils.copyResourceFile(FileConstants.POM_TEMPLATE_PATH, flinkPomFilePath, StandardOpenOption.WRITE);

        // 创建日志文件
        String logPath = acornPluginProperties.getTmpDir() + File.separator + acornRequest.getExecuteId() + File.separator + FileConstants.JOB_LOG_NAME;
        FileUtils.generateFile(logPath);

        // 执行编译且运行作业
        String targetFilePath = projectPath + "target" + File.separator + FileConstants.FLINK_JAR_NAME;
        String executeCommand = "mvn clean package -f " + flinkPomFilePath + " && " + "flink run " + targetFilePath;
        log.debug(" 执行命令:" + executeCommand);
        try {
            CommandUtils.executeCommand(executeCommand, logPath);
        } catch (Exception e) {
            throw new AcornException(AcornExceptionEnum.BUILD_COMMAND_ERROR);
        }

        // 删除临时项目文件
        if (!acornPluginProperties.isStorageTmp()) {
            log.debug("删除生成的文件夹");
            FileUtils.RecursionDeleteFile(Paths.get(projectPath));
        }

        // 读取日志最后一行 Job has been submitted with JobID 133d87e09f586e72e1f1fe2575d1a3c4
        String backlog = CommandUtils.executeBackCommand("sed -n '$p' " + logPath);
        if (backlog.contains(FileConstants.SUCCESS_FLINK_LOG)) {
            String jobId = backlog.replaceAll(FileConstants.SUCCESS_FLINK_LOG, "").trim();
            log.debug("AcornBizService.execute.jobId:" + jobId);
            return AcornData.builder().jobId(jobId).build();
        } else {
            throw new AcornException(AcornExceptionEnum.EXECUTE_COMMAND_ERROR);
        }
    }

    public String executeSql(AcornRequest acornRequest) {

        Map<String, Object> freemarkerParams = new HashMap<>();
        freemarkerParams.put("jobName", acornRequest.getName() == null ? "acorn-job" : acornRequest.getName());
        freemarkerParams.put("flinkSqlList", acornRequest.getSql());

        try {
            return FreemarkerUtils.templateToString(FileConstants.ACORN_SQL_TEMPLATE_NAME, freemarkerParams);
        } catch (AcornException e) {
            throw new AcornException(AcornExceptionEnum.JAVA_CODE_GENERATE_ERROR);
        }
    }

    public String executeJava(AcornRequest acornRequest) {

        Map<String, Object> freemarkerParams = new HashMap<>();
        freemarkerParams.put("flinkJavaCode", acornRequest.getJava());

        try {
            return FreemarkerUtils.templateToString(FileConstants.ACORN_JAVA_TEMPLATE_NAME, freemarkerParams);
        } catch (AcornException e) {
            throw new AcornException(AcornExceptionEnum.JAVA_CODE_GENERATE_ERROR);
        }
    }

    public AcornData getJobLog(AcornRequest acornRequest) {

        String logPath = acornPluginProperties.getTmpDir() + File.separator + acornRequest.getExecuteId() + File.separator + FileConstants.JOB_LOG_NAME;
        Path path = Paths.get(logPath);
        Resource resource;
        try {
            resource = new UrlResource(path.toUri());
            String logStr = new BufferedReader(new InputStreamReader(resource.getInputStream())).lines().collect(Collectors.joining("\n"));
            return AcornData.builder().jobLog(logStr).build();
        } catch (IOException e) {
            log.debug("AcornBizService.getJobLog.getMessage" + e.getMessage());
            throw new AcornException(AcornExceptionEnum.READ_LOG_FILE_ERROR);
        }
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

        JobStatusResultDto jobStatusResultDto = HttpUtils.doGet("http://" + acornPluginProperties.getFlinkHost() + ":" + acornPluginProperties.getFlinkPort() + UrlConstants.FLINK_JOBS_OVERVIEW, JobStatusResultDto.class);
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

    public AcornData queryJobStatus() {

        JobStatusResultDto jobStatusResultDto = HttpUtils.doGet("http://" + acornPluginProperties.getFlinkHost() + ":" + acornPluginProperties.getFlinkPort() + UrlConstants.FLINK_JOBS_OVERVIEW, JobStatusResultDto.class);
        if (jobStatusResultDto.getJobs() == null) {
            return null;
        }
        return AcornData.builder().jobInfoList(jobStatusResultDto.getJobs()).build();
    }
}
