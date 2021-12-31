package com.isxcode.acorn.plugin.exception;

import com.isxcode.acorn.common.exception.AcornExceptionEnum;
import com.isxcode.acorn.common.response.AcornResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exception")
public class ExceptionController {

    /**
     * key为null
     */
    @RequestMapping("/keyIsNull")
    public AcornResponse keyIsNull() {

        return new AcornResponse(AcornExceptionEnum.KEY_IS_NULL);
    }

    /**
     * key错误
     */
    @RequestMapping("/keyIsError")
    public AcornResponse keyIsError() {

        return new AcornResponse(AcornExceptionEnum.KEY_IS_ERROR);
    }
}
