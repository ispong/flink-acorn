#### 下载安装包

```bash
# 下载安装包
nohup wget https://archive.apache.org/dist/flink/flink-1.14.0/flink-1.14.0-bin-scala_2.12.tgz >> download_flink.log 2>&1 &
tail -f download_flink.log

# 解压安装包 创建软连接
sudo tar -vzxf ~/flink-1.14.0-bin-scala_2.12.tgz -C /data/
sudo ln -s /data/flink-1.14.0 /opt/flink 

# 配置环境变量
sudo vim /etc/profile
# === sudo vim /etc/profile ===
export FLINK_HOME=/opt/flink
export PATH=$PATH:$FLINK_HOME/bin 
# === sudo vim /etc/profile ===
source /etc/profile

# 查看版本号
sudo chown -R dehoop:dehoop /data/flink-1.14.0
flink --version
```

#### 修改端口号

```bash
vim /opt/flink/conf/flink-conf.yaml

# --- vim $flink/conf/flink-conf.yaml ---

# 端口号，默认端口号 8081
rest.port: 30114

# 平行执行管道个数，默认1
taskmanager.numberOfTaskSlots: 20

# --- vim $flink/conf/flink-conf.yaml ---
```

#### 开启flink

> Note:
> 最好在$flink目录下执行启动命令，否则flink无法找到配置文件

```bash
cd /opt/flink
sudo bash ./bin/start-cluster.sh
sudo bash ./bin/stop-cluster.sh
```

#### 安装测试

```bash
flink run /opt/flink/examples/streaming/WordCount.jar
tail /opt/flink/log/flink-*-taskexecutor-*.out
```

#### 支持mysql

```bash
cp /home/dehoop/.m2/repository/org/apache/flink/flink-connector-jdbc_2.12/1.14.0/flink-connector-jdbc_2.12-1.14.0.jar /opt/flink/lib/ 

# 重启flink
cd /opt/flink
sudo bash ./bin/stop-cluster.sh
sudo bash ./bin/start-cluster.sh
```
