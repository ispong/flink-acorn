package com.isxcode.acorn.api.pojo;

import com.isxcode.acorn.api.pojo.dto.AcornData;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AcornResponse extends BaseResponse<AcornData> {

    public AcornResponse(String code, String msg, AcornData acornData) {

        super(code, msg, acornData);
    }

    public AcornResponse(String code, String msg) {

        super(code, msg);
    }
}
