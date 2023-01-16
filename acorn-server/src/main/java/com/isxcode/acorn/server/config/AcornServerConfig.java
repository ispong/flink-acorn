package com.isxcode.acorn.server.config;

import com.isxcode.acorn.api.properties.AcornProperties;
import com.isxcode.acorn.server.utils.PrintUtils;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
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
    @ConditionalOnProperty(prefix = "acorn", name = "check-env", havingValue = "true")
    public void checkEnvironment() {

        String flinkHomePath = System.getenv("FLINK_HOME");
        if (Strings.isEmpty(flinkHomePath)) {
            PrintUtils.printErrorLog("请配置FLINK_HOME环境变量");
            context.close();
            return;
        }

        String acornHomePath = System.getenv("ACORN_HOME");
        if (Strings.isEmpty(acornHomePath)) {
            PrintUtils.printErrorLog("请配置ACORN_HOME环境变量");
            context.close();
            return;
        }

        String yarnConfDir = System.getenv("HADOOP_HOME");
        if (Strings.isEmpty(yarnConfDir)) {
            PrintUtils.printErrorLog("请配置HADOOP_HOME环境变量");
            context.close();
        }
    }
}
