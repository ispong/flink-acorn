package com.isxcode.acorn.common.pojo.node;

import com.isxcode.acorn.common.menu.DataFormat;
import com.isxcode.acorn.common.pojo.dto.ColumnInfo;
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
