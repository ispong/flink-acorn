package com.isxcode.acorn.common.constant;

/**
 * 所有请求路径
 */
public interface UrlConstants {

    /**
     * flink查询所有作业状态的路径
     */
    String FLINK_JOBS_OVERVIEW = "/jobs/overview";

    /**
     * 基础路径
     */
    String BASE_URL = "http://%s:%s/flink-acorn";

    /**
     * 发布作业接口
     */
    String EXECUTE_URL = "/job/execute";

    /**
     * 停止作业接口
     */
    String STOP_JOB_URL = "/job/stopJob";

    /**
     * 获取作业状态接口
     */
    String GET_JOB_STATUS_URL = "/job/getJobStatus";

    /**
     * 获取作业日志接口
     */
    String GET_JOB_LOG_URL = "/job/getJobLog";

    /**
     * 查询所有祖业状态接口
     */
    String QUERY_JOB_STATUS_URL = "/job/queryJobStatus";
}
