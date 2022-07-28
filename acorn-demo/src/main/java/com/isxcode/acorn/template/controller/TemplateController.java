package com.isxcode.acorn.template.controller;

import com.isxcode.acorn.common.pojo.AcornResponse;
import com.isxcode.acorn.common.template.AcornTemplate;
import com.isxcode.acorn.template.pojo.DemoReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor
public class TemplateController {

    private final AcornTemplate acornTemplate;

    @PostMapping("/executeSql")
    public String executeSql(@RequestBody DemoReq demoReq) {

        AcornResponse acornResponse = acornTemplate.build()
            .executeId(String.valueOf(UUID.randomUUID()))
            .name(demoReq.getName())
            .sql(demoReq.getSql())
            .execute();

        log.info("acornResponse {}", acornResponse.toString());
        return acornResponse.getAcornData().getExecuteId();
    }

    @PostMapping("/getExecuteLog")
    public String getExecuteLog(@RequestBody DemoReq demoReq) {

        AcornResponse acornResponse = acornTemplate.build()
            .executeId(demoReq.getExecuteId())
            .getExecuteLog();

        log.info("acornResponse {}", acornResponse.toString());
        return acornResponse.getAcornData().getExecuteLog();
    }

    @PostMapping("/getJobId")
    public String getJobId(@RequestBody DemoReq demoReq) {

        AcornResponse acornResponse = acornTemplate.build()
            .executeId(demoReq.getExecuteId())
            .getJobId();

        log.info("acornResponse {}", acornResponse.toString());
        return acornResponse.getAcornData().getJobId();
    }

   @PostMapping("/getJobStatus")
    public String getJobStatus(@RequestBody DemoReq demoReq) {

       AcornResponse acornResponse = acornTemplate.build()
           .jobId(demoReq.getJobId())
           .getJobStatus();

        log.info("acornResponse {}", acornResponse.toString());
        return acornResponse.getAcornData().getJobStatus();
    }

    @PostMapping("/getJobLog")
    public String getJobLog(@RequestBody DemoReq demoReq) {

        AcornResponse acornResponse = acornTemplate.build()
            .jobId(demoReq.getJobId())
            .getJobLog();

        log.info("acornResponse {}", acornResponse.toString());
        return acornResponse.getAcornData().getJobLog();
    }

    @PostMapping("/stopJob")
    public void stopJob(@RequestBody DemoReq demoReq) {

        AcornResponse acornResponse = acornTemplate.build()
            .jobId(demoReq.getJobId())
            .stopJob();

        log.info("acornResponse {}", acornResponse.toString());
    }

    @PostMapping("/killJob")
    public void killJob(@RequestBody DemoReq demoReq) {

        AcornResponse acornResponse = acornTemplate.build()
            .jobId(demoReq.getJobId())
            .killJob();

        log.info("acornResponse {}", acornResponse.toString());
    }
}
