### Jdbc扩展

!> 默认flink是不支持jdbc同步数据的，需要额外下载依赖。

> 下面以Mysql数据库为例，[Mysql安装参考文档](https://ispong.isxcode.com/spring/mysql/mysql%20docker%E5%AE%89%E8%A3%85/)。

###### 下载jdbc驱动

```bash
wget https://dev.mysql.com/get/Downloads/Connector-J/mysql-connector-java-8.0.22.tar.gz
tar -vzxf mysql-connector-java-8.0.22.tar.gz 
mv mysql-connector-java-8.0.22/mysql-connector-java-8.0.22.jar /opt/flink/lib/
```

###### FlinkSql样例

```sql
CREATE TABLE from_table(
  username STRING,
  age INT
) WITH (
    'connector'='jdbc',
    'url'='jdbc:mysql://isxcode:30306/ispong_db',
    'table-name'='users',
    'driver'='com.mysql.cj.jdbc.Driver',
    'username'='root',
    'password'='ispong123');

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

insert into to_table select username, age from from_table;
```
