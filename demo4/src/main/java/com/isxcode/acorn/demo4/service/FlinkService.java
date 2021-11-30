package com.isxcode.acorn.demo4.service;

import com.alibaba.fastjson.JSON;
import com.isxcode.acorn.demo4.pojo.FlinkReq;
import com.isxcode.acorn.demo4.pojo.dto.NodeInfo;
import com.isxcode.acorn.demo4.pojo.entity.FlinkFlowEntity;
import com.isxcode.acorn.demo4.pojo.entity.FlinkNodeEntity;
import com.isxcode.acorn.demo4.pojo.node.KafkaInput;
import com.isxcode.acorn.demo4.pojo.node.KafkaOutput;
import com.isxcode.acorn.demo4.repository.FlinkRepository;
import com.isxcode.oxygen.core.snowflake.SnowflakeUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class FlinkService {

    private final FlinkRepository flinkRepository;

    public FlinkService(FlinkRepository flinkRepository) {
        this.flinkRepository = flinkRepository;
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

    public void executeFlink(String flowId, String executeId) {

        // 判断flowId 是否存在
        FlinkFlowEntity flow = flinkRepository.getFlow(flowId);
        if (flow == null) {
            throw new RuntimeException("工作流不存在");
        }


        // 指定模板文件，生成模板项目，打包且生成jar包


        // 执行本地命令 flink run xxx.jar
    }

}
