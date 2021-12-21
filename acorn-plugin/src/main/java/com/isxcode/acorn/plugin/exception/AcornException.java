package com.isxcode.acorn.plugin.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AcornException extends RuntimeException {

    private final String code;

    private final String msg;

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public AcornException(String msg, String code) {

        this.code = code;
        this.msg = msg;
    }
}
