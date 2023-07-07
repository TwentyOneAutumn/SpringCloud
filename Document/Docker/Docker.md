# Docker

### Docker安装

##### 卸载旧版本Docker

```shell
yum remove docker 
yum remove docker-client
yum remove docker-client-latest
yum remove docker-common 
yum remove docker-latest 
yum remove docker-latest-logrotate 
yum remove docker-logrotate 
yum remove docker-selinux 
yum remove docker-engine-selinux 
yum remove docker-engine 
yum remove docker-ce
```



##### 安装yum工具

```shell
yum install -y yum-utils
yum install device-mapper-persistent-data
yum install lvm2 --skip-broken
```



### Docker命令

```shell
# 关闭防火墙
systemctl stop firewalld

# 禁止开机启动防火墙
systemctl disable firewalld

#切换root权限
sudo -i 
# Password:2762581@com

# 启动Docker容器
systemctl start docker

# 停止Docker容器
systemctl stop docker

# 重启Docker容器
systemctl restart docker

# 查看Docker容器状态
systemctl status docker

# 设置开机启动Docker容器
systemctl enable docker
```



**docker  logs  containerName  [-f]** 

作用 ：查看docker容器运行日志

-f ：持续查看容器日志

 docker logs containerName -f -t --tail=100

Ctrl + c退出Log模式



**docker exec -it containerName bash **

作用 ：进入docker容器

-it ：给当前进入的容器创建一个标准输入输出终端，允许我们与容器交互

bash ：进入容器后执行的命令



### DockerCompose

##### 下载

```shell
# 国内镜像下载地址
curl -L https://get.daocloud.io/docker/compose/releases/download/1.29.1/docker-compose-`uname -s`-`uname -m` > /usr/local/bin/docker-compose

# 给docker compose 目录授权
sudo chmod +x /usr/local/bin/docker-compose
```



### Docker镜像仓库

```yaml
version: '3.0'
services:
  registry:
    image: registry
    volumes:
      - ./registry-data:/var/lib/registry
  ui:
    image: joxit/docker-registry-ui:static
    ports:
      - port:port
    environment:
      - REGISTRY_TITLE=仓库名称
      - REGISTRY_URL=http://registry:5000
    depends_on:
      - registry
```

访问http://YourIp:5000/v2/_catalog 可以查看当前私有镜像服务中包含的镜像



##### 添加Docker信任地址

```sh
# 打开要修改的文件
vi /etc/docker/daemon.json

# 添加内容
"insecure-registries":["http://192.168.150.101:8080"]

# 重加载
systemctl daemon-reload

# 重启docker
systemctl restart docker
```



##### Docker安装

1. 自动安装 ：curl -fsSL https://get.docker.com | bash -s docker --mirror Aliyun

2. 设置开机启动 ：sudo systemctl enable docker.service

3. 编辑配置文件 ：vim /etc/sysctl.conf

4. 配置网络 ：net.ipv4.ip_forward=1

5. 验证：sysctl net.ipv4.ip_forward

6. 返回 ：net.ipv4.ip_forward = 1 表示修改成功

   

---



1. dnf remove podman -y
2. dnf install -y yum-utils device-mapper-persistent-data lvm2
3. dnf config-manager --add-repo http://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo
4. dnf install docker-ce -y
5. systemctl enable  docker
