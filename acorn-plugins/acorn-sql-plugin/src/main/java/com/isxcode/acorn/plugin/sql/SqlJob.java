package com.isxcode.acorn.plugin.sql;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class SqlJob {

    private static final Logger LOG = LoggerFactory.getLogger(SqlJob.class);

    public static void main(String[] args) {

        StreamExecutionEnvironment bsEnv = StreamExecutionEnvironment.getExecutionEnvironment();
        bsEnv.enableCheckpointing(1000);
        EnvironmentSettings settings = EnvironmentSettings.newInstance().useBlinkPlanner().inStreamingMode().build();
        StreamTableEnvironment tEnv = StreamTableEnvironment.create(bsEnv,settings);

        String sql = args[0];
        String[] sqls = sql.split(";");
        Arrays.stream(sqls).forEach(metaSql -> {
            LOG.info("执行sql:" + metaSql);
            tEnv.executeSql(metaSql);
        });
    }
}
