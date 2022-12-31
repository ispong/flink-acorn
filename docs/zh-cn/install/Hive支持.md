### Jdbc扩展

!> 默认flink是不支持hive数据同步的，需要额外下载依赖。

> [Hive单节点安装参考文档](https://ispong.isxcode.com/hadoop/hive/hive%20%E5%8D%95%E8%8A%82%E7%82%B9%E5%AE%89%E8%A3%85/)

###### 下载flink hive connector

> 需要下载对应版本的jar包

```bash
cd /opt/flink/lib/
wget https://repo1.maven.org/maven2/org/apache/flink/flink-connector-hive_2.12/1.14.0/flink-connector-hive_2.12-1.14.0.jar
```

###### 下载hive-exec

```bash
cd /opt/flink/lib/
wget https://repo1.maven.org/maven2/org/apache/hive/hive-exec/3.1.2/hive-exec-3.1.2.jar
wget https://repo1.maven.org/maven2/org/apache/thrift/libfb303/0.9.3/libfb303-0.9.3.jar
```

###### 

```bash
vim /opt/flink/conf/flink-conf.yaml
```

```yml
classloader.resolve-order: parent-first
classloader.check-leaked-classloader: false
```

###### 和hadoop中的guava版本保持一致

```bash
cp /opt/hadoop/share/hadoop/hdfs/lib/guava-*.jar /opt/flink/lib/
cp /opt/hadoop/share/hadoop/common/hadoop-common-*.jar /opt/flink/lib/
cp /opt/hadoop/share/hadoop/mapreduce/hadoop-mapreduce-client-core-*.jar /opt/flink/lib/
cp /opt/hadoop/share/hadoop/mapreduce/hadoop-mapreduce-client-jobclient-*.jar /opt/flink/lib/
cp /opt/hadoop/share/hadoop/mapreduce/hadoop-mapreduce-client-common-*.jar /opt/flink/lib/
```

###### 修改hive配置文件

> 新增配置 `hive.metastore.uris`

```bash
vim /opt/hive/conf/hive-site.xml
```

```xml
<configuration>
    <property>
        <name>hive.metastore.port</name>
        <value>9083</value>
    </property>

    <property>
        <name>hive.metastore.uris</name>
        <value>thrift://isxcode:9083</value>
    </property>
</configuration>
```

###### FlinkSql 案例

- hive同步到mysql

```sql
CREATE CATALOG from_db WITH (
    'type' = 'hive',
    'hive-conf-dir' = '/opt/hive/conf',
    'default-database' = 'ispong_db'
);

USE CATALOG from_db;
            
CREATE TABLE to_table(
    username STRING,
    age INT
) WITH (
    'connector'='jdbc',
    'url'='jdbc:mysql://isxcode:30306/ispong_db',
    'table-name'='users_sink',
    'driver'='com.mysql.cj.jdbc.Driver',
    'username'='root',
    'password'='ispong123');

insert into to_table select username, age from users;
```
