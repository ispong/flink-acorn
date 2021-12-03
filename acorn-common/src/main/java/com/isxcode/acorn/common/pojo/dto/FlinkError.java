package com.isxcode.acorn.common.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FlinkError {

    private String code;

    private String msg;
}
