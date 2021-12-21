package com.isxcode.acorn.plugin.utils;

import java.util.regex.Pattern;

public class ParseSqlUtils {

    public static String getTableName(String sql) {

        sql = Pattern.compile("CREATE * TABLE").matcher(sql).replaceAll("");
        return Pattern.compile("\\( .*").matcher(sql).replaceAll("").trim();
    }
}
