package com.isxcode.acorn.api.exception;

import lombok.Getter;

public abstract class AbstractException extends RuntimeException {

	@Getter private final String code;

	@Getter private final String msg;

    public AbstractException(AbstractExceptionEnum abstractExceptionEnum) {

        this.code = abstractExceptionEnum.getCode();
        this.msg = abstractExceptionEnum.getMsg();
    }

	public AbstractException(String code, String msg) {

		this.code = code;
		this.msg = msg;
	}

	public AbstractException(String msg) {

		this.code = null;
		this.msg = msg;
	}
}
