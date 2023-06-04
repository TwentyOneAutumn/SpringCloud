# Redis



-----



### 介绍

Redis是一个高性能的Key，Value结构的非关系缓存数据库



-----



### Redis的数据类型

| 数据类型 | 可存储的值           | 操作                              |
| -------- | -------------------- | --------------------------------- |
| String   | 字符串，整数，浮点数 | 做简单的键值对存储                |
| List     | 有序列表，可重复     | 存储一些列表类型的数据结构        |
| Set      | 唯一无序集合         | 进行交集，并集，差集的操作        |
| Hash     | 包含键值对的无序列表 | 结构化数据                        |
| ZSet     | 唯一有序集合         | 去重，可以根据Value的分数进行排序 |



-----



### Redis命令参考

https://www.redis.net.cn/order/

https://doc.redisfans.com/text-in



---



### Redis命令



##### key

| 命令                 | 描述                                          |
| -------------------- | --------------------------------------------- |
| type key             | 返回 key 所储存的值的类型                     |
| rename oldKey newKey | 修改 key 的名称                               |
| rersist key          | 移除 key 的过期时间，key 将持久保持           |
| move key dbName      | 将当前数据库的 key 移动到给定的数据库 db 当中 |
| ttl key              | 以秒为单位，返回给定 key 的剩余生存时间       |
| pttl key             | 以毫秒为单位返回 key 的剩余的过期时间         |
| del key              | 该命令用于在 key 存在是删除 key               |
| exists key           | 检查给定 key 是否存在                         |
| keys key1 key2 ...   | 查找所有符合给定模式的 key 。                 |



##### String

| 命令                           | 描述                                                         |
| ------------------------------ | ------------------------------------------------------------ |
| setx key val                   | 只有在 key 不存在时设置 key 的值                             |
| getrange key start end         | 返回 key 中字符串值的子字符                                  |
| mset key val key val ...       | 同时设置一个或多个 key-value 对                              |
| setex key timeout value        | 将值 value 关联到 key ，并将 key 的过期时间设为 seconds (以秒为单位)。 |
| set key val                    | 设置指定 key 的值                                            |
| get key                        | 获取指定 key 的值。                                          |
| incr key                       | 将 key 中储存的数字值加1                                     |
| decr key                       | 将 key 中储存的数字值减1                                     |
| incrby key num                 | key 所储存的值加上给定的值                                   |
| decrby key num                 | key 所储存的值减去给定的值                                   |
| strlen key                     | 返回 key 所储存的字符串值的长度                              |
| msetnx key1 val1 key2 val2 ... | 同时设置一个或多个 key-value 对，当且仅当所有给定 key 都不存在 |



##### list

| 命令                     | 描述                                                         |
| ------------------------ | ------------------------------------------------------------ |
| lindex key index         | 通过索引获取列表中的元素                                     |
| rpush key val1 val2 ...  | 在列表中添加一个或多个值                                     |
| lrange key start end     | 获取列表指定范围内的元素                                     |
| lset key index val       | 通过索引设置列表元素的值                                     |
| lpush key val1 val2 ...  | 将一个或多个值插入到列表头部                                 |
| lpushx key val1 val2 ... | 如果Key存在，将一个或多个值插入到列表头部                    |
| lpop key                 | 移出并获取列表的第一个元素                                   |
| rpop key                 | 移除并获取列表最后一个元素                                   |
| blpop key                | 移出并获取列表的第1个元素，如果没有元素会阻塞等待，直到获取  |
| brpop key                | 移出并获取列表的最后1个元素，如果没有元素会阻塞等待，直到获取 |



##### hash

| 命令                                  | 描述                              |
| ------------------------------------- | --------------------------------- |
| hset key field val                    | 设置field-val键值对到Key中        |
| hget key field                        | 获取Key中匹配field的值            |
| hmset key field1 val1 field2 val2 ... | 设置多对field-val键值对到Key中    |
| hmget key field1 field2 ...           | 获取Key中所有匹配field的值        |
| hgetall key                           | 获取Key中所有的field-val键值对    |
| hexists key field                     | 查看key中field是否存在            |
| hincrby key field num                 | 为key中的指定field的整数值加上num |
| hdecrby key field num                 | 为key中的指定field的整数值减去num |
| hlen key                              | 获取key中键值对的数量             |
| hdel key field1 field2 ...            | 删除一个或多个field匹配的键值对   |
| hvals key                             | 获取key中所有的val                |
| hkeys                                 | 获取哈希表中所有键                |
| hsetnx key                            | 在key中field不存在时设置          |



##### Set

| 命令                          | 描述                            |
| ----------------------------- | ------------------------------- |
| sadd key val1 val2 ...        | 向key中添加一个或多个 val       |
| scard key                     | 返回key中的val数量              |
| smembers                      | 返回key中所有val                |
| srem key val1 val2 ...        | 移除key中一个或多个val          |
| smove key1 key2 val           | 将key1中的val移除并添加到key2中 |
| sismember key val             | 判断key中是否有val              |
| sdiff key1 key2 ...           | 返回所有集合差集val             |
| sdiffstore key key1 key2 ...  | 将多个key*的差集存储在key中     |
| sinter key1 key2 ...          | 返回多个key的交集val            |
| sinterstore key key1 key2 ... | 将多个key*的交集存储在key中     |
| sunion key1 key2 ...          | 返回多个key的交集val            |
| sunionstore key key1 key2 ... | 将多个key*的并集存储在key中     |



##### ZSet

| 命令 | 描述 |
| ---- | ---- |
|      |      |
|      |      |
|      |      |
|      |      |
|      |      |
|      |      |
|      |      |



---



### RDB

Redis默认的持久化策略就是RDB模式

可以在指定的时间间隔内生成snapshot(数据快照)，默认保存到dump.rdb文件中，当Redis重启后会自动加载该文件的内容到内存中



##### SAVE

同步保存，当用户输入save命令时，会阻塞其他所有操作，直到Redis完成数据的持久化后，才会进行别的操作



##### BGSAVE

异步保存，分出一个子进程进行持久化操作，不影响父进程的操作



##### 优点

1. RDB文件是一个紧凑文件，直接使用RDB文件就可以还原数据
2. 数据保存会由一个子进程进行保存，不影响父进程
3. 因为是直接读取文件，所以恢复数据的效率要高于AOF



##### 缺点

1. 因为RDB是间隔保存，如果在间隔时间内Redis意外关闭，可能会导致数据丢失
2. 由于每次保存数据都需要分出子进程，在数据量较大时会比较耗费性能



---



### AOF

AOF默认关闭，需要在配置文件中开启AOF，Redis支持RDB和AOF同时生效，如果两种都存在，则AOF的优先级高于RDB，在Redis重新启动时，就会使用AOF进行数据恢复

AOF是通过监听Redis执行命令，如果发现执行了数据修改操作，就会同时直接将数据同步到数据库文件中



##### 开始AOF

修改redis.conf中appendonly属性为yes，可以修改appendfilename属性来指定AOF文件的名称



##### 优点

1. 相对于RDB模式来说，数据更加安全，不容易丢失数据



##### 缺点

1. 相同数据集AOF要大于RDB
2. 相对于RDB恢复数据会慢一些











































































