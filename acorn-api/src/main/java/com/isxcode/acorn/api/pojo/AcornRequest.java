package com.isxcode.acorn.api.pojo;

import com.isxcode.acorn.api.menu.Template;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * acorn 请求体
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AcornRequest {

    /**
     * flink组件中的jobId
     */
    private String jobId;

    /**
     * 执行flink作业的唯一标识，执行id executeId
     */
    private String executeId;

    /**
     * flink组件中作业的名称,在flink组件中jobName可以重复
     */
    private String name;

    /*
     * 支持通过json的方式发布flink作业
     *
     * @ispong
     */
    private String json;

    /*
     * 支持手写java代码的方式发布flink作业
     *
     * @ispong
     */
    private String java;

    private String sql;

    private Template template;

    private int masterMemoryMB = 1024;

    private int taskManagerMemoryMB = 1024;

    private int slotsPerTaskManager = 1;

    private String pluginName;

    private String pluginMainClass;

    private List<String> pluginArguments;

    private String applicationId;
}
