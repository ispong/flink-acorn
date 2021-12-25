package com.isxcode.acorn.common.template;

import com.isxcode.acorn.common.constant.SecurityConstants;
import com.isxcode.acorn.common.constant.UrlConstants;
import com.isxcode.acorn.common.menu.ResponseEnum;
import com.isxcode.acorn.common.pojo.dto.AcornResponse;
import com.isxcode.acorn.common.pojo.model.AcornModel1;
import com.isxcode.acorn.common.properties.AcornNodeProperties;
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

        public AcornResponse execute(AcornModel1 acornModel1) {

            try {

                String executeUrl = String.format(UrlConstants.EXECUTE_URL, acornNodeProperties.getHost(), acornNodeProperties.getPort());

                Map<String, String> headers = new HashMap<>();
                headers.put(SecurityConstants.HEADER_AUTHORIZATION, acornNodeProperties.getKey());

                return HttpUtils.doPost(executeUrl, headers, acornModel1, AcornResponse.class);
            } catch (IOException e) {
                log.debug(e.getMessage());
                return new AcornResponse(ResponseEnum.REMOTE_ERROR);
            }
        }

        public AcornResponse getJobLog(String executeId) {

            String executeUrl = String.format(UrlConstants.GET_JOB_LOG_URL + executeId, acornNodeProperties.getHost(), acornNodeProperties.getPort());

            Map<String, String> headers = new HashMap<>();
            headers.put(SecurityConstants.HEADER_AUTHORIZATION, acornNodeProperties.getKey());

            return HttpUtils.doGet(executeUrl, headers, AcornResponse.class);
        }

        public AcornResponse stopJob(String jobId) {

            String executeUrl = String.format(UrlConstants.STOP_JOB_URL + jobId, acornNodeProperties.getHost(), acornNodeProperties.getPort());

            Map<String, String> headers = new HashMap<>();
            headers.put(SecurityConstants.HEADER_AUTHORIZATION, acornNodeProperties.getKey());

            return HttpUtils.doGet(executeUrl, headers, AcornResponse.class);
        }

        public AcornResponse getJobStatus(String jobId) {

            String executeUrl = String.format(UrlConstants.GET_JOB_STATUS_URL + jobId, acornNodeProperties.getHost(), acornNodeProperties.getPort());

            Map<String, String> headers = new HashMap<>();
            headers.put(SecurityConstants.HEADER_AUTHORIZATION, acornNodeProperties.getKey());

            return HttpUtils.doGet(executeUrl, headers, AcornResponse.class);
        }
    }

}
