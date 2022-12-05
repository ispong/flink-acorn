package com.isxcode.acorn.server.controller;

import com.isxcode.acorn.api.constant.UrlConstants;
import com.isxcode.acorn.api.exception.AcornException;
import com.isxcode.acorn.api.pojo.AcornRequest;
import com.isxcode.acorn.api.pojo.dto.AcornData;
import com.isxcode.acorn.server.service.AcornBizService;
import com.isxcode.oxygen.common.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.client.deployment.ClusterDeploymentException;
import org.apache.flink.client.program.ProgramInvocationException;
import org.apache.hadoop.yarn.exceptions.YarnException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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
    @PostMapping(UrlConstants.GET_YARN_STATUS_URL)
    public AcornData getYarnStatus(@RequestBody AcornRequest acornRequest) {

        try {
            return acornBizService.getYarnStatus(acornRequest);
        } catch (IOException | YarnException e) {
            log.error(e.getMessage());
            throw new AcornException("执行异常", "50001");
        }
    }

    @SuccessResponse
    @PostMapping(UrlConstants.GET_DEPLOY_LOG_URL)
    public AcornData getYarnLog(@RequestBody AcornRequest acornRequest) {

        return acornBizService.getYarnLog(acornRequest);
    }

    @SuccessResponse
    @PostMapping(UrlConstants.GET_JOB_EXCEPTIONS_URL)
    public AcornData getJobExceptions(@RequestBody AcornRequest acornRequest) {

        return acornBizService.getJobExceptions(acornRequest);
    }

    @SuccessResponse
    @PostMapping(UrlConstants.STOP_JOB_URL)
    public AcornData stopJob(@RequestBody AcornRequest acornRequest) {

        try {
            return acornBizService.killYarn(acornRequest);
        } catch (IOException | YarnException e) {
            log.error(e.getMessage());
            throw new AcornException("执行异常", "50001");
        }
    }

    @SuccessResponse
    @PostMapping(UrlConstants.GET_JOB_STATUS_URL)
    public AcornData getJobStatus(@RequestBody AcornRequest acornRequest) {

        try {
            return acornBizService.getJobStatus(acornRequest);
        } catch (IOException | YarnException e) {
            log.error(e.getMessage());
            throw new AcornException("执行异常", "50001");
        }
    }

    @SuccessResponse
    @GetMapping(UrlConstants.HEART_CHECK_URL)
    public AcornData heartCheck() {

        return AcornData.builder().jobLog("正常").build();
    }

}
