package com.database.multiDataSource;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 在启动类上添加此注解，以开启多数据源
 * 示例的yaml配置如下
 * multi:
 *   datasource:
 *     - name: 数据源名称(唯一,非空)
 *       driver-class-name: 驱动类名(非空)
 *       url: 数据库连接地址(非空)
 *       username: 用户名(非空)
 *       password: 密码(非空)
 *       mapper-scan: Mapper接口包扫描(唯一,非空)
 *       xml-scan: xml影射文件包扫描(唯一)
 */
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({MultiDataRegisterSourceFactory.class})
public @interface EnableMultiDataSource {

}
