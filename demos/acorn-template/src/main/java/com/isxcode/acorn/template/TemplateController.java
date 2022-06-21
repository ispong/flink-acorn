package com.isxcode.acorn.template;

import com.isxcode.acorn.common.pojo.AcornRequest;
import com.isxcode.acorn.common.pojo.AcornResponse;
import com.isxcode.acorn.common.template.AcornTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor
public class TemplateController {

    private final AcornTemplate acornTemplate;

    @GetMapping("/executeSql")
    public void executeSql() {

        List<String> sqlList = new ArrayList<>();
        sqlList.add(0, " CREATE TABLE from_table ( " +
            "       username STRING, " +
            "       age INT" +
            "    ) WITH (" +
            "       'scan.startup.mode'='latest-offset'," +
            "       'properties.group.id'='test-consumer-group'," +
            "       'connector'='kafka'," +
            "       'topic'='acorn-topic'," +
            "       'properties.zookeeper.connect'='localhost:2181'," +
            "       'properties.bootstrap.servers'='172.26.34.172:9092'," +
            "       'format'='csv'," +
            "       'csv.ignore-parse-errors' = 'true'" +
            " )");
        sqlList.add(1, "   CREATE TABLE to_table ( " +
            "        username STRING, " +
            "        age INT" +
            "     ) WITH (" +
            "        'connector'='jdbc','url'='jdbc:mysql://localhost:30102/acorn'," +
            "        'table-name'='flink_test_table'," +
            "        'driver'='com.mysql.cj.jdbc.Driver'," +
            "        'username'='root'," +
            "        'password'='acorn'" +
            "  )");
        sqlList.add(2, "   INSERT INTO to_table SELECT username,age FROM from_table WHERE age >19");

        AcornRequest acornRequest = AcornRequest.builder()
            .executeId(String.valueOf(UUID.randomUUID()))
            .sqlList(sqlList)
            .build();

        log.info(acornTemplate.build("inner").executeSql(acornRequest).toString());
    }

    @GetMapping("/executeJava")
    public void executeJava() {

        String javaCode = "" +
            "import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;\n" +
            "import org.apache.flink.table.api.EnvironmentSettings;\n" +
            "import org.apache.flink.table.api.Table;\n" +
            "import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;\n" +
            "\n" +
            "public class Acorn {\n" +
            "\n" +
            "    public static void main(String[] args) {\n" +
            "\n" +
            "        StreamExecutionEnvironment bsEnv = StreamExecutionEnvironment.getExecutionEnvironment();\n" +
            "        bsEnv.enableCheckpointing(5000);\n" +
            "        EnvironmentSettings settings = EnvironmentSettings.newInstance().useBlinkPlanner().inStreamingMode().build();\n" +
            "        StreamTableEnvironment tEnv = StreamTableEnvironment.create(bsEnv,settings);\n" +
            "        tEnv.getConfig().getConfiguration().setString(\"pipeline.name\", \"acorn-test\");\n" +
            "\n" +
            "        tEnv.executeSql(\"CREATE TABLE from_table ( username STRING, age INT) WITH ('scan.startup.mode'='latest-offset','properties.group.id'='test-consumer-group','connector'='kafka','topic'='flink-topic','properties.zookeeper.connect'='172.26.34.170:2181','properties.bootstrap.servers'='172.26.34.170:9092','format'='csv','csv.ignore-parse-errors' = 'true')\");\n" +
            "        tEnv.executeSql(\"CREATE TABLE to_table ( username STRING, age INT) WITH ('connector'='jdbc','url'='jdbc:mysql://172.26.34.170:30102/flink','table-name'='flink_test_table','driver'='com.mysql.cj.jdbc.Driver','username'='root','password'='flink2022')\");\n" +
            "        tEnv.executeSql(\"INSERT INTO to_table SELECT username,age FROM from_table WHERE age >19\");\n" +
            "    }\n" +
            "}\n";

        AcornRequest acornRequest = AcornRequest.builder()
            .executeId(String.valueOf(UUID.randomUUID()))
            .java(javaCode)
            .build();

        log.info(acornTemplate.build("inner").executeSql(acornRequest).toString());
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
