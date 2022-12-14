package com.isxcode.acorn.server.config;

import com.isxcode.acorn.api.properties.AcornProperties;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(AcornProperties.class)
public class AcornServerConfig {

    private final ConfigurableApplicationContext context;

    @Bean
    @ConditionalOnClass(AcornServerConfig.class)
    public void checkEnvironment() {

        String flinkHomePath = System.getenv("FLINK_HONE");
        if (Strings.isEmpty(flinkHomePath)) {
            System.out.println("ERROR:请配置FLINK_HONE环境变量");
            context.close();
        }

        String acornHomePath = System.getenv("ACORN_HONE");
        if (Strings.isEmpty(acornHomePath)) {
            System.out.println("ERROR:请配置ACORN_HONE环境变量");
            context.close();
        }

        String yarnConfDir = System.getenv("YARN_CONF_DIR");
        if (Strings.isEmpty(yarnConfDir)) {
            System.out.println("ERROR:请配置YARN_CONF_DIR环境变量");
            context.close();
        }
    }
}
