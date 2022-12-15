<p align="center">
  <a href="https://github.com/ispong/flink-acorn" style="border-bottom: none !important;">
    <img alt="flink-acorn" width="400" src="https://img.isxcode.com/isxcode_img/flink-acorn/logo1.png">
  </a>
</p>

<h3 align="center">
    🐿️ 基于Spring对远程Flink服务二次封装，实现FlinkSql执行、Job状态查询以及获取运行日志等。
</h3>

<h4 align="center">
    👉 https://flink-acorn.isxcode.com 👈
</h4>

<div align="center" class="badge">
    [![Maven Version](https://img.shields.io/maven-central/v/com.isxcode.acorn/acorn-client)](https://search.maven.org/artifact/com.isxcode.acorn/acorn-client)
</div>

<h2></h2>

### 📢 注意

> 目前仅支持`Yarn-Per-Job`模式

### 📒 文档

- [快速使用](https://flink-acorn.isxcode.com/#/zh-cn/start/%E5%BF%AB%E9%80%9F%E4%BD%BF%E7%94%A8)
- [快速安装](https://flink-acorn.isxcode.com/#/zh-cn/install/%E5%BF%AB%E9%80%9F%E5%AE%89%E8%A3%85)
- [Api说明](https://flink-acorn.isxcode.com/#/zh-cn/reference/Api%E8%AF%B4%E6%98%8E)

### 📦 使用说明

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
import com.isxcode.acorn.api.pojo.AcornResponse;
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
        "applicationId":"application_1667964484125_0003",
        "flinkJobId":"1667964484125_0003"
    }
}
```

***

**Thanks for free JetBrains Open Source license**

<a href="https://www.jetbrains.com/?from=spring-demo" target="_blank" style="border-bottom: none !important;">
    <img src="https://img.isxcode.com/index_img/jetbrains/jetbrains-3.png" height="100" alt="jetbrains"/>
</a>

