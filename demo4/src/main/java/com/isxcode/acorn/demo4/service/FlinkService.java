package com.isxcode.acorn.demo4.service;

import com.isxcode.acorn.demo4.utils.ShellUtils;
import io.micrometer.core.instrument.util.IOUtils;
import com.alibaba.fastjson.JSON;
import com.isxcode.acorn.demo4.pojo.FlinkReq;
import com.isxcode.acorn.demo4.pojo.dto.ExecuteConfig;
import com.isxcode.acorn.demo4.pojo.dto.FlinkError;
import com.isxcode.acorn.demo4.pojo.dto.NodeInfo;
import com.isxcode.acorn.demo4.pojo.entity.FlinkNodeEntity;
import com.isxcode.acorn.demo4.pojo.node.KafkaInput;
import com.isxcode.acorn.demo4.pojo.node.KafkaOutput;
import com.isxcode.acorn.demo4.properties.FlinkProperties;
import com.isxcode.acorn.demo4.repository.FlinkRepository;
import com.isxcode.oxygen.core.snowflake.SnowflakeUtils;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.util.Assert;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FlinkService {

    private final FlinkRepository flinkRepository;

    private final FlinkProperties flinkProperties;

    private final FreeMarkerConfigurer freeMarkerConfigurer;

    public FlinkService(FlinkRepository flinkRepository,
                        FlinkProperties flinkProperties,
                        FreeMarkerConfigurer freeMarkerConfigurer) {

        this.flinkRepository = flinkRepository;
        this.flinkProperties = flinkProperties;
        this.freeMarkerConfigurer = freeMarkerConfigurer;
    }

    public NodeInfo addNode(FlinkReq flinkReq) {

        Assert.notNull(flinkReq.getNodeType(), "节点类型不可为空");

        String nodeId = SnowflakeUtils.getNextUuid();

        FlinkNodeEntity flinkNodeEntity = new FlinkNodeEntity();
        flinkNodeEntity.setNodeId(nodeId);
        flinkNodeEntity.setType(flinkReq.getNodeType().name());

        switch (flinkReq.getNodeType()) {
            case KAFKA_INPUT:
                KafkaInput kafkaInput = new KafkaInput();
                BeanUtils.copyProperties(flinkReq, kafkaInput);
                flinkNodeEntity.setConfig(JSON.toJSONString(kafkaInput));
                break;
            case KAFKA_OUTPUT:
                KafkaOutput kafkaOutput = new KafkaOutput();
                BeanUtils.copyProperties(flinkReq, kafkaOutput);
                flinkNodeEntity.setConfig(JSON.toJSONString(kafkaOutput));
            default:
                throw new RuntimeException("节点类型不支持");
        }

        // 保存节点
        flinkRepository.addNode(flinkNodeEntity);
        return getNode(nodeId);
    }

    public NodeInfo getNode(String nodeId) {

        NodeInfo nodeInfo = new NodeInfo();

        FlinkNodeEntity nodeEntity = flinkRepository.getNode(nodeId);
        BeanUtils.copyProperties(nodeEntity, nodeInfo);

        return nodeInfo;
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
        String flinkJobFilePath = flinkProperties.getTmpDir() + "/" + executeConfig.getExecuteId() + "/src/main/java/com/acorn/demo4/FlinkJob.java";
        try {
            Path file = Files.createFile(Paths.get(flinkJobFilePath));
            Files.write(file, flinkJobJavaCode.getBytes());
        } catch (IOException e) {
            return new FlinkError("10007", "创建文件失败");
        }

        // 创建pom.xml文件
        String flinkPomFilePath = flinkProperties.getTmpDir() + "/" + executeConfig.getExecuteId() + "pom.xml";
        DefaultResourceLoader defaultResourceLoader = new DefaultResourceLoader();
        Resource resource = defaultResourceLoader.getResource("template/pom.xml");
        try {
            Path file = Files.createFile(Paths.get(flinkPomFilePath));
            Files.write(file, IOUtils.toString(resource.getInputStream(), StandardCharsets.UTF_8).getBytes());
        } catch (IOException e) {
            return new FlinkError("10007", "创建文件失败");
        }

        // 执行编译且运行的命令
        String buildCommand = "cd " + flinkProperties.getTmpDir() + "/" + executeConfig.getExecuteId() + " && mvn clean package" + "&& cd " + flinkProperties.getTmpDir() + "/" + executeConfig.getExecuteId() + "/target && flink run flinkJob-1.0.0.jar";
        ShellUtils.executeCommand(executeConfig.getExecuteId(), buildCommand, flinkProperties.getLogDir());
        return new FlinkError("10009", "运行成功");
    }
}
