package com.wang.platform.message.controller;


import com.wang.platform.beans.ResultInfo;
import com.wang.platform.message.entity.ConsumerSetting;
import com.wang.platform.message.service.IConsumerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("msg-c/consumers")
public class MessageCenterApiController {
    @Autowired
    private IConsumerService consumerService;

    @GetMapping
    public ResultInfo consumers(ConsumerSetting consumer) {
        return ResultInfo.ok("消费者集合", consumerService.getConsumers(consumer));
    }

    @PostMapping
    public ResultInfo buildConsumer(@RequestBody ConsumerSetting consumer) {
        if (consumer.invalid()) {
            return ResultInfo.fail("topic,appName,uri不能为空");
        }
        throw new RuntimeException("暂未实现");
    }
}
