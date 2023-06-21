# Mac命令



---



### 复制本机文件到虚拟机

```shell
scp <本机文件路径> <账号>@<虚拟机IP>:<虚拟机目录>
```



---



### 复制虚拟机文件到本机

```shell
scp <账号>@<虚拟机IP>:<虚拟机目录> <本机文件路径>
```



---



### ping端口

```shell
nc -zv <host> <port>
```



---



### 查看端口占用

```shell
lsof -i tcp:<port>
```



---



### 杀死进程

```shell
kill -9 <PID>
```



---

