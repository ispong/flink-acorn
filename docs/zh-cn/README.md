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
    🐿️ Flink服务器插件，通过Restful接口发布Flink的Job。
</h4>

<h4 align="center">
    < https://ispong.github.io/flink-acorn >
</h4>

### 📢 公告

目前，插件主要针对`flink-1.14.0-scala-2.12`版本进行开发

### 📒 相关文档

- [Flink 集群安装]()
- [Flink 常用命令]()

### 📦 插件安装

##### 服务器插件安装

```bash
# git clone https://gitee.com/ispong/flink-acorn.git
git clone https://github.com/ispong/flink-acorn.git
cd acorn-plugin && mvn clean package
nohup java -jar -Xmx2048m ./target/acorn-plugin.jar >> ./flink-acorn.log 2>&1 &
```

##### 本地服务器使用

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    
    <repositories>
        <repository>
            <id>apache.snapshots</id>
            <name>Apache Development Snapshot Repository</name>
            <url>https://s01.oss.sonatype.org/content/repositories/snapshots/</url>
            <releases>
                <enabled>false</enabled>
            </releases>
            <snapshots>
                <enabled>true</enabled>
            </snapshots>
        </repository>
    </repositories>

    <dependencies>
        <groupId>com.isxcode.acorn</groupId>
        <artifactId>acorn-common</artifactId>
        <version>0.0.3-SNAPSHOT</version>
    </dependencies>
    
</project>
```

```java
@RestController
@RequestMapping
@SpringBootApplication
public class TemplateApplication {

    private final AcornTemplate acornTemplate;

    public TemplateApplication(AcornTemplate acornTemplate) {
        this.acornTemplate = acornTemplate;
    }

    public static void main(String[] args) {

        SpringApplication.run(TemplateApplication.class, args);
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
                .topic("ispong_kafka")
                .dataFormat(DataFormat.CSV)
                .columnList(kafkaInputColumns)
                .build();

        // 输出点
        List<FlinkCol> mysqlOutputColumns = new ArrayList<>();
        mysqlOutputColumns.add(new FlinkCol("username", FlinkSqlType.STRING));
        mysqlOutputColumns.add(new FlinkCol("age", FlinkSqlType.INT));

        MysqlOutput mysqlOutput = MysqlOutput.builder()
                .url("jdbc:mysql://host:port/VATtest")
                .tableName("ispong_table")
                .driver("com.mysql.cj.jdbc.Driver")
                .username("admin")
                .password("gsw921226")
                .columnList(mysqlOutputColumns)
                .build();

        // 构建请求对象
        ExecuteConfig executeConfig = ExecuteConfig.builder()
                .executeId("executeId123")
                .flowId("flowId123")
                .workType(WorkType.KAFKA_INPUT_MYSQL_OUTPUT)
                .kafkaInput(kafkaInput)
                .mysqlOutput(mysqlOutput)
                .build();

        // 运行
        return acornTemplate.executeFlink("host", "port", "key", executeConfig);
    }
}
```

### ✨ 模块说明

| 模块名  |  说明 |
| ---  | --- |
| [acorn-common](./acorn-common/README.md) | 负责客户端调用服务器插件 |
| [acorn-plugin](./acorn-plugin/README.md) | 服务器插件模块 |
| [acorn-template](./acorn-template/README.md) | 给用户使用的项目模板 |
| [demo1](./demo1/README.md) | kafka输入csv格式数据，输出kafka为csv数据格式 |
| [demo2](./demo2/README.md) | kafka输入csv格式数据，输出mysql |
| [demo3](./demo3/README.md) | kafka输入json格式数据，输出mysql |
| [demo5](./demo5/README.md) | kafka输入json格式数据，输出hive |

### 👏 欢迎维护

欢迎一同维护开发，请参照[开发文档](https://github.com/ispong/flink-acorn/blob/main/CONTRIBUTING.md) 