package com.isxcode.demo2;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;

import static org.apache.flink.table.api.Expressions.$;

public class Demo {
    public static void main(String[] args) {

        // 创建flink环境
        StreamExecutionEnvironment bsEnv = StreamExecutionEnvironment.getExecutionEnvironment();
        // 每隔5秒上传一次文件
        bsEnv.enableCheckpointing(5000);
        EnvironmentSettings settings = EnvironmentSettings.newInstance().useBlinkPlanner().inStreamingMode().build();
        StreamTableEnvironment tEnv = StreamTableEnvironment.create(bsEnv,settings);
        // 配置管道名称
        tEnv.getConfig().getConfiguration().setString("pipeline.name", "isxcode-flink-pipeline");

        // --- from kafka ---
        tEnv.executeSql("CREATE TABLE from_kafka (\n" +
                "   age INT" +
//                "   username STRING," +
//                "   lucky_date TIMESTAMP(3) METADATA FROM 'timestamp' " +
                ") WITH (\n" +
                "   'connector' = 'kafka'," +
                "   'topic' = 'ispong-kafka'," +
                "   'properties.zookeeper.connect' = '39.103.230.188:30121'," +
                "   'properties.bootstrap.servers' = '39.103.230.188:30120'," +
                "   'format' = 'csv'," +
                "   'csv.ignore-parse-errors' = 'true'" +
                ")");

        // --- to mysql ---
        tEnv.executeSql("CREATE TABLE to_mysql (\n" +
                "   age int" +
//                "   username varchar(150)" +
//                "   lucky_date date " +
                ") WITH (\n" +
                "   'connector' = 'jdbc'," +
                "   'url' = 'jdbc:mysql://47.103.203.73:3306/VATtest'," +
                "   'table-name' = 'ispong_table'," +
                "   'driver' = 'com.mysql.cj.jdbc.Driver'," +
                "   'username'='admin'," +
                "   'password' = 'gsw921226'"+
                ")");

        // from data
        Table fromData = tEnv.from("from_kafka");

        // filter
        fromData = fromData.select($("age").as("age"));
//        select($("username").as("username"))
//                .select($("lucky_date").as("lucky_date"));

        // to data
        fromData.executeInsert("to_mysql");
    }

}