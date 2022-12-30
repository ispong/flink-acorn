### Jdbc扩展

!> 默认flink是不支持hive数据同步的，需要额外下载依赖。

> [Hive单节点安装参考文档](https://ispong.isxcode.com/hadoop/hive/hive%20%E5%8D%95%E8%8A%82%E7%82%B9%E5%AE%89%E8%A3%85/)

###### 下载flink hive connector

> 需要下载对应版本的jar包

```bash
cd /opt/flink/lib/
wget https://repo1.maven.org/maven2/org/apache/flink/flink-connector-hive_2.12/1.14.0/flink-connector-hive_2.12-1.14.0.jar
```

###### 修改hive配置文件

```bash
vim /opt/acorn/conf/hive-site.xml
```

###### FlinkSql 案例

- hive --> mysql

```sql
CREATE CATALOG from_db WITH (
    'type' = 'hive',
    'hive-conf-dir' = '/opt/acorn/conf',
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

insert into to_table select username, age from from_db.users;
```
