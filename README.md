<h1 align="center">
    Flink Acorn
</h1>

<h3 align="center">
    🐿️ Flink + Spring Boot 快速集成，支持flink-sql、flink-java
</h3>

<h4 align="center">
    ✨✨✨ <a href="https://ispong.github.io/flink-acorn" > 
         https://ispong.github.io/flink-acorn
    </a> ✨✨✨
</h4>

<h2></h2>

### 📢 公告

目前，插件主要针对**flink-1.14.0-scala-2.12**版本进行开发。

### ✨ 模块说明

| 模块名                                                    | 状态                 | 说明                                    |
|:-------------------------------------------------------|--------------------|:--------------------------------------|
| [acorn-common](https://ispong.github.io/flink-acorn)   | :white_check_mark: | 提供AcornTemplate组件，方便客户端直接调用Acorn插件服务。 |
| [acorn-plugin](https://ispong.github.io/flink-acorn)   | :white_check_mark: | Acorn服务器插件。                           |
| [acorn-template](https://ispong.github.io/flink-acorn) | :white_check_mark: | 演示客户端如何使用插件。                          |
| [demos](https://ispong.github.io/flink-acorn)          | :white_check_mark: | 各种flink相关的小demo。                      |

### 📒 相关文档

- [快速使用](https://ispong.github.io/flink-acorn/#/zh-cn/start/快速使用)
- [维护手册](https://ispong.github.io/flink-acorn/#/zh-cn/contributing)
- [版本历史](https://ispong.github.io/flink-acorn/#/zh-cn/changelog)

### 📦 安装使用

[![Maven Version](https://img.shields.io/maven-central/v/com.isxcode.acorn/acorn-common)](https://search.maven.org/artifact/com.isxcode.acorn/acorn-common)

```xml
<dependency>
    <groupId>com.isxcode.acorn</groupId>
    <artifactId>acorn-common</artifactId>
    <version>0.0.1</version>
</dependency>
```

```yaml
acorn:
  client:
    server:
      host: xxx.xxx.xxx.xxx
      port: 30155
      key: acorn-key
```

```java
class demo {

    private final AcornTemplate acornTemplate;

    public void use(){

        AcornRequest acornRequest = AcornRequest.builder()
            .executeId(String.valueOf(UUID.randomUUID()))
            .sql("INSERT INTO to_table SELECT username,age FROM from_table WHERE age >19 ")
            .build();

        log.info(acornTemplate.build().executeSql(acornRequest).toString());
    }
}
```

### 👏 社区开发

欢迎大家一同维护开发，请参照具体[开发文档](https://github.com/ispong/flink-acorn/blob/main/CONTRIBUTING.md) 。
如需加入我们，请联系邮箱 ispong@outlook.com 。
