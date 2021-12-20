package com.isxcode.acorn.common.pojo.model;

import com.isxcode.acorn.common.menu.TemplateType;
import lombok.Data;
import org.springframework.util.Assert;

@Data
public abstract class AcornModel {

    private String templateName;

    private String executeId;

    private String jobName;

    public AcornModel() {
    }

    public AcornModel(TemplateType templateName, String executeId, String jobName) {
        this.templateName = templateName.getTemplateFileName() + ".ftl";
        this.executeId = executeId;
        this.jobName = jobName;
    }

    public void check() {

        Assert.isNull(this.templateName, "templateName 不能为空");
    }
}
