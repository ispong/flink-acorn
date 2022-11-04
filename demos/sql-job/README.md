### kafka输入json格式输出到mysql

##### 输入样例

```json
{"username":"john","lucky_date":"2020-12-12","age":12,"lucky_datetime":"2020-12-12 12:12:12","lucky_time":"12:12:12"}
```

#### mysql 字段映射关系

| mysql    | flink     |
| -------- | --------- |
| datetime | TIMESTAMP |
| date     | DATE      |
| time     | TIME      |