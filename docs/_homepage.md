Flink Acorn 是什么


?>让Spring更好的集成flink组件，且让Spring支持更多版本的flink组件。

##### 使用案例

###### 支持flink sql

```java

```

###### 支持flink java

```java

```

###### 支持flink json

```java
class demo{
    
    public void executeJson() {

        String jsonConfig = "{\n" +
            "  \"jobName\": \"acorn-test\",\n" +
            "  \"flinkSqlList\": [\n" +
            "      \"CREATE TABLE from_table ( " +
            "           username STRING, " +
            "           age INT" +
            "       ) WITH (" +
            "           'scan.startup.mode'='latest-offset'," +
            "           'properties.group.id'='test-consumer-group'," +
            "           'connector'='kafka'," +
            "           'topic'='acorn-topic'," +
            "           'properties.zookeeper.connect'='localhost:2181'," +
            "           'properties.bootstrap.servers'='172.26.34.172:9092'," +
            "           'format'='csv'," +
            "           'csv.ignore-parse-errors' = 'true'" +
            "       )\",\n" +
            "      \"CREATE TABLE to_table ( " +
            "           username STRING, " +
            "           age INT" +
            "       ) WITH (" +
            "           'connector'='jdbc','url'='jdbc:mysql://localhost:30102/acorn'," +
            "           'table-name'='flink_test_table'," +
            "           'driver'='com.mysql.cj.jdbc.Driver'," +
            "           'username'='root'," +
            "           'password'='acorn'" +
            "       )\",\n" +
            "      \"INSERT INTO to_table SELECT username,age FROM from_table WHERE age >19\"\n" +
            "  ]\n" +
            "}";

        AcornRequest acornRequest = AcornRequest.builder()
            .executeId(String.valueOf(UUID.randomUUID()))
            .json(jsonConfig)
            .build();

        log.info(acornTemplate.build().executeJson(acornRequest).toString());
    }
}
```
