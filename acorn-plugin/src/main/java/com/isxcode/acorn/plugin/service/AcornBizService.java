package com.isxcode.acorn.plugin.service;

import com.isxcode.acorn.common.pojo.dto.AcornResponse;
import com.isxcode.acorn.common.pojo.dto.ExecuteConfig;
import com.isxcode.acorn.plugin.constant.FlinkConstants;
import com.isxcode.acorn.plugin.properties.AcornProperties;
import com.isxcode.acorn.plugin.utils.ShellUtils;
import com.isxcode.oxygen.core.file.FileUtils;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.File;
import java.io.IOException;
import java.nio.file.StandardOpenOption;

@Slf4j
@Service
public class AcornBizService {

    private final AcornProperties acornProperties;

    private final FreeMarkerConfigurer freeMarkerConfigurer;

    public AcornBizService(AcornProperties acornProperties,
                           FreeMarkerConfigurer freeMarkerConfigurer) {

        this.acornProperties = acornProperties;
        this.freeMarkerConfigurer = freeMarkerConfigurer;
    }

    public AcornResponse executeFlink(ExecuteConfig executeConfig) {

        // 检查配置文件合法性
        if (Strings.isEmpty(executeConfig.getExecuteId())) {
            return new AcornResponse("10001", "executeId为空");
        }
        if (Strings.isEmpty(executeConfig.getWorkType().name())) {
            return new AcornResponse("10002", "workType为空");
        }

        // 生成FlinkJob.java代码
        String flinkJobJavaCode;
        try {
            Template template;
            switch (executeConfig.getWorkType()) {
                case KAFKA_INPUT_MYSQL_OUTPUT:
                    template = freeMarkerConfigurer.getConfiguration().getTemplate("FlinkKafkaToMysqlTemplate.ftl");
                    break;
                case KAFKA_INPUT_KAFKA_OUTPUT:
                    return new AcornResponse("10004", "作业类型暂不支持");
                case KAFKA_INPUT_HIVE_OUTPUT:
                    return new AcornResponse("10006", "作业类型暂不支持");
                default:
                    return new AcornResponse("10003", "作业类型不支持");
            }
            flinkJobJavaCode = FreeMarkerTemplateUtils.processTemplateIntoString(template, executeConfig);
        }catch (IOException | TemplateException e) {
            return new AcornResponse("10004", "初始化代码失败");
        }

        // 创建FlinkJob.java文件
        String flinkJobPath = acornProperties.getTmpDir() + File.separator + executeConfig.getExecuteId() + FlinkConstants.JOB_HOME_PATH + File.separator + FlinkConstants.JOB_FILE_NAME;
        FileUtils.StringToFile(flinkJobJavaCode, flinkJobPath, StandardOpenOption.WRITE);

        // 创建pom.xml文件
        String flinkPomFilePath = acornProperties.getTmpDir() + File.separator + executeConfig.getExecuteId() + File.separator + FlinkConstants.POM_XML;
        FileUtils.copyResourceFile("templates/pom.xml", flinkPomFilePath, StandardOpenOption.WRITE);

        // 创建日志文件
        String logPath = acornProperties.getLogDir() + File.separator + executeConfig.getExecuteId() + FlinkConstants.LOG_SUFFIX;
        FileUtils.generateFile(logPath);

        // 执行编译且运行的命令
        String mvnBuildCommand = "mvn clean package -f " + flinkPomFilePath;
        ShellUtils.executeCommand(mvnBuildCommand, logPath);

        String submitFlinkJob = "flink run " + acornProperties.getTmpDir() + File.separator + executeConfig.getExecuteId() + File.separator + "target" + File.separator + "flinkJob-1.0.0.jar";
        ShellUtils.executeCommand(submitFlinkJob, logPath);

        return new AcornResponse("10009", "运行成功");
    }
}