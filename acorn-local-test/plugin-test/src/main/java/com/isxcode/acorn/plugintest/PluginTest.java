package com.isxcode.acorn.plugintest;

import com.isxcode.acorn.plugin.sql.SqlJob;

import java.util.ArrayList;
import java.util.List;

public class PluginTest {

    public static void main(String[] args) {

        List<String> argsList = new ArrayList<>();
        argsList.add("CREATE TABLE from_table(\n" +
            "    username STRING,\n" +
            "    age INT\n" +
            ") WITH (\n" +
            "    'connector'='jdbc',\n" +
            "    'url'='jdbc:mysql://dcloud-dev:30102/ispong_db',\n" +
            "    'table-name'='users',\n" +
            "    'driver'='com.mysql.cj.jdbc.Driver',\n" +
            "    'username'='ispong',\n" +
            "    'password'='ispong123');" +
            " select * from from_table");

        SqlJob.main(argsList.toArray(new String[0]));
    }
}
