package com.isxcode.acorn.template;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.SqlDialect;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.apache.flink.table.catalog.hive.HiveCatalog;

import static org.apache.flink.table.api.Expressions.$;
import static org.apache.flink.table.api.Expressions.ifThenElse;

public class FlinkJob {

    public static void main(String[] args) {

        StreamExecutionEnvironment bsEnv = StreamExecutionEnvironment.getExecutionEnvironment();
        bsEnv.enableCheckpointing(5000);
        EnvironmentSettings settings = EnvironmentSettings.newInstance().useBlinkPlanner().inStreamingMode().build();
        StreamTableEnvironment tEnv = StreamTableEnvironment.create(bsEnv,settings);
        tEnv.getConfig().getConfiguration().setString("pipeline.name", "${jobName}");

        // --- kafka ---
        tEnv.getConfig().setSqlDialect(SqlDialect.DEFAULT);

        // kafka输入
        tEnv.executeSql("${fromConnectorSql}");

        // --- insert ---
        Table fromData = tEnv.from("${fromTableName}");

        ${filterCode}

        // --- hive ---
        String name = "to_table";
        String defaultDatabase = "${hiveDatabase}";
        String hiveConfDir = "${hiveConfigPath}";

        tEnv.getConfig().setSqlDialect(SqlDialect.HIVE);
        HiveCatalog hive = new HiveCatalog(name, defaultDatabase, hiveConfDir);
        tEnv.registerCatalog(name, hive);
        tEnv.useCatalog(name);

        tEnv.createTemporaryView("${fromTableName}_tmp", fromData);

        tEnv.getConfig().setSqlDialect(SqlDialect.DEFAULT);
        tEnv.executeSql("INSERT INTO ${hiveTable} SELECT ${hiveInsertColumns} FROM ${fromTableName}_tmp");
    }
}

