package com.isxcode.demo3;

import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.Expressions;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableEnvironment;

public class Demo {

    public static void main(String[] args) {

        // 构建环境
        EnvironmentSettings settings = EnvironmentSettings.newInstance().build();
        TableEnvironment tEnv = TableEnvironment.create(settings);
        tEnv.getConfig().getConfiguration().setString("pipeline.name", "isxcode-pipeline");

        // kafka输入
        tEnv.executeSql("CREATE TABLE from_kafka(\n" +
                "   username STRING," +
                "   age INT," +
                "   lucky_datetime TIMESTAMP," +
                "   lucky_date DATE," +
                "   lucky_time TIME" +
                ") WITH (\n" +
                "   'connector'='kafka'," +
                "   'topic'='ispong_kafka'," +
                "   'properties.zookeeper.connect'='39.103.230.188:30121'," +
                "   'properties.bootstrap.servers'='39.103.230.188:30120'," +
                "   'format'='json'," +
                "   'json.timestamp-format.standard'='SQL'," +
                "   'json.ignore-parse-errors'='true'," +
                "   'json.fail-on-missing-field'='false'" +
                ")");

        // mysql输出
        tEnv.executeSql("CREATE TABLE to_mysql (\n" +
                "   username STRING," +
                "   age INT," +
                "   lucky_time TIME," +
                "   lucky_date DATE," +
                "   lucky_datetime TIMESTAMP" +
                ") WITH (\n" +
                "   'connector'='jdbc'," +
                "   'url'='jdbc:mysql://47.103.203.73:3306/VATtest'," +
                "   'table-name'='ispong_table'," +
                "   'driver'='com.mysql.cj.jdbc.Driver'," +
                "   'username'='admin'," +
                "   'password'='gsw921226'" +
                ")");

        // 处理数据流
        Table fromData = tEnv.from("from_kafka");

        // select中的字段一定根据mysql输出的字段顺序排序
        fromData = fromData.select(
                Expressions.$("username").as("username"),
                Expressions.$("age").as("age"),
                Expressions.$("lucky_time").as("lucky_time"),
                Expressions.$("lucky_date").as("lucky_date"),
                Expressions.$("lucky_datetime").as("lucky_datetime")
        );

        // 输出数据流
        fromData.executeInsert("to_mysql");
    }
}
