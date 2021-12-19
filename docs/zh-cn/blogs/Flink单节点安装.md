---
title: Flink 单节点安装
subtitle: Flink 单节点安装
tags:
- flink
  categories:
- Flink
  index_img: 'https://gitee.com/isxcode/blogs-galaxy-images/raw/master/flink/flink.png'
  mermaid: false
  math: false
  hide: false
  comments: true
  date: 2021-08-09 11:16:38
---

## 🧙 Questions

> flink 安装文档 (1.14.0 scala-2.12)

## ☄️ Ideas

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

#### 移动jar

```bash
wget https://dev.mysql.com/get/Downloads/Connector-J/mysql-connector-java-8.0.22.tar.gz
tar -vzxf mysql-connector-java-8.0.22.tar.gz 
mv mysql-connector-java-8.0.22/mysql-connector-java-8.0.22.jar /opt/flink/lib/

mvn -version
cp /data/dehoop/maven/repository/org/apache/flink/flink-connector-jdbc_2.12/1.14.0/flink-connector-jdbc_2.12-1.14.0.jar /opt/flink/lib/ 
```

## 🔗 Links

- [flink website](https://flink.apache.org/)
- [flink install docs](https://ci.apache.org/projects/flink/flink-docs-release-1.12/try-flink/local_installation.html)
