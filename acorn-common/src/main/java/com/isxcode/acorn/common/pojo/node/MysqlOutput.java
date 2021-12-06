package com.isxcode.acorn.common.pojo.node;

import com.isxcode.acorn.common.pojo.dto.FlinkCol;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class MysqlOutput extends NodeBaseInfo{

    private String url;

    private String tableName;

    private String driver;

    private String username;

    private String password;

    private List<FlinkCol> columnList;
}
