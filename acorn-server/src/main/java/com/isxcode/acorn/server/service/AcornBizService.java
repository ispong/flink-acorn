package com.isxcode.acorn.server.service;

import com.isxcode.acorn.common.pojo.AcornRequest;
import com.isxcode.acorn.common.pojo.dto.AcornData;
import com.isxcode.acorn.common.properties.AcornProperties;
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

        // 初始化yarnConfig和yarnClient
        YarnClient yarnClient = YarnClient.createYarnClient();
        YarnConfiguration yarnConfig = new YarnConfiguration();
        yarnClient.init(yarnConfig);
        yarnClient.start();

        // 初始化flinkConfig
        Configuration flinkConfig = GlobalConfiguration.loadConfiguration("/opt/flink/conf");

        // 参考命令 yarn-session.sh --jobManagerMemory 4096 --taskManagerMemory 4096 --name isxcode-flink-cluster --slots 4 -d
        // 配置一个flink在yarn集群中分配的资源和配置
        ClusterSpecification clusterSpecification = new ClusterSpecification.ClusterSpecificationBuilder()
            .setMasterMemoryMB(1024)
            .setTaskManagerMemoryMB(1024)
            .setSlotsPerTaskManager(1)
            .createClusterSpecification();

        // 初始化一个 flink per-job on yarn连接
        YarnClusterDescriptor descriptor = new YarnClusterDescriptor(
            flinkConfig, yarnConfig, yarnClient, YarnClientYarnClusterInformationRetriever.create(yarnClient), false);

        // 指定flink-dist文件位置，即使用那个flink的客户端包在yarn中运行
        descriptor.setLocalJarPath(new Path("/opt/flink/lib/flink-dist_2.12-1.14.0.jar"));

        // 添加shipFiles辅助，flink-dist可以在yarn环境中可以正常启动
        List<File> shipFiles = new ArrayList<>();
        File[] jars = new File("/opt/flink/lib/").listFiles();
        if (jars != null) {
            shipFiles.addAll(Arrays.asList(jars));
        }
        descriptor.addShipFiles(shipFiles);

        // 特殊驱动依赖可以动态添加，比如jdbc等，直接添加打flink/lib下是不能识别的，需要手动添加
        List<URL> classpathFiles = new ArrayList<>();
        classpathFiles.add(new File("/opt/flink/lib/flink-connector-jdbc_2.12-1.14.0.jar").toURI().toURL());

        // 执行需要提交作业的程序，提交哪个jar包，哪个入口函数，需要传递的参数，额外的驱动,信息存储路径
        PackagedProgram program = PackagedProgram.newBuilder()
            .setJarFile(new File("/opt/acorn/plugins/acorn-sql-plugin.jar"))
            .setEntryPointClassName("com.isxcode.acorn.plugin.sql.SqlJob")
            .setArguments(acornRequest.getSql())
            .setUserClassPaths(classpathFiles)
            .setSavepointRestoreSettings(SavepointRestoreSettings.none())
            .build();

        // 核心：初始化JobGraph
        JobGraph jobGraph = PackagedProgramUtils.createJobGraph(
            program, flinkConfig, flinkConfig.getInteger(DEFAULT_PARALLELISM), false);

        // 提交部署作业
        ClusterClientProvider<ApplicationId> provider = descriptor.deployJobCluster(clusterSpecification, jobGraph, true);

        return AcornData.builder().applicationId(provider.getClusterClient().getClusterId().toString()).build();
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
