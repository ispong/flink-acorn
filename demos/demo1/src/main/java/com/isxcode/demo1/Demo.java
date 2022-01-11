package com.isxcode.demo1;

import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableEnvironment;

import static org.apache.flink.table.api.Expressions.$;

public class Demo {

    public static void main(String[] args) {

        EnvironmentSettings settings = EnvironmentSettings.newInstance().build();
        TableEnvironment tEnv = TableEnvironment.create(settings);
        tEnv.getConfig().getConfiguration().setString("pipeline.name", "ispong-test");

        // --- from kafka ---
        tEnv.executeSql("CREATE TABLE from_kafka (\n" +
                "   username STRING," +
                "   age INT" +
                ") WITH (\n" +
                "   'connector'='kafka'," +
                "   'topic'='ispong-test'," +
                "   'properties.zookeeper.connect'='39.103.230.188:30121'," +
                "   'properties.bootstrap.servers'='39.103.230.188:30120'," +
                "   'properties.group.id'='testGroup'," +
                "   'scan.startup.mode'='latest-offset',"+
                "   'format' = 'csv'," +
                "   'csv.ignore-parse-errors' = 'true'," +
                "   'csv.field-delimiter'=','"+
                ")");

        // --- to mysql ---
        tEnv.executeSql("CREATE TABLE to_mysql (\n" +
                "   username STRING," +
                "   age INT" +
                ") WITH (\n" +
                "   'connector'='jdbc'," +
                "   'url'='jdbc:mysql://47.103.203.73:3306/VATtest'," +
                "   'table-name'='ispong_flink_table'," +
                "   'driver'='com.mysql.cj.jdbc.Driver'," +
                "   'username'='admin'," +
                "   'password'='gsw921226'" +
                ")");

        Table fromData = tEnv.from("from_kafka");

        fromData = fromData.select($("username").as("username"), $("age").as("age"));

        fromData.executeInsert("to_mysql");
    }
}
