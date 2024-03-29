package com.isxcode.acorn.client.config;

import com.isxcode.acorn.api.constant.MsgConstants;
import com.isxcode.acorn.api.constant.SecurityConstants;
import com.isxcode.acorn.api.constant.URLs;
import com.isxcode.acorn.api.pojo.AcornResponse;
import com.isxcode.acorn.api.properties.AcornProperties;
import com.isxcode.acorn.api.properties.ServerInfo;
import com.isxcode.acorn.api.utils.HttpUtils;
import com.isxcode.acorn.client.template.AcornTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
@EnableConfigurationProperties({AcornProperties.class, ServerInfo.class})
@RequiredArgsConstructor
public class AcornAutoConfig {

    private final AcornProperties acornProperties;

    /**
     * 初始化用户默认配置的节点信息
     *
     * @return acornTemplate
     */
    @Bean("acornTemplate")
    public AcornTemplate acornTemplate() {

        return new AcornTemplate(acornProperties);
    }

    /*
     * 测试插件连接是否正常
     *
     * @ispong
     */
    @Bean
    @ConditionalOnProperty(prefix = "acorn.servers.default", name = {"host", "port", "key"})
    public void checkServerStatus() {

        if (!acornProperties.getCheckServers()) {
            return;
        }

        if (acornProperties.getCheckServers() == null) {
            return;
        }

        System.out.println("=================检查节点=======================");
        acornProperties.getServers().forEach((k, v) -> {
            try {
                // 检查用户配置的节点是否有效
                String heartCheckUrl = String.format(URLs.BASE_URL + URLs.HEART_CHECK_URL, v.getHost(), v.getPort());
                Map<String, String> headers = new HashMap<>();
                headers.put(SecurityConstants.HEADER_AUTHORIZATION, v.getKey());
                AcornResponse acornResponse = HttpUtils.doGet(heartCheckUrl, headers, AcornResponse.class);

                if (MsgConstants.SUCCESS_CODE.equals(acornResponse.getCode())) {
                    System.out.println(k + ":" + v.getHost() + ":[ok]");
                } else {
                    System.out.println(k + ":" + v.getHost() + ":[error]");
                }
            } catch (Exception e) {
                log.error(e.getMessage());
                System.out.println(k + ":" + v.getHost() + ":[error]");
            }
        });
        System.out.println("==============================================");
    }
}
