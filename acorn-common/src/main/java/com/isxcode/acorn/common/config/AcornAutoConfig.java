package com.isxcode.acorn.common.config;

import com.isxcode.acorn.common.properties.AcornNodeProperties;
import com.isxcode.acorn.common.properties.AcornPluginProperties;
import com.isxcode.acorn.common.template.AcornTemplate;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({AcornPluginProperties.class, AcornNodeProperties.class})
public class AcornAutoConfig {

    @Bean("acornTemplate")
    public AcornTemplate initAcornTemplate(AcornNodeProperties acornNodeProperties) {

        return new AcornTemplate(acornNodeProperties);
    }
}
