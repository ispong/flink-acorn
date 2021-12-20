package com.isxcode.acorn.common.pojo.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AcornModel3 extends AcornModel {

    private String filterCode;

    private String fromConnectorSql;

    private String toConnectorSql;

    private String fromTableName;

    private String toTableName;
}
