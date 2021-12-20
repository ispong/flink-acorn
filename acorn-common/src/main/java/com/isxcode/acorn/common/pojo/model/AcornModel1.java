package com.isxcode.acorn.common.pojo.model;

import com.isxcode.acorn.common.menu.TemplateType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AcornModel1 {

    private TemplateType templateName;

    private String executeId;

    private String jobName;

    private String filterCode;

    private String fromConnectorSql;

    private String toConnectorSql;

    private String fromTableName;

    private String toTableName;

    public void check() {

    }
}
