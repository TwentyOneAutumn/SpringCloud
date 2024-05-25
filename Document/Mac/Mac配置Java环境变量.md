# Mac配置Java环境变量

1. 打开终端

2. 运行命令

   ```shell
   open ~/.bash_profile
   ```

3. 添加内容

   ```shell
   # Set JAVA_HOME environment variable for Java 8
   export JAVA_HOME=$(/usr/libexec/java_home -v 1.8)
   
   # Update PATH to include JAVA_HOME
   export PATH=$JAVA_HOME/bin:$PATH
   ```

4. 重新加载文件

   ```shell
   source ~/.bash_profile
   ```