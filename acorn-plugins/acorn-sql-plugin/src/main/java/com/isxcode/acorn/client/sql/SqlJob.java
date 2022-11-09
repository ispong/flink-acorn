package com.isxcode.acorn.client.sql;

import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.TableEnvironment;

public class SqlJob {

    public static void main(String[] args) {

        EnvironmentSettings settings = EnvironmentSettings.newInstance().build();
        TableEnvironment tEnv = TableEnvironment.create(settings);

        System.out.println(args[0]);
    }
}
