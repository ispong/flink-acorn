package com.isxcode.acorn.plugin.service;

import com.isxcode.acorn.common.constant.FlinkConstants;
import com.isxcode.acorn.common.pojo.dto.AcornResponse;
import com.isxcode.acorn.common.pojo.model.AcornModel1;
import com.isxcode.acorn.common.properties.AcornPluginProperties;
import com.isxcode.acorn.plugin.utils.CommandUtils;
import com.isxcode.oxygen.core.file.FileUtils;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

@Slf4j
@Service
public class AcornBizService {

    private final AcornPluginProperties acornPluginProperties;

    private final FreeMarkerConfigurer freeMarkerConfigurer;

    public AcornBizService(AcornPluginProperties acornPluginProperties,
                           FreeMarkerConfigurer freeMarkerConfigurer) {

        this.acornPluginProperties = acornPluginProperties;
        this.freeMarkerConfigurer = freeMarkerConfigurer;
    }

    public String getTableName(String sql) {

        sql = Pattern.compile("CREATE * TABLE").matcher(sql).replaceAll("");
        return Pattern.compile("\\( .*").matcher(sql).replaceAll("");
    }

    /**
     * 执行flink任务
     */
    public AcornResponse execute(AcornModel1 acornModel) {

        // 检测数据合法性
        acornModel.check();

        // 选择模板
        Template template;
        try {
            template = freeMarkerConfigurer.getConfiguration().getTemplate(acornModel.getTemplateName().getTemplateFileName());
        } catch (IOException e) {
            e.printStackTrace();
            return new AcornResponse("10001", "executeId为空");
        }

        // 解析表名
        acornModel.setFromTableName(getTableName(acornModel.getFromConnectorSql()));
        acornModel.setToTableName(getTableName(acornModel.getToConnectorSql()));

        // 生成FlinkJob.java代码
        String flinkJobJavaCode;
        try {
            System.out.println(acornModel.toString());
            flinkJobJavaCode = FreeMarkerTemplateUtils.processTemplateIntoString(template, acornModel);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
            return new AcornResponse("10001", "executeId为空");
        }

        // 创建FlinkJob.java文件
        String tmpPath = acornPluginProperties.getTmpDir() + File.separator + acornModel.getExecuteId();
        String flinkJobPath = tmpPath + FlinkConstants.JOB_HOME_PATH + File.separator + FlinkConstants.JOB_FILE_NAME;
        FileUtils.StringToFile(flinkJobJavaCode, flinkJobPath, StandardOpenOption.WRITE);

        // 创建pom.xml文件
        String flinkPomFilePath = acornPluginProperties.getTmpDir() + File.separator + acornModel.getExecuteId() + File.separator + FlinkConstants.POM_XML;
        FileUtils.copyResourceFile("templates/pom.xml", flinkPomFilePath, StandardOpenOption.WRITE);

        // 创建日志文件
        String logPath = acornPluginProperties.getLogDir() + File.separator + acornModel.getExecuteId() + FlinkConstants.LOG_SUFFIX;
        FileUtils.generateFile(logPath);

        // 执行编译且运行作业
        String targetFilePath = acornPluginProperties.getTmpDir() + File.separator + acornModel.getExecuteId() + File.separator + "target" + File.separator + "acorn.jar";
        String executeCommand = "mvn clean package -f " + flinkPomFilePath + " && " + "flink run " + targetFilePath;
        CommandUtils.executeCommand(executeCommand, logPath);

        // 删除缓存中的文件
        try {
            Files.delete(Paths.get(tmpPath));
        } catch (IOException e) {
            return new AcornResponse("10005", "文件删除异常");
        }

        return new AcornResponse("10001", "executeId为空");
    }

}
