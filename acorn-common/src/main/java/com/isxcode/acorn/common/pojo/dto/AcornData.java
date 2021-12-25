package com.isxcode.acorn.common.pojo.dto;

import lombok.Builder;
import lombok.Data;

/**
 * 返回所有acorn需要返回的数据
 */
@Data
@Builder
public class AcornData {

    private String jobId;

    private JobStatusDto jobInfo;

    private String jobLog;

    public AcornData() {
    }

    public AcornData(String jobId, JobStatusDto jobInfo, String jobLog) {
        this.jobId = jobId;
        this.jobInfo = jobInfo;
        this.jobLog = jobLog;
    }
}
