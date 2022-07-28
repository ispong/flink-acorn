package com.isxcode.acorn.common.pojo.dto;

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

    private String jobId;

    private JobStatusDto jobInfo;

    private String jobLog;

    private String deployLog;

    private List<JobStatusDto> jobInfoList;

    private String executeId;

    private String jobStatus;
}
