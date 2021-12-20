package com.isxcode.acorn.plugin.controller;

import com.isxcode.acorn.common.menu.ResponseEnum;
import com.isxcode.acorn.common.pojo.dto.AcornResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exception")
public class ExceptionController {

    /**
     * token为null异常
     */
    @RequestMapping("/tokenIsNull")
    public AcornResponse tokenIsNull() {

        return new AcornResponse(ResponseEnum.REMOTE_ERROR);
    }

    /**
     * token不合法异常
     */
    @RequestMapping("/tokenIsInvalid")
    public AcornResponse tokenIsInvalid() {

        return new AcornResponse(ResponseEnum.REMOTE_ERROR);
    }

    /**
     * 权限不足异常
     */
    @RequestMapping("/authError")
    public AcornResponse exceptionAuthError() {

        return new AcornResponse(ResponseEnum.REMOTE_ERROR);
    }
}
