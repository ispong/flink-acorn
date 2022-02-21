package com.isxcode.acorn.common.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * flink节点配置
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = "acorn.client.server")
public class AcornServerInfo {

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
