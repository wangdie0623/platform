package com.wang.platform.message.service;

import com.wang.platform.message.entity.ConsumerSetting;

import java.util.List;

public interface IConsumerService {
    /**
     * 新建消费者
     *
     * @param consumer
     */
    void build(ConsumerSetting consumer);

    /**
     * 注销消费者
     *
     * @param consumer
     */
    void destroy(ConsumerSetting consumer);

    /**
     * 更改消费者状态
     *
     * @param consumer
     */
    void changeStatus(ConsumerSetting consumer);

    /**
     * 更改消费者关联资源 uri
     *
     * @param consumer
     */
    void changeUri(ConsumerSetting consumer);

    /**
     * 获取单个消费者
     *
     * @param consumer
     * @return
     */
    ConsumerSetting getConsumer(ConsumerSetting consumer);

    /**
     * 获取消费者列表
     *
     * @param consumer
     * @return
     */
    List<ConsumerSetting> getConsumers(ConsumerSetting consumer);
}
