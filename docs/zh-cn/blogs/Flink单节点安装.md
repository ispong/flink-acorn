#### 推荐配置


| 系统             | 	vCPU | 内存   | 	处理器主频/睿频       | 
|----------------|-------|------|-----------------|
| CentOS 7.8 64位 | 4     | 16GB | 2.5 GHz/3.2 GHz |

#### 下载安装包

```bash
# 下载安装包
nohup wget https://archive.apache.org/dist/flink/flink-1.14.0/flink-1.14.0-bin-scala_2.12.tgz >> download_flink.log 2>&1 &
tail -f download_flink.log

# 解压安装包 创建软连接
sudo mkdir /data
sudo tar -vzxf ~/flink-1.14.0-bin-scala_2.12.tgz -C /data/
sudo ln -s /data/flink-1.14.0 /opt/flink 

# 配置环境变量
sudo vim /etc/profile
# === sudo vim /etc/profile ===
export FLINK_HOME=/opt/flink
export PATH=$PATH:$FLINK_HOME/bin 
# === sudo vim /etc/profile ===
source /etc/profile

# 安装jdk
sudo yum -y install java-1.8.0-openjdk-devel java-1.8.0-openjdk

# 查看版本号
flink --version
```

#### 修改端口号

```bash
sudo vim /opt/flink/conf/flink-conf.yaml

# --- vim $flink/conf/flink-conf.yaml ---

# 端口号，默认端口号 8081
rest.port: 30114

# 平行执行管道个数，默认1
taskmanager.numberOfTaskSlots: 100

# --- vim $flink/conf/flink-conf.yaml ---
```

#### 开启flink

> Note:
> 最好在$flink目录下执行启动命令，否则flink无法找到配置文件

```bash
cd /opt/flink
bash ./bin/start-cluster.sh
#bash ./bin/stop-cluster.sh
```

#### 访问flink界面

- http://localhost:30114

#### 安装测试

```bash
flink run /opt/flink/examples/streaming/WordCount.jar
tail /opt/flink/log/flink-*-taskexecutor-*.out
```
