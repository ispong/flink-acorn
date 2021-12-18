package com.isxcode.acorn.common.template;

import com.isxcode.acorn.common.properties.AcornProperties;
import org.springframework.stereotype.Service;

@Service
public class AcornTemplate {

    private final AcornProperties acornProperties;

    public AcornTemplate(AcornProperties acornProperties) {

        this.acornProperties = acornProperties;
    }

//    public AcornServiceImpl builder() {
//
//        return new AcornServiceImpl(acornProperties.getNode());
//    }
//
//    public AcornServiceImpl builder(String host, int port, String key) {
//
//        return new AcornServiceImpl(host, port, key);
//    }
//
//    private final AcornNode acornNode;
//
//    public AcornServiceImpl(AcornNode acornNode) {
//
//        this.acornNode = acornNode;
//    }
//
//    public AcornServiceImpl(String host, int port, String key) {
//        AcornNode acornNode = new AcornNode();
//        acornNode.setHost(host);
//        acornNode.setPort(port);
//        acornNode.setKey(key);
//        this.acornNode = acornNode;
//    }
//
//    public AcornResponse execute(AcornRequest acornRequest) {
//
//        try {
//            String executeUrl = String.format(UrlConstants.EXECUTE_URL, acornNode.getHost(), acornNode.getPort());
//            Map<String, String> headers = new HashMap<>();
//            headers.put(SecurityConstants.HEADER_AUTHORIZATION, acornNode.getKey());
//            return HttpUtils.doPost(executeUrl, headers, acornRequest, AcornResponse.class);
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//
//    }
}
