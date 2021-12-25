package com.isxcode.acorn.plugin.exception;

import com.isxcode.acorn.common.menu.ResponseEnum;
import com.isxcode.acorn.common.pojo.dto.AcornResponse;
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

        return new AcornResponse(ResponseEnum.REMOTE_ERROR);
    }

    /**
     * key错误
     */
    @RequestMapping("/keyIsError")
    public AcornResponse keyIsError() {

        return new AcornResponse(ResponseEnum.REMOTE_ERROR);
    }
}
