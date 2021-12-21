#### 下载安装包

```bash
nohup wget https://archive.apache.org/dist/flink/flink-1.14.0/flink-1.14.0-bin-scala_2.12.tgz >> download_flink.log 2>&1 &
tail -f download_flink.log

sudo tar -vzxf ~/flink-1.14.0-bin-scala_2.12.tgz -C /data/dehoop/
sudo ln -s /data/dehoop/flink-1.14.0 /opt/flink 

sudo vim /etc/profile
# === sudo vim /etc/profile ===
export FLINK_HOME=/opt/flink
export PATH=$PATH:$FLINK_HOME/bin 
# === sudo vim /etc/profile ===
source /etc/profile

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
taskmanager.numberOfTaskSlots: 20

# --- vim $flink/conf/flink-conf.yaml ---
```

#### 开启flink

> Note:
> 最好在$flink目录下执行启动命令，否则flink无法找到配置文件

```bash
cd /opt/flink
sudo bash ./bin/start-cluster.sh
# sudo bash ./bin/stop-cluster.sh
```

#### 安装测试

```bash
flink run /opt/flink/examples/streaming/WordCount.jar
tail /opt/flink/log/flink-*-taskexecutor-*.out
```

## 🔗 Links

- [flink website](https://flink.apache.org/)
- [flink install docs](https://ci.apache.org/projects/flink/flink-docs-release-1.12/try-flink/local_installation.html)
