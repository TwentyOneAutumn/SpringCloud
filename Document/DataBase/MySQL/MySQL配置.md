# MySQL配置

## [client]

| 配置                     | 说明                                                         |
| ------------------------ | ------------------------------------------------------------ |
| port                     | 端口                                                         |
| socket                   | socket文件地址                                               |
| character_set_client     | 用于定义客户端（应用程序）向服务器发送数据时使用的字符集     |
| character_set_connection | 用于定义客户端与服务器之间的连接所使用的字符集               |
| character_set_database   | 默认数据库使用的字符集                                       |
| character_set_results    | 用于向客户端返回查询结果的字符集,这包括列值等结果数据、列名等结果元数据和错误消息 |
| character_set_server     | 服务器默认字符集                                             |

## [mysqld]

| 配置                          | 说明                                                         |
| ----------------------------- | ------------------------------------------------------------ |
| autocommit                    | 是否开启自动提交自动提交模式                                 |
| datadir                       | MySQL服务器数据存放目录的路径                                |
| default_storage_engine        | 用于指定新创建的表使用的默认存储引擎                         |
| general_log                   | 用于启用或禁用通用查询日志                                   |
| general_log_file              | 通用查询日志文件的名称                                       |
| lc_messages                   | 用于错误消息的语言设置                                       |
| lock_wait_timeout             | 用于设置等待获取锁的最长时间                                 |
| log_error                     | 用于指定错误日志文件的路径和文件名                           |
| log_output                    | 用于指定日志输出的目标                                       |
| max_connect_errors            | 用于指定在处理连接错误时允许的最大错误数                     |
| max_connections               | 用于指定 MySQL 服务器允许的最大同时连接数                    |
| sql_require_primary_key       | 用于控制是否强制在存储引擎中创建一个主键`Primary Key`        |
| sql_safe_updates              | 用于控制是否启用安全更新模式(必须使用`WHERE`子句限制更新或删除的行) |
| system_time_zone              | 用于表示MySQL服务器当前使用的系统时间时区                    |
| time_zone                     | 用于表示当前会话的时区设置                                   |
| transaction_isolation         | 用于设置事务的隔离级别                                       |
| log_bin                       | 用于设置是否启用MySQL二进制日志(binlog)                      |
| log_bin_index                 | 用于指定二进制日志(binlog)索引文件的路径和文件名             |
| binlog_format                 | 用于指定二进制日志(binlog)的格式                             |
| binlog_row_image              | 用于控制`ROW`格式记录二进制日志(binlog)时,二进制日志中的行格式 |
| binlog_expire_logs_seconds    | 用于指定二进制日志(binlog)文件的自动清理时间(单位:秒)        |
| max_binlog_size               | 用于指定单个二进制日志(binlog)文件的最大大小                 |
| sync_binlog                   | 用于控制二进制日志(binlog)在多少个事务提交之后同步到磁盘     |
| lower_case_table_names        | 用于控制数据库表名在文件系统中的存储方式的大小写敏感性       |
| slow_query_log                | 用于启用或禁用慢查询日志记录                                 |
| slow_query_log_file           | 用于指定慢查询日志文件的路径和文件名                         |
| long_query_time               | 用于定义了查询执行时间超过多少时间才被记录到慢查询日志中(单位:秒) |
| log_queries_not_using_indexes | 用于执行时是否记录没有使用索引的查询                         |

