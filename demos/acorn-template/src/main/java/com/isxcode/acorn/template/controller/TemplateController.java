package com.isxcode.acorn.template.controller;

import com.isxcode.acorn.common.pojo.AcornResponse;
import com.isxcode.acorn.common.template.AcornTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor
public class TemplateController {

    private final AcornTemplate acornTemplate;

    @GetMapping("/executeSql")
    public String executeSql(@RequestParam String sql) {

        AcornResponse acornResponse = acornTemplate.build()
            .executeId(String.valueOf(UUID.randomUUID()))
            .name("ispong_job")
            .sql(sql)
            .execute();

        log.info("acornResponse {}", acornResponse.toString());
        return acornResponse.getAcornData().getExecuteId();
    }

    @GetMapping("/getExecuteLog")
    public String getExecuteLog(@RequestParam String executeId) {

        AcornResponse acornResponse = acornTemplate.build()
            .executeId(executeId)
            .getExecuteLog();

        log.info("acornResponse {}", acornResponse.toString());
        return acornResponse.getAcornData().getExecuteLog();
    }

    @GetMapping("/getJobId")
    public String getJobId(@RequestParam String executeId) {

        AcornResponse acornResponse = acornTemplate.build()
            .executeId(executeId)
            .getJobId();

        log.info("acornResponse {}", acornResponse.toString());
        return acornResponse.getAcornData().getJobId();
    }

    @GetMapping("/getJobStatus")
    public String getJobStatus(@RequestParam String jobId) {

        AcornResponse acornResponse = acornTemplate.build()
            .jobId(jobId)
            .getJobStatus();

        log.info("acornResponse {}", acornResponse.toString());
        return acornResponse.getAcornData().getJobStatus();
    }

    @GetMapping("/getJobLog")
    public String getJobLog(@RequestParam String jobId) {

        AcornResponse acornResponse = acornTemplate.build()
            .jobId(jobId)
            .getJobLog();

        log.info("acornResponse {}", acornResponse.toString());
        return acornResponse.getAcornData().getJobLog();
    }

    @GetMapping("/stopJob")
    public void stopJob(@RequestParam String jobId) {

        AcornResponse acornResponse = acornTemplate.build()
            .jobId(jobId).stopJob();

        log.info("acornResponse {}", acornResponse.toString());
    }

    @GetMapping("/killJob")
    public void killJob(@RequestParam String jobId) {

        AcornResponse acornResponse = acornTemplate.build()
            .jobId(jobId).killJob();

        log.info("acornResponse {}", acornResponse.toString());
    }
}
