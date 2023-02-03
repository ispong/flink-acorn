package com.isxcode.acorn.server.service;

import com.alibaba.fastjson.JSON;
import com.isxcode.acorn.api.exception.AcornException;
import com.isxcode.acorn.api.pojo.AcornRequest;
import com.isxcode.acorn.api.pojo.dto.AcornData;
import com.isxcode.acorn.api.pojo.flink.JobExceptions;
import com.isxcode.acorn.api.pojo.flink.JobStatus;
import com.isxcode.acorn.api.utils.YamlUtils;
import com.isxcode.acorn.server.utils.HadoopUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.client.deployment.ClusterDeploymentException;
import org.apache.flink.client.deployment.ClusterSpecification;
import org.apache.flink.client.program.ClusterClientProvider;
import org.apache.flink.client.program.PackagedProgram;
import org.apache.flink.client.program.PackagedProgramUtils;
import org.apache.flink.client.program.ProgramInvocationException;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.configuration.GlobalConfiguration;
import org.apache.flink.runtime.jobgraph.JobGraph;
import org.apache.flink.runtime.jobgraph.SavepointRestoreSettings;
import org.apache.flink.yarn.YarnClientYarnClusterInformationRetriever;
import org.apache.flink.yarn.YarnClusterClientFactory;
import org.apache.flink.yarn.YarnClusterDescriptor;
import org.apache.flink.yarn.configuration.YarnConfigOptionsInternal;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.yarn.api.records.ApplicationId;
import org.apache.hadoop.yarn.api.records.ApplicationReport;
import org.apache.hadoop.yarn.api.records.FinalApplicationStatus;
import org.apache.hadoop.yarn.api.records.YarnApplicationState;
import org.apache.hadoop.yarn.client.api.YarnClient;
import org.apache.hadoop.yarn.conf.YarnConfiguration;
import org.apache.hadoop.yarn.exceptions.YarnException;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;
import static org.apache.flink.configuration.CoreOptions.DEFAULT_PARALLELISM;

@Slf4j
@Service
@RequiredArgsConstructor
public class AcornBizService {

    public java.io.InputStream getHadoopConfigPath(String configName) {

        String hadoopHomeDir = System.getenv("HADOOP_HOME");
        try {
            return Files.newInputStream(Paths.get(hadoopHomeDir + File.separator + "etc" + File.separator + "hadoop" + File.separator + configName));
        } catch (IOException e) {
            e.printStackTrace();
            throw new AcornException("50001", configName + "配置文件不存在");
        }
    }

    public org.apache.hadoop.conf.Configuration generateHadoopConf() {

        org.apache.hadoop.conf.Configuration hadoopConf = new org.apache.hadoop.conf.Configuration(false);
        hadoopConf.addResource(getHadoopConfigPath("core-site.xml"));
        hadoopConf.addResource(getHadoopConfigPath("hdfs-site.xml"));
        hadoopConf.addResource(getHadoopConfigPath("yarn-site.xml"));

        return hadoopConf;
    }

    public AcornData executeSql(AcornRequest acornRequest) {

        // 获取hadoop的配置文件目录
        String flinkHomeDir = System.getenv("FLINK_HOME");
        String acornHomeDir = System.getenv("ACORN_HOME");

        // 初始化yarn客户端
        YarnClient yarnClient = YarnClient.createYarnClient();
        YarnConfiguration yarnConfig = new YarnConfiguration(generateHadoopConf());
        yarnClient.init(yarnConfig);
        yarnClient.start();

        // 配置flink配置
        Configuration flinkConfig = GlobalConfiguration.loadConfiguration(flinkHomeDir + "/conf");
        flinkConfig.setString(YarnConfigOptionsInternal.APPLICATION_LOG_CONFIG_FILE, flinkHomeDir + "/conf/log4j.properties");

        // 配置flink on yarn的资源
        ClusterSpecification clusterSpecification = new ClusterSpecification.ClusterSpecificationBuilder()
            .setMasterMemoryMB(acornRequest.getMasterMemoryMB())
            .setTaskManagerMemoryMB(acornRequest.getTaskManagerMemoryMB())
            .setSlotsPerTaskManager(acornRequest.getSlotsPerTaskManager())
            .createClusterSpecification();

        YarnClusterDescriptor descriptor = new YarnClusterDescriptor(
            flinkConfig,
            yarnConfig,
            yarnClient,
            YarnClientYarnClusterInformationRetriever.create(yarnClient),
            false);

        File[] jars = new File(flinkHomeDir + "/lib/").listFiles();
        List<File> shipFiles = new ArrayList<>();
        List<URL> classpathFiles = new ArrayList<>();
        if (jars != null) {
            for (File jar : jars) {
                if (jar.getName().contains("flink-dist")) {
                    try {
                        descriptor.setLocalJarPath(new Path(jar.toURI().toURL().toString()));
                    } catch (MalformedURLException e) {
                        log.error(e.getMessage());
                        throw new AcornException("50015",e.getMessage());
                    }
                } else if (jar.getName().contains("flink-connector")
                    || jar.getName().contains("hive-exec")
                    || jar.getName().contains("libfb303")) {
                    try {
                        shipFiles.add(jar);
                        classpathFiles.add(jar.toURI().toURL());
                    } catch (MalformedURLException e) {
                        log.error(e.getMessage());
                        throw new AcornException("50015", e.getMessage());
                    }
                } else {
                    shipFiles.add(jar);
                }
            }
        }

        File[] connectorJars = new File(acornHomeDir + "/connector/").listFiles();
        if (connectorJars != null) {
            shipFiles.addAll(Arrays.asList(connectorJars));
            for (File connectorJar : connectorJars) {
                try {
                    classpathFiles.add(connectorJar.toURI().toURL());
                } catch (MalformedURLException e) {
                    log.error(e.getMessage());
                    throw new AcornException("50015", e.getMessage());
                }
            }
        }

        shipFiles.add(new File("/opt/flink/conf/log4j.properties"));

        descriptor.addShipFiles(shipFiles);

        PackagedProgram program;
        if (!Strings.isEmpty(acornRequest.getSql())) {
            try {
                program = PackagedProgram.newBuilder()
                    .setJarFile(new File(acornHomeDir + "/plugins/acorn-sql-plugin.jar"))
                    .setEntryPointClassName("com.isxcode.acorn.plugin.sql.SqlJob")
                    .setArguments(acornRequest.getSql())
                    .setUserClassPaths(classpathFiles)
                    .setSavepointRestoreSettings(SavepointRestoreSettings.none())
                    .build();
            } catch (ProgramInvocationException e) {
                log.error(e.getMessage());
                throw new AcornException("50015",e.getMessage());
            }
        } else {

            if (Strings.isEmpty(acornRequest.getPluginName())) {
                throw new AcornException("50016", "PluginName不能为空");
            }

            String pluginJarPath = acornHomeDir + File.separator + "plugins" + File.separator + acornRequest.getPluginName() + ".jar";

            try {
                PackagedProgram.Builder packagedProgramBuilder = PackagedProgram.newBuilder()
                    .setJarFile(new File(pluginJarPath))
                    .setUserClassPaths(classpathFiles)
                    .setSavepointRestoreSettings(SavepointRestoreSettings.none());

                if (!Strings.isEmpty(acornRequest.getPluginMainClass())) {
                    packagedProgramBuilder.setEntryPointClassName(acornRequest.getPluginMainClass());
                }

                if (acornRequest.getPluginArguments() != null) {
                    packagedProgramBuilder.setArguments(acornRequest.getPluginArguments().toString());
                }

                program = packagedProgramBuilder.build();

            } catch (ProgramInvocationException e) {
                log.error(e.getMessage());
                throw new AcornException("50015", e.getMessage());
            }
        }

        JobGraph jobGraph;
        try {
            jobGraph = PackagedProgramUtils.createJobGraph(program, flinkConfig, flinkConfig.getInteger(DEFAULT_PARALLELISM), false);
        } catch (ProgramInvocationException e) {
            e.printStackTrace();
            throw new AcornException("50014", e.getMessage());
        }

        // 将本地jar包提交到yarn集群
        ClusterClientProvider<ApplicationId> provider;
        try {
            provider = descriptor.deployJobCluster(clusterSpecification, jobGraph, true);
        } catch (ClusterDeploymentException e) {
            e.printStackTrace();
            throw new AcornException("50015", e.getMessage());
        }

        String applicationId = provider.getClusterClient().getClusterId().toString();
        String flinkJobId = jobGraph.getJobID().toString();

        return AcornData.builder()
            .applicationId(applicationId)
            .flinkJobId(flinkJobId)
            .webInterfaceURL(provider.getClusterClient().getWebInterfaceURL())
            .build();
    }

    public AcornData getTaskManagerLog(AcornRequest acornRequest) {

        Map<String, String> map = HadoopUtils.parseYarnLog(acornRequest.getApplicationId());

        String taskManagerLog = map.get("taskmanager.log") == null ? "" : map.get("taskmanager.log");

        return AcornData.builder()
            .taskManagerLogs(Arrays.asList(taskManagerLog.split("\n")))
            .build();
    }

    public AcornData getJobManagerLog(AcornRequest acornRequest)  {

        Map<String, String> map = HadoopUtils.parseYarnLog(acornRequest.getApplicationId());

        String jobManagerLog = map.get("jobmanager.log") == null ? "" : map.get("jobmanager.log");

        return AcornData.builder()
            .jobManagerLogs(Arrays.asList(jobManagerLog.split("\n")))
            .build();
    }

    public AcornData getYarnStatus(AcornRequest acornRequest) throws IOException, YarnException {

        YarnClient yarnClient = YarnClient.createYarnClient();
        YarnConfiguration yarnConfig = new YarnConfiguration(generateHadoopConf());
        yarnClient.init(yarnConfig);
        yarnClient.start();

        ApplicationReport applicationReport;
        try {
            applicationReport = yarnClient.getApplicationReport(ApplicationId.fromString(acornRequest.getApplicationId()));
        } catch (Exception e) {
            throw new AcornException("50020", e.getMessage());
        }

        FinalApplicationStatus finalApplicationStatus = applicationReport.getFinalApplicationStatus();
        YarnApplicationState yarnApplicationState = applicationReport.getYarnApplicationState();

        return AcornData.builder().finalStatus(finalApplicationStatus.name()).yarnState(yarnApplicationState.name()).build();
    }

    public AcornData killYarn(AcornRequest acornRequest) throws IOException, YarnException {

        YarnClient yarnClient = YarnClient.createYarnClient();
        YarnConfiguration yarnConfig = new YarnConfiguration(generateHadoopConf());
        yarnClient.init(yarnConfig);
        yarnClient.start();

        try {
            yarnClient.killApplication(ApplicationId.fromString(acornRequest.getApplicationId()));
        } catch (Exception e) {
            throw new AcornException("50017", e.getMessage());
        }

        return AcornData.builder().build();
    }

    public AcornData getJobStatus(AcornRequest acornRequest) throws IOException, YarnException {

        ResponseEntity<JobStatus> response;
        try {
            response = new RestTemplate().getForEntity(YamlUtils.getFlinkJobHistoryUrl() + "/jobs/" + acornRequest.getJobId(), JobStatus.class);
        }catch (Exception e){
            log.error(e.getMessage());
            throw new AcornException("50016", "作业正在运行中或者作业不存在");
        }

        return AcornData.builder().jobStatus(response.getBody()).build();
    }

    public AcornData getRootExceptions(AcornRequest acornRequest) {

        ResponseEntity<JobExceptions> response;
        try {
            response = new RestTemplate().getForEntity(YamlUtils.getFlinkJobHistoryUrl() + "/jobs/" + acornRequest.getJobId() + "/exceptions", JobExceptions.class);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new AcornException("50016", "作业正在运行中或者作业不存在");
        }

        String rootExceptions;
        if (response.getBody() == null || response.getBody().getRootException() == null) {
            rootExceptions = "";
        } else {
            rootExceptions = response.getBody().getRootException();
        }

        return AcornData.builder().rootExceptions(Arrays.asList(rootExceptions.split("\n"))).build();
    }

    public AcornData getData(AcornRequest acornRequest) throws IOException, YarnException {

        Map<String, String> map = HadoopUtils.parseYarnLog(acornRequest.getApplicationId());
        String dataLog = map.get("taskmanager.out");
        String jsonStr = "[" + dataLog + "]";
        if (Strings.isEmpty(dataLog)) {
            return AcornData.builder().build();
        }

        List<String> columnNames = new ArrayList<>();
        String managerLog = map.get("taskmanager.log");
        Pattern pattern = compile("(table=\\[default_catalog.default_database.out_table\\], fields=\\[).+?]", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(managerLog);
        if(matcher.find()){
            String trim = matcher.group().trim().replace(" ", "");
            columnNames = Arrays.asList(trim.substring(60, trim.length() - 1).split(","));
        }

        return AcornData.builder().dataList(JSON.parseArray(jsonStr)).columnNames(columnNames).build();
    }
}
