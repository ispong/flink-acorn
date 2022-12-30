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

    @GetMapping("/executeMysql")
    public AcornResponse executeMysql() {

        String flinkSql = "CREATE TABLE from_table(\n" +
            "    username STRING,\n" +
            "    age INT\n" +
            ") WITH (\n" +
            "    'connector'='jdbc',\n" +
            "    'url'='jdbc:mysql://isxcode:30306/ispong_db',\n" +
            "    'table-name'='users',\n" +
            "    'driver'='com.mysql.cj.jdbc.Driver',\n" +
            "    'username'='root',\n" +
            "    'password'='ispong123');" +
            "CREATE TABLE to_table(\n" +
            "    username STRING,\n" +
            "    age INT\n" +
            ") WITH (\n" +
            "    'connector'='jdbc',\n" +
            "    'url'='jdbc:mysql://isxcode:30306/ispong_db',\n" +
            "    'table-name'='users_sink',\n" +
            "    'driver'='com.mysql.cj.jdbc.Driver',\n" +
            "    'username'='root',\n" +
            "    'password'='ispong123');" +
            "insert into to_table select username, age from from_table";

        return acornTemplate.build().sql(flinkSql).deploy();
    }

    @GetMapping("/executeHive")
    public AcornResponse executeHive() {

        String flinkSql = "" +
            "CREATE CATALOG from_table WITH (\n" +
            "  'type' = 'hive',\n" +
            "  'hive-conf-dir' = '/opt/acorn/conf'\n" +
            ");\n" +
            "USE CATALOG from_table;" +
            "CREATE TABLE to_table(\n" +
            "    username STRING,\n" +
            "    age INT\n" +
            ") WITH (\n" +
            "    'connector'='jdbc',\n" +
            "    'url'='jdbc:mysql://isxcode:30306/ispong_db',\n" +
            "    'table-name'='users_sink',\n" +
            "    'driver'='com.mysql.cj.jdbc.Driver',\n" +
            "    'username'='root',\n" +
            "    'password'='ispong123');" +
            "insert into to_table select username, age from from_table";

        return acornTemplate.build().sql(flinkSql).deploy();
    }

    @GetMapping("/executePlugin")
    public AcornResponse executePlugin() {

        return acornTemplate.build().pluginName("sql-job-0.0.1").deploy();
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
