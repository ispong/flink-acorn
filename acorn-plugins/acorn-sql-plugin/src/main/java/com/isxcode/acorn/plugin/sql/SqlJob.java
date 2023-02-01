package com.isxcode.acorn.plugin.sql;

import com.isxcode.acorn.plugin.common.EnvFactory;
import org.apache.flink.table.api.TableResult;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.apache.flink.types.Row;
import org.apache.flink.util.CloseableIterator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.List;

public class SqlJob {

    private static final Logger log = LogManager.getLogger("SqlJob");

    public static void main(String[] args) {

        log.error("初始化环境");
        StreamTableEnvironment tEnv = EnvFactory.initStreamTableEnvironment("local");

        log.warn("解析sql");
        List<String> sqlList = Arrays.asList(args[0].split(";"));

        log.info("执行sql语句");
        sqlList.forEach(metaSql -> {
            if ((sqlList.indexOf(metaSql) == sqlList.size() - 1) && !metaSql.contains("insert")) {
                TableResult tableResult = tEnv.executeSql(metaSql);
                try (CloseableIterator<Row> it = tableResult.collect()) {
                    while (it.hasNext()) {
                        Row row = it.next();
                        log.info("data:" + row);
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } else {
                tEnv.executeSql(metaSql);
            }
        });
    }
}
