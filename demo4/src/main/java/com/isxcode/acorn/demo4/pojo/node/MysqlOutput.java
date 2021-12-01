package com.isxcode.acorn.demo4.pojo.node;

import com.isxcode.acorn.demo4.pojo.dto.ColumnInfo;
import lombok.Data;

import java.util.List;

@Data
public class MysqlOutput {

    private String url;

    private String tableName;

    private String driver;

    private String username;

    private String password;

    private List<ColumnInfo> columnList;
}
