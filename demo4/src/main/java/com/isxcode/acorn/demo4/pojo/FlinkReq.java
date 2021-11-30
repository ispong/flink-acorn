package com.isxcode.acorn.demo4.pojo;

import com.isxcode.acorn.demo4.menu.NodeType;
import lombok.Data;

@Data
public class FlinkReq {

    private NodeType nodeType;

    private String nodeId;
}
