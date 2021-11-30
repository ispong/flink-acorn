package com.isxcode.acorn.demo4.pojo.dto;

import com.isxcode.acorn.demo4.menu.WorkType;
import com.isxcode.acorn.demo4.pojo.node.KafkaInput;
import com.isxcode.acorn.demo4.pojo.node.KafkaOutput;
import com.isxcode.acorn.demo4.pojo.node.MysqlOutput;
import lombok.Data;

@Data
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

}
