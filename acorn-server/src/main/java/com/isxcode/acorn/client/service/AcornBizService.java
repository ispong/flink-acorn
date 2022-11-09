package com.isxcode.acorn.client.service;

import com.isxcode.acorn.common.pojo.AcornRequest;
import com.isxcode.acorn.common.pojo.dto.AcornData;
import com.isxcode.acorn.common.properties.AcornProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.client.deployment.ClusterDeploymentException;
import org.apache.flink.client.program.ProgramInvocationException;
import org.apache.hadoop.fs.Path;
import org.apache.flink.client.deployment.ClusterSpecification;
import org.apache.flink.client.program.ClusterClientProvider;
import org.apache.flink.client.program.PackagedProgram;
import org.apache.flink.client.program.PackagedProgramUtils;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.configuration.GlobalConfiguration;
import org.apache.flink.runtime.jobgraph.JobGraph;
import org.apache.flink.runtime.jobgraph.SavepointRestoreSettings;
import org.apache.flink.yarn.YarnClientYarnClusterInformationRetriever;
import org.apache.flink.yarn.YarnClusterDescriptor;
import org.apache.hadoop.yarn.api.records.ApplicationId;
import org.apache.hadoop.yarn.client.api.YarnClient;
import org.apache.hadoop.yarn.conf.YarnConfiguration;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
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

        Configuration flinkConfig = GlobalConfiguration.loadConfiguration(acornProperties.getFlinkConfDir());

        ClusterSpecification clusterSpecification = new ClusterSpecification.ClusterSpecificationBuilder()
            .setMasterMemoryMB(acornRequest.getMasterMemoryMB())
            .setTaskManagerMemoryMB(acornRequest.getTaskManagerMemoryMB())
            .setSlotsPerTaskManager(acornRequest.getSlotsPerTaskManager())
            .createClusterSpecification();

        YarnClusterDescriptor descriptor = new YarnClusterDescriptor(
            flinkConfig, yarnConfig, yarnClient, YarnClientYarnClusterInformationRetriever.create(yarnClient), false);

        descriptor.setLocalJarPath(new Path(acornProperties.getFlinkDistPath()));

        List<File> shipFiles = new ArrayList<>();
        File[] jars = new File(acornProperties.getFlinkLibDir()).listFiles();
        if (jars != null) {
            shipFiles.addAll(Arrays.asList(jars));
        }
        descriptor.addShipFiles(shipFiles);

//        List<URL> classpathFiles = new ArrayList<>();
//        classpathFiles.add(new File("/opt/flink/lib/flink-connector-jdbc_2.12-1.14.0.jar").toURI().toURL());

        PackagedProgram program = PackagedProgram.newBuilder()
            .setJarFile(new File("/opt/acorn/plugins/acorn-sql-plugin.jar"))
            .setEntryPointClassName("com.isxcode.acorn.client.sql.SqlJob")
            .setArguments(acornRequest.getSql())
//            .setUserClassPaths(classpathFiles)
            .setSavepointRestoreSettings(SavepointRestoreSettings.none())
            .build();

        JobGraph jobGraph = PackagedProgramUtils.createJobGraph(
            program, flinkConfig, flinkConfig.getInteger(DEFAULT_PARALLELISM), false);

        ClusterClientProvider<ApplicationId> provider = descriptor.deployJobCluster(clusterSpecification, jobGraph, true);

        String applicationId = provider.getClusterClient().getClusterId().toString();

        return AcornData.builder().applicationId(applicationId).build();
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
