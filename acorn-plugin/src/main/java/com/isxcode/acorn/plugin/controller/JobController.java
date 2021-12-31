package com.isxcode.acorn.plugin.controller;

import com.isxcode.acorn.common.constant.UrlConstants;
import com.isxcode.acorn.common.pojo.dto.AcornData;
import com.isxcode.acorn.common.pojo.model.AcornModel1;
import com.isxcode.acorn.plugin.response.SuccessResponse;
import com.isxcode.acorn.plugin.service.AcornBizService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class JobController {

    private final AcornBizService acornBizService;

    public JobController(AcornBizService acornBizService) {

        this.acornBizService = acornBizService;
    }

    /**
     * 发布flink作业接口
     */
    @SuccessResponse
    @PostMapping(UrlConstants.EXECUTE_URL)
    public AcornData executeFlink(@RequestBody AcornModel1 acornModel) {

        return acornBizService.execute(acornModel);
    }

    /**
     * 获取flink作业发布日志接口
     */
    @SuccessResponse
    @PostMapping(UrlConstants.GET_JOB_LOG_URL)
    public AcornData getJobLog(@RequestBody AcornModel1 acornModel) {

        return acornBizService.getJobLog(acornModel);
    }

    /**
     * 停止flink作业接口
     */
    @SuccessResponse
    @PostMapping(UrlConstants.STOP_JOB_URL)
    public AcornData stopJob(@RequestBody AcornModel1 acornModel) {

        return acornBizService.stopJob(acornModel);
    }

    /**
     * 获取指定flink作业的状态
     */
    @SuccessResponse
    @PostMapping(UrlConstants.GET_JOB_STATUS_URL)
    public AcornData getJobStatus(@RequestBody AcornModel1 acornModel) {

        return acornBizService.getJobInfo(acornModel);
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
