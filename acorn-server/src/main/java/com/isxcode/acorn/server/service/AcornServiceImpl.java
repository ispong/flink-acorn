package com.isxcode.acorn.server.service;

import com.isxcode.acorn.common.pojo.AcornRequest;
import org.springframework.stereotype.Service;

@Service
public class AcornServiceImpl implements AcornService {


    @Override
    public String getLogPath(AcornRequest acornRequest) {
        return null;
    }

    @Override
    public String initPomFile(String projectPath) {
        return null;
    }

    @Override
    public void initJavaFile(String projectPath, String javaCode) {

    }

    @Override
    public String getProjectPath(AcornRequest acornRequest) {
        return null;
    }

    @Override
    public String getJavaCode(AcornRequest acornRequest) {
        return null;
    }

    @Override
    public void deployJob(String projectPath, String PomPath, String logPath) {

    }

    @Override
    public String getJobId(String logPath) {
        return null;
    }
}
