package com.isxcode.demo3;

import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableEnvironment;

import static org.apache.flink.table.api.Expressions.$;

public class Demo {

    public static void main(String[] args) {

        EnvironmentSettings settings = EnvironmentSettings.newInstance().build();
        TableEnvironment tEnv = TableEnvironment.create(settings);
        tEnv.getConfig().getConfiguration().setString("pipeline.name", "isxcode-pipeline");

        /**
         * example:
         * {
         *     "username":"john",
         *     "lucky_date":"2020-12-12",
         *     "age":12,
         *     "lucky_datetime":"2020-12-12 12:12:12",
         *     "lucky_time":"12:12:12"
         * }
         */
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

        //  mysql --> flink
        //  datetime -> TIMESTAMP
        //  date --> DATE
        //  time --> TIME
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

        Table fromData = tEnv.from("from_kafka");

        // select中的字段一定根据mysql输出的字段顺序排序
        fromData = fromData.select(
                $("username").as("username"),
                $("age").as("age"),
                $("lucky_time").as("lucky_time"),
                $("lucky_date").as("lucky_date"),
                $("lucky_datetime").as("lucky_datetime")
        );

        fromData.executeInsert("to_mysql");
    }
}
