####
```http request
#POST http://localhost:30155/execute
POST http://39.103.230.188:30155/execute
Content-Type: application/json
```

```json
{
  "executeId": "executeIdIsxcode",
  "workType": "KAFKA_INPUT_MYSQL_OUTPUT",
  "flowId": "flowIdIsxcode",
  "kafkaInput": {
    "brokerList": "39.103.230.188:30120",
    "zookeeper": "39.103.230.188:30121",
    "topic": "ispong_kafka",
    "dataFormat": "CSV",
    "columnList": [
      {
        "name": "username",
        "type": "STRING"
      },
      {
        "name": "age",
        "type": "INT"
      }
    ]
  },
  "mysqlOutput": {
    "url": "jdbc:mysql://47.103.203.73:3306/VATtest",
    "tableName": "ispong_table",
    "driver": "com.mysql.cj.jdbc.Driver",
    "username": "admin",
    "password": "gsw921226",
    "columnList": [
      {
        "name": "username",
        "type": "STRING"
      },
      {
        "name": "age",
        "type": "INT"
      }
    ]
  },
  "ConditionList":[
    {
      "name": "",
      "type": "",
      "opts": [
        {
          "col": "",
          "opt": "",
          "value": ""
        },
        {
          "col": "",
          "opt": "",
          "value": ""
        }
      ]
    } 
  ],
  "columnMapping": [
    {
      "fromCol": "",
      "toCol": ""
    },
    {
      "fromCol": "",
      "toCol": ""
    },
  ]
}
```