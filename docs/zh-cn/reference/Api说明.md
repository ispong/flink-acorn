##### - 执行flinkSql

```java
public void executeSql() {

    String sql = "select * from a";

    AcornResponse acornResponse = acornTemplate.build()
        .sql(sql)
        .deploy();
}
```

##### - 获取作业日志

```java
public void getExecuteLog() {

    AcornResponse acornResponse = acornTemplate.build()
        .applicationId("applicationId")
        .getLog();
}
```

##### - 获取作业状态

```java
public void getJobStatus() {

    AcornResponse acornResponse = acornTemplate.build()
        .applicationId("applicationId")
        .getStatus();
}
```

##### - 停止作业

```java
public void stopJob() {

    AcornResponse acornResponse = acornTemplate.build()
        .applicationId("applicationId")
        .kill();
}
```
