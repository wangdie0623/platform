package com.wang.platform.message.service.impl;

import com.wang.platform.message.dao.ConsumerSettingDao;
import com.wang.platform.message.entity.ConsumerSetting;
import com.wang.platform.message.exceptions.ServiceException;
import com.wang.platform.message.service.IConsumerService;
import com.wang.platform.utils.BeanCopyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ConsumerServiceImpl implements IConsumerService {

    @Autowired
    private ConsumerSettingDao settingDao;

    private ConsumerSetting getOne(ConsumerSetting e, String msg) {
        List<ConsumerSetting> list = settingDao.selectByEntity(e, null, null);
        if (list == null || list.isEmpty()) {
            return null;
        }
        if (list.size() > 1) {
            throw new ServiceException(msg + list.size());
        }
        return list.get(0);
    }

    @Override
    public void build(ConsumerSetting consumer) {
        if (consumer.invalid()) {
            throw new ServiceException("topic,appName,uri不能为空");
        }
        settingDao.insert(consumer);
    }

    @Override
    public void destroy(ConsumerSetting consumer) {
        ConsumerSetting one = getOne(consumer, "注销条件不规范:");
        if (one == null) {
            return;
        }
        settingDao.del(one.getId());
    }

    @Override
    public void edit(ConsumerSetting consumer) {
        if (consumer.getId() == null ) {
            throw new ServiceException("id不能为空");
        }
        ConsumerSetting old = settingDao.selectById(consumer.getId());
        BeanCopyUtils.copyProperties(old, consumer);
        settingDao.update(old);
    }


    @Override
    public ConsumerSetting getConsumer(ConsumerSetting consumer) {
        List<ConsumerSetting> list = settingDao.selectByEntity(consumer, null, null);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<ConsumerSetting> getConsumers(ConsumerSetting consumer) {
        return settingDao.selectByEntity(consumer, null, null);
    }

}
