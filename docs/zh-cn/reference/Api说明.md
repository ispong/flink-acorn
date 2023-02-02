## - 执行FlinkSql

```java
public AcornResponse executeFlinkSql() {

    String flinkSql = "CREATE TABLE from_table(\n" +
    "    username STRING,\n" +
    "    age INT\n" +
    ") WITH (\n" +
    "    'connector'='jdbc',\n" +
    "    'url'='jdbc:mysql://isxcode:30306/ispong_db',\n" +
    "    'table-name'='users',\n" +
    "    'driver'='com.mysql.cj.jdbc.Driver',\n" +
    "    'username'='root',\n" +
    "    'password'='ispong123');" +
    "CREATE TABLE to_table(\n" +
    "    username STRING,\n" +
    "    age INT\n" +
    ") WITH (\n" +
    "    'connector'='jdbc',\n" +
    "    'url'='jdbc:mysql://isxcode:30306/ispong_db',\n" +
    "    'table-name'='users_sink',\n" +
    "    'driver'='com.mysql.cj.jdbc.Driver',\n" +
    "    'username'='root',\n" +
    "    'password'='ispong123');" +
    "insert into to_table select username, age from from_table";

    return acornTemplate.build().sql(flinkSql).deploy();
}
```

```json
{
    "code": "200",
    "msg": "部署成功",
    "data": {
        "flinkJobId": "092cdca855b719303e826e690c483c73",
        "applicationId": "application_1671005804173_0007",
        "webInterfaceURL": "http://isxcode:45037"
    }
}
```

## - 获取Yarn容器状态

```java
public AcornResponse getYarnStatus(@RequestParam String applicationId) {

    return acornTemplate.build().applicationId(applicationId).getYarnStatus();
}
```

```json
{
    "code": "200",
    "msg": "获取yarn作业状态成功",
    "data": {
        "finalStatus": "SUCCEEDED",
        "yarnState": "FINISHED"
    }
}
```

## - 获取JobManager日志

```java
public AcornResponse getJobManagerLog(@RequestParam String applicationId) {

    return acornTemplate.build().applicationId(applicationId).getJobManagerLog();
}
```

```json
{
  "code": "200",
  "msg": "获取JobManager日志成功",
  "data": {
    "jobManagerLogs": [
      "2022-12-30 12:19:13,983 INFO  org.apache.flink.runtime.entrypoint.ClusterEntrypoint        [] - --------------------------------------------------------------------------------",
      "2022-12-30 12:19:13,987 INFO  org.apache.flink.runtime.entrypoint.ClusterEntrypoint        [] -  Starting YarnJobClusterEntrypoint (Version: 1.14.0, Scala: 2.12, Rev:460b386, Date:2021-09-22T08:39:40+02:00)",
      "2022-12-30 12:19:13,987 INFO  org.apache.flink.runtime.entrypoint.ClusterEntrypoint        [] -  OS current user: ispong",
      "2022-12-30 12:19:14,139 WARN  org.apache.hadoop.util.NativeCodeLoader                      [] - Unable to load native-hadoop library for your platform... using builtin-java classes where applicable",
      "2022-12-30 12:19:14,173 INFO  org.apache.flink.runtime.entrypoint.ClusterEntrypoint        [] -  Current Hadoop/Kerberos user: ispong",
      "2022-12-30 12:19:14,173 INFO  org.apache.flink.runtime.entrypoint.ClusterEntrypoint        [] -  JVM: OpenJDK 64-Bit Server VM - Red Hat, Inc. - 1.8/25.352-b08",
      "2022-12-30 12:19:14,173 INFO  org.apache.flink.runtime.entrypoint.ClusterEntrypoint        [] -  Maximum heap size: 981 MiBytes",
      "2022-12-30 12:19:14,173 INFO  org.apache.flink.runtime.entrypoint.ClusterEntrypoint        [] -  JAVA_HOME: /usr/lib/jvm/java-1.8.0-openjdk",
      "2022-12-30 12:19:14,175 INFO  org.apache.flink.runtime.entrypoint.ClusterEntrypoint        [] -  Hadoop version: 3.2.4",
      "2022-12-30 12:19:14,175 INFO  org.apache.flink.runtime.entrypoint.ClusterEntrypoint        [] -  JVM Options:",
      "2022-12-30 12:19:14,175 INFO  org.apache.flink.runtime.entrypoint.ClusterEntrypoint        [] -     -Xmx1073741824",
      "2022-12-30 12:19:14,175 INFO  org.apache.flink.runtime.entrypoint.ClusterEntrypoint        [] -     -Xms1073741824",
      "2022-12-30 12:19:14,175 INFO  org.apache.flink.runtime.entrypoint.ClusterEntrypoint        [] -     -XX:MaxMetaspaceSize=268435456",
      "2022-12-30 12:19:14,175 INFO  org.apache.flink.runtime.entrypoint.ClusterEntrypoint        [] -     -XX:MaxMetaspaceSize=268435400",
      "2022-12-30 12:19:14,175 INFO  org.apache.flink.runtime.entrypoint.ClusterEntrypoint        [] -     -Dlog.file=/data/hadoop/hadoop-3.2.4/logs/userlogs/application_1672365636481_0011/container_1672365636481_0011_01_000001/jobmanager.log",
      "2022-12-30 12:19:14,175 INFO  org.apache.flink.runtime.entrypoint.ClusterEntrypoint        [] -     -Dlog4j.configuration=file:log4j.properties",
      "2022-12-30 12:19:14,175 INFO  org.apache.flink.runtime.entrypoint.ClusterEntrypoint        [] -     -Dlog4j.configurationFile=file:log4j.properties",
      "2022-12-30 12:19:14,175 INFO  org.apache.flink.runtime.entrypoint.ClusterEntrypoint        [] -  Program Arguments:",
      "2022-12-30 12:19:14,176 INFO  org.apache.flink.runtime.entrypoint.ClusterEntrypoint        [] -     -D",
      "2022-12-30 12:19:14,176 INFO  org.apache.flink.runtime.entrypoint.ClusterEntrypoint        [] -     jobmanager.memory.off-heap.size=134217728b",
      "2022-12-30 12:19:14,176 INFO  org.apache.flink.runtime.entrypoint.ClusterEntrypoint        [] -     -D",
      "2022-12-30 12:19:14,176 INFO  org.apache.flink.runtime.entrypoint.ClusterEntrypoint        [] -     jobmanager.memory.jvm-overhead.min=201326592b",
      "2022-12-30 12:19:14,176 INFO  org.apache.flink.runtime.entrypoint.ClusterEntrypoint        [] -     -D",
      "2022-12-30 12:19:14,176 INFO  org.apache.flink.runtime.entrypoint.ClusterEntrypoint        [] -     jobmanager.memory.jvm-metaspace.size=268435456b",
      "2022-12-30 12:19:14,176 INFO  org.apache.flink.runtime.entrypoint.ClusterEntrypoint        [] -     -D",
      "2022-12-30 12:19:14,176 INFO  org.apache.flink.runtime.entrypoint.ClusterEntrypoint        [] -     jobmanager.memory.heap.size=1073741824b",
      "2022-12-30 12:19:14,176 INFO  org.apache.flink.runtime.entrypoint.ClusterEntrypoint        [] -     -D",
      "2022-12-30 12:19:14,176 INFO  org.apache.flink.runtime.entrypoint.ClusterEntrypoint        [] -     jobmanager.memory.jvm-overhead.max=201326592b",
      "2022-12-30 12:19:14,177 INFO  org.apache.flink.runtime.entrypoint.ClusterEntrypoint        [] -  Classpath: :flink-csv-1.14.0.jar:flink-json-1.14.0.jar:flink-shaded-zookeeper-3.4.14.jar:flink-table_2.12-1.14.0.jar:log4j-1.2-api-2.14.1.jar:log4j-api-2.14.1.jar:log4j-core-2.14.1.jar:log4j-slf4j-impl-2.14.1.jar:mysql-connector-java-8.0.22.jar:sql-job-0.0.1.jar:flink-dist_2.12-1.14.0.jar:job.graph:flink-conf.yaml::/opt/hadoop/etc/hadoop:/data/hadoop/hadoop-3.2.4/share/hadoop/common/hadoop-common-3.2.4-tests.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/hadoop-kms-3.2.4.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/hadoop-common-3.2.4.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/hadoop-nfs-3.2.4.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/jetty-servlet-9.4.43.v20210629.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/j2objc-annotations-1.1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/kerb-client-1.0.1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/httpclient-4.5.13.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/error_prone_annotations-2.2.0.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/jaxb-api-2.2.11.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/jetty-server-9.4.43.v20210629.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/paranamer-2.3.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/kerb-common-1.0.1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/curator-recipes-2.13.0.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/kerby-config-1.0.1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/commons-text-1.4.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/javax.activation-api-1.2.0.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/commons-net-3.6.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/commons-collections-3.2.2.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/kerb-core-1.0.1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/jackson-core-asl-1.9.13.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/accessors-smart-2.4.7.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/metrics-core-3.2.4.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/animal-sniffer-annotations-1.17.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/guava-27.0-jre.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/stax2-api-4.2.1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/javax.servlet-api-3.1.0.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/jersey-servlet-1.19.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/checker-qual-2.5.2.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/asm-5.0.4.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/jcip-annotations-1.0-1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/failureaccess-1.0.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/jersey-core-1.19.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/jackson-xc-1.9.13.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/jackson-jaxrs-1.9.13.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/reload4j-1.2.18.3.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/listenablefuture-9999.0-empty-to-avoid-conflict-with-guava.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/kerb-simplekdc-1.0.1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/commons-cli-1.2.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/curator-client-2.13.0.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/jetty-util-9.4.43.v20210629.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/jackson-annotations-2.10.5.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/jersey-json-1.19.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/woodstox-core-5.3.0.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/json-smart-2.4.7.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/jersey-server-1.19.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/jackson-core-2.10.5.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/jsr311-api-1.1.1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/jsr305-3.0.2.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/zookeeper-3.4.14.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/commons-configuration2-2.1.1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/jetty-http-9.4.43.v20210629.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/jetty-webapp-9.4.43.v20210629.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/commons-beanutils-1.9.4.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/protobuf-java-2.5.0.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/jetty-security-9.4.43.v20210629.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/slf4j-api-1.7.35.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/kerby-pkix-1.0.1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/commons-lang3-3.7.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/dnsjava-2.1.7.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/re2j-1.1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/commons-math3-3.1.1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/kerby-xdr-1.0.1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/kerb-identity-1.0.1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/kerb-admin-1.0.1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/commons-compress-1.21.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/commons-io-2.8.0.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/jetty-util-ajax-9.4.43.v20210629.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/netty-3.10.6.Final.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/jsp-api-2.1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/audience-annotations-0.5.0.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/kerb-crypto-1.0.1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/jettison-1.1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/kerby-util-1.0.1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/httpcore-4.4.13.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/spotbugs-annotations-3.1.9.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/jsch-0.1.55.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/snappy-java-1.0.5.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/jetty-io-9.4.43.v20210629.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/kerb-util-1.0.1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/jackson-mapper-asl-1.9.13.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/hadoop-annotations-3.2.4.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/kerby-asn1-1.0.1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/slf4j-reload4j-1.7.35.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/commons-logging-1.1.3.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/commons-codec-1.11.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/jul-to-slf4j-1.7.35.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/jetty-xml-9.4.43.v20210629.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/jaxb-impl-2.2.3-1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/nimbus-jose-jwt-9.8.1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/curator-framework-2.13.0.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/jackson-databind-2.10.5.1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/gson-2.9.0.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/kerb-server-1.0.1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/htrace-core4-4.1.0-incubating.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/avro-1.7.7.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/hadoop-auth-3.2.4.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/token-provider-1.0.1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/hadoop-hdfs-client-3.2.4.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/hadoop-hdfs-httpfs-3.2.4.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/hadoop-hdfs-native-client-3.2.4.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/hadoop-hdfs-rbf-3.2.4.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/hadoop-hdfs-rbf-3.2.4-tests.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/hadoop-hdfs-3.2.4.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/hadoop-hdfs-nfs-3.2.4.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/hadoop-hdfs-client-3.2.4-tests.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/hadoop-hdfs-native-client-3.2.4-tests.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/hadoop-hdfs-3.2.4-tests.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/jetty-servlet-9.4.43.v20210629.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/j2objc-annotations-1.1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/kerb-client-1.0.1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/httpclient-4.5.13.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/error_prone_annotations-2.2.0.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/jaxb-api-2.2.11.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/jetty-server-9.4.43.v20210629.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/paranamer-2.3.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/kerb-common-1.0.1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/curator-recipes-2.13.0.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/kerby-config-1.0.1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/commons-text-1.4.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/javax.activation-api-1.2.0.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/commons-net-3.6.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/commons-collections-3.2.2.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/kerb-core-1.0.1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/okhttp-2.7.5.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/jackson-core-asl-1.9.13.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/accessors-smart-2.4.7.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/animal-sniffer-annotations-1.17.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/guava-27.0-jre.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/stax2-api-4.2.1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/javax.servlet-api-3.1.0.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/jersey-servlet-1.19.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/checker-qual-2.5.2.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/asm-5.0.4.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/commons-daemon-1.0.13.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/jcip-annotations-1.0-1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/failureaccess-1.0.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/jersey-core-1.19.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/jackson-xc-1.9.13.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/jackson-jaxrs-1.9.13.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/reload4j-1.2.18.3.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/listenablefuture-9999.0-empty-to-avoid-conflict-with-guava.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/kerb-simplekdc-1.0.1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/commons-cli-1.2.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/curator-client-2.13.0.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/jetty-util-9.4.43.v20210629.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/jackson-annotations-2.10.5.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/jersey-json-1.19.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/woodstox-core-5.3.0.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/json-smart-2.4.7.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/jersey-server-1.19.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/jackson-core-2.10.5.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/jsr311-api-1.1.1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/jsr305-3.0.2.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/zookeeper-3.4.14.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/leveldbjni-all-1.8.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/commons-configuration2-2.1.1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/jetty-http-9.4.43.v20210629.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/jetty-webapp-9.4.43.v20210629.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/commons-beanutils-1.9.4.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/protobuf-java-2.5.0.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/jetty-security-9.4.43.v20210629.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/kerby-pkix-1.0.1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/commons-lang3-3.7.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/dnsjava-2.1.7.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/re2j-1.1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/netty-all-4.1.68.Final.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/commons-math3-3.1.1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/kerby-xdr-1.0.1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/kerb-identity-1.0.1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/kerb-admin-1.0.1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/commons-compress-1.21.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/commons-io-2.8.0.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/jetty-util-ajax-9.4.43.v20210629.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/okio-1.6.0.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/netty-3.10.6.Final.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/audience-annotations-0.5.0.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/kerb-crypto-1.0.1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/jettison-1.1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/kerby-util-1.0.1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/httpcore-4.4.13.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/spotbugs-annotations-3.1.9.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/jsch-0.1.55.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/json-simple-1.1.1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/snappy-java-1.0.5.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/jetty-io-9.4.43.v20210629.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/kerb-util-1.0.1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/jackson-mapper-asl-1.9.13.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/hadoop-annotations-3.2.4.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/kerby-asn1-1.0.1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/commons-logging-1.1.3.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/commons-codec-1.11.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/jetty-xml-9.4.43.v20210629.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/jaxb-impl-2.2.3-1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/nimbus-jose-jwt-9.8.1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/curator-framework-2.13.0.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/jackson-databind-2.10.5.1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/gson-2.9.0.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/kerb-server-1.0.1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/htrace-core4-4.1.0-incubating.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/avro-1.7.7.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/hadoop-auth-3.2.4.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/token-provider-1.0.1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/yarn/hadoop-yarn-server-web-proxy-3.2.4.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/yarn/hadoop-yarn-server-timeline-pluginstorage-3.2.4.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/yarn/hadoop-yarn-server-nodemanager-3.2.4.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/yarn/hadoop-yarn-services-api-3.2.4.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/yarn/hadoop-yarn-server-sharedcachemanager-3.2.4.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/yarn/hadoop-yarn-registry-3.2.4.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/yarn/hadoop-yarn-api-3.2.4.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/yarn/hadoop-yarn-client-3.2.4.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/yarn/hadoop-yarn-common-3.2.4.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/yarn/hadoop-yarn-server-applicationhistoryservice-3.2.4.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/yarn/hadoop-yarn-services-core-3.2.4.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/yarn/hadoop-yarn-applications-unmanaged-am-launcher-3.2.4.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/yarn/hadoop-yarn-server-tests-3.2.4.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/yarn/hadoop-yarn-server-common-3.2.4.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/yarn/hadoop-yarn-submarine-3.2.4.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/yarn/hadoop-yarn-applications-distributedshell-3.2.4.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/yarn/hadoop-yarn-server-router-3.2.4.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/yarn/hadoop-yarn-server-resourcemanager-3.2.4.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/yarn/lib/json-io-2.5.1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/yarn/lib/geronimo-jcache_1.0_spec-1.0-alpha-1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/yarn/lib/jackson-jaxrs-json-provider-2.10.5.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/yarn/lib/metrics-core-3.2.4.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/yarn/lib/guice-servlet-4.0.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/yarn/lib/jackson-module-jaxb-annotations-2.10.5.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/yarn/lib/snakeyaml-1.26.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/yarn/lib/fst-2.50.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/yarn/lib/HikariCP-java7-2.4.12.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/yarn/lib/bcprov-jdk15on-1.60.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/yarn/lib/jersey-client-1.19.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/yarn/lib/objenesis-1.0.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/yarn/lib/javax.inject-1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/yarn/lib/jakarta.activation-api-1.2.1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/yarn/lib/swagger-annotations-1.5.4.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/yarn/lib/mssql-jdbc-6.2.1.jre7.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/yarn/lib/jackson-jaxrs-base-2.10.5.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/yarn/lib/jersey-guice-1.19.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/yarn/lib/java-util-1.9.0.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/yarn/lib/jakarta.xml.bind-api-2.3.2.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/yarn/lib/aopalliance-1.0.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/yarn/lib/ehcache-3.3.1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/yarn/lib/guice-4.0.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/yarn/lib/bcpkix-jdk15on-1.60.jar",
      "2022-12-30 12:19:14,179 INFO  org.apache.flink.runtime.entrypoint.ClusterEntrypoint        [] - --------------------------------------------------------------------------------",
      "2022-12-30 12:19:14,180 INFO  org.apache.flink.runtime.entrypoint.ClusterEntrypoint        [] - Registered UNIX signal handlers for [TERM, HUP, INT]",
      "2022-12-30 12:19:14,183 INFO  org.apache.flink.runtime.entrypoint.ClusterEntrypoint        [] - YARN daemon is running as: ispong Yarn client user obtainer: ispong",
      "2022-12-30 12:19:14,191 INFO  org.apache.flink.configuration.GlobalConfiguration           [] - Loading configuration property: taskmanager.memory.process.size, 1728m",
      "2022-12-30 12:19:14,191 INFO  org.apache.flink.configuration.GlobalConfiguration           [] - Loading configuration property: jobmanager.archive.fs.dir, hdfs://isxcode:9000/completed-jobs",
      "2022-12-30 12:19:14,191 INFO  org.apache.flink.configuration.GlobalConfiguration           [] - Loading configuration property: historyserver.web.address, isxcode",
      "2022-12-30 12:19:14,191 INFO  org.apache.flink.configuration.GlobalConfiguration           [] - Loading configuration property: internal.jobgraph-path, job.graph",
      "2022-12-30 12:19:14,191 INFO  org.apache.flink.configuration.GlobalConfiguration           [] - Loading configuration property: historyserver.web.port, 8082",
      "2022-12-30 12:19:14,192 INFO  org.apache.flink.configuration.GlobalConfiguration           [] - Loading configuration property: env.java.opts.jobmanager, -XX:MaxMetaspaceSize=268435400",
      "2022-12-30 12:19:14,192 INFO  org.apache.flink.configuration.GlobalConfiguration           [] - Loading configuration property: classloader.resolve-order, parent-first",
      "2022-12-30 12:19:14,192 INFO  org.apache.flink.configuration.GlobalConfiguration           [] - Loading configuration property: jobmanager.execution.failover-strategy, region",
      "2022-12-30 12:19:14,192 INFO  org.apache.flink.configuration.GlobalConfiguration           [] - Loading configuration property: high-availability.cluster-id, application_1672365636481_0011",
      "2022-12-30 12:19:14,192 INFO  org.apache.flink.configuration.GlobalConfiguration           [] - Loading configuration property: jobmanager.rpc.address, localhost",
      "2022-12-30 12:19:14,192 INFO  org.apache.flink.configuration.GlobalConfiguration           [] - Loading configuration property: jobmanager.memory.process.size, 1600m",
      "2022-12-30 12:19:14,192 INFO  org.apache.flink.configuration.GlobalConfiguration           [] - Loading configuration property: historyserver.archive.fs.refresh-interval, 10000",
      "2022-12-30 12:19:14,192 INFO  org.apache.flink.configuration.GlobalConfiguration           [] - Loading configuration property: jobmanager.rpc.port, 6123",
      "2022-12-30 12:19:14,192 INFO  org.apache.flink.configuration.GlobalConfiguration           [] - Loading configuration property: internal.cluster.execution-mode, DETACHED",
      "2022-12-30 12:19:14,192 INFO  org.apache.flink.configuration.GlobalConfiguration           [] - Loading configuration property: parallelism.default, 1",
      "2022-12-30 12:19:14,192 INFO  org.apache.flink.configuration.GlobalConfiguration           [] - Loading configuration property: taskmanager.numberOfTaskSlots, 1",
      "2022-12-30 12:19:14,192 INFO  org.apache.flink.configuration.GlobalConfiguration           [] - Loading configuration property: historyserver.archive.fs.dir, hdfs://isxcode:9000/completed-jobs",
      "2022-12-30 12:19:14,192 INFO  org.apache.flink.configuration.GlobalConfiguration           [] - Loading configuration property: $internal.yarn.log-config-file, /opt/flink/conf/log4j.properties",
      "2022-12-30 12:19:14,211 WARN  org.apache.flink.configuration.Configuration                 [] - Config uses deprecated configuration key 'web.port' instead of proper key 'rest.bind-port'",
      "2022-12-30 12:19:14,221 INFO  org.apache.flink.runtime.clusterframework.BootstrapTools     [] - Setting directories for temporary files to: /tmp/hadoop-ispong/nm-local-dir/usercache/ispong/appcache/application_1672365636481_0011",
      "2022-12-30 12:19:14,229 INFO  org.apache.flink.runtime.entrypoint.ClusterEntrypoint        [] - Starting YarnJobClusterEntrypoint.",
      "2022-12-30 12:19:14,233 WARN  org.apache.flink.core.plugin.PluginConfig                    [] - The plugins directory [plugins] does not exist.",
      "2022-12-30 12:19:14,248 INFO  org.apache.flink.runtime.entrypoint.ClusterEntrypoint        [] - Install default filesystem.",
      "2022-12-30 12:19:14,272 INFO  org.apache.flink.runtime.entrypoint.ClusterEntrypoint        [] - Install security context.",
      "2022-12-30 12:19:14,295 INFO  org.apache.flink.runtime.security.modules.HadoopModule       [] - Hadoop user set to ispong (auth:SIMPLE)",
      "2022-12-30 12:19:14,299 INFO  org.apache.flink.runtime.security.modules.JaasModule         [] - Jaas file will be created as /tmp/hadoop-ispong/nm-local-dir/usercache/ispong/appcache/application_1672365636481_0011/jaas-7579504151031722980.conf.",
      "2022-12-30 12:19:14,307 INFO  org.apache.flink.runtime.entrypoint.ClusterEntrypoint        [] - Initializing cluster services.",
      "2022-12-30 12:19:14,715 INFO  org.apache.flink.runtime.rpc.akka.AkkaRpcServiceUtils        [] - Trying to start actor system, external address isxcode:0, bind address 0.0.0.0:0.",
      "2022-12-30 12:19:15,353 INFO  akka.event.slf4j.Slf4jLogger                                 [] - Slf4jLogger started",
      "2022-12-30 12:19:15,379 INFO  akka.remote.RemoteActorRefProvider                           [] - Akka Cluster not in use - enabling unsafe features anyway because `akka.remote.use-unsafe-remote-features-outside-cluster` has been enabled.",
      "2022-12-30 12:19:15,379 INFO  akka.remote.Remoting                                         [] - Starting remoting",
      "2022-12-30 12:19:15,496 INFO  akka.remote.Remoting                                         [] - Remoting started; listening on addresses :[akka.tcp://flink@isxcode:34335]",
      "2022-12-30 12:19:15,641 INFO  org.apache.flink.runtime.rpc.akka.AkkaRpcServiceUtils        [] - Actor system started at akka.tcp://flink@isxcode:34335",
      "2022-12-30 12:19:15,662 WARN  org.apache.flink.configuration.Configuration                 [] - Config uses deprecated configuration key 'web.port' instead of proper key 'rest.port'",
      "2022-12-30 12:19:15,668 INFO  org.apache.flink.runtime.blob.BlobServer                     [] - Created BLOB server storage directory /tmp/hadoop-ispong/nm-local-dir/usercache/ispong/appcache/application_1672365636481_0011/blobStore-57e825aa-1bb5-43ad-9bed-026a1e02a67a",
      "2022-12-30 12:19:15,670 INFO  org.apache.flink.runtime.blob.BlobServer                     [] - Started BLOB server at 0.0.0.0:37206 - max concurrent requests: 50 - max backlog: 1000",
      "2022-12-30 12:19:15,678 INFO  org.apache.flink.runtime.metrics.MetricRegistryImpl          [] - No metrics reporter configured, no metrics will be exposed/reported.",
      "2022-12-30 12:19:15,681 INFO  org.apache.flink.runtime.rpc.akka.AkkaRpcServiceUtils        [] - Trying to start actor system, external address isxcode:0, bind address 0.0.0.0:0.",
      "2022-12-30 12:19:15,702 INFO  akka.event.slf4j.Slf4jLogger                                 [] - Slf4jLogger started",
      "2022-12-30 12:19:15,707 INFO  akka.remote.RemoteActorRefProvider                           [] - Akka Cluster not in use - enabling unsafe features anyway because `akka.remote.use-unsafe-remote-features-outside-cluster` has been enabled.",
      "2022-12-30 12:19:15,708 INFO  akka.remote.Remoting                                         [] - Starting remoting",
      "2022-12-30 12:19:15,719 INFO  akka.remote.Remoting                                         [] - Remoting started; listening on addresses :[akka.tcp://flink-metrics@isxcode:42507]",
      "2022-12-30 12:19:15,732 INFO  org.apache.flink.runtime.rpc.akka.AkkaRpcServiceUtils        [] - Actor system started at akka.tcp://flink-metrics@isxcode:42507",
      "2022-12-30 12:19:15,745 INFO  org.apache.flink.runtime.rpc.akka.AkkaRpcService             [] - Starting RPC endpoint for org.apache.flink.runtime.metrics.dump.MetricQueryService at akka://flink-metrics/user/rpc/MetricQueryService .",
      "2022-12-30 12:19:15,779 WARN  org.apache.flink.configuration.Configuration                 [] - Config uses deprecated configuration key 'web.port' instead of proper key 'rest.bind-port'",
      "2022-12-30 12:19:15,780 INFO  org.apache.flink.runtime.jobmaster.MiniDispatcherRestEndpoint [] - Upload directory /tmp/flink-web-09c6fc60-3011-4cf5-b2c5-b63243b089d2/flink-web-upload does not exist. ",
      "2022-12-30 12:19:15,780 INFO  org.apache.flink.runtime.jobmaster.MiniDispatcherRestEndpoint [] - Created directory /tmp/flink-web-09c6fc60-3011-4cf5-b2c5-b63243b089d2/flink-web-upload for file uploads.",
      "2022-12-30 12:19:15,795 INFO  org.apache.flink.runtime.jobmaster.MiniDispatcherRestEndpoint [] - Starting rest endpoint.",
      "2022-12-30 12:19:15,995 INFO  org.apache.flink.runtime.webmonitor.WebMonitorUtils          [] - Determined location of main cluster component log file: /data/hadoop/hadoop-3.2.4/logs/userlogs/application_1672365636481_0011/container_1672365636481_0011_01_000001/jobmanager.log",
      "2022-12-30 12:19:15,995 INFO  org.apache.flink.runtime.webmonitor.WebMonitorUtils          [] - Determined location of main cluster component stdout file: /data/hadoop/hadoop-3.2.4/logs/userlogs/application_1672365636481_0011/container_1672365636481_0011_01_000001/jobmanager.out",
      "2022-12-30 12:19:16,157 INFO  org.apache.flink.runtime.jobmaster.MiniDispatcherRestEndpoint [] - Rest endpoint listening at isxcode:45037",
      "2022-12-30 12:19:16,158 INFO  org.apache.flink.runtime.jobmaster.MiniDispatcherRestEndpoint [] - http://isxcode:45037 was granted leadership with leaderSessionID=00000000-0000-0000-0000-000000000000",
      "2022-12-30 12:19:16,159 INFO  org.apache.flink.runtime.jobmaster.MiniDispatcherRestEndpoint [] - Web frontend listening at http://isxcode:45037.",
      "2022-12-30 12:19:16,175 INFO  org.apache.flink.runtime.util.config.memory.ProcessMemoryUtils [] - The derived from fraction jvm overhead memory (172.800mb (181193935 bytes)) is less than its min value 192.000mb (201326592 bytes), min value will be used instead",
      "2022-12-30 12:19:16,246 INFO  org.apache.flink.runtime.dispatcher.runner.DefaultDispatcherRunner [] - DefaultDispatcherRunner was granted leadership with leader id 00000000-0000-0000-0000-000000000000. Creating new DispatcherLeaderProcess.",
      "2022-12-30 12:19:16,250 INFO  org.apache.flink.runtime.dispatcher.runner.JobDispatcherLeaderProcess [] - Start JobDispatcherLeaderProcess.",
      "2022-12-30 12:19:16,262 INFO  org.apache.flink.runtime.rpc.akka.AkkaRpcService             [] - Starting RPC endpoint for org.apache.flink.runtime.dispatcher.MiniDispatcher at akka://flink/user/rpc/dispatcher_0 .",
      "2022-12-30 12:19:16,282 INFO  org.apache.flink.runtime.resourcemanager.ResourceManagerServiceImpl [] - Starting resource manager service.",
      "2022-12-30 12:19:16,286 INFO  org.apache.flink.runtime.resourcemanager.ResourceManagerServiceImpl [] - Resource manager service is granted leadership with session id 00000000-0000-0000-0000-000000000000.",
      "2022-12-30 12:19:16,306 INFO  org.apache.flink.configuration.GlobalConfiguration           [] - Loading configuration property: taskmanager.memory.process.size, 1728m",
      "2022-12-30 12:19:16,306 INFO  org.apache.flink.configuration.GlobalConfiguration           [] - Loading configuration property: jobmanager.archive.fs.dir, hdfs://isxcode:9000/completed-jobs",
      "2022-12-30 12:19:16,306 INFO  org.apache.flink.configuration.GlobalConfiguration           [] - Loading configuration property: historyserver.web.address, isxcode",
      "2022-12-30 12:19:16,306 INFO  org.apache.flink.configuration.GlobalConfiguration           [] - Loading configuration property: internal.jobgraph-path, job.graph",
      "2022-12-30 12:19:16,306 INFO  org.apache.flink.configuration.GlobalConfiguration           [] - Loading configuration property: historyserver.web.port, 8082",
      "2022-12-30 12:19:16,306 INFO  org.apache.flink.configuration.GlobalConfiguration           [] - Loading configuration property: env.java.opts.jobmanager, -XX:MaxMetaspaceSize=268435400",
      "2022-12-30 12:19:16,306 INFO  org.apache.flink.configuration.GlobalConfiguration           [] - Loading configuration property: classloader.resolve-order, parent-first",
      "2022-12-30 12:19:16,306 INFO  org.apache.flink.configuration.GlobalConfiguration           [] - Loading configuration property: jobmanager.execution.failover-strategy, region",
      "2022-12-30 12:19:16,306 INFO  org.apache.flink.configuration.GlobalConfiguration           [] - Loading configuration property: high-availability.cluster-id, application_1672365636481_0011",
      "2022-12-30 12:19:16,307 INFO  org.apache.flink.configuration.GlobalConfiguration           [] - Loading configuration property: jobmanager.rpc.address, localhost",
      "2022-12-30 12:19:16,307 INFO  org.apache.flink.configuration.GlobalConfiguration           [] - Loading configuration property: jobmanager.memory.process.size, 1600m",
      "2022-12-30 12:19:16,307 INFO  org.apache.flink.configuration.GlobalConfiguration           [] - Loading configuration property: historyserver.archive.fs.refresh-interval, 10000",
      "2022-12-30 12:19:16,307 INFO  org.apache.flink.configuration.GlobalConfiguration           [] - Loading configuration property: jobmanager.rpc.port, 6123",
      "2022-12-30 12:19:16,307 INFO  org.apache.flink.configuration.GlobalConfiguration           [] - Loading configuration property: internal.cluster.execution-mode, DETACHED",
      "2022-12-30 12:19:16,307 INFO  org.apache.flink.configuration.GlobalConfiguration           [] - Loading configuration property: parallelism.default, 1",
      "2022-12-30 12:19:16,307 INFO  org.apache.flink.configuration.GlobalConfiguration           [] - Loading configuration property: taskmanager.numberOfTaskSlots, 1",
      "2022-12-30 12:19:16,307 INFO  org.apache.flink.configuration.GlobalConfiguration           [] - Loading configuration property: historyserver.archive.fs.dir, hdfs://isxcode:9000/completed-jobs",
      "2022-12-30 12:19:16,307 INFO  org.apache.flink.configuration.GlobalConfiguration           [] - Loading configuration property: $internal.yarn.log-config-file, /opt/flink/conf/log4j.properties",
      "2022-12-30 12:19:16,353 INFO  org.apache.flink.runtime.rpc.akka.AkkaRpcService             [] - Starting RPC endpoint for org.apache.flink.runtime.jobmaster.JobMaster at akka://flink/user/rpc/jobmanager_1 .",
      "2022-12-30 12:19:16,363 INFO  org.apache.flink.runtime.rpc.akka.AkkaRpcService             [] - Starting RPC endpoint for org.apache.flink.runtime.resourcemanager.active.ActiveResourceManager at akka://flink/user/rpc/resourcemanager_2 .",
      "2022-12-30 12:19:16,367 INFO  org.apache.flink.runtime.jobmaster.JobMaster                 [] - Initializing job 'ispong-pipeline' (5d94191b2621dddb7b0cafb9acec425f).",
      "2022-12-30 12:19:16,375 INFO  org.apache.flink.runtime.resourcemanager.active.ActiveResourceManager [] - Starting the resource manager.",
      "2022-12-30 12:19:16,408 INFO  org.apache.flink.runtime.jobmaster.JobMaster                 [] - Using restart back off time strategy NoRestartBackoffTimeStrategy for ispong-pipeline (5d94191b2621dddb7b0cafb9acec425f).",
      "2022-12-30 12:19:16,449 INFO  org.apache.hadoop.yarn.client.RMProxy                        [] - Connecting to ResourceManager at /0.0.0.0:8030",
      "2022-12-30 12:19:16,469 INFO  org.apache.flink.runtime.jobmaster.JobMaster                 [] - Running initialization on master for job ispong-pipeline (5d94191b2621dddb7b0cafb9acec425f).",
      "2022-12-30 12:19:16,591 INFO  org.apache.flink.runtime.jobmaster.JobMaster                 [] - Successfully ran initialization on master in 121 ms.",
      "2022-12-30 12:19:16,612 INFO  org.apache.flink.runtime.scheduler.adapter.DefaultExecutionTopology [] - Built 1 pipelined regions in 1 ms",
      "2022-12-30 12:19:16,648 INFO  org.apache.flink.runtime.jobmaster.JobMaster                 [] - No state backend has been configured, using default (HashMap) org.apache.flink.runtime.state.hashmap.HashMapStateBackend@79ffcbaa",
      "2022-12-30 12:19:16,648 INFO  org.apache.flink.runtime.state.StateBackendLoader            [] - State backend loader loads the state backend as HashMapStateBackend",
      "2022-12-30 12:19:16,650 INFO  org.apache.flink.runtime.jobmaster.JobMaster                 [] - Checkpoint storage is set to 'jobmanager'",
      "2022-12-30 12:19:16,672 INFO  org.apache.flink.runtime.checkpoint.CheckpointCoordinator    [] - No checkpoint found during restore.",
      "2022-12-30 12:19:16,678 INFO  org.apache.flink.runtime.jobmaster.JobMaster                 [] - Using failover strategy org.apache.flink.runtime.executiongraph.failover.flip1.RestartPipelinedRegionFailoverStrategy@5ba41e2b for ispong-pipeline (5d94191b2621dddb7b0cafb9acec425f).",
      "2022-12-30 12:19:16,696 INFO  org.apache.flink.runtime.jobmaster.JobMaster                 [] - Starting execution of job 'ispong-pipeline' (5d94191b2621dddb7b0cafb9acec425f) under job master id 00000000000000000000000000000000.",
      "2022-12-30 12:19:16,697 INFO  org.apache.flink.runtime.jobmaster.JobMaster                 [] - Starting scheduling with scheduling strategy [org.apache.flink.runtime.scheduler.strategy.PipelinedRegionSchedulingStrategy]",
      "2022-12-30 12:19:16,697 INFO  org.apache.flink.runtime.executiongraph.ExecutionGraph       [] - Job ispong-pipeline (5d94191b2621dddb7b0cafb9acec425f) switched from state CREATED to RUNNING.",
      "2022-12-30 12:19:16,702 INFO  org.apache.flink.runtime.executiongraph.ExecutionGraph       [] - Source: TableSourceScan(table=[[default_catalog, default_database, from_table]], fields=[username, age]) -> Sink: Sink(table=[default_catalog.default_database.to_table], fields=[username, age]) (1/1) (cbbbbc39109beaba72ef6273b58a1d5e) switched from CREATED to SCHEDULED.",
      "2022-12-30 12:19:16,710 INFO  org.apache.flink.yarn.YarnResourceManagerDriver              [] - Recovered 0 containers from previous attempts ([]).",
      "2022-12-30 12:19:16,711 INFO  org.apache.flink.runtime.resourcemanager.active.ActiveResourceManager [] - Recovered 0 workers from previous attempt.",
      "2022-12-30 12:19:16,730 INFO  org.apache.flink.runtime.jobmaster.JobMaster                 [] - Connecting to ResourceManager akka.tcp://flink@isxcode:34335/user/rpc/resourcemanager_*(00000000000000000000000000000000)",
      "2022-12-30 12:19:16,745 INFO  org.apache.hadoop.conf.Configuration                         [] - resource-types.xml not found",
      "2022-12-30 12:19:16,745 INFO  org.apache.hadoop.yarn.util.resource.ResourceUtils           [] - Unable to find 'resource-types.xml'.",
      "2022-12-30 12:19:16,764 INFO  org.apache.flink.runtime.externalresource.ExternalResourceUtils [] - Enabled external resources: []",
      "2022-12-30 12:19:16,770 INFO  org.apache.hadoop.yarn.client.api.async.impl.NMClientAsyncImpl [] - Upper bound of the thread pool size is 500",
      "2022-12-30 12:19:16,776 INFO  org.apache.flink.runtime.jobmaster.JobMaster                 [] - Resolved ResourceManager address, beginning registration",
      "2022-12-30 12:19:16,782 INFO  org.apache.flink.runtime.resourcemanager.active.ActiveResourceManager [] - Registering job manager 00000000000000000000000000000000@akka.tcp://flink@isxcode:34335/user/rpc/jobmanager_1 for job 5d94191b2621dddb7b0cafb9acec425f.",
      "2022-12-30 12:19:16,788 INFO  org.apache.flink.runtime.resourcemanager.active.ActiveResourceManager [] - Registered job manager 00000000000000000000000000000000@akka.tcp://flink@isxcode:34335/user/rpc/jobmanager_1 for job 5d94191b2621dddb7b0cafb9acec425f.",
      "2022-12-30 12:19:16,792 INFO  org.apache.flink.runtime.jobmaster.JobMaster                 [] - JobManager successfully registered at ResourceManager, leader id: 00000000000000000000000000000000.",
      "2022-12-30 12:19:16,793 INFO  org.apache.flink.runtime.resourcemanager.slotmanager.DeclarativeSlotManager [] - Received resource requirements from job 5d94191b2621dddb7b0cafb9acec425f: [ResourceRequirement{resourceProfile=ResourceProfile{UNKNOWN}, numberOfRequiredSlots=1}]",
      "2022-12-30 12:19:16,799 INFO  org.apache.flink.runtime.resourcemanager.active.ActiveResourceManager [] - Requesting new worker with resource spec WorkerResourceSpec {cpuCores=1.0, taskHeapSize=384.000mb (402653174 bytes), taskOffHeapSize=0 bytes, networkMemSize=128.000mb (134217730 bytes), managedMemSize=512.000mb (536870920 bytes), numSlots=1}, current pending count: 1.",
      "2022-12-30 12:19:16,812 INFO  org.apache.flink.yarn.YarnResourceManagerDriver              [] - Requesting new TaskExecutor container with resource TaskExecutorProcessSpec {cpuCores=1.0, frameworkHeapSize=128.000mb (134217728 bytes), frameworkOffHeapSize=128.000mb (134217728 bytes), taskHeapSize=384.000mb (402653174 bytes), taskOffHeapSize=0 bytes, networkMemSize=128.000mb (134217730 bytes), managedMemorySize=512.000mb (536870920 bytes), jvmMetaspaceSize=256.000mb (268435456 bytes), jvmOverheadSize=192.000mb (201326592 bytes), numSlots=1}, priority 1.",
      "2022-12-30 12:19:22,808 INFO  org.apache.flink.yarn.YarnResourceManagerDriver              [] - Received 1 containers.",
      "2022-12-30 12:19:22,809 INFO  org.apache.flink.yarn.YarnResourceManagerDriver              [] - Received 1 containers with priority 1, 1 pending container requests.",
      "2022-12-30 12:19:22,813 INFO  org.apache.flink.yarn.YarnResourceManagerDriver              [] - Removing container request Capability[<memory:1728, vCores:1>]Priority[1]AllocationRequestId[0]ExecutionTypeRequest[{Execution Type: GUARANTEED, Enforce Execution Type: false}]Resource Profile[null].",
      "2022-12-30 12:19:22,814 INFO  org.apache.flink.yarn.YarnResourceManagerDriver              [] - TaskExecutor container_1672365636481_0011_01_000002(isxcode:34772) will be started on isxcode with TaskExecutorProcessSpec {cpuCores=1.0, frameworkHeapSize=128.000mb (134217728 bytes), frameworkOffHeapSize=128.000mb (134217728 bytes), taskHeapSize=384.000mb (402653174 bytes), taskOffHeapSize=0 bytes, networkMemSize=128.000mb (134217730 bytes), managedMemorySize=512.000mb (536870920 bytes), jvmMetaspaceSize=256.000mb (268435456 bytes), jvmOverheadSize=192.000mb (201326592 bytes), numSlots=1}.",
      "2022-12-30 12:19:22,814 INFO  org.apache.flink.yarn.YarnResourceManagerDriver              [] - Accepted 1 requested containers, returned 0 excess containers, 0 pending container requests of resource <memory:1728, vCores:1>.",
      "2022-12-30 12:19:22,828 INFO  org.apache.flink.yarn.YarnResourceManagerDriver              [] - Creating container launch context for TaskManagers",
      "2022-12-30 12:19:22,830 INFO  org.apache.flink.yarn.YarnResourceManagerDriver              [] - Starting TaskManagers",
      "2022-12-30 12:19:22,853 INFO  org.apache.flink.runtime.resourcemanager.active.ActiveResourceManager [] - Requested worker container_1672365636481_0011_01_000002(isxcode:34772) with resource spec WorkerResourceSpec {cpuCores=1.0, taskHeapSize=384.000mb (402653174 bytes), taskOffHeapSize=0 bytes, networkMemSize=128.000mb (134217730 bytes), managedMemSize=512.000mb (536870920 bytes), numSlots=1}.",
      "2022-12-30 12:19:22,854 INFO  org.apache.hadoop.yarn.client.api.async.impl.NMClientAsyncImpl [] - Processing Event EventType: START_CONTAINER for Container container_1672365636481_0011_01_000002",
      "2022-12-30 12:19:26,203 INFO  org.apache.flink.runtime.resourcemanager.active.ActiveResourceManager [] - Registering TaskManager with ResourceID container_1672365636481_0011_01_000002(isxcode:34772) (akka.tcp://flink@isxcode:46633/user/rpc/taskmanager_0) at ResourceManager",
      "2022-12-30 12:19:26,238 INFO  org.apache.flink.runtime.resourcemanager.active.ActiveResourceManager [] - Worker container_1672365636481_0011_01_000002(isxcode:34772) is registered.",
      "2022-12-30 12:19:26,239 INFO  org.apache.flink.runtime.resourcemanager.active.ActiveResourceManager [] - Worker container_1672365636481_0011_01_000002(isxcode:34772) with resource spec WorkerResourceSpec {cpuCores=1.0, taskHeapSize=384.000mb (402653174 bytes), taskOffHeapSize=0 bytes, networkMemSize=128.000mb (134217730 bytes), managedMemSize=512.000mb (536870920 bytes), numSlots=1} was requested in current attempt. Current pending count after registering: 0.",
      "2022-12-30 12:19:26,306 INFO  org.apache.flink.runtime.executiongraph.ExecutionGraph       [] - Source: TableSourceScan(table=[[default_catalog, default_database, from_table]], fields=[username, age]) -> Sink: Sink(table=[default_catalog.default_database.to_table], fields=[username, age]) (1/1) (cbbbbc39109beaba72ef6273b58a1d5e) switched from SCHEDULED to DEPLOYING.",
      "2022-12-30 12:19:26,306 INFO  org.apache.flink.runtime.executiongraph.ExecutionGraph       [] - Deploying Source: TableSourceScan(table=[[default_catalog, default_database, from_table]], fields=[username, age]) -> Sink: Sink(table=[default_catalog.default_database.to_table], fields=[username, age]) (1/1) (attempt #0) with attempt id cbbbbc39109beaba72ef6273b58a1d5e to container_1672365636481_0011_01_000002 @ isxcode (dataPort=39777) with allocation id 25ca3895de9c0aa5ae702622b807ef0e",
      "2022-12-30 12:19:26,412 INFO  org.apache.flink.runtime.executiongraph.ExecutionGraph       [] - Source: TableSourceScan(table=[[default_catalog, default_database, from_table]], fields=[username, age]) -> Sink: Sink(table=[default_catalog.default_database.to_table], fields=[username, age]) (1/1) (cbbbbc39109beaba72ef6273b58a1d5e) switched from DEPLOYING to INITIALIZING.",
      "2022-12-30 12:19:27,001 INFO  org.apache.flink.runtime.executiongraph.ExecutionGraph       [] - Source: TableSourceScan(table=[[default_catalog, default_database, from_table]], fields=[username, age]) -> Sink: Sink(table=[default_catalog.default_database.to_table], fields=[username, age]) (1/1) (cbbbbc39109beaba72ef6273b58a1d5e) switched from INITIALIZING to RUNNING.",
      "2022-12-30 12:19:27,073 INFO  org.apache.flink.runtime.executiongraph.ExecutionGraph       [] - Source: TableSourceScan(table=[[default_catalog, default_database, from_table]], fields=[username, age]) -> Sink: Sink(table=[default_catalog.default_database.to_table], fields=[username, age]) (1/1) (cbbbbc39109beaba72ef6273b58a1d5e) switched from RUNNING to FAILED on container_1672365636481_0011_01_000002 @ isxcode (dataPort=39777).",
      "java.lang.IllegalArgumentException: open() failed.Table 'ispong_db.users' doesn't exist",
      "\tat org.apache.flink.connector.jdbc.table.JdbcRowDataInputFormat.open(JdbcRowDataInputFormat.java:207) ~[?:?]",
      "\tat org.apache.flink.streaming.api.functions.source.InputFormatSourceFunction.run(InputFormatSourceFunction.java:84) ~[sql-job-0.0.1.jar:?]",
      "\tat org.apache.flink.streaming.api.operators.StreamSource.run(StreamSource.java:116) ~[sql-job-0.0.1.jar:?]",
      "\tat org.apache.flink.streaming.api.operators.StreamSource.run(StreamSource.java:73) ~[sql-job-0.0.1.jar:?]",
      "\tat org.apache.flink.streaming.runtime.tasks.SourceStreamTask$LegacySourceFunctionThread.run(SourceStreamTask.java:323) ~[sql-job-0.0.1.jar:?]",
      "Caused by: java.sql.SQLSyntaxErrorException: Table 'ispong_db.users' doesn't exist",
      "\tat com.mysql.cj.jdbc.exceptions.SQLError.createSQLException(SQLError.java:120) ~[mysql-connector-java-8.0.22.jar:8.0.22]",
      "\tat com.mysql.cj.jdbc.exceptions.SQLError.createSQLException(SQLError.java:97) ~[mysql-connector-java-8.0.22.jar:8.0.22]",
      "\tat com.mysql.cj.jdbc.exceptions.SQLExceptionsMapping.translateException(SQLExceptionsMapping.java:122) ~[mysql-connector-java-8.0.22.jar:8.0.22]",
      "\tat com.mysql.cj.jdbc.ClientPreparedStatement.executeInternal(ClientPreparedStatement.java:953) ~[mysql-connector-java-8.0.22.jar:8.0.22]",
      "\tat com.mysql.cj.jdbc.ClientPreparedStatement.executeQuery(ClientPreparedStatement.java:1003) ~[mysql-connector-java-8.0.22.jar:8.0.22]",
      "\tat org.apache.flink.connector.jdbc.table.JdbcRowDataInputFormat.open(JdbcRowDataInputFormat.java:204) ~[?:?]",
      "\t... 4 more",
      "2022-12-30 12:19:27,090 INFO  org.apache.flink.runtime.resourcemanager.slotmanager.DeclarativeSlotManager [] - Clearing resource requirements of job 5d94191b2621dddb7b0cafb9acec425f",
      "2022-12-30 12:19:27,093 INFO  org.apache.flink.runtime.executiongraph.failover.flip1.RestartPipelinedRegionFailoverStrategy [] - Calculating tasks to restart to recover the failed task cbc357ccb763df2852fee8c4fc7d55f2_0.",
      "2022-12-30 12:19:27,093 INFO  org.apache.flink.runtime.executiongraph.failover.flip1.RestartPipelinedRegionFailoverStrategy [] - 1 tasks should be restarted to recover the failed task cbc357ccb763df2852fee8c4fc7d55f2_0. ",
      "2022-12-30 12:19:27,095 INFO  org.apache.flink.runtime.executiongraph.ExecutionGraph       [] - Job ispong-pipeline (5d94191b2621dddb7b0cafb9acec425f) switched from state RUNNING to FAILING.",
      "org.apache.flink.runtime.JobException: Recovery is suppressed by NoRestartBackoffTimeStrategy",
      "\tat org.apache.flink.runtime.executiongraph.failover.flip1.ExecutionFailureHandler.handleFailure(ExecutionFailureHandler.java:138) ~[sql-job-0.0.1.jar:?]",
      "\tat org.apache.flink.runtime.executiongraph.failover.flip1.ExecutionFailureHandler.getFailureHandlingResult(ExecutionFailureHandler.java:82) ~[sql-job-0.0.1.jar:?]",
      "\tat org.apache.flink.runtime.scheduler.DefaultScheduler.handleTaskFailure(DefaultScheduler.java:228) ~[sql-job-0.0.1.jar:?]",
      "\tat org.apache.flink.runtime.scheduler.DefaultScheduler.maybeHandleTaskFailure(DefaultScheduler.java:218) ~[sql-job-0.0.1.jar:?]",
      "\tat org.apache.flink.runtime.scheduler.DefaultScheduler.updateTaskExecutionStateInternal(DefaultScheduler.java:209) ~[sql-job-0.0.1.jar:?]",
      "\tat org.apache.flink.runtime.scheduler.SchedulerBase.updateTaskExecutionState(SchedulerBase.java:679) ~[sql-job-0.0.1.jar:?]",
      "\tat org.apache.flink.runtime.scheduler.SchedulerNG.updateTaskExecutionState(SchedulerNG.java:79) ~[sql-job-0.0.1.jar:?]",
      "\tat org.apache.flink.runtime.jobmaster.JobMaster.updateTaskExecutionState(JobMaster.java:444) ~[sql-job-0.0.1.jar:?]",
      "\tat sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method) ~[?:1.8.0_352]",
      "\tat sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62) ~[?:1.8.0_352]",
      "\tat sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) ~[?:1.8.0_352]",
      "\tat java.lang.reflect.Method.invoke(Method.java:498) ~[?:1.8.0_352]",
      "\tat org.apache.flink.runtime.rpc.akka.AkkaRpcActor.lambda$handleRpcInvocation$1(AkkaRpcActor.java:316) ~[flink-rpc-akka_49a1ba5e-4a5f-4efd-a0d6-5d2ea3f4646d.jar:1.14.0]",
      "\tat org.apache.flink.runtime.concurrent.akka.ClassLoadingUtils.runWithContextClassLoader(ClassLoadingUtils.java:83) ~[flink-rpc-akka_49a1ba5e-4a5f-4efd-a0d6-5d2ea3f4646d.jar:1.14.0]",
      "\tat org.apache.flink.runtime.rpc.akka.AkkaRpcActor.handleRpcInvocation(AkkaRpcActor.java:314) ~[flink-rpc-akka_49a1ba5e-4a5f-4efd-a0d6-5d2ea3f4646d.jar:1.14.0]",
      "\tat org.apache.flink.runtime.rpc.akka.AkkaRpcActor.handleRpcMessage(AkkaRpcActor.java:217) ~[flink-rpc-akka_49a1ba5e-4a5f-4efd-a0d6-5d2ea3f4646d.jar:1.14.0]",
      "\tat org.apache.flink.runtime.rpc.akka.FencedAkkaRpcActor.handleRpcMessage(FencedAkkaRpcActor.java:78) ~[flink-rpc-akka_49a1ba5e-4a5f-4efd-a0d6-5d2ea3f4646d.jar:1.14.0]",
      "\tat org.apache.flink.runtime.rpc.akka.AkkaRpcActor.handleMessage(AkkaRpcActor.java:163) ~[flink-rpc-akka_49a1ba5e-4a5f-4efd-a0d6-5d2ea3f4646d.jar:1.14.0]",
      "\tat akka.japi.pf.UnitCaseStatement.apply(CaseStatements.scala:24) [flink-rpc-akka_49a1ba5e-4a5f-4efd-a0d6-5d2ea3f4646d.jar:1.14.0]",
      "\tat akka.japi.pf.UnitCaseStatement.apply(CaseStatements.scala:20) [flink-rpc-akka_49a1ba5e-4a5f-4efd-a0d6-5d2ea3f4646d.jar:1.14.0]",
      "\tat scala.PartialFunction.applyOrElse(PartialFunction.scala:123) [flink-rpc-akka_49a1ba5e-4a5f-4efd-a0d6-5d2ea3f4646d.jar:1.14.0]",
      "\tat scala.PartialFunction.applyOrElse$(PartialFunction.scala:122) [flink-rpc-akka_49a1ba5e-4a5f-4efd-a0d6-5d2ea3f4646d.jar:1.14.0]",
      "\tat akka.japi.pf.UnitCaseStatement.applyOrElse(CaseStatements.scala:20) [flink-rpc-akka_49a1ba5e-4a5f-4efd-a0d6-5d2ea3f4646d.jar:1.14.0]",
      "\tat scala.PartialFunction$OrElse.applyOrElse(PartialFunction.scala:171) [flink-rpc-akka_49a1ba5e-4a5f-4efd-a0d6-5d2ea3f4646d.jar:1.14.0]",
      "\tat scala.PartialFunction$OrElse.applyOrElse(PartialFunction.scala:172) [flink-rpc-akka_49a1ba5e-4a5f-4efd-a0d6-5d2ea3f4646d.jar:1.14.0]",
      "\tat scala.PartialFunction$OrElse.applyOrElse(PartialFunction.scala:172) [flink-rpc-akka_49a1ba5e-4a5f-4efd-a0d6-5d2ea3f4646d.jar:1.14.0]",
      "\tat akka.actor.Actor.aroundReceive(Actor.scala:537) [flink-rpc-akka_49a1ba5e-4a5f-4efd-a0d6-5d2ea3f4646d.jar:1.14.0]",
      "\tat akka.actor.Actor.aroundReceive$(Actor.scala:535) [flink-rpc-akka_49a1ba5e-4a5f-4efd-a0d6-5d2ea3f4646d.jar:1.14.0]",
      "\tat akka.actor.AbstractActor.aroundReceive(AbstractActor.scala:220) [flink-rpc-akka_49a1ba5e-4a5f-4efd-a0d6-5d2ea3f4646d.jar:1.14.0]",
      "\tat akka.actor.ActorCell.receiveMessage(ActorCell.scala:580) [flink-rpc-akka_49a1ba5e-4a5f-4efd-a0d6-5d2ea3f4646d.jar:1.14.0]",
      "\tat akka.actor.ActorCell.invoke(ActorCell.scala:548) [flink-rpc-akka_49a1ba5e-4a5f-4efd-a0d6-5d2ea3f4646d.jar:1.14.0]",
      "\tat akka.dispatch.Mailbox.processMailbox(Mailbox.scala:270) [flink-rpc-akka_49a1ba5e-4a5f-4efd-a0d6-5d2ea3f4646d.jar:1.14.0]",
      "\tat akka.dispatch.Mailbox.run(Mailbox.scala:231) [flink-rpc-akka_49a1ba5e-4a5f-4efd-a0d6-5d2ea3f4646d.jar:1.14.0]",
      "\tat akka.dispatch.Mailbox.exec(Mailbox.scala:243) [flink-rpc-akka_49a1ba5e-4a5f-4efd-a0d6-5d2ea3f4646d.jar:1.14.0]",
      "\tat java.util.concurrent.ForkJoinTask.doExec(ForkJoinTask.java:289) [?:1.8.0_352]",
      "\tat java.util.concurrent.ForkJoinPool$WorkQueue.runTask(ForkJoinPool.java:1056) [?:1.8.0_352]",
      "\tat java.util.concurrent.ForkJoinPool.runWorker(ForkJoinPool.java:1692) [?:1.8.0_352]",
      "\tat java.util.concurrent.ForkJoinWorkerThread.run(ForkJoinWorkerThread.java:175) [?:1.8.0_352]",
      "Caused by: java.lang.IllegalArgumentException: open() failed.Table 'ispong_db.users' doesn't exist",
      "\tat org.apache.flink.connector.jdbc.table.JdbcRowDataInputFormat.open(JdbcRowDataInputFormat.java:207) ~[?:?]",
      "\tat org.apache.flink.streaming.api.functions.source.InputFormatSourceFunction.run(InputFormatSourceFunction.java:84) ~[sql-job-0.0.1.jar:?]",
      "\tat org.apache.flink.streaming.api.operators.StreamSource.run(StreamSource.java:116) ~[sql-job-0.0.1.jar:?]",
      "\tat org.apache.flink.streaming.api.operators.StreamSource.run(StreamSource.java:73) ~[sql-job-0.0.1.jar:?]",
      "\tat org.apache.flink.streaming.runtime.tasks.SourceStreamTask$LegacySourceFunctionThread.run(SourceStreamTask.java:323) ~[sql-job-0.0.1.jar:?]",
      "Caused by: java.sql.SQLSyntaxErrorException: Table 'ispong_db.users' doesn't exist",
      "\tat com.mysql.cj.jdbc.exceptions.SQLError.createSQLException(SQLError.java:120) ~[mysql-connector-java-8.0.22.jar:8.0.22]",
      "\tat com.mysql.cj.jdbc.exceptions.SQLError.createSQLException(SQLError.java:97) ~[mysql-connector-java-8.0.22.jar:8.0.22]",
      "\tat com.mysql.cj.jdbc.exceptions.SQLExceptionsMapping.translateException(SQLExceptionsMapping.java:122) ~[mysql-connector-java-8.0.22.jar:8.0.22]",
      "\tat com.mysql.cj.jdbc.ClientPreparedStatement.executeInternal(ClientPreparedStatement.java:953) ~[mysql-connector-java-8.0.22.jar:8.0.22]",
      "\tat com.mysql.cj.jdbc.ClientPreparedStatement.executeQuery(ClientPreparedStatement.java:1003) ~[mysql-connector-java-8.0.22.jar:8.0.22]",
      "\tat org.apache.flink.connector.jdbc.table.JdbcRowDataInputFormat.open(JdbcRowDataInputFormat.java:204) ~[?:?]",
      "\tat org.apache.flink.streaming.api.functions.source.InputFormatSourceFunction.run(InputFormatSourceFunction.java:84) ~[sql-job-0.0.1.jar:?]",
      "\tat org.apache.flink.streaming.api.operators.StreamSource.run(StreamSource.java:116) ~[sql-job-0.0.1.jar:?]",
      "\tat org.apache.flink.streaming.api.operators.StreamSource.run(StreamSource.java:73) ~[sql-job-0.0.1.jar:?]",
      "\tat org.apache.flink.streaming.runtime.tasks.SourceStreamTask$LegacySourceFunctionThread.run(SourceStreamTask.java:323) ~[sql-job-0.0.1.jar:?]",
      "2022-12-30 12:19:27,103 INFO  org.apache.flink.runtime.executiongraph.ExecutionGraph       [] - Job ispong-pipeline (5d94191b2621dddb7b0cafb9acec425f) switched from state FAILING to FAILED.",
      "org.apache.flink.runtime.JobException: Recovery is suppressed by NoRestartBackoffTimeStrategy",
      "\tat org.apache.flink.runtime.executiongraph.failover.flip1.ExecutionFailureHandler.handleFailure(ExecutionFailureHandler.java:138) ~[sql-job-0.0.1.jar:?]",
      "\tat org.apache.flink.runtime.executiongraph.failover.flip1.ExecutionFailureHandler.getFailureHandlingResult(ExecutionFailureHandler.java:82) ~[sql-job-0.0.1.jar:?]",
      "\tat org.apache.flink.runtime.scheduler.DefaultScheduler.handleTaskFailure(DefaultScheduler.java:228) ~[sql-job-0.0.1.jar:?]",
      "\tat org.apache.flink.runtime.scheduler.DefaultScheduler.maybeHandleTaskFailure(DefaultScheduler.java:218) ~[sql-job-0.0.1.jar:?]",
      "\tat org.apache.flink.runtime.scheduler.DefaultScheduler.updateTaskExecutionStateInternal(DefaultScheduler.java:209) ~[sql-job-0.0.1.jar:?]",
      "\tat org.apache.flink.runtime.scheduler.SchedulerBase.updateTaskExecutionState(SchedulerBase.java:679) ~[sql-job-0.0.1.jar:?]",
      "\tat org.apache.flink.runtime.scheduler.SchedulerNG.updateTaskExecutionState(SchedulerNG.java:79) ~[sql-job-0.0.1.jar:?]",
      "\tat org.apache.flink.runtime.jobmaster.JobMaster.updateTaskExecutionState(JobMaster.java:444) ~[sql-job-0.0.1.jar:?]",
      "\tat sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method) ~[?:1.8.0_352]",
      "\tat sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62) ~[?:1.8.0_352]",
      "\tat sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) ~[?:1.8.0_352]",
      "\tat java.lang.reflect.Method.invoke(Method.java:498) ~[?:1.8.0_352]",
      "\tat org.apache.flink.runtime.rpc.akka.AkkaRpcActor.lambda$handleRpcInvocation$1(AkkaRpcActor.java:316) ~[flink-rpc-akka_49a1ba5e-4a5f-4efd-a0d6-5d2ea3f4646d.jar:1.14.0]",
      "\tat org.apache.flink.runtime.concurrent.akka.ClassLoadingUtils.runWithContextClassLoader(ClassLoadingUtils.java:83) ~[flink-rpc-akka_49a1ba5e-4a5f-4efd-a0d6-5d2ea3f4646d.jar:1.14.0]",
      "\tat org.apache.flink.runtime.rpc.akka.AkkaRpcActor.handleRpcInvocation(AkkaRpcActor.java:314) ~[flink-rpc-akka_49a1ba5e-4a5f-4efd-a0d6-5d2ea3f4646d.jar:1.14.0]",
      "\tat org.apache.flink.runtime.rpc.akka.AkkaRpcActor.handleRpcMessage(AkkaRpcActor.java:217) ~[flink-rpc-akka_49a1ba5e-4a5f-4efd-a0d6-5d2ea3f4646d.jar:1.14.0]",
      "\tat org.apache.flink.runtime.rpc.akka.FencedAkkaRpcActor.handleRpcMessage(FencedAkkaRpcActor.java:78) ~[flink-rpc-akka_49a1ba5e-4a5f-4efd-a0d6-5d2ea3f4646d.jar:1.14.0]",
      "\tat org.apache.flink.runtime.rpc.akka.AkkaRpcActor.handleMessage(AkkaRpcActor.java:163) ~[flink-rpc-akka_49a1ba5e-4a5f-4efd-a0d6-5d2ea3f4646d.jar:1.14.0]",
      "\tat akka.japi.pf.UnitCaseStatement.apply(CaseStatements.scala:24) [flink-rpc-akka_49a1ba5e-4a5f-4efd-a0d6-5d2ea3f4646d.jar:1.14.0]",
      "\tat akka.japi.pf.UnitCaseStatement.apply(CaseStatements.scala:20) [flink-rpc-akka_49a1ba5e-4a5f-4efd-a0d6-5d2ea3f4646d.jar:1.14.0]",
      "\tat scala.PartialFunction.applyOrElse(PartialFunction.scala:123) [flink-rpc-akka_49a1ba5e-4a5f-4efd-a0d6-5d2ea3f4646d.jar:1.14.0]",
      "\tat scala.PartialFunction.applyOrElse$(PartialFunction.scala:122) [flink-rpc-akka_49a1ba5e-4a5f-4efd-a0d6-5d2ea3f4646d.jar:1.14.0]",
      "\tat akka.japi.pf.UnitCaseStatement.applyOrElse(CaseStatements.scala:20) [flink-rpc-akka_49a1ba5e-4a5f-4efd-a0d6-5d2ea3f4646d.jar:1.14.0]",
      "\tat scala.PartialFunction$OrElse.applyOrElse(PartialFunction.scala:171) [flink-rpc-akka_49a1ba5e-4a5f-4efd-a0d6-5d2ea3f4646d.jar:1.14.0]",
      "\tat scala.PartialFunction$OrElse.applyOrElse(PartialFunction.scala:172) [flink-rpc-akka_49a1ba5e-4a5f-4efd-a0d6-5d2ea3f4646d.jar:1.14.0]",
      "\tat scala.PartialFunction$OrElse.applyOrElse(PartialFunction.scala:172) [flink-rpc-akka_49a1ba5e-4a5f-4efd-a0d6-5d2ea3f4646d.jar:1.14.0]",
      "\tat akka.actor.Actor.aroundReceive(Actor.scala:537) [flink-rpc-akka_49a1ba5e-4a5f-4efd-a0d6-5d2ea3f4646d.jar:1.14.0]",
      "\tat akka.actor.Actor.aroundReceive$(Actor.scala:535) [flink-rpc-akka_49a1ba5e-4a5f-4efd-a0d6-5d2ea3f4646d.jar:1.14.0]",
      "\tat akka.actor.AbstractActor.aroundReceive(AbstractActor.scala:220) [flink-rpc-akka_49a1ba5e-4a5f-4efd-a0d6-5d2ea3f4646d.jar:1.14.0]",
      "\tat akka.actor.ActorCell.receiveMessage(ActorCell.scala:580) [flink-rpc-akka_49a1ba5e-4a5f-4efd-a0d6-5d2ea3f4646d.jar:1.14.0]",
      "\tat akka.actor.ActorCell.invoke(ActorCell.scala:548) [flink-rpc-akka_49a1ba5e-4a5f-4efd-a0d6-5d2ea3f4646d.jar:1.14.0]",
      "\tat akka.dispatch.Mailbox.processMailbox(Mailbox.scala:270) [flink-rpc-akka_49a1ba5e-4a5f-4efd-a0d6-5d2ea3f4646d.jar:1.14.0]",
      "\tat akka.dispatch.Mailbox.run(Mailbox.scala:231) [flink-rpc-akka_49a1ba5e-4a5f-4efd-a0d6-5d2ea3f4646d.jar:1.14.0]",
      "\tat akka.dispatch.Mailbox.exec(Mailbox.scala:243) [flink-rpc-akka_49a1ba5e-4a5f-4efd-a0d6-5d2ea3f4646d.jar:1.14.0]",
      "\tat java.util.concurrent.ForkJoinTask.doExec(ForkJoinTask.java:289) [?:1.8.0_352]",
      "\tat java.util.concurrent.ForkJoinPool$WorkQueue.runTask(ForkJoinPool.java:1056) [?:1.8.0_352]",
      "\tat java.util.concurrent.ForkJoinPool.runWorker(ForkJoinPool.java:1692) [?:1.8.0_352]",
      "\tat java.util.concurrent.ForkJoinWorkerThread.run(ForkJoinWorkerThread.java:175) [?:1.8.0_352]",
      "Caused by: java.lang.IllegalArgumentException: open() failed.Table 'ispong_db.users' doesn't exist",
      "\tat org.apache.flink.connector.jdbc.table.JdbcRowDataInputFormat.open(JdbcRowDataInputFormat.java:207) ~[?:?]",
      "\tat org.apache.flink.streaming.api.functions.source.InputFormatSourceFunction.run(InputFormatSourceFunction.java:84) ~[sql-job-0.0.1.jar:?]",
      "\tat org.apache.flink.streaming.api.operators.StreamSource.run(StreamSource.java:116) ~[sql-job-0.0.1.jar:?]",
      "\tat org.apache.flink.streaming.api.operators.StreamSource.run(StreamSource.java:73) ~[sql-job-0.0.1.jar:?]",
      "\tat org.apache.flink.streaming.runtime.tasks.SourceStreamTask$LegacySourceFunctionThread.run(SourceStreamTask.java:323) ~[sql-job-0.0.1.jar:?]",
      "Caused by: java.sql.SQLSyntaxErrorException: Table 'ispong_db.users' doesn't exist",
      "\tat com.mysql.cj.jdbc.exceptions.SQLError.createSQLException(SQLError.java:120) ~[mysql-connector-java-8.0.22.jar:8.0.22]",
      "\tat com.mysql.cj.jdbc.exceptions.SQLError.createSQLException(SQLError.java:97) ~[mysql-connector-java-8.0.22.jar:8.0.22]",
      "\tat com.mysql.cj.jdbc.exceptions.SQLExceptionsMapping.translateException(SQLExceptionsMapping.java:122) ~[mysql-connector-java-8.0.22.jar:8.0.22]",
      "\tat com.mysql.cj.jdbc.ClientPreparedStatement.executeInternal(ClientPreparedStatement.java:953) ~[mysql-connector-java-8.0.22.jar:8.0.22]",
      "\tat com.mysql.cj.jdbc.ClientPreparedStatement.executeQuery(ClientPreparedStatement.java:1003) ~[mysql-connector-java-8.0.22.jar:8.0.22]",
      "\tat org.apache.flink.connector.jdbc.table.JdbcRowDataInputFormat.open(JdbcRowDataInputFormat.java:204) ~[?:?]",
      "\tat org.apache.flink.streaming.api.functions.source.InputFormatSourceFunction.run(InputFormatSourceFunction.java:84) ~[sql-job-0.0.1.jar:?]",
      "\tat org.apache.flink.streaming.api.operators.StreamSource.run(StreamSource.java:116) ~[sql-job-0.0.1.jar:?]",
      "\tat org.apache.flink.streaming.api.operators.StreamSource.run(StreamSource.java:73) ~[sql-job-0.0.1.jar:?]",
      "\tat org.apache.flink.streaming.runtime.tasks.SourceStreamTask$LegacySourceFunctionThread.run(SourceStreamTask.java:323) ~[sql-job-0.0.1.jar:?]",
      "2022-12-30 12:19:27,106 INFO  org.apache.flink.runtime.checkpoint.CheckpointCoordinator    [] - Stopping checkpoint coordinator for job 5d94191b2621dddb7b0cafb9acec425f.",
      "2022-12-30 12:19:27,117 INFO  org.apache.flink.runtime.dispatcher.MiniDispatcher           [] - Job 5d94191b2621dddb7b0cafb9acec425f reached terminal state FAILED.",
      "org.apache.flink.runtime.JobException: Recovery is suppressed by NoRestartBackoffTimeStrategy",
      "\tat org.apache.flink.runtime.executiongraph.failover.flip1.ExecutionFailureHandler.handleFailure(ExecutionFailureHandler.java:138)",
      "\tat org.apache.flink.runtime.executiongraph.failover.flip1.ExecutionFailureHandler.getFailureHandlingResult(ExecutionFailureHandler.java:82)",
      "\tat org.apache.flink.runtime.scheduler.DefaultScheduler.handleTaskFailure(DefaultScheduler.java:228)",
      "\tat org.apache.flink.runtime.scheduler.DefaultScheduler.maybeHandleTaskFailure(DefaultScheduler.java:218)",
      "\tat org.apache.flink.runtime.scheduler.DefaultScheduler.updateTaskExecutionStateInternal(DefaultScheduler.java:209)",
      "\tat org.apache.flink.runtime.scheduler.SchedulerBase.updateTaskExecutionState(SchedulerBase.java:679)",
      "\tat org.apache.flink.runtime.scheduler.SchedulerNG.updateTaskExecutionState(SchedulerNG.java:79)",
      "\tat org.apache.flink.runtime.jobmaster.JobMaster.updateTaskExecutionState(JobMaster.java:444)",
      "\tat sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)",
      "\tat sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)",
      "\tat sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)",
      "\tat java.lang.reflect.Method.invoke(Method.java:498)",
      "\tat org.apache.flink.runtime.rpc.akka.AkkaRpcActor.lambda$handleRpcInvocation$1(AkkaRpcActor.java:316)",
      "\tat org.apache.flink.runtime.concurrent.akka.ClassLoadingUtils.runWithContextClassLoader(ClassLoadingUtils.java:83)",
      "\tat org.apache.flink.runtime.rpc.akka.AkkaRpcActor.handleRpcInvocation(AkkaRpcActor.java:314)",
      "\tat org.apache.flink.runtime.rpc.akka.AkkaRpcActor.handleRpcMessage(AkkaRpcActor.java:217)",
      "\tat org.apache.flink.runtime.rpc.akka.FencedAkkaRpcActor.handleRpcMessage(FencedAkkaRpcActor.java:78)",
      "\tat org.apache.flink.runtime.rpc.akka.AkkaRpcActor.handleMessage(AkkaRpcActor.java:163)",
      "\tat akka.japi.pf.UnitCaseStatement.apply(CaseStatements.scala:24)",
      "\tat akka.japi.pf.UnitCaseStatement.apply(CaseStatements.scala:20)",
      "\tat scala.PartialFunction.applyOrElse(PartialFunction.scala:123)",
      "\tat scala.PartialFunction.applyOrElse$(PartialFunction.scala:122)",
      "\tat akka.japi.pf.UnitCaseStatement.applyOrElse(CaseStatements.scala:20)",
      "\tat scala.PartialFunction$OrElse.applyOrElse(PartialFunction.scala:171)",
      "\tat scala.PartialFunction$OrElse.applyOrElse(PartialFunction.scala:172)",
      "\tat scala.PartialFunction$OrElse.applyOrElse(PartialFunction.scala:172)",
      "\tat akka.actor.Actor.aroundReceive(Actor.scala:537)",
      "\tat akka.actor.Actor.aroundReceive$(Actor.scala:535)",
      "\tat akka.actor.AbstractActor.aroundReceive(AbstractActor.scala:220)",
      "\tat akka.actor.ActorCell.receiveMessage(ActorCell.scala:580)",
      "\tat akka.actor.ActorCell.invoke(ActorCell.scala:548)",
      "\tat akka.dispatch.Mailbox.processMailbox(Mailbox.scala:270)",
      "\tat akka.dispatch.Mailbox.run(Mailbox.scala:231)",
      "\tat akka.dispatch.Mailbox.exec(Mailbox.scala:243)",
      "\tat java.util.concurrent.ForkJoinTask.doExec(ForkJoinTask.java:289)",
      "\tat java.util.concurrent.ForkJoinPool$WorkQueue.runTask(ForkJoinPool.java:1056)",
      "\tat java.util.concurrent.ForkJoinPool.runWorker(ForkJoinPool.java:1692)",
      "\tat java.util.concurrent.ForkJoinWorkerThread.run(ForkJoinWorkerThread.java:175)",
      "Caused by: java.lang.IllegalArgumentException: open() failed.Table 'ispong_db.users' doesn't exist",
      "\tat org.apache.flink.connector.jdbc.table.JdbcRowDataInputFormat.open(JdbcRowDataInputFormat.java:207)",
      "\tat org.apache.flink.streaming.api.functions.source.InputFormatSourceFunction.run(InputFormatSourceFunction.java:84)",
      "\tat org.apache.flink.streaming.api.operators.StreamSource.run(StreamSource.java:116)",
      "\tat org.apache.flink.streaming.api.operators.StreamSource.run(StreamSource.java:73)",
      "\tat org.apache.flink.streaming.runtime.tasks.SourceStreamTask$LegacySourceFunctionThread.run(SourceStreamTask.java:323)",
      "Caused by: java.sql.SQLSyntaxErrorException: Table 'ispong_db.users' doesn't exist",
      "\tat com.mysql.cj.jdbc.exceptions.SQLError.createSQLException(SQLError.java:120)",
      "\tat com.mysql.cj.jdbc.exceptions.SQLError.createSQLException(SQLError.java:97)",
      "\tat com.mysql.cj.jdbc.exceptions.SQLExceptionsMapping.translateException(SQLExceptionsMapping.java:122)",
      "\tat com.mysql.cj.jdbc.ClientPreparedStatement.executeInternal(ClientPreparedStatement.java:953)",
      "\tat com.mysql.cj.jdbc.ClientPreparedStatement.executeQuery(ClientPreparedStatement.java:1003)",
      "\tat org.apache.flink.connector.jdbc.table.JdbcRowDataInputFormat.open(JdbcRowDataInputFormat.java:204)",
      "\t... 4 more",
      "2022-12-30 12:19:27,119 INFO  org.apache.flink.runtime.dispatcher.MiniDispatcher           [] - Shutting down cluster with state FAILED, jobCancelled: false, executionMode: DETACHED",
      "2022-12-30 12:19:27,120 INFO  org.apache.flink.runtime.entrypoint.ClusterEntrypoint        [] - Shutting YarnJobClusterEntrypoint down with application status FAILED. Diagnostics null.",
      "2022-12-30 12:19:27,120 INFO  org.apache.flink.runtime.jobmaster.MiniDispatcherRestEndpoint [] - Shutting down rest endpoint.",
      "2022-12-30 12:19:27,137 INFO  org.apache.flink.runtime.jobmaster.JobMaster                 [] - Stopping the JobMaster for job 'ispong-pipeline' (5d94191b2621dddb7b0cafb9acec425f).",
      "2022-12-30 12:19:27,139 INFO  org.apache.flink.runtime.checkpoint.StandaloneCompletedCheckpointStore [] - Shutting down",
      "2022-12-30 12:19:27,140 INFO  org.apache.flink.runtime.jobmaster.slotpool.DefaultDeclarativeSlotPool [] - Releasing slot [25ca3895de9c0aa5ae702622b807ef0e].",
      "2022-12-30 12:19:27,142 INFO  org.apache.flink.runtime.jobmaster.JobMaster                 [] - Close ResourceManager connection 3eefdd88f5f11debde2b3c39649ed556: Stopping JobMaster for job 'ispong-pipeline' (5d94191b2621dddb7b0cafb9acec425f).",
      "2022-12-30 12:19:27,146 INFO  org.apache.flink.runtime.resourcemanager.active.ActiveResourceManager [] - Disconnect job manager 00000000000000000000000000000000@akka.tcp://flink@isxcode:34335/user/rpc/jobmanager_1 for job 5d94191b2621dddb7b0cafb9acec425f from the resource manager.",
      "2022-12-30 12:19:27,202 INFO  org.apache.flink.runtime.jobmaster.MiniDispatcherRestEndpoint [] - Removing cache directory /tmp/flink-web-09c6fc60-3011-4cf5-b2c5-b63243b089d2/flink-web-ui",
      "2022-12-30 12:19:27,204 INFO  org.apache.flink.runtime.jobmaster.MiniDispatcherRestEndpoint [] - http://isxcode:45037 lost leadership",
      "2022-12-30 12:19:27,204 INFO  org.apache.flink.runtime.jobmaster.MiniDispatcherRestEndpoint [] - Shut down complete.",
      "2022-12-30 12:19:27,210 INFO  org.apache.flink.runtime.resourcemanager.active.ActiveResourceManager [] - Shut down cluster because application is in FAILED, diagnostics null.",
      "2022-12-30 12:19:27,212 INFO  org.apache.flink.yarn.YarnResourceManagerDriver              [] - Unregister application from the YARN Resource Manager with final status FAILED.",
      "2022-12-30 12:19:27,221 INFO  org.apache.hadoop.yarn.client.api.impl.AMRMClientImpl        [] - Waiting for application to be successfully unregistered.",
      "2022-12-30 12:19:27,675 INFO  org.apache.flink.runtime.entrypoint.component.DispatcherResourceManagerComponent [] - Closing components.",
      "2022-12-30 12:19:27,676 INFO  org.apache.flink.runtime.dispatcher.runner.DefaultDispatcherRunner [] - DefaultDispatcherRunner was revoked the leadership with leader id 00000000-0000-0000-0000-000000000000. Stopping the DispatcherLeaderProcess.",
      "2022-12-30 12:19:27,676 INFO  org.apache.flink.runtime.dispatcher.runner.JobDispatcherLeaderProcess [] - Stopping JobDispatcherLeaderProcess.",
      "2022-12-30 12:19:27,677 INFO  org.apache.flink.runtime.dispatcher.MiniDispatcher           [] - Stopping dispatcher akka.tcp://flink@isxcode:34335/user/rpc/dispatcher_0.",
      "2022-12-30 12:19:27,677 INFO  org.apache.flink.runtime.dispatcher.MiniDispatcher           [] - Stopping all currently running jobs of dispatcher akka.tcp://flink@isxcode:34335/user/rpc/dispatcher_0.",
      "2022-12-30 12:19:27,677 INFO  org.apache.flink.runtime.resourcemanager.ResourceManagerServiceImpl [] - Stopping resource manager service.",
      "2022-12-30 12:19:27,677 INFO  org.apache.flink.runtime.resourcemanager.ResourceManagerServiceImpl [] - Resource manager service is not running. Ignore revoking leadership.",
      "2022-12-30 12:19:27,679 INFO  org.apache.flink.runtime.dispatcher.MiniDispatcher           [] - Stopped dispatcher akka.tcp://flink@isxcode:34335/user/rpc/dispatcher_0.",
      "2022-12-30 12:19:27,699 INFO  org.apache.flink.runtime.resourcemanager.slotmanager.DeclarativeSlotManager [] - Closing the slot manager.",
      "2022-12-30 12:19:27,699 INFO  org.apache.flink.runtime.resourcemanager.slotmanager.DeclarativeSlotManager [] - Suspending the slot manager.",
      "2022-12-30 12:19:27,701 INFO  org.apache.flink.runtime.blob.BlobServer                     [] - Stopped BLOB server at 0.0.0.0:37206",
      "2022-12-30 12:19:27,701 INFO  org.apache.flink.runtime.rpc.akka.AkkaRpcService             [] - Stopping Akka RPC service.",
      "2022-12-30 12:19:27,705 INFO  org.apache.flink.runtime.rpc.akka.AkkaRpcService             [] - Stopping Akka RPC service.",
      "2022-12-30 12:19:27,758 INFO  akka.remote.RemoteActorRefProvider$RemotingTerminator        [] - Shutting down remote daemon.",
      "2022-12-30 12:19:27,758 INFO  akka.remote.RemoteActorRefProvider$RemotingTerminator        [] - Shutting down remote daemon.",
      "2022-12-30 12:19:27,759 INFO  akka.remote.RemoteActorRefProvider$RemotingTerminator        [] - Remote daemon shut down; proceeding with flushing remote transports.",
      "2022-12-30 12:19:27,762 INFO  akka.remote.RemoteActorRefProvider$RemotingTerminator        [] - Remote daemon shut down; proceeding with flushing remote transports.",
      "2022-12-30 12:19:27,789 INFO  akka.remote.RemoteActorRefProvider$RemotingTerminator        [] - Remoting shut down.",
      "2022-12-30 12:19:27,793 INFO  akka.remote.RemoteActorRefProvider$RemotingTerminator        [] - Remoting shut down.",
      "2022-12-30 12:19:27,804 INFO  org.apache.flink.runtime.rpc.akka.AkkaRpcService             [] - Stopped Akka RPC service.",
      "2022-12-30 12:19:27,808 INFO  org.apache.flink.runtime.rpc.akka.AkkaRpcService             [] - Stopped Akka RPC service.",
      "2022-12-30 12:19:27,832 INFO  org.apache.flink.runtime.history.FsJobArchivist              [] - Job 5d94191b2621dddb7b0cafb9acec425f has been archived at hdfs://isxcode:9000/completed-jobs/5d94191b2621dddb7b0cafb9acec425f.",
      "2022-12-30 12:19:27,837 INFO  org.apache.flink.runtime.entrypoint.ClusterEntrypoint        [] - Terminating cluster entrypoint process YarnJobClusterEntrypoint with exit code 1443."
    ]
  }
}
```

## - 获取TaskManager日志

```java
public AcornResponse getTaskManagerLog(@RequestParam String applicationId) {

    return acornTemplate.build().applicationId(applicationId).getTaskManagerLog();
}
```

```json
{
    "code": "200",
    "msg": "获取TaskManager日志成功",
    "data": {
        "taskManagerLogs": [
            "2022-12-30 12:19:23,729 INFO  org.apache.flink.yarn.YarnTaskExecutorRunner                 [] - --------------------------------------------------------------------------------",
            "2022-12-30 12:19:23,733 INFO  org.apache.flink.yarn.YarnTaskExecutorRunner                 [] -  Starting YARN TaskExecutor runner (Version: 1.14.0, Scala: 2.12, Rev:460b386, Date:2021-09-22T08:39:40+02:00)",
            "2022-12-30 12:19:23,733 INFO  org.apache.flink.yarn.YarnTaskExecutorRunner                 [] -  OS current user: ispong",
            "2022-12-30 12:19:23,881 WARN  org.apache.hadoop.util.NativeCodeLoader                      [] - Unable to load native-hadoop library for your platform... using builtin-java classes where applicable",
            "2022-12-30 12:19:23,912 INFO  org.apache.flink.yarn.YarnTaskExecutorRunner                 [] -  Current Hadoop/Kerberos user: ispong",
            "2022-12-30 12:19:23,912 INFO  org.apache.flink.yarn.YarnTaskExecutorRunner                 [] -  JVM: OpenJDK 64-Bit Server VM - Red Hat, Inc. - 1.8/25.352-b08",
            "2022-12-30 12:19:23,912 INFO  org.apache.flink.yarn.YarnTaskExecutorRunner                 [] -  Maximum heap size: 491 MiBytes",
            "2022-12-30 12:19:23,912 INFO  org.apache.flink.yarn.YarnTaskExecutorRunner                 [] -  JAVA_HOME: /usr/lib/jvm/java-1.8.0-openjdk",
            "2022-12-30 12:19:23,914 INFO  org.apache.flink.yarn.YarnTaskExecutorRunner                 [] -  Hadoop version: 3.2.4",
            "2022-12-30 12:19:23,915 INFO  org.apache.flink.yarn.YarnTaskExecutorRunner                 [] -  JVM Options:",
            "2022-12-30 12:19:23,915 INFO  org.apache.flink.yarn.YarnTaskExecutorRunner                 [] -     -Xmx536870902",
            "2022-12-30 12:19:23,915 INFO  org.apache.flink.yarn.YarnTaskExecutorRunner                 [] -     -Xms536870902",
            "2022-12-30 12:19:23,915 INFO  org.apache.flink.yarn.YarnTaskExecutorRunner                 [] -     -XX:MaxDirectMemorySize=268435458",
            "2022-12-30 12:19:23,915 INFO  org.apache.flink.yarn.YarnTaskExecutorRunner                 [] -     -XX:MaxMetaspaceSize=268435456",
            "2022-12-30 12:19:23,915 INFO  org.apache.flink.yarn.YarnTaskExecutorRunner                 [] -     -Dlog.file=/data/hadoop/hadoop-3.2.4/logs/userlogs/application_1672365636481_0011/container_1672365636481_0011_01_000002/taskmanager.log",
            "2022-12-30 12:19:23,915 INFO  org.apache.flink.yarn.YarnTaskExecutorRunner                 [] -     -Dlog4j.configuration=file:./log4j.properties",
            "2022-12-30 12:19:23,915 INFO  org.apache.flink.yarn.YarnTaskExecutorRunner                 [] -     -Dlog4j.configurationFile=file:./log4j.properties",
            "2022-12-30 12:19:23,915 INFO  org.apache.flink.yarn.YarnTaskExecutorRunner                 [] -  Program Arguments:",
            "2022-12-30 12:19:23,917 INFO  org.apache.flink.yarn.YarnTaskExecutorRunner                 [] -     -D",
            "2022-12-30 12:19:23,917 INFO  org.apache.flink.yarn.YarnTaskExecutorRunner                 [] -     taskmanager.memory.network.min=134217730b",
            "2022-12-30 12:19:23,917 INFO  org.apache.flink.yarn.YarnTaskExecutorRunner                 [] -     -D",
            "2022-12-30 12:19:23,917 INFO  org.apache.flink.yarn.YarnTaskExecutorRunner                 [] -     taskmanager.cpu.cores=1.0",
            "2022-12-30 12:19:23,917 INFO  org.apache.flink.yarn.YarnTaskExecutorRunner                 [] -     -D",
            "2022-12-30 12:19:23,917 INFO  org.apache.flink.yarn.YarnTaskExecutorRunner                 [] -     taskmanager.memory.task.off-heap.size=0b",
            "2022-12-30 12:19:23,917 INFO  org.apache.flink.yarn.YarnTaskExecutorRunner                 [] -     -D",
            "2022-12-30 12:19:23,917 INFO  org.apache.flink.yarn.YarnTaskExecutorRunner                 [] -     taskmanager.memory.jvm-metaspace.size=268435456b",
            "2022-12-30 12:19:23,917 INFO  org.apache.flink.yarn.YarnTaskExecutorRunner                 [] -     -D",
            "2022-12-30 12:19:23,917 INFO  org.apache.flink.yarn.YarnTaskExecutorRunner                 [] -     external-resources=none",
            "2022-12-30 12:19:23,917 INFO  org.apache.flink.yarn.YarnTaskExecutorRunner                 [] -     -D",
            "2022-12-30 12:19:23,917 INFO  org.apache.flink.yarn.YarnTaskExecutorRunner                 [] -     taskmanager.memory.jvm-overhead.min=201326592b",
            "2022-12-30 12:19:23,917 INFO  org.apache.flink.yarn.YarnTaskExecutorRunner                 [] -     -D",
            "2022-12-30 12:19:23,917 INFO  org.apache.flink.yarn.YarnTaskExecutorRunner                 [] -     taskmanager.memory.framework.off-heap.size=134217728b",
            "2022-12-30 12:19:23,917 INFO  org.apache.flink.yarn.YarnTaskExecutorRunner                 [] -     -D",
            "2022-12-30 12:19:23,918 INFO  org.apache.flink.yarn.YarnTaskExecutorRunner                 [] -     taskmanager.memory.network.max=134217730b",
            "2022-12-30 12:19:23,918 INFO  org.apache.flink.yarn.YarnTaskExecutorRunner                 [] -     -D",
            "2022-12-30 12:19:23,918 INFO  org.apache.flink.yarn.YarnTaskExecutorRunner                 [] -     taskmanager.memory.framework.heap.size=134217728b",
            "2022-12-30 12:19:23,918 INFO  org.apache.flink.yarn.YarnTaskExecutorRunner                 [] -     -D",
            "2022-12-30 12:19:23,918 INFO  org.apache.flink.yarn.YarnTaskExecutorRunner                 [] -     taskmanager.memory.managed.size=536870920b",
            "2022-12-30 12:19:23,918 INFO  org.apache.flink.yarn.YarnTaskExecutorRunner                 [] -     -D",
            "2022-12-30 12:19:23,918 INFO  org.apache.flink.yarn.YarnTaskExecutorRunner                 [] -     taskmanager.memory.task.heap.size=402653174b",
            "2022-12-30 12:19:23,918 INFO  org.apache.flink.yarn.YarnTaskExecutorRunner                 [] -     -D",
            "2022-12-30 12:19:23,918 INFO  org.apache.flink.yarn.YarnTaskExecutorRunner                 [] -     taskmanager.numberOfTaskSlots=1",
            "2022-12-30 12:19:23,918 INFO  org.apache.flink.yarn.YarnTaskExecutorRunner                 [] -     -D",
            "2022-12-30 12:19:23,918 INFO  org.apache.flink.yarn.YarnTaskExecutorRunner                 [] -     taskmanager.memory.jvm-overhead.max=201326592b",
            "2022-12-30 12:19:23,918 INFO  org.apache.flink.yarn.YarnTaskExecutorRunner                 [] -     --configDir",
            "2022-12-30 12:19:23,918 INFO  org.apache.flink.yarn.YarnTaskExecutorRunner                 [] -     .",
            "2022-12-30 12:19:23,918 INFO  org.apache.flink.yarn.YarnTaskExecutorRunner                 [] -     -Djobmanager.rpc.address=isxcode",
            "2022-12-30 12:19:23,918 INFO  org.apache.flink.yarn.YarnTaskExecutorRunner                 [] -     -Djobmanager.memory.jvm-overhead.min=201326592b",
            "2022-12-30 12:19:23,918 INFO  org.apache.flink.yarn.YarnTaskExecutorRunner                 [] -     -Dtaskmanager.resource-id=container_1672365636481_0011_01_000002",
            "2022-12-30 12:19:23,919 INFO  org.apache.flink.yarn.YarnTaskExecutorRunner                 [] -     -Dweb.port=0",
            "2022-12-30 12:19:23,919 INFO  org.apache.flink.yarn.YarnTaskExecutorRunner                 [] -     -Djobmanager.memory.off-heap.size=134217728b",
            "2022-12-30 12:19:23,919 INFO  org.apache.flink.yarn.YarnTaskExecutorRunner                 [] -     -Dweb.tmpdir=/tmp/flink-web-09c6fc60-3011-4cf5-b2c5-b63243b089d2",
            "2022-12-30 12:19:23,919 INFO  org.apache.flink.yarn.YarnTaskExecutorRunner                 [] -     -Dinternal.taskmanager.resource-id.metadata=isxcode:34772",
            "2022-12-30 12:19:23,919 INFO  org.apache.flink.yarn.YarnTaskExecutorRunner                 [] -     -Djobmanager.rpc.port=34335",
            "2022-12-30 12:19:23,919 INFO  org.apache.flink.yarn.YarnTaskExecutorRunner                 [] -     -Drest.address=isxcode",
            "2022-12-30 12:19:23,919 INFO  org.apache.flink.yarn.YarnTaskExecutorRunner                 [] -     -Djobmanager.memory.jvm-metaspace.size=268435456b",
            "2022-12-30 12:19:23,919 INFO  org.apache.flink.yarn.YarnTaskExecutorRunner                 [] -     -Djobmanager.memory.heap.size=1073741824b",
            "2022-12-30 12:19:23,919 INFO  org.apache.flink.yarn.YarnTaskExecutorRunner                 [] -     -Djobmanager.memory.jvm-overhead.max=201326592b",
            "2022-12-30 12:19:23,919 INFO  org.apache.flink.yarn.YarnTaskExecutorRunner                 [] -  Classpath: :flink-csv-1.14.0.jar:flink-json-1.14.0.jar:flink-shaded-zookeeper-3.4.14.jar:flink-table_2.12-1.14.0.jar:log4j-1.2-api-2.14.1.jar:log4j-api-2.14.1.jar:log4j-core-2.14.1.jar:log4j-slf4j-impl-2.14.1.jar:mysql-connector-java-8.0.22.jar:sql-job-0.0.1.jar:flink-dist_2.12-1.14.0.jar:job.graph:flink-conf.yaml::/opt/hadoop/etc/hadoop:/data/hadoop/hadoop-3.2.4/share/hadoop/common/hadoop-common-3.2.4-tests.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/hadoop-kms-3.2.4.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/hadoop-common-3.2.4.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/hadoop-nfs-3.2.4.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/jetty-servlet-9.4.43.v20210629.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/j2objc-annotations-1.1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/kerb-client-1.0.1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/httpclient-4.5.13.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/error_prone_annotations-2.2.0.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/jaxb-api-2.2.11.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/jetty-server-9.4.43.v20210629.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/paranamer-2.3.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/kerb-common-1.0.1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/curator-recipes-2.13.0.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/kerby-config-1.0.1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/commons-text-1.4.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/javax.activation-api-1.2.0.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/commons-net-3.6.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/commons-collections-3.2.2.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/kerb-core-1.0.1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/jackson-core-asl-1.9.13.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/accessors-smart-2.4.7.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/metrics-core-3.2.4.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/animal-sniffer-annotations-1.17.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/guava-27.0-jre.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/stax2-api-4.2.1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/javax.servlet-api-3.1.0.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/jersey-servlet-1.19.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/checker-qual-2.5.2.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/asm-5.0.4.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/jcip-annotations-1.0-1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/failureaccess-1.0.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/jersey-core-1.19.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/jackson-xc-1.9.13.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/jackson-jaxrs-1.9.13.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/reload4j-1.2.18.3.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/listenablefuture-9999.0-empty-to-avoid-conflict-with-guava.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/kerb-simplekdc-1.0.1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/commons-cli-1.2.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/curator-client-2.13.0.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/jetty-util-9.4.43.v20210629.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/jackson-annotations-2.10.5.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/jersey-json-1.19.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/woodstox-core-5.3.0.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/json-smart-2.4.7.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/jersey-server-1.19.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/jackson-core-2.10.5.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/jsr311-api-1.1.1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/jsr305-3.0.2.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/zookeeper-3.4.14.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/commons-configuration2-2.1.1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/jetty-http-9.4.43.v20210629.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/jetty-webapp-9.4.43.v20210629.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/commons-beanutils-1.9.4.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/protobuf-java-2.5.0.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/jetty-security-9.4.43.v20210629.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/slf4j-api-1.7.35.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/kerby-pkix-1.0.1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/commons-lang3-3.7.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/dnsjava-2.1.7.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/re2j-1.1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/commons-math3-3.1.1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/kerby-xdr-1.0.1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/kerb-identity-1.0.1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/kerb-admin-1.0.1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/commons-compress-1.21.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/commons-io-2.8.0.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/jetty-util-ajax-9.4.43.v20210629.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/netty-3.10.6.Final.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/jsp-api-2.1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/audience-annotations-0.5.0.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/kerb-crypto-1.0.1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/jettison-1.1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/kerby-util-1.0.1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/httpcore-4.4.13.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/spotbugs-annotations-3.1.9.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/jsch-0.1.55.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/snappy-java-1.0.5.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/jetty-io-9.4.43.v20210629.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/kerb-util-1.0.1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/jackson-mapper-asl-1.9.13.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/hadoop-annotations-3.2.4.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/kerby-asn1-1.0.1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/slf4j-reload4j-1.7.35.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/commons-logging-1.1.3.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/commons-codec-1.11.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/jul-to-slf4j-1.7.35.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/jetty-xml-9.4.43.v20210629.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/jaxb-impl-2.2.3-1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/nimbus-jose-jwt-9.8.1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/curator-framework-2.13.0.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/jackson-databind-2.10.5.1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/gson-2.9.0.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/kerb-server-1.0.1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/htrace-core4-4.1.0-incubating.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/avro-1.7.7.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/hadoop-auth-3.2.4.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/common/lib/token-provider-1.0.1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/hadoop-hdfs-client-3.2.4.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/hadoop-hdfs-httpfs-3.2.4.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/hadoop-hdfs-native-client-3.2.4.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/hadoop-hdfs-rbf-3.2.4.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/hadoop-hdfs-rbf-3.2.4-tests.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/hadoop-hdfs-3.2.4.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/hadoop-hdfs-nfs-3.2.4.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/hadoop-hdfs-client-3.2.4-tests.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/hadoop-hdfs-native-client-3.2.4-tests.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/hadoop-hdfs-3.2.4-tests.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/jetty-servlet-9.4.43.v20210629.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/j2objc-annotations-1.1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/kerb-client-1.0.1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/httpclient-4.5.13.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/error_prone_annotations-2.2.0.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/jaxb-api-2.2.11.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/jetty-server-9.4.43.v20210629.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/paranamer-2.3.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/kerb-common-1.0.1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/curator-recipes-2.13.0.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/kerby-config-1.0.1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/commons-text-1.4.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/javax.activation-api-1.2.0.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/commons-net-3.6.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/commons-collections-3.2.2.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/kerb-core-1.0.1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/okhttp-2.7.5.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/jackson-core-asl-1.9.13.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/accessors-smart-2.4.7.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/animal-sniffer-annotations-1.17.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/guava-27.0-jre.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/stax2-api-4.2.1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/javax.servlet-api-3.1.0.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/jersey-servlet-1.19.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/checker-qual-2.5.2.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/asm-5.0.4.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/commons-daemon-1.0.13.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/jcip-annotations-1.0-1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/failureaccess-1.0.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/jersey-core-1.19.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/jackson-xc-1.9.13.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/jackson-jaxrs-1.9.13.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/reload4j-1.2.18.3.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/listenablefuture-9999.0-empty-to-avoid-conflict-with-guava.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/kerb-simplekdc-1.0.1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/commons-cli-1.2.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/curator-client-2.13.0.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/jetty-util-9.4.43.v20210629.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/jackson-annotations-2.10.5.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/jersey-json-1.19.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/woodstox-core-5.3.0.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/json-smart-2.4.7.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/jersey-server-1.19.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/jackson-core-2.10.5.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/jsr311-api-1.1.1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/jsr305-3.0.2.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/zookeeper-3.4.14.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/leveldbjni-all-1.8.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/commons-configuration2-2.1.1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/jetty-http-9.4.43.v20210629.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/jetty-webapp-9.4.43.v20210629.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/commons-beanutils-1.9.4.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/protobuf-java-2.5.0.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/jetty-security-9.4.43.v20210629.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/kerby-pkix-1.0.1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/commons-lang3-3.7.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/dnsjava-2.1.7.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/re2j-1.1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/netty-all-4.1.68.Final.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/commons-math3-3.1.1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/kerby-xdr-1.0.1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/kerb-identity-1.0.1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/kerb-admin-1.0.1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/commons-compress-1.21.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/commons-io-2.8.0.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/jetty-util-ajax-9.4.43.v20210629.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/okio-1.6.0.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/netty-3.10.6.Final.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/audience-annotations-0.5.0.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/kerb-crypto-1.0.1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/jettison-1.1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/kerby-util-1.0.1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/httpcore-4.4.13.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/spotbugs-annotations-3.1.9.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/jsch-0.1.55.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/json-simple-1.1.1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/snappy-java-1.0.5.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/jetty-io-9.4.43.v20210629.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/kerb-util-1.0.1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/jackson-mapper-asl-1.9.13.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/hadoop-annotations-3.2.4.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/kerby-asn1-1.0.1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/commons-logging-1.1.3.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/commons-codec-1.11.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/jetty-xml-9.4.43.v20210629.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/jaxb-impl-2.2.3-1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/nimbus-jose-jwt-9.8.1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/curator-framework-2.13.0.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/jackson-databind-2.10.5.1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/gson-2.9.0.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/kerb-server-1.0.1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/htrace-core4-4.1.0-incubating.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/avro-1.7.7.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/hadoop-auth-3.2.4.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/hdfs/lib/token-provider-1.0.1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/yarn/hadoop-yarn-server-web-proxy-3.2.4.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/yarn/hadoop-yarn-server-timeline-pluginstorage-3.2.4.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/yarn/hadoop-yarn-server-nodemanager-3.2.4.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/yarn/hadoop-yarn-services-api-3.2.4.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/yarn/hadoop-yarn-server-sharedcachemanager-3.2.4.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/yarn/hadoop-yarn-registry-3.2.4.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/yarn/hadoop-yarn-api-3.2.4.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/yarn/hadoop-yarn-client-3.2.4.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/yarn/hadoop-yarn-common-3.2.4.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/yarn/hadoop-yarn-server-applicationhistoryservice-3.2.4.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/yarn/hadoop-yarn-services-core-3.2.4.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/yarn/hadoop-yarn-applications-unmanaged-am-launcher-3.2.4.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/yarn/hadoop-yarn-server-tests-3.2.4.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/yarn/hadoop-yarn-server-common-3.2.4.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/yarn/hadoop-yarn-submarine-3.2.4.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/yarn/hadoop-yarn-applications-distributedshell-3.2.4.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/yarn/hadoop-yarn-server-router-3.2.4.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/yarn/hadoop-yarn-server-resourcemanager-3.2.4.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/yarn/lib/json-io-2.5.1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/yarn/lib/geronimo-jcache_1.0_spec-1.0-alpha-1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/yarn/lib/jackson-jaxrs-json-provider-2.10.5.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/yarn/lib/metrics-core-3.2.4.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/yarn/lib/guice-servlet-4.0.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/yarn/lib/jackson-module-jaxb-annotations-2.10.5.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/yarn/lib/snakeyaml-1.26.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/yarn/lib/fst-2.50.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/yarn/lib/HikariCP-java7-2.4.12.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/yarn/lib/bcprov-jdk15on-1.60.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/yarn/lib/jersey-client-1.19.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/yarn/lib/objenesis-1.0.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/yarn/lib/javax.inject-1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/yarn/lib/jakarta.activation-api-1.2.1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/yarn/lib/swagger-annotations-1.5.4.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/yarn/lib/mssql-jdbc-6.2.1.jre7.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/yarn/lib/jackson-jaxrs-base-2.10.5.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/yarn/lib/jersey-guice-1.19.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/yarn/lib/java-util-1.9.0.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/yarn/lib/jakarta.xml.bind-api-2.3.2.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/yarn/lib/aopalliance-1.0.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/yarn/lib/ehcache-3.3.1.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/yarn/lib/guice-4.0.jar:/data/hadoop/hadoop-3.2.4/share/hadoop/yarn/lib/bcpkix-jdk15on-1.60.jar",
            "2022-12-30 12:19:23,921 INFO  org.apache.flink.yarn.YarnTaskExecutorRunner                 [] - --------------------------------------------------------------------------------",
            "2022-12-30 12:19:23,923 INFO  org.apache.flink.yarn.YarnTaskExecutorRunner                 [] - Registered UNIX signal handlers for [TERM, HUP, INT]",
            "2022-12-30 12:19:23,926 INFO  org.apache.flink.yarn.YarnTaskExecutorRunner                 [] - Current working Directory: /tmp/hadoop-ispong/nm-local-dir/usercache/ispong/appcache/application_1672365636481_0011/container_1672365636481_0011_01_000002",
            "2022-12-30 12:19:23,938 INFO  org.apache.flink.configuration.GlobalConfiguration           [] - Loading configuration property: taskmanager.memory.process.size, 1728m",
            "2022-12-30 12:19:23,938 INFO  org.apache.flink.configuration.GlobalConfiguration           [] - Loading configuration property: jobmanager.archive.fs.dir, hdfs://isxcode:9000/completed-jobs",
            "2022-12-30 12:19:23,938 INFO  org.apache.flink.configuration.GlobalConfiguration           [] - Loading configuration property: historyserver.web.address, isxcode",
            "2022-12-30 12:19:23,938 INFO  org.apache.flink.configuration.GlobalConfiguration           [] - Loading configuration property: internal.jobgraph-path, job.graph",
            "2022-12-30 12:19:23,938 INFO  org.apache.flink.configuration.GlobalConfiguration           [] - Loading configuration property: historyserver.web.port, 8082",
            "2022-12-30 12:19:23,938 INFO  org.apache.flink.configuration.GlobalConfiguration           [] - Loading configuration property: env.java.opts.jobmanager, -XX:MaxMetaspaceSize=268435400",
            "2022-12-30 12:19:23,938 INFO  org.apache.flink.configuration.GlobalConfiguration           [] - Loading configuration property: classloader.resolve-order, parent-first",
            "2022-12-30 12:19:23,938 INFO  org.apache.flink.configuration.GlobalConfiguration           [] - Loading configuration property: jobmanager.execution.failover-strategy, region",
            "2022-12-30 12:19:23,938 INFO  org.apache.flink.configuration.GlobalConfiguration           [] - Loading configuration property: high-availability.cluster-id, application_1672365636481_0011",
            "2022-12-30 12:19:23,938 INFO  org.apache.flink.configuration.GlobalConfiguration           [] - Loading configuration property: jobmanager.rpc.address, localhost",
            "2022-12-30 12:19:23,939 INFO  org.apache.flink.configuration.GlobalConfiguration           [] - Loading configuration property: jobmanager.memory.process.size, 1600m",
            "2022-12-30 12:19:23,939 INFO  org.apache.flink.configuration.GlobalConfiguration           [] - Loading configuration property: historyserver.archive.fs.refresh-interval, 10000",
            "2022-12-30 12:19:23,939 INFO  org.apache.flink.configuration.GlobalConfiguration           [] - Loading configuration property: jobmanager.rpc.port, 6123",
            "2022-12-30 12:19:23,939 INFO  org.apache.flink.configuration.GlobalConfiguration           [] - Loading configuration property: internal.cluster.execution-mode, DETACHED",
            "2022-12-30 12:19:23,939 INFO  org.apache.flink.configuration.GlobalConfiguration           [] - Loading configuration property: parallelism.default, 1",
            "2022-12-30 12:19:23,939 INFO  org.apache.flink.configuration.GlobalConfiguration           [] - Loading configuration property: taskmanager.numberOfTaskSlots, 1",
            "2022-12-30 12:19:23,939 INFO  org.apache.flink.configuration.GlobalConfiguration           [] - Loading configuration property: historyserver.archive.fs.dir, hdfs://isxcode:9000/completed-jobs",
            "2022-12-30 12:19:23,939 INFO  org.apache.flink.configuration.GlobalConfiguration           [] - Loading configuration property: $internal.yarn.log-config-file, /opt/flink/conf/log4j.properties",
            "2022-12-30 12:19:23,939 INFO  org.apache.flink.yarn.YarnTaskExecutorRunner                 [] - Current working/local Directory: /tmp/hadoop-ispong/nm-local-dir/usercache/ispong/appcache/application_1672365636481_0011",
            "2022-12-30 12:19:23,950 INFO  org.apache.flink.runtime.clusterframework.BootstrapTools     [] - Setting directories for temporary files to: /tmp/hadoop-ispong/nm-local-dir/usercache/ispong/appcache/application_1672365636481_0011",
            "2022-12-30 12:19:23,950 INFO  org.apache.flink.yarn.YarnTaskExecutorRunner                 [] - TM: local keytab path obtained null",
            "2022-12-30 12:19:23,950 INFO  org.apache.flink.yarn.YarnTaskExecutorRunner                 [] - TM: keytab principal obtained null",
            "2022-12-30 12:19:23,963 INFO  org.apache.flink.yarn.YarnTaskExecutorRunner                 [] - YARN daemon is running as: ispong Yarn client user obtainer: ispong",
            "2022-12-30 12:19:23,975 WARN  org.apache.flink.core.plugin.PluginConfig                    [] - The plugins directory [plugins] does not exist.",
            "2022-12-30 12:19:24,032 INFO  org.apache.flink.runtime.state.changelog.StateChangelogStorageLoader [] - StateChangelogStorageLoader initialized with shortcut names {memory}.",
            "2022-12-30 12:19:24,034 INFO  org.apache.flink.runtime.state.changelog.StateChangelogStorageLoader [] - StateChangelogStorageLoader initialized with shortcut names {memory}.",
            "2022-12-30 12:19:24,067 INFO  org.apache.flink.runtime.security.modules.HadoopModule       [] - Hadoop user set to ispong (auth:SIMPLE)",
            "2022-12-30 12:19:24,080 INFO  org.apache.flink.runtime.security.modules.JaasModule         [] - Jaas file will be created as /tmp/hadoop-ispong/nm-local-dir/usercache/ispong/appcache/application_1672365636481_0011/jaas-2924739990733436830.conf.",
            "2022-12-30 12:19:24,407 WARN  org.apache.flink.configuration.Configuration                 [] - Config uses deprecated configuration key 'web.port' instead of proper key 'rest.port'",
            "2022-12-30 12:19:24,410 INFO  org.apache.flink.runtime.taskexecutor.TaskManagerRunner      [] - Using configured hostname/address for TaskManager: isxcode.",
            "2022-12-30 12:19:24,494 INFO  org.apache.flink.runtime.rpc.akka.AkkaRpcServiceUtils        [] - Trying to start actor system, external address isxcode:0, bind address 0.0.0.0:0.",
            "2022-12-30 12:19:25,106 INFO  akka.event.slf4j.Slf4jLogger                                 [] - Slf4jLogger started",
            "2022-12-30 12:19:25,131 INFO  akka.remote.RemoteActorRefProvider                           [] - Akka Cluster not in use - enabling unsafe features anyway because `akka.remote.use-unsafe-remote-features-outside-cluster` has been enabled.",
            "2022-12-30 12:19:25,131 INFO  akka.remote.Remoting                                         [] - Starting remoting",
            "2022-12-30 12:19:25,272 INFO  akka.remote.Remoting                                         [] - Remoting started; listening on addresses :[akka.tcp://flink@isxcode:46633]",
            "2022-12-30 12:19:25,456 INFO  org.apache.flink.runtime.rpc.akka.AkkaRpcServiceUtils        [] - Actor system started at akka.tcp://flink@isxcode:46633",
            "2022-12-30 12:19:25,473 INFO  org.apache.flink.runtime.metrics.MetricRegistryImpl          [] - No metrics reporter configured, no metrics will be exposed/reported.",
            "2022-12-30 12:19:25,476 INFO  org.apache.flink.runtime.rpc.akka.AkkaRpcServiceUtils        [] - Trying to start actor system, external address isxcode:0, bind address 0.0.0.0:0.",
            "2022-12-30 12:19:25,498 INFO  akka.event.slf4j.Slf4jLogger                                 [] - Slf4jLogger started",
            "2022-12-30 12:19:25,503 INFO  akka.remote.RemoteActorRefProvider                           [] - Akka Cluster not in use - enabling unsafe features anyway because `akka.remote.use-unsafe-remote-features-outside-cluster` has been enabled.",
            "2022-12-30 12:19:25,503 INFO  akka.remote.Remoting                                         [] - Starting remoting",
            "2022-12-30 12:19:25,521 INFO  akka.remote.Remoting                                         [] - Remoting started; listening on addresses :[akka.tcp://flink-metrics@isxcode:36649]",
            "2022-12-30 12:19:25,531 INFO  org.apache.flink.runtime.rpc.akka.AkkaRpcServiceUtils        [] - Actor system started at akka.tcp://flink-metrics@isxcode:36649",
            "2022-12-30 12:19:25,545 INFO  org.apache.flink.runtime.rpc.akka.AkkaRpcService             [] - Starting RPC endpoint for org.apache.flink.runtime.metrics.dump.MetricQueryService at akka://flink-metrics/user/rpc/MetricQueryService_container_1672365636481_0011_01_000002 .",
            "2022-12-30 12:19:25,558 INFO  org.apache.flink.runtime.blob.PermanentBlobCache             [] - Created BLOB cache storage directory /tmp/hadoop-ispong/nm-local-dir/usercache/ispong/appcache/application_1672365636481_0011/blobStore-06a9d6ed-f98c-445c-95f0-b4ff5df51bd1",
            "2022-12-30 12:19:25,560 INFO  org.apache.flink.runtime.blob.TransientBlobCache             [] - Created BLOB cache storage directory /tmp/hadoop-ispong/nm-local-dir/usercache/ispong/appcache/application_1672365636481_0011/blobStore-9e6078f8-bb67-462b-b4ee-3ef266da00d0",
            "2022-12-30 12:19:25,563 INFO  org.apache.flink.runtime.externalresource.ExternalResourceUtils [] - Enabled external resources: []",
            "2022-12-30 12:19:25,563 INFO  org.apache.flink.runtime.taskexecutor.TaskManagerRunner      [] - Starting TaskManager with ResourceID: container_1672365636481_0011_01_000002(isxcode:34772)",
            "2022-12-30 12:19:25,581 INFO  org.apache.flink.runtime.taskexecutor.TaskManagerServices    [] - Temporary file directory '/tmp/hadoop-ispong/nm-local-dir/usercache/ispong/appcache/application_1672365636481_0011': total 39 GB, usable 28 GB (71.79% usable)",
            "2022-12-30 12:19:25,583 INFO  org.apache.flink.runtime.io.disk.iomanager.IOManager         [] - Created a new FileChannelManager for spilling of task related data to disk (joins, sorting, ...). Used directories:",
            "\t/tmp/hadoop-ispong/nm-local-dir/usercache/ispong/appcache/application_1672365636481_0011/flink-io-20a4e8a8-af62-42f2-ac17-e950b3630b0a",
            "2022-12-30 12:19:25,590 INFO  org.apache.flink.runtime.io.network.netty.NettyConfig        [] - NettyConfig [server address: /0.0.0.0, server port: 0, ssl enabled: false, memory segment size (bytes): 32768, transport type: AUTO, number of server threads: 1 (manual), number of client threads: 1 (manual), server connect backlog: 0 (use Netty's default), client connect timeout (sec): 120, send/receive buffer size (bytes): 0 (use Netty's default)]",
            "2022-12-30 12:19:25,592 INFO  org.apache.flink.runtime.io.network.NettyShuffleServiceFactory [] - Created a new FileChannelManager for storing result partitions of BLOCKING shuffles. Used directories:",
            "\t/tmp/hadoop-ispong/nm-local-dir/usercache/ispong/appcache/application_1672365636481_0011/flink-netty-shuffle-29993c5f-67e5-4783-a4a7-e40deecbbebf",
            "2022-12-30 12:19:25,759 INFO  org.apache.flink.runtime.io.network.buffer.NetworkBufferPool [] - Allocated 128 MB for network buffer pool (number of memory segments: 4096, bytes per segment: 32768).",
            "2022-12-30 12:19:25,770 INFO  org.apache.flink.runtime.io.network.NettyShuffleEnvironment  [] - Starting the network environment and its components.",
            "2022-12-30 12:19:25,824 INFO  org.apache.flink.runtime.io.network.netty.NettyClient        [] - Transport type 'auto': using EPOLL.",
            "2022-12-30 12:19:25,825 INFO  org.apache.flink.runtime.io.network.netty.NettyClient        [] - Successful initialization (took 55 ms).",
            "2022-12-30 12:19:25,830 INFO  org.apache.flink.runtime.io.network.netty.NettyServer        [] - Transport type 'auto': using EPOLL.",
            "2022-12-30 12:19:25,858 INFO  org.apache.flink.runtime.io.network.netty.NettyServer        [] - Successful initialization (took 31 ms). Listening on SocketAddress /0:0:0:0:0:0:0:0%0:39777.",
            "2022-12-30 12:19:25,859 INFO  org.apache.flink.runtime.taskexecutor.KvStateService         [] - Starting the kvState service and its components.",
            "2022-12-30 12:19:25,886 INFO  org.apache.flink.runtime.rpc.akka.AkkaRpcService             [] - Starting RPC endpoint for org.apache.flink.runtime.taskexecutor.TaskExecutor at akka://flink/user/rpc/taskmanager_0 .",
            "2022-12-30 12:19:25,903 INFO  org.apache.flink.runtime.taskexecutor.DefaultJobLeaderService [] - Start job leader service.",
            "2022-12-30 12:19:25,904 INFO  org.apache.flink.runtime.filecache.FileCache                 [] - User file cache uses directory /tmp/hadoop-ispong/nm-local-dir/usercache/ispong/appcache/application_1672365636481_0011/flink-dist-cache-c199f168-12b9-4819-b607-92891fac76bc",
            "2022-12-30 12:19:25,906 INFO  org.apache.flink.runtime.taskexecutor.TaskExecutor           [] - Connecting to ResourceManager akka.tcp://flink@isxcode:34335/user/rpc/resourcemanager_*(00000000000000000000000000000000).",
            "2022-12-30 12:19:26,140 INFO  org.apache.flink.runtime.taskexecutor.TaskExecutor           [] - Resolved ResourceManager address, beginning registration",
            "2022-12-30 12:19:26,222 INFO  org.apache.flink.runtime.taskexecutor.TaskExecutor           [] - Successful registration at resource manager akka.tcp://flink@isxcode:34335/user/rpc/resourcemanager_* under registration id ae4016651bdf358e9b17a8c2fcc58724.",
            "2022-12-30 12:19:26,248 INFO  org.apache.flink.runtime.taskexecutor.TaskExecutor           [] - Receive slot request 25ca3895de9c0aa5ae702622b807ef0e for job 5d94191b2621dddb7b0cafb9acec425f from resource manager with leader id 00000000000000000000000000000000.",
            "2022-12-30 12:19:26,252 INFO  org.apache.flink.runtime.taskexecutor.TaskExecutor           [] - Allocated slot for 25ca3895de9c0aa5ae702622b807ef0e.",
            "2022-12-30 12:19:26,252 INFO  org.apache.flink.runtime.taskexecutor.DefaultJobLeaderService [] - Add job 5d94191b2621dddb7b0cafb9acec425f for job leader monitoring.",
            "2022-12-30 12:19:26,254 INFO  org.apache.flink.runtime.taskexecutor.DefaultJobLeaderService [] - Try to register at job manager akka.tcp://flink@isxcode:34335/user/rpc/jobmanager_1 with leader id 00000000-0000-0000-0000-000000000000.",
            "2022-12-30 12:19:26,271 INFO  org.apache.flink.runtime.taskexecutor.DefaultJobLeaderService [] - Resolved JobManager address, beginning registration",
            "2022-12-30 12:19:26,288 INFO  org.apache.flink.runtime.taskexecutor.DefaultJobLeaderService [] - Successful registration at job manager akka.tcp://flink@isxcode:34335/user/rpc/jobmanager_1 for job 5d94191b2621dddb7b0cafb9acec425f.",
            "2022-12-30 12:19:26,289 INFO  org.apache.flink.runtime.taskexecutor.TaskExecutor           [] - Establish JobManager connection for job 5d94191b2621dddb7b0cafb9acec425f.",
            "2022-12-30 12:19:26,291 INFO  org.apache.flink.runtime.taskexecutor.TaskExecutor           [] - Offer reserved slots to the leader of job 5d94191b2621dddb7b0cafb9acec425f.",
            "2022-12-30 12:19:26,320 INFO  org.apache.flink.runtime.taskexecutor.slot.TaskSlotTableImpl [] - Activate slot 25ca3895de9c0aa5ae702622b807ef0e.",
            "2022-12-30 12:19:26,334 INFO  org.apache.flink.runtime.state.changelog.StateChangelogStorageLoader [] - Creating a changelog storage with name 'memory'.",
            "2022-12-30 12:19:26,347 INFO  org.apache.flink.runtime.taskexecutor.TaskExecutor           [] - Received task Source: TableSourceScan(table=[[default_catalog, default_database, from_table]], fields=[username, age]) -> Sink: Sink(table=[default_catalog.default_database.to_table], fields=[username, age]) (1/1)#0 (cbbbbc39109beaba72ef6273b58a1d5e), deploy into slot with allocation id 25ca3895de9c0aa5ae702622b807ef0e.",
            "2022-12-30 12:19:26,348 INFO  org.apache.flink.runtime.taskmanager.Task                    [] - Source: TableSourceScan(table=[[default_catalog, default_database, from_table]], fields=[username, age]) -> Sink: Sink(table=[default_catalog.default_database.to_table], fields=[username, age]) (1/1)#0 (cbbbbc39109beaba72ef6273b58a1d5e) switched from CREATED to DEPLOYING.",
            "2022-12-30 12:19:26,350 INFO  org.apache.flink.runtime.taskmanager.Task                    [] - Loading JAR files for task Source: TableSourceScan(table=[[default_catalog, default_database, from_table]], fields=[username, age]) -> Sink: Sink(table=[default_catalog.default_database.to_table], fields=[username, age]) (1/1)#0 (cbbbbc39109beaba72ef6273b58a1d5e) [DEPLOYING].",
            "2022-12-30 12:19:26,351 INFO  org.apache.flink.runtime.taskexecutor.slot.TaskSlotTableImpl [] - Activate slot 25ca3895de9c0aa5ae702622b807ef0e.",
            "2022-12-30 12:19:26,384 INFO  org.apache.flink.streaming.runtime.tasks.StreamTask          [] - No state backend has been configured, using default (HashMap) org.apache.flink.runtime.state.hashmap.HashMapStateBackend@296a2550",
            "2022-12-30 12:19:26,384 INFO  org.apache.flink.runtime.state.StateBackendLoader            [] - State backend loader loads the state backend as HashMapStateBackend",
            "2022-12-30 12:19:26,386 INFO  org.apache.flink.streaming.runtime.tasks.StreamTask          [] - Checkpoint storage is set to 'jobmanager'",
            "2022-12-30 12:19:26,399 INFO  org.apache.flink.runtime.taskmanager.Task                    [] - Source: TableSourceScan(table=[[default_catalog, default_database, from_table]], fields=[username, age]) -> Sink: Sink(table=[default_catalog.default_database.to_table], fields=[username, age]) (1/1)#0 (cbbbbc39109beaba72ef6273b58a1d5e) switched from DEPLOYING to INITIALIZING.",
            "2022-12-30 12:19:26,556 WARN  org.apache.flink.metrics.MetricGroup                         [] - The operator name Sink: Sink(table=[default_catalog.default_database.to_table], fields=[username, age]) exceeded the 80 characters length limit and was truncated.",
            "2022-12-30 12:19:26,566 WARN  org.apache.flink.metrics.MetricGroup                         [] - The operator name Source: TableSourceScan(table=[[default_catalog, default_database, from_table]], fields=[username, age]) exceeded the 80 characters length limit and was truncated.",
            "2022-12-30 12:19:26,998 INFO  org.apache.flink.runtime.taskmanager.Task                    [] - Source: TableSourceScan(table=[[default_catalog, default_database, from_table]], fields=[username, age]) -> Sink: Sink(table=[default_catalog.default_database.to_table], fields=[username, age]) (1/1)#0 (cbbbbc39109beaba72ef6273b58a1d5e) switched from INITIALIZING to RUNNING.",
            "2022-12-30 12:19:27,053 WARN  org.apache.flink.runtime.taskmanager.Task                    [] - Source: TableSourceScan(table=[[default_catalog, default_database, from_table]], fields=[username, age]) -> Sink: Sink(table=[default_catalog.default_database.to_table], fields=[username, age]) (1/1)#0 (cbbbbc39109beaba72ef6273b58a1d5e) switched from RUNNING to FAILED with failure cause: java.lang.IllegalArgumentException: open() failed.Table 'ispong_db.users' doesn't exist",
            "\tat org.apache.flink.connector.jdbc.table.JdbcRowDataInputFormat.open(JdbcRowDataInputFormat.java:207)",
            "\tat org.apache.flink.streaming.api.functions.source.InputFormatSourceFunction.run(InputFormatSourceFunction.java:84)",
            "\tat org.apache.flink.streaming.api.operators.StreamSource.run(StreamSource.java:116)",
            "\tat org.apache.flink.streaming.api.operators.StreamSource.run(StreamSource.java:73)",
            "\tat org.apache.flink.streaming.runtime.tasks.SourceStreamTask$LegacySourceFunctionThread.run(SourceStreamTask.java:323)",
            "Caused by: java.sql.SQLSyntaxErrorException: Table 'ispong_db.users' doesn't exist",
            "\tat com.mysql.cj.jdbc.exceptions.SQLError.createSQLException(SQLError.java:120)",
            "\tat com.mysql.cj.jdbc.exceptions.SQLError.createSQLException(SQLError.java:97)",
            "\tat com.mysql.cj.jdbc.exceptions.SQLExceptionsMapping.translateException(SQLExceptionsMapping.java:122)",
            "\tat com.mysql.cj.jdbc.ClientPreparedStatement.executeInternal(ClientPreparedStatement.java:953)",
            "\tat com.mysql.cj.jdbc.ClientPreparedStatement.executeQuery(ClientPreparedStatement.java:1003)",
            "\tat org.apache.flink.connector.jdbc.table.JdbcRowDataInputFormat.open(JdbcRowDataInputFormat.java:204)",
            "\t... 4 more",
            "",
            "2022-12-30 12:19:27,054 INFO  org.apache.flink.runtime.taskmanager.Task                    [] - Freeing task resources for Source: TableSourceScan(table=[[default_catalog, default_database, from_table]], fields=[username, age]) -> Sink: Sink(table=[default_catalog.default_database.to_table], fields=[username, age]) (1/1)#0 (cbbbbc39109beaba72ef6273b58a1d5e).",
            "2022-12-30 12:19:27,059 INFO  org.apache.flink.runtime.taskexecutor.TaskExecutor           [] - Un-registering task and sending final execution state FAILED to JobManager for task Source: TableSourceScan(table=[[default_catalog, default_database, from_table]], fields=[username, age]) -> Sink: Sink(table=[default_catalog.default_database.to_table], fields=[username, age]) (1/1)#0 cbbbbc39109beaba72ef6273b58a1d5e.",
            "2022-12-30 12:19:27,159 INFO  org.apache.flink.runtime.taskexecutor.slot.TaskSlotTableImpl [] - Free slot TaskSlot(index:0, state:ACTIVE, resource profile: ResourceProfile{cpuCores=1, taskHeapMemory=384.000mb (402653174 bytes), taskOffHeapMemory=0 bytes, managedMemory=512.000mb (536870920 bytes), networkMemory=128.000mb (134217730 bytes)}, allocationId: 25ca3895de9c0aa5ae702622b807ef0e, jobId: 5d94191b2621dddb7b0cafb9acec425f).",
            "2022-12-30 12:19:27,167 INFO  org.apache.flink.runtime.taskexecutor.DefaultJobLeaderService [] - Remove job 5d94191b2621dddb7b0cafb9acec425f from job leader monitoring.",
            "2022-12-30 12:19:27,167 INFO  org.apache.flink.runtime.taskexecutor.TaskExecutor           [] - Close JobManager connection for job 5d94191b2621dddb7b0cafb9acec425f.",
            "2022-12-30 12:19:27,729 INFO  org.apache.flink.yarn.YarnTaskExecutorRunner                 [] - RECEIVED SIGNAL 15: SIGTERM. Shutting down as requested.",
            "2022-12-30 12:19:27,738 INFO  org.apache.flink.runtime.state.TaskExecutorLocalStateStoresManager [] - Shutting down TaskExecutorLocalStateStoresManager.",
            "2022-12-30 12:19:27,744 INFO  org.apache.flink.runtime.state.TaskExecutorStateChangelogStoragesManager [] - Shutting down TaskExecutorStateChangelogStoragesManager.",
            "2022-12-30 12:19:27,745 INFO  org.apache.flink.runtime.blob.TransientBlobCache             [] - Shutting down BLOB cache",
            "2022-12-30 12:19:27,746 INFO  org.apache.flink.runtime.filecache.FileCache                 [] - removed file cache directory /tmp/hadoop-ispong/nm-local-dir/usercache/ispong/appcache/application_1672365636481_0011/flink-dist-cache-c199f168-12b9-4819-b607-92891fac76bc",
            "2022-12-30 12:19:27,746 INFO  org.apache.flink.runtime.blob.PermanentBlobCache             [] - Shutting down BLOB cache",
            "2022-12-30 12:19:27,747 INFO  org.apache.flink.runtime.io.disk.FileChannelManagerImpl      [] - FileChannelManager removed spill file directory /tmp/hadoop-ispong/nm-local-dir/usercache/ispong/appcache/application_1672365636481_0011/flink-io-20a4e8a8-af62-42f2-ac17-e950b3630b0a",
            "2022-12-30 12:19:27,750 INFO  org.apache.flink.runtime.io.disk.FileChannelManagerImpl      [] - FileChannelManager removed spill file directory /tmp/hadoop-ispong/nm-local-dir/usercache/ispong/appcache/application_1672365636481_0011/flink-netty-shuffle-29993c5f-67e5-4783-a4a7-e40deecbbebf"
        ]
    }
}
```

## - 查看FlinkJob状态

```java
public AcornResponse getJobStatus(@RequestParam String flinkJobId) {

    return acornTemplate.build().jobId(flinkJobId).getJobStatus();
}
```

```json
{
    "code": "200",
    "msg": "获取flink作业状态",
    "data": {
        "jobStatus": {
            "jid": "ce1d15ddcf217da4fb5a8195dfbc14ed",
            "state": "FINISHED"
        }
    }
}
```

## - 查看FlinkJob异常

> `http://localhost:8080/getJobExceptions?flinkJobId=ce1d15ddcf217da4fb5a8195dfbc14ed`

```java
public AcornResponse getJobExceptions(@RequestParam String flinkJobId) {

    return acornTemplate.build().jobId(flinkJobId).getJobExceptions();
}
```

```json
{
    "code": "200",
    "msg": "获取flink作业异常日志",
    "data": {
        "jobExceptions": {
            "rootException": null
        }
    }
}
```

## - 停止作业

> `http://localhost:8080/stopYarnJob?applicationId=application_1671005804173_0008`

```java
AcornResponse acornResponse = acornTemplate.build()
    .applicationId("applicationId")
    .stopJob();
```

```json
{
  "code": "200",
  "msg": "停止作业",
  "data": {}
}
```

## - 查询数据

```java
AcornResponse acornResponse = acornTemplate.build()
    .applicationId("applicationId")
    .getData();
```

```json
{
  "code": "200",
  "msg": "停止作业",
  "data": {
    "columnNames": [
      "username",
      "age",
      "birth"
    ],
    "dataList": [
      [
        "ispong",
        "18",
        "2020-12-12"
      ]
    ]
  }
}
```

