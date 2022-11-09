package com.isxcode.acorn.server.service;

import com.isxcode.acorn.common.pojo.AcornRequest;
import org.springframework.stereotype.Service;

@Service
public interface AcornService {

    String getLogPath(AcornRequest acornRequest);


    String initPomFile(String projectPath);

    void initJavaFile(String projectPath, String javaCode);

    String getProjectPath(AcornRequest acornRequest);

    String getJavaCode(AcornRequest acornRequest);

    void deployJob(String projectPath, String PomPath, String logPath);

    String getJobId(String logPath);

}