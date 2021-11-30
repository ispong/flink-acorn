package com.isxcode.acorn.demo4.pojo.node;

import com.isxcode.acorn.demo4.menu.DataFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class KafkaInput extends NodeBaseInfo {

    private String brokerList;

    private String topic;

    private String zookeeper;

    private DataFormat dataFormat;
}
