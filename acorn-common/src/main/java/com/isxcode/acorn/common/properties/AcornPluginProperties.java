package com.isxcode.acorn.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 服务器查询相关配置
 */
@Data
@ConfigurationProperties("acorn.plugin")
public class AcornPluginProperties {

    /**
     * 临时存储位置
     */
    private String tmpDir = "/opt/flink-acorn/tmp";

    /**
     * 服务器密钥
     */
    private String key = "acorn-key";

    /**
     * flink默认端口
     */
    private int flinkPort = 8081;

    /**
     * flink默认host
     */
    private String flinkHost = "127.0.0.1";

    /**
     * 配置hive文件路径
     */
    private String hiveConfPath = "/opt/flink/conf";

    /**
     * 保存tmp中文件
     */
    private boolean storageTmp = false;
}
