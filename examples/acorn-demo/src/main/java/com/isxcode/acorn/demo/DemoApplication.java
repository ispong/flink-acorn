package com.isxcode.acorn.demo;

import com.isxcode.acorn.client.template.AcornTemplate;
import com.isxcode.acorn.common.pojo.AcornResponse;
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
public class DemoApplication {

    private final AcornTemplate acornTemplate;

    public static void main(String[] args) {

        SpringApplication.run(DemoApplication.class, args);
    }

    @GetMapping("/execute")
    public AcornResponse executeFlinkSql() {

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

    @GetMapping("/getYarnStatus")
    public AcornResponse getYarnStatus(@RequestParam String applicationId) {

        return acornTemplate.build().applicationId(applicationId).getYarnStatus();
    }

    @GetMapping("/getYarnLog")
    public AcornResponse getYarnLog(@RequestParam String applicationId) {

        return acornTemplate.build().applicationId(applicationId).getYarnLog();
    }

    @GetMapping("/getJobStatus")
    public AcornResponse getJobStatus(@RequestParam String flinkJobId) {

        return acornTemplate.build().jobId(flinkJobId).getJobStatus();
    }

    @GetMapping("/getJobLog")
    public AcornResponse getJobLog(@RequestParam String flinkJobId) {

        return acornTemplate.build().jobId(flinkJobId).getJobExceptions();
    }

}
