package com.isxcode.acorn.demo4.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "acorn.config")
public class FlinkProperties {

    /**
     * 模板文件
     */
    private String templateDir;

    /**
     * 目标目录
     */
    private String targetDir;

    /**
     * 日志目录
     */
    private String logDir;
}
