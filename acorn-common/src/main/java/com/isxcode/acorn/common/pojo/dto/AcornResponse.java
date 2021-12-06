package com.isxcode.acorn.common.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AcornResponse {

    private String code;

    private String msg;
}
