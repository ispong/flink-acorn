package com.isxcode.acorn.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * flink节点配置
 */
@Data
@ConfigurationProperties("acorn")
public class AcornProperties {

    private String secret = "acorn-key";

    private Boolean checkServers = false;

    private Map<String, ServerInfo> servers;

    private String flinkConfDir = "/opt/flink/conf";

    private String flinkLibDir = "/opt/flink/lib";

    private String flinkDistPath = "/opt/flink/lib/flink-dist_2.12-1.14.0.jar";
}
