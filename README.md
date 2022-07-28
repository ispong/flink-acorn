<h1 align="center">
    Flink Acorn
</h1>

<h3 align="center">
    🐿️ flink on spring 快速集成插件
</h3>

<h4 align="center">
    ✨✨✨ <a href="https://flink-acorn.isxcode.com" > 
         https://flink-acorn.isxcode.com
    </a> ✨✨✨
</h4>

<h2></h2>

### 📢 公告

- 目前支持**flink-1.14.0-scala-2.12**

### 📒 相关文档

- [快速使用](https://flink-acorn.isxcode.com/#/zh-cn/start/快速使用)
- [维护手册](https://flink-acorn.isxcode.com/#/zh-cn/start/contributing)
- [版本历史](https://flink-acorn.isxcode.com/#/zh-cn/start/changelog)

### 本地文档

```bash
git clone https://github.com/ispong/flink-acorn.git
npm install docsify-cli --location=global
docsify serve docs
```

### 📦 安装使用

[![Maven Version](https://img.shields.io/maven-central/v/com.isxcode.acorn/acorn-common)](https://search.maven.org/artifact/com.isxcode.acorn/acorn-common)

```xml
<dependency>
    <groupId>com.isxcode.acorn</groupId>
    <artifactId>acorn-common</artifactId>
    <version>0.0.1</version>
</dependency>
```

```java
class demo {

    private final AcornTemplate acornTemplate;

    public void execute() {

        String sql1 = " CREATE TABLE from_table ( " +
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
                " )" ;

        String sql2 = "   CREATE TABLE to_table ( " +
                "        username STRING, " +
                "        age INT" +
                "     ) WITH (" +
                "        'connector'='jdbc','url'='jdbc:mysql://localhost:30102/acorn'," +
                "        'table-name'='flink_test_table'," +
                "        'driver'='com.mysql.cj.jdbc.Driver'," +
                "        'username'='root'," +
                "        'password'='acorn'" +
                "  )";

        String sql3 = "   INSERT INTO to_table SELECT username,age FROM from_table WHERE age >19";

        acornTemplate.build("127.0.0.1", 30155, "acorn-key")
                .executeId("custom_id")
                .name("ispong_test_flink")
                .sql(sql1 + sql2 + sql3).execute();
    }
    
}
```

### 👏 社区开发

欢迎大家一同维护开发，请参照具体[开发文档](https://github.com/ispong/flink-acorn/blob/main/CONTRIBUTING.md) 。
如需加入我们，请联系邮箱 ispong@outlook.com 。
