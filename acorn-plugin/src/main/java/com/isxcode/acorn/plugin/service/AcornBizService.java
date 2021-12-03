package com.isxcode.acorn.plugin.service;

import com.isxcode.acorn.common.pojo.dto.ExecuteConfig;
import com.isxcode.acorn.common.pojo.dto.FlinkError;
import com.isxcode.acorn.plugin.constant.FlinkConstants;
import com.isxcode.acorn.plugin.properties.AcornProperties;
import com.isxcode.acorn.plugin.utils.ShellUtils;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import io.micrometer.core.instrument.util.IOUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    public FlinkError executeFlink(ExecuteConfig executeConfig) {

        // 检查配置文件合法性
        if (Strings.isEmpty(executeConfig.getExecuteId())) {
            return new FlinkError("10001", "executeId为空");
        }
        if (Strings.isEmpty(executeConfig.getWorkType().name())) {
            return new FlinkError("10002", "workType为空");
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
                    return new FlinkError("10004", "作业类型暂不支持");
                case KAFKA_INPUT_HIVE_OUTPUT:
                    return new FlinkError("10006", "作业类型暂不支持");
                default:
                    return new FlinkError("10003", "作业类型不支持");
            }
            flinkJobJavaCode = FreeMarkerTemplateUtils.processTemplateIntoString(template, executeConfig);
        }catch (IOException | TemplateException e) {
            return new FlinkError("10004", "初始化代码失败");
        }

        // 创建FlinkJob.java文件
        String flinkJobPath = acornProperties.getTmpDir() + FlinkConstants.SPLIT_CODE + executeConfig.getExecuteId() + FlinkConstants.SPLIT_CODE + "src" + FlinkConstants.SPLIT_CODE + "main" + FlinkConstants.SPLIT_CODE + "java" + FlinkConstants.SPLIT_CODE + "com" + FlinkConstants.SPLIT_CODE + "isxcode" + FlinkConstants.SPLIT_CODE + "acorn" + FlinkConstants.SPLIT_CODE + "demo4";
        try {
            if (!Files.exists(Paths.get(flinkJobPath))) {
                log.info(flinkJobPath);
                Files.createDirectories(Paths.get(flinkJobPath));
            }
            flinkJobPath = flinkJobPath + FlinkConstants.SPLIT_CODE + "FlinkJob.java";
            if (!Files.exists(Paths.get(flinkJobPath))) {
                Files.createFile(Paths.get(flinkJobPath));
            }
            Path file = Paths.get(flinkJobPath);
            Files.write(file, flinkJobJavaCode.getBytes(), StandardOpenOption.WRITE);
        } catch (IOException e) {
            log.debug(e.getMessage());
            return new FlinkError("10007", "创建文件失败");
        }

        // 创建pom.xml文件
        String flinkPomFilePath = acornProperties.getTmpDir() + FlinkConstants.SPLIT_CODE + executeConfig.getExecuteId() + FlinkConstants.SPLIT_CODE + "pom.xml";
        DefaultResourceLoader defaultResourceLoader = new DefaultResourceLoader();
        Resource resource = defaultResourceLoader.getResource("templates/pom.xml");
        try {
            log.info(flinkPomFilePath);
            if (!Files.exists(Paths.get(flinkPomFilePath))) {
                Files.createFile(Paths.get(flinkPomFilePath));
            }
            Path file = Paths.get(flinkPomFilePath);
            Files.write(file, IOUtils.toString(resource.getInputStream(), StandardCharsets.UTF_8).getBytes(), StandardOpenOption.WRITE);
        } catch (IOException e) {
            log.debug(e.getMessage());
            return new FlinkError("10007", "创建文件失败");
        }

        // 创建日志文件
        String logPath = acornProperties.getLogDir();
        log.info(logPath);
        try {
            if (!Files.exists(Paths.get(logPath))) {
                log.debug(logPath);
                Files.createDirectories(Paths.get(logPath));
            }
            logPath = logPath + FlinkConstants.SPLIT_CODE + executeConfig.getExecuteId() + ".log";
            if (Files.exists(Paths.get(logPath))) {
                Files.delete(Paths.get(logPath));
            }
            Files.createFile(Paths.get(logPath));
        } catch (IOException e) {
            log.debug(e.getMessage());
            return new FlinkError("10007", "创建文件失败");
        }

        // 执行编译且运行的命令
        String mvnBuildCommand = "mvn clean package -f " + flinkPomFilePath;
        ShellUtils.executeCommand(mvnBuildCommand, logPath);

        String submitFlinkJob = "flink run " + acornProperties.getTmpDir() + FlinkConstants.SPLIT_CODE + executeConfig.getExecuteId() + FlinkConstants.SPLIT_CODE + "target" + FlinkConstants.SPLIT_CODE + "flinkJob-1.0.0.jar";
        ShellUtils.executeCommand(submitFlinkJob, logPath);

        return new FlinkError("10009", "运行成功");
    }
}