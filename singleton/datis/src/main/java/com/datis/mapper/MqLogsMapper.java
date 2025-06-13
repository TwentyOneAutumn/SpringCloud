package com.datis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.datis.domain.MqLogs;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MqLogsMapper extends BaseMapper<MqLogs> {
}
