package com.isxcode.acorn.server.config;

import com.isxcode.acorn.api.properties.AcornProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(AcornProperties.class)
public class AcornServerConfig {

}
