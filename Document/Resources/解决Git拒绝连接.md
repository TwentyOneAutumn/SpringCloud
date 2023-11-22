# 解决Git拒绝连接

```cmd
git config --global --unset http.proxy 
git config --global --unset https.proxy
git config --global http.sslVerify false
```

