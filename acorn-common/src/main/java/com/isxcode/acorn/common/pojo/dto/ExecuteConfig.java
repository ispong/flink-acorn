package com.isxcode.acorn.common.pojo.dto;

import com.isxcode.acorn.common.menu.WorkType;
import com.isxcode.acorn.common.pojo.node.KafkaInput;
import com.isxcode.acorn.common.pojo.node.KafkaOutput;
import com.isxcode.acorn.common.pojo.node.MysqlOutput;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ExecuteConfig {

    /**
     * 执行id
     */
    private String executeId;

    /**
     * 作业类型
     */
    private WorkType workType;

    /**
     * 工作流id
     */
    private String flowId;

    /**
     * kafka输入
     */
    private KafkaInput kafkaInput;

    /**
     * kafka输出
     */
    private KafkaOutput kafkaOutput;

    /**
     * mysql输出
     */
    private MysqlOutput mysqlOutput;


    /**
     * 字段映射
     */
    private List<ColumnMapping> columnMapping;
}
