## - 执行FlinkSql

> `http://localhost:8080/execute`

```java
public AcornResponse executeFlinkSql() {

    String flinkSql = "CREATE TABLE from_table(\n" +
    "    username STRING,\n" +
    "    age INT\n" +
    ") WITH (\n" +
    "    'connector'='jdbc',\n" +
    "    'url'='jdbc:mysql://isxcode:30306/ispong_db',\n" +
    "    'table-name'='users',\n" +
    "    'driver'='com.mysql.cj.jdbc.Driver',\n" +
    "    'username'='root',\n" +
    "    'password'='ispong123');" +
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
    "insert into to_table select username, age from from_table";

    return acornTemplate.build().sql(flinkSql).deploy();
}
```

```json
{
    "code": "200",
    "msg": "部署成功",
    "data": {
        "flinkJobId": "092cdca855b719303e826e690c483c73",
        "applicationId": "application_1671005804173_0007"
    }
}
```

![20221109174510](https://img.isxcode.com/picgo/20221109174510.png)


## - 获取Yarn容器状态

```java
public AcornResponse getYarnStatus(@RequestParam String applicationId) {

    return acornTemplate.build().applicationId(applicationId).getYarnStatus();
}
```

```json
{
    "code": "200",
    "msg": "获取yarn作业状态成功",
    "data": {
        "finalStatus": "SUCCEEDED",
        "yarnState": "FINISHED"
    }
}
```

## - 获取Yarn容器日志

```java
public AcornResponse getYarnLog(@RequestParam String applicationId) {

    return acornTemplate.build().applicationId(applicationId).getYarnLog();
}
```

```json
{
  "code": "200",
  "message": "操作成功",
  "acornData": {
    "yarnLogs": [
        ""
    ]
  }
}
```

## - 查看FlinkJob状态

> `http://localhost:8080/getJobStatus?flinkJobId=ce1d15ddcf217da4fb5a8195dfbc14ed`

```java
public AcornResponse getJobStatus(@RequestParam String flinkJobId) {

    return acornTemplate.build().jobId(flinkJobId).getJobStatus();
}
```

```json
{
    "code": "200",
    "msg": "获取flink作业状态",
    "data": {
        "jobStatus": {
            "jid": "ce1d15ddcf217da4fb5a8195dfbc14ed",
            "state": "FINISHED"
        }
    }
}
```

## - 查看FlinkJob异常

> `http://localhost:8080/getJobExceptions?flinkJobId=ce1d15ddcf217da4fb5a8195dfbc14ed`

```java
public AcornResponse getJobExceptions(@RequestParam String flinkJobId) {

    return acornTemplate.build().jobId(flinkJobId).getJobExceptions();
}
```

```json
{
    "code": "200",
    "msg": "获取flink作业异常日志",
    "data": {
        "jobExceptions": {
            "rootException": null,
            "allExceptions": []
        }
    }
}
```

## - 停止作业

> `http://localhost:8080/stopYarnJob?applicationId=application_1671005804173_0008`

```java
AcornResponse acornResponse = acornTemplate.build()
    .applicationId("applicationId")
    .stopJob();
```

```json
{
  "code": "200",
  "msg": "停止作业",
  "data": {}
}
```

