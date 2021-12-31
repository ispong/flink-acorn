package com.isxcode.acorn.common.pojo.model;

import com.isxcode.acorn.common.menu.TemplateType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * 模型1
 * 只支持一个节点输入  一个节点输出  中间可以编写自己的sql
 */
@Data
@Builder
@AllArgsConstructor
public class AcornModel1 {

    /**
     * flink job Id
     */
    private String jobId;

    /**
     * 模板类型
     */
    private TemplateType templateName;

    /**
     * 执行id
     */
    private String executeId;

    /**
     * 作业的名称 会在flink的job中看到jobName
     */
    private String jobName;

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
