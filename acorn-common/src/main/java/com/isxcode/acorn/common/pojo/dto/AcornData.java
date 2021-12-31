package com.isxcode.acorn.common.pojo.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 返回所有acorn需要返回的数据
 */
@Data
@Builder
public class AcornData {

    private String jobId;

    private JobStatusDto jobInfo;

    private String jobLog;

    private List<JobStatusDto> jobInfoList;

    public AcornData() {
    }

    public AcornData(String jobId, JobStatusDto jobInfo, String jobLog, List<JobStatusDto> jobInfoList) {
        this.jobId = jobId;
        this.jobInfo = jobInfo;
        this.jobLog = jobLog;
        this.jobInfoList = jobInfoList;
    }
}
