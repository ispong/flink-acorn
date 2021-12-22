<h1 align="center">
    Flink Acorn
</h1>

<h4 align="center">
    <a href="https://ispong.github.io/flink-acorn" >
        https://ispong.github.io/flink-acorn
    </a>
</h4>

<h4 align="center">
    🐿️ Flink服务器插件，通过Restful接口管理Flink作业。
</h4>

### 📢 公告

目前，插件主要针对`flink-1.14.0-scala-2.12`版本进行开发。

- kafka --> mysql
- kafka --> kafka
- kafka --> hive
- binlog --> kafka
- kafka --> doris

### ✨ 模块说明

| 模块名                                          | 状态                 | 说明                                                  |
|:---------------------------------------------|--------------------|:----------------------------------------------------|
| [acorn-common](./acorn-common/README.md)     | :white_check_mark: | 提供AcornTemplate组件，方便用户调用插件服务。                       |
| [acorn-plugin](./acorn-plugin/README.md)     | :white_check_mark: | 服务器插件本体。                                            |
| [acorn-template](./acorn-template/README.md) | :white_check_mark: | 如何使用插件的模板。                                          |
| [demo1](./demo1/README.md)                   | :white_check_mark: | kafka -> kafka                                      |
| [demo2](./demo2/README.md)                   | :white_check_mark: | kafka -> mysql                                      |
| [demo3](./demo3/README.md)                   | :white_check_mark: | kafka -> hive                                       |
| [demo4](./demo4/README.md)                   | :white_check_mark: | mysql -> binlog -> kafka -> flink -> kafka -> doris |

### 📦 安装使用

#### 插件安装（服务器端）

```bash
git clone https://github.com/ispong/flink-acorn.git
```

```bash
vim flink-acorn/acorn-plugin/src/main/resources/application.yml 
```

```yaml
server:
  port: 30155

acorn:
  plugin:
    log-dir: /home/acorn/log
    tmp-dir: /home/acorn/tmp
    server-key: acorn-key
    flink-port: 8081
    storage-tmp: false
```

```bash
cd flink-acorn/acorn-plugin && mvn clean package && java -jar ./target/acorn-plugin.jar
```

#### 插件使用（客户端）

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
    host: 39.103.230.188
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

    /**
     * 发布作业  返回jobId
     */
    @GetMapping("/execute")
    public String execute() {

        AcornModel1 acornModel1 = AcornModel1.builder()
            .jobName("job-name")
            .executeId("1314520")
            .fromConnectorSql("CREATE TABLE from_table ( ... ) WITH ( ... )")
            .toConnectorSql("CREATE TABLE to_table ( ... ) WITH ( ... )")
            .filterCode("Table fromData = fromData.select( ... )")
            .templateName(TemplateType.KAFKA_INPUT_KAFKA_OUTPUT)
            .build();

        AcornResponse acornResponse = acornTemplate.build().execute(acornModel1);
        return acornResponse.getAcornData().getJobId();
    }

    /**
     * 获取作业日志
     */
    @GetMapping("/getLog")
    public String getLog() {

        AcornResponse log = acornTemplate.build().getLog("1314520");
        return log.getAcornData().getJobLog();
    }

    /**
     * 获取作业运行状态
     */
    @GetMapping("/getJobStatus")
    public String getJobStatus() {

        AcornResponse jobInfo = acornTemplate.build().getJobInfo("jobId");
        return jobInfo.getAcornData().getJobInfo().getState();
    }

    /**
     * 停止作业
     */
    @GetMapping("/stopJob")
    public void stop() {

        acornTemplate.build().stopJob("jobId");
    }
}
```

### 👏 社区开发

欢迎大家一同维护开发，请参照具体[开发文档](https://github.com/ispong/flink-acorn/blob/main/CONTRIBUTING.md) 。
如需加入我们，请联系邮箱 ispong@outlook.com 。
