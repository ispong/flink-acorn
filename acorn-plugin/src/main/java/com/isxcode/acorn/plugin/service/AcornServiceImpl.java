package com.isxcode.acorn.plugin.service;

import com.isxcode.acorn.common.constant.FileConstants;
import com.isxcode.acorn.common.exception.AcornException;
import com.isxcode.acorn.common.exception.AcornExceptionEnum;
import com.isxcode.acorn.common.pojo.AcornRequest;
import com.isxcode.acorn.common.pojo.dto.AcornData;
import com.isxcode.acorn.common.properties.AcornPluginProperties;
import com.isxcode.acorn.common.utils.CommandUtils;
import com.isxcode.acorn.common.utils.FileUtils;
import com.isxcode.acorn.common.utils.FreemarkerUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class AcornServiceImpl implements AcornService {

    private final AcornPluginProperties acornPluginProperties;

    @Override
    public String getLogPath(AcornRequest acornRequest) {

        String logPath = acornPluginProperties.getTmpDir() + File.separator + acornRequest.getExecuteId() + File.separator + FileConstants.JOB_LOG_NAME;
        FileUtils.generateFile(logPath);
        return logPath;
    }

    @Override
    public String initPomFile(String projectPath) {

        String flinkPomFilePath = projectPath + FileConstants.POM_XML;
        FileUtils.copyResourceFile(FileConstants.POM_TEMPLATE_PATH, flinkPomFilePath, StandardOpenOption.WRITE);
        return flinkPomFilePath;
    }

    @Override
    public void initJavaFile(String projectPath, String javaCode) {

        String flinkJobPath = projectPath + FileConstants.JOB_TMP_PATH + File.separator + FileConstants.JOB_FILE_NAME;
        FileUtils.StringToFile(javaCode, flinkJobPath, StandardOpenOption.WRITE);
    }

    @Override
    public String getProjectPath(AcornRequest acornRequest) {

        // project path:  /opt/flink-acorn/tmp/${executeId}/acorn
        return acornPluginProperties.getTmpDir() + File.separator + acornRequest.getExecuteId() + File.separator + FileConstants.JOB_PROJECT_NAME + File.separator;
    }

    @Override
    public String getJavaCode(AcornRequest acornRequest) {

        Map<String, Object> freemarkerParams = new HashMap<>();
        freemarkerParams.put("jobName", acornRequest.getName());
        freemarkerParams.put("flinkSql", acornRequest.getSql());

        try {
            return FreemarkerUtils.templateToString(FileConstants.ACORN_SQL_TEMPLATE_NAME, freemarkerParams);
        } catch (AcornException e) {
            throw new AcornException(AcornExceptionEnum.JAVA_CODE_GENERATE_ERROR);
        }

    }

    @Override
    public void deployJob(String projectPath, String PomPath, String logPath) {

        String targetFilePath = projectPath + "target" + File.separator + FileConstants.FLINK_JAR_NAME;
        String executeCommand = "mvn clean package -f " + PomPath + " && " + "flink run " + targetFilePath;
        log.debug(" 执行命令:" + executeCommand);
        try {
            CommandUtils.executeCommand(executeCommand, logPath);
        } catch (Exception e) {
            throw new AcornException(AcornExceptionEnum.BUILD_COMMAND_ERROR);
        }

        if (!acornPluginProperties.isStorageTmp()) {
            log.debug("删除生成的文件夹");
            FileUtils.RecursionDeleteFile(Paths.get(projectPath));
        }
    }

    @Override
    public String getJobId(String logPath) {

        String backlog = CommandUtils.executeBackCommand("sed -n '$p' " + logPath);
        if (backlog.contains(FileConstants.SUCCESS_FLINK_LOG)) {
            String jobId = backlog.replaceAll(FileConstants.SUCCESS_FLINK_LOG, "").trim();
            log.debug("AcornBizService.execute.jobId:" + jobId);
            return jobId;
        } else {
            throw new AcornException(AcornExceptionEnum.EXECUTE_COMMAND_ERROR);
        }
    }
}
