package com.isxcode.acorn.demo4.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "acorn.config")
public class FlinkProperties {

    /**
     * 临时存储位置
     */
    private String tmpDir;

    /**
     * 日志存储位置
     */
    private String logDir;
}
