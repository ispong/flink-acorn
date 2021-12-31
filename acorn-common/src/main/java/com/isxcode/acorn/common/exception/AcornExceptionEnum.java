package com.isxcode.acorn.common.exception;

import lombok.Getter;

/**
 * 所有返回异常
 */
public enum AcornExceptionEnum {

    JAVA_CODE_GENERATE_ERROR("50001", "代码初始化失败"),

    BUILD_COMMAND_ERROR("50002", "运行发布命令错误，请查看日志"),

    EXECUTE_COMMAND_ERROR("50003", "执行发布命令错误，请查看日志"),

    READ_LOG_FILE_ERROR("50004", "读取日志文件失败"),

    KEY_IS_NULL("50005", "key为null"),

    KEY_IS_ERROR("50006", "key不正确"),
    ;

    @Getter
    private final String code;

    @Getter
    private final String message;

    AcornExceptionEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
