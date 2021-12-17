package com.isxcode.acorn.template;

import com.isxcode.acorn.common.menu.DataFormat;
import com.isxcode.acorn.common.menu.FlinkSqlType;
import com.isxcode.acorn.common.menu.WorkType;
import com.isxcode.acorn.common.pojo.dto.AcornResponse;
import com.isxcode.acorn.common.pojo.dto.ExecuteConfig;
import com.isxcode.acorn.common.pojo.dto.FlinkCol;
import com.isxcode.acorn.common.pojo.node.KafkaInput;
import com.isxcode.acorn.common.pojo.node.MysqlOutput;
import com.isxcode.acorn.common.template.AcornTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping
@SpringBootApplication
public class TemplateApplication {

    private final AcornTemplate acornTemplate;

    public TemplateApplication(AcornTemplate acornTemplate) {
        this.acornTemplate = acornTemplate;
    }

    public static void main(String[] args) {

        SpringApplication.run(TemplateApplication.class, args);
    }

    @GetMapping("/demo")
    public AcornResponse testExecuteFlink() {

        // 输入点
        List<FlinkCol> kafkaInputColumns = new ArrayList<>();
        kafkaInputColumns.add(new FlinkCol("username", FlinkSqlType.STRING));
        kafkaInputColumns.add(new FlinkCol("age", FlinkSqlType.INT));

        KafkaInput kafkaInput = KafkaInput.builder()
                .brokerList("39.103.230.188:30120")
                .zookeeper("39.103.230.188:30121")
                .topic("ispong_kafka")
                .dataFormat(DataFormat.CSV)
                .columnList(kafkaInputColumns)
                .build();

        // 输出点
        List<FlinkCol> mysqlOutputColumns = new ArrayList<>();
        mysqlOutputColumns.add(new FlinkCol("username", FlinkSqlType.STRING));
        mysqlOutputColumns.add(new FlinkCol("age", FlinkSqlType.INT));

        MysqlOutput mysqlOutput = MysqlOutput.builder()
                .url("jdbc:mysql://47.103.203.73:3306/VATtest")
                .tableName("ispong_table")
                .driver("com.mysql.cj.jdbc.Driver")
                .username("admin")
                .password("gsw921226")
                .columnList(mysqlOutputColumns)
                .build();

        // 构建请求对象
        ExecuteConfig executeConfig = ExecuteConfig.builder()
                .executeId("executeId123")
                .flowId("flowId123")
                .workType(WorkType.KAFKA_INPUT_MYSQL_OUTPUT)
                .kafkaInput(kafkaInput)
                .mysqlOutput(mysqlOutput)
                .build();

        // 运行
        return acornTemplate.executeFlink("39.103.230.188", "30155", "key", executeConfig);
    }
}