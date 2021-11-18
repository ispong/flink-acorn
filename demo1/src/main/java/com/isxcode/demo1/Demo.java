package com.isxcode.demo1;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.SqlDialect;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.apache.flink.table.catalog.hive.HiveCatalog;

import static org.apache.flink.table.api.Expressions.$;

public class Demo {
    public static void main(String[] args) {

        StreamExecutionEnvironment bsEnv = StreamExecutionEnvironment.getExecutionEnvironment();
        bsEnv.enableCheckpointing(5000);
        EnvironmentSettings settings = EnvironmentSettings.newInstance().useBlinkPlanner().inStreamingMode().build();
        StreamTableEnvironment tEnv = StreamTableEnvironment.create(bsEnv,settings);
        tEnv.getConfig().getConfiguration().setString("pipeline.name", "isxcode-pipeline");

        // --- kafka ---
        tEnv.getConfig().setSqlDialect(SqlDialect.DEFAULT);
        tEnv.executeSql("CREATE TABLE from_kafka (\n" +
                "   `sql` STRING," +
                "   `select` INT" +
                ") WITH (\n" +
                "   'connector'='kafka'," +
                "   'topic'='ispong_kafka'," +
                "   'properties.zookeeper.connect'='39.103.230.188:30121'," +
                "   'properties.bootstrap.servers'='39.103.230.188:30120'," +
                "   'format' = 'json'," +
                "   'json.ignore-parse-errors' = 'false'," +
                "   'json.fail-on-missing-field' = 'true'"+
                ")");

        // --- insert ---
        Table fromData = tEnv.from("from_kafka");
        fromData = fromData.select($("sql").as("sql"), $("select").as("select"));

        // --- hive ---
        String name = "to_hive";
        String defaultDatabase = "cdh_dev";
        String hiveConfDir = "/opt/flink/conf";

        tEnv.getConfig().setSqlDialect(SqlDialect.HIVE);
        HiveCatalog hive = new HiveCatalog(name, defaultDatabase, hiveConfDir);
        tEnv.registerCatalog(name, hive);
        tEnv.useCatalog(name);

        tEnv.createTemporaryView("from_kafka_tmp", fromData);
//        tEnv.executeSql("insert into ispong_table (username,age) values('zhangsan',4)");

        tEnv.getConfig().setSqlDialect(SqlDialect.DEFAULT);
        tEnv.executeSql("INSERT INTO ispong_table SELECT `sql`,`select` FROM from_kafka_tmp");
//        tEnv.executeSql("select * from to_hive.cdh_dev.ispong_table").print();
    }

}