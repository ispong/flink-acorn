package com.isxcode.acorn.demo4.pojo.node;

import com.isxcode.acorn.demo4.menu.DataFormat;
import com.isxcode.acorn.demo4.pojo.dto.ColumnInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class KafkaInput extends NodeBaseInfo {

    private String brokerList;

    private String topic;

    private String zookeeper;

    private DataFormat dataFormat;

    private List<ColumnInfo> columnList;
}
