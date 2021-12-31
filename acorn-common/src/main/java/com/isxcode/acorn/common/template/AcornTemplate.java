package com.isxcode.acorn.common.template;

import com.isxcode.acorn.common.constant.SecurityConstants;
import com.isxcode.acorn.common.constant.UrlConstants;
import com.isxcode.acorn.common.pojo.model.AcornModel1;
import com.isxcode.acorn.common.properties.AcornNodeProperties;
import com.isxcode.acorn.common.response.AcornResponse;
import com.isxcode.oxygen.core.http.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * acornTemplate 提供快捷方式访问
 */
@Service
@Slf4j
public class AcornTemplate {

    private final AcornNodeProperties acornNodeProperties;

    public AcornTemplate(AcornNodeProperties acornNodeProperties) {

        this.acornNodeProperties = acornNodeProperties;
    }

    public AcornTemplate.Builder build() {

        return new Builder(acornNodeProperties);
    }

    public AcornTemplate.Builder build(String host, int port, String key) {

        return new Builder(host, port, key);
    }

    public static class Builder {

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

        public AcornResponse requestAcornServer(String url, AcornModel1 acornModel1) {

            Map<String, String> headers = new HashMap<>();
            headers.put(SecurityConstants.HEADER_AUTHORIZATION, acornNodeProperties.getKey());

            try {
                return HttpUtils.doPost(url, headers, acornModel1, AcornResponse.class);
            } catch (IOException e) {
                return new AcornResponse("500", e.getMessage());
            }
        }

        public AcornResponse execute(AcornModel1 acornModel1) {

            String executeUrl = String.format(UrlConstants.BASE_URL + UrlConstants.EXECUTE_URL, acornNodeProperties.getHost(), acornNodeProperties.getPort());
            return requestAcornServer(executeUrl, acornModel1);
        }

        public AcornResponse getJobLog(String executeId) {

            AcornModel1 acornModel1 = AcornModel1.builder().executeId(executeId).build();
            String executeUrl = String.format(UrlConstants.BASE_URL + UrlConstants.GET_JOB_LOG_URL, acornNodeProperties.getHost(), acornNodeProperties.getPort());
            return requestAcornServer(executeUrl, acornModel1);
        }

        public AcornResponse stopJob(String jobId) {

            AcornModel1 acornModel1 = AcornModel1.builder().jobId(jobId).build();
            String executeUrl = String.format(UrlConstants.BASE_URL + UrlConstants.STOP_JOB_URL, acornNodeProperties.getHost(), acornNodeProperties.getPort());
            return requestAcornServer(executeUrl, acornModel1);
        }

        public AcornResponse getJobStatus(String jobId) {

            AcornModel1 acornModel1 = AcornModel1.builder().jobId(jobId).build();
            String executeUrl = String.format(UrlConstants.BASE_URL + UrlConstants.GET_JOB_STATUS_URL, acornNodeProperties.getHost(), acornNodeProperties.getPort());
            return requestAcornServer(executeUrl, acornModel1);
        }

        public AcornResponse queryJobStatus() {

            String executeUrl = String.format(UrlConstants.BASE_URL + UrlConstants.QUERY_JOB_STATUS_URL, acornNodeProperties.getHost(), acornNodeProperties.getPort());

            Map<String, String> headers = new HashMap<>();
            headers.put(SecurityConstants.HEADER_AUTHORIZATION, acornNodeProperties.getKey());

            return HttpUtils.doGet(executeUrl, headers, AcornResponse.class);
        }
    }

}
