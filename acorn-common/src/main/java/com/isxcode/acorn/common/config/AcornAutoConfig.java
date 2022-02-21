package com.isxcode.acorn.common.config;

import com.isxcode.acorn.common.properties.AcornServerProperties;
import com.isxcode.acorn.common.properties.AcornPluginProperties;
import com.isxcode.acorn.common.template.AcornTemplate;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({AcornPluginProperties.class, AcornServerProperties.class})
public class AcornAutoConfig {

    /**
     * 初始化用户默认配置的节点信息
     *
     * @param acornServerProperties 结点配置信息
     * @return acornTemplate
     */
    @Bean("acornTemplate")
    public AcornTemplate acornTemplate(AcornServerProperties acornServerProperties) {

        return new AcornTemplate(acornServerProperties);
    }
}
