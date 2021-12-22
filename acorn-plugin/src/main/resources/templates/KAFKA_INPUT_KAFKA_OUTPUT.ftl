package com.isxcode.acorn.template;

import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableEnvironment;

import static org.apache.flink.table.api.Expressions.$;
import static org.apache.flink.table.api.Expressions.ifThenElse;

public class FlinkJob {

    public static void main(String[] args) {

        StreamExecutionEnvironment bsEnv = StreamExecutionEnvironment.getExecutionEnvironment();
        bsEnv.enableCheckpointing(5000);
        EnvironmentSettings settings = EnvironmentSettings.newInstance().useBlinkPlanner().inStreamingMode().build();
        StreamTableEnvironment tEnv = StreamTableEnvironment.create(bsEnv,settings);
        tEnv.getConfig().getConfiguration().setString("pipeline.name", "${jobName}");

        // kafka输入
        tEnv.executeSql("${fromConnectorSql}");

        // kafka输出
        tEnv.executeSql("${toConnectorSql}");

        Table fromData = tEnv.from("${fromTableName}");

        ${filterCode}

        // 输出数据流
        fromData.executeInsert("${toTableName}");
    }
}
