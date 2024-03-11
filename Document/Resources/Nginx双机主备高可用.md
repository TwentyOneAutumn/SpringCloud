# Nginx双机热备高可用

## 环境准备

### 安装Nginx

```shell
# 准备Nginx安装包并放置在/usr/local/src/目录下

# 安装Nginx所需环境
yum install libpcre3 libpcre3-dev -y

cd /usr/local/src/

tar -zxvf nginx-1.24.0.tar.gz

cd nginx-1.24.0

./configure --prefix=/usr/local/nginx

make && make install

cd /etc/systemd/system

# 创建服务文件
echo "[Unit]
Description=Nginx - high performance web server
Documentation=http://nginx.org/en/docs/
After=network.target

[Service]
Type=forking
ExecStart=/usr/local/nginx/sbin/nginx -c /usr/local/nginx/conf/nginx.conf
ExecReload=/usr/local/nginx/sbin/nginx -s reload
ExecStop=/usr/local/nginx/sbin/nginx -s stop
PrivateTmp=true
Restart=on-failure
RestartSec=5s

[Install]
WantedBy=multi-user.target" | sudo tee /etc/systemd/system/nginx.service > /dev/null
```

### 安装keepalived

```shell
yum install keepalived –y
```

## 配置

### 修改Nginx配置

```
vim /usr/local/nginx/conf/nginx.conf 
```

### 设置keepalived配置文件

```shell
cd /etc/keepalived

vim keepalived.conf 
```

#### 主节点

```shell
global_defs {
	# 标识节点的ID
	router_id node1
	script_user root
	enable_script_security
} 

# 定义Nginx监控服务
vrrp_script check_nginx {
	# 检测Nginx状态的脚本路径
	script "/etc/keepalived/nginx_check.sh" 
	# 检测时间间隔
	interval 2 
	# 如果条件成立，权重-20
	weight -20
}

# 定义虚拟路由
vrrp_instance VI_1 {
	# 主节点为MASTER,对应的备份节点为BACKUP
	state MASTER 
	# 绑定虚拟IP的网络接口,与本机IP地址所在的网络接口相同
	interface enp0s5
	# 虚拟路由的ID号,主备节点必须一致
	virtual_router_id 33
	# 本机IP地址
	mcast_src_ip 10.211.55.8
	# 节点优先级,值范围 0-254
	priority 100
	# 优先级高的设置nopreempt,解决异常恢复后再次抢占的问题
	nopreempt
	# 组播信息发送间隔
	advert_int 1
	# 设置验证信息,主备节点必须一致
	authentication {
		auth_type PASS
		auth_pass 2762581
	}
	# 将track_script块加入instance配置块
	track_script {
		# 执行Nginx监控的服务
		check_nginx
	} #
	# 虚拟IP池
	virtual_ipaddress {
		# 虚拟IP,主备节点必须一致
		10.211.55.3
	}
}
```

#### 备用节点

```sh
global_defs {
	# 标识节点的ID
	router_id node2
	script_user root
	enable_script_security
} 

# 定义Nginx监控服务
vrrp_script check_nginx {
	# 检测Nginx状态的脚本路径
	script "/etc/keepalived/nginx_check.sh" 
	# 检测时间间隔
	interval 2 
	# 如果条件成立，权重-20
	weight -20
}

# 定义虚拟路由
vrrp_instance VI_1 {
	# 主节点为MASTER,对应的备份节点为BACKUP
	state BACKUP
	# 绑定虚拟IP的网络接口,与本机IP地址所在的网络接口相同
	interface enp0s5
	# 虚拟路由的ID号,主备节点必须一致
	virtual_router_id 33
	# 本机 IP 地址
	mcast_src_ip 10.211.55.9 
	# 节点优先级,值范围 0-254
	priority 80
	# 优先级高的设置nopreempt,解决异常恢复后再次抢占的问题
	nopreempt
	# 组播信息发送间隔
	advert_int 1
	# 设置验证信息,主备节点必须一致
	authentication {
		auth_type PASS
		auth_pass 2762581
	}
	# 将track_script块加入instance配置块
	track_script {
		# 执行Nginx监控的服务
		check_nginx
	} #
	# 虚拟IP池
	virtual_ipaddress {
		# 虚拟IP,主备节点必须一致
		10.211.55.3
	}
}
```

### 编写检测Nginx脚本

```shell
cd /etc/keepalived

vim nginx_check.sh
```

```shell
#!/bin/bash
A=`ps -C nginx --no-header | wc -l`
if [ $A -eq 0 ];then
    /usr/local/nginx/sbin/nginx
    sleep 2
    if [ `ps -C nginx --no-header |wc -l` -eq 0 ];then
	    killall keepalived
    fi
fi
```

## 启动

### 启动Nginx

```shell
systemctl start nginx

systemctl status nginx
```

### 启动keepalived

```shell
systemctl start keepalived

systemctl status keepalived
```