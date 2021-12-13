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

        // canal 读取数据
        tEnv.executeSql("CREATE TABLE from_canal_kafka(\n" +
                "   username STRING," +
                "   age INT" +
                ") WITH (\n" +
                "   'connector'='kafka'," +
                "   'topic'='ispong_kafka'," +
                "   'properties.group.id'='test-consumer-group'," +
                "   'properties.zookeeper.connect'='172.26.34.166:30099'," +
                "   'properties.bootstrap.servers'='172.26.34.166:30098'," +
                "   'format'='canal-json'," +
                "   'canal-json.ignore-parse-errors'='true'" +
                ")");

        // 装成csv
        tEnv.executeSql("CREATE TABLE from_csv_kafka(\n" +
                "   username STRING ," +
                "   age INT" +
                ") WITH (\n" +
                "   'connector'='upsert-kafka'," +
                "   'topic'='ispong_kafka_json'," +
                "   'properties.zookeeper.connect'='172.26.34.166:30099'," +
                "   'properties.bootstrap.servers'='172.26.34.166:30098'," +
                "   'key.format' = 'json'," +
                "   'key.json.ignore-parse-errors'='false'," +
                "   'value.format' = 'json'," +
                "   'value.json.ignore-parse-errors'='false'" +
                ")");

        // 输入到mysql
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

        // 存入json
        Table fromData = tEnv.from("from_kafka");
        fromData = fromData.select(
                $("username").as("username"),
                $("age").as("age")
        );
        fromData.executeInsert("from_csv_kafka");

        // json存入mysql
        Table from_csv_kafka = tEnv.from("from_csv_kafka");
        from_csv_kafka = from_csv_kafka.select(
                $("username").as("username"),
                $("age").as("age")
        );
        from_csv_kafka.executeInsert("to_mysql");

    }
}
