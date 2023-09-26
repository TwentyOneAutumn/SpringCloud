# Validation

| 注解              | 作用                                         |
| ----------------- | -------------------------------------------- |
| `@NotBlank`       | 验字符串是否非空且长度大于 0（不包括空格）   |
| `@NotNull`        | 校验对象是否为非空                           |
| `@NotEmpty`       | 集合或数组是否非空                           |
| `@Size`           | 校验字符串、集合或数组的大小是否在指定范围内 |
| `@Min`            | 校验数值是否大于等于指定值                   |
| `@Max`            | 校验数值是否小于等于指定值                   |
| `@Pattern`        | 校验字符串是否符合指定的正则表达式           |
| `@Email`          | 校验字符串是否为有效的电子邮件地址           |
| `@Digits`         | 校验数值是否符合指定的整数和小数位数限制     |
| `@DecimalMin`     | 校验数值是否大于等于指定值                   |
| `@DecimalMax`     | 校验数值是否小于等于指定值                   |
| `@Positive`       | 校验数值是否为正数                           |
| `@Negative`       | 校验数值是否为负数                           |
| `@PositiveOrZero` | 校验数值是否为非负数（包括零）               |
| `@NegativeOrZero` | 校验数值是否为非正数（包括零）               |
| `@Future`         | 校验日期或时间是否为将来的时间               |
| `@Past`           | 校验日期或时间是否为过去的时间               |
| `@Valid`          | 标记属性或参数需要递归校验嵌套对象           |
| `@AssertTrue`     | 校验布尔类型属性是否为 true                  |
| `@AssertFalse`    | 校验布尔类型属性是否为 false                 |



---



`@Validated` 是 Spring 框架中的一个注解，它用于在方法级别上触发参数校验。

`@Validated` 注解提供了一种方便的方式，可以将参数校验与 Spring MVC 或 Spring Boot 中的请求处理方法结合起来。它基于 Java Bean Validation（JSR 380）规范，并与 Spring 的验证机制集成。

使用 `@Validated` 注解的主要作用如下：

1. 触发参数校验：在 Spring MVC 或 Spring Boot 的控制器方法中，使用 `@Validated` 注解可以触发方法参数的校验。当请求到达该方法时，Spring 会自动对方法参数进行校验，并根据校验结果进行处理。
2. 支持分组校验：`@Validated` 注解支持指定校验分组（Validation Group），可以在不同的场景下选择性地进行参数校验。通过在 `@Validated` 注解上添加分组信息，可以根据不同的场景选择性地启用特定的校验规则。
3. 提供校验异常处理：当校验失败时，`@Validated` 注解会抛出 `MethodArgumentNotValidException` 异常。您可以通过全局异常处理或自定义异常处理来捕获该异常，并对校验失败的情况进行适当的处理。

需要注意的是，`@Validated` 注解是 Spring 特有的注解，用于在 Spring MVC 或 Spring Boot 的请求处理方法中触发参数校验。它与 Hibernate Validator 的 `@Valid` 注解类似，但有一些区别。`@Validated` 注解在 Spring 环境中提供了更多的功能和扩展性，例如支持分组校验和特定的异常处理。

总结来说，`@Validated` 注解用于触发 Spring MVC 或 Spring Boot 中请求处理方法的参数校验，并提供了一些额外的特性，以方便地进行参数校验和处理校验异常。



---

