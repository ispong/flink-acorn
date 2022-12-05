package com.isxcode.acorn.api.pojo.node;

import com.isxcode.acorn.api.menu.DataFormat;
import com.isxcode.acorn.api.pojo.dto.FlinkCol;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
public class KafkaInput extends NodeBaseInfo {

    private String brokerList;

    private String topic;

    private String zookeeper;

    private DataFormat dataFormat;

    private List<FlinkCol> columnList;
}
