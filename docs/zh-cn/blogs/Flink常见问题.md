---
title: Flink 常见问题
subtitle: Flink 常见问题
tags:
- flink
  categories:
- Flink
  index_img: 'https://gitee.com/isxcode/blogs-galaxy-images/raw/master/flink/flink.png'
  mermaid: false
  math: false
  hide: false
  comments: true
  date: 2021-08-09 11:17:24
---

## 🧙 Questions

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

## 🔗 Links

- [flink website](https://flink.apache.org/)
