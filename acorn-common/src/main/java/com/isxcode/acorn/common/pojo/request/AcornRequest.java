package com.isxcode.acorn.common.pojo.request;

import com.isxcode.acorn.common.menu.TemplateType;
import lombok.Data;
import org.springframework.util.Assert;

@Data
public abstract class AcornRequest {

    private TemplateType templateName;

    private String executeId;

    private String jobName;

    public void check() {
        Assert.isNull(this.templateName, "templateName 不能为空");
        Assert.isNull(this.executeId, "executeId 不能为空");
        Assert.isNull(this.jobName, "jobName 不能为空");
    }
}
