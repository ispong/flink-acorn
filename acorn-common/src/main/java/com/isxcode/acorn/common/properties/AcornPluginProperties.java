package com.isxcode.acorn.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.File;

@Data
@ConfigurationProperties("acorn.plugin")
public class AcornPluginProperties {

    /**
     * 临时存储位置
     */
    private String tmpDir = System.getProperty("user.home") + File.separator + "acorn-plugin" + File.separator + "tmp";

    /**
     * 日志存储位置
     */
    private String logDir = System.getProperty("user.home") + File.separator + "acorn-plugin" + File.separator + "log";

    /**
     * 服务器密钥
     */
    private String serverKey = "acorn-key";

    /**
     * 保存tmp中文件
     */
    private boolean storageTmp = false;
}
