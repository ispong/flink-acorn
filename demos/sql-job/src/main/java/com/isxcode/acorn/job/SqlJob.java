package com.isxcode.acorn.job;

//import org.apache.flink.table.api.EnvironmentSettings;
//import org.apache.flink.table.api.TableEnvironment;

public class SqlJob {

    public static void main(String[] args) {

        System.out.println("start sql job");
//        EnvironmentSettings settings = EnvironmentSettings.newInstance().build();
//        TableEnvironment tEnv = TableEnvironment.create(settings);
//        tEnv.getConfig().getConfiguration().setString("pipeline.name", "isxcode-pipeline");
//
//        tEnv.executeSql("CREATE TABLE from_table(\n" +
//            "    username STRING,\n" +
//            "    age INT\n" +
//            ") WITH (\n" +
//            "    'connector'='jdbc',\n" +
//            "    'url'='jdbc:mysql://isxcode:30306/ispong_db',\n" +
//            "    'table-name'='users',\n" +
//            "    'driver'='com.mysql.cj.jdbc.Driver',\n" +
//            "    'username'='root',\n" +
//            "    'password'='ispong123')");
//
//        tEnv.executeSql("CREATE TABLE to_table(\n" +
//            "    username STRING,\n" +
//            "    age INT\n" +
//            ") WITH (\n" +
//            "    'connector'='jdbc',\n" +
//            "    'url'='jdbc:mysql://isxcode:30306/ispong_db',\n" +
//            "    'table-name'='users_sink',\n" +
//            "    'driver'='com.mysql.cj.jdbc.Driver',\n" +
//            "    'username'='root',\n" +
//            "    'password'='ispong123')");
//
//        tEnv.executeSql("insert into to_table select username, age from from_table");
    }
}
