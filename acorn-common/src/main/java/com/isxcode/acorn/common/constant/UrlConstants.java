package com.isxcode.acorn.common.constant;

public interface UrlConstants {

    String BASE_URL = "http://%s:%s/flink-acorn";

    String EXECUTE_URL = BASE_URL + "/execute";

    String STOP_FLINK_URL = BASE_URL + "/stopJob";

    String GET_FLINK_URL = BASE_URL + "/getJobInfo";

    String GET_FLINK_LOG_URL = BASE_URL + "/getJobLog";
}
