package com.isxcode.acorn.demo;

import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.commons.cli.Option;

public class SqlJob {

    public static void main(String[] args) {

        EnvironmentSettings settings = EnvironmentSettings.newInstance().build();
        TableEnvironment tEnv = TableEnvironment.create(settings);
        tEnv.getConfig().getConfiguration().setString("pipeline.name", "ispong-pipeline");

        tEnv.executeSql("CREATE CATALOG from_db WITH (\n" +
            "    'type' = 'hive',\n" +
            "    'hive-version' = '3.1.2', \n" +
            "    'hive-conf-dir' = '/opt/hive/conf',\n" +
            "    'default-database' = 'ispong_db'\n" +
            ")");

        tEnv.executeSql("USE CATALOG from_db");

        tEnv.executeSql("insert into users_sink select username, age, birth from users");
    }
}
