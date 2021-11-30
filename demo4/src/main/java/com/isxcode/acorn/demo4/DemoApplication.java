package com.isxcode.acorn.demo4;

import com.isxcode.acorn.demo4.pojo.FlinkReq;
import com.isxcode.acorn.demo4.pojo.dto.NodeInfo;
import com.isxcode.acorn.demo4.service.FlinkService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.web.bind.annotation.*;

@RequestMapping
@RestController
@SpringBootApplication(exclude = {MongoAutoConfiguration.class})
public class DemoApplication {

    private final FlinkService flinkService;

    public DemoApplication(FlinkService flinkService) {
        this.flinkService = flinkService;
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @GetMapping("/demo")
    public String demo() {

        return "hello";
    }

    /**
     * 添加节点
     */
    @PostMapping("/addNode")
    public NodeInfo addNode(@RequestBody FlinkReq flinkReq) {

        return flinkService.addNode(flinkReq);
    }

    /**
     * 添获取点
     */
    @PostMapping("/getNode")
    public NodeInfo getNode(@RequestBody FlinkReq flinkReq) {

        return flinkService.getNode(flinkReq.getNodeId());
    }

    /**
     * 更新节点
     */
    public void updateNode() {

    }

    /**
     * 配置节点的工作流
     */
    public void configNodeFlow(){

    }

    /**
     * 执行工作流
     */
    @GetMapping("/execute")
    public void executeFlink(@RequestParam String flowId, @RequestParam String executeId) {

        flinkService.executeFlink(flowId, executeId);
    }


    public void getFlinkLog(){


    }

    public void getFlinkRunningLog(){

    }
}