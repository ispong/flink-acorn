package com.isxcode.acorn.api.pojo;

import com.isxcode.acorn.api.exception.AcornExceptionEnum;
import com.isxcode.acorn.api.pojo.dto.AcornData;
import com.isxcode.oxygen.common.response.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@NoArgsConstructor
public class AcornResponse extends BaseResponse<AcornData> {

    public AcornResponse(String code, String msg) {

        super(code, msg);
    }

    public AcornResponse(AcornExceptionEnum acornExceptionEnum) {

        super(acornExceptionEnum.getCode(), acornExceptionEnum.getMessage());
    }
}
