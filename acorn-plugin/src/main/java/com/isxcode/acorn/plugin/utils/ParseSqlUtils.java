package com.isxcode.acorn.plugin.utils;

import java.util.regex.Pattern;

/**
 * 解析sql工具类
 */
public class ParseSqlUtils {

    /**
     * 获取sql中的表名
     */
    public static String getTableName(String sql) {

        if (sql == null) {
            return null;
        }
        sql = Pattern.compile("CREATE * TABLE").matcher(sql).replaceAll("");
        return Pattern.compile("\\( .*").matcher(sql).replaceAll("").trim();
    }
}
