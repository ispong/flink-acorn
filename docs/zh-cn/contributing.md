#### 如何加入我们

#### 如何运行 如何调试

#### 如何部署新版本

#### 提交代码规范

0.0.1-f-1.14.0

#### 如何提交新的demo

#### 如何维护官方网站


mvn clean deploy -P release


https://s01.oss.sonatype.org/

https://search.maven.org/search?q=isxcode

git clone https://github.com/ispong/flink-acorn.git
cd acorn-plugin && mvn clean package
java -jar -Xmx2048m ./target/acorn-plugin.jar >> ./flink-acorn.log 2>&1 &


> Note:
> 需要提前将jdbc下载到flink的lib文件夹下并重启flink

```bash
sudo cp mysql-connector-java-8.0.22/mysql-connector-java-8.0.22.jar /opt/flink/lib/
sudo cp /home/dehoop/.m2/repository/org/apache/flink/flink-connector-jdbc_2.12/1.14.0/flink-connector-jdbc_2.12-1.14.0.jar /opt/flink/lib/
```


####### 添加镜像仓库

```xml
<repositories>
    <repository>
        <id>s01.apache.snapshots</id>
        <name>S01 Apache Development Snapshot Repository</name>
        <url>https://s01.oss.sonatype.org/content/repositories/snapshots</url>
        <releases>
            <enabled>false</enabled>
        </releases>
        <snapshots>
            <enabled>true</enabled>
        </snapshots>
    </repository>
</repositories>
```

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
