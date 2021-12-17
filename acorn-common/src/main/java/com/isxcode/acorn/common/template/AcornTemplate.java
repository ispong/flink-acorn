package com.isxcode.acorn.common.template;

import com.isxcode.acorn.common.properties.AcornProperties;
import com.isxcode.acorn.common.service.AcornServiceImpl;
import org.springframework.stereotype.Service;

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
}
