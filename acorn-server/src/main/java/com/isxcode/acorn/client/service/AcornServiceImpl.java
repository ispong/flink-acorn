package com.isxcode.acorn.client.service;

import com.isxcode.acorn.common.constant.FileConstants;
import com.isxcode.acorn.common.exception.AcornException;
import com.isxcode.acorn.common.exception.AcornExceptionEnum;
import com.isxcode.acorn.common.pojo.AcornRequest;
import com.isxcode.acorn.common.utils.CommandUtils;
import com.isxcode.acorn.common.utils.FileUtils;
import com.isxcode.acorn.common.utils.FreemarkerUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
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
