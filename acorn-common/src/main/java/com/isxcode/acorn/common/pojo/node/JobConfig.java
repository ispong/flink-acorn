package com.isxcode.acorn.common.pojo.node;

import com.isxcode.acorn.common.menu.TemplateType;
import lombok.Data;

@Data
public class JobConfig {

    /**
     * 模板类型
     */
    private TemplateType templateName;

    /**
     * 过滤代码
     */
    private String filterCode;

    /**
     * 开源sql
     */
    private String fromConnectorSql;

    /**
     * 目标code
     */
    private String toConnectorSql;

    /**
     * 不用传入
     */
    private String fromTableName;

    /**
     * 不用传入
     */
    private String toTableName;

    /**
     * hive的表名
     */
    private String hiveTable;

    /**
     * hive表所在的数据库名
     */
    private String hiveDatabase;

    /**
     * 不用传入
     */
    private String hiveConfigPath;

    /**
     * hive中选择需要同步的字段
     */
    private String hiveInsertColumns;
}
