package com.isxcode.acorn.plugin.common.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

public class SqlUtils {

    public static String getSelectSqlTableName(String sql) {

        if (sql.contains(" left ") || sql.contains(" right ") || sql.contains(" where ") || sql.contains(" limit ")) {
            Pattern pattern = compile("(from ).+? ", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(sql);
            return matcher.find() ? matcher.group().trim().substring(5) : "";
        }

        return sql.split(" from ")[1].trim();
    }

    public static String getCreateSqlTableName(String sql) {

        Pattern pattern = compile("(create table).+?\\(", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(sql);
        return matcher.find() ? matcher.group().toLowerCase().replace("create table", "").replace("(", "").trim() : "";
    }

    public static List<String> getCreateSqlColumnList(String sql) {

        Pattern pattern = compile("\\(.+?(\\) with)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(sql.toLowerCase().replace("\n", ""));
        if (matcher.find()) {

            String[] columns = matcher.group().replace("(", "").replace(") with", "").split(",");
            List<String> columnList = new ArrayList<>();
            for (String column : columns) {
                columnList.add(column.trim());
            }
            return columnList;
        } else {
            return null;
        }
    }

}
