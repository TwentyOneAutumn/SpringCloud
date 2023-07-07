package com.rabbitmq.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.core.doMain.SysMenu;
import com.rabbitmq.doMain.ErrorMessage;
import com.rabbitmq.doMain.Weather;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ErrorMessageMapper extends BaseMapper<ErrorMessage> {
}
