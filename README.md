<p align="center">
  <a href="https://github.com/ispong/flink-acorn" style="border-bottom: none !important;">
    <img alt="flink-acorn" width="180" src="https://github.com/ispong/flink-acorn/raw/main/logo.png">
  </a>
</p>

<h1 align="center">
    Flink Acorn
</h1>

<h3 align="center">
    大栗子
</h3>

### 📢 公告

> 目前针对版本为`1.12.5 scala-2.11`的flink集群

### 🔨 使用场景

> Flink集群服务器插件，通过Restful接口发布管理Flink的Job。

### 📒 相关文档

- [Flink 集群安装]()
- [Flink 常用命令]()

### 📦 插件安装

```bash
# git clone https://gitee.com/ispong/flink-acorn.git
git clone https://github.com/ispong/flink-acorn.git
cd acorn-common && mvn clean install
cd acorn-plugin 
mvn package
java -jar acorn-plugin/target/acorn-plugin.jar
```

### 👏 

### ✨ 项目模块说明

| 模块名  | 作者 | 说明 |
| --- | --- | --- |
| [demo1](./demo1/README.md) | [ispong](https://github.com/ispong) | kafka输入csv格式数据，输出kafka为csv数据格式 |
| [demo2](./demo2/README.md) | [ispong](https://github.com/ispong) | kafka输入csv格式数据，输出mysql |
| [demo3](./demo3/README.md) | [ispong](https://github.com/ispong) | kafka输入json格式数据，输出mysql |
| [demo4](./demo4/README.md) | [ispong](https://github.com/ispong) | spring集成flink，实现配置节点和工作流，然后一键发布flink|
| [demo5](./demo5/README.md) | [ispong](https://github.com/ispong) | kafka输入json格式数据，输出hive |