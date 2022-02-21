package com.isxcode.acorn.template.controller;

import com.isxcode.acorn.common.pojo.AcornRequest;
import com.isxcode.acorn.common.pojo.AcornResponse;
import com.isxcode.acorn.common.template.AcornTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor
public class TestController {

    private final AcornTemplate acornTemplate;

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

    @GetMapping("/execute1")
    public void execute1() {

        AcornRequest acornRequest = AcornRequest.builder()
            .executeId("1314520")
            .sql("select * from a")
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
}
