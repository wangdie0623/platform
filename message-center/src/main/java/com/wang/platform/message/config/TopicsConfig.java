package com.wang.platform.message.config;

import com.wang.platform.message.dao.TopicDao;
import com.wang.platform.message.entity.Topic;
import com.wang.platform.message.exceptions.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.DeleteTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.KafkaFuture;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Component
@Slf4j
public class TopicsConfig implements InitializingBean {
    public static final Set<String> TOPICS = ConcurrentHashMap.newKeySet();

    @Autowired
    private KafkaAdmin kafkaAdmin;
    @Autowired
    private TopicDao topicDao;

    private AdminClient client;

    @Override
    public void afterPropertiesSet() throws Exception {
        client = AdminClient.create(kafkaAdmin.getConfig());
        //        删除操作有坑，原因未知 建议通过kafka，zookeeper客户端管理
        //        client.deleteTopics(Arrays.asList("json-A", "json-B", "json-C", "json-D"));
        reloadTopics();
    }

    //加载数据库中主题
    private void reloadTopics() {
        List<Topic> topics = topicDao.selectAll();
        if (topics == null || topics.isEmpty()) {
            return;
        }
        List<NewTopic> list = new ArrayList<>();
        topics.forEach(it -> {
            list.add(new NewTopic(it.getName(), it.getNumPartitions(), it.getReplicationFactor()));
            TOPICS.add(it.getName());
        });
        CreateTopicsResult createResult = client.createTopics(list);
        try {
            createResult.all().get(30, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("加载数据库默认主题异常", e);
        }
    }

    //新建主题
    public void buildNewTopic(NewTopic newTopic) {
        CreateTopicsResult topics = client.createTopics(Arrays.asList(newTopic));
        try {
            topics.all().get(30, TimeUnit.SECONDS);
            TOPICS.add(newTopic.name());
        } catch (Exception e) {
            log.error("新建主题请求异常", e);
            throw new ServiceException("kafka新增主题失败");
        }
    }

    //删除主题
    public void destroyTopic(String name) {
        try {
            DeleteTopicsResult result = client.deleteTopics(Arrays.asList(name));
            result.all().get(30, TimeUnit.SECONDS);
            TOPICS.remove(name);
        } catch (Exception e) {
            log.error("删除主题异常", e);
            throw new ServiceException("kafka注销主题失败");
        }
    }
}
