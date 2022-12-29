package com.isxcode.acorn.plugin.sql;

import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.TableEnvironment;

import java.util.Arrays;

public class SqlJob {

    public static void main(String[] args) {

        System.out.println("开始执行作业");

        EnvironmentSettings settings = EnvironmentSettings.newInstance().build();
        System.out.println("构建环境成功");

        TableEnvironment tEnv = TableEnvironment.create(settings);
        System.out.println("创建tEnv成功");

        String sql = args[0];
        String[] sqls = sql.split(";");
        Arrays.stream(sqls).forEach(metaSql -> {
            System.out.println("执行sql:" + metaSql);
            tEnv.executeSql(metaSql);
        });

        System.out.println("任务部署成功");
    }
}
