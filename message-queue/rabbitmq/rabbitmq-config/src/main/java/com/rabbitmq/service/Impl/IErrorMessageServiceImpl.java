package com.rabbitmq.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rabbitmq.doMain.ErrorMessage;
import com.rabbitmq.doMain.Weather;
import com.rabbitmq.mapper.ErrorMessageMapper;
import com.rabbitmq.service.IErrorMessageService;
import org.springframework.stereotype.Service;

@Service
public class IErrorMessageServiceImpl extends ServiceImpl<ErrorMessageMapper, ErrorMessage> implements IErrorMessageService {

}
