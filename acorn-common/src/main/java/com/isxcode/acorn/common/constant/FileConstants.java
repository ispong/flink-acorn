package com.isxcode.acorn.common.constant;

import java.io.File;

/**
 * 生成文件的相关静态参数
 */
public interface FileConstants {

    /**
     * 项目临时生成的所在路径
     */
    String JOB_TMP_PATH = File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator + "com" + File.separator + "isxcode" + File.separator + "acorn" + File.separator + "template";

    /**
     * 需要生成java文件的名称
     */
    String JOB_FILE_NAME = "FlinkJob.java";

    /**
     * pom文件的名称
     */
    String POM_XML = "pom.xml";

    /**
     * 日志文件的后缀
     */
    String LOG_SUFFIX = ".log";

}
