package com.isxcode.acorn.common.constant;

/**
 * 安全相关静态常量
 */
public interface SecurityConstants {

    /**
     * 请求头中key的名称
     */
    String HEADER_AUTHORIZATION = "Acorn-Key";

    /**
     * 请求中没有key
     */
    String KEY_IS_NULL_EXCEPTION = "/exception/keyIsNull";

    /**
     * 请求中key错误
     */
    String KEY_IS_ERROR_EXCEPTION = "/exception/keyIsError";
}
