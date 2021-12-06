package com.isxcode.acorn.common.config;

import com.isxcode.acorn.common.service.AcornTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AcornAutoConfig {

    @Bean("acornTemplate")
    public AcornTemplate initAcornTemplate() {

        return new AcornTemplate();
    }
}
