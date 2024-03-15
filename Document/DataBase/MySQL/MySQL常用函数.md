# MySQL常用函数



### FIND_IN_SET(小字符串,大字符串)

##### 作用

> 查找在大字符串中是否包含小字符串,并自动分割`,`匹配,返回小字符串所在index,如果不存在则返回0

##### 例子

> SELECT FIND_IN_SET('b','a,b,c'); // 2
>
> SELECT FIND_IN_SET('a','ab,b,c'); // 0



### 时间单位

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

##### 

### TIMESTAMPDIFF(unit,startTime,endTime)

##### 作用

> 计算时间差,并转换为对应时间单位(直接舍去小数部分,取整)

##### 例子

> SELECT TIMESTAMPDIFF(SECOND,'2023-09-01','2023-09-10'); // 777600
>
> SELECT TIMESTAMPDIFF(DAY,'2023-09-01','2023-09-10'); // 9
>
> SELECT TIMESTAMPDIFF(YEAR,'2023-09-01','2023-09-10'); // 0



### NULLIF(param2,param2)

##### 作用

> 比较两个参数,如果这两个参数相等,则返回 `NULL`,否则返回第一个参数的值

##### 例子

> SELECT NULLIF('a','a'); // null
>
> SELECT NULLIF('a','b'); // a



### TRIM(str)

##### 作用

> 去除字符串两边的空白字符

##### 例子

> SELECT TRIM('  b  '); // b
>



### CAST(expression AS dataType)

##### 作用

> 将一个表达式转换为指定的数据类型

##### 例子

> SELECT CAST('2023-02-02 10:00:00' AS DATE); // 2023-02-02
>
> SELECT CAST('2023-02-02' AS DATETIME); // 2023-02-02 00:00:00



### DATE_FORMAT(date,format)

##### 作用

> 将日期按照指定的格式进行格式化，并返回一个字符串

##### 例子

> DATE_FORMAT('2023-10-09 10:00:00', '%Y-%m-%d'); // 2023-10-09



### DATE_ADD(date, INTERVAL value unit)

##### 作用

> 用于在给定日期上执行加法操作，返回一个新的日期
>
> date：时间
>
> value：整数
>
> unit：时间单位

##### 例子

> SELECT DATE_ADD('2023-10-09', INTERVAL 5 DAY); // 2023-10-14



### DATE_SUB(date, INTERVAL value unit)

##### 作用

> 用于在给定日期上执行加法操作，返回一个新的日期
>
> date：时间
>
> value：整数
>
> unit：时间单位

##### 例子

> SELECT DATE_SUB('2023-10-09', INTERVAL 3 MONTH); // 2023-07-09

