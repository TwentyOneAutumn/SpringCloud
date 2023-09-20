# 安装Helm

### 下载Helm压缩包

> [`Helm GitHub`](https://github.com/helm/helm/releases)

### 安装Helm

```shell
# 安装解压工具
yum install tar

# 解压
tar -zxvf helm-v3.12.2-linux-amd64.tar.gz

# 将操作命令移动到bin目录下
mv linux-amd64/helm /usr/local/bin/helm

# 检验是否成功
helm version
```

### 配置仓库

```shell
# 添加稳定仓库
helm repo add bitnami https://charts.bitnami.com/bitnami

# 查看仓库
helm repo list
```
