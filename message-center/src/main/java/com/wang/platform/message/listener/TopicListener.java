package com.wang.platform.message.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.AbstractMessageListenerContainer;
import org.springframework.kafka.listener.config.ContainerProperties;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TopicListener {


    @KafkaListener(topicPattern = "json.*")
    public void jsonMsg(ConsumerRecord<?, ?> record, Acknowledgment ack) {
        ack.acknowledge();//提交offset
        System.out.println("json====================================");
        System.out.println(record);
    }

    @KafkaListener(topicPattern = "byte.*")
    public void byteMsg(ConsumerRecord<?, ?> record) {
        System.out.println("byte====================================");
        System.out.println(record);
    }


}
