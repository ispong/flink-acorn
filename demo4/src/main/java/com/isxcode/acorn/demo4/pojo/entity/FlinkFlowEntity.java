package com.isxcode.acorn.demo4.pojo.entity;

import com.isxcode.oxygen.flysql.annotation.ColumnName;
import com.isxcode.oxygen.flysql.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("FLINK_FLOW")
@Data
public class FlinkFlowEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ColumnName("FLOW_ID")
    private String flowId;

    @ColumnName("WEB_CONFIG")
    private String webConfig;

    @ColumnName("NODE_LINK")
    private String nodeLink;
}