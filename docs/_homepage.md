#### Flink Acorn 是什么

?>让Spring更好更快的集成Flink组件，且让Spring支持更多版本的Flink组件。

###### flink-sql用法

```java
class demo{
    public void test() {
        List<String> sqlList = new ArrayList<>();
        sqlList.add(0, " CREATE TABLE from_table ( " +
            "       username STRING, " +
            "       age INT" +
            "    ) WITH (" +
            "       'scan.startup.mode'='latest-offset'," +
            "       'properties.group.id'='test-consumer-group'," +
            "       'connector'='kafka'," +
            "       'topic'='acorn-topic'," +
            "       'properties.zookeeper.connect'='localhost:2181'," +
            "       'properties.bootstrap.servers'='172.26.34.172:9092'," +
            "       'format'='csv'," +
            "       'csv.ignore-parse-errors' = 'true'" +
            " )");
        sqlList.add(1, "   CREATE TABLE to_table ( " +
            "        username STRING, " +
            "        age INT" +
            "     ) WITH (" +
            "        'connector'='jdbc','url'='jdbc:mysql://localhost:30102/acorn'," +
            "        'table-name'='flink_test_table'," +
            "        'driver'='com.mysql.cj.jdbc.Driver'," +
            "        'username'='root'," +
            "        'password'='acorn'" +
            "  )");
        sqlList.add(2, "   INSERT INTO to_table SELECT username,age FROM from_table WHERE age >19");

        AcornRequest acornRequest = AcornRequest.builder()
            .executeId(String.valueOf(UUID.randomUUID()))
            .sqlList(sqlList)
            .build();

        log.info(acornTemplate.build("inner").executeSql(acornRequest).toString());
    }
}
```

###### flink-java用法

```java
class demo{
    public void test() {
        String javaCode = "" +
            "import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;\n" +
            "import org.apache.flink.table.api.EnvironmentSettings;\n" +
            "import org.apache.flink.table.api.Table;\n" +
            "import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;\n" +
            "\n" +
            "public class Acorn {\n" +
            "\n" +
            "    public static void main(String[] args) {\n" +
            "\n" +
            "        StreamExecutionEnvironment bsEnv = StreamExecutionEnvironment.getExecutionEnvironment();\n" +
            "        bsEnv.enableCheckpointing(5000);\n" +
            "        EnvironmentSettings settings = EnvironmentSettings.newInstance().useBlinkPlanner().inStreamingMode().build();\n" +
            "        StreamTableEnvironment tEnv = StreamTableEnvironment.create(bsEnv,settings);\n" +
            "        tEnv.getConfig().getConfiguration().setString(\"pipeline.name\", \"acorn-test\");\n" +
            "\n" +
            "        tEnv.executeSql(\"CREATE TABLE from_table ( username STRING, age INT) WITH ('scan.startup.mode'='latest-offset','properties.group.id'='test-consumer-group','connector'='kafka','topic'='flink-topic','properties.zookeeper.connect'='172.26.34.170:2181','properties.bootstrap.servers'='172.26.34.170:9092','format'='csv','csv.ignore-parse-errors' = 'true')\");\n" +
            "        tEnv.executeSql(\"CREATE TABLE to_table ( username STRING, age INT) WITH ('connector'='jdbc','url'='jdbc:mysql://172.26.34.170:30102/flink','table-name'='flink_test_table','driver'='com.mysql.cj.jdbc.Driver','username'='root','password'='flink2022')\");\n" +
            "        tEnv.executeSql(\"INSERT INTO to_table SELECT username,age FROM from_table WHERE age >19\");\n" +
            "    }\n" +
            "}\n";

        AcornRequest acornRequest = AcornRequest.builder()
            .executeId(String.valueOf(UUID.randomUUID()))
            .java(javaCode)
            .build();

        log.info(acornTemplate.build("inner").executeSql(acornRequest).toString());
    }
}
```
