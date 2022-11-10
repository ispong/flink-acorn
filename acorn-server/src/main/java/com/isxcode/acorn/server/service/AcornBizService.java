package com.isxcode.acorn.server.service;

import com.isxcode.acorn.common.pojo.AcornRequest;
import com.isxcode.acorn.common.pojo.dto.AcornData;
import com.isxcode.acorn.common.pojo.flink.JobExceptions;
import com.isxcode.acorn.common.pojo.flink.JobStatus;
import com.isxcode.acorn.common.properties.AcornProperties;
import com.isxcode.acorn.common.utils.CommandUtils;
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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.apache.flink.configuration.CoreOptions.DEFAULT_PARALLELISM;

@Slf4j
@Service
@RequiredArgsConstructor
public class AcornBizService {

    private final AcornProperties acornProperties;

    public AcornData executeSql(AcornRequest acornRequest) throws MalformedURLException, ProgramInvocationException, ClusterDeploymentException {

        YarnClient yarnClient = YarnClient.createYarnClient();
        YarnConfiguration yarnConfig = new YarnConfiguration();
        yarnClient.init(yarnConfig);
        yarnClient.start();

        Configuration flinkConfig = GlobalConfiguration.loadConfiguration(acornProperties.getFlinkDir() + "/conf");

        ClusterSpecification clusterSpecification = new ClusterSpecification.ClusterSpecificationBuilder()
            .setMasterMemoryMB(acornRequest.getMasterMemoryMB())
            .setTaskManagerMemoryMB(acornRequest.getTaskManagerMemoryMB())
            .setSlotsPerTaskManager(acornRequest.getSlotsPerTaskManager())
            .createClusterSpecification();

        YarnClusterDescriptor descriptor = new YarnClusterDescriptor(
            flinkConfig, yarnConfig, yarnClient, YarnClientYarnClusterInformationRetriever.create(yarnClient), false);

        File[] jars = new File(acornProperties.getFlinkDir() + "/lib/").listFiles();
        List<File> shipFiles = new ArrayList<>();
        List<URL> classpathFiles = new ArrayList<>();
        if (jars != null) {
            for (File jar : jars) {
                if (jar.toURI().toURL().toString().contains("flink-dist")) {
                    descriptor.setLocalJarPath(new Path(jar.toURI().toURL().toString()));
                } else if (jar.toURI().toURL().toString().contains("flink-connector")) {
                    classpathFiles.add(jar.toURI().toURL());
                } else {
                    shipFiles.add(jar);
                }
            }
        }
        descriptor.addShipFiles(shipFiles);

        PackagedProgram program;
        if (!Strings.isEmpty(acornRequest.getSql())) {
            program = PackagedProgram.newBuilder()
                .setJarFile(new File("/opt/acorn/plugins/acorn-sql-plugin.jar"))
                .setEntryPointClassName("com.isxcode.acorn.plugin.sql.SqlJob")
                .setArguments(acornRequest.getSql())
                .setUserClassPaths(classpathFiles)
                .setSavepointRestoreSettings(SavepointRestoreSettings.none())
                .build();
        } else {
            program = PackagedProgram.newBuilder()
                .setJarFile(new File(acornRequest.getPluginJarPath()))
                .setEntryPointClassName(acornRequest.getPluginMainClass())
                .setArguments(acornRequest.getPluginArguments().toString())
                .setUserClassPaths(classpathFiles)
                .setSavepointRestoreSettings(SavepointRestoreSettings.none())
                .build();
        }

        JobGraph jobGraph = PackagedProgramUtils.createJobGraph(
            program, flinkConfig, flinkConfig.getInteger(DEFAULT_PARALLELISM), false);

        ClusterClientProvider<ApplicationId> provider = descriptor.deployJobCluster(clusterSpecification, jobGraph, true);

        String applicationId = provider.getClusterClient().getClusterId().toString();
        String flinkJobId = jobGraph.getJobID().toString();

        return AcornData.builder().applicationId(applicationId).flinkJobId(flinkJobId).build();
    }

    public AcornData getYarnLog(AcornRequest acornRequest) throws IOException, YarnException {

        String getLogCommand = "yarn logs -applicationId " + acornRequest.getApplicationId();

        String yarnLog = CommandUtils.executeBackCommand(getLogCommand, 5000);
        return AcornData.builder().yarnLog(yarnLog).build();
    }

    public AcornData getYarnStatus(AcornRequest acornRequest) throws IOException, YarnException {

        YarnClient yarnClient = YarnClient.createYarnClient();
        YarnConfiguration yarnConfig = new YarnConfiguration();
        yarnClient.init(yarnConfig);
        yarnClient.start();

        ApplicationReport applicationReport = yarnClient.getApplicationReport(ApplicationId.fromString(acornRequest.getApplicationId()));
        FinalApplicationStatus finalApplicationStatus = applicationReport.getFinalApplicationStatus();
        YarnApplicationState yarnApplicationState = applicationReport.getYarnApplicationState();

        return AcornData.builder()
            .finalStatus(finalApplicationStatus.name())
            .yarnState(yarnApplicationState.name())
            .build();
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
