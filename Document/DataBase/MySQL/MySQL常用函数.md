# MySQL常用函数



## 字符串相关

### FIND_IN_SET

#### 参数

```sql
FIND_IN_SET(str,strlist)
```

#### 作用

> 查找在`大字符串`中是否包含`小字符串`,并自动分割`,`匹配,返回小字符串所在index,如果不存在则返回0

#### 示例

```sql
SELECT FIND_IN_SET('b','a,b,c'); -- 2

SELECT FIND_IN_SET('a','ab,b,c'); -- 0
```



### TRIM

#### 参数

```sql
TRIM(str)
```

#### 作用

> 去除字符串两边的空白字符

#### 示例

```sql
SELECT TRIM('  b  '); -- b
```



### CAST

#### 参数

```sql
CAST(expression AS dataType)
```

#### 作用

> 将一个表达式转换为指定的数据类型

#### 示例

```sql
SELECT CAST('2023-02-02 10:00:00' AS DATE); -- 2023-02-02

SELECT CAST('2023-02-02' AS DATETIME); -- 2023-02-02 00:00:00
```



### NULLIF

#### 参数

```sql
NULLIF(param2,param2)
```

#### 作用

> 比较两个参数,如果这两个参数相等,则返回 `NULL`,否则返回第一个参数的值

#### 示例

```sql
SELECT NULLIF('a','a'); -- null

SELECT NULLIF('a','b'); -- a
```



## 时间相关

### unit(时间单位)

| 关键字    | 单位 |
| --------- | ---- |
| `SECOND`  | 秒   |
| `MINUTE`  | 分钟 |
| `HOUR`    | 小时 |
| `DAY`     | 天   |
| `WEEK`    | 周   |
| `MONTH`   | 月   |
| `QUARTER` | 季度 |
| `YEAR`    | 年   |



### format(时间格式)

| 格式 | 示例 |
| ---- | ---- |
|      |      |
|      |      |
|      |      |



### TIMESTAMPDIFF

#### 参数

TIMESTAMPDIFF(unit,startTime,endTime)

#### 作用

> 计算两个时间的时间差(endTime - startTime),并转换为指定时间单位(unit),计算结果会直接舍去小数部分(取整)

#### 示例

```sql
SELECT TIMESTAMPDIFF(SECOND,'2023-09-01','2023-09-10'); -- 777600

SELECT TIMESTAMPDIFF(DAY,'2023-09-01','2023-09-10'); -- 9

 SELECT TIMESTAMPDIFF(YEAR,'2023-09-01','2023-09-10'); -- 0
```







### DATE_FORMAT(date,format)

#### 作用

> 将日期按照指定的格式进行格式化，并返回一个字符串

#### 示例

> DATE_FORMAT('2023-10-09 10:00:00', '%Y-%m-%d'); // 2023-10-09



### DATE_ADD(date, INTERVAL value unit)

#### 作用

> 用于在给定日期上执行加法操作，返回一个新的日期
>
> date：时间
>
> value：整数
>
> unit：时间单位

#### 示例

> SELECT DATE_ADD('2023-10-09', INTERVAL 5 DAY); // 2023-10-14



### DATE_SUB(date, INTERVAL value unit)

#### 作用

> 用于在给定日期上执行加法操作，返回一个新的日期
>
> date：时间
>
> value：整数
>
> unit：时间单位

#### 示例

> SELECT DATE_SUB('2023-10-09', INTERVAL 3 MONTH); // 2023-07-09




