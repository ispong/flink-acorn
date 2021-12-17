package com.isxcode.acorn.common.pojo.dto;

import com.isxcode.acorn.common.menu.ResponseEnum;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AcornResponse {

    private String code;

    private String message;

    public AcornResponse(String message, String code) {

        this.code = code;
        this.message = message;
    }

    public AcornResponse(ResponseEnum responseEnum) {

        this.code = responseEnum.getCode();
        this.message = responseEnum.getMessage();
    }
}
