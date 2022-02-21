##### 安装kafka单节点

```bash
# 下载kafka
nohup wget https://archive.apache.org/dist/kafka/2.6.2/kafka_2.12-2.6.2.tgz --no-check-certificate >> download_kafka.log 2>&1 &
tail -f download_kafka.log

# 软连接目录
sudo tar -vzxf kafka_2.12-2.6.2.tgz -C /data/
sudo ln -s /data/kafka_2.12-2.6.2 /opt/kafka

# 配置环境变量
sudo vim /etc/profile
# === vim /etc/profile ===
export KAFKA_HOME=/opt/kafka
export PATH=$PATH:$KAFKA_HOME/bin
# === vim /etc/profile ===
source /etc/profile

# 启动zookeeper
cd /opt/kafka/bin
sudo ./zookeeper-server-start.sh -daemon /opt/kafka/config/zookeeper.properties
tail -f /opt/kafka/logs/zookeeper.out
sudo netstat -ntpl | grep 2181

# 配置kafka配置
sudo vim /opt/kafka/config/server.properties
# === sudo vim /opt/kafka/config/server.properties ===
listeners=PLAINTEXT://${内网ip}:9092
advertised.listeners=PLAINTEXT://${公网ip}:9092
# === sudo vim /opt/kafka/config/server.properties ===

# 启动kafka
cd /opt/kafka/bin
sudo ./kafka-server-start.sh -daemon /opt/kafka/config/server.properties
sudo netstat -ntpl | grep 9092
```

##### kafka测试

```bash
# 创建topic
kafka-topics.sh --create --zookeeper localhost:2181 --topic acorn-topic --replication-factor 1 --partitions 1 
# 创建生产者
kafka-console-producer.sh --topic acorn-topic --broker-list ${内网ip}:9092
# 创建消费者
kafka-console-consumer.sh --bootstrap-server ${内网ip}:9092 --topic acorn-topic --from-beginning --property print.key=true 
```
