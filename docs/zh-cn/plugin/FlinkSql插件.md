### FlinkSql插件

> Flink-Acorn默认支持该插件。

##### 使用方式

```java
acornTemplate.build().sql(flinkSql).deploy();
```

##### 支持场景

?> 以下为亲测可用场景。


| 来源数据库 | 目标数据库  | 可用状态 | 
|-------|--------|------|
| Mysql | Mysql  | ✅    |
| Mysql | Hive   | ✅    |
| Hive  | Mysql  | ✅    |
| Hive  | Hive   | ✅    |
| Kafka | Mysql  | ✅    |
| Kafka | Hive   | ✅    |
| Kafka | Kafka  | ✅    |
