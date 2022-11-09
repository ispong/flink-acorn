package com.isxcode.acorn.client.controller;

import com.isxcode.acorn.client.response.SuccessResponse;
import com.isxcode.acorn.client.service.AcornBizService;
import com.isxcode.acorn.common.constant.UrlConstants;
import com.isxcode.acorn.common.exception.AcornException;
import com.isxcode.acorn.common.pojo.AcornRequest;
import com.isxcode.acorn.common.pojo.dto.AcornData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.client.deployment.ClusterDeploymentException;
import org.apache.flink.client.program.ProgramInvocationException;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;

@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor
public class JobController {

    private final AcornBizService acornBizService;

    @SuccessResponse
    @PostMapping(UrlConstants.EXECUTE_SQL_URL)
    public AcornData executeFlinkSql(@RequestBody AcornRequest acornRequest) {

        try {
            return acornBizService.executeSql(acornRequest);
        } catch (MalformedURLException | ProgramInvocationException | ClusterDeploymentException e) {
            log.error(e.getMessage());
            throw new AcornException("执行异常", "50001");
        }
    }

    @SuccessResponse
    @PostMapping(UrlConstants.GET_DEPLOY_LOG_URL)
    public AcornData getDeployLog(@RequestBody AcornRequest acornRequest) {

        return acornBizService.getDeployLog(acornRequest);
    }

    @SuccessResponse
    @PostMapping(UrlConstants.GET_JOB_EXCEPTIONS_URL)
    public AcornData getJobExceptions(@RequestBody AcornRequest acornRequest) {

        return acornBizService.getJobExceptions(acornRequest);
    }

    @SuccessResponse
    @PostMapping(UrlConstants.GET_JOB_ID_URL)
    public AcornData getJobId(@RequestBody AcornRequest acornRequest) {

        return acornBizService.getJobId(acornRequest);
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
