package com.isxcode.acorn.template;

import com.isxcode.acorn.common.menu.TemplateType;
import com.isxcode.acorn.common.pojo.dto.AcornResponse;
import com.isxcode.acorn.common.pojo.model.AcornModel1;
import com.isxcode.acorn.common.template.AcornTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    /**
     * 发布作业  返回jobId
     */
    @GetMapping("/execute")
    public String execute() {

        AcornModel1 acornModel1 = AcornModel1.builder()
            .jobName("job-name")
            .executeId("1314520")
            .fromConnectorSql("CREATE TABLE from_table ( ... ) WITH ( ... )")
            .toConnectorSql("CREATE TABLE to_table ( ... ) WITH ( ... )")
            .filterCode("Table fromData = fromData.select( ... )")
            .templateName(TemplateType.KAFKA_INPUT_KAFKA_OUTPUT)
            .build();

        AcornResponse acornResponse = acornTemplate.build().execute(acornModel1);
        return acornResponse.getAcornData().getJobId();
    }

    /**
     * 获取作业日志
     */
    @GetMapping("/getLog")
    public String getLog() {

        AcornResponse log = acornTemplate.build().getLog("1314520");
        return log.getAcornData().getJobLog();
    }

    /**
     * 获取作业运行状态
     */
    @GetMapping("/getJobStatus")
    public String getJobStatus() {

        AcornResponse jobInfo = acornTemplate.build().getJobInfo("jobId");
        return jobInfo.getAcornData().getJobInfo().getState();
    }

    /**
     * 停止作业
     */
    @GetMapping("/stopJob")
    public void stop() {

        acornTemplate.build().stopJob("jobId");
    }

}
