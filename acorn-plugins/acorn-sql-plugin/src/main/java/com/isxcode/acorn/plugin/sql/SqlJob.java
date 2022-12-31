package com.isxcode.acorn.plugin.sql;

import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.TableEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class SqlJob {

    private static final Logger LOG = LoggerFactory.getLogger(SqlJob.class);

    public static void main(String[] args) {

        EnvironmentSettings settings = EnvironmentSettings.newInstance().build();
        TableEnvironment tEnv = TableEnvironment.create(settings);

        String sql = args[0];
        String[] sqls = sql.split(";");
        Arrays.stream(sqls).forEach(metaSql -> {
            LOG.info("执行sql:" + metaSql);
            tEnv.executeSql(metaSql);
        });
    }
}
