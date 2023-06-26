# 安装并启动Jenkins



1. 下载Jenkins:[Jenkins官网](https://www.jenkins.io/download/)

1. 打开终端启动War包

   ```shell
   java -jar jenkins.war --httpPort=<port>
   ```

1. 访问 [Jenkins控制台](http://localhost:port)

1. 查看终端输出日志，获取初始化密码

   ```
   *************************************************************
   *************************************************************
   *************************************************************
   
   Jenkins initial setup is required. An admin user has been created and a password generated.
   Please use the following password to proceed to installation:
   
   4af8baed28b34c78badf80a0353e8589
   
   This may also be found at: /Users/thetwentyoneautumn/.jenkins/secrets/initialAdminPassword
   
   *************************************************************
   *************************************************************
   *************************************************************
   ```

1. 填写密码

   ![image-20230625141134219](..\img\Jenkins1.png)

1. 进入 Manage Jenkins.  >>>.  Manage Plugin.  >>>.  Advanced.  >>>.  Update Site(升级站点)，设置为：https://mirrors.tuna.tsinghua.edu.cn/jenkins/updates/update-center.json

1. 

   

   

   

   

   

   

   

   

   

