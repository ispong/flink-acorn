package com.isxcode.acorn.api.pojo.dto;

import com.isxcode.acorn.api.menu.DataFormat;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NodeInfo {

    private String nodeId;

    private String name;

    private String brokerList;

    private String topic;

    private String zookeeper;

    private DataFormat dataFormat;
}
