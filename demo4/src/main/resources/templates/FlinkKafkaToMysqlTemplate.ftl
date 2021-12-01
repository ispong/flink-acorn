package com.isxcode.acorn.demo4;

import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableEnvironment;

import static org.apache.flink.table.api.Expressions.$;
import static org.apache.flink.table.api.Expressions.ifThenElse;

public class FlinkJob {

    public static void main(String[] args) {

            // 构建环境
            EnvironmentSettings settings = EnvironmentSettings.newInstance().build();
            TableEnvironment tEnv = TableEnvironment.create(settings);
            tEnv.getConfig().getConfiguration().setString("pipeline.name", "${flowId}");

            // kafka输入
            tEnv.executeSql("CREATE TABLE from_kafka(" +
            <#list kafkaInput.columnList as column>
            "   ${column.name} ${column.type} <#if column_has_next> ," + <#else > " + </#if>
            </#list>
            ") WITH (" +
            "   'connector'='kafka'," +
            "   'topic'='${kafkaInput.topic}'," +
            "   'properties.zookeeper.connect'='${kafkaInput.zookeeper}'," +
            "   'properties.bootstrap.servers'='${kafkaInput.brokerList}'," +
            "   'format'='${kafkaInput.dataFormat}'," +
            <#if (kafkaInput.dataFormat)=='CSV' >
            "   'csv.ignore-parse-errors' = 'true'" +
            <#else>
            "   'json.timestamp-format.standard'='SQL'," +
            "   'json.ignore-parse-errors'='true'," +
            "   'json.fail-on-missing-field'='false'" +
            </#if>
            ")");

            // mysql输出
            tEnv.executeSql("CREATE TABLE to_mysql (\n" +
            <#list mysqlOutput.columnList as column>
            "   ${column.name} ${column.type} <#if column_has_next> ," + <#else > " + </#if>
            </#list>
            ") WITH (\n" +
            "   'connector'='jdbc'," +
            "   'url'='${mysqlOutput.url}'," +
            "   'table-name'='${mysqlOutput.tableName}'," +
            "   'driver'='${mysqlOutput.driver}'," +
            "   'username'='${mysqlOutput.username}'," +
            "   'password'='${mysqlOutput.password}'" +
            ")");

            // 处理数据流
            Table fromData = tEnv.from("from_kafka");

            // 输出数据流
            fromData.executeInsert("to_mysql");
    }
}
