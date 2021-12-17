package com.isxcode.acorn.common.pojo.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.util.Assert;

@Data
@EqualsAndHashCode(callSuper = true)
public class AcornModel1 extends AcornRequest {

    private String filterCode;

    private String fromConnectorSql;

    private String toConnectorSql;

    private String fromTableName;

    private String toTableName;

    @Override
    public void check() {

        super.check();
        Assert.isNull(this.filterCode, "filterCode 不能为空");
        Assert.isNull(this.fromConnectorSql, "fromConnectorSql 不能为空");
        Assert.isNull(this.toConnectorSql, "toConnectorSql 不能为空");
        Assert.isNull(this.fromTableName, "fromTableName 不能为空");
        Assert.isNull(this.toTableName, "toTableName 不能为空");
    }
}
