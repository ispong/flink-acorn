package com.isxcode.acorn.api.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * flink节点配置
 */
@Data
@ConfigurationProperties("acorn")
public class AcornProperties {

    /*
     * 访问密钥
     *
     * @ispong
     */
    private String secret = "acorn-key";

    /*
     * 检查连接数据
     *
     * @ispong
     */
    private Boolean checkServers = false;

    /*
     * 检查acorn的服务是否可以正常使用
     *
     * @ispong
     */
    private Boolean checkEnv = true;

    /*
     * 配置服务器信息
     *
     * @ispong
     */
    private Map<String, ServerInfo> servers;

}
