package com.wang.platform.message.controller;

import com.wang.platform.beans.ResultInfo;
import com.wang.platform.message.config.TopicsConfig;
import com.wang.platform.message.entity.Topic;
import com.wang.platform.message.service.ITopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("msg-c/topics")
public class TopicMangerController {

    @Autowired
    private ITopicService topicService;

    @GetMapping
    public ResultInfo topics() {
        return ResultInfo.ok("工作中topic", TopicsConfig.TOPICS);
    }

    @PostMapping
    public ResultInfo buildTopic(@RequestBody Topic topic) {
        topicService.buildTopic(topic);
        return ResultInfo.ok("新建主题成功");
    }

    @DeleteMapping
    public ResultInfo destroyTopic(String name) {
        topicService.destroyTopic(name);
        return ResultInfo.ok("注销主题成功");
    }
}
