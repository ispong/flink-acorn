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
    public void executeSql(@RequestBody DemoReq demoReq) {

        AcornResponse acornResponse = acornTemplate.build()
            .executeId(demoReq.getExecuteId())
            .name(demoReq.getName())
            .sql(demoReq.getSql())
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

    @PostMapping("/killJob")
    public void killJob(@RequestBody DemoReq demoReq) {

        AcornResponse acornResponse = acornTemplate.build()
            .jobId(demoReq.getJobId())
            .killJob();

        log.info("acornResponse {}", acornResponse.toString());
    }
}
