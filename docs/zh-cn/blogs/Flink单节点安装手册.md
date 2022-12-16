🧙 Questions

> flink 安装文档 (1.14.0 scala-2.12)

☄️ Ideas

##### 下载安装包

```bash
nohup wget https://archive.apache.org/dist/flink/flink-1.14.0/flink-1.14.0-bin-scala_2.12.tgz >> download_flink.log 2>&1 &
tail -f download_flink.log
```

##### 解压安装

```bash
sudo mkdir -p /data/flink
sudo chown -R ispong:ispong /data/flink 
```

```bash
tar -vzxf flink-1.14.0-bin-scala_2.12.tgz -C /data/flink/
sudo ln -s /data/flink/flink-1.14.0 /opt/flink 
```

##### 配置环境变量

```bash
sudo vim /etc/profile
```

```bash
export FLINK_HOME=/opt/flink
export PATH=$PATH:$FLINK_HOME/bin

# flink on yarn 一定要配置
export YARN_CONF_DIR=/opt/hadoop/etc/hadoop
```

```bash
source /etc/profile
```

##### 修改配置

```bash
vim /opt/flink/conf/flink-conf.yaml
```

```bash
# 端口号，默认端口号 8081
# 使用yarn部署，不需要配置端口号
# rest.port: 30114

# 可选
taskmanager.numberOfTaskSlots: 20
```

##### 使用yarn启动flink(可选)

> -jm 主节点内存
> -tm 从节点内存
> -d 后台运行

> slots 不能超过yarn容器最大虚拟内核数 (yarn.scheduler.maximum-allocation-vcores)

```bash
yarn-session.sh \
  --jobManagerMemory 4096 \
  --taskManagerMemory 4096 \
  --name isxcode-flink-cluster \
  --slots 4 \
  -d
```

![20221104160923](https://img.isxcode.com/picgo/20221104160923.png)

- http://isxcode:8088

![20221104161013](https://img.isxcode.com/picgo/20221104161013.png)

- http://isxcode:8088/proxy/application_1667534773030_0002/
- http://isxcode:34256

![20221104161049](https://img.isxcode.com/picgo/20221104161049.png)


> 如果要使用本地一定要删除这个文件，否则一直会提交到yarn中

```bash
rm /tmp/.yarn-properties-ispong
```

![20221104171459](https://img.isxcode.com/picgo/20221104171459.png)

##### 本地启动flink(可选)

> 最好在$flink目录下执行启动命令，否则flink无法找到配置文件
> 关闭flink
> cd /opt/flink
> bash ./bin/stop-cluster.sh

```bash
cd /opt/flink
bash ./bin/start-cluster.sh
```

- http://isxcode:8081

##### 安装测试

```bash
flink run /opt/flink/examples/streaming/WordCount.jar
tail /opt/flink/log/flink-*-taskexecutor-*.out
```

#### Yarn per-job

```bash
# 单独提交任务
flink run -t yarn-per-job --detached /home/ispong/flink-acorn/demos/sql-job/target/sql-job-0.0.1.jar

# List running job on the cluster
flink list -t yarn-per-job -Dyarn.application.id=application_XXXX_YY

# Cancel running job
flink cancel -t yarn-per-job -Dyarn.application.id=application_XXXX_YY <jobId>
```

#### 支持mysql同步(可选)

```bash
wget https://dev.mysql.com/get/Downloads/Connector-J/mysql-connector-java-8.0.22.tar.gz
tar -vzxf mysql-connector-java-8.0.22.tar.gz 
mv mysql-connector-java-8.0.22/mysql-connector-java-8.0.22.jar /opt/flink/lib/

cd /opt/flink/lib/
wget https://repo1.maven.org/maven2/org/apache/flink/flink-connector-jdbc_2.12/1.14.0/flink-connector-jdbc_2.12-1.14.0.jar
```

🔗 Links

- [flink website](https://flink.apache.org/)
- [flink install docs](https://ci.apache.org/projects/flink/flink-docs-release-1.12/try-flink/local_installation.html)
