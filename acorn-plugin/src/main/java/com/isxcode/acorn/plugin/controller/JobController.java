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

    /**
     * 发布flink作业接口
     */
    @SuccessResponse
    @PostMapping(UrlConstants.EXECUTE_JSON_URL)
    public AcornData executeFlink(@RequestBody AcornRequest acornRequest) {

        return acornBizService.executeJson(acornRequest);
    }

    /**
     * 获取flink作业发布日志接口
     */
    @SuccessResponse
    @PostMapping(UrlConstants.GET_JOB_LOG_URL)
    public AcornData getJobLog(@RequestBody AcornRequest acornRequest) {

        return acornBizService.getJobLog(acornRequest);
    }

    /**
     * 停止flink作业接口
     */
    @SuccessResponse
    @PostMapping(UrlConstants.STOP_JOB_URL)
    public AcornData stopJob(@RequestBody AcornRequest acornRequest) {

        return acornBizService.stopJob(acornRequest);
    }

    /**
     * 获取指定flink作业的状态
     */
    @SuccessResponse
    @PostMapping(UrlConstants.GET_JOB_STATUS_URL)
    public AcornData getJobStatus(@RequestBody AcornRequest acornRequest) {

        return acornBizService.getJobInfo(acornRequest);
    }

    /**
     * 查询所有flink作业的状态
     */
    @SuccessResponse
    @GetMapping(UrlConstants.QUERY_JOB_STATUS_URL)
    public AcornData queryJobStatus() {

        return acornBizService.queryJobStatus();
    }
}
