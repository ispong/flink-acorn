package com.definesys;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;

import static org.apache.flink.table.api.Expressions.$;
import static org.apache.flink.table.api.Expressions.ifThenElse;

public class Job {

public static void main(String[] args) {

        StreamExecutionEnvironment bsEnv = StreamExecutionEnvironment.getExecutionEnvironment();
        bsEnv.enableCheckpointing(5000);
        EnvironmentSettings settings = EnvironmentSettings.newInstance().useBlinkPlanner().inStreamingMode().build();
        StreamTableEnvironment tEnv = StreamTableEnvironment.create(bsEnv,settings);
        tEnv.getConfig().getConfiguration().setString(\\\"pipeline.name\\\", \\\"${flink.envType}_${flink.workId}\\\");

        tEnv.executeSql(\\\"CREATE TABLE from_${flink.workId} (\n\\\" +
            \\\"${flink.fromColumns}\\\"+
            \\\") WITH (\n\\\" +
            \\\"${flink.fromConnectInfo}\\\"+
            \\\")\\\");

        tEnv.executeSql(\\\"CREATE TABLE to_${flink.workId} (\n\\\" +
            \\\"${flink.toColumns}\\\"+
            \\\") WITH (\n\\\" +
            \\\"${flink.toConnectInfo}\\\"+
            \\\")\\\");

        Table fromData = tEnv.from(\\\"from_${flink.workId}\\\");

        ${flink.filter}

        fromData.executeInsert(\\\"to_${flink.workId}\\\");
    }
}
