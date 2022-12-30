package com.isxcode.acorn.server.controller;

import com.isxcode.acorn.api.constant.URLs;
import com.isxcode.acorn.api.exception.AcornException;
import com.isxcode.acorn.api.pojo.AcornRequest;
import com.isxcode.acorn.api.pojo.dto.AcornData;
import com.isxcode.acorn.common.response.SuccessResponse;
import com.isxcode.acorn.server.service.AcornBizService;
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

    @SuccessResponse("部署成功")
    @PostMapping(URLs.EXECUTE_SQL_URL)
    public AcornData executeFlinkSql(@RequestBody AcornRequest acornRequest) {

        return acornBizService.executeSql(acornRequest);
    }

    @SuccessResponse("获取yarn作业状态成功")
    @PostMapping(URLs.GET_YARN_STATUS_URL)
    public AcornData getYarnStatus(@RequestBody AcornRequest acornRequest) {

        try {
            return acornBizService.getYarnStatus(acornRequest);
        } catch (IOException | YarnException e) {
            log.error(e.getMessage());
            throw new AcornException("执行异常", "50001");
        }
    }

    @SuccessResponse("获取JobManager日志成功")
    @PostMapping(URLs.GET_JOB_MANAGER_LOG_URL)
    public AcornData getJobManagerLog(@RequestBody AcornRequest acornRequest) {

        return acornBizService.getJobManagerLog(acornRequest);
    }

    @SuccessResponse("获取TaskManager日志成功")
    @PostMapping(URLs.GET_TASK_MANAGER_LOG_URL)
    public AcornData getTaskManagerLog(@RequestBody AcornRequest acornRequest) {

        return acornBizService.getTaskManagerLog(acornRequest);
    }

    @SuccessResponse("获取flink作业异常日志")
    @PostMapping(URLs.GET_JOB_EXCEPTIONS_URL)
    public AcornData getJobExceptions(@RequestBody AcornRequest acornRequest) {

        return acornBizService.getJobExceptions(acornRequest);
    }

    @SuccessResponse("停止作业")
    @PostMapping(URLs.STOP_JOB_URL)
    public AcornData stopJob(@RequestBody AcornRequest acornRequest) {

        try {
            return acornBizService.killYarn(acornRequest);
        } catch (IOException | YarnException e) {
            log.error(e.getMessage());
            throw new AcornException("执行异常", "50001");
        }
    }

    @SuccessResponse("获取flink作业状态")
    @PostMapping(URLs.GET_JOB_STATUS_URL)
    public AcornData getJobStatus(@RequestBody AcornRequest acornRequest) {

        try {
            return acornBizService.getJobStatus(acornRequest);
        } catch (IOException | YarnException e) {
            log.error(e.getMessage());
            throw new AcornException("执行异常", "50001");
        }
    }

    @SuccessResponse(msg = "心跳检测成功")
    @GetMapping(URLs.HEART_CHECK_URL)
    public AcornData heartCheck() {

        return AcornData.builder().jobLog("正常").build();
    }

}
