package com.isxcode.acorn.common.constant;

/**
 * 所有请求路径
 */
public interface UrlConstants {

    String FLINK_JOBS_OVERVIEW = "/jobs/overview";

    /**
     * 基础路径
     */
    String BASE_URL = "http://%s:%s/flink-acorn";

    String EXECUTE_URL = "/job/execute";

    String STOP_JOB_URL = "/job/stopJob";

    String GET_JOB_STATUS_URL = "/job/getJobStatus";

    String GET_JOB_LOG_URL = "/job/getJobLog";

    String QUERY_JOB_STATUS_URL = "/job/queryJobStatus";
}
