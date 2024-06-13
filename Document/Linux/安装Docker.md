# 安装Docker

## docker

```shell
# 更新软件包索引
sudo yum update -y

# 安装必要的依赖包
sudo yum install -y yum-utils device-mapper-persistent-data lvm2

# 添加Docker的仓库
sudo yum-config-manager --add-repo https://download.docker.com/linux/centos/docker-ce.repo

# 安装Docker CE（Community Edition）
sudo yum install -y docker-ce docker-ce-cli containerd.io

# 启动Docker服务
sudo systemctl start docker

# 设置Docker开机自启动
sudo systemctl enable docker

# 验证Docker安装
docker --version
```



### Docker Compose

运行一下命令查看

```shell
uname -s
uname -m
```

访问 https://github.com/docker/compose/releases

```shell
# 下载Docker Compose的二进制文件
sudo curl -L "https://github.com/docker/compose/releases/download/1.29.2/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose

# 赋予Docker Compose可执行权限
sudo chmod +x /usr/local/bin/docker-compose

# 验证Docker Compose安装
docker-compose --version
```



## 手动安装Docker

1. 下载docker压缩包

   https://download.docker.com/linux/static/stable/x86_64/docker-26.0.1.tgz

2. 将压缩包拷贝到目标主机上

3. 切换到压缩包所在目录

4. 运行docker-install.sh

   ```shell
   #!/bin/sh
    
   echo '解压tar包'
   tar -xvf docker-26.0.0.tgz
   
   echo '将docker目录下所有文件复制到/usr/bin目录'
   
   
   echo '将docker.service 复制到/etc/systemd/system/目录'
   cp docker.service /etc/systemd/system/
   l
   echo '添加文件可执行权限'
   chmod +x /etc/systemd/system/docker.service
   
   echo '重新加载配置文件'
   systemctl daemon-reload
   
   echo '启动docker'
   systemctl start docker
   
   echo '设置开机自启'
   systemctl enable docker.service
   
   echo 'docker安装成功'
   docker -v
   ```

   

## 手动安装docker-compose

1. 查看主机架构

   ```shell
   uname -s
   uname -m
   ```

2. 下载docker-compose

   https://github.com/docker/compose/releases

3. 将docker-compose二进制文件拷贝到目标主机上

4. 切换到docker-compose二进制文件所在目录

5. 复制文件到根目录

   ```shell
   cp docker-compose /usr/local/bin/docker-compose
   ```

6. 添加权限

   ```shell
   # 赋予Docker Compose可执行权限
   sudo chmod +x /usr/local/bin/docker-compose
   ```

7. 验证

   ```shell
   # 验证Docker Compose安装
   docker-compose --version
   ```
