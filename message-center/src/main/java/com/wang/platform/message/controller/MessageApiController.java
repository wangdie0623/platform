package com.wang.platform.message.controller;

import com.wang.platform.beans.ResultInfo;
import com.wang.platform.message.config.TopicsConfig;
import com.wang.platform.message.controller.params.TopicMsg;
import com.wang.platform.message.service.IKafkaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("msg-c")
public class MessageApiController {
    @Autowired
    private IKafkaService kafkaService;

    @PostMapping("msg")
    public ResultInfo sendMsg(@RequestBody TopicMsg params) {
        if (params.invalid()) {
            return ResultInfo.fail("topic,data不能为空");
        }
        if (!TopicsConfig.TOPICS.contains(params.getTopic())) {
            return ResultInfo.fail(String.format("主题:%s不存在", params.getTopic()));
        }
        Object data = params.getData();
        if (data instanceof String) {
            kafkaService.sendMsg(params.getTopic(), data.toString());
        } else {
            kafkaService.sendMsg(params.getTopic(), data);
        }
        return ResultInfo.ok("发布新消息成功");
    }
}
