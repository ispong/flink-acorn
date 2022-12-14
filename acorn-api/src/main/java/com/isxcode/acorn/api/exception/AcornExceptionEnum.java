package com.isxcode.acorn.api.exception;

import lombok.Getter;

/**
 * 所有返回异常
 */
public enum AcornExceptionEnum implements AbstractExceptionEnum {

    JAVA_CODE_GENERATE_ERROR("50001", "代码初始化失败"),

    BUILD_COMMAND_ERROR("50002", "运行发布命令错误，请查看日志"),

    EXECUTE_COMMAND_ERROR("50003", "执行发布命令错误，请查看日志"),

    READ_LOG_FILE_ERROR("50004", "读取日志失败"),

    KEY_IS_NULL("50005", "key为null"),

    KEY_IS_ERROR("50006", "key不正确"),

    REQUEST_VALUE_EMPTY("50007", "缺少输入参数"),

    ACORN_SERVER_NOT_FOUND("50008", "没有发现Acorn服务"),

    ACORN_SERVER_IS_EMPTY("50009", "Acorn服务为空"),

    FLINK_SERVICE_ERROR("500010", "flink服务异常"),;

    @Getter
    private final String code;

    @Getter
    private final String msg;

    AcornExceptionEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
