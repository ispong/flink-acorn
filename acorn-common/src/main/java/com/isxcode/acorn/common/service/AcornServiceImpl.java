package com.isxcode.acorn.common.service;

import com.isxcode.acorn.common.constant.SecurityConstants;
import com.isxcode.acorn.common.constant.UrlConstants;
import com.isxcode.acorn.common.pojo.dto.AcornResponse;
import com.isxcode.acorn.common.pojo.req.AcornRequest;
import com.isxcode.acorn.common.properties.AcornNode;
import com.isxcode.oxygen.core.http.HttpUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AcornServiceImpl implements AcornService{

    private final AcornNode acornNode;

    public AcornServiceImpl(AcornNode acornNode) {

        this.acornNode = acornNode;
    }

    public AcornServiceImpl(String host, int port, String key) {
        AcornNode acornNode = new AcornNode();
        acornNode.setHost(host);
        acornNode.setPort(port);
        acornNode.setKey(key);
        this.acornNode = acornNode;
    }

    public AcornResponse execute(AcornRequest acornRequest) {

        try {
            String executeUrl = String.format(UrlConstants.EXECUTE_URL, acornNode.getHost(), acornNode.getPort());
            Map<String, String> headers = new HashMap<>();
            headers.put(SecurityConstants.HEADER_AUTHORIZATION, acornNode.getKey());
            return HttpUtils.doPost(executeUrl, headers, acornRequest, AcornResponse.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

}
