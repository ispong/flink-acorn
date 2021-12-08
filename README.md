<p align="center">
  <a href="https://github.com/ispong/flink-acorn" style="border-bottom: none !important;">
    <img alt="flink-acorn" width="180" src="https://github.com/ispong/flink-acorn/raw/main/docs/assets/images/logo.png">
  </a>
</p>

<h1 align="center">
    Flink Acorn
</h1>

<h3 align="center">
    小栗子
</h3>

<h4 align="center">
    <a href="https://ispong.github.io/flink-acorn" >
        https://ispong.github.io/flink-acorn
    </a>
</h4>


<h4 align="center">
    🐿️ Flink服务器插件，通过Restful接口发布Flink的作业。
</h4>

### 📢 公告

目前，插件主要针对`flink-1.14.0-scala-2.12`版本进行开发。

### ✨ 模块

| 模块名                                          | 说明                                          |
|:---------------------------------------------| :-------------------------------------------- |
| [acorn-common](./acorn-common/README.md)     | 提供AcornTemplate组件，方便用户调用插件服务。 |
| [acorn-plugin](./acorn-plugin/README.md)     | 服务器插件本体。                              |
| [acorn-template](./acorn-template/README.md) | 如何使用插件的模板。                          |
| [demo1](./demo1/README.md)                   | kafka输入，kafka输出。                        |
| [demo2](./demo2/README.md)                   | kafka输入，mysql输出。                        |
| [demo3](./demo3/README.md)                   | kafka输入，hive输出。                         |

### 📦 插件安装

##### 服务器插件安装

```bash
# 或者 git clone https://gitee.com/ispong/flink-acorn.git
git clone https://github.com/ispong/flink-acorn.git
# 构建插件
cd acorn-plugin && mvn clean package
# 运行插件，默认端口`30155`
nohup java -jar -Xmx2048m ./target/acorn-plugin.jar >> ./flink-acorn.log 2>&1 &
```

##### 本地客户端使用

```xml
<!-- 添加maven依赖 -->
<dependency>
    <groupId>com.isxcode.acorn</groupId>
    <artifactId>acorn-common</artifactId>
    <version>0.0.3-SNAPSHOT</version>
</dependency>
```

```java
@RestController
@RequestMapping
public class TemplateController {

    private final AcornTemplate acornTemplate;

    public TemplateApplication(AcornTemplate acornTemplate) {
        this.acornTemplate = acornTemplate;
    }

    @GetMapping("/demo")
    public AcornResponse testExecuteFlink() {

        // 输入点
        List<FlinkCol> kafkaInputColumns = new ArrayList<>();
        kafkaInputColumns.add(new FlinkCol("username", FlinkSqlType.STRING));
        kafkaInputColumns.add(new FlinkCol("age", FlinkSqlType.INT));

        KafkaInput kafkaInput = KafkaInput.builder()
                .brokerList("host:port")
                .zookeeper("host:port")
                .topic("topicName")
                .dataFormat(DataFormat.CSV)
                .columnList(kafkaInputColumns)
                .build();

        // 输出点
        List<FlinkCol> mysqlOutputColumns = new ArrayList<>();
        mysqlOutputColumns.add(new FlinkCol("username", FlinkSqlType.STRING));
        mysqlOutputColumns.add(new FlinkCol("age", FlinkSqlType.INT));

        MysqlOutput mysqlOutput = MysqlOutput.builder()
                .url("jdbc:mysql://host:port/dbName")
                .tableName("tableName")
                .driver("com.mysql.cj.jdbc.Driver")
                .username("username")
                .password("password")
                .columnList(mysqlOutputColumns)
                .build();

        // 构建请求对象
        ExecuteConfig executeConfig = ExecuteConfig.builder()
                .executeId("executeId")
                .flowId("flowId")
                .workType(WorkType.KAFKA_INPUT_MYSQL_OUTPUT)
                .kafkaInput(kafkaInput)
                .mysqlOutput(mysqlOutput)
                .build();

        // 运行
        return acornTemplate.executeFlink("host", "port", "key", executeConfig);
    }
}
```

### 👏 社区开发

> 欢迎大家一同维护开发，请参照具体[开发文档](https://github.com/ispong/flink-acorn/blob/main/CONTRIBUTING.md) 。
> 如需加入我们，请联系邮箱[ispong@outlook.com] 。