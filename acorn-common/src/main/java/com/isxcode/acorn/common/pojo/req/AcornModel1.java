package com.isxcode.acorn.common.pojo.req;

import lombok.Data;

@Data
public class AcornModel1 implements AcornRequest {

    private String jobName;

    private String executeId;

    private String filterCode;

    private String fromConnectorSql;

    private String toConnectorSql;

    private String fromTableName;

    private String toTableName;
}
