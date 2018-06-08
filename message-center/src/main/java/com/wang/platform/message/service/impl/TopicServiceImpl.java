package com.wang.platform.message.service.impl;

import com.wang.platform.message.config.TopicsConfig;
import com.wang.platform.message.dao.TopicDao;
import com.wang.platform.message.entity.Topic;
import com.wang.platform.message.exceptions.ServiceException;
import com.wang.platform.message.service.ITopicService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class TopicServiceImpl implements ITopicService {

    @Autowired
    private TopicDao topicDao;

    @Autowired
    private TopicsConfig topicsConfig;

    @Override
    @Transactional
    public void buildTopic(Topic topic) {
        if (topic.invalid()) {
            throw new ServiceException("topic值无效");
        }
        Topic old = topicDao.selectById(topic.getName());
        if (old != null) {
            throw new ServiceException(String.format("主题%s已存在", topic.getName()));
        }
        topicDao.insert(topic);
        topicsConfig.buildNewTopic(new NewTopic(topic.getName(), topic.getNumPartitions(), topic.getReplicationFactor()));
    }

    @Override
    @Transactional
    public void destroyTopic(String name) {
        if (StringUtils.isBlank(name)) {
            throw new ServiceException("主题名称不能为空");
        }
        topicDao.del(name);
        topicsConfig.destroyTopic(name);
    }
}
