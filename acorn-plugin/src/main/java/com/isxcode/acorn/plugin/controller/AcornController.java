package com.isxcode.acorn.plugin.controller;

import com.isxcode.acorn.common.pojo.dto.AcornResponse;
import com.isxcode.acorn.common.pojo.model.AcornModel1;
import com.isxcode.acorn.plugin.exception.AcornException;
import com.isxcode.acorn.plugin.service.AcornBizService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("flink-acorn")
public class AcornController {

    private final AcornBizService acornBizService;

    public AcornController(AcornBizService acornBizService) {
        this.acornBizService = acornBizService;
    }

    /**
     * 执行flink作业
     *
     * @return code 错误码
     */
    @PostMapping("/execute")
    public AcornResponse executeFlink(@RequestBody AcornModel1 acornModel) {

        AcornResponse acornResponse = new AcornResponse();

        try {
            acornResponse.setCode("200");
            acornResponse.setMessage("发布成功");
            acornResponse.setAcornData(acornBizService.execute(acornModel));
        } catch (AcornException e) {
            acornResponse.setCode(e.getCode());
            acornResponse.setMessage(e.getMsg());
        }

        return acornResponse;
    }

    /**
     * 获取实时作业日志
     */
    @GetMapping("/getJobLog")
    public AcornResponse getJobLog(@RequestBody String executeId) {

        AcornResponse acornResponse = new AcornResponse();

        try {
            acornResponse.setCode("200");
            acornResponse.setMessage("查询日志成功");
            acornResponse.setAcornData(acornBizService.getJobLog(executeId));
        } catch (AcornException e) {
            acornResponse.setCode(e.getCode());
            acornResponse.setMessage(e.getMsg());
        }

        return acornResponse;
    }

    /**
     * 停止实时作业
     */
    @GetMapping("/stopJob")
    public AcornResponse stopJob(@RequestBody String jobId) {

        AcornResponse acornResponse = new AcornResponse();

        try {
            acornResponse.setCode("200");
            acornResponse.setMessage("停止实时作业成功");
            acornResponse.setAcornData(acornBizService.stopJob(jobId));
        } catch (AcornException e) {
            acornResponse.setCode(e.getCode());
            acornResponse.setMessage(e.getMsg());
        }

        return acornResponse;
    }

    /**
     * 获取实时作业运行状态
     */
    @GetMapping("/getJobInfo")
    public AcornResponse getJobInfo(@RequestBody String jobId) {

        AcornResponse acornResponse = new AcornResponse();

        try {
            acornResponse.setCode("200");
            acornResponse.setMessage("获取实时作业成功");
            acornResponse.setAcornData(acornBizService.getJobInfo(jobId));
        } catch (AcornException e) {
            acornResponse.setCode(e.getCode());
            acornResponse.setMessage(e.getMsg());
        }

        return acornResponse;
    }

}
