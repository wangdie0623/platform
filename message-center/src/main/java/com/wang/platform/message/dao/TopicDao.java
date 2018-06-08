package com.wang.platform.message.dao;


import com.wang.platform.message.entity.Topic;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TopicDao extends BaseDao<Topic, String> {
}
