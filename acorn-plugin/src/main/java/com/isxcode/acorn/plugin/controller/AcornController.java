package com.isxcode.acorn.plugin.controller;

import com.isxcode.acorn.common.pojo.dto.AcornResponse;
import com.isxcode.acorn.common.pojo.dto.ExecuteConfig;
import com.isxcode.acorn.common.pojo.req.AcornModel1;
import com.isxcode.acorn.common.pojo.req.AcornRequest;
import com.isxcode.acorn.plugin.service.AcornBizService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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
    public AcornResponse executeFlink(@RequestBody AcornModel1 acornRequest) {

        return acornBizService.execute(acornRequest);
    }

}