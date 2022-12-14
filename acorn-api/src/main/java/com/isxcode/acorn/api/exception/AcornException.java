package com.isxcode.acorn.api.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AcornException extends AbstractException {

    public AcornException(AbstractExceptionEnum abstractExceptionEnum) {
        super(abstractExceptionEnum);
    }

    public AcornException(String code, String msg) {
        super(code, msg);
    }

    public AcornException(String msg) {
        super(msg);
    }
}
