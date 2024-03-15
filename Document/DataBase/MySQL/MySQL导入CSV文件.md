# MySQL导入CSV文件

## 修改配置

```properties
[mysqld]
# 设置加载文件的路径,空就是任何路径
secure_file_priv=
# 允许客户端从本地文件系统加载数据到MySQL服务器
local_infile=ON
```

## 重启MySQL

## 导入CSV文件

```sql
-- 导入本地CSV文件的路径
LOAD DATA LOCAL INFILE '/Users/maplej/Downloads/sys_test.csv'
-- 目标表
INTO TABLE sys_test_copy1
-- 字段之间的分隔符
FIELDS TERMINATED BY ',' 
-- 字段包裹符
ENCLOSED BY '"'
-- 行终止符
LINES TERMINATED BY '\n'
-- 跳过第一行(标题行)
IGNORE 1 LINES
-- 字段名映射,按照标题行的顺序进行映射
-- 例如标题行为:id,age,name
-- 目标表字段为:sid_id,sid_age,sid_name
-- 那么映射就为:(sid_id,sid_age,name)
-- 如果目标表字段为:sid_id,sid_name
-- 此时需要使用占位符 @dummy1 跳过不需要的字段
-- 那么映射就为:(sid_id,@dummy1,sid_name)
-- 使用占位符占用标题行age字段的位置,从而跳过age字段
(sid_id,@dummy1,sid_name)
-- 用来设置CSV中不存在而目标表需要的字段值
set sid_age='3';;
```





