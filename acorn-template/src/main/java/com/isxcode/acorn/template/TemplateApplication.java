package com.isxcode.acorn.template;

import com.isxcode.acorn.common.menu.DataFormat;
import com.isxcode.acorn.common.menu.FlinkColType;
import com.isxcode.acorn.common.pojo.dto.AcornResponse;
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
        kafkaInputColumns.add(new FlinkCol("username", FlinkColType.STRING));
        kafkaInputColumns.add(new FlinkCol("age", FlinkColType.INT));

        KafkaInput kafkaInput = KafkaInput.builder()
                .brokerList("39.103.230.188:30120")
                .zookeeper("39.103.230.188:30121")
                .topic("ispong_kafka")
                .dataFormat(DataFormat.CSV)
                .columnList(kafkaInputColumns)
                .build();

        // 输出点
        List<FlinkCol> mysqlOutputColumns = new ArrayList<>();
        mysqlOutputColumns.add(new FlinkCol("username", FlinkColType.STRING));
        mysqlOutputColumns.add(new FlinkCol("age", FlinkColType.INT));

        MysqlOutput mysqlOutput = MysqlOutput.builder()
                .url("jdbc:mysql://47.103.203.73:3306/VATtest")
                .tableName("ispong_table")
                .driver("com.mysql.cj.jdbc.Driver")
                .username("admin")
                .password("gsw921226")
                .columnList(mysqlOutputColumns)
                .build();

        // 构建请求对象


        // 运行
        return acornTemplate.build("39.103.230.188", 30155, "key").execute(null);
    }
}
