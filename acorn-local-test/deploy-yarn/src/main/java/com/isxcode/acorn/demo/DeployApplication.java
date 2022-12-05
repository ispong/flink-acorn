package com.isxcode.acorn.demo;

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
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.yarn.api.records.ApplicationId;
import org.apache.hadoop.yarn.api.records.ApplicationReport;
import org.apache.hadoop.yarn.client.api.YarnClient;
import org.apache.hadoop.yarn.conf.YarnConfiguration;
import org.apache.hadoop.yarn.exceptions.YarnException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.apache.flink.configuration.CoreOptions.DEFAULT_PARALLELISM;

@RequestMapping
@RestController
@SpringBootApplication
public class DeployApplication {

	public static void main(String[] args) {
        SpringApplication.run(DeployApplication.class, args);
	}

    @GetMapping("/hello")
    public String hello() {

        System.out.println("hello world");
        return "hello world";
    }

    @GetMapping("/deploy")
    public String deploy() throws Exception {

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
            .setJarFile(new File("/home/ispong/flink-acorn/demos/sql-job/target/sql-job-0.0.1.jar"))
            .setEntryPointClassName("com.isxcode.acorn.job.SqlJob")
            .setArguments("hello")
            .setUserClassPaths(classpathFiles)
            .setSavepointRestoreSettings(SavepointRestoreSettings.none())
            .build();

        // 核心：初始化JobGraph
        JobGraph jobGraph = PackagedProgramUtils.createJobGraph(
            program, flinkConfig, flinkConfig.getInteger(DEFAULT_PARALLELISM), false);

        // 提交部署作业
        ClusterClientProvider<ApplicationId> provider = descriptor.deployJobCluster(clusterSpecification, jobGraph, true);

        // 返回yarn中应用applicationId
        return provider.getClusterClient().getClusterId().toString();
    }

    @GetMapping("/getReport")
    public String getApplicationReport(@RequestParam String applicationId) throws IOException, YarnException {

        // 初始化yarnConfig和yarnClient
        YarnClient yarnClient = YarnClient.createYarnClient();
        YarnConfiguration yarnConfig = new YarnConfiguration();
        yarnClient.init(yarnConfig);
        yarnClient.start();

        // 查看应用报告
        ApplicationReport applicationReport = yarnClient.getApplicationReport(ApplicationId.fromString(applicationId));
        return applicationReport.getYarnApplicationState().toString();
    }

    @GetMapping("/kill")
    public void kill(@RequestParam String applicationId) throws IOException, YarnException {

        // 初始化yarnConfig和yarnClient
        YarnClient yarnClient = YarnClient.createYarnClient();
        YarnConfiguration yarnConfig = new YarnConfiguration();
        yarnClient.init(yarnConfig);
        yarnClient.start();

        // 杀死应用
        yarnClient.killApplication(ApplicationId.fromString(applicationId));
    }
}
