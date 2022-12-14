package com.isxcode.acorn.common.controller;

import com.isxcode.acorn.api.pojo.BaseResponse;
import com.isxcode.acorn.common.response.CommonExceptionEnum;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exception")
public class ExceptionController {

    @RequestMapping("/keyIsNull")
    public BaseResponse<?> keyIsNull() {

        return new BaseResponse<>(CommonExceptionEnum.KEY_IS_NULL);
    }

    @RequestMapping("/keyIsError")
    public BaseResponse<?> keyIsError() {

        return new BaseResponse<>(CommonExceptionEnum.KEY_IS_ERROR);
    }
}
