package com.isxcode.acorn.api.pojo.node;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobConfig {

    private String jobName;

    private List<String> flinkSqlList;
}
