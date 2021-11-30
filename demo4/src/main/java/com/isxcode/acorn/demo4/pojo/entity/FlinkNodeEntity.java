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
@TableName("FLINK_NODE")
@Data
public class FlinkNodeEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ColumnName("NODE_ID")
    private String nodeId;

    @ColumnName("FLOW_ID")
    private String flowId;

    @ColumnName("NAME")
    private String name;

    @ColumnName("TYPE")
    private String type;

    @ColumnName("CONFIG")
    private String config;
}