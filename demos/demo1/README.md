### kafka 同步到 mysql

```bash
cd flink-acorn/demos/demo1
mvn clean package
flink run target/demo1-0.0.1.jar
```

### kafka相关命令

```bash
sudo kafka-topics --list --zookeeper 39.103.230.188:30121
sudo kafka-topics --create --zookeeper 39.103.230.188:30121 --replication-factor 1 --partitions 1 --topic ispong-test
sudo kafka-console-producer --broker-list 39.103.230.188:30120 --topic ispong-test
```
