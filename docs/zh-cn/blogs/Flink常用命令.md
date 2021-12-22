##### 查询所有的job

```bash
flink list -a
```

##### flink shell

> 只有scala2.11版本的flink,有flink-shell。

```bash
# start-scala-shell.sh remote dcloud-dev 30114
start-scala-shell.sh remote ${host} ${port}
```

##### kafka同步到hive

```sql
-- hive
set table.sql-dialect=hive; -- 设置catalog类型 
USE cdh_dev; -- 使用数据源
DROP TABLE IF EXISTS ispong_table; --创建表
CREATE EXTERNAL TABLE hive_table (
    data_timestamp BIGINT,
    a STRING,
    b STRING
) PARTITIONED BY (`day` STRING, `hour` STRING) STORED AS PARQUET
TBLPROPERTIES (
	--这里支持filesystem connector的所有参数
        'parquet.compression'='SNAPPY',--压缩算法
        'sink.partition-commit.policy.kind' = 'metastore,success-file',--分区提交策略，自动创建分区和success文件
        'sink.partition-commit.success-file.name' = '_SUCCESS'
);


-- kafka
USE CATALOG default_catalog; --可选，使用默认catalog，也可以使用hive catalog
set table.sql-dialect=default;--这里必须切换到default dialect
DROP TABLE IF EXISTS kafka_table;
CREATE TABLE kafka_table (
    data_timestamp BIGINT,
    a STRING,
    b STRING
) WITH (
    'connector' = 'kafka',
    'topic' = 'topicx',
    'properties.bootstrap.servers' = 'xxx:9092',
    'properties.group.id' = 'topicx-groupid',
    'scan.startup.mode' = 'latest-offset',
    'format' = 'json'
);

-- sync
insert into hive_catalog.mydb.hive_table --如果使用default catalog，这里必须使用全限定名，使用hive catalog则不需要
select
    data_timestamp,
    a,
    b,
    from_unixtime(data_timestamp/1000,'yyyy-MM-dd') as `day`,
    from_unixtime(data_timestamp/1000,'HH') as `hour`
from kafka_table;
```
