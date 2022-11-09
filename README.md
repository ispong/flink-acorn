<p align="center">
  <a href="https://github.com/ispong/flink-acorn" style="border-bottom: none !important;">
    <img alt="flink-acorn" width="400" src="https://img.isxcode.com/isxcode_img/flink-acorn/logo.png">
  </a>
</p>

<h1 align="center">
    Flink Acorn
</h1>

<h3 align="center">
    🐿️ Support development plugin for Flink custom api restful
</h3>

<h2></h2>

### 📢 公告

- 目前仅支持 **flink-1.14.0-scala-2.12** 版本

### 📒 相关文档

- [快速使用](https://flink-acorn.isxcode.com/#/zh-cn/start/快速使用)
- [维护手册](https://flink-acorn.isxcode.com/#/zh-cn/contributing)
- [版本历史](https://flink-acorn.isxcode.com/#/zh-cn/changelog)

### 📦 使用说明

[![Maven Version](https://img.shields.io/maven-central/v/com.isxcode.acorn/acorn-common)](https://search.maven.org/artifact/com.isxcode.acorn/acorn-common)

```xml
<dependency>
    <groupId>com.isxcode.acorn</groupId>
    <artifactId>acorn-client</artifactId>
    <version>1.2.0</version>
</dependency>
```

```yml
acorn:
  check-servers: true
  servers:
    default:
      host: isxcode
      port: 30155
      key: acorn-key
```

```java
package com.isxcode.acorn.demo;

import com.isxcode.acorn.client.template.AcornTemplate;
import com.isxcode.acorn.common.pojo.AcornResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping
@RestController
@SpringBootApplication
@RequiredArgsConstructor
public class DemoApplication {

    private final AcornTemplate acornTemplate;

    public static void main(String[] args) {

        SpringApplication.run(DemoApplication.class, args);
    }

    @GetMapping("/execute")
    public AcornResponse executeFlinkSql() {

        String flinkSql = "" +
            "CREATE TABLE from_table(\n" +
            "    username STRING,\n" +
            "    age INT\n" +
            ") WITH (\n" +
            "    'connector'='jdbc',\n" +
            "    'url'='jdbc:mysql://isxcode:30306/ispong_db',\n" +
            "    'table-name'='users',\n" +
            "    'driver'='com.mysql.cj.jdbc.Driver',\n" +
            "    'username'='root',\n" +
            "    'password'='ispong123');" +
            "" +
            "CREATE TABLE to_table(\n" +
            "    username STRING,\n" +
            "    age INT\n" +
            ") WITH (\n" +
            "    'connector'='jdbc',\n" +
            "    'url'='jdbc:mysql://isxcode:30306/ispong_db',\n" +
            "    'table-name'='users_sink',\n" +
            "    'driver'='com.mysql.cj.jdbc.Driver',\n" +
            "    'username'='root',\n" +
            "    'password'='ispong123');" +
            "" +
            "insert into to_table select username, age from from_table";

        return acornTemplate.build().sql(flinkSql).deploy();
    }
}
```

```json
{
    "code":"200",
    "message":"操作成功",
    "acornData":{
        "applicationId":"application_1667964484125_0003"
    }
}
```

### 👏 社区开发

- 欢迎大家一同维护开发，请参照具体[开发文档](https://flink-acorn.isxcode.com/#/zh-cn/contributing) 。
- 如需加入我们，请联系邮箱 ispong@outlook.com 。
