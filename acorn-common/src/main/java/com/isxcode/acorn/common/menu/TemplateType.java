package com.isxcode.acorn.common.menu;

import lombok.Getter;

/**
 * 作业类型
 */
public enum TemplateType {
    
    /**
     * kafka输入mysql输出
     */
    KAFKA_INPUT_MYSQL_OUTPUT(""),

    /**
     * kafka输入kafka输出
     */
    KAFKA_INPUT_KAFKA_OUTPUT(""),

    /**
     * kafka输入hive输出
     */
    KAFKA_INPUT_HIVE_OUTPUT("");

    @Getter
    private final String templateFileName;

    TemplateType(String templateFileName) {

        this.templateFileName = templateFileName;
    };

}
