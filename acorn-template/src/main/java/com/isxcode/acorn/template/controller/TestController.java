package com.isxcode.acorn.template.controller;

import com.isxcode.acorn.common.menu.TemplateType;
import com.isxcode.acorn.common.pojo.model.AcornModel1;
import com.isxcode.acorn.common.response.AcornResponse;
import com.isxcode.acorn.common.template.AcornTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping
public class TestController {

    private final AcornTemplate acornTemplate;

    public TestController(AcornTemplate acornTemplate) {

        this.acornTemplate = acornTemplate;
    }

    @GetMapping("/execute")
    public void execute() {

        AcornModel1 acornModel1 = AcornModel1.builder()
            .jobName("ispong-test-flink-job")
            .executeId("1314520")
            .fromConnectorSql("CREATE TABLE from_table ( " +
                "  username STRING," +
                "  age INT " +
                ") WITH ( " +
                "  'scan.startup.mode'='latest-offset'," +
                "  'properties.group.id'='test-consumer-group'," +
                "  'connector'='kafka'," +
                "  'topic'='flink-topic'," +
                "  'properties.zookeeper.connect'='8.142.131.65:2181'," +
                "  'properties.bootstrap.servers'='8.142.131.65:9092'," +
                "  'format'='csv'," +
                "  'csv.ignore-parse-errors' = 'true')")
            .toConnectorSql("CREATE TABLE to_table ( " +
                "  username varchar(100)," +
                "  age int" +
                ") WITH (" +
                "  'connector'='jdbc'," +
                "  'url'='jdbc:mysql://8.142.131.65:30102/flink'," +
                "  'table-name'='flink_test_table'," +
                "  'driver'='com.mysql.cj.jdbc.Driver'," +
                "  'username'='root'," +
                "  'password'='flink2022')")
            .filterCode("fromData = fromData.select($(\"username\").as(\"username\"),$(\"age\").as(\"age\"));")
            .templateName(TemplateType.KAFKA_INPUT_MYSQL_OUTPUT)
            .build();

        log.info(acornTemplate.build().execute(acornModel1).toString());
    }

    @GetMapping("/getLog")
    public void getLog() {

        AcornResponse flinkLog = acornTemplate.build().getJobLog("1314520");
        log.info(flinkLog.toString());
    }

    @GetMapping("/getJobStatus")
    public void getJobStatus() {

        AcornResponse jobInfo = acornTemplate.build().getJobStatus("9d0694eb8f597e2547970d98dc05a2b3");
        log.info(jobInfo.toString());
    }

    @GetMapping("/queryJobStatus")
    public void queryJobStatus() {

        AcornResponse jobInfo = acornTemplate.build().queryJobStatus();
        log.info(jobInfo.toString());
    }

    @GetMapping("/stopJob")
    public void stop() {

        AcornResponse d = acornTemplate.build().stopJob("9d0694eb8f597e2547970d98dc05a2b3");
        log.info(d.toString());
    }
}
