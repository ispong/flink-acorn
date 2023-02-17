package com.isxcode.acorn.demo;

import com.isxcode.acorn.client.template.AcornTemplate;
import com.isxcode.acorn.api.pojo.AcornResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

@RequestMapping
@RestController
@SpringBootApplication
@RequiredArgsConstructor
public class ClientDemoApplication {

    private final AcornTemplate acornTemplate;

    public static void main(String[] args) {

        SpringApplication.run(ClientDemoApplication.class, args);
    }

    @PostMapping("/executeSql")
    public AcornResponse executeSql(@RequestBody AcornReq acornReq) {

        return acornTemplate.build().sql(acornReq.getFlinkSql()).deploy();
    }

    @PostMapping("/getData")
    public AcornResponse getData(@RequestBody AcornReq acornReq) {

        return acornTemplate.build().applicationId(acornReq.getApplicationId()).getData();
    }

    @PostMapping("/getYarnStatus")
    public AcornResponse getYarnStatus(@RequestBody AcornReq acornReq) {

        return acornTemplate.build().applicationId(acornReq.getApplicationId()).getYarnStatus();
    }

    @PostMapping("/getJobManagerLog")
    public AcornResponse getJobManagerLog(@RequestBody AcornReq acornReq) {

        return acornTemplate.build().applicationId(acornReq.getApplicationId()).getJobManagerLog();
    }

    @PostMapping("/getTaskManagerLog")
    public AcornResponse getTaskManagerLog(@RequestBody AcornReq acornReq) {

        return acornTemplate.build().applicationId(acornReq.getApplicationId()).getTaskManagerLog();
    }

    @PostMapping("/getJobStatus")
    public AcornResponse getJobStatus(@RequestBody AcornReq acornReq) {

        return acornTemplate.build().jobId(acornReq.getFlinkJobId()).getJobStatus();
    }

    @PostMapping("/getRootExceptions")
    public AcornResponse getRootExceptions(@RequestBody AcornReq acornReq) {

        return acornTemplate.build().jobId(acornReq.getFlinkJobId()).getRootExceptions();
    }

    @PostMapping("/stopYarnJob")
    public AcornResponse stopYarnJob(@RequestBody AcornReq acornReq) {

        return acornTemplate.build().applicationId(acornReq.getApplicationId()).stopJob();
    }
}
