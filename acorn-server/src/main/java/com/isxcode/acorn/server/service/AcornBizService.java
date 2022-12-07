package com.isxcode.acorn.server.service;

import com.isxcode.acorn.api.exception.AcornException;
import com.isxcode.acorn.api.pojo.AcornRequest;
import com.isxcode.acorn.api.pojo.dto.AcornData;
import com.isxcode.acorn.api.pojo.flink.JobExceptions;
import com.isxcode.acorn.api.pojo.flink.JobStatus;
import com.isxcode.acorn.api.properties.AcornProperties;
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
import org.apache.flink.yarn.YarnClusterDescriptor;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.yarn.api.records.ApplicationId;
import org.apache.hadoop.yarn.api.records.ApplicationReport;
import org.apache.hadoop.yarn.api.records.FinalApplicationStatus;
import org.apache.hadoop.yarn.api.records.YarnApplicationState;
import org.apache.hadoop.yarn.client.api.YarnClient;
import org.apache.hadoop.yarn.conf.YarnConfiguration;
import org.apache.hadoop.yarn.exceptions.YarnException;
import org.apache.logging.log4j.util.Strings;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.apache.flink.configuration.CoreOptions.DEFAULT_PARALLELISM;

@Slf4j
@Service
@RequiredArgsConstructor
public class AcornBizService {

    private final AcornProperties acornProperties;

    public AcornData executeSql(AcornRequest acornRequest) throws IOException, ProgramInvocationException, ClusterDeploymentException {

        // 获取hadoop的配置文件目录
        String hadoopConfDir = System.getenv("YARN_CONF_DIR");
        String flinkHomeDir = System.getenv("FLINK_HOME");
        String acornHomeDir = System.getenv("ACORN_HOME");

        // 读取配置yarn-site.yml文件
        org.apache.hadoop.conf.Configuration hadoopConf = new org.apache.hadoop.conf.Configuration(false);
        java.nio.file.Path path = Paths.get(hadoopConfDir + File.separator + "yarn-site.xml");
        hadoopConf.addResource(Files.newInputStream(path));
        YarnConfiguration yarnConfig = new YarnConfiguration(hadoopConf);

        // 获取yarn客户端
        YarnClient yarnClient = YarnClient.createYarnClient();
        yarnClient.init(yarnConfig);
        yarnClient.start();

        Configuration flinkConfig = GlobalConfiguration.loadConfiguration(flinkHomeDir + "/conf");

        ClusterSpecification clusterSpecification = new ClusterSpecification.ClusterSpecificationBuilder().setMasterMemoryMB(acornRequest.getMasterMemoryMB()).setTaskManagerMemoryMB(acornRequest.getTaskManagerMemoryMB()).setSlotsPerTaskManager(acornRequest.getSlotsPerTaskManager()).createClusterSpecification();

        YarnClusterDescriptor descriptor = new YarnClusterDescriptor(flinkConfig, yarnConfig, yarnClient, YarnClientYarnClusterInformationRetriever.create(yarnClient), false);

        File[] jars = new File(flinkHomeDir + "/lib/").listFiles();
        List<File> shipFiles = new ArrayList<>();
        List<URL> classpathFiles = new ArrayList<>();
        if (jars != null) {
            for (File jar : jars) {
                if (jar.getName().contains("flink-dist")) {
                    descriptor.setLocalJarPath(new Path(jar.toURI().toURL().toString()));
                } else if (jar.getName().contains("flink-connector")) {
                    classpathFiles.add(jar.toURI().toURL());
                } else {
                    shipFiles.add(jar);
                }
            }
        }
        descriptor.addShipFiles(shipFiles);

        PackagedProgram program;
        if (!Strings.isEmpty(acornRequest.getSql())) {
//D:\isxcode\flink-acorn\acorn-plugins\acorn-sql-plugin\target\
            program = PackagedProgram.newBuilder().setJarFile(new File(acornHomeDir+"/plugins/acorn-sql-plugin.jar")).setEntryPointClassName("com.isxcode.acorn.plugin.sql.SqlJob").setArguments(acornRequest.getSql()).setUserClassPaths(classpathFiles).setSavepointRestoreSettings(SavepointRestoreSettings.none()).build();
        } else {
            program = PackagedProgram.newBuilder().setJarFile(new File(acornRequest.getPluginJarPath())).setEntryPointClassName(acornRequest.getPluginMainClass()).setArguments(acornRequest.getPluginArguments().toString()).setUserClassPaths(classpathFiles).setSavepointRestoreSettings(SavepointRestoreSettings.none()).build();
        }

        JobGraph jobGraph = PackagedProgramUtils.createJobGraph(program, flinkConfig, flinkConfig.getInteger(DEFAULT_PARALLELISM), false);

        // 将本地jar包提交到yarn集群
        ClusterClientProvider<ApplicationId> provider = descriptor.deployJobCluster(clusterSpecification, jobGraph, true);

        String applicationId = provider.getClusterClient().getClusterId().toString();
        String flinkJobId = jobGraph.getJobID().toString();

        return AcornData.builder().applicationId(applicationId).flinkJobId(flinkJobId).build();
    }

    public AcornData getYarnLog(AcornRequest acornRequest) throws IOException {
      // 获取hadoop的配置文件目录
        String hadoopConfDir = System.getenv("YARN_CONF_DIR");

        // 读取配置yarn-site.yml文件
        org.apache.hadoop.conf.Configuration hadoopConf = new org.apache.hadoop.conf.Configuration(false);
        java.nio.file.Path path = Paths.get(hadoopConfDir + File.separator + "yarn-site.xml");
        hadoopConf.addResource(Files.newInputStream(path));
        YarnConfiguration yarnConfig = new YarnConfiguration(hadoopConf);

        // 获取yarn客户端
        YarnClient yarnClient = YarnClient.createYarnClient();
        yarnClient.init(yarnConfig);
        yarnClient.start();

        // 获取 yarn.resourcemanager.webapp.address 配置
        String managerAddress = hadoopConf.get("yarn.resourcemanager.webapp.address");
        if (managerAddress == null) {
            throw new AcornException("50010", "请在yarn-site.xml配置yarn.resourcemanager.webapp.address属性");
        }

        // 获取应用的信息
        Map appInfoMap;
        try {
            appInfoMap = new RestTemplate().getForObject("http://" + managerAddress + "/ws/v1/cluster/apps/" + acornRequest.getApplicationId(), Map.class);
        }catch (Exception e){
            log.error(e.getMessage());
            throw new AcornException("50011", "无法正常访问yarn集群:" + managerAddress);
        }

        // 获取amContainerLogs的Url
        Map<String, Map<String, Object>> appMap = (Map<String, Map<String, Object>>) appInfoMap.get("app");
        String amContainerLogsUrl = String.valueOf(appMap.get("amContainerLogs"));

        // 通过url获取html的内容
        Document doc = Jsoup.connect(amContainerLogsUrl).get();
        Elements el = doc.getElementsByClass("content");

        // 获取jobManager日志内容url
        Elements afs = el.get(0).select("a[href]");
        String jobManagerLogUrl = afs.attr("href");
        String jobHistoryAddress = hadoopConf.get("mapreduce.jobhistory.webapp.address");

        // 使用Jsoup爬取jobManager的日志
        Document managerDoc = Jsoup.connect("http://" + jobHistoryAddress + jobManagerLogUrl).get();

        return AcornData.builder().yarnLogs(Arrays.asList(managerDoc.body().text().split("\n"))).build();
    }

    public AcornData getYarnStatus(AcornRequest acornRequest) throws IOException, YarnException {

        YarnClient yarnClient = YarnClient.createYarnClient();
        YarnConfiguration yarnConfig = new YarnConfiguration();
        yarnClient.init(yarnConfig);
        yarnClient.start();

        ApplicationReport applicationReport = yarnClient.getApplicationReport(ApplicationId.fromString(acornRequest.getApplicationId()));
        FinalApplicationStatus finalApplicationStatus = applicationReport.getFinalApplicationStatus();
        YarnApplicationState yarnApplicationState = applicationReport.getYarnApplicationState();

        return AcornData.builder().finalStatus(finalApplicationStatus.name()).yarnState(yarnApplicationState.name()).build();
    }

    public AcornData killYarn(AcornRequest acornRequest) throws IOException, YarnException {

        YarnClient yarnClient = YarnClient.createYarnClient();
        YarnConfiguration yarnConfig = new YarnConfiguration();
        yarnClient.init(yarnConfig);
        yarnClient.start();

        yarnClient.killApplication(ApplicationId.fromString(acornRequest.getApplicationId()));

        return AcornData.builder().build();
    }

    public AcornData getJobStatus(AcornRequest acornRequest) throws IOException, YarnException {

        ResponseEntity<JobStatus> response = new RestTemplate().getForEntity(acornProperties.getJobHistoryUrl() + "/jobs/" + acornRequest.getJobId(), JobStatus.class);

        return AcornData.builder().jobStatus(response.getBody()).build();
    }

    public AcornData getJobExceptions(AcornRequest acornRequest) {

        ResponseEntity<JobExceptions> response = new RestTemplate().getForEntity(acornProperties.getJobHistoryUrl() + "/jobs/" + acornRequest.getJobId() + "/exceptions", JobExceptions.class);

        return AcornData.builder().jobExceptions(response.getBody()).build();
    }

}
