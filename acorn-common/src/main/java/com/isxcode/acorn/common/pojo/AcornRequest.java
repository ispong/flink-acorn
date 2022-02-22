package com.isxcode.acorn.common.pojo;

import com.isxcode.acorn.common.pojo.node.JobConfig;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * acorn 请求体
 */
@Data
@Builder
public class AcornRequest {

    /**
     * flink组件中的jobId
     */
    private String jobId;

    /**
     * 执行flink作业的唯一标识，执行id
     */
    private String executeId;

    /**
     * flink组件中作业的名称,在flink组件中jobName可以重复
     */
    private String jobName;

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

    /*
     * 支持手写sql的方式发布flink作业
     *
     * @ispong
     */
    private String sql;

}
