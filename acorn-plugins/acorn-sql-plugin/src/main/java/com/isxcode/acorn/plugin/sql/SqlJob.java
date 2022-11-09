package com.isxcode.acorn.plugin.sql;

import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.TableEnvironment;

import java.util.Arrays;

public class SqlJob {

    public static void main(String[] args) {

        EnvironmentSettings settings = EnvironmentSettings.newInstance().build();
        TableEnvironment tEnv = TableEnvironment.create(settings);

        String sql = args[0];
        String[] metaSql = sql.split(";");
        Arrays.stream(metaSql).forEach(tEnv::executeSql);
    }
}
