package com.isxcode.acorn.api.pojo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class JobLogDto {

    @JsonProperty("root-exception")
    private String rootException;
}
