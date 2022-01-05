<h1 align="center">
    Flink Acorn
</h1>

<h3 align="center">
    🐿️ Flink服务器插件，通过Restful接口管理Flink作业。
</h3>

<h4 align="center">
    ✨✨✨ <a href="https://ispong.github.io/flink-acorn" > 
         https://ispong.github.io/flink-acorn
    </a> ✨✨✨
</h4>

### 📢 公告

目前，插件主要针对`flink-1.14.0-scala-2.12`版本进行开发。

支持以下实时数据处理方式:
- kafka --> mysql
- kafka --> kafka
- kafka --> hive
- kafka --> doris

### ✨ 模块说明

| 模块名                                                    | 状态                 | 说明                            |
|:-------------------------------------------------------|--------------------|:------------------------------|
| [acorn-common](https://ispong.github.io/flink-acorn)   | :white_check_mark: | 提供AcornTemplate组件，方便用户调用插件服务。 |
| [acorn-plugin](https://ispong.github.io/flink-acorn)   | :white_check_mark: | 服务器插件本体。                      |
| [acorn-template](https://ispong.github.io/flink-acorn) | :white_check_mark: | 演示客户端如何使用插件。                  |
| [demos](https://ispong.github.io/flink-acorn)          | :white_check_mark: | 各种flink相关的小demo。              |

### 📒 相关文档

- [快速使用入口](https://ispong.github.io/flink-acorn/#/zh-cn/start/%E5%BF%AB%E9%80%9F%E4%BD%BF%E7%94%A8)
- [开发者手册](https://ispong.github.io/flink-acorn/#/zh-cn/contributing)
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
  node:
    host: xxx.xxx.xxx.xxx
    port: 30155
    key: acorn-key
```

```java
@RestController
@RequestMapping
public class DemoController {

    private final AcornTemplate acornTemplate;

    public DemoController(AcornTemplate acornTemplate) {
        this.acornTemplate = acornTemplate;
    }
    
    @GetMapping("/execute")
    public String execute() {

        AcornModel1 acornModel1 = AcornModel1.builder()
            .jobName("job-name")
            .executeId("1314520")
            .fromConnectorSql("CREATE TABLE from_table ( ... ) WITH ( ... )")
            .toConnectorSql("CREATE TABLE to_table ( ... ) WITH ( ... )")
            .filterCode("fromData = fromData.from( ... ).where( ... );")
            .templateName(TemplateType.KAFKA_INPUT_KAFKA_OUTPUT)
            .build();

        AcornResponse acornResponse = acornTemplate.build().execute(acornModel1);
        return acornResponse.getAcornData().getJobId();
    }
}
```

### 👏 社区开发

欢迎大家一同维护开发，请参照具体[开发文档](https://github.com/ispong/flink-acorn/blob/main/CONTRIBUTING.md) 。
如需加入我们，请联系邮箱 ispong@outlook.com 。
