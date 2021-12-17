package com.isxcode.acorn.common.config;

import com.isxcode.acorn.common.properties.AcornNode;
import com.isxcode.acorn.common.properties.AcornProperties;
import com.isxcode.acorn.common.template.AcornTemplate;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({AcornProperties.class, AcornNode.class})
public class AcornAutoConfig {

    @Bean("acornTemplate")
    public AcornTemplate initAcornTemplate(AcornProperties acornProperties) {

        return new AcornTemplate(acornProperties);
    }
}
