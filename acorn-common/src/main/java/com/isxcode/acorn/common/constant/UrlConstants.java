package com.isxcode.acorn.common.constant;

/**
 * 所有请求路径
 */
public interface UrlConstants {

    /**
     * 基础路径
     */
    String BASE_URL = "http://%s:%s/flink-acorn";

    String EXECUTE_URL = BASE_URL + "/job/execute";

    String STOP_JOB_URL = BASE_URL + "/job/stopJob?jobId=";

    String GET_JOB_STATUS_URL = BASE_URL + "/job/getJobStatus?jobId=";

    String GET_JOB_LOG_URL = BASE_URL + "/job/getJobLog?executeId=";
}
