package com.database.Interface;

import com.database.config.DataSourceConfiguration;
import org.springframework.context.annotation.Import;
import java.lang.annotation.*;

/**
 * 在启动类上添加此注解，以开启多数据源
 */
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({DataSourceConfiguration.class})
public @interface EnableMultiDataSourceConfig {
}
