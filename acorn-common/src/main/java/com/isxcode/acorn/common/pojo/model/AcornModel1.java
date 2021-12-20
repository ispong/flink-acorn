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
    public AcornModel1(TemplateType templateName, String executeId, String jobName, String filterCode, String fromConnectorSql, String toConnectorSql, String fromTableName, String toTableName) {

        super(templateName, executeId, jobName);
        this.filterCode = filterCode;
        this.fromConnectorSql = fromConnectorSql;
        this.toConnectorSql = toConnectorSql;
        this.fromTableName = fromTableName;
        this.toTableName = toTableName;
    }

    @Override
    public void check() {

        Assert.isNull(this.filterCode, "filterCode 不能为空");
        Assert.isNull(this.fromConnectorSql, "fromConnectorSql 不能为空");
        Assert.isNull(this.toConnectorSql, "toConnectorSql 不能为空");
        Assert.isNull(this.fromTableName, "fromTableName 不能为空");
        Assert.isNull(this.toTableName, "toTableName 不能为空");
    }
}
