
## ☄️ Ideas

#### 前提

- 安装java
- 安装hadoop
- 安装zookeeper

##### 安装flink

```bash
# 创建安装目录
sudo mkdir -p /data/flink/
sudo chown -R isxcode:isxcode /data/flink/

# 下载flink安装包
nohup wget https://archive.apache.org/dist/flink/flink-1.13.3/flink-1.13.3-bin-scala_2.12.tgz >> download_flink.log 2>&1 &
tail -f download_flink.log

# 发放安装包
scp flink-1.13.3-bin-scala_2.12.tgz isxcode@node1:~/
scp flink-1.13.3-bin-scala_2.12.tgz isxcode@node2:~/

# 解压并创建软连接
tar -vzxf flink-1.13.3-bin-scala_2.12.tgz -C /data/flink/
sudo ln -s /data/flink/flink-1.13.3 /opt/flink 

# 配置项目环境
sudo vim /etc/profile
# === sudo vim /etc/profile ===
export FLINK_HOME=/opt/flink
export PATH=$PATH:$FLINK_HOME/bin
# === sudo vim /etc/profile ===
source /etc/profile

# 查看flink版本
flink --version
```

##### 启动flink

> 一定要配置 HADOOP_CLASSPATH

```bash
yarn-session.sh --help

# 创建长时间运行的flink集群
# -jm 主节点内存
# -tm 从节点内存
# -d 后台运行
yarn-session.sh \
  --jobManagerMemory 4096 \
  --taskManagerMemory 4096 \
  --name isxcode-flink-cluster \
  --slots 5 \
  -d

- http://8.142.75.40:8088/cluster

# 访问页面
- http://8.142.75.40:8088/proxy/application_1638001959645_0001/#/overview

# 停掉集群
.yarn-properties-${user}
more /tmp/.yarn-properties-isxcode
yarn application -kill application_1638001959645_0002
```

jobmanager.web.access-control-allow-origin: 192.168.252.35,192.168.24.216
jobmanager.web.allow-access-address: 192.168.252.35,192.168.24.216

## 🔗 Links

- [flink website](https://flink.apache.org/)
- [flink install docs](https://nightlies.apache.org/flink/flink-docs-release-1.13/docs/try-flink/local_installation/)
- [flink zk ha](https://nightlies.apache.org/flink/flink-docs-release-1.13/docs/deployment/ha/zookeeper_ha/)
