package com.wang.platform.redis.model.service.impl;

import com.wang.platform.exceptions.ServiceException;
import com.wang.platform.redis.model.service.IRedisService;
import com.wang.platform.utils.RedisKeyUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RedisServiceImpl implements IRedisService {

    @Autowired
    private RedisTemplate<String, String> template;

    @Override
    public void rPushList(String key, String value, Long time, TimeUnit unit) {
        saveVerify(key);
        template.opsForList().rightPush(RedisKeyUtils.listKey(key), value);
        template.expire(key, time, unit);
    }

    @Override
    public void rPushList(String key, String value, Long time) {
        rPushList(key, value, time, TimeUnit.MINUTES);
    }

    @Override
    public void rPushList(String key, String value) {
        rPushList(key, value, 10l);
    }


    @Override
    public void lPushList(String key, String value, Long time, TimeUnit unit) {
        saveVerify(key);
        template.opsForList().leftPush(RedisKeyUtils.listKey(key), value);
        template.expire(key, time, unit);
    }

    @Override
    public void lPushList(String key, String value, Long time) {
        lPushList(key, value, time, TimeUnit.MINUTES);
    }

    @Override
    public void lPushList(String key, String value) {
        lPushList(key, value, 10l);
    }

    @Override
    public void pushObj(String key, String value, Long time, TimeUnit unit) {
        saveVerify(key);
        template.opsForValue().set(RedisKeyUtils.objKey(key), value, time, unit);
    }

    @Override
    public void pushObj(String key, String value, Long time) {
        pushObj(key, value, time, TimeUnit.MINUTES);
    }

    @Override
    public void pushObj(String key, String value) {
        pushObj(key, value, 10l);
    }

    @Override
    public String getListTop(String key) {
        return template.opsForList().leftPop(RedisKeyUtils.listKey(key));
    }

    @Override
    public String getListTail(String key) {
        return template.opsForList().rightPop(RedisKeyUtils.listKey(key));
    }

    @Override
    public String getFullValue(String key) {
        try {
            return template.opsForValue().get(RedisKeyUtils.objKey(key));
        } catch (Exception e) {
            log.error(String.format("%s值非字符串", key), e);
            throw new ServiceException(String.format("%s值非字符串", key));
        }
    }

    @Override
    public void del(String key) {
        delObj(key);
        delList(key);
    }

    @Override
    public void delObj(String key) {
        emptyVerify(key);
        if (key.endsWith("*")) {
            Set<String> keys = template.keys(RedisKeyUtils.objKey(key));
            template.delete(keys);
            return;
        }
        template.delete(RedisKeyUtils.objKey(key));
    }

    @Override
    public void delList(String key) {
        emptyVerify(key);
        if (key.endsWith("*")) {
            Set<String> keys = template.keys(RedisKeyUtils.listKey(key));
            template.delete(keys);
            return;
        }
        template.delete(RedisKeyUtils.listKey(key));
    }

    @Override
    public List<Map.Entry<String, String>> keys(String queryStr) {
        Set<String> keys = template.keys(StringUtils.isBlank(queryStr) ? "*" : queryStr);
        if (keys == null || keys.size() == 0) {
            return null;
        }
        List<Map.Entry<String, String>> result = new ArrayList<>();
        for (String key : keys) {
            if (key.startsWith("list:")) {
                Map.Entry<String, String> entry =
                        new AbstractMap.SimpleEntry(RedisKeyUtils.listKey(key), "List");
                result.add(entry);
                continue;
            }
            if (key.startsWith("obj:")) {
                Map.Entry<String, String> entry =
                        new AbstractMap.SimpleEntry(RedisKeyUtils.objKey(key), "Object");
                result.add(entry);
            }
        }
        return result;
    }

    /**
     * 保存key验证
     *
     * @param key
     */
    public static void saveVerify(String key) {
        emptyVerify(key);
        if (RedisKeyUtils.invalidKey(key)) {
            throw new ServiceException("list:,obj:为敏感字符");
        }
    }

    /**
     * 空验证
     *
     * @param key
     */
    public static void emptyVerify(String key) {
        if (StringUtils.isBlank(key)) {
            throw new ServiceException("key不能为空");
        }
    }
}
