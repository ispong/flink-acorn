package com.isxcode.acorn.common.service;

import com.alibaba.fastjson.JSON;
import com.isxcode.acorn.common.constant.UrlConstants;
import com.isxcode.acorn.common.pojo.dto.AcornResponse;
import com.isxcode.acorn.common.pojo.dto.ExecuteConfig;
import com.isxcode.oxygen.core.http.HttpUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class AcornTemplate {

    public AcornResponse executeFlink(String host, String port, String key, ExecuteConfig executeConfig) {

        try {
            String executeUrl = String.format(UrlConstants.EXECUTE_URL, host, port);
            return HttpUtils.doPost(executeUrl, executeConfig, AcornResponse.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
