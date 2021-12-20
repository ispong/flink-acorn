package com.isxcode.acorn.common.pojo.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AcornData {

    private String jobId;

    public AcornData() {
    }

    public AcornData(String jobId) {
        this.jobId = jobId;
    }
}
