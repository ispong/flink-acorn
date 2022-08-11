#### 下载kafka

```bash
nohup wget https://archive.apache.org/dist/kafka/2.6.2/kafka_2.12-2.6.2.tgz --no-check-certificate >> download_kafka.log 2>&1 &
tail -f download_kafka.log
```
#### 软连接目录

```bash
sudo tar -vzxf kafka_2.12-2.6.2.tgz -C /data/
sudo ln -s /data/kafka_2.12-2.6.2 /opt/kafka
sudo chown -R ispong:ispong /data/kafka_2.12-2.6.2 
```

#### 配置环境变量

```bash
sudo vim /etc/profile
```

```bash
export KAFKA_HOME=/opt/kafka
export PATH=$PATH:$KAFKA_HOME/bin
```

```bash
source /etc/profile
```

#### 启动zookeeper

```bash
cd /opt/kafka/bin
./zookeeper-server-start.sh -daemon /opt/kafka/config/zookeeper.properties
```

```bash
netstat -ntpl | grep 2181
tail -f /opt/kafka/logs/zookeeper.out
```

#### 配置kafka配置

```bash
vim /opt/kafka/config/server.properties
```

!> 外网 192.168.66.66 <br/>
内网 127.0.0.1

```bash
listeners=PLAINTEXT://127.0.0.1:9092
advertised.listeners=PLAINTEXT://192.168.66.66:9092
```

#### 启动kafka

```bash
cd /opt/kafka/bin
./kafka-server-start.sh -daemon /opt/kafka/config/server.properties
```

```bash
netstat -ntpl | grep 9092
tail -f /opt/kafka/logs/kafkaServer.out
```

#### kafka测试

##### 创建topic

```bash
kafka-topics.sh --create --zookeeper 127.0.0.1:2181 --topic acorn-input --replication-factor 1 --partitions 1 
```

##### 创建生产者

```bash
kafka-console-producer.sh --topic acorn-input --broker-list 127.0.0.1:9092
```

##### 创建消费者

```bash
kafka-console-consumer.sh --bootstrap-server 127.0.0.1:9092 --topic acorn-input --from-beginning --property print.key=true 
```