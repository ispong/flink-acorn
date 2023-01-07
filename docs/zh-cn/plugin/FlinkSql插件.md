### FlinkSql插件

> Flink-Acorn默认支持该插件。

##### 使用方式

```java
acornTemplate.build().sql(flinkSql).deploy();
```

##### 支持场景

?> 以下为亲测可用场景。

!> Hive不支持跨服务器同步,即Flink-Acorn安装在A集群，只能访问A集群的Hive，不能访问B集群的Hive，哪怕是配置了B集群的hive-site.xml，也不好使。

| 来源数据库 | 目标数据库  | 可用状态 | 
|-------|--------|------|
| Mysql | Mysql  | ✅    |
| Mysql | Hive   | ✅    |
| Hive  | Mysql  | ✅    |
| Hive  | Hive   | ✅    |
| Kafka | Mysql  | ✅    |
| Kafka | Hive   | ✅    |
| Kafka | Kafka  | ✅    |
