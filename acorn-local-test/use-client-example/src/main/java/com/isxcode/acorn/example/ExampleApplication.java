package com.isxcode.acorn.example;

import com.isxcode.acorn.client.template.AcornTemplate;
import com.isxcode.acorn.api.pojo.AcornResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping
@RestController
@SpringBootApplication
@RequiredArgsConstructor
public class ExampleApplication {

    private final AcornTemplate acornTemplate;

    public static void main(String[] args) {

        SpringApplication.run(ExampleApplication.class, args);
    }

    @GetMapping("/executeQuery")
    public AcornResponse executeQuery() {

        String flinkSql = "CREATE TABLE from_table(\n" +
            "    username STRING,\n" +
            "    age INT\n" +
            ") WITH (\n" +
            "    'connector'='jdbc',\n" +
            "    'url'='jdbc:mysql://isxcode:30306/ispong_db',\n" +
            "    'table-name'='users',\n" +
            "    'driver'='com.mysql.cj.jdbc.Driver',\n" +
            "    'username'='ispong',\n" +
            "    'password'='ispong123');" +
            "CREATE TABLE out_table (\n" +
            "  username STRING,\n" +
            "  age INT\n" +
            ") WITH (\n" +
            "  'connector' = 'out'\n" +
            ")" +
            "insert into out_table select username, age from from_table";

        return acornTemplate.build().sql(flinkSql).deploy();
    }

    @GetMapping("/executeMysql")
    public AcornResponse executeMysql() {

        String flinkSql = "CREATE TABLE from_table(\n" +
            "    username STRING,\n" +
            "    age INT\n" +
            ") WITH (\n" +
            "    'connector'='jdbc',\n" +
            "    'url'='jdbc:mysql://dcloud-dev:30102/ispong_db',\n" +
            "    'table-name'='users',\n" +
            "    'driver'='com.mysql.cj.jdbc.Driver',\n" +
            "    'username'='ispong',\n" +
            "    'password'='ispong123');" +
            "CREATE TABLE to_table(\n" +
            "    username STRING,\n" +
            "    age INT\n" +
            ") WITH (\n" +
            "    'connector'='jdbc',\n" +
            "    'url'='jdbc:mysql://dcloud-dev:30102/ispong_db',\n" +
            "    'table-name'='users_sink',\n" +
            "    'driver'='com.mysql.cj.jdbc.Driver',\n" +
            "    'username'='ispong',\n" +
            "    'password'='ispong123');" +
            "insert into to_table select username, age from from_table";

        return acornTemplate.build().sql(flinkSql).deploy();
    }

    @GetMapping("/executeHive")
    public AcornResponse executeHive() {

        String flinkSql = "" +
            "CREATE CATALOG from_db WITH (\n" +
            "    'type' = 'hive',\n" +
            "    'hive-conf-dir' = '/home/dehoop/hive-conf'\n" +
            ");\n" +
            "\n" +
            "USE CATALOG from_db;\n" +
            "            \n" +
            "insert into ispong_db2.to_table select username, age from ispong_db.users_to;";

        return acornTemplate.build().sql(flinkSql).deploy();
    }

    @GetMapping("/executeKafka")
    public AcornResponse executeKafka() {

        String flinkSql = "" +
            "CREATE TABLE to_table(\n" +
            "    username STRING,\n" +
            "    age INT\n" +
            ") WITH (\n" +
            "    'connector'='jdbc',\n" +
            "    'url'='jdbc:mysql://dcloud-dev:30102/ispong_db',\n" +
            "    'table-name'='users_sink',\n" +
            "    'driver'='com.mysql.cj.jdbc.Driver',\n" +
            "    'username'='ispong',\n" +
            "    'password'='ispong123');" +
            "CREATE TABLE kafka_table (\n" +
            "  username STRING,\n" +
            "  age INT\n" +
            ") WITH (\n" +
            "  'connector' = 'kafka',\n" +
            "  'topic' = 'ispong-topic',\n" +
            "  'properties.bootstrap.servers' = 'dcloud-dev:30120',\n" +
            "  'properties.group.id' = 'test-consumer-group',\n" +
            "  'scan.startup.mode' = 'latest-offset',\n" +
            "  'format' = 'json'\n" +
            ");" +
            "insert into to_table select username, cast(null as int) from kafka_table";
        return acornTemplate.build().sql(flinkSql).deploy();
    }

    @GetMapping("/executePlugin")
    public AcornResponse executePlugin() {

        return acornTemplate.build().pluginName("hive-job-example").deploy();
    }

    @GetMapping("/getYarnStatus")
    public AcornResponse getYarnStatus(@RequestParam String applicationId) {

        return acornTemplate.build().applicationId(applicationId).getYarnStatus();
    }

    @GetMapping("/getJobManagerLog")
    public AcornResponse getJobManagerLog(@RequestParam String applicationId) {

        return acornTemplate.build().applicationId(applicationId).getJobManagerLog();
    }

    @GetMapping("/getTaskManagerLog")
    public AcornResponse getTaskManagerLog(@RequestParam String applicationId) {

        return acornTemplate.build().applicationId(applicationId).getTaskManagerLog();
    }

    @GetMapping("/getJobStatus")
    public AcornResponse getJobStatus(@RequestParam String flinkJobId) {

        return acornTemplate.build().jobId(flinkJobId).getJobStatus();
    }

    @GetMapping("/getRootExceptions")
    public AcornResponse getRootExceptions(@RequestParam String flinkJobId) {

        return acornTemplate.build().jobId(flinkJobId).getRootExceptions();
    }

    @GetMapping("/stopYarnJob")
    public AcornResponse stopYarnJob(@RequestParam String applicationId) {

        return acornTemplate.build().applicationId(applicationId).stopJob();
    }

}
