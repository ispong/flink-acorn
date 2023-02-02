package com.isxcode.acorn.plugin.sql;

import com.isxcode.acorn.plugin.common.EnvFactory;
import org.apache.flink.table.api.TableResult;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.apache.flink.types.Row;
import org.apache.flink.util.CloseableIterator;

import java.util.Arrays;
import java.util.List;

public class SqlJob {

    public static void main(String[] args) {

        StreamTableEnvironment tEnv = EnvFactory.initStreamTableEnvironment("cluster");

        List<String> sqlList = Arrays.asList(args[0].split(";"));

        sqlList.forEach(metaSql -> {
            if ((sqlList.indexOf(metaSql) == sqlList.size() - 1) && !metaSql.contains("insert")) {
                TableResult tableResult = tEnv.executeSql(metaSql);
                try (CloseableIterator<Row> it = tableResult.collect()) {
                    while (it.hasNext()) {
                        Row row = it.next();
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
