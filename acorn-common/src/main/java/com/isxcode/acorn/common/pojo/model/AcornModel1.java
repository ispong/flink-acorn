package com.isxcode.acorn.common.pojo.model;

import com.isxcode.acorn.common.menu.TemplateType;
import lombok.*;
import org.springframework.util.Assert;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AcornModel1 extends AcornModel {

    private String filterCode;

    private String fromConnectorSql;

    private String toConnectorSql;

    private String fromTableName;

    private String toTableName;

    public AcornModel1() {
        super();
    }

    @Builder
    public AcornModel1(TemplateType templateName, String executeId, String jobName, String filterCode, String fromConnectorSql, String toConnectorSql) {

        super(templateName, executeId, jobName);
        this.filterCode = filterCode;
        this.fromConnectorSql = fromConnectorSql;
        this.toConnectorSql = toConnectorSql;
    }

    @Override
    public void check() {

    }
}
