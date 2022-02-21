package com.isxcode.acorn.common.template;

import com.isxcode.acorn.common.constant.SecurityConstants;
import com.isxcode.acorn.common.constant.SysConstants;
import com.isxcode.acorn.common.constant.UrlConstants;
import com.isxcode.acorn.common.exception.AcornException;
import com.isxcode.acorn.common.exception.AcornExceptionEnum;
import com.isxcode.acorn.common.pojo.AcornRequest;
import com.isxcode.acorn.common.pojo.AcornResponse;
import com.isxcode.acorn.common.properties.AcornServerInfo;
import com.isxcode.acorn.common.properties.AcornServerProperties;
import com.isxcode.oxygen.core.http.HttpUtils;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class AcornTemplate {

    private final AcornServerProperties acornServerProperties;

    public AcornTemplate.Builder build(String serverName) {

        AcornServerInfo serverInfo = acornServerProperties.getServer().get(serverName);
        if (serverInfo == null) {
            throw new AcornException(AcornExceptionEnum.ACORN_SERVER_NOT_FOUND);
        }
        return new Builder(serverInfo);
    }

    public AcornTemplate.Builder build() {

        AcornServerInfo serverInfo = acornServerProperties.getServer().get(SysConstants.DEFAULT_SERVER_NAME);
        if (serverInfo == null) {
            throw new AcornException(AcornExceptionEnum.ACORN_SERVER_NOT_FOUND);
        }
        return new Builder(serverInfo);
    }

    public AcornTemplate.Builder build(String host, int port, String key) {

        return new Builder(new AcornServerInfo(host, port, key));
    }

    public static class Builder {

        private final AcornServerInfo serverInfo;

        public Builder(AcornServerInfo serverInfo) {

            this.serverInfo = serverInfo;
        }

        public AcornResponse requestAcornServer(String url, AcornRequest acornRequest) {

            Map<String, String> headers = new HashMap<>();
            headers.put(SecurityConstants.HEADER_AUTHORIZATION, serverInfo.getKey());

            try {
                return HttpUtils.doPost(url, headers, acornRequest, AcornResponse.class);
            } catch (IOException e) {
                return new AcornResponse("500", e.getMessage());
            }
        }

        public AcornResponse executeJson(AcornRequest acornRequest) {

            String executeUrl = String.format(UrlConstants.BASE_URL + UrlConstants.EXECUTE_JSON_URL, serverInfo.getHost(), serverInfo.getPort());
            return requestAcornServer(executeUrl, acornRequest);
        }

        public AcornResponse getJobLog(String executeId) {

            AcornRequest acornRequest = AcornRequest.builder().executeId(executeId).build();
            String executeUrl = String.format(UrlConstants.BASE_URL + UrlConstants.GET_JOB_LOG_URL, serverInfo.getHost(), serverInfo.getPort());
            return requestAcornServer(executeUrl, acornRequest);
        }

        public AcornResponse stopJob(String jobId) {

            AcornRequest acornRequest = AcornRequest.builder().jobId(jobId).build();
            String executeUrl = String.format(UrlConstants.BASE_URL + UrlConstants.STOP_JOB_URL, serverInfo.getHost(), serverInfo.getPort());
            return requestAcornServer(executeUrl, acornRequest);
        }

        public AcornResponse getJobStatus(String jobId) {

            AcornRequest acornRequest = AcornRequest.builder().jobId(jobId).build();
            String executeUrl = String.format(UrlConstants.BASE_URL + UrlConstants.GET_JOB_STATUS_URL, serverInfo.getHost(), serverInfo.getPort());
            return requestAcornServer(executeUrl, acornRequest);
        }

        public AcornResponse queryJobStatus() {

            String executeUrl = String.format(UrlConstants.BASE_URL + UrlConstants.QUERY_JOB_STATUS_URL, serverInfo.getHost(), serverInfo.getPort());
            return requestAcornServer(executeUrl, null);
        }
    }

}
