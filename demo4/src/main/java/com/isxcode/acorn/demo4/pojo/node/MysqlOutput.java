package com.isxcode.acorn.demo4.pojo.node;

import com.isxcode.acorn.demo4.pojo.dto.ColumnInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class MysqlOutput extends NodeBaseInfo{

    private String url;

    private String tableName;

    private String driver;

    private String username;

    private String password;

    private List<ColumnInfo> columnList;
}
