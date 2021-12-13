package com.isxcode.demo2;

import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableEnvironment;

import static org.apache.flink.table.api.Expressions.$;

public class CanalDemo {

    public static void main(String[] args) {

        EnvironmentSettings settings = EnvironmentSettings.newInstance().build();
        TableEnvironment tEnv = TableEnvironment.create(settings);
        tEnv.getConfig().getConfiguration().setString("pipeline.name", "isxcode-pipeline");

        // properties.group.id | cat consumer.properties
        tEnv.executeSql("CREATE TABLE from_kafka(\n" +
                "   username STRING," +
                "   age INT" +
                ") WITH (\n" +
                "   'connector'='upsert-kafka'," +
                "   'topic'='ispong_kafka'," +
                "   'properties.group.id'='test-consumer-group'," +
                "   'key.format'='canal-json'," +
                "   'value.format'='canal-json'," +
                "   'properties.zookeeper.connect'='172.26.34.166:30099'," +
                "   'properties.bootstrap.servers'='172.26.34.166:30098'" +
                ")");

        tEnv.executeSql("CREATE TABLE to_mysql (\n" +
                "   username STRING primary key," +
                "   age INT" +
                ") WITH (\n" +
                "   'connector'='jdbc'," +
                "   'url'='jdbc:mysql://172.26.34.166:30010/dehoop'," +
                "   'table-name'='ispong_table_bak'," +
                "   'driver'='com.mysql.cj.jdbc.Driver'," +
                "   'username'='root'," +
                "   'password'='dehoop2021'" +
                ")");

        Table fromData = tEnv.from("from_kafka");

        // select中的字段需要根据mysql输出的字段顺序排序
        fromData = fromData.select(
                $("username").as("username"),
                $("age").as("age")
        );

        fromData.executeInsert("to_mysql");
    }
}
