package com.isxcode.acorn.api.pojo.flink;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class JobExceptionInfo {

    @JsonAlias("all-exceptions")
    private String exception;
}
