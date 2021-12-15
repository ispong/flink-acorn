package com.isxcode.acorn.demo4;

import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableEnvironment;

import static org.apache.flink.table.api.Expressions.$;

public class Demo4 {

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
                "   'scan.startup.mode' = 'earliest-offset'," +
                "   'properties.group.id'='test-consumer-group'," +
                "   'properties.zookeeper.connect'='192.168.66.66:30121'," +
                "   'properties.bootstrap.servers'='192.168.66.66:30120'," +
                "   'format'='canal-json'," +
                "   'canal-json.ignore-parse-errors'='true'" +
                ")");

        // from kafka
//        tEnv.executeSql("CREATE TABLE from_kafka(\n" +
//                "   username STRING PRIMARY KEY," +
//                "   age INT" +
//                ") WITH (\n" +
//                "   'connector'='upsert-kafka'," +
//                "   'topic'='ispong_kafka_json'," +
//                "   'properties.group.id'='test-consumer-group'," +
//                "   'properties.zookeeper.connect'='172.26.34.166:30099'," +
//                "   'properties.bootstrap.servers'='172.26.34.166:30098'," +
//                "   'key.format' = 'csv'," +
//                "   'key.csv.ignore-parse-errors'='false'," +
//                "   'value.format' = 'csv'," +
//                "   'value.csv.ignore-parse-errors'='false'" +
//                ")");

        // 输入到mysql
        tEnv.executeSql("CREATE TABLE to_mysql (\n" +
                "   username STRING PRIMARY KEY," +
                "   age INT" +
                ") WITH (\n" +
                "   'connector'='jdbc'," +
                "   'driver'='com.mysql.cj.jdbc.Driver'," +
                "   'url'='jdbc:mysql://192.168.66.66:30102/dehoop'," +
                "   'table-name'='ispong_table_bak'," +
                "   'username'='root'," +
                "   'password'='dehoop2021'" +
                ")");

        // 存入json
//        Table fromData = tEnv.from("from_canal_kafka");
//        fromData = fromData.select(
//                $("username").as("username"),
//                $("age").as("age")
//        );
//        fromData.executeInsert("from_kafka");

        // json存入mysql
        Table from_csv_kafka = tEnv.from("from_canal_kafka");
        from_csv_kafka = from_csv_kafka.select(
                $("username").as("username"),
                $("age").as("age")
        );
        from_csv_kafka.executeInsert("to_mysql");

    }
}
