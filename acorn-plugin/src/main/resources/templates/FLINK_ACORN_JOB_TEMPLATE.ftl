package com.isxcode.acorn.template;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;

public class FlinkJob {

    public static void main(String[] args) {

        StreamExecutionEnvironment bsEnv = StreamExecutionEnvironment.getExecutionEnvironment();
        bsEnv.enableCheckpointing(5000);
        EnvironmentSettings settings = EnvironmentSettings.newInstance().useBlinkPlanner().inStreamingMode().build();
        StreamTableEnvironment tEnv = StreamTableEnvironment.create(bsEnv,settings);
        tEnv.getConfig().getConfiguration().setString("pipeline.name", "${jobName}");

        <#list flinkSqlList as flinkSql>
            tEnv.executeSql("${flinkSql}");
        </#list>

    }
}
