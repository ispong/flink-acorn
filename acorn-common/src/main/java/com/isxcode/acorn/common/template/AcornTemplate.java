package com.isxcode.acorn.common.template;

import com.isxcode.acorn.common.constant.SecurityConstants;
import com.isxcode.acorn.common.constant.UrlConstants;
import com.isxcode.acorn.common.pojo.dto.AcornResponse;
import com.isxcode.acorn.common.pojo.request.AcornRequest;
import com.isxcode.acorn.common.properties.AcornNode;
import com.isxcode.acorn.common.properties.AcornProperties;
import com.isxcode.acorn.common.service.AcornServiceImpl;
import com.isxcode.oxygen.core.http.HttpUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class AcornTemplate {

    private final AcornProperties acornProperties;

    public AcornTemplate(AcornProperties acornProperties) {

        this.acornProperties = acornProperties;
    }

    public AcornServiceImpl builder() {

        return new AcornServiceImpl(acornProperties.getNode());
    }

    public AcornServiceImpl builder(String host, int port, String key) {

        return new AcornServiceImpl(host, port, key);
    }

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
