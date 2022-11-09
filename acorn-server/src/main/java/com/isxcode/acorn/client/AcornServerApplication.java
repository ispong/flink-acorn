package com.isxcode.acorn.client;

import com.isxcode.acorn.common.properties.AcornProperties;
import com.isxcode.acorn.common.properties.ServerInfo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({AcornProperties.class, ServerInfo.class})
public class AcornServerApplication {

    public static void main(String[] args) {

        SpringApplication.run(AcornServerApplication.class, args);
    }
}
