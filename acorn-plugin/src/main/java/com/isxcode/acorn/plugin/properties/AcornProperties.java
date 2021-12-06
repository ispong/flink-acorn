package com.isxcode.acorn.plugin.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.File;

@Data
@Component
@ConfigurationProperties(prefix = "acorn.config")
public class AcornProperties {

    /**
     * 临时存储位置
     */
    private String tmpDir = System.getProperty("user.home") + File.separator + "acorn-plugin" + File.separator + "tmp";

    /**
     * 日志存储位置
     */
    private String logDir = System.getProperty("user.home") + File.separator + "acorn-plugin" + File.separator + "log";
}
