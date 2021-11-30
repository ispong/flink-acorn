package com.isxcode.acorn.demo4.pojo.dto;

import com.isxcode.acorn.demo4.menu.DataFormat;
import lombok.Data;

@Data
public class NodeInfo {

    private String nodeId;

    private String name;

    private String brokerList;

    private String topic;

    private String zookeeper;

    private DataFormat dataFormat;
}
