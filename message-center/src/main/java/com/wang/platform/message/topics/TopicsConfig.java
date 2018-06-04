package com.wang.platform.message.topics;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TopicsConfig {

    @Bean
    public NewTopic applyLoginInfo(){
        return new NewTopic("json-applyLoginInfo",1,(short) 1);
    }
}
