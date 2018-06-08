package com.wang.platform.message.service;

import com.wang.platform.message.entity.Topic;

public interface ITopicService {
    void buildTopic(Topic topic);

    void destroyTopic(String name);
}
