# MyBatis重点知识



### XML映射标签

| 标签        |                                                              |
| ----------- | ------------------------------------------------------------ |
| mapper      | 相当于定义了一个Mapper层接口的实现类，用其namespace属性映射Mapper层接口 |
| select      | 定义在mapper标签下，标签里编写查询操作SQL，使用ID属性标识映射的接口方法 |
| insert      | 定义在mapper标签下，标签里编写新增操作SQL，使用ID属性标识映射的接口方法 |
| update      | 定义在mapper标签下，标签里编写更新操作SQL，使用ID属性标识映射的接口方法 |
| delete      | 定义在mapper标签下，标签里编写删除操作SQL，使用ID属性标识映射的接口方法 |
| sql         | 定义公共sql，通过include标签引用                             |
| include     | 引用sql，refid = sql的id                                     |
| resultMap   | 定义映射关系，通过type指定要返回的对象类型，其他标签通过resultMap = "id"引用 |
| result      | 映射表字段与属性值，通过column指定表字段名，property指定属性名 |
| association | 定义在resultMap内，用于解决一对一关系，通过property指定对象名，通过javaType指定对象类型 |
| collection  | 定义在resultMap内，用于解决一对多关系，通过property指定集合或数组名，通过ofType指定集合中元素的类型 |
| cache       | 开启二级缓存，可通过type=类全路径来指定使用那个二级缓存器    |



-----



### 标签属性

| 属性             | 说明                                                         |
| ---------------- | ------------------------------------------------------------ |
| resultType       | 定义返回值类型，需要跟映射的Mapper接口中方法保持一致         |
| paramaterType    | 定义参数类型，需要跟映射的Mapper接口中方法保持一致           |
| useGeneratedKeys | 更新操作中，设置是否使用数据库自增主键策略回填主键值，配合keyPorperty使用 |
| keyPorperty      | 生成的主键值用那个参数储存，配合useGeneratedKeys使用         |
| resultMap        | 指定要引用的resultMap                                        |
| column           | 指定表字段名                                                 |
| property         | 指定属性名                                                   |
| javaType         | 指定元素类型                                                 |
| ofType           | 指定集合中元素的类型                                         |



-----



### SQL占位符

| 占位符 | 说明                                                         |
| ------ | ------------------------------------------------------------ |
| #{}    | 底层使用prepareStatement对象执行SQL，可以防止预编译，SQL注入等 |
| ${}    | 底层使用Stament对象执行SQL，无法防止预编译，SQL注入等        |



-----



### 注解

| 注解   | 说明                                                         |
| ------ | ------------------------------------------------------------ |
| @Param | 加在参数前，Value为别名，通过#{别名}引用，底层是将参数封装为Map |



-----



### 动态SQL

| 标签      | 属性                      | 说明                                                         |
| --------- | ------------------------- | ------------------------------------------------------------ |
| if        | test = "表达式"           | 当表达式成立时，拼接标签中内容，表达式可以直接写属性值或Key值 |
| where     |                           | 如果标签内部有条件，会动态拼接where关键字和条件，并自动去除多余的and |
| choose    |                           | 相当于case                                                   |
| when      | test = "表达式"           | 相当于when，多个when存在时，只会拼接第一个匹配的when         |
| otherwise |                           | 相当于else，当没有when匹配时，匹配otherwise                  |
| set       |                           | 用于update操作，替换set关键字并动态去除if中多余的 **,**      |
| trim      |                           | 用于动态拼接和去除前缀后缀                                   |
| trim      | prefix = "值"             | 动态拼接值为前缀                                             |
| trim      | prefixOrerrides = "值"    | 要去除的前缀                                                 |
| trim      | suffix = "值"             | 动态拼接值为后缀                                             |
| trim      | suffixOrerrides = "值"    | 要去除的后缀                                                 |
| foreach   |                           |                                                              |
| foreach   | collection = "集合或数组" | 要遍历的对象                                                 |
| foreach   | separator = "值"          | 多个元素取出后，用什么分割                                   |
| foreach   | open = "值"               | 以什么开头                                                   |
| foreach   | close = "值"              | 以什么结尾                                                   |
| foreach   | item = "name"             | 本次循环从集合取出的元素的name，如果是Map，则为Value         |
| foreach   | index = "值"              | 本次循环的下标，如果是Map，则为Key                           |
| bind      |                           | 绑定一对数据，Key为name属性，Value为value属性，通过#{name}   |



-----



### 多表关联查询之一对一



-----



##### Order表结构

| 表字段   | 类型   | 主外键             | 说明        |
| -------- | ------ | ------------------ | ----------- |
| order_id | number | 主键，自增         | Order表主键 |
| user_id  | number | 关联User表id，外键 | userId      |
| price    | number | default：0         | 价格        |



##### User表结构

| 表字段    | 类型                     | 主外键    |
| --------- | ------------------------ | --------- |
| user_id   | 关联Order表user_id，主键 | 主键 自增 |
| user_name | userName                 | userName  |



##### Order表数据

| order_id | user_id | price |
| -------- | ------- | ----- |
| 1        | 22      | 200   |
| 2        | 22      | 300   |
| 3        | 23      | 400   |
| 4        | 23      | 500   |



##### User表数据

| user_id | user_name |
| ------- | --------- |
| 22      | 张三      |
| 23      | 李四      |



##### 实体类

`````java
package com.demo.order.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pojo.User;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private Long orderId;
    private Long userId;
    private String price;
    private User user;
}
`````

```java
package com.demo.order.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pojo.User;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long userId;
    private String userName;
}
```



##### Mapper

```java
package com.demo.order.mapper;

import pojo.Order;

public interface TestMapper {
    public Order queryOrderAndUser(Order order);
}
```



##### XML

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "mybatis-3-mapper.dtd" >

<mapper namespace="com.demo.order.mapper.TestMapper">
    <resultMap id="orderAndUserResultMap" type="Order">
        <result column="order_id" property="orderId"></result>
        <result column="user_id" property="userId"></result>
        <result column="price" property="price"></result>
        <association property="user" javaType="User">
            <id column="user_id" property="userId"></id>
            <result column="user_name" property="userName"></result>
        </association>
    </resultMap>

    <select id="queryOrderAndUser" resultMap="orderAndUserResultMap">
        select
            t1.order_id,
            t1.user_id,
            t1.price,
            t2.user_name
        from
        order as t1 left join user as t2
            on t1.order_id = t2.user_id
        where t1.order_id = #{orderId}
    </select>
</mapper>
```



-----



### 多表关联查询之一对多



-----



##### Order表结构

| 表字段   | 类型   | 主外键             | 说明        |
| -------- | ------ | ------------------ | ----------- |
| order_id | number | 主键，自增         | Order表主键 |
| user_id  | number | 关联User表id，外键 | userId      |
| price    | number | default：0         | 价格        |



##### User表结构

| 表字段    | 类型                     | 主外键    |
| --------- | ------------------------ | --------- |
| user_id   | 关联Order表user_id，主键 | 主键 自增 |
| user_name | userName                 | userName  |



##### Order表数据

| order_id | user_id | price |
| -------- | ------- | ----- |
| 1        | 22      | 200   |
| 2        | 22      | 300   |
| 3        | 23      | 400   |
| 4        | 23      | 500   |



##### User表数据

| user_id | user_name |
| ------- | --------- |
| 22      | 张三      |
| 23      | 李四      |



##### 实体类

`````java
package com.demo.order.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pojo.User;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private Long orderId;
    private Long userId;
    private String price;
}
`````

```java
package com.demo.order.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pojo.User;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class user {
    private Long userId;
    private String userName;
    private List<Order> orderList;
}
```



##### Mapper

```java
package com.demo.order.mapper;

import pojo.Order;

public interface TestMapper {
    public User queryUserAndOrders(Order order);
}
```



##### XML

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "mybatis-3-mapper.dtd" >

<mapper namespace="com.demo.order.mapper.TestMapper">
    <resultMap id="userAndOrderResultMap" type="order">
        <result column="user_id" property="userId"></result>
        <result column="user_name" property="userName"></result>
        <collection property="orderList" javaType="user">
            <result column="order_id" property="orderId"></result>
            <id column="user_id" property="userId"></id>
            <result column="price" property="price"></result>
        </collection>
    </resultMap>

    <select id="queryUserAndOrders" resultMap="userAndOrderResultMap">
        select
            t1.user_id,
            t1.user_name,
            t2.order_id,
            t2.price
        from
        user as t1 left join order as t2
            on t1.user_id = t2.order_id
        where t1.user_id = #{userId}
    </select>
</mapper>
```



-----



### 多表关联查询之多对多



-----



##### Order表结构

| 表字段   | 类型   | 主外键                         | 说明        |
| -------- | ------ | ------------------------------ | ----------- |
| order_id | number | 主键，关联UserRecord表order_id | Order表主键 |
| price    | number | default：0                     | 价格        |



##### UserRecord表结构

| 表字段   | 类型   | 主外键              | 说明    |
| -------- | ------ | ------------------- | ------- |
| order_id | number | 关联order表order_id | orderId |
| user_id  | number | 关联User表user_id   | userId  |



##### User表结构

| 表字段    | 类型                          | 主外键    |
| --------- | ----------------------------- | --------- |
| user_id   | 主键，关联UserRecord表user_id | 主键 自增 |
| user_name | userName                      | userName  |



##### Order表数据

| order_id | user_id | price |
| -------- | ------- | ----- |
| 1        | 22      | 200   |
| 2        | 22      | 300   |
| 3        | 23      | 400   |
| 4        | 23      | 500   |



##### UserRecord表数据

| user_id | order_id |
| ------- | -------- |
| 22      | 1        |
| 22      | 2        |
| 23      | 3        |
| 23      | 4        |



##### User表数据

| user_id | user_name |
| ------- | --------- |
| 22      | 张三      |
| 23      | 李四      |



##### 实体类

`````java
package com.demo.order.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pojo.User;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private Long orderId;
    private String price;
}
`````

```java
package com.demo.order.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pojo.User;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long userId;
    private String userName;
    private List<UserRecord> userRecordList;
}
```

```java
package com.demo.order.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pojo.User;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRecord {
    private Long userId;
    private Long orderId;
    private Order order;
}
```



##### Mapper

```java
package com.demo.order.mapper;

import pojo.Order;

public interface TestMapper {
    public User queryUserAndOrders(User user);
}
```



##### XML

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "mybatis-3-mapper.dtd" >

<mapper namespace="com.demo.order.mapper.TestMapper">
    <resultMap id="userAndOrderResultMap" type="user">
        <result column="user_id" property="userId"></result>
        <result column="user_name" property="userName"></result>
        <collection property="userRecordList" ofType="userRecord">
            <id column="user_id" property="userId"></id>
            <id column="order_id" property="orderId"></id>
            <association property="order" javaType="order">
                <id column="order_id" property="orderId"></id>
                <result column="price" property="price"></result>
            </association>
        </collection>
    </resultMap>

    <select id="queryUserAndOrders" resultMap="userAndOrderResultMap">
        select
            t1.user_id,
            t1.user_name,
            t2.order_id,
            t3.price
        from
        user as t1
        left join
        userRecord as t2
            on t1.user_id = t2.user_id
        left join
        order as t3
            on t2.order_id = t3.order_id
        where t1.user_id = #{userId}
    </select>
</mapper>
```



-----



### 级联查询



-----



##### Order表结构

| 表字段   | 类型   | 主外键             | 说明        |
| -------- | ------ | ------------------ | ----------- |
| order_id | number | 主键，自增         | Order表主键 |
| user_id  | number | 关联User表id，外键 | userId      |
| price    | number | default：0         | 价格        |



##### User表结构

| 表字段    | 类型                     | 主外键    |
| --------- | ------------------------ | --------- |
| user_id   | 关联Order表user_id，主键 | 主键 自增 |
| user_name | userName                 | userName  |



##### Order表数据

| order_id | user_id | price |
| -------- | ------- | ----- |
| 1        | 22      | 200   |
| 2        | 22      | 300   |
| 3        | 23      | 400   |
| 4        | 23      | 500   |



##### User表数据

| user_id | user_name |
| ------- | --------- |
| 22      | 张三      |
| 23      | 李四      |



##### 实体类

`````java
package com.demo.order.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pojo.User;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private Long orderId;
    private Long userId;
    private String price;
}
`````

```java
package com.demo.order.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pojo.User;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long userId;
    private String userName;
    private List<Order> orderList;
}
```



##### Mapper

```java
package com.demo.order.mapper;

import pojo.Order;

public interface TestMapper {
    
    public User queryUser(User user);
    
    public Order queryUser(Long userId);
}
```



##### XML

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "mybatis-3-mapper.dtd" >

<mapper namespace="com.demo.order.mapper.TestMapper">
    <resultMap id="userAndOrderResultMap" type="user">
        <result column="user_id" property="userId"></result>
        <result column="user_name" property="userName"></result>
        <!--  
            property:属性
            javaType:属性类型
            select:要级联的SQL，填写方法Mapper的全路径
            column:级联查询要传入的参数的property
            fetchType:lazy 懒加载 eager 立即加载 可以覆盖全局配置
        -->
        <collection property="orderList"
                    javaType="list"
                    select="com.demo.order.mapper.TestMapper.queryOrder"
                    column="userId"
                    fetchType="lazy">
            <result column="order_id" property="orderId"></result>
            <id column="user_id" property="userId"></id>
            <result column="price" property="price"></result>
        </collection>
    </resultMap>

    <resultMap id="orderResultMap" type="order">
        <id column="order_id" property="orderId"></id>
        <result column="user_id" property="userId"></result>
        <result column="price" property="price"></result>
    </resultMap>

    <select id="queryUser" resultMap="userAndOrderResultMap">
        select
            user_id,
            user_name
        from user
        where user_id = #{userId}
    </select>

    <select id="queryOrder" resultMap="orderResultMap">
        select * from order
        where user_id = #{userId}
    </select>
</mapper>
```



-----



### 延迟加载

通过设置懒加载可以使关联对象延迟加载，即执行级联查询时，内级属性的SQL不会被立即执行，而是当内级属性被调用时，才会调用SQL并填充值

```yaml
mybatis:
    lazy-loading-enabled: true  # 开启全局关联对象延迟加载，所有的关联对象都会延迟加载，可以通过fetchType属性覆盖全局配置
    aggressive-lazy-loading: f # 开启全局懒加载，当开启时，Mybatis所有方法都会懒加载对象的所有属性
```



-----



### 注解开发和XML开发

| XML                                                 | 注解                                           |
| --------------------------------------------------- | ---------------------------------------------- |
| 类和类之间解耦                                      | 简化配置                                       |
| 利于修改，直接修改XML文件，无需修改源代码再重新编译 | 使用起来更直观，提高开发效率                   |
| 配置集中在XML文件中，对象关系易读，利于维护         | 注解的解析可以不依赖第三方库，可以通过反射解析 |
| 容易和其他系统进行数据交换                          | 不能使用动态SQL，级联查询等复杂功能            |
| 用来定义动态SQL，级联查询，Map映射等复杂SQL         | 用来定义一些不会修改的基础SQL                  |



-----



### 一级缓存

![](.\img\Mybatis一级缓存图.png)

##### 一级缓存流程

1. 调用Mapper方法
2. Mybatis调用SqlSession
3. SqlSession查询缓存，如果有，就从缓存中取，如果没有就调用数据库
4. 数据库返回结果集
5. SqlSession接收结果集后会存储到缓存区，缓存为Key=Value结构，Key为调用Mapper的namespace + 调用的SQL的ID + 参数 的 hash值 构成一个Key，Value为数据库返回的结果集
6. 返回结果集



> **一级缓存的作用域为SqlSession，所以只有使用同一个SqlSession多次访问时，一级缓存才会生效**
>
> **一级缓存会在当前SqlSession执行增删改或者调用commit，会自动清空当前SqlSession的缓存**
>
> **一级缓存基于内存做缓存**



-----



### 二级缓存

1. 调用Mapper方法
2. Mybatis调用SqlSession
3. SqlSession查询当前namespace的缓存区，如果有，就从缓存中取，如果没有就调用数据库
4. 数据库返回结果集
5. SqlSession接收结果集后会存储到namespace缓存区，缓存为Key=Value结构，Key为调用Mapper的namespace + 调用的SQL的ID + 参数 的 hash值 构成一个Key，Value为数据库返回的结果集
6. 返回结果集



##### 开启二级缓存

```yaml
mybatis:
  configuration:
    cache-enabled: true # 开启全局二级缓存
```





> **在XML中添加<cache/>标签开启此namespcace的二级缓存，在SQL标签上通过设置属性useCache="true"来标识此SQL使用二级缓存，通过设置属性flushCache="true"来标识执行此SQL之后刷新二级缓存**
>
> **二级缓存的作用域为namespace，所以只有使用同一个namespace下的SqlSession多次访问时，二级缓存才会生效**
>
> **SqlSession执行结果并关闭后，会把该SqlSession一级缓存中的数据添加到namespace的二级缓存中**
>
> **二级缓存会在当前namespace中的SqlSession调用commit，才会将数据放入二级缓存**
>
> **如果进行当前namespace下的SQL调用了另一个namespace下的SQL，如果第二个namespace下不通过cache-ref来引用当前namespace的二级缓存，就会导致两个namespace的二级缓存不一致，会出现脏读的情况**
>
> **二级缓存会将结果集序列化之后，存储到二级缓存中，需要使用二级缓存的POJO实体类实现Serializable接口，不然会抛出异常NotSerializableException，表示当前对象不支持序列化**
>
> **可以通过Redis等第三方来实现二级缓存**



-----



























-----

