package com.isxcode.acorn.api.pojo;

import com.isxcode.acorn.api.exception.AbstractExceptionEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponse<T> {

	private String code;

	private String msg;

	private T data;

    public BaseResponse(String code, String message) {

        this.code = code;
        this.msg = message;
    }

    public BaseResponse(AbstractExceptionEnum abstractExceptionEnum) {

        this.code = abstractExceptionEnum.getCode();
        this.msg = abstractExceptionEnum.getMsg();
    }
}
