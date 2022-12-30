package com.isxcode.acorn.client.template;

import com.isxcode.acorn.api.constant.SecurityConstants;
import com.isxcode.acorn.api.constant.SysConstants;
import com.isxcode.acorn.api.constant.URLs;
import com.isxcode.acorn.api.exception.AcornException;
import com.isxcode.acorn.api.exception.AcornExceptionEnum;
import com.isxcode.acorn.api.menu.Template;
import com.isxcode.acorn.api.pojo.AcornRequest;
import com.isxcode.acorn.api.pojo.AcornResponse;
import com.isxcode.acorn.api.properties.AcornProperties;
import com.isxcode.acorn.api.properties.ServerInfo;
import com.isxcode.acorn.api.utils.HttpUtils;
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

    private final AcornProperties acornProperties;

    public AcornTemplate.Builder build(String serverName) {

        ServerInfo serverInfo = acornProperties.getServers().get(serverName);
        if (serverInfo == null) {
            throw new AcornException(AcornExceptionEnum.ACORN_SERVER_NOT_FOUND);
        }
        return new Builder(serverInfo);
    }

    public AcornTemplate.Builder build() {

        ServerInfo serverInfo = acornProperties.getServers().get(SysConstants.DEFAULT_SERVER_NAME);
        if (serverInfo == null) {
            throw new AcornException(AcornExceptionEnum.ACORN_SERVER_NOT_FOUND);
        }
        return new Builder(serverInfo);
    }

    public AcornTemplate.Builder build(String host, int port, String key) {

        return new Builder(new ServerInfo(host, port, key));
    }

    public static class Builder {

        public AcornRequest acornRequest = new AcornRequest();

        private final ServerInfo serverInfo;

        public Builder(ServerInfo serverInfo) {

            this.serverInfo = serverInfo;
        }

        public AcornResponse requestAcornServer(String url, AcornRequest acornRequest) {

            Map<String, String> headers = new HashMap<>();
            headers.put(SecurityConstants.HEADER_AUTHORIZATION, serverInfo.getKey());

            try {
                return HttpUtils.doPost(url, headers, acornRequest, AcornResponse.class);
            } catch (IOException e) {
                log.error(e.getMessage());
                return new AcornResponse("500", e.getMessage());
            }
        }

        public Builder applicationId(String applicationId) {

            acornRequest.setApplicationId(applicationId);
            return this;
        }

        public Builder name(String name) {

            acornRequest.setName(name);
            return this;
        }

        public Builder pluginName(String pluginName) {

            acornRequest.setPluginName(pluginName);
            return this;
        }

        public Builder sql(String sql) {

            acornRequest.setSql(sql);
            return this;
        }

        public Builder jobId(String jobId) {

            acornRequest.setJobId(jobId);
            return this;
        }

        public Builder java(String java) {

            acornRequest.setJava(java);
            return this;
        }

        public Builder template(Template template) {

            acornRequest.setTemplate(template);
            return this;
        }

        public AcornResponse deploy() {

            String executeUrl = String.format(URLs.BASE_URL + URLs.EXECUTE_SQL_URL, serverInfo.getHost(), serverInfo.getPort());
            return requestAcornServer(executeUrl, acornRequest);
        }

        public AcornResponse stopJob() {

            String executeUrl = String.format(URLs.BASE_URL + URLs.STOP_JOB_URL, serverInfo.getHost(), serverInfo.getPort());
            return requestAcornServer(executeUrl, acornRequest);
        }

        public AcornResponse getJobStatus() {

            String executeUrl = String.format(URLs.BASE_URL + URLs.GET_JOB_STATUS_URL, serverInfo.getHost(), serverInfo.getPort());
            return requestAcornServer(executeUrl, acornRequest);
        }

        public AcornResponse getYarnStatus() {

            String executeUrl = String.format(URLs.BASE_URL + URLs.GET_YARN_STATUS_URL, serverInfo.getHost(), serverInfo.getPort());
            return requestAcornServer(executeUrl, acornRequest);
        }

        public AcornResponse getRootExceptions() {

            String executeUrl = String.format(URLs.BASE_URL + URLs.GET_ROOT_EXCEPTIONS_URL, serverInfo.getHost(), serverInfo.getPort());
            return requestAcornServer(executeUrl, acornRequest);
        }

        public AcornResponse getJobManagerLog() {

            String executeUrl = String.format(URLs.BASE_URL + URLs.GET_JOB_MANAGER_LOG_URL, serverInfo.getHost(), serverInfo.getPort());
            return requestAcornServer(executeUrl, acornRequest);
        }

        public AcornResponse getTaskManagerLog() {

            String executeUrl = String.format(URLs.BASE_URL + URLs.GET_TASK_MANAGER_LOG_URL, serverInfo.getHost(), serverInfo.getPort());
            return requestAcornServer(executeUrl, acornRequest);
        }

    }

}
