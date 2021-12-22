package com.isxcode.acorn.plugin.service;

import com.isxcode.acorn.common.constant.FlinkConstants;
import com.isxcode.acorn.common.menu.TemplateType;
import com.isxcode.acorn.common.pojo.dto.AcornData;
import com.isxcode.acorn.common.pojo.dto.JobInfoDto;
import com.isxcode.acorn.common.pojo.dto.JobStatusResultDto;
import com.isxcode.acorn.common.pojo.model.AcornModel1;
import com.isxcode.acorn.common.properties.AcornPluginProperties;
import com.isxcode.acorn.plugin.exception.AcornException;
import com.isxcode.acorn.plugin.utils.AcornFileUtils;
import com.isxcode.acorn.plugin.utils.CommandUtils;
import com.isxcode.acorn.plugin.utils.ParseSqlUtils;
import com.isxcode.oxygen.core.file.FileUtils;
import com.isxcode.oxygen.core.http.HttpUtils;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import io.micrometer.core.instrument.util.IOUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ExecutionException;

@Slf4j
@Service
public class AcornBizService {

    private final AcornPluginProperties acornPluginProperties;

    private final FreeMarkerConfigurer freeMarkerConfigurer;

    public AcornBizService(AcornPluginProperties acornPluginProperties,
                           FreeMarkerConfigurer freeMarkerConfigurer) {

        this.acornPluginProperties = acornPluginProperties;
        this.freeMarkerConfigurer = freeMarkerConfigurer;
    }

    /**
     * 执行flink任务
     */
    public AcornData execute(AcornModel1 acornModel) throws AcornException {

        if (TemplateType.KAFKA_INPUT_HIVE_OUTPUT.equals(acornModel.getTemplateName())) {
            acornModel.setHiveConfigPath(acornPluginProperties.getHiveConfPath());
        }

        // 获取模板
        Template template;
        try {
            template = freeMarkerConfigurer.getConfiguration().getTemplate(acornModel.getTemplateName().getTemplateFileName());
        } catch (IOException e) {
            log.debug(e.getMessage());
            throw new AcornException("10001", "模板不存在");
        }

        // 解析connectorSql中表名
        acornModel.setFromTableName(ParseSqlUtils.getTableName(acornModel.getFromConnectorSql()));
        acornModel.setToTableName(ParseSqlUtils.getTableName(acornModel.getToConnectorSql()));

        // 生成FlinkJob.java代码
        String flinkJobJavaCode;
        try {
            flinkJobJavaCode = FreeMarkerTemplateUtils.processTemplateIntoString(template, acornModel);
            log.debug(flinkJobJavaCode);
        } catch (IOException | TemplateException e) {
            log.debug(e.getMessage());
            throw new AcornException("10002", "代码生成错误");
        }

        // 创建FlinkJob.java文件
        String tmpPath = acornPluginProperties.getTmpDir() + File.separator + acornModel.getExecuteId();
        String flinkJobPath = tmpPath + FlinkConstants.JOB_HOME_PATH + File.separator + FlinkConstants.JOB_FILE_NAME;
        FileUtils.StringToFile(flinkJobJavaCode, flinkJobPath, StandardOpenOption.WRITE);

        // 创建pom.xml文件
        String flinkPomFilePath = acornPluginProperties.getTmpDir() + File.separator + acornModel.getExecuteId() + File.separator + FlinkConstants.POM_XML;
        FileUtils.copyResourceFile("templates/pom.xml", flinkPomFilePath, StandardOpenOption.WRITE);

        // 创建日志文件
        String logPath = acornPluginProperties.getLogDir() + File.separator + acornModel.getExecuteId() + FlinkConstants.LOG_SUFFIX;
        FileUtils.generateFile(logPath);

        // 执行编译且运行作业
        String targetFilePath = tmpPath + File.separator + "target" + File.separator + "acorn.jar";
        String executeCommand = "mvn clean package -f " + flinkPomFilePath + " && " + "flink run " + targetFilePath;
        try {
            CommandUtils.executeCommand(executeCommand, logPath);
        } catch (Exception e) {
            throw new AcornException("10004", "flink运行错误");
        }

        // 删除临时项目文件
        if (!acornPluginProperties.isStorageTmp()) {
            AcornFileUtils.RecursionDeleteFile(Paths.get(tmpPath));
        }

        // 读取日志最后一行 Job has been submitted with JobID 133d87e09f586e72e1f1fe2575d1a3c4
        String flinkSuccessLog = "Job has been submitted with JobID";
        String backlog = CommandUtils.executeBackCommand("sed -n '$p' " + logPath);
        if (backlog.contains(flinkSuccessLog)) {
            String jobId = backlog.replaceAll(flinkSuccessLog, "").trim();
            log.debug("jobId ==>" + jobId);
            return AcornData.builder().jobId(jobId).build();
        } else {
            throw new AcornException("10003", "flink运行错误");
        }
    }

    /**
     * 获取作业日志
     */
    public AcornData getJobLog(String executeId) {

        String logPath = acornPluginProperties.getLogDir() + File.separator + executeId + FlinkConstants.LOG_SUFFIX;
        Path path = Paths.get(logPath);
        Resource resource;
        try {
            resource = new UrlResource(path.toUri());
            String logStr = IOUtils.toString(resource.getInputStream(), StandardCharsets.UTF_8);
            return AcornData.builder().jobLog(logStr).build();
        } catch (IOException e) {
            log.debug(e.getMessage());
            throw new AcornException("10004", "读取日志失败");
        }
    }

    /**
     * 停止实时作业
     */
    public AcornData stopJob(String jobId) {

        String stopFlinkCommand = "flink cancel " + jobId;
        try {
            CommandUtils.executeNoBackCommand(stopFlinkCommand);
        } catch (Exception e) {
            throw new AcornException("10005", "停止实时作业失败");
        }
        return AcornData.builder().build();
    }

    /**
     * 获取实时作业状态
     */
    public AcornData getJobInfo(String jobId) {

        JobStatusResultDto jobStatusResultDto = HttpUtils.doGet("http://127.0.0.1:" + acornPluginProperties.getFlinkPort() + "/jobs/overview", JobStatusResultDto.class);

        if (jobStatusResultDto.getJobs() == null) {
            return null;
        }

        for (JobInfoDto metaJob : jobStatusResultDto.getJobs()) {
            if (metaJob.getJid().equals(jobId)) {
                return AcornData.builder().jobInfo(metaJob).build();
            }
        }

        return null;
    }
}
