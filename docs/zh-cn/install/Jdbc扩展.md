### Jdbc扩展

> 下面以Mysql数据库为例，[Mysql安装参考文档](https://ispong.isxcode.com/spring/mysql/mysql%20docker%E5%AE%89%E8%A3%85/)。

!> 默认flink是不支持jdbc同步数据的，需要格外下载依赖。

###### 下载jdbc驱动

```bash
wget https://dev.mysql.com/get/Downloads/Connector-J/mysql-connector-java-8.0.22.tar.gz
tar -vzxf mysql-connector-java-8.0.22.tar.gz 
mv mysql-connector-java-8.0.22/mysql-connector-java-8.0.22.jar /opt/flink/lib/
```

###### 下载flink jdbc connector

> 需要下载对应版本的jar包

```bash
cd /opt/flink/lib/
wget https://repo1.maven.org/maven2/org/apache/flink/flink-connector-jdbc_2.12/1.14.0/flink-connector-jdbc_2.12-1.14.0.jar
```
