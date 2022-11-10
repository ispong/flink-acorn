package com.isxcode.acorn.common.pojo.dto;

import com.isxcode.acorn.common.pojo.flink.JobExceptions;
import com.isxcode.acorn.common.pojo.flink.JobStatus;
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

    private String yarnLog;
}
