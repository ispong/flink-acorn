package com.isxcode.acorn.common.pojo.dto;

import lombok.Data;

/**
 * flink 作业状态信息
 */
@Data
public class JobStatusDto {

    private String jid;

    private String name;

    private String state;

    private String duration;
}
