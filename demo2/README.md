---
title: Flink kafka同步到mysql方案
subtitle: Flink kafka同步到mysql方案
tags:
- flink
  categories:
- Flink
  index_img: 'https://gitee.com/isxcode/blogs-galaxy-images/raw/master/flink/flink.png'
  mermaid: false
  math: false
  hide: false
  comments: true
  date: 2021-08-09 11:17:40
---

## 🧙 Questions

> kafka同步到mysql
> Note:
> 当向lib中添加依赖的话，一定要重启flink

## ☄️ Ideas

##### 初始化项目

```bash
mvn archetype:generate -DarchetypeGroupId=org.apache.flink -DarchetypeArtifactId=flink-quickstart-java -DarchetypeVersion=1.12.3 -DgroupId=com.definesys -DartifactId=dehoop-flink-template -Dversion=0.1 -Dpackage=com.definesys -DinteractiveMode=false
```

##### 修改依赖

```bash
vim pom.xml
```

```xml
<properties>
  <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
  <flink.version>1.12.5</flink.version>
  <target.java.version>1.8</target.java.version>
  <scala.binary.version>2.11</scala.binary.version>
  <maven.compiler.source>${target.java.version}</maven.compiler.source>
  <maven.compiler.target>${target.java.version}</maven.compiler.target>
  <log4j.version>2.13.2</log4j.version>
</properties>

<dependencies>

  <!-- mysql-connector-java -->
  <dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.23</version>
  </dependency>

  <!-- flink-connector-jdbc -->
  <dependency>
    <groupId>org.apache.flink</groupId>
    <artifactId>flink-connector-jdbc_${scala.binary.version}</artifactId>
    <version>${flink.version}</version>
  </dependency>

  <!-- flink-sql-connector-kafka -->
  <dependency>
    <groupId>org.apache.flink</groupId>
    <artifactId>flink-sql-connector-kafka_${scala.binary.version}</artifactId>
    <version>${flink.version}</version>
  </dependency>

  <!-- flink-table-planner-blink -->
  <dependency>
    <groupId>org.apache.flink</groupId>
    <artifactId>flink-table-planner-blink_${scala.binary.version}</artifactId>
    <version>${flink.version}</version>
    <scope>provided</scope>
  </dependency>

  <!-- flink-table-api-java-bridge -->
  <dependency>
          <groupId>org.apache.flink</groupId>
          <artifactId>flink-table-api-java-bridge_${scala.binary.version}</artifactId>
          <version>${flink.version}</version>
          <scope>provided</scope>
  </dependency>

  <dependency>
    <groupId>org.apache.flink</groupId>
    <artifactId>flink-streaming-scala_${scala.binary.version}</artifactId>
    <version>${flink.version}</version>
  </dependency>

  <dependency>
    <groupId>org.apache.flink</groupId>
    <artifactId>flink-table-common</artifactId>
    <version>${flink.version}</version>
    <scope>provided</scope>
  </dependency>

</dependencies>  
```

##### 代码

```bash
vim Demo.java
```

```java
package com.isxcode;

import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableEnvironment;

import static org.apache.flink.table.api.Expressions.$;

public class Demo {
    public static void main(String[] args) {

        EnvironmentSettings settings = EnvironmentSettings.newInstance().build();
        TableEnvironment tEnv = TableEnvironment.create(settings);
        tEnv.getConfig().getConfiguration().setString("pipeline.name", "demo-pipeline");

        tEnv.executeSql("CREATE TABLE from_kafka (\n" +
                "   username STRING," +
                "   age INT"+
                ") WITH (\n" +
                "  'connector'='kafka'," +
                "  'topic'='ispong_kafka'," +
                "  'properties.zookeeper.connect'='39.103.230.188:30121'," +
                "  'properties.bootstrap.servers'='39.103.230.188:30120'," +
                "  'format'='csv'"+
                ")");

        tEnv.executeSql("CREATE TABLE to_mysql (\n" +
                "  username varchar(100)," +
                "  age int"+
                ") WITH (\n" +
                "  'connector'='jdbc'," +
                "  'url'='jdbc:mysql://47.103.203.73:3306/VATtest'," +
                "  'table-name'='ispong_demo'," +
                "  'driver'='com.mysql.cj.jdbc.Driver'," +
                "  'username'='admin','password'='gsw921226'"+
                ")");

        Table fromData = tEnv.from("from_kafka");

        // age大于20
        fromData = fromData.where($("age").isGreater(20));
        fromData = fromData.select($("username").as("username"),$("age").as("age"));

        fromData.executeInsert("to_mysql");
    }

}
```

##### 修改启动类

```bash
vim pom.xml
```

```xml
<transformers>
  <transformer implementation="org.apache.maven.plugins.shade.resource.ManifestResourceTransformer">
    <mainClass>com.isxcode.Demo</mainClass>
  </transformer>
</transformers>
```

##### 添加环境需要的jar包

```bash
sudo cp /home/dehoop/.m2/repository/org/apache/flink/flink-sql-connector-kafka_2.11/1.12.4/flink-sql-connector-kafka_2.11-1.12.4.jar /opt/flink/lib/

#   sudo cp /home/dehoop/.m2/repository/org/apache/flink/flink-connector-jdbc_2.11/1.12.4/flink-connector-jdbc_2.11-1.12.4.jar /opt/flink/lib/
```

##### 打包运行

```bash
mvn clean package
flink run flink-demo-0.1.jar
```

## 🔗 Links

- [flink website](https://flink.apache.org/)
- [flink table api demo](https://ci.apache.org/projects/flink/flink-docs-release-1.12/try-flink/table_api.html)