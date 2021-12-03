package com.isxcode.acorn.common.pojo;

import com.isxcode.acorn.common.menu.NodeType;
import lombok.Data;

@Data
public class FlinkReq {

    private NodeType nodeType;

    private String nodeId;
}
