package com.isxcode.acorn.common.pojo.model;

import com.isxcode.acorn.common.menu.TemplateType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class AcornModel {

    private TemplateType templateName;

    private String executeId;

    private String jobName;

    public void check() {

        Assert.isNull(this.templateName, "templateName 不能为空");
    }
}
