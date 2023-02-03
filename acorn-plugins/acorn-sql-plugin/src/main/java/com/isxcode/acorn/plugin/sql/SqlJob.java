package com.isxcode.acorn.plugin.sql;

import com.isxcode.acorn.plugin.common.EnvFactory;
import com.isxcode.acorn.plugin.common.utils.SqlUtils;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.apache.logging.log4j.util.Strings;

import java.util.*;

public class SqlJob {

    public static List<String> selectSqlToOutSql(String selectSql, Map<String, List<String>> tableColumnMap) {

        List<String> sqlList = new ArrayList<>();

        String tableName = SqlUtils.getSelectSqlTableName(selectSql);

        sqlList.add("CREATE TABLE out_table (" + Strings.join(tableColumnMap.get(tableName), ',') + ") WITH ( 'connector' = 'out')");
        sqlList.add("insert into out_table " + selectSql);

        return sqlList;
    }

    public static void main(String[] args) {

        Map<String, List<String>> tableColumnMap = new HashMap<>();

        StreamTableEnvironment tEnv = EnvFactory.initStreamTableEnvironment("cluster");

        List<String> sqlList = Arrays.asList(args[0].split(";"));

        sqlList.forEach(metaSql -> {
            if ((sqlList.indexOf(metaSql) == sqlList.size() - 1) && !metaSql.contains("insert ") && metaSql.contains("select ")) {
                selectSqlToOutSql(metaSql, tableColumnMap).forEach(tEnv::executeSql);
            } else if (metaSql.toLowerCase().contains("create table ")) {
                String tableName = SqlUtils.getCreateSqlTableName(metaSql);
                List<String> columnList = SqlUtils.getCreateSqlColumnList(metaSql);
                tableColumnMap.put(tableName, columnList);

                tEnv.executeSql(metaSql);
            } else {
                tEnv.executeSql(metaSql);
            }
        });
    }
}
