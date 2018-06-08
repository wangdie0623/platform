package com.wang.platform.message.controller;


import com.wang.platform.beans.ResultInfo;
import com.wang.platform.message.entity.ConsumerSetting;
import com.wang.platform.message.service.IConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("msg-c/consumers")
public class ConsumerManagerApiController {
    @Autowired
    private IConsumerService consumerService;

    @GetMapping
    public ResultInfo consumers(ConsumerSetting consumer) {
        return ResultInfo.ok("查询消费者集合成功", consumerService.getConsumers(consumer));
    }

    @PostMapping
    public ResultInfo buildConsumer(@RequestBody ConsumerSetting consumer) {
        consumerService.build(consumer);
        return ResultInfo.ok("新建消费者成功");
    }

    @PutMapping
    public ResultInfo edit(@RequestBody ConsumerSetting consumer) {
        consumerService.edit(consumer);
        return ResultInfo.ok("修改消费者成功");
    }

    @DeleteMapping
    public ResultInfo destroy(@RequestParam Integer id) {
        ConsumerSetting consumer = new ConsumerSetting();
        consumer.setId(id);
        consumerService.destroy(consumer);
        return ResultInfo.ok("注销消费者成功");
    }
}
