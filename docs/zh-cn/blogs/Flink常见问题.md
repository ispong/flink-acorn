
- [未使用标准表](#未使用标准表)

## ☄️ Ideas

#### 未使用标准表

```log
Exception in thread "Thread-3" java.lang.IllegalStateException: Trying to access closed classloader. Please check if you store classloaders directly or indirectly in static fields. If the stacktrace suggests that the leak occurs in a third party library and cannot be fixed immediately, you can disable this check with the configuration 'classloader.check-leaked-classloader'.
        at org.apache.flink.runtime.execution.librarycache.FlinkUserCodeClassLoaders$SafetyNetWrapperClassLoader.ensureInner(FlinkUserCodeClassLoaders.java:164)
        at org.apache.flink.runtime.execution.librarycache.FlinkUserCodeClassLoaders$SafetyNetWrapperClassLoader.getResource(FlinkUserCodeClassLoaders.java:183)
        at org.apache.hadoop.conf.Configuration.getResource(Configuration.java:2647)
        at org.apache.hadoop.conf.Configuration.getStreamReader(Configuration.java:2905)
        at org.apache.hadoop.conf.Configuration.loadResource(Configuration.java:2864)
        at org.apache.hadoop.conf.Configuration.loadResources(Configuration.java:2838)
        at org.apache.hadoop.conf.Configuration.getProps(Configuration.java:2715)
        at org.apache.hadoop.conf.Configuration.get(Configuration.java:1186)
        at org.apache.hadoop.conf.Configuration.getTimeDuration(Configuration.java:1774)
        at org.apache.hadoop.util.ShutdownHookManager.getShutdownTimeout(ShutdownHookManager.java:183)
        at org.apache.hadoop.util.ShutdownHookManager.shutdownExecutor(ShutdownHookManager.java:145)
        at org.apache.hadoop.util.ShutdownHookManager.access$300(ShutdownHookManager.java:65)
        at org.apache.hadoop.util.ShutdownHookManager$1.run(ShutdownHookManager.java:102)
```

##### 解决方案

```bash
sudo vim /opt/flink/conf/flink-conf.yaml

# --- sudo vim /opt/flink/conf/flink-conf.yaml ---
classloader.check-leaked-classloader: false
# --- sudo vim /opt/flink/conf/flink-conf.yaml ---
```

#### org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer

```log
Caused by: java.lang.ClassCastException: cannot assign instance of org.apache.commons.collections.map.LinkedMap to field org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumerBase.pendingOffsetsToCommit of type org.apache.commons.collections.map.LinkedMap in instance of org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer
	at java.io.ObjectStreamClass$FieldReflector.setObjFieldValues(ObjectStreamClass.java:2301)
	at java.io.ObjectStreamClass.setObjFieldValues(ObjectStreamClass.java:1431)
	at java.io.ObjectInputStream.defaultReadFields(ObjectInputStream.java:2410)
	at java.io.ObjectInputStream.readSerialData(ObjectInputStream.java:2328)
	at java.io.ObjectInputStream.readOrdinaryObject(ObjectInputStream.java:2186)
	at java.io.ObjectInputStream.readObject0(ObjectInputStream.java:1666)
	at java.io.ObjectInputStream.defaultReadFields(ObjectInputStream.java:2404)
	at java.io.ObjectInputStream.readSerialData(ObjectInputStream.java:2328)
	at java.io.ObjectInputStream.readOrdinaryObject(ObjectInputStream.java:2186)
	at java.io.ObjectInputStream.readObject0(ObjectInputStream.java:1666)
	at java.io.ObjectInputStream.defaultReadFields(ObjectInputStream.java:2404)
	at java.io.ObjectInputStream.readSerialData(ObjectInputStream.java:2328)
	at java.io.ObjectInputStream.readOrdinaryObject(ObjectInputStream.java:2186)
	at java.io.ObjectInputStream.readObject0(ObjectInputStream.java:1666)
	at java.io.ObjectInputStream.readObject(ObjectInputStream.java:502)
	at java.io.ObjectInputStream.readObject(ObjectInputStream.java:460)
	at org.apache.flink.util.InstantiationUtil.deserializeObject(InstantiationUtil.java:615)
	at org.apache.flink.util.InstantiationUtil.deserializeObject(InstantiationUtil.java:600)
	at org.apache.flink.util.InstantiationUtil.deserializeObject(InstantiationUtil.java:587)
	at org.apache.flink.util.InstantiationUtil.readObjectFromConfig(InstantiationUtil.java:541)
	at org.apache.flink.streaming.api.graph.StreamConfig.getStreamOperatorFactory(StreamConfig.java:317)
	... 6 more
```

##### 解决方案

```bash
sudo vim /opt/flink/conf/flink-conf.yaml

# --- sudo vim /opt/flink/conf/flink-conf.yaml ---
classloader.resolve-order: parent-first
# --- sudo vim /opt/flink/conf/flink-conf.yaml ---
```

##### 不支持jdbc

```log
Caused by: org.apache.flink.table.api.ValidationException: Cannot discover a connector using option: 'connector'='jdbc'
        at org.apache.flink.table.factories.FactoryUtil.enrichNoMatchingConnectorError(FactoryUtil.java:367)
        at org.apache.flink.table.factories.FactoryUtil.getDynamicTableFactory(FactoryUtil.java:354)
        at org.apache.flink.table.factories.FactoryUtil.createTableSink(FactoryUtil.java:152)
        ... 34 more
Caused by: org.apache.flink.table.api.ValidationException: Could not find any factory for identifier 'jdbc' that implements 'org.apache.flink.table.factories.DynamicTableFactory' in the classpath.
```

##### 解决方案1

- 看看flink的lib下是否存在jdbc驱动 是否存在 connector-jdbc

```bash
cd /opt/flink/lib

wget https://dev.mysql.com/get/Downloads/Connector-J/mysql-connector-java-8.0.22.tar.gz
tar -vzxf mysql-connector-java-8.0.22.tar.gz 
mv mysql-connector-java-8.0.22/mysql-connector-java-8.0.22.jar /opt/flink/lib/

cp /home/dehoop/.m2/repository/org/apache/flink/flink-connector-jdbc_2.12/1.14.0/flink-connector-jdbc_2.12-1.14.0.jar /opt/flink/lib/ 

# 重启flink
cd /opt/flink
sudo bash ./bin/start-cluster.sh
# sudo bash ./bin/stop-cluster.sh
```

## 🔗 Links

- [flink website](https://flink.apache.org/)
