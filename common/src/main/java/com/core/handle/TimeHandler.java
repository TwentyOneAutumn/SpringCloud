package com.core.handle;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 设置字段填充策略
 */
@Component
public class TimeHandler implements MetaObjectHandler {

    /**
     * 插入时填充
     * @param metaObject 元对象
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        strictInsertFill(metaObject,"createTime", LocalDateTime.class,LocalDateTime.now());
        strictUpdateFill(metaObject,"updateTime", LocalDateTime.class,LocalDateTime.now());
    }

    /**
     * 更新时填充
     * @param metaObject 元对象
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        strictUpdateFill(metaObject,"updateTime", LocalDateTime.class,LocalDateTime.now());
    }
}
