package com.wang.platform.message.service.impl;

import com.wang.platform.message.entity.ConsumerSetting;
import com.wang.platform.message.service.IConsumerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ConsumerServiceImpl implements IConsumerService {
    @Override
    public void build(ConsumerSetting consumer) {

    }

    @Override
    public void destroy(ConsumerSetting consumer) {

    }

    @Override
    public void changeStatus(ConsumerSetting consumer) {

    }

    @Override
    public void changeUri(ConsumerSetting consumer) {

    }

    @Override
    public ConsumerSetting getConsumer(ConsumerSetting consumer) {
        return null;
    }

    @Override
    public List<ConsumerSetting> getConsumers(ConsumerSetting consumer) {
        return null;
    }
}
