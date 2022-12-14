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
    "msg": "",
    "data": {
        "flinkJobId": "092cdca855b719303e826e690c483c73",
        "applicationId": "application_1671005804173_0007"
    }
}
```

![20221109174510](https://img.isxcode.com/picgo/20221109174510.png)


## - 获取yarn容器状态

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

## - 获取yarn容器日志

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
      "******************************************************************************",
      "",
      "Container: container_1668067924093_0008_01_000001 on isxcode_36708",
      "LogAggregationType: AGGREGATED",
      "==================================================================",
      "LogType:prelaunch.out",
      "LogLastModifiedTime:Thu Nov 10 20:16:13 +0800 2022",
      "LogLength:100",
      "LogContents:",
      "Setting up env variables",
      "Setting up job resources",
      "Copying debugging information",
      "Launching container",
      "",
      "End of LogType:prelaunch.out",
      "******************************************************************************"
    ]
  }
}
```


## - 停止作业

```java
AcornResponse acornResponse = acornTemplate.build()
    .applicationId("applicationId")
    .stopJob();
```

## - 查看flinkJob状态

```java
AcornResponse acornResponse = acornTemplate.build()
    .jobId("flinkJobId")
    .getJobStatus();
```

```json
{
  "code": "200",
  "message": "操作成功",
  "acornData": {
    "jobStatus": {
      "jid": "88f660b6fbf4ac78b98d0649de2b79f2",
      "state": "FINISHED"
    }
  }
}
```

## - 查看flinkJob异常

```java
AcornResponse acornResponse = acornTemplate.build()
    .jobId("flinkJobId")
    .getJobExceptions();
```

```json
{
    "code": "200",
    "message": "操作成功",
    "acornData": {
        "jobExceptions": {
            "rootException": null,
            "allExceptions": []
        }
    }
}
```
