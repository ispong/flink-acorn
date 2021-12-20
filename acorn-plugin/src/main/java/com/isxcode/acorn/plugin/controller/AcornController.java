package com.isxcode.acorn.plugin.controller;

import com.isxcode.acorn.common.pojo.dto.AcornResponse;
import com.isxcode.acorn.common.pojo.model.AcornModel;
import com.isxcode.acorn.common.pojo.model.AcornModel1;
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

        return acornBizService.execute(acornModel);
    }
//
//    /**
//     * 获取实时作业运行状态
//     *
//     */
//    @GetMapping("/getJobStatus")
//    public AcornResponse getJobStatus(@RequestBody String jobName) {
//
//        return acornBizService.getJobStatus(jobName);
//    }
//
//
//    /**
//     * 停止实时作业
//     *
//     */
//    @GetMapping("/stopJob")
//    public AcornResponse stopJob(@RequestBody String jobName) {
//
//        return acornBizService.stopJob(acornModel);
//    }

}
