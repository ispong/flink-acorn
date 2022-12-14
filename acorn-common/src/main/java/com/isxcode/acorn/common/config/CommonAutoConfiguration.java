package com.isxcode.acorn.common.config;

import com.isxcode.acorn.api.properties.AcornProperties;
import com.isxcode.acorn.common.controller.ExceptionController;
import com.isxcode.acorn.common.filter.CommonKeyFilter;
import com.isxcode.acorn.common.response.GlobalExceptionAdvice;
import com.isxcode.acorn.common.response.SuccessResponseAdvice;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;

@Slf4j
@RequiredArgsConstructor
@EnableConfigurationProperties(AcornProperties.class)
public class CommonAutoConfiguration {

    private final AcornProperties acornProperties;

    private final MessageSource messageSource;

    @Bean
    @ConditionalOnClass(CommonAutoConfiguration.class)
    private CommonKeyFilter initCommonFilter() {

        return new CommonKeyFilter(acornProperties);
    }

    @Bean
    @ConditionalOnClass(CommonAutoConfiguration.class)
    private ExceptionController initExceptionController() {

        return new ExceptionController();
    }

    @Bean
    @ConditionalOnClass(CommonAutoConfiguration.class)
    private GlobalExceptionAdvice initGlobalExceptionAdvice() {

        return new GlobalExceptionAdvice();
    }

    @Bean
    @ConditionalOnClass(CommonAutoConfiguration.class)
    private SuccessResponseAdvice initSuccessResponseAdvice() {

        return new SuccessResponseAdvice(messageSource);
    }
}
