package com.isxcode.acorn.api.pojo.dto;

import lombok.Data;

import java.util.List;

/**
 * 查询flink作业状态的返回对象
 */
@Data
public class JobStatusResultDto {

    private List<JobStatusDto> jobs;
}
