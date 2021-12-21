package com.isxcode.acorn.common.template;

import com.isxcode.acorn.common.constant.SecurityConstants;
import com.isxcode.acorn.common.constant.UrlConstants;
import com.isxcode.acorn.common.pojo.dto.AcornResponse;
import com.isxcode.acorn.common.pojo.model.AcornModel;
import com.isxcode.acorn.common.pojo.model.AcornModel1;
import com.isxcode.acorn.common.properties.AcornNodeProperties;
import com.isxcode.oxygen.core.http.HttpUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class AcornTemplate {

    private final AcornNodeProperties acornNodeProperties;

    public AcornTemplate(AcornNodeProperties acornNodeProperties) {

        this.acornNodeProperties = acornNodeProperties;
    }

    public AcornTemplate.Builder build() {

        return new AcornTemplate.Builder(acornNodeProperties);
    }

    public AcornTemplate.Builder build(String host, int port, String key) {

        return new AcornTemplate.Builder(host, port, key);
    }

    public class Builder {

        private final AcornNodeProperties acornNodeProperties;

        public Builder(AcornNodeProperties acornNodeProperties) {

            this.acornNodeProperties = acornNodeProperties;
        }

        public Builder(String host, int port, String key) {

            AcornNodeProperties acornNodeProperties = new AcornNodeProperties();
            acornNodeProperties.setHost(host);
            acornNodeProperties.setPort(port);
            acornNodeProperties.setKey(key);
            this.acornNodeProperties = acornNodeProperties;
        }

        public AcornResponse execute(AcornModel1 acornModel1) {

            try {
                String executeUrl = String.format(UrlConstants.EXECUTE_URL, acornNodeProperties.getHost(), acornNodeProperties.getPort());
                Map<String, String> headers = new HashMap<>();
                headers.put(SecurityConstants.HEADER_AUTHORIZATION, acornNodeProperties.getKey());
                return HttpUtils.doPost(executeUrl, headers, acornModel1, AcornResponse.class);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        public AcornResponse getLog(String executeId) {

            String executeUrl = String.format(UrlConstants.GET_FLINK_LOG_URL + "?executeId=" + executeId, acornNodeProperties.getHost(), acornNodeProperties.getPort());
            return HttpUtils.doGet(executeUrl, AcornResponse.class);
        }

        public AcornResponse stopJob(String jobId) {

            String executeUrl = String.format(UrlConstants.STOP_FLINK_URL + "?jobId=" + jobId, acornNodeProperties.getHost(), acornNodeProperties.getPort());
            return HttpUtils.doGet(executeUrl, AcornResponse.class);
        }

        public AcornResponse getJobInfo(String jobId) {

            String executeUrl = String.format(UrlConstants.GET_FLINK_URL + "?jobId=" + jobId, acornNodeProperties.getHost(), acornNodeProperties.getPort());
            return HttpUtils.doGet(executeUrl, AcornResponse.class);
        }
    }

}
