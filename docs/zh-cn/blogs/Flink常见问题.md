
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
cp mysql-connector-java-8.0.22/mysql-connector-java-8.0.22.jar /opt/flink/lib/

cd /opt/flink
sudo bash ./bin/start-cluster.sh
# sudo bash ./bin/stop-cluster.sh
```

#####

```log
Table options are:

'connector'='kafka'
'csv.ignore-parse-errors'='true'
'format'='csv'
'properties.bootstrap.servers'='39.103.230.188:30120'
'properties.group.id'='test-consumer-group'
'properties.zookeeper.connect'='39.103.230.188:30121'
'topic'='ispong-input-2'
        at org.apache.flink.table.factories.FactoryUtil.createTableSource(FactoryUtil.java:122)
        at org.apache.flink.table.planner.plan.schema.CatalogSourceTable.createDynamicTableSource(CatalogSourceTable.java:254)
        at org.apache.flink.table.planner.plan.schema.CatalogSourceTable.toRel(CatalogSourceTable.java:100)
        at org.apache.calcite.rel.core.RelFactories$TableScanFactoryImpl.createScan(RelFactories.java:495)
        at org.apache.calcite.tools.RelBuilder.scan(RelBuilder.java:1099)
        at org.apache.calcite.tools.RelBuilder.scan(RelBuilder.java:1123)
        at org.apache.flink.table.planner.plan.QueryOperationConverter$SingleRelVisitor.visit(QueryOperationConverter.java:346)
        at org.apache.flink.table.planner.plan.QueryOperationConverter$SingleRelVisitor.visit(QueryOperationConverter.java:149)
        at org.apache.flink.table.operations.CatalogQueryOperation.accept(CatalogQueryOperation.java:68)
        at org.apache.flink.table.planner.plan.QueryOperationConverter.defaultMethod(QueryOperationConverter.java:146)
        at org.apache.flink.table.planner.plan.QueryOperationConverter.defaultMethod(QueryOperationConverter.java:128)
        at org.apache.flink.table.operations.utils.QueryOperationDefaultVisitor.visit(QueryOperationDefaultVisitor.java:92)
        at org.apache.flink.table.operations.CatalogQueryOperation.accept(CatalogQueryOperation.java:68)
        at org.apache.flink.table.planner.plan.QueryOperationConverter.lambda$defaultMethod$0(QueryOperationConverter.java:145)
        at java.util.Collections$SingletonList.forEach(Collections.java:4822)
        at org.apache.flink.table.planner.plan.QueryOperationConverter.defaultMethod(QueryOperationConverter.java:145)
        at org.apache.flink.table.planner.plan.QueryOperationConverter.defaultMethod(QueryOperationConverter.java:128)
        at org.apache.flink.table.operations.utils.QueryOperationDefaultVisitor.visit(QueryOperationDefaultVisitor.java:47)
        at org.apache.flink.table.operations.ProjectQueryOperation.accept(ProjectQueryOperation.java:74)
        at org.apache.flink.table.planner.calcite.FlinkRelBuilder.queryOperation(FlinkRelBuilder.scala:186)
        at org.apache.flink.table.planner.delegation.PlannerBase.translateToRel(PlannerBase.scala:218)
        at org.apache.flink.table.planner.delegation.PlannerBase$$anonfun$1.apply(PlannerBase.scala:159)
        at org.apache.flink.table.planner.delegation.PlannerBase$$anonfun$1.apply(PlannerBase.scala:159)
        at scala.collection.TraversableLike$$anonfun$map$1.apply(TraversableLike.scala:234)
        at scala.collection.TraversableLike$$anonfun$map$1.apply(TraversableLike.scala:234)
        at scala.collection.Iterator$class.foreach(Iterator.scala:891)
        at scala.collection.AbstractIterator.foreach(Iterator.scala:1334)
        at scala.collection.IterableLike$class.foreach(IterableLike.scala:72)
        at scala.collection.AbstractIterable.foreach(Iterable.scala:54)
        at scala.collection.TraversableLike$class.map(TraversableLike.scala:234)
        at scala.collection.AbstractTraversable.map(Traversable.scala:104)
        at org.apache.flink.table.planner.delegation.PlannerBase.translate(PlannerBase.scala:159)
        at org.apache.flink.table.api.internal.TableEnvironmentImpl.translate(TableEnvironmentImpl.java:1329)
        at org.apache.flink.table.api.internal.TableEnvironmentImpl.executeInternal(TableEnvironmentImpl.java:676)
        at org.apache.flink.table.api.internal.TableImpl.executeInsert(TableImpl.java:572)
        at org.apache.flink.table.api.internal.TableImpl.executeInsert(TableImpl.java:554)
        at com.isxcode.acorn.template.FlinkJob.main(FlinkJob.java:31)
        at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
        at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
        at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
        at java.lang.reflect.Method.invoke(Method.java:498)
        at org.apache.flink.client.program.PackagedProgram.callMainMethod(PackagedProgram.java:349)
        ... 11 more
Caused by: org.apache.flink.table.api.TableException: Could not load service provider for factories.
        at org.apache.flink.table.factories.FactoryUtil.discoverFactories(FactoryUtil.java:407)
        at org.apache.flink.table.factories.FactoryUtil.discoverFactory(FactoryUtil.java:214)
        at org.apache.flink.table.factories.FactoryUtil.getDynamicTableFactory(FactoryUtil.java:352)
        at org.apache.flink.table.factories.FactoryUtil.createTableSource(FactoryUtil.java:118)
        ... 52 more
Caused by: java.util.ServiceConfigurationError: org.apache.flink.table.factories.Factory: Provider org.apache.flink.connector.jdbc.catalog.factory.JdbcCatalogFactory not a subtype
        at java.util.ServiceLoader.fail(ServiceLoader.java:239)
        at java.util.ServiceLoader.access$300(ServiceLoader.java:185)
        at java.util.ServiceLoader$LazyIterator.nextService(ServiceLoader.java:376)
        at java.util.ServiceLoader$LazyIterator.next(ServiceLoader.java:404)
        at java.util.ServiceLoader$1.next(ServiceLoader.java:480)
        at java.util.Iterator.forEachRemaining(Iterator.java:116)
        at org.apache.flink.table.factories.FactoryUtil.discoverFactories(FactoryUtil.java:403)
        ... 55 more
```

```bash
版本问题
```


##### 缺少hive依赖

```log
java.lang.NoClassDefFoundError: org/apache/thrift/TException
        at com.isxcode.acorn.template.FlinkJob.main(FlinkJob.java:43)
        at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
        at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
        at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
        at java.lang.reflect.Method.invoke(Method.java:498)
        at org.apache.flink.client.program.PackagedProgram.callMainMethod(PackagedProgram.java:355)
        at org.apache.flink.client.program.PackagedProgram.invokeInteractiveModeForExecution(PackagedProgram.java:222)
        at org.apache.flink.client.ClientUtils.executeProgram(ClientUtils.java:114)
        at org.apache.flink.client.cli.CliFrontend.executeProgram(CliFrontend.java:812)
        at org.apache.flink.client.cli.CliFrontend.run(CliFrontend.java:246)
        at org.apache.flink.client.cli.CliFrontend.parseAndRun(CliFrontend.java:1054)
        at org.apache.flink.client.cli.CliFrontend.lambda$main$10(CliFrontend.java:1132)
        at org.apache.flink.runtime.security.contexts.NoOpSecurityContext.runSecured(NoOpSecurityContext.java:28)
        at org.apache.flink.client.cli.CliFrontend.main(CliFrontend.java:1132)
Caused by: java.lang.ClassNotFoundException: org.apache.thrift.TException
        at java.net.URLClassLoader.findClass(URLClassLoader.java:381)
        at java.lang.ClassLoader.loadClass(ClassLoader.java:424)
        at org.apache.flink.util.FlinkUserCodeClassLoader.loadClassWithoutExceptionHandling(FlinkUserCodeClassLoader.java:64)
        at org.apache.flink.util.ChildFirstClassLoader.loadClassWithoutExceptionHandling(ChildFirstClassLoader.java:74)
        at org.apache.flink.util.FlinkUserCodeClassLoader.loadClass(FlinkUserCodeClassLoader.java:48)
        at java.lang.ClassLoader.loadClass(ClassLoader.java:357)
        ... 14 more
```

```bash
#将 hive lib下的包全部复制到flink/lib下
cp /opt/cloudera/parcels/CDH/lib/hive/lib/*.jar  /opt/flink/lib/

# 重启flink
cd /opt/flink
sudo bash ./bin/start-cluster.sh
# sudo bash ./bin/stop-cluster.sh
```

##### hive报错

```log
java.lang.NoSuchMethodError: org.apache.calcite.sql.parser.SqlParser.config()Lorg/apache/calcite/sql/parser/SqlParser$Config;
        at org.apache.flink.table.planner.delegation.PlannerContext.lambda$getSqlParserConfig$1(PlannerContext.java:258)
        at java.util.Optional.orElseGet(Optional.java:267)
        at org.apache.flink.table.planner.delegation.PlannerContext.getSqlParserConfig(PlannerContext.java:252)
        at org.apache.flink.table.planner.delegation.PlannerContext.createFrameworkConfig(PlannerContext.java:144)
        at org.apache.flink.table.planner.delegation.PlannerContext.<init>(PlannerContext.java:126)
        at org.apache.flink.table.planner.delegation.PlannerBase.<init>(PlannerBase.scala:109)
        at org.apache.flink.table.planner.delegation.StreamPlanner.<init>(StreamPlanner.scala:52)
        at org.apache.flink.table.planner.delegation.DefaultPlannerFactory.create(DefaultPlannerFactory.java:61)
        at org.apache.flink.table.factories.PlannerFactoryUtil.createPlanner(PlannerFactoryUtil.java:50)
        at org.apache.flink.table.api.bridge.java.internal.StreamTableEnvironmentImpl.create(StreamTableEnvironmentImpl.java:151)
        at org.apache.flink.table.api.bridge.java.StreamTableEnvironment.create(StreamTableEnvironment.java:128)
        at com.isxcode.acorn.template.FlinkJob.main(FlinkJob.java:20)
        at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
        at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
        at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
        at java.lang.reflect.Method.invoke(Method.java:498)
        at org.apache.flink.client.program.PackagedProgram.callMainMethod(PackagedProgram.java:355)
        at org.apache.flink.client.program.PackagedProgram.invokeInteractiveModeForExecution(PackagedProgram.java:222)
        at org.apache.flink.client.ClientUtils.executeProgram(ClientUtils.java:114)
        at org.apache.flink.client.cli.CliFrontend.executeProgram(CliFrontend.java:812)
        at org.apache.flink.client.cli.CliFrontend.run(CliFrontend.java:246)
        at org.apache.flink.client.cli.CliFrontend.parseAndRun(CliFrontend.java:1054)
        at org.apache.flink.client.cli.CliFrontend.lambda$main$10(CliFrontend.java:1132)
        at org.apache.flink.runtime.security.contexts.NoOpSecurityContext.runSecured(NoOpSecurityContext.java:28)
        at org.apache.flink.client.cli.CliFrontend.main(CliFrontend.java:1132)
```

```bash
cp /home/dehoop/.m2/repository/org/apache/flink/flink-table-planner_2.12/1.14.0/flink-table-planner_2.12-1.14.0.jar /opt/flink/lib
cp /home/dehoop/.m2/repository/org/apache/flink/flink-table-planner-blink_2.12/1.12.4/flink-table-planner-blink_2.12-1.12.4.jar /opt/flink/lib
#cp /home/dehoop/.m2/repository/org/apache/flink/flink-connector-hive_2.12/1.14.0/flink-connector-hive_2.12-1.14.0.jar /opt/flink/lib/
## 重启flink
cd /opt/flink
sudo bash ./bin/start-cluster.sh
## sudo bash ./bin/stop-cluster.sh

# 添加一下依赖
    <dependency>
            <groupId>org.apache.flink</groupId>
            <artifactId>flink-table-planner_${scala.binary.version}</artifactId>
            <version>${flink.version}</version>
            <scope>provided</scope>
        </dependency>
```

##### 

```log
org.apache.flink.client.program.ProgramInvocationException: The main method caused an error: Could not instantiate the executor. Make sure a planner module is on the classpath
        at org.apache.flink.client.program.PackagedProgram.callMainMethod(PackagedProgram.java:372)
        at org.apache.flink.client.program.PackagedProgram.invokeInteractiveModeForExecution(PackagedProgram.java:222)
        at org.apache.flink.client.ClientUtils.executeProgram(ClientUtils.java:114)
        at org.apache.flink.client.cli.CliFrontend.executeProgram(CliFrontend.java:812)
        at org.apache.flink.client.cli.CliFrontend.run(CliFrontend.java:246)
        at org.apache.flink.client.cli.CliFrontend.parseAndRun(CliFrontend.java:1054)
        at org.apache.flink.client.cli.CliFrontend.lambda$main$10(CliFrontend.java:1132)
        at org.apache.flink.runtime.security.contexts.NoOpSecurityContext.runSecured(NoOpSecurityContext.java:28)
        at org.apache.flink.client.cli.CliFrontend.main(CliFrontend.java:1132)
Caused by: org.apache.flink.table.api.TableException: Could not instantiate the executor. Make sure a planner module is on the classpath
        at org.apache.flink.table.api.bridge.java.internal.StreamTableEnvironmentImpl.lookupExecutor(StreamTableEnvironmentImpl.java:185)
        at org.apache.flink.table.api.bridge.java.internal.StreamTableEnvironmentImpl.create(StreamTableEnvironmentImpl.java:148)
        at org.apache.flink.table.api.bridge.java.StreamTableEnvironment.create(StreamTableEnvironment.java:128)
        at com.isxcode.acorn.template.FlinkJob.main(FlinkJob.java:20)
        at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
        at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
        at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
        at java.lang.reflect.Method.invoke(Method.java:498)
        at org.apache.flink.client.program.PackagedProgram.callMainMethod(PackagedProgram.java:355)
        ... 8 more
Caused by: org.apache.flink.table.api.TableException: Could not load service provider for factories.
        at org.apache.flink.table.factories.FactoryUtil.discoverFactories(FactoryUtil.java:625)
        at org.apache.flink.table.factories.FactoryUtil.discoverFactory(FactoryUtil.java:376)
        at org.apache.flink.table.api.bridge.java.internal.StreamTableEnvironmentImpl.lookupExecutor(StreamTableEnvironmentImpl.java:176)
        ... 16 more
Caused by: java.util.ServiceConfigurationError: org.apache.flink.table.factories.Factory: Provider org.apache.flink.table.formats.raw.RawFormatFactory could not be instantiated
        at java.util.ServiceLoader.fail(ServiceLoader.java:232)
        at java.util.ServiceLoader.access$100(ServiceLoader.java:185)
        at java.util.ServiceLoader$LazyIterator.nextService(ServiceLoader.java:384)
        at java.util.ServiceLoader$LazyIterator.next(ServiceLoader.java:404)
        at java.util.ServiceLoader$1.next(ServiceLoader.java:480)
        at java.util.Iterator.forEachRemaining(Iterator.java:116)
        at org.apache.flink.table.factories.FactoryUtil.discoverFactories(FactoryUtil.java:621)
        ... 18 more
Caused by: java.lang.NoClassDefFoundError: org/apache/flink/shaded/guava18/com/google/common/collect/Sets
        at org.apache.flink.table.formats.raw.RawFormatFactory.<clinit>(RawFormatFactory.java:144)
        at sun.reflect.NativeConstructorAccessorImpl.newInstance0(Native Method)
        at sun.reflect.NativeConstructorAccessorImpl.newInstance(NativeConstructorAccessorImpl.java:62)
        at sun.reflect.DelegatingConstructorAccessorImpl.newInstance(DelegatingConstructorAccessorImpl.java:45)
        at java.lang.reflect.Constructor.newInstance(Constructor.java:423)
        at java.lang.Class.newInstance(Class.java:442)
        at java.util.ServiceLoader$LazyIterator.nextService(ServiceLoader.java:380)
        ... 22 more
Caused by: java.lang.ClassNotFoundException: org.apache.flink.shaded.guava18.com.google.common.collect.Sets
        at java.net.URLClassLoader.findClass(URLClassLoader.java:381)
        at java.lang.ClassLoader.loadClass(ClassLoader.java:424)
        at org.apache.flink.util.FlinkUserCodeClassLoader.loadClassWithoutExceptionHandling(FlinkUserCodeClassLoader.java:64)
        at org.apache.flink.util.ChildFirstClassLoader.loadClassWithoutExceptionHandling(ChildFirstClassLoader.java:65)
        at org.apache.flink.util.FlinkUserCodeClassLoader.loadClass(FlinkUserCodeClassLoader.java:48)
        at java.lang.ClassLoader.loadClass(ClassLoader.java:357)
        ... 29 more
```

```bash
antlr-runtime-3.5.2.jar
```


##### 不支持mysql

```log
Caused by: org.apache.flink.table.api.ValidationException: Could not find any factory for identifier 'jdbc' that implements 'org.apache.flink.table.factories.DynamicTableFactory' in the classpath.

Available factory identifiers are:

blackhole
datagen
filesystem
kafka
print
upsert-kafka
        at org.apache.flink.table.factories.FactoryUtil.discoverFactory(FactoryUtil.java:397)
        at org.apache.flink.table.factories.FactoryUtil.enrichNoMatchingConnectorError(FactoryUtil.java:581)
        ... 34 more
```

```bash
cp /home/dehoop/.m2/repository/org/apache/flink/flink-connector-jdbc_2.12/1.14.0/flink-connector-jdbc_2.12-1.14.0.jar /opt/flink/lib/ 

# 重启flink
cd /opt/flink
sudo bash ./bin/start-cluster.sh
# sudo bash ./bin/stop-cluster.sh
```
