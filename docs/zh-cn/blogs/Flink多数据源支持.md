#### 支持mysql

```bash
# 下载flink-mysql驱动
wget https://repo1.maven.org/maven2/org/apache/flink/flink-connector-jdbc_2.12/1.14.0/flink-connector-jdbc_2.12-1.14.0.jar
cp flink-connector-jdbc_2.12-1.14.0.jar /opt/flink/lib/ 

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

