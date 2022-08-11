package com.isxcode.acorn.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * flink节点配置
 */
@Data
@ConfigurationProperties("acorn.worker")
public class AcornServerProperties {

    private Map<String, AcornServerInfo> workers;

    private Boolean checkServer = true;
}
