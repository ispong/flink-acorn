package com.isxcode.acorn.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * flink节点配置
 */
@Data
@ConfigurationProperties("acorn.node")
public class AcornNodeProperties {

    /**
     * 插件服务器ip
     */
    private String host;

    /**
     * 插件服务器端口
     */
    private int port;

    /**
     * 插件服务器密钥
     */
    private String key;
}
