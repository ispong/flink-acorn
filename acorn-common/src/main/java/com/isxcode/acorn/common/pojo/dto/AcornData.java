package com.isxcode.acorn.common.pojo.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AcornData {

    private String jobId;

    private JobInfoDto jobInfo;

    private String jobLog;

    public AcornData() {
    }

    public AcornData(String jobId, JobInfoDto jobInfo, String jobLog) {
        this.jobId = jobId;
        this.jobInfo = jobInfo;
        this.jobLog = jobLog;
    }
}
