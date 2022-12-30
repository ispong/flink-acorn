package com.isxcode.acorn.plugin.sql;

import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.TableEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class SqlJob {

    private static final Logger LOG = LoggerFactory.getLogger(SqlJob.class);

    public static void main(String[] args) {

        LOG.info("开始执行作业");

        EnvironmentSettings settings = EnvironmentSettings.newInstance().build();
        LOG.info("构建环境成功");

        TableEnvironment tEnv = TableEnvironment.create(settings);
        LOG.info("创建tEnv成功");

        String sql = args[0];
        String[] sqls = sql.split(";");
        Arrays.stream(sqls).forEach(metaSql -> {
            LOG.info("执行sql:" + metaSql);
            try {
                tEnv.executeSql(metaSql);
            } catch (Exception e) {
                LOG.error(e.getMessage());
            }
        });

        LOG.info("任务部署成功");
    }
}
