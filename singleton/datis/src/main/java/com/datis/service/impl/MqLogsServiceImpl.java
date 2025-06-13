package com.datis.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.datis.domain.MqLogs;
import com.datis.mapper.MqLogsMapper;
import com.datis.service.IMqLogsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MqLogsServiceImpl extends ServiceImpl<MqLogsMapper, MqLogs> implements IMqLogsService {

    @Autowired
    private MqLogsMapper mqLogsMapper;

}
