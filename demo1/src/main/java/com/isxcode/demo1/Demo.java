package com.isxcode.demo1;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;

import static org.apache.flink.table.api.Expressions.$;

public class Demo {
    public static void main(String[] args) {

        StreamExecutionEnvironment bsEnv = StreamExecutionEnvironment.getExecutionEnvironment();
        bsEnv.enableCheckpointing(5000);
        EnvironmentSettings settings = EnvironmentSettings.newInstance().useBlinkPlanner().inStreamingMode().build();
        StreamTableEnvironment tEnv = StreamTableEnvironment.create(bsEnv,settings);
        tEnv.getConfig().getConfiguration().setString("pipeline.name", "isxcode-flink-pipeline");

        // --- from kafka ---
        tEnv.executeSql("CREATE TABLE from_kafka (\n" +
                "   username STRING," +
                "   age INT" +
                ") WITH (\n" +
                "   'connector'='kafka'," +
                "   'topic'='ispong-input-1'," +
                "   'properties.zookeeper.connect'='101.132.135.228:30099'," +
                "   'properties.bootstrap.servers'='101.132.135.228:30098'," +
                "   'format' = 'csv'" +
                ")");

        // --- to kafka ---
        tEnv.executeSql("CREATE TABLE to_kafka (\n" +
                "   username STRING" +
                ") WITH (\n" +
                "   'connector'='kafka'," +
                "   'topic'='ispong-input-2'," +
                "   'properties.zookeeper.connect'='101.132.135.228:30099'," +
                "   'properties.bootstrap.servers'='101.132.135.228:30098'," +
                "   'format' = 'csv'," +
                "   'csv.ignore-parse-errors' = 'true'" +
                ")");

        // from data
        Table fromData = tEnv.from("from_kafka");

        // filter
        fromData = fromData.select($("username").as("username"));

        // to data
        fromData.executeInsert("to_kafka");
    }

}