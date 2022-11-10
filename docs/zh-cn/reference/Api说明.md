## - 执行flinkSql

```java
String sql = "select * from table";

AcornResponse acornResponse = acornTemplate.build()
    .sql(sql)
    .deploy();
```

```json
{
  "code": "200",
  "message": "操作成功",
  "acornData": {
    "flinkJobId": "88f660b6fbf4ac78b98d0649de2b79f2",
    "applicationId": "application_1668067924093_0008"
  }
}
```

## - 获取yarn容器日志

```java
AcornResponse acornResponse = acornTemplate.build()
        .applicationId("applicationId")
        .getYarnLog();
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

## - 获取yarn容器状态

```java
AcornResponse acornResponse = acornTemplate.build()
    .applicationId("applicationId")
    .getYarnStatus();
```

```json
{
  "code": "200",
  "message": "操作成功",
  "acornData": {
    "finalStatus": "SUCCEEDED",
    "yarnState": "FINISHED"
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
