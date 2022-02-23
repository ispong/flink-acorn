?> 解决服务器重启，flink服务无法启动的问题

!> 必须写绝对路径，软连接路径不生效 **/data/dehoop/flink-1.14.0/**

```bash
sudo vim /usr/lib/systemd/system/flink.service

# === sudo vim /usr/lib/systemd/system/flink.service ===
[Unit]
Description= Flink Service

[Service]
Environment=JAVA_HOME=/usr/lib/jvm/java-1.8.0-openjdk
Type=forking
WorkingDirectory=/opt/flink
PermissionsStartOnly=true
ExecStart=cd /data/dehoop/flink-1.14.0/bin/start-cluster.sh
ExecStop=/data/dehoop/flink-1.14.0/bin/stop-cluster.sh
KillMode=none
Restart=always

[Install]
WantedBy=multi-user.target
# === sudo vim /usr/lib/systemd/system/flink.service ===

# 刷新service
sudo systemctl daemon-reload

# 开启flink服务自启
sudo systemctl enable flink
```

```bash
sudo systemctl start flink
sudo systemctl stop flink
sudo systemctl status flink
```
