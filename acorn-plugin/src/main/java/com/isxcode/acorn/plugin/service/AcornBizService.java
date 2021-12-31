package com.isxcode.acorn.plugin.service;

import com.isxcode.acorn.common.constant.FileConstants;
import com.isxcode.acorn.common.constant.UrlConstants;
import com.isxcode.acorn.common.menu.TemplateType;
import com.isxcode.acorn.common.pojo.dto.AcornData;
import com.isxcode.acorn.common.pojo.dto.JobStatusDto;
import com.isxcode.acorn.common.pojo.dto.JobStatusResultDto;
import com.isxcode.acorn.common.pojo.model.AcornModel1;
import com.isxcode.acorn.common.properties.AcornPluginProperties;
import com.isxcode.acorn.plugin.exception.AcornException;
import com.isxcode.acorn.common.exception.AcornExceptionEnum;
import com.isxcode.acorn.plugin.utils.ParseSqlUtils;
import com.isxcode.oxygen.core.command.CommandUtils;
import com.isxcode.oxygen.core.exception.OxygenException;
import com.isxcode.oxygen.core.file.FileUtils;
import com.isxcode.oxygen.core.freemarker.FreemarkerUtils;
import com.isxcode.oxygen.core.http.HttpUtils;
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
import java.util.stream.Collectors;

@Slf4j
@Service
public class AcornBizService {

    private final AcornPluginProperties acornPluginProperties;

    public AcornBizService(AcornPluginProperties acornPluginProperties) {

        this.acornPluginProperties = acornPluginProperties;
    }

    public AcornData execute(AcornModel1 acornModel) {

//        // 关键字检测
//        if (acornModel.getExecuteId() == null) {
//            throw new AcornException(AcornExceptionEnum.REQUEST_VALUE_EMPTY);
//        }
//
//        // 如果需要支持hive则需要默认添加配置路径
//        if (TemplateType.KAFKA_INPUT_HIVE_OUTPUT.equals(acornModel.getTemplateName())) {
//            acornModel.setHiveConfigPath(acornPluginProperties.getHiveConfPath());
//        }
//
//        // 解析connectorSql中表名
//        acornModel.setFromTableName(ParseSqlUtils.getTableName(acornModel.getFromConnectorSql()));
//        acornModel.setToTableName(ParseSqlUtils.getTableName(acornModel.getToConnectorSql()));
//
//        // 生成FlinkJob.java代码
//        String flinkJobJavaCode;
//        try {
//            flinkJobJavaCode = FreemarkerUtils.templateToString(acornModel.getTemplateName().getTemplateFileName(), acornModel);
//            log.debug("AcornBizService.execute.flinkJobJavaCode:" + flinkJobJavaCode);
//        } catch (OxygenException e) {
//            throw new AcornException(AcornExceptionEnum.JAVA_CODE_GENERATE_ERROR);
//        }
//
//        // 项目临时文件路径
//        String tmpPath = acornPluginProperties.getTmpDir() + File.separator + acornModel.getExecuteId();
//
//        // 创建FlinkJob.java文件
//        String flinkJobPath = tmpPath + FileConstants.JOB_TMP_PATH + File.separator + FileConstants.JOB_FILE_NAME;
//        FileUtils.StringToFile(flinkJobJavaCode, flinkJobPath, StandardOpenOption.WRITE);
//
//        // 创建pom.xml文件
//        String flinkPomFilePath = acornPluginProperties.getTmpDir() + File.separator + acornModel.getExecuteId() + File.separator + FileConstants.POM_XML;
//        FileUtils.copyResourceFile(FileConstants.POM_TEMPLATE_PATH, flinkPomFilePath, StandardOpenOption.WRITE);
//
//        // 创建日志文件
//        String logPath = acornPluginProperties.getLogDir() + File.separator + acornModel.getExecuteId() + FileConstants.LOG_SUFFIX;
//        FileUtils.generateFile(logPath);
//
//        // 执行编译且运行作业
//        String targetFilePath = tmpPath + File.separator + "target" + File.separator + FileConstants.FLINK_JAR_NAME;
//        String executeCommand = "mvn clean package -f " + flinkPomFilePath + " && " + "flink run " + targetFilePath;
//        try {
//            CommandUtils.executeCommand(executeCommand, logPath);
//        } catch (Exception e) {
//            throw new AcornException(AcornExceptionEnum.BUILD_COMMAND_ERROR);
//        }
//
//        // 删除临时项目文件
//        if (!acornPluginProperties.isStorageTmp()) {
//            FileUtils.RecursionDeleteFile(Paths.get(tmpPath));
//        }
//
//        // 读取日志最后一行 Job has been submitted with JobID 133d87e09f586e72e1f1fe2575d1a3c4
//        String backlog = CommandUtils.executeBackCommand("sed -n '$p' " + logPath);
//        if (backlog.contains(FileConstants.SUCCESS_FLINK_LOG)) {
//            String jobId = backlog.replaceAll(FileConstants.SUCCESS_FLINK_LOG, "").trim();
//            log.debug("AcornBizService.execute.jobId:" + jobId);
//            return AcornData.builder().jobId(jobId).build();
//        } else {
//            throw new AcornException(AcornExceptionEnum.EXECUTE_COMMAND_ERROR);
//        }

        return AcornData.builder().jobId("123").build();
    }

    public AcornData getJobLog(AcornModel1 acornModel1) {

        String logPath = acornPluginProperties.getLogDir() + File.separator + acornModel1.getExecuteId() + FileConstants.LOG_SUFFIX;
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

    public AcornData stopJob(AcornModel1 acornModel1) {

        String stopFlinkCommand = "flink cancel " + acornModel1.getJobId();
        try {
            CommandUtils.executeNoBackCommand(stopFlinkCommand);
        } catch (Exception e) {
            log.debug("AcornBizService.stopJob.getMessage" + e.getMessage());
            throw new AcornException(AcornExceptionEnum.EXECUTE_COMMAND_ERROR);
        }
        return AcornData.builder().build();
    }

    public AcornData getJobInfo(AcornModel1 acornModel1) {

        JobStatusResultDto jobStatusResultDto = HttpUtils.doGet("http://" + acornPluginProperties.getFlinkHost() + ":" + acornPluginProperties.getFlinkPort() + UrlConstants.FLINK_JOBS_OVERVIEW, JobStatusResultDto.class);
        if (jobStatusResultDto.getJobs() == null) {
            return null;
        }
        for (JobStatusDto metaJob : jobStatusResultDto.getJobs()) {
            if (metaJob.getJid().equals(acornModel1.getJobId())) {
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
