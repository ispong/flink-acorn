## - 执行flink-sql

```java
public void executeSql() {

    String sql = " " +
        "  CREATE TABLE from_table ( " +
        "       username STRING, " +
        "       age INT" +
        "  ) WITH (" +
        "       'scan.startup.mode'='latest-offset'," +
        "       'properties.group.id'='test-consumer-group'," +
        "       'connector'='kafka'," +
        "       'topic'='acorn-input'," +
        "       'properties.zookeeper.connect'='127.0.0.1:2181'," +
        "       'properties.bootstrap.servers'='127.0.0.1:9092'," +
        "       'format'='csv'," +
        "       'csv.ignore-parse-errors' = 'true'" +
        "  ); " +
        "  CREATE TABLE to_table ( " +
        "        username STRING, " +
        "        age INT" +
        "  ) WITH (" +
        "       'scan.startup.mode'='latest-offset'," +
        "       'properties.group.id'='test-consumer-group'," +
        "       'connector'='kafka'," +
        "       'topic'='acorn-output'," +
        "       'properties.zookeeper.connect'='127.0.0.1:2181'," +
        "       'properties.bootstrap.servers'='127.0.0.1:9092'," +
        "       'format'='csv'," +
        "       'csv.ignore-parse-errors' = 'true'" +
        "  ); " +
        "  INSERT INTO to_table SELECT username,age FROM from_table WHERE age > 18;";

    AcornResponse acornResponse = acornTemplate.build()
        .executeId("custom_execute_id")
        .name("test_flink")
        .sql(sql)
        .execute();

    log.info("acornResponse {}", acornResponse.toString());
}

```

```log
acornResponse AcornResponse(code=200, message=操作成功, acornData=AcornData(jobId=null, jobInfo=null, jobLog=null, deployLog=null, jobInfoList=null, executeId=custom_execute_id, jobStatus=null))
```

## - 获取部署的日志

```java
public void getExecuteLog() {

    AcornResponse acornResponse = acornTemplate.build()
        .executeId("custom_execute_id")
        .getDeployLog();

    log.info("acornResponse {}", acornResponse.toString());
}
```

```log
 acornResponse AcornResponse(code=200, message=操作成功, acornData=AcornData(jobId=null, jobInfo=null, jobLog=null, deployLog=[INFO] Scanning for projects...
[INFO]                                                                         
[INFO] ------------------------------------------------------------------------
[INFO] Building acorn 0.0.1
[INFO] ------------------------------------------------------------------------
Downloading: https://repo.maven.apache.org/maven2/org/apache/logging/log4j/log4j-slf4j-impl/maven-metadata.xml
2/2 KB   
2/2 KB   
         
Downloaded: https://repo.maven.apache.org/maven2/org/apache/logging/log4j/log4j-slf4j-impl/maven-metadata.xml (2 KB at 1.1 KB/sec)
Downloading: https://repo.maven.apache.org/maven2/org/apache/logging/log4j/log4j-api/maven-metadata.xml
2/2 KB   
2/2 KB   
         
Downloaded: https://repo.maven.apache.org/maven2/org/apache/logging/log4j/log4j-api/maven-metadata.xml (2 KB at 3.9 KB/sec)
[INFO] 
[INFO] --- maven-clean-plugin:2.4.1:clean (default-clean) @ acorn ---
[INFO] 
[INFO] --- maven-resources-plugin:2.5:resources (default-resources) @ acorn ---
[debug] execute contextualize
[INFO] Using 'UTF-8' encoding to copy filtered resources.
[INFO] skip non existing resourceDirectory /tmp/flink-acorn/custom_execute_id/acorn/src/main/resources
[INFO] 
[INFO] --- maven-compiler-plugin:3.1:compile (default-compile) @ acorn ---
[INFO] Changes detected - recompiling the module!
[INFO] Compiling 1 source file to /tmp/flink-acorn/custom_execute_id/acorn/target/classes
[WARNING] /tmp/flink-acorn/custom_execute_id/acorn/src/main/java/com/isxcode/acorn/template/Acorn.java: /tmp/flink-acorn/custom_execute_id/acorn/src/main/java/com/isxcode/acorn/template/Acorn.java uses or overrides a deprecated API.
[WARNING] /tmp/flink-acorn/custom_execute_id/acorn/src/main/java/com/isxcode/acorn/template/Acorn.java: Recompile with -Xlint:deprecation for details.
[INFO] 
[INFO] --- maven-resources-plugin:2.5:testResources (default-testResources) @ acorn ---
[debug] execute contextualize
[INFO] Using 'UTF-8' encoding to copy filtered resources.
[INFO] skip non existing resourceDirectory /tmp/flink-acorn/custom_execute_id/acorn/src/test/resources
[INFO] 
[INFO] --- maven-compiler-plugin:3.1:testCompile (default-testCompile) @ acorn ---
[INFO] Not compiling test sources
[INFO] 
[INFO] --- maven-surefire-plugin:2.10:test (default-test) @ acorn ---
[INFO] Tests are skipped.
[INFO] 
[INFO] --- maven-jar-plugin:2.3.2:jar (default-jar) @ acorn ---
[INFO] Building jar: /tmp/flink-acorn/custom_execute_id/acorn/target/acorn.jar
[INFO] 
[INFO] --- maven-shade-plugin:3.1.1:shade (default) @ acorn ---
[INFO] Including com.fasterxml.jackson.core:jackson-databind:jar:2.9.10.4 in the shaded jar.
[INFO] Including com.fasterxml.jackson.core:jackson-annotations:jar:2.9.10 in the shaded jar.
[INFO] Including com.fasterxml.jackson.core:jackson-core:jar:2.9.10 in the shaded jar.
[INFO] Including org.apache.flink:flink-connector-kafka_2.12:jar:1.14.0 in the shaded jar.
[INFO] Including org.apache.flink:flink-connector-base:jar:1.14.0 in the shaded jar.
[INFO] Including org.apache.kafka:kafka-clients:jar:2.4.1 in the shaded jar.
[INFO] Including com.github.luben:zstd-jni:jar:1.4.3-1 in the shaded jar.
[INFO] Including org.lz4:lz4-java:jar:1.6.0 in the shaded jar.
[INFO] Including org.xerial.snappy:snappy-java:jar:1.1.7.3 in the shaded jar.
[INFO] Including org.apache.flink:flink-shaded-force-shading:jar:14.0 in the shaded jar.
[INFO] Including org.apache.flink:flink-json:jar:1.14.0 in the shaded jar.
[INFO] Excluding com.google.code.findbugs:jsr305:jar:1.3.9 from the shaded jar.
[INFO] Including org.apache.flink:flink-connector-jdbc_2.12:jar:1.14.0 in the shaded jar.
[INFO] Including com.h2database:h2:jar:1.4.200 in the shaded jar.
[INFO] Including org.apache.flink:flink-sql-connector-kafka_2.12:jar:1.14.0 in the shaded jar.
[INFO] Including mysql:mysql-connector-java:jar:8.0.27 in the shaded jar.
[INFO] Including com.google.protobuf:protobuf-java:jar:3.11.4 in the shaded jar.
[INFO] Including org.apache.flink:flink-core:jar:1.14.0 in the shaded jar.
[INFO] Including org.apache.flink:flink-annotations:jar:1.14.0 in the shaded jar.
[INFO] Including org.apache.flink:flink-metrics-core:jar:1.14.0 in the shaded jar.
[INFO] Including com.esotericsoftware.kryo:kryo:jar:2.24.0 in the shaded jar.
[INFO] Including com.esotericsoftware.minlog:minlog:jar:1.2 in the shaded jar.
[INFO] Including org.objenesis:objenesis:jar:2.1 in the shaded jar.
[INFO] Including commons-collections:commons-collections:jar:3.2.2 in the shaded jar.
[INFO] Including org.apache.commons:commons-lang3:jar:3.3.2 in the shaded jar.
[INFO] Including org.apache.commons:commons-math3:jar:3.5 in the shaded jar.
[INFO] Excluding org.slf4j:slf4j-api:jar:1.7.15 from the shaded jar.
[INFO] Including org.apache.flink:flink-streaming-java_2.12:jar:1.14.0 in the shaded jar.
[INFO] Including org.apache.flink:flink-file-sink-common:jar:1.14.0 in the shaded jar.
[INFO] Including org.apache.flink:flink-runtime:jar:1.14.0 in the shaded jar.
[INFO] Including org.apache.flink:flink-rpc-core:jar:1.14.0 in the shaded jar.
[INFO] Including org.apache.flink:flink-rpc-akka-loader:jar:1.14.0 in the shaded jar.
[INFO] Including org.apache.flink:flink-queryable-state-client-java:jar:1.14.0 in the shaded jar.
[INFO] Including org.apache.flink:flink-hadoop-fs:jar:1.14.0 in the shaded jar.
[INFO] Including org.apache.flink:flink-shaded-netty:jar:4.1.65.Final-14.0 in the shaded jar.
[INFO] Including org.apache.flink:flink-shaded-jackson:jar:2.12.4-14.0 in the shaded jar.
[INFO] Including org.apache.flink:flink-shaded-zookeeper-3:jar:3.4.14-14.0 in the shaded jar.
[INFO] Including org.javassist:javassist:jar:3.24.0-GA in the shaded jar.
[INFO] Including org.apache.flink:flink-scala_2.12:jar:1.14.0 in the shaded jar.
[INFO] Including com.twitter:chill_2.12:jar:0.7.6 in the shaded jar.
[INFO] Including com.twitter:chill-java:jar:0.7.6 in the shaded jar.
[INFO] Including org.apache.flink:flink-shaded-guava:jar:30.1.1-jre-14.0 in the shaded jar.
[INFO] Including commons-cli:commons-cli:jar:1.3.1 in the shaded jar.
[INFO] Including org.scala-lang:scala-reflect:jar:2.12.7 in the shaded jar.
[INFO] Including org.scala-lang:scala-library:jar:2.12.7 in the shaded jar.
[INFO] Including org.scala-lang:scala-compiler:jar:2.12.7 in the shaded jar.
[INFO] Including org.scala-lang.modules:scala-xml_2.12:jar:1.0.6 in the shaded jar.
[INFO] Including org.apache.flink:flink-connector-files:jar:1.14.0 in the shaded jar.
[INFO] Including org.apache.flink:flink-shaded-asm-7:jar:7.1-14.0 in the shaded jar.
[INFO] Excluding org.apache.logging.log4j:log4j-slf4j-impl:jar:2.18.0 from the shaded jar.
[INFO] Excluding org.apache.logging.log4j:log4j-api:jar:2.18.0 from the shaded jar.
[INFO] Excluding org.apache.logging.log4j:log4j-core:jar:2.18.0 from the shaded jar.
[INFO] Including org.apache.flink:flink-connector-hive_2.12:jar:1.14.0 in the shaded jar.
[INFO] Including commons-io:commons-io:jar:2.4 in the shaded jar.
[INFO] Including org.apache.commons:commons-compress:jar:1.9 in the shaded jar.
[WARNING] flink-sql-connector-kafka_2.12-1.14.0.jar, flink-connector-kafka_2.12-1.14.0.jar define 197 overlapping classes: 
[WARNING]   - org.apache.flink.streaming.connectors.kafka.internals.Handover$WakeupException
[WARNING]   - org.apache.flink.streaming.connectors.kafka.table.KafkaDynamicSource$ReadableMetadata$6
[WARNING]   - org.apache.flink.streaming.connectors.kafka.config.OffsetCommitModes
[WARNING]   - org.apache.flink.streaming.connectors.kafka.table.KafkaDynamicSink$KafkaHeader
[WARNING]   - org.apache.flink.streaming.connectors.kafka.partitioner.FlinkKafkaPartitioner
[WARNING]   - org.apache.flink.connector.kafka.source.enumerator.KafkaSourceEnumerator$PartitionOffsetsRetrieverImpl
[WARNING]   - org.apache.flink.connector.kafka.sink.KafkaCommittable
[WARNING]   - org.apache.flink.streaming.connectors.kafka.internals.KafkaCommitCallback
[WARNING]   - org.apache.flink.connector.kafka.sink.DefaultKafkaSinkContext
[WARNING]   - org.apache.flink.streaming.connectors.kafka.internals.KafkaFetcher
[WARNING]   - 187 more...
[WARNING] maven-shade-plugin has detected that some class files are
[WARNING] present in two or more JARs. When this happens, only one
[WARNING] single version of the class is copied to the uber jar.
[WARNING] Usually this is not harmful and you can skip these warnings,
[WARNING] otherwise try to manually exclude artifacts based on
[WARNING] mvn dependency:tree -Ddetail=true and the above output.
[WARNING] See http://maven.apache.org/plugins/maven-shade-plugin/
[INFO] Replacing original artifact with shaded artifact.
[INFO] Replacing /tmp/flink-acorn/custom_execute_id/acorn/target/acorn.jar with /tmp/flink-acorn/custom_execute_id/acorn/target/acorn-0.0.1-shaded.jar
[INFO] Dependency-reduced POM written at: /tmp/flink-acorn/custom_execute_id/acorn/dependency-reduced-pom.xml
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 11.136s
[INFO] Finished at: Thu Aug 11 16:56:09 CST 2022
[INFO] Final Memory: 168M/1315M
[INFO] ------------------------------------------------------------------------
SLF4J: Class path contains multiple SLF4J bindings.
SLF4J: Found binding in [jar:file:/data/flink-1.14.0/lib/log4j-slf4j-impl-2.14.1.jar!/org/slf4j/impl/StaticLoggerBinder.class]
SLF4J: Found binding in [jar:file:/data/hadoop/hadoop-3.2.2/share/hadoop/common/lib/slf4j-log4j12-1.7.25.jar!/org/slf4j/impl/StaticLoggerBinder.class]
SLF4J: See http://www.slf4j.org/codes.html#multiple_bindings for an explanation.
SLF4J: Actual binding is of type [org.apache.logging.slf4j.Log4jLoggerFactory]
Job has been submitted with JobID f453055bf7f6bc8e56eef06ca7a8b633, jobInfoList=null, executeId=null, jobStatus=null))
```

## - 获取flink的jobId

```java
public void getJobId() {

    AcornResponse acornResponse = acornTemplate.build()
        .executeId("custom_execute_id")
        .getJobId();

    log.info("acornResponse {}", acornResponse.toString());
}
```

```log
acornResponse AcornResponse(code=200, message=操作成功, acornData=AcornData(jobId=f453055bf7f6bc8e56eef06ca7a8b633, jobInfo=null, jobLog=null, deployLog=null, jobInfoList=null, executeId=null, jobStatus=null))
```

## - 获取flink作业的状态

```java
public void getJobStatus() {

    AcornResponse acornResponse = acornTemplate.build()
        .jobId("f453055bf7f6bc8e56eef06ca7a8b633")
        .getJobStatus();

    log.info("acornResponse {}", acornResponse.toString());
}
```

```log

```