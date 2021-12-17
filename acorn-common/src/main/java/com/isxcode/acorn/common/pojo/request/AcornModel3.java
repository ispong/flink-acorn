package com.isxcode.acorn.common.pojo.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AcornModel3 extends AcornRequest {

    private String filterCode;

    private String fromConnectorSql;

    private String toConnectorSql;

    private String fromTableName;

    private String toTableName;
}
