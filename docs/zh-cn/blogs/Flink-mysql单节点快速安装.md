##### Docker 安装

```bash
# 卸载旧的docker
sudo yum remove -y docker \
                  docker-client \
                  docker-client-latest \
                  docker-common \
                  docker-latest \
                  docker-latest-logrotate \
                  docker-logrotate \
                  docker-engine
                  
# 安装docker
sudo yum install -y yum-utils
sudo yum-config-manager --add-repo http://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo 
sudo yum makecache all
sudo yum install -y docker-ce docker-ce-cli containerd.io

# 启动docker
sudo systemctl enable docker
sudo systemctl start docker

# 配置docker阿里云镜像
sudo mkdir -p /etc/docker
sudo tee /etc/docker/daemon.json <<-'EOF'
{
  "registry-mirrors": ["https://3fe1zqfu.mirror.aliyuncs.com"],
  "data-root":"/data/docker"
}
EOF
sudo systemctl daemon-reload
sudo systemctl restart docker
sudo systemctl status docker
```

##### 安装mysql

```bash
sudo mkdir -p /data/mysql/data
sudo mkdir -p /data/mysql/conf.d

sudo docker run \
  --name acorn-mysql \
  --privileged=true \
  --restart=always \
  -d \
  -p 30102:3306 \
  -e MYSQL_ROOT_PASSWORD=acorn \
  -e MYSQL_DATABASE=acorn \
  -v /data/mysql/data:/var/lib/mysql \
  -v /data/mysql/conf.d:/etc/mysql/conf.d \
  mysql
```

##### 建表

```bash
sudo docker exec -it acorn-mysql /bin/bash
mysql -h localhost -u root -pacorn -P 30102 
```

```sql
create table acorn.flink_test_table
(
    username varchar(100),
    age int
);
```
