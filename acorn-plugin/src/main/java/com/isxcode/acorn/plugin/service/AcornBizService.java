package com.isxcode.acorn.plugin.service;

import com.isxcode.acorn.common.pojo.dto.AcornResponse;
import com.isxcode.acorn.common.pojo.request.AcornRequest;
import com.isxcode.acorn.common.properties.AcornProperties;
import com.isxcode.acorn.plugin.constant.FlinkConstants;
import com.isxcode.acorn.plugin.utils.ShellUtils;
import com.isxcode.oxygen.core.file.FileUtils;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.File;
import java.io.IOException;
import java.nio.file.StandardOpenOption;

@Slf4j
@Service
public class AcornBizService {

    private final AcornProperties acornProperties;

    private final FreeMarkerConfigurer freeMarkerConfigurer;

    public AcornBizService(AcornProperties acornProperties,
                           FreeMarkerConfigurer freeMarkerConfigurer) {

        this.acornProperties = acornProperties;
        this.freeMarkerConfigurer = freeMarkerConfigurer;
    }

    /**
     * 执行flink任务
     */
    public AcornResponse execute(AcornRequest acornRequest) {

        // 检测数据合法性
        acornRequest.check();

        // 选择模板
        Template template;
        try {
            template = freeMarkerConfigurer.getConfiguration().getTemplate(acornRequest.getTemplateName().getTemplateFileName());
        } catch (IOException e) {
            e.printStackTrace();
            return new AcornResponse("10001", "executeId为空");
        }

        // 生成FlinkJob.java代码
        String flinkJobJavaCode;
        try {
            flinkJobJavaCode = FreeMarkerTemplateUtils.processTemplateIntoString(template, acornRequest);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
            return new AcornResponse("10001", "executeId为空");
        }

        // 创建FlinkJob.java文件
        String flinkJobPath = acornProperties.getTmpDir() + File.separator + acornRequest.getExecuteId() + FlinkConstants.JOB_HOME_PATH + File.separator + FlinkConstants.JOB_FILE_NAME;
        FileUtils.StringToFile(flinkJobJavaCode, flinkJobPath, StandardOpenOption.WRITE);

        // 创建pom.xml文件
        String flinkPomFilePath = acornProperties.getTmpDir() + File.separator + acornRequest.getExecuteId() + File.separator + FlinkConstants.POM_XML;
        FileUtils.copyResourceFile("templates/pom.xml", flinkPomFilePath, StandardOpenOption.WRITE);

        // 创建日志文件
        String logPath = acornProperties.getLogDir() + File.separator + acornRequest.getExecuteId() + FlinkConstants.LOG_SUFFIX;
        FileUtils.generateFile(logPath);

        // 执行编译且运行作业
        String targetFilePath = acornProperties.getTmpDir() + File.separator + acornRequest.getExecuteId() + File.separator + "target" + File.separator + "acorn.jar";
        String executeCommand = "mvn clean package -f " + flinkPomFilePath + " && " + "flink run " + targetFilePath;
        ShellUtils.executeCommand(executeCommand, logPath);

        return new AcornResponse("10001", "executeId为空");
    }

}