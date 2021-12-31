> demo1
> kafka到flink  然后flink再推出到另一个kafka  

```shell
cd demo1
mvn clean package
cd target
flink run demo1-0.0.1.jar
```

```shell
# 创建管道
sudo ./kafka-topics.sh --create --zookeeper 101.132.135.228:30099 --replication-factor 1 --partitions 1 --topic ispong-input-1
# 检查管道是否存在
sudo ./kafka-topics.sh --list --zookeeper 101.132.135.228:30099
# 先检查管道是否正常传输
sudo ./kafka-console-producer.sh --broker-list 101.132.135.228:30098 --topic ispong-input-1
sudo ./kafka-console-consumer.sh --bootstrap-server 101.132.135.228:30098 --topic ispong-input-2 --from-beginning
```

```shell
# 输入管道1 ispong-kafka-input1
# 输入管道2 ispong-kafka-input2
# 检查输出管道 ispong-kafka-input2是否有数据
```