package com.isxcode.acorn.deploy;

import org.apache.flink.client.deployment.ClusterDeploymentException;
import org.apache.flink.client.deployment.ClusterSpecification;
import org.apache.flink.client.program.ClusterClientProvider;
import org.apache.flink.client.program.PackagedProgram;
import org.apache.flink.client.program.PackagedProgramUtils;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.configuration.GlobalConfiguration;
import org.apache.flink.runtime.executiongraph.DefaultExecutionGraphBuilder;
import org.apache.flink.runtime.jobgraph.JobGraph;
import org.apache.flink.runtime.jobgraph.JobVertex;
import org.apache.flink.shaded.guava30.com.google.common.graph.GraphBuilder;
import org.apache.flink.yarn.YarnClientYarnClusterInformationRetriever;
import org.apache.flink.yarn.YarnClusterDescriptor;
import org.apache.flink.yarn.configuration.YarnConfigOptions;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.yarn.api.records.ApplicationId;
import org.apache.hadoop.yarn.api.records.ApplicationSubmissionContext;
import org.apache.hadoop.yarn.api.records.Priority;
import org.apache.hadoop.yarn.api.records.Resource;
import org.apache.hadoop.yarn.client.api.YarnClient;
import org.apache.hadoop.yarn.client.api.YarnClientApplication;
import org.apache.hadoop.yarn.conf.YarnConfiguration;
import org.apache.hadoop.yarn.exceptions.YarnException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.apache.flink.configuration.CoreOptions.DEFAULT_PARALLELISM;

@RequestMapping
@RestController
@SpringBootApplication
public class DeployApplication {

	public static void main(String[] args) {
        SpringApplication.run(DeployApplication.class, args);
	}

    @GetMapping("/demo")
    public String demo() {

        System.out.println("hello world");
        return "hello world";
    }

    @GetMapping("/deploy")
    public String deploy() throws Exception {

        // 初始化yarnClient
        YarnConfiguration yarnConfig = new YarnConfiguration();
        YarnClient yarnClient = YarnClient.createYarnClient();
        yarnClient.init(yarnConfig);
        yarnClient.start();

        // 配置容器资源
        YarnClientApplication application = yarnClient.createApplication();
        ApplicationSubmissionContext appContext = application.getApplicationSubmissionContext();
        appContext.setApplicationName("spring-demo");
        appContext.setResource(Resource.newInstance(1024, 1));
        appContext.setPriority(Priority.newInstance(0));
        appContext.setQueue("default");
        appContext.setApplicationType("Flink Sql Job");

        // 初始化flinkConfig
        Configuration flinkConfig = GlobalConfiguration.loadConfiguration("/opt/flink/conf");
        flinkConfig.setString(YarnConfigOptions.APPLICATION_QUEUE, "default");

        // 配置yarn job的资源分配
        ClusterSpecification clusterSpecification = new ClusterSpecification.ClusterSpecificationBuilder()
            .setMasterMemoryMB(1024)
            .setTaskManagerMemoryMB(1024)
            .setSlotsPerTaskManager(1)
            .createClusterSpecification();

        // 配置flink yarn job环境
        YarnClusterDescriptor descriptor = new YarnClusterDescriptor(flinkConfig, yarnConfig, yarnClient, YarnClientYarnClusterInformationRetriever.create(yarnClient), false);

        List<File> shipFiles = new ArrayList<>();
        File[] jars = new File("/opt/flink/lib").listFiles();
        if (jars != null) {
            Collections.addAll(shipFiles, jars);
        }
        descriptor.addShipFiles(shipFiles);
        descriptor.setLocalJarPath(new Path("/home/ispong/flink-acorn/demos/sql-job/target/sql-job-0.0.1.jar"));

        // 部署作业

        ClusterClientProvider<ApplicationId> provider = descriptor.deployJobCluster(clusterSpecification, buildJobGraph(new String[0], flinkConfig), true);

        // 打印应用信息
        System.out.println("applicationId:" + provider.getClusterClient().getClusterId().toString());

        // "java -jar xxx.jar org.apache.flink.yarn.entrypoint.YarnJobClusterEntrypoint --job-classname com.isxcode.acorn.deploy.DeployApplication --job-id 1d9b9b9b-1b1b-1b1b-1b1b-1b1b1b1b1b1b --job-artifacts /tmp/flink-dist-1.12.0-bin_2.12.tgz#flink-dist-1.12.0 --parallelism 1 --detached --jobmanager-memory 1024m --jobmanager-cpu 1 --taskmanager-memory 1024m --taskmanager-cpu 1 --taskmanager-num 1 --dynamicPropertiesEncoded e30="
        return "deploy success";
    }


    public static JobGraph buildJobGraph(String[] programArgs, Configuration flinkConf) throws Exception {

        File jarFile = new File("/home/ispong/flink-acorn/demos/sql-job/target/sql-job-0.0.1.jar");

        PackagedProgram program =
            PackagedProgram.newBuilder()
                .setJarFile(jarFile)
                .setEntryPointClassName("com.isxcode.acorn.job.SqlJob.main")
                .setConfiguration(flinkConf)
                .setArguments(programArgs)
                .build();

        JobGraph jobGraph =
            PackagedProgramUtils.createJobGraph(
                program,
                flinkConf,
                flinkConf.getInteger(DEFAULT_PARALLELISM),
                false);

        List<URL> pluginClassPath =
            jobGraph.getUserArtifacts().entrySet().stream()
                .filter(tmp -> tmp.getKey().startsWith("class_path"))
                .map(tmp -> new File(tmp.getValue().filePath))
                .map(
                    file -> {
                        try {
                            return file.toURI().toURL();
                        } catch (MalformedURLException e) {
                            System.out.println(e.getMessage());
                        }
                        return null;
                    })
                .collect(Collectors.toList());
        jobGraph.setClasspaths(pluginClassPath);
        return jobGraph;
    }

}
