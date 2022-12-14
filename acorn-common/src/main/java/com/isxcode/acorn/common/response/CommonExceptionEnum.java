package com.isxcode.acorn.common.response;

import com.isxcode.acorn.api.exception.AbstractExceptionEnum;
import lombok.Getter;

public enum CommonExceptionEnum implements AbstractExceptionEnum {

    KEY_IS_NULL("50005", "key is null"),

    KEY_IS_ERROR("50006", "key is error"),;

    @Getter
    private final String code;

    @Getter
    private final String msg;

    CommonExceptionEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
