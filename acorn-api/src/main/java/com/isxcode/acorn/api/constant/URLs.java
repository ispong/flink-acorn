package com.isxcode.acorn.api.constant;

/**
 * 所有请求路径
 */
public interface URLs {

    String HTTP = "http://";

    String HTTPS = "https://";

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
    String EXECUTE_SQL_URL = "/job/executeSql";

    /**
     * 发布作业接口
     */
    String EXECUTE_JAR_URL = "/job/executeJar";

    /**
     * 发布作业接口
     */
    String EXECUTE_JAVA_URL = "/job/executeJava";

    /**
     * 发布作业接口
     */
    String EXECUTE_JSON_URL = "/job/executeJson";

    /**
     * 停止作业接口
     */
    String STOP_JOB_URL = "/job/stopJob";

    /**
     * 获取作业状态接口
     */
    String GET_JOB_STATUS_URL = "/job/getJobStatus";

    /**
     * 获取作业状态接口
     */
    String GET_YARN_STATUS_URL = "/job/getYarnStatus";

    /**
     * 获取查询数据接口
     */
    String GET_DATA_URL = "/job/getData";

    /**
     * 获取yarn中JobManager日志
     */
    String GET_JOB_MANAGER_LOG_URL = "/job/getJobManagerLogs";

    /**
     * 获取yarn中TaskManager日志
     */
    String GET_TASK_MANAGER_LOG_URL = "/job/getTaskManagerLogs";

    /**
     * 获取作业日志接口
     */
    String GET_ROOT_EXCEPTIONS_URL = "/job/getRootExceptions";

    /**
     * 获取作业flink jobId
     */
    String GET_JOB_ID_URL = "/job/getJobId";

    /**
     * 查询所有祖业状态接口
     */
    String QUERY_JOB_STATUS_URL = "/job/queryJobStatus";

    /**
     * 心跳检查接口
     */
    String HEART_CHECK_URL = "/heartCheck";
}
