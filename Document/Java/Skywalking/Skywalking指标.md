# Skywalking指标



## Global Root 全局概览

### Service

| 指标             | 作用                        |
| ---------------- | --------------------------- |
| Service Groups   | 服务分组                    |
| Service Names    | 服务名                      |
| Load (calls/min) | 服务平均每分钟接受请求次数  |
| Success Rate (%) | 服务平均成功率              |
| Latency (ms)     | 服务平均响应延时，单位:毫秒 |
| Apdex            | 服务评分                    |

### Topology



## Global Overview 全局概览

| 指标                                         | 作用                                  |
| -------------------------------------------- | ------------------------------------- |
| Service Apdex                                | 服务评分                              |
| Success Rate                                 | 接口请求成功率，单位:百分比           |
| Service Load (calls/min)                     | 服务接受请求数量                      |
| Service Avg Response Time (ms)               | 服务平均响应延时，单位:毫秒           |
| Service Response Time Percentile (ms)        | 服务响应延时百分比，单位:毫秒         |
| Message Queue Consuming Count                | 消息队列消费数量                      |
| Message Queue Avg Consuming Latency (ms)     | 消息队列平均消费时间                  |
| Servce Instances Load (calls/min)            | 每个服务实例的请求数量                |
| Show Service Instance                        | 每个服务实例的平均响应延时            |
| Service Instance Successful Rate             | 每个服务实例的请求成功率，单位:百分比 |
| Endpoint Load in Current Service             | 接口每分钟请求数量                    |
| Slow Endpoints in Current Service            | 接口平均响应延时，单位:毫秒           |
| Endpoints Successful Rate in Current Service | 每个端点的请求成功率，单位:百分比     |

## Instance 服务实例

### Overview 概览

| 指标                              | 作用                                      |
| --------------------------------- | ----------------------------------------- |
| Service Instance Load (calls/min) | 当前服务实例接收请求数量                  |
| Service Instance Success Rate (%) | 当前服务实例接收请求的成功率，单位:百分比 |
| Service Instance Latency (ms)     | 当前服务实例响应延时，单位:毫秒           |
| Database Connection Pool          | 当前服务实例数据库连接池相关信息          |
| Thread Pool                       | 当前服务实例线程池相关信息                |

#### Database Connection Pool 数据库连接池

| 指标                      | 作用                               |
| ------------------------- | ---------------------------------- |
| activeConnections         | 当前正在使用的数据库连接数量       |
| connectionTimeout         | 获取数据库连接的最大等待时间       |
| idleConnections           | 当前空闲的数据库连接数量           |
| idleTimeout               | 空闲连接在被关闭前的最大存活时间   |
| leakDetectionThreshold    | 连接泄漏检测的时间阈值             |
| maximumPoolSize           | 连接池中允许的最大连接数量         |
| minimumldle               | 连接池中保持的最小空闲连接数量     |
| threadsAwaitingConnection | 当前等待获取数据库连接的线程数量   |
| totalConnections          | 连接池中活动连接和空闲连接的总数量 |
| validationTimeout         | 连接验证的最大等待时间             |

#### 线程池

| 指标           | 作用                     |
| -------------- | ------------------------ |
| active_size    | 当前正在执行任务的线程数 |
| core_pool_size | 线程池中核心线程的数量   |
| max_pool_size  | 线程池中允许的最大线程数 |
| pool_size      | 当前线程池中的线程数     |
| queue_size     | 当前等待执行的任务数     |

### Trace 链路追踪

对请求端点的链路进行追踪

### JVM

| 指标                   | 作用                                    |
| ---------------------- | --------------------------------------- |
| JVM CPU (%)            | JVM相对于宿主机的CPU占用百分比          |
| JVM Memory (MB)        | JVM 内存占用情况，单位:MB               |
| JVM GC Time (ms)       | JVM 垃圾回收时间所消耗的时间，单位:毫秒 |
| JVM GC Count           | JVM 垃圾回收次数量                      |
| JVM Thread Count       | 当前实例的线程数量                      |
| JVM Thread State Count | 当前实例的各状态线程数量                |
| JVM Class Count        | 当前实例类的数量                        |

#### JVM Memory

| 指标       | 作用                       |
| ---------- | -------------------------- |
| noheap_max | JVM 中非堆内存的最大限制值 |
| noheap     | JVM 中实际使用的非堆内存量 |
| heap_max   | JVM 中堆内存的最大限制值   |
| heap       | JVM 中实际使用的堆内存量   |

#### JVM GC

| 指标      | 作用       |
| --------- | ---------- |
| young_gc  | 年轻代回收 |
| old_gc    | 老年代回收 |
| normal_gc | 正常回收   |

#### JVM Thread Count

| 指标   | 作用               |
| ------ | ------------------ |
| live   | 活动线程数量       |
| daemon | 守护线程数量       |
| peak   | 活动线程的峰值数量 |

#### JVM Thread State Count

| 指标          | 作用         |
| ------------- | ------------ |
| timed_waiting | 计时等待状态 |
| blocked       | 阻塞状态     |
| waiting       | 等待状态     |
| runnable      | 可运行状态   |

#### JVM Class Count

| 指标           | 作用               |
| -------------- | ------------------ |
| loaded         | 已加载的类的数量   |
| total_loaded   | 总共加载的类的数量 |
| total_unloaded | 卸载的类的数量     |

## Endpoint 端点

一般情况下SpringMVC的一个接口就是一个端点

| 指标                                     | 作用                          |
| ---------------------------------------- | ----------------------------- |
| Endpoint Load (calls/min)                | 每分钟请求次数                |
| Endpoint Success Rate (%)                | 接口请求成功率                |
| Endpoint Latency (ms)                    | 接口延时，单位:毫秒           |
| Endpoint Avg Response Time (ms)          | 接口平均响应延时，单位:毫秒   |
| Endpoint Response Time Percentile (ms)   | 服务响应延时百分比，单位:毫秒 |
| Message Queue Avg Consuming Latency (ms) | 消息队列平均消费时间          |

