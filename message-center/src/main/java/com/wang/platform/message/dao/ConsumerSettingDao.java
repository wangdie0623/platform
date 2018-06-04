package com.wang.platform.message.dao;


import com.wang.platform.message.entity.ConsumerSetting;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ConsumerSettingDao extends BaseDao<ConsumerSetting, Integer> {
}
