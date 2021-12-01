package com.isxcode.acorn.demo4.service;

import com.isxcode.acorn.demo4.constant.FlinkConstants;
import com.isxcode.acorn.demo4.pojo.dto.ExecuteConfig;
import com.isxcode.acorn.demo4.pojo.dto.FlinkError;
import com.isxcode.acorn.demo4.properties.FlinkProperties;
import com.isxcode.acorn.demo4.utils.ShellUtils;
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

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.FileAttribute;

@Slf4j
@Service
public class FlinkService {

    private final FlinkProperties flinkProperties;

    private final FreeMarkerConfigurer freeMarkerConfigurer;

    public FlinkService(FlinkProperties flinkProperties,
                        FreeMarkerConfigurer freeMarkerConfigurer) {

        this.flinkProperties = flinkProperties;
        this.freeMarkerConfigurer = freeMarkerConfigurer;
    }

//    public NodeInfo addNode(FlinkReq flinkReq) {
//
//        Assert.notNull(flinkReq.getNodeType(), "节点类型不可为空");
//
//        String nodeId = "SnowflakeUtils.getNextUuid()";
//
//        FlinkNodeEntity flinkNodeEntity = new FlinkNodeEntity();
//        flinkNodeEntity.setNodeId(nodeId);
//        flinkNodeEntity.setType(flinkReq.getNodeType().name());
//
//        switch (flinkReq.getNodeType()) {
//            case KAFKA_INPUT:
//                KafkaInput kafkaInput = new KafkaInput();
//                BeanUtils.copyProperties(flinkReq, kafkaInput);
//                flinkNodeEntity.setConfig(JSON.toJSONString(kafkaInput));
//                break;
//            case KAFKA_OUTPUT:
//                KafkaOutput kafkaOutput = new KafkaOutput();
//                BeanUtils.copyProperties(flinkReq, kafkaOutput);
//                flinkNodeEntity.setConfig(JSON.toJSONString(kafkaOutput));
//            default:
//                throw new RuntimeException("节点类型不支持");
//        }
//
//        // 保存节点
//        flinkRepository.addNode(flinkNodeEntity);
//        return getNode(nodeId);
//    }
//
//    public NodeInfo getNode(String nodeId) {
//
//        NodeInfo nodeInfo = new NodeInfo();
//
//        FlinkNodeEntity nodeEntity = flinkRepository.getNode(nodeId);
//        BeanUtils.copyProperties(nodeEntity, nodeInfo);
//
//        return nodeInfo;
//    }


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
        String flinkJobPath = flinkProperties.getTmpDir() + FlinkConstants.SPLIT_CODE + executeConfig.getExecuteId() + FlinkConstants.SPLIT_CODE + "src" + FlinkConstants.SPLIT_CODE + "main" + FlinkConstants.SPLIT_CODE + "java" + FlinkConstants.SPLIT_CODE + "com" + FlinkConstants.SPLIT_CODE + "isxcode" + FlinkConstants.SPLIT_CODE + "acorn" + FlinkConstants.SPLIT_CODE + "demo4";
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
        String flinkPomFilePath = flinkProperties.getTmpDir() + FlinkConstants.SPLIT_CODE + executeConfig.getExecuteId() + FlinkConstants.SPLIT_CODE + "pom.xml";
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
        String logPath = flinkProperties.getLogDir();
        log.info(logPath);
        try {
            if (!Files.exists(Paths.get(logPath))) {
                log.debug(logPath);
                Files.createDirectories(Paths.get(logPath));
            }
            logPath = logPath + FlinkConstants.SPLIT_CODE + executeConfig.getExecuteId() + ".log";
            if (!Files.exists(Paths.get(logPath))) {
                Files.createFile(Paths.get(logPath));
            }
        } catch (IOException e) {
            log.debug(e.getMessage());
            return new FlinkError("10007", "创建文件失败");
        }

        // 执行编译且运行的命令
        // cd /home/dehoop/acorn/tmp/executeIdIsxcode && mvn clean package && /opt/flink/bin/flink run /home/dehoop/acorn/tmp/executeIdIsxcode/target/flinkJob-1.0.0.jar
        String goHomeCommand = "cd " + flinkProperties.getTmpDir() + FlinkConstants.SPLIT_CODE + executeConfig.getExecuteId();
        String mvnBuildCommand = "mvn clean package";
        String submitFlinkJob = "flink run " + flinkProperties.getTmpDir() + FlinkConstants.SPLIT_CODE + executeConfig.getExecuteId() + FlinkConstants.SPLIT_CODE + "target" + FlinkConstants.SPLIT_CODE + "flinkJob-1.0.0.jar";
        String buildCommand = goHomeCommand + " && " + mvnBuildCommand + " && " + submitFlinkJob;
        log.info(buildCommand);
        ShellUtils.executeCommand(buildCommand, logPath);
        return new FlinkError("10009", "运行成功");
    }
}
