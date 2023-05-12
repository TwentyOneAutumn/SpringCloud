package com.core.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class TestAspect {
//    Spring Boot 使用 AspectJ 语法来定义切面表达式，AspectJ 是一个独立的面向切面编程的语言，它可以与 Java 代码无缝集成。下面是一些常见的切面表达式：

//    匹配方法：execution(modifiers-pattern? ret-type-pattern declaring-type-pattern? name-pattern(param-pattern)throws-pattern?)
//    其中各个部分的含义如下：
//    modifiers-pattern：修饰符匹配，例如 public、protected、private、final 等
//    ret-type-pattern：返回类型匹配，例如 void、int、java.util.List 等
//    declaring-type-pattern：类路径匹配，例如 com.example.MyClass、com.example.、、..* 等
//    name-pattern：方法名匹配，例如 foo、bar、*、Service、get 等
//    param-pattern：参数类型匹配，例如 java.lang.String、java.util.、 等
//throws-pattern：异常类型匹配，例如 java.lang.Exception、java.lang.RuntimeException 等
//    示例：
//    less
//    Copy code
    // 匹配返回类型为 void 的 UserService 中的所有方法
    @Pointcut("execution(void com.example.UserService.*(..))")
    public void userServiceMethod() {}

    // 匹配返回类型为 java.util.List 的 Service 中以 get 开头的方法，参数类型为 int
    @Pointcut("execution(java.util.List com.example.*.get*(int))")
    public void serviceGetMethod() {}
//    匹配注解：@annotation(annotation-type-pattern)
//    示例：
//    java
//    Copy code
    // 匹配所有被 @MyAnnotation 标注的方法
    @Pointcut("@annotation(com.example.MyAnnotation)")
    public void myAnnotationMethod() {}
//    匹配类：within(type-pattern)
//    示例：
//    java
//    Copy code
    // 匹配 com.example 包下的所有类中的方法
    @Pointcut("within(com.example..*)")
    public void withinExamplePackage() {}
//    匹配对象：this(target-pattern) 或者 target(target-pattern)
//    示例：
//    less
//    Copy code
    // 匹配实现了 MyInterface 接口的对象
    @Pointcut("this(com.example.MyInterface)")
    public void thisMyInterface() {}

    // 匹配实现了 MyInterface 接口的对象的方法
    @Pointcut("target(com.example.MyInterface)")
    public void targetMyInterface() {}
//    逻辑运算：&&（与）、||（或）、！（非）
//    示例：
//    java
//    Copy code
    // 匹配返回类型为 void 的 UserService 中的以 save 或 update 开头的方法
    @Pointcut("execution(void com.example.UserService.save*(..)) || execution(void com.example.UserService.update*(..))")
    public void saveOrUpdateMethod() {}
//    更多关于 AspectJ 的语法可以参考官方文档：https://www.eclipse.org/aspectj/doc/released/progguide/index.html
}