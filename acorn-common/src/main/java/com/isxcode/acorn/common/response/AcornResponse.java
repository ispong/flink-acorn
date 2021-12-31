package com.isxcode.acorn.common.response;

import com.isxcode.acorn.common.exception.AcornExceptionEnum;
import com.isxcode.acorn.common.pojo.dto.AcornData;
import lombok.Builder;
import lombok.Data;

/**
 * acorn 请求返回体
 */
@Data
@Builder
public class AcornResponse {

    private String code;

    private String message;

    private AcornData acornData;

    public AcornResponse() {
    }

    public AcornResponse(String code, String message, AcornData acornData) {
        this.code = code;
        this.message = message;
        this.acornData = acornData;
    }

    public AcornResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public AcornResponse(AcornExceptionEnum acornExceptionEnum) {

        this.code = acornExceptionEnum.getCode();
        this.message = acornExceptionEnum.getMessage();
    }
}
