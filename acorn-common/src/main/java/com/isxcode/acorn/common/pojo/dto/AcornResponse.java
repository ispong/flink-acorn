package com.isxcode.acorn.common.pojo.dto;

import com.isxcode.acorn.common.menu.ResponseEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AcornResponse {

    private String code;

    private String message;

    public AcornResponse(ResponseEnum responseEnum) {

        this.code = responseEnum.getCode();
        this.message = responseEnum.getMessage();
    }
}
