package com.isxcode.acorn.common.pojo;

import com.isxcode.acorn.common.menu.NodeType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FlinkReq {

    private NodeType nodeType;

    private String nodeId;
}
