package com.isxcode.acorn.common.pojo.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ColumnMapping {

    private String fromCol;

    private String toCol;
}
