#### 下载安装包

```bash
nohup wget https://archive.apache.org/dist/flink/flink-1.14.0/flink-1.14.0-bin-scala_2.12.tgz >> download_flink.log 2>&1 &
tail -f download_flink.log
```

#### 解压安装包并创建软连接

```bash
sudo mkdir /data
sudo tar -vzxf ~/flink-1.14.0-bin-scala_2.12.tgz -C /data/
sudo chown -R ispong:ispong /data/flink-1.14.0
sudo ln -s /data/flink-1.14.0 /opt/flink  
```

#### 配置环境变量

```bash
sudo vim /etc/profile
```

```bash
export FLINK_HOME=/opt/flink
export PATH=$PATH:$FLINK_HOME/bin 
```

```bash
source /etc/profile
```

#### 安装jdk

```bash
sudo yum -y install java-1.8.0-openjdk-devel java-1.8.0-openjdk
```

#### 查看版本号

```bash
flink --version
```

#### 修改端口号

```bash
vim /opt/flink/conf/flink-conf.yaml
```

```bash
# ispong :) 端口号，默认端口号 8081
rest.port: 30114

# ispong :) 平行执行管道个数，默认1
taskmanager.numberOfTaskSlots: 100
```

#### 开启flink

!> 最好在$flink目录下执行启动命令，否则flink无法找到配置文件

```bash
cd /opt/flink
bash ./bin/start-cluster.sh
```

```bash
cd /opt/flink
bash ./bin/stop-cluster.sh
```

#### 访问flink界面

- http://192.168.66.66:30114

#### 安装测试

```bash
flink run /opt/flink/examples/streaming/WordCount.jar
tail /opt/flink/log/flink-*-taskexecutor-*.out
```
