package com.isxcode.acorn.common.pojo.request;

import com.isxcode.acorn.common.pojo.dto.ColumnMapping;
import com.isxcode.acorn.common.pojo.node.KafkaInput;
import com.isxcode.acorn.common.pojo.node.KafkaOutput;
import com.isxcode.acorn.common.pojo.node.MysqlOutput;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class AcornModel2 extends AcornRequest {

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
