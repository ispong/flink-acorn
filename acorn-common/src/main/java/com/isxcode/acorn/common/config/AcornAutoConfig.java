package com.isxcode.acorn.common.config;

import com.isxcode.acorn.common.constant.MsgConstants;
import com.isxcode.acorn.common.constant.SecurityConstants;
import com.isxcode.acorn.common.constant.UrlConstants;
import com.isxcode.acorn.common.pojo.AcornResponse;
import com.isxcode.acorn.common.properties.AcornPluginProperties;
import com.isxcode.acorn.common.properties.AcornServerInfo;
import com.isxcode.acorn.common.properties.AcornServerProperties;
import com.isxcode.acorn.common.template.AcornTemplate;
import com.isxcode.oxygen.core.http.HttpUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableConfigurationProperties({AcornPluginProperties.class, AcornServerProperties.class, AcornServerInfo.class})
@RequiredArgsConstructor
public class AcornAutoConfig {

    private final AcornServerProperties acornServerProperties;

    /**
     * 初始化用户默认配置的节点信息
     *
     * @param acornServerProperties 结点配置信息
     * @return acornTemplate
     */
    @Bean("acornTemplate")
    public AcornTemplate acornTemplate(AcornServerProperties acornServerProperties) {

        return new AcornTemplate(acornServerProperties);
    }

    /*
     * 测试插件连接是否正常
     *
     * @ispong
     */
    @Bean
    public void checkServerStatus() {

        if (!acornServerProperties.getCheckServer()) {
            return;
        }

        if (acornServerProperties.getServer() == null) {
            System.out.println("Acorn配置中未找到server配置");
            return;
        }

        System.out.println("=================检查节点=======================");
        acornServerProperties.getServer().forEach((k, v) -> {
            try {
                // 检查用户配置的节点是否有效
                String heartCheckUrl = String.format(UrlConstants.BASE_URL + UrlConstants.HEART_CHECK_URL, v.getHost(), v.getPort());
                Map<String, String> headers = new HashMap<>();
                headers.put(SecurityConstants.HEADER_AUTHORIZATION, v.getKey());
                AcornResponse acornResponse = HttpUtils.doGet(heartCheckUrl, headers, AcornResponse.class);

                if (MsgConstants.SUCCESS_CODE.equals(acornResponse.getCode())) {
                    System.out.println(k + ":" + v.getHost() + ":[ok]");
                } else {
                    System.out.println(k + ":" + v.getHost() + ":[error]");
                }
            } catch (Exception e) {
                System.out.println(k + ":" + v.getHost() + ":[error]");
            }
        });
        System.out.println("==============================================");
    }
}
