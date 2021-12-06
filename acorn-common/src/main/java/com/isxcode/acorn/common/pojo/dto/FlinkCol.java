package com.isxcode.acorn.common.pojo.dto;

import com.isxcode.acorn.common.menu.FlinkSqlType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class FlinkCol {

    private String name;

    private FlinkSqlType type;
}
