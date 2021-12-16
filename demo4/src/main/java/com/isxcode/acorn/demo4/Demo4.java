package com.isxcode.acorn.demo4;

import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableEnvironment;

import static org.apache.flink.table.api.Expressions.$;
import static org.apache.flink.table.api.Expressions.ifThenElse;

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

        // to upinsert kafka
        tEnv.executeSql("CREATE TABLE to_kafka(\n" +
                "   username STRING ," +
                "   age INT ," +
                "   __DELETE_LABEL__ INT,"+
                "   PRIMARY KEY (username) NOT ENFORCED " +
                ") WITH (\n" +
                "   'connector'='upsert-kafka'," +
                "   'topic'='ispong_kafka_doris'," +
                "   'properties.group.id'='test-consumer-group'," +
                "   'properties.zookeeper.connect'='192.168.66.66:30121'," +
                "   'properties.bootstrap.servers'='192.168.66.66:30120'," +
                "   'key.format' = 'json'," +
                "   'key.json.ignore-parse-errors' = 'true'," +
                "   'value.format' = 'json',"+
                "   'value.json.fail-on-missing-field' = 'false'" +
                ")");

        // json存入mysql
        Table from_csv_kafka = tEnv.from("from_canal_kafka");
        Table upinsertTable = from_csv_kafka.select(
                $("username").as("username"),
                $("age").as("age")
        ).addColumns(ifThenElse($("age").isNull(), 1, 0).as("__DELETE_LABEL__"));
        upinsertTable.executeInsert("to_kafka");

    }
}
