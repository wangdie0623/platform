package com.wang.platform.redis.model.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public interface IRedisService {

    /**
     * 在相同key下，存储value在右边添加新值
     * value为自增list
     *
     * @param key   标识
     * @param value 值
     * @param unit  单位
     * @param time  数值
     */
    void rPushList(String key, String value, Long time, TimeUnit unit);

    /**
     * 默认单位 minutes 分钟
     *
     * @param key
     * @param value
     * @param time
     */
    void rPushList(String key, String value, Long time);

    /**
     * 默认保留时间 10分钟
     *
     * @param key
     * @param value
     */
    void rPushList(String key, String value);


    /**
     * 在相同key下，存储value在右边添加新值
     * value为自增list
     *
     * @param key   标识
     * @param value 值
     * @param unit  单位
     * @param time  数值
     */
    void lPushList(String key, String value, Long time, TimeUnit unit);

    /**
     * 默认单位 minutes 分钟
     *
     * @param key
     * @param value
     * @param time
     */
    void lPushList(String key, String value, Long time);

    /**
     * 默认保留时间 10分钟
     *
     * @param key
     * @param value
     */
    void lPushList(String key, String value);

    /**
     * 新增或替换key:value
     *
     * @param key   标识
     * @param value 值
     * @param unit  单位
     * @param time  数值
     */
    void pushObj(String key, String value, Long time, TimeUnit unit);

    /**
     * 默认单位 minutes 分钟
     *
     * @param key
     * @param value
     * @param time
     */
    void pushObj(String key, String value, Long time);

    /**
     * 默认保留时间 10分钟
     *
     * @param key
     * @param value
     */
    void pushObj(String key, String value);

    /**
     * list中从左边开始拿一条数据
     *
     * @param key
     */
    String getListTop(String key);

    /**
     * list中从右边拿一条数据
     *
     * @param key
     */
    String getListTail(String key);

    /**
     * 获取完整值
     *
     * @param key
     * @return
     */
    String getFullValue(String key);

    /**
     * 根据key删除 数据
     * 包含*为模糊匹配 不包含为全匹配
     *
     * @param key
     */
    void del(String key);

    void delObj(String key);

    void delList(String key);

    /**
     * 查询所有符合条件key
     *
     * @return
     */
    List<Map.Entry<String, String>> keys(String queryStr);
}
