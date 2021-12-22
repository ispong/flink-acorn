package com.isxcode.acorn.demo4;

import com.sun.istack.Nullable;
import org.apache.flink.kafka.shaded.org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer;
import org.apache.flink.streaming.connectors.kafka.KafkaSerializationSchema;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.Expressions;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.thrift.TException;
import org.apache.calcite.sql.parser.SqlParser;

import java.nio.charset.StandardCharsets;
import java.util.Properties;

import static org.apache.flink.table.api.Expressions.$;
import static org.apache.flink.table.api.Expressions.ifThenElse;

public class Demo4 {

    public static void main(String[] args) {

        EnvironmentSettings settings = EnvironmentSettings.newInstance().build();
        TableEnvironment tEnv = TableEnvironment.create(settings);
        tEnv.getConfig().getConfiguration().setString("pipeline.name", "isxcode-pipeline");
        tEnv.getConfig().getConfiguration().setString("table.exec.sink.not-null-enforcer", "drop");

        // scan.startup.mode latest-offset
        // canal 读取数据
        tEnv.executeSql("CREATE TABLE from_canal_kafka(\n" +
                "   username STRING," +
                "   age INT" +
                ") WITH (\n" +
                "   'connector'='kafka'," +
                "   'topic'='ispong_kafka'," +
                "   'scan.startup.mode' = 'earliest-offset'," +
                "   'properties.group.id'='test-consumer-group'," +
                "   'properties.zookeeper.connect'='192.168.66.66:30121'," +
                "   'properties.bootstrap.servers'='192.168.66.66:30120'," +
                "   'format'='canal-json'," +
                "   'canal-json.ignore-parse-errors'='true'" +
                ")").print();

        // to upinsert kafka
        tEnv.executeSql("CREATE TABLE to_kafka(\n" +
                "   username STRING ," +
                "   age INT ," +
                "   PRIMARY KEY (username) NOT ENFORCED " +
                ") WITH (\n" +
                "   'connector'='upsert-kafka'," +
                "   'topic'='ispong_kafka_doris'," +
                "   'properties.group.id'='test-consumer-group'," +
                "   'properties.zookeeper.connect'='192.168.66.66:30121'," +
                "   'properties.bootstrap.servers'='192.168.66.66:30120'," +
                "   'key.format' = 'json'," +
                "   'key.json.ignore-parse-errors' = 'true'," +
                "   'value.format' = 'json',"+
                "   'value.json.fail-on-missing-field' = 'false'," +
                "   'value.fields-include' = 'ALL'" +
                ")");

        Table from_csv_kafka = tEnv.from("from_canal_kafka");
        Table upinsertTable = from_csv_kafka.select(
                $("username").as("username"),
                $("age").as("age")
        );
        upinsertTable.executeInsert("to_kafka");

        // 需要重新整理这个kafka中的数据 ispong_kafka_doris
        // 解出key和value 重新生成json放入指定的doris中kafka

    }
}
