package com.isxcode.acorn.client.service;

import com.isxcode.acorn.common.pojo.AcornRequest;
import com.isxcode.acorn.common.pojo.dto.AcornData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AcornBizService {

    private final AcornService acornService;


    public AcornData executeFlinkSql(AcornRequest acornRequest) {


        return null;
    }

    public AcornData getDeployLog(AcornRequest acornRequest) {

        return null;
    }

    public AcornData getJobId(AcornRequest acornRequest) {

        return null;
    }

    public AcornData stopJob(AcornRequest acornRequest) {

        return null;
    }

    public AcornData getJobInfo(AcornRequest acornRequest) {

        return null;
    }

    public AcornData getJobExceptions(AcornRequest acornRequest) {

        return null;
    }

    public AcornData queryJobStatus() {

        return null;
    }
}
