package com.wang.platform.message.service.impl;

import com.wang.platform.message.service.IKafkaService;
import com.wang.platform.utils.BeanCopyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaServiceImpl implements IKafkaService {

    @Autowired
    private KafkaTemplate kafkaTemplate;



    @Override
    public void sendMsg(String title, String jsonMsg) {
        kafkaTemplate.send(title, jsonMsg);
    }

    @Override
    public void sendMsg(String title, Object obj) {
        kafkaTemplate.send(title, BeanCopyUtils.obj2Byte(obj));
    }
}
