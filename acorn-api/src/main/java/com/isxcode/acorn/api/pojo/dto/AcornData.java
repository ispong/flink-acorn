package com.isxcode.acorn.api.pojo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.isxcode.acorn.api.pojo.flink.JobExceptions;
import com.isxcode.acorn.api.pojo.flink.JobStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 返回所有acorn需要返回的数据
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AcornData {

    private String flinkJobId;

    private JobStatusDto jobInfo;

    private String jobLog;

    private String deployLog;

    private List<JobStatusDto> jobInfoList;

    private String executeId;

    private String applicationId;

    private String finalStatus;

    private String yarnState;

    private JobStatus jobStatus;

    private JobExceptions jobExceptions;

    private List<String> yarnLogs;

    private String webInterfaceURL;
}
