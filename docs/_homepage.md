#### Flink Acorn 是什么

?> &nbsp; **问：** 为什么开发这个组件？

> **答：**
> Flink开发痛点: Flink只支持提交Jar包的方式提交作业，意味着一个Jar包中只能包含一个写死的逻辑，每次业务逻辑的更新都需要重新修改Jar包中的内容，开发非常不友好。
> 于是思考，是否可以将Jar包开发成作业模板，以动态传参的方式，实现一个Jar包多次使用。举例：若单纯的执行FlinkSql，就可以开发一个FlinkSql作业模板，每次传入不同的Sql，让模板执行。
> 这样就不需要每次都在Jar中写死Sql。

?> &nbsp; **问：** 组件支持集群模式吗？

> **答：**
> 目前在代码层面是没有实现集群的模式的，但是可以通过Nginx负载均衡的方式，实现集群模式。即，在每台服务器上都安装Flink-Acorn服务，然后配置好Nginx负载均衡，在客户端只需要填写Nginx的域名，就可以实现多集群模式。只要一台服务器还可以访问，就不会影响整体的业务功能。

?> &nbsp; **问：** Flink-Acorn支持Flink的哪些部署模式？

> **答：**
> Flink原生支持local，standalone，application，flink-on-yarn，Per-Job on yarn。但目前Flink-Acorn**只支持Per-Job On Yarn模式**。
> </br> 原因是：每部署一个作业即启动一个Flink环境运行。
> </br> &nbsp; &nbsp; 1. 资源可控：可以对Flink环境做资源控制，指定内存使用量，指定cpu内核使用数量。
> </br> &nbsp; &nbsp; 2. 环境稳定：每个作业之间不会互相影响，不会出现一个Flink环境跑了太多的作业，导致Flink服务崩了，而影响到重要的作业运行。
