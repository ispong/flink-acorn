package com.isxcode.acorn.plugin.utils;

import java.util.regex.Pattern;

public class ParseSqlUtils {

    public static String getTableName(String sql) {

        if (sql == null) {
            return null;
        }
        sql = Pattern.compile("CREATE * TABLE").matcher(sql).replaceAll("");
        return Pattern.compile("\\( .*").matcher(sql).replaceAll("").trim();
    }
}
