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

    @PostMapping("/getDeployLog")
    public void getExecuteLog(@RequestBody DemoReq demoReq) {

        AcornResponse acornResponse = acornTemplate.build()
            .executeId(demoReq.getExecuteId())
            .getDeployLog();

        log.info("acornResponse {}", acornResponse.toString());
    }

    @PostMapping("/getJobId")
    public void getJobId(@RequestBody DemoReq demoReq) {

        AcornResponse acornResponse = acornTemplate.build()
            .executeId(demoReq.getExecuteId())
            .getJobId();

        log.info("acornResponse {}", acornResponse.toString());
    }

    @PostMapping("/getJobStatus")
    public void getJobStatus(@RequestBody DemoReq demoReq) {

        AcornResponse acornResponse = acornTemplate.build()
            .jobId(demoReq.getJobId())
            .getJobStatus();

        log.info("acornResponse {}", acornResponse.toString());
    }

    @PostMapping("/getJobLog")
    public void getJobLog(@RequestBody DemoReq demoReq) {

        AcornResponse acornResponse = acornTemplate.build()
            .jobId(demoReq.getJobId())
            .getJobLog();

        log.info("acornResponse {}", acornResponse.toString());
    }

    @PostMapping("/stopJob")
    public void stopJob(@RequestBody DemoReq demoReq) {

        AcornResponse acornResponse = acornTemplate.build()
            .jobId(demoReq.getJobId())
            .stopJob();

        log.info("acornResponse {}", acornResponse.toString());
    }
}
