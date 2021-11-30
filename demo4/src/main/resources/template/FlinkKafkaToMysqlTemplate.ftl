package com.definesys;

import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableEnvironment;

import static org.apache.flink.table.api.Expressions.$;
import static org.apache.flink.table.api.Expressions.ifThenElse;

public class Job {

    public static void main(String[] args) {

        EnvironmentSettings settings = EnvironmentSettings.newInstance().build();
        TableEnvironment tEnv = TableEnvironment.create(settings);
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
