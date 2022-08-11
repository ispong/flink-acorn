<h1 align="center">
    Flink Acorn
</h1>

<h3 align="center">
    🐿️ ️ 对不同服务器上不同版本的flink做统一管理，并提供api与现有服务做无缝集成。
</h3>

<h2></h2>

### 📢 公告

- 目前仅支持 **flink-1.14.0-scala-2.12** 版本

### 📒 相关文档

- [快速使用](https://flink-acorn.isxcode.com/#/zh-cn/start/快速使用)
- [维护手册](https://flink-acorn.isxcode.com/#/zh-cn/start/contributing)
- [版本历史](https://flink-acorn.isxcode.com/#/zh-cn/start/changelog)

### 📦 使用说明

[![Maven Version](https://img.shields.io/maven-central/v/com.isxcode.acorn/acorn-common)](https://search.maven.org/artifact/com.isxcode.acorn/acorn-common)

```xml
<dependency>
    <groupId>com.isxcode.acorn</groupId>
    <artifactId>acorn-common</artifactId>
    <version>1.1.2</version>
</dependency>
```

```yml
acorn:
  workers:
    default:
      host: 192.168.66.66
      port: 30155
      key: acorn-key
```

```java
class demo {

    private final AcornTemplate acornTemplate;

    public void executeSql() {

        String sql = " " +
                "  CREATE TABLE from_table ( " +
                "       username STRING, " +
                "       age INT" +
                "  ) WITH (" +
                "       'scan.startup.mode'='latest-offset'," +
                "       'properties.group.id'='test-consumer-group'," +
                "       'connector'='kafka'," +
                "       'topic'='acorn-topic'," +
                "       'properties.zookeeper.connect'='localhost:2181'," +
                "       'properties.bootstrap.servers'='localhost:9092'," +
                "       'format'='csv'," +
                "       'csv.ignore-parse-errors' = 'true'" +
                "  ); " +
                "  CREATE TABLE to_table ( " +
                "        username STRING, " +
                "        age INT" +
                "  ) WITH (" +
                "        'connector'='jdbcnk_test_table'," +
                "        'driver'='com.mys','url'='jdbc:mysql://localhost:30102/acorn'," +
                "        'table-name'='fliql.cj.jdbc.Driver'," +
                "        'username'='root'," +
                "        'password'='acorn'" +
                "  ); " +
                "  INSERT INTO to_table SELECT username,age FROM from_table WHERE age >19;";

        AcornResponse acornResponse = acornTemplate.build()
                .executeId("custom_execute_id")
                .name("ispong_test_flink")
                .sql(sql)
                .execute();
        
        log.info("acornResponse {}", acornResponse.toString());
    }
}
```

```log
2022-08-01 14:22:41.165  INFO 4540 --- [nio-8080-exec-1] c.i.a.t.controller.TemplateController    : acornResponse AcornResponse(code=200, message=操作成功, acornData=AcornData(jobId=null, jobInfo=null, jobLog=null, deployLog=null, jobInfoList=null, executeId=custom_execute_id    , jobStatus=null))
```

### 👏 社区开发

- 欢迎大家一同维护开发，请参照具体[开发文档](https://flink-acorn.isxcode.com/#/zh-cn/contributing.md) 。
- 如需加入我们，请联系邮箱 ispong@outlook.com 。
