package com.isxcode.acorn.api.utils;

import com.isxcode.acorn.api.constant.URLs;
import com.isxcode.acorn.api.exception.AcornException;
import org.apache.logging.log4j.util.Strings;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class YamlUtils {

    public static String getFlinkJobHistoryUrl() {

        DefaultResourceLoader defaultResourceLoader = new DefaultResourceLoader();
        Resource resource = defaultResourceLoader.getResource("test.yml");
        Yaml yaml = new Yaml();

        String flinkConfigPath = System.getenv("FLINK_HOME") + File.separator + "conf" + File.separator + "flink-conf.yaml";
        try {
            InputStream inputStream = Files.newInputStream(Paths.get(flinkConfigPath));
            Map<String, String> load = yaml.load(inputStream);

            String address = load.get("historyserver.web.address");
            if (Strings.isEmpty(address)) {
                throw new AcornException("50010", "请在flink-conf.yaml中配置historyserver.web.address属性，并开启flink的jobHistory服务");
            }

            String port = String.valueOf(load.get("historyserver.web.port"));
            if (Strings.isEmpty(port)) {
                throw new AcornException("50010", "请在flink-conf.yaml中配置historyserver.web.port属性，并开启flink的jobHistory服务");
            }

            return URLs.HTTP + address + ":" + port;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
