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
sudo flink --version
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

#### 安装测试

```bash
sudo flink run /opt/flink/examples/streaming/WordCount.jar
sudo tail /opt/flink/log/flink-*-taskexecutor-*.out
```

#### 支持mysql

```bash
cp ~/.m2/repository/org/apache/flink/flink-connector-jdbc_2.12/1.14.0/flink-connector-jdbc_2.12-1.14.0.jar /opt/flink/lib/ 

# 重启flink
cd /opt/flink
bash ./bin/stop-cluster.sh
bash ./bin/start-cluster.sh
```

#### 支持hive

```bash
# hive下的jars
#scp cdh@cdh-master:/opt/cloudera/parcels/CDH/jars/hive-*.jar /opt/flink/lib/ 
sudo cp /opt/cloudera/parcels/CDH/jars/hive-*.jar /opt/flink/lib/ 
# hadoop下的jars
#scp cdh@cdh-master:/opt/cloudera/parcels/CDH/jars/hadoop-*.jar /opt/flink/lib/ 
cp /opt/cloudera/parcels/CDH/jars/hadoop-*.jar /opt/flink/lib/
# flink-connector-hive_xxx.jar
cp ~/.m2/repository/org/apache/flink/flink-connector-hive_2.12/1.14.0/flink-connector-hive_2.12-1.14.0.jar /opt/flink/lib/

# 修改配置
sudo vim /opt/flink/conf/flink-conf.yaml

# --- sudo vim /opt/flink/conf/flink-conf.yaml ---
classloader.check-leaked-classloader: false
# --- sudo vim /opt/flink/conf/flink-conf.yaml ---

# 修改配置
sudo vim /opt/flink/conf/hive-site.xml

# 重启flink
cd /opt/flink
sudo bash ./bin/stop-cluster.sh
sudo bash ./bin/start-cluster.sh
```

##### sudo vim /opt/flink/conf/hive-site.xml

```xml
<?xml version="1.0"?>
<?xml-stylesheet type="text/xsl" href="configuration.xsl"?>

<configuration>

    <!-- hive配置的数据库 -->
    <property>
        <name>javax.jdo.option.ConnectionURL</name>
        <value>jdbc:mysql://xxx:xxx/xxx</value>
    </property>

    <!-- 驱动 -->
    <property>
        <name>javax.jdo.option.ConnectionDriverName</name>
        <value>com.mysql.cj.jdbc.Driver</value>
    </property>

    <!-- 账号 -->
    <property>
        <name>javax.jdo.option.ConnectionUserName</name>
        <value>xxx</value>
    </property>

    <!-- 密码 -->
    <property>
        <name>javax.jdo.option.ConnectionPassword</name>
        <value>xxx</value>
    </property>

    <!-- 默认端口号 9083 -->
    <property>
        <name>hive.metastore.uris</name>
        <value>thrift://xxx:9083</value>
    </property>

</configuration>
```

