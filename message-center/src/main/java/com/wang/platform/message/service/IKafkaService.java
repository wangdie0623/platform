package com.wang.platform.message.service;

public interface IKafkaService {
    /**
     * 发布json格式消息
     * @param title 主题
     * @param jsonMsg 消息
     */
    void sendMsg(String title, String jsonMsg);

    /**
     * 发布对象格式消息
      * @param title 主题
     * @param obj 消息
     */
    void sendMsg(String title, Object obj);
}
