package com.isxcode.acorn.common.constant;

import java.io.File;

/**
 * 生成文件的相关的静态常量
 */
public interface FileConstants {

    /**
     * 项目临时生成的所在路径
     */
    String JOB_TMP_PATH = File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator + "com" + File.separator + "isxcode" + File.separator + "acorn" + File.separator + "template";

    /**
     * 生成java文件的名称
     */
    String JOB_FILE_NAME = "FlinkJob.java";

    /**
     * 生成log文件的名称
     */
    String JOB_LOG_NAME = "acorn.log";

    /**
     * pom文件模板位置
     */
    String POM_TEMPLATE_PATH = "templates/pom.xml";

    /**
     * pom文件的名称
     */
    String POM_XML = "pom.xml";

    /**
     * 生成jar包的名字
     */
    String FLINK_JAR_NAME = "acorn.jar";

    /**
     * 成功返回flink的日志内容
     */
    String SUCCESS_FLINK_LOG = "Job has been submitted with JobID";

    /*
     * 临时项目目录
     *
     * @ispong
     */
    String JOB_PROJECT_NAME = "acorn";

    /*
     * acorn的json模板名称
     *
     * @ispong
     */
    String ACORN_JSON_TEMPLATE_NAME = "ACORN_JSON_TEMPLATE.ftl";

    /*
     * acorn的java模板名称
     *
     * @ispong
     */
    String ACORN_JAVA_TEMPLATE_NAME = "ACORN_JAVA_TEMPLATE.ftl";

    /*
     * acorn的sql模板名称
     *
     * @ispong
     */
    String ACORN_SQL_TEMPLATE_NAME = "ACORN_SQL_TEMPLATE.ftl";

}
