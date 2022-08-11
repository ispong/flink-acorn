package com.isxcode.acorn.template.controller;

import com.isxcode.acorn.common.pojo.AcornResponse;
import com.isxcode.acorn.common.template.AcornTemplate;
import com.isxcode.acorn.template.pojo.DemoReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor
public class TemplateController {

    private final AcornTemplate acornTemplate;

    @GetMapping("/executeSql")
    public void executeSql() {

        String sql = " " +
            "  CREATE TABLE from_table ( " +
            "       username STRING, " +
            "       age INT" +
            "  ) WITH (" +
            "       'scan.startup.mode'='latest-offset'," +
            "       'properties.group.id'='test-consumer-group'," +
            "       'connector'='kafka'," +
            "       'topic'='acorn-input'," +
            "       'properties.zookeeper.connect'='127.0.0.1:2181'," +
            "       'properties.bootstrap.servers'='127.0.0.1:9092'," +
            "       'format'='csv'," +
            "       'csv.ignore-parse-errors' = 'true'" +
            "  ); " +
            "  CREATE TABLE to_table ( " +
            "        username STRING, " +
            "        age INT" +
            "  ) WITH (" +
            "       'scan.startup.mode'='latest-offset'," +
            "       'properties.group.id'='test-consumer-group'," +
            "       'connector'='kafka'," +
            "       'topic'='acorn-output'," +
            "       'properties.zookeeper.connect'='127.0.0.1:2181'," +
            "       'properties.bootstrap.servers'='127.0.0.1:9092'," +
            "       'format'='csv'," +
            "       'csv.ignore-parse-errors' = 'true'" +
            "  ); " +
            "  INSERT INTO to_table SELECT username,age FROM from_table WHERE age > 18;";

        AcornResponse acornResponse = acornTemplate.build()
            .executeId("custom_execute_id")
            .name("test_flink")
            .sql(sql)
            .execute();

        log.info("acornResponse {}", acornResponse.toString());
    }

    @GetMapping("/getDeployLog")
    public void getExecuteLog() {

        AcornResponse acornResponse = acornTemplate.build()
            .executeId("custom_execute_id")
            .getDeployLog();

        log.info("acornResponse {}", acornResponse.toString());
    }

    @GetMapping("/getJobId")
    public void getJobId() {

        AcornResponse acornResponse = acornTemplate.build()
            .executeId("custom_execute_id")
            .getJobId();

        log.info("acornResponse {}", acornResponse.toString());
    }

    @GetMapping("/getJobStatus")
    public void getJobStatus() {

        AcornResponse acornResponse = acornTemplate.build()
            .jobId("f453055bf7f6bc8e56eef06ca7a8b633")
            .getJobStatus();

        log.info("acornResponse {}", acornResponse.toString());
    }

    @GetMapping("/getJobLog")
    public void getJobLog() {

        AcornResponse acornResponse = acornTemplate.build()
            .jobId("f453055bf7f6bc8e56eef06ca7a8b633")
            .getJobExceptions();

        log.info("acornResponse {}", acornResponse.toString());
    }

    @GetMapping("/stopJob")
    public void stopJob() {

        AcornResponse acornResponse = acornTemplate.build()
            .jobId("f453055bf7f6bc8e56eef06ca7a8b633")
            .stopJob();

        log.info("acornResponse {}", acornResponse.toString());
    }
}
