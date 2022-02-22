package com.isxcode.acorn.common.pojo.node;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class JobConfig {

    private String jobName;

    private List<String> flinkSqlList;
}
