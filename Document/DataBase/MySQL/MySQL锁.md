# MySQL锁

## 表状态

```sql
-- 查看表的状态
SHOW OPEN TABLES WHERE `TABLE` = '表名';

-- 查看正在使用的表
SHOW OPEN TABLES where `In_use` > 0;

-- 查看被锁定的表
SHOW OPEN TABLES where `Name_locked` = 1;
```

| 列名        | 作用                                                         |
| ----------- | ------------------------------------------------------------ |
| Database    | 表所属的数据库名称                                           |
| Table       | 表的名称                                                     |
| In_use      | 表当前是否正在被使用。如果表正在被使用，则该值为大于0的数字，否则为0 |
| Name_locked | 表是否被名称锁定，如果表被锁定，则该值为`1`，否则为`0`       |



## 事物信息

```sql
SELECT
    trx_id AS  `ID`,
    trx_state AS `状态`,
    trx_requested_lock_id  AS  `需要等待的资源`,
    trx_wait_started    AS  `开始等待时间`,
    trx_tables_in_use AS `使用表`,
    trx_tables_locked AS `拥有锁`,
    trx_rows_locked  AS `锁定行`,
    trx_rows_modified  AS `更改行`
FROM INFORMATION_SCHEMA.INNODB_TRX;
```



