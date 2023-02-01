package com.isxcode.acorn.plugin.common;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;

public class EnvFactory {

    public static StreamTableEnvironment initStreamTableEnvironment(String envType) {

        StreamExecutionEnvironment env;
        if ("local".equals(envType)) {
            env = StreamExecutionEnvironment.createLocalEnvironment();
        }else{
            env = StreamExecutionEnvironment.getExecutionEnvironment();
        }

        EnvironmentSettings settings = EnvironmentSettings.newInstance().inStreamingMode().build();

        return StreamTableEnvironment.create(env, settings);
    }
}
