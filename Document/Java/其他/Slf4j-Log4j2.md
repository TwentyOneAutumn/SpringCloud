# Slf4j Log4j2



---



### 依赖

```xml
<!-- Web依赖 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
    <exclusions>
        <!-- 移除自带的logback -->
        <exclusion>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-logging</artifactId>
        </exclusion>
    </exclusions>
</dependency>

<!-- SpringBootSecurity -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
    <exclusions>
        <!-- 移除自带的logback -->
        <exclusion>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-logging</artifactId>
        </exclusion>
    </exclusions>
</dependency>

<!-- Lombok依赖 -->
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>、
    <version>1.18.22</version>
</dependency>

<!-- slf4j依赖-->
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-api</artifactId>
    <version>1.7.30</version>
</dependency>

<!-- log4j2和slf4j桥接依赖-->
<dependency>
    <groupId>org.apache.logging.log4j</groupId>
    <artifactId>log4j-slf4j-impl</artifactId>
    <version>2.19.0</version>
</dependency>

<!-- log4j2依赖-->
<dependency>
    <groupId>org.apache.logging.log4j</groupId>
    <artifactId>log4j-api</artifactId>
    <version>2.19.0</version>
</dependency>

<dependency>
    <groupId>org.apache.logging.log4j</groupId>
    <artifactId>log4j-core</artifactId>
    <version>2.19.0</version>
</dependency>
```



如果继承了SpringBoot父工程，则需要屏蔽spring-boot-starter所携带的spring-boot-starter-logging依赖

```xml
<dependencyManagement>
        <dependencies>
            <!-- springBoot父依赖 -->
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter</artifactId>
                <version>${SpringBoot.version}</version>
                <exclusions>
                    <!-- 移除自带的logback -->
                    <exclusion>
                        <groupId>org.springframework.boot</groupId>
                        <artifactId>spring-boot-starter-logging</artifactId>
                    </exclusion>
                </exclusions>
        </dependency>
		</dependencies>
</dependencyManagement>
```



---



### Log4j2日志



##### 日志级别

| 日志级别 | 描述                                                         |
| -------- | ------------------------------------------------------------ |
| ALL      | 最低等级，用于打开所有级别的日志记录                         |
| TRACE    | 程序推进下的追踪信息，追踪信息的日志级别非常低，一般不会使用 |
| DEBUG    | 指出细粒度信息事件，对调试应用程序非常有帮助，主要时配合开发使用，在开发过程中打印一些重要的运行信息 |
| INFO     | 消息的粗粒度级别运行信息                                     |
| WARN     | 标识警告，程序在运行的过程中会出现有可能发生的隐形错误，注意，有些信息不是错误，但是这个级别的输出目的就是为了给程序员以提示 |
| FATAL    | 表示严重错误，它时那种一旦发生系统就不可能继续运行的严重错误，如果这种级别的错误出现了，表示程序可以停止运行了 |
| OFF      | 最高等级的级别，关闭所有日志记录                             |



##### 日志格式

| 字符    | 描述                                                         |
| ------- | ------------------------------------------------------------ |
| %m      | 输入代码中指定的日志信息                                     |
| %p      | 输出优先级                                                   |
| %n      | 换行符                                                       |
| %r      | 输出自应用启动到输出该log信息耗费的毫秒数                    |
| %c      | 输出打印语句所属类的全限定名称                               |
| %t      | 输出产生该日志的线程全名                                     |
| %d      | 输出服务器当前时间，默认为ISO8601，也可以指定格式，例如：%d{yyyy-MM-dd HH:mm:ss} |
| %l      | 输出日志发生的位置，包括类名，线程，及在代码中的行数         |
| %f      | 输出日志消息产生时所在的文件名称                             |
| %L      | 输出代码中的行号                                             |
| %字符   | 输出一个"字符"，可以在%和字符之间加上修饰符来控制最小宽度，最大宽度，以及文本的对其方式 |
| %5c     | 最小宽度是5，如果小于5，填充空格，默认情况下右对齐，-号指定左对齐 |
| %.5c    | 最大宽度是5，如果长度大于5，就会将左边多余的字符，小于5不会有空格 |
| %20.30c | 小于20补空格，并且右对齐，大于30就截掉左边多余字符           |



##### 日志输出方式

| Appender                 | 描述                                                         |
| ------------------------ | ------------------------------------------------------------ |
| ConsoleAppender          | 将日志输出到控制台中                                         |
| FileAppender             | 将文件输出到文件中                                           |
| DailyRollingFileAppender | 将日志输出到一个日志文件中，并且每天输出得到一个新的文件     |
| RollingFileAppender      | 将日志信息输出到一个日志文件，并指定文件的尺寸，当文件大小达到指定尺寸时，会自动把文件改名，同时产生一个新的文件 |
| JDBCAppender             | 把日志信息保存到数据库中                                     |



##### 配置log4j2.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration status="warn" monitorInterval="30">
    <!--  先定义项目名称  -->
    <Properties>
        <Property name="ServiceName">Basic</Property>
    </Properties>

    <!-- 先定义所有的appender(附加器)-->
    <appenders>
        <!-- 输出控制台Log配置 -->
        <Console name="Console" target="SYSTEM_OUT">
            <Filters>
                <!-- 设置日志级别,级别以下的日志将不会被输出 -->
                <ThresholdFilter level="INFO" onMatch="ACCEPT" onMismatch="DENY"/>
            </Filters>
            <!-- 设置日志格式 -->
            <PatternLayout pattern="[%p] %l:%m%n "/>
        </Console>

        <!-- Info日志输入Log文件配置 -->
        <!-- fileName:文件的路径, [./]表示当前项目跟目录 -->
        <!-- filePattern:滚动后文件的路径 -->
        <RollingFile name="RollingInfoFile" fileName="./logs/${ServiceName}/info/${date:yyyy-MM-dd}/${ServiceName}-info.log" filePattern="./logs/${ServiceName}/info/${date:yyyy-MM-dd}/${ServiceName}-info.log">
            <Filters>
                <!-- 设置日志级别,级别以下的日志将不会被输出 -->
                <ThresholdFilter level="INFO" onMatch="ACCEPT" onMismatch="DENY"/>
            </Filters>
            <!-- 设置日志格式 -->
            <PatternLayout pattern="%d %p %c{1.} [%t] %m%n"/>
            <Policies>
                <!-- 根据文件大小进行滚动 -->
                <SizeBasedTriggeringPolicy size="5MB"/>
            </Policies>
            <!-- 默认同一文件夹下最多7个文件 -->
            <DefaultRolloverStrategy max="7"/>
        </RollingFile>

        <!-- Error日志输入Log文件配置 -->
        <RollingFile name="RollingErrorFile" fileName="./logs/${ServiceName}/error/${date:yyyy-MM-dd}/${ServiceName}-error.log" filePattern="./logs/${ServiceName}/error/${date:yyyy-MM-dd}/${ServiceName}-error.log">
            <Filters>
                <!-- 设置日志级别,级别以下的日志将不会被输出 -->
                <ThresholdFilter level="ERROR" onMatch="ACCEPT" onMismatch="DENY"/>
            </Filters>
            <!-- 设置日志格式 -->
            <PatternLayout pattern="%d %p %c{1.} [%t] %m%n"/>
            <Policies>
                <!-- 根据文件大小进行滚动 -->
                <SizeBasedTriggeringPolicy size="5MB"/>
            </Policies>
            <!-- 默认同一文件夹下最多7个文件 -->
            <DefaultRolloverStrategy max="7"/>
        </RollingFile>
    </appenders>

    <!-- 然后定义logger，只有定义了logger并引入的appender，appender才会生效 -->
    <loggers>
        <logger name="org.springframework" level="OFF"/>
        <logger name="com.github.pagehelper" level="OFF"/>

        <!-- 使用 RollingInfoFile appender 记录 INFO 级别的日志 -->
        <logger name="InfoLogger" level="INFO">
            <appender-ref ref="RollingInfoFile"/>
        </logger>

        <!-- 使用 RollingErrorFile appender 记录 ERROR 级别的日志 -->
        <logger name="ErrorLogger" level="ERROR">
            <appender-ref ref="RollingErrorFile"/>
        </logger>

        <!-- 建立一个默认的root的logger -->
        <root level="INFO">
            <!-- 控制台输出日志 -->
            <appender-ref ref="Console"/>
        </root>
    </loggers>
</configuration>
```



---



### 代码示例

```java
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Logger {
    public static void main(String[] args) {
        log.info("这是一条日志");
    }
}
```

