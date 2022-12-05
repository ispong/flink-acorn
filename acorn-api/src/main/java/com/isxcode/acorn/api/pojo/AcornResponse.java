package com.isxcode.acorn.api.pojo;

import com.isxcode.acorn.api.exception.AcornExceptionEnum;
import com.isxcode.acorn.api.pojo.dto.AcornData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * acorn 响应体
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AcornResponse {

    private String code;

    private String message;

    private AcornData acornData;

    public AcornResponse(String code, String message) {

        this.code = code;
        this.message = message;
    }

    public AcornResponse(AcornExceptionEnum acornExceptionEnum) {

        this.code = acornExceptionEnum.getCode();
        this.message = acornExceptionEnum.getMessage();
    }
}
