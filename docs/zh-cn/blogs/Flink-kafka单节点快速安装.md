##### 安装kafka单节点

```bash
nohup wget https://archive.apache.org/dist/kafka/2.6.2/kafka_2.12-2.6.2.tgz --no-check-certificate >> download_kafka.log 2>&1 &
tail -f download_kafka.log
sudo tar -vzxf kafka_2.12-2.6.2.tgz -C /data/
sudo ln -s /data/kafka_2.12-2.6.2 /opt/kafka

sudo vim /etc/profile
# === vim /etc/profile ===
export KAFKA_HOME=/opt/kafka
export PATH=$PATH:$KAFKA_HOME/bin
# === vim /etc/profile ===
source /etc/profile

cd /opt/kafka/bin
sudo ./zookeeper-server-start.sh -daemon /opt/kafka/config/zookeeper.properties
tail -f /opt/kafka/logs/zookeeper.out
sudo netstat -ntpl | grep 2181

sudo vim /opt/kafka/config/server.properties
# === sudo vim /opt/kafka/config/server.properties ===
listeners=PLAINTEXT://172.26.34.170:9092
advertised.listeners=PLAINTEXT://8.142.131.65:9092
# === sudo vim /opt/kafka/config/server.properties ===

cd /opt/kafka/bin
sudo ./kafka-server-start.sh -daemon /opt/kafka/config/server.properties
sudo netstat -ntpl | grep 9092
```

##### kafka测试

```bash
kafka-topics.sh --create --zookeeper 172.26.34.170:2181 --topic flink-topic --replication-factor 1 --partitions 1 
kafka-console-producer.sh --topic flink-topic --broker-list 172.26.34.170:9092
kafka-console-consumer.sh --bootstrap-server 172.26.34.170:9092 --topic flink-topic --from-beginning --property print.key=true 
```
