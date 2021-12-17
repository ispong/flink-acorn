package com.isxcode.acorn.plugin.service;

import com.isxcode.acorn.common.pojo.dto.AcornResponse;
import com.isxcode.acorn.common.pojo.dto.ExecuteConfig;
import com.isxcode.acorn.common.pojo.req.AcornModel1;
import com.isxcode.acorn.common.pojo.req.AcornRequest;
import com.isxcode.acorn.common.properties.AcornProperties;
import com.isxcode.acorn.plugin.constant.FlinkConstants;
import com.isxcode.acorn.plugin.utils.ShellUtils;
import com.isxcode.oxygen.core.file.FileUtils;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
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

    public AcornResponse execute(AcornModel1 acornModel1) {

//        AcornModel1 acornModel1 = acornRequest instanceof AcornModel1 ? ((AcornModel1) acornRequest) : null;

        // 检查配置文件合法性
        assert acornModel1 != null;
        if (Strings.isEmpty(acornModel1.getExecuteId())) {
            return new AcornResponse("10001", "executeId为空");
        }

        // 生成FlinkJob.java代码
        String flinkJobJavaCode;
        Template template;
        try {
            template = freeMarkerConfigurer.getConfiguration().getTemplate("Model1-kafkaToMysql.ftl");
            flinkJobJavaCode = FreeMarkerTemplateUtils.processTemplateIntoString(template, acornModel1);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
            return new AcornResponse("10001", "executeId为空");
        }

        // 创建FlinkJob.java文件
        String flinkJobPath = acornProperties.getTmpDir() + File.separator + acornModel1.getExecuteId() + FlinkConstants.JOB_HOME_PATH + File.separator + FlinkConstants.JOB_FILE_NAME;
        FileUtils.StringToFile(flinkJobJavaCode, flinkJobPath, StandardOpenOption.WRITE);

        // 创建pom.xml文件
        String flinkPomFilePath = acornProperties.getTmpDir() + File.separator + acornModel1.getExecuteId() + File.separator + FlinkConstants.POM_XML;
        FileUtils.copyResourceFile("templates/pom.xml", flinkPomFilePath, StandardOpenOption.WRITE);

        // 创建日志文件
        String logPath = acornProperties.getLogDir() + File.separator + acornModel1.getExecuteId() + FlinkConstants.LOG_SUFFIX;
        FileUtils.generateFile(logPath);

        // 执行编译且运行的命令
        String mvnBuildCommand = "mvn clean package -f " + flinkPomFilePath;
//        ShellUtils.executeCommand(mvnBuildCommand, logPath);

        String submitFlinkJob = "flink run " + acornProperties.getTmpDir() + File.separator + acornModel1.getExecuteId() + File.separator + "target" + File.separator + "flinkJob-1.0.0.jar";
//        ShellUtils.executeCommand(submitFlinkJob, logPath);

        ShellUtils.executeCommand(mvnBuildCommand + " && " + submitFlinkJob, logPath);

        return new AcornResponse("10009", "运行成功");
    }
}