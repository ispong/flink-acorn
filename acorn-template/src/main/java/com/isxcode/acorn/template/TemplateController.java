package com.isxcode.acorn.template;

import com.isxcode.acorn.common.pojo.AcornRequest;
import com.isxcode.acorn.common.pojo.AcornResponse;
import com.isxcode.acorn.common.pojo.node.JobConfig;
import com.isxcode.acorn.common.template.AcornTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor
public class TemplateController {

    private final AcornTemplate acornTemplate;

    @GetMapping("/executeJson")
    public void executeJson() {

        String jsonConfig = "{\n" +
            "  \"jobName\": \"acorn-test\",\n" +
            "  \"flinkSqlList\": [\n" +
            "      \"CREATE TABLE from_table ( username STRING, age INT) WITH ('scan.startup.mode'='latest-offset','properties.group.id'='test-consumer-group','connector'='kafka','topic'='flink-topic','properties.zookeeper.connect'='172.26.34.170:2181','properties.bootstrap.servers'='172.26.34.170:9092','format'='csv','csv.ignore-parse-errors' = 'true'\",\n" +
            "      \"CREATE TABLE to_table ( username STRING, age INT) WITH ('connector'='jdbc','url'='jdbc:mysql://172.26.34.170:30102/flink','table-name'='flink_test_table','driver'='com.mysql.cj.jdbc.Driver','username'='root','password'='flink2022')\",\n" +
            "      \"INSERT INTO to_table SELECT username,age FROM from_table WHERE age >19\"\n" +
            "  ]\n" +
            "}";

        AcornRequest acornRequest = AcornRequest.builder()
            .executeId(String.valueOf(UUID.randomUUID()))
            .json(jsonConfig)
            .build();

        log.info(acornTemplate.build().executeJson(acornRequest).toString());
    }

    @GetMapping("/execute2")
    public void execute2() {

        AcornRequest acornRequest = AcornRequest.builder()
            .executeId("1314520")
            .java("String id='12'")
            .build();

        log.info(acornTemplate.build().executeJson(acornRequest).toString());
    }

    @GetMapping("/execute3")
    public void execute3() {

        AcornRequest acornRequest = AcornRequest.builder()
            .executeId("1314520")
            .json("{" +
                "\"user\":\"12\"" +
                "}")
            .build();

        log.info(acornTemplate.build().executeJson(acornRequest).toString());
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

        AcornResponse jobInfo = acornTemplate.build().stopJob("9d0694eb8f597e2547970d98dc05a2b3");
        log.info(jobInfo.toString());
    }

    @GetMapping("/killJob")
    public void kill() {

        AcornResponse jobInfo = acornTemplate.build().stopJob("9d0694eb8f597e2547970d98dc05a2b3");
        log.info(jobInfo.toString());
    }
}
