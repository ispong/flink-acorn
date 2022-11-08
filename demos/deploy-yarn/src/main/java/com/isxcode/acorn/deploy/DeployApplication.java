package com.isxcode.acorn.deploy;

import org.apache.flink.client.deployment.ClusterSpecification;
import org.apache.flink.client.program.ClusterClientProvider;
import org.apache.flink.client.program.PackagedProgram;
import org.apache.flink.client.program.PackagedProgramUtils;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.configuration.GlobalConfiguration;
import org.apache.flink.runtime.jobgraph.JobGraph;
import org.apache.flink.yarn.YarnClientYarnClusterInformationRetriever;
import org.apache.flink.yarn.YarnClusterDescriptor;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.yarn.api.records.ApplicationId;
import org.apache.hadoop.yarn.client.api.YarnClient;
import org.apache.hadoop.yarn.conf.YarnConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
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

    @GetMapping("/demo")
    public String demo() {

        System.out.println(Thread.currentThread().getContextClassLoader());
        System.out.println("hello world");
        return "hello world";
    }

    @GetMapping("/deploy")
    public String deploy() throws Exception {

        // init yarn config
        YarnClient yarnClient = YarnClient.createYarnClient();
        YarnConfiguration yarnConfig = new YarnConfiguration();
        yarnClient.init(yarnConfig);
        yarnClient.start();

        // init flink config
        Configuration flinkConfig = GlobalConfiguration.loadConfiguration("/opt/flink/conf");

        // flink cluster resource
        ClusterSpecification clusterSpecification = new ClusterSpecification.ClusterSpecificationBuilder()
            .setMasterMemoryMB(1024)
            .setTaskManagerMemoryMB(1024)
            .setSlotsPerTaskManager(1)
            .createClusterSpecification();

        // The descriptor with deployment information for deploying a Flink cluster on Yarn.
        YarnClusterDescriptor descriptor = new YarnClusterDescriptor(
            flinkConfig, yarnConfig, yarnClient, YarnClientYarnClusterInformationRetriever.create(yarnClient), false);
        descriptor.setLocalJarPath(new Path("/opt/flink/lib/flink-dist_2.12-1.14.0.jar"));
        List<File> shipFiles = new ArrayList<>();
        File[] jars = new File("/opt/flink/lib/").listFiles();
        if (jars != null) {
            shipFiles.addAll(Arrays.asList(jars));
        }
        descriptor.addShipFiles(shipFiles);

        // The packaged program to be executed on the cluster.
        List<URL> classpathFiles = new ArrayList<>();
        classpathFiles.add(new File("/opt/flink/lib/flink-connector-jdbc_2.12-1.14.0.jar").toURI().toURL());

        PackagedProgram program = PackagedProgram.newBuilder()
            .setJarFile(new File("/home/ispong/flink-acorn/demos/sql-job/target/sql-job-0.0.1.jar"))
            .setEntryPointClassName("com.isxcode.acorn.job.SqlJob")
            .setArguments("hello")
            .setUserClassPaths(classpathFiles)
            .build();

        // The job graph to be deployed on the cluster.
        JobGraph jobGraph = PackagedProgramUtils.createJobGraph(
            program, flinkConfig, flinkConfig.getInteger(DEFAULT_PARALLELISM), false);

        // Deploys a per-job cluster with the given job on the cluster.
        ClusterClientProvider<ApplicationId> provider = descriptor.deployJobCluster(clusterSpecification, jobGraph, true);
        System.out.println("applicationId:" + provider.getClusterClient().getClusterId().toString());

        return "deploy success";
    }


}
