package com.rabbitmq.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rabbitmq.doMain.ErrorMessage;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ErrorMessageMapper extends BaseMapper<ErrorMessage> {
}
