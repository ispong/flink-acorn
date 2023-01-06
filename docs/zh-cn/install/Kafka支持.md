### Kafka支持

!> 默认flink是不支持kafka同步数据，需要额外下载依赖。

> [Kafka单节点安装参考文档](https://ispong.isxcode.com/hadoop/kafka/kafka%20%E5%8D%95%E8%8A%82%E7%82%B9%E5%AE%89%E8%A3%85/)

###### 下载flink kafka connector

> 需要下载对应版本的jar包

```bash
cd /opt/flink/lib/
wget https://repo1.maven.org/maven2/org/apache/flink/flink-connector-kafka_2.12/1.14.0/flink-connector-kafka_2.12-1.14.0.jar
```

###### FlinkSql样例

```sql
CREATE CATALOG from_db WITH (
    'type' = 'hive',
    'hive-conf-dir' = '/data/cloudera/parcels/CDH/lib/hive/conf/',
    'default-database' = 'ispong_db'
);
USE CATALOG from_db;
CREATE TABLE kafka_table
(
    username STRING,
    age      INT
) WITH (
      'connector' = 'kafka',
      'topic' = 'ispong-topic',
      'properties.bootstrap.servers' = 'dcloud-dev:30120',
      'properties.group.id' = 'test-consumer-group',
      'scan.startup.mode' = 'latest-offset',
      'format' = 'json'
      );

insert into users_to
select username, cast(null as int)
from kafka_table
```
