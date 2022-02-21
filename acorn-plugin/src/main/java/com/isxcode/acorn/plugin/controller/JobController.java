package com.isxcode.acorn.plugin.controller;

import com.isxcode.acorn.common.constant.UrlConstants;
import com.isxcode.acorn.common.pojo.AcornRequest;
import com.isxcode.acorn.common.pojo.dto.AcornData;
import com.isxcode.acorn.plugin.response.SuccessResponse;
import com.isxcode.acorn.plugin.service.AcornBizService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class JobController {

    private final AcornBizService acornBizService;

    @SuccessResponse
    @PostMapping(UrlConstants.EXECUTE_JSON_URL)
    public AcornData executeFlink(@RequestBody AcornRequest acornRequest) {

        return acornBizService.executeJson(acornRequest);
    }

    @SuccessResponse
    @PostMapping(UrlConstants.GET_JOB_LOG_URL)
    public AcornData getJobLog(@RequestBody AcornRequest acornRequest) {

        return acornBizService.getJobLog(acornRequest);
    }

    @SuccessResponse
    @PostMapping(UrlConstants.STOP_JOB_URL)
    public AcornData stopJob(@RequestBody AcornRequest acornRequest) {

        return acornBizService.stopJob(acornRequest);
    }

    @SuccessResponse
    @PostMapping(UrlConstants.GET_JOB_STATUS_URL)
    public AcornData getJobStatus(@RequestBody AcornRequest acornRequest) {

        return acornBizService.getJobInfo(acornRequest);
    }

    @SuccessResponse
    @GetMapping(UrlConstants.QUERY_JOB_STATUS_URL)
    public AcornData queryJobStatus() {

        return acornBizService.queryJobStatus();
    }

    @SuccessResponse
    @GetMapping(UrlConstants.HEART_CHECK_URL)
    public AcornData heartCheck() {

        return AcornData.builder().jobLog("正常").build();
    }

}
