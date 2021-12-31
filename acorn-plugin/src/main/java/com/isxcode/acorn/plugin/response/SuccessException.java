package com.isxcode.acorn.plugin.response;

import com.isxcode.acorn.common.response.AcornResponse;
import lombok.Getter;
import lombok.Setter;

public class SuccessException extends RuntimeException {

    @Setter
    @Getter
    private AcornResponse baseResponse;

    public SuccessException(AcornResponse baseResponse) {

        this.baseResponse = baseResponse;
    }
}
