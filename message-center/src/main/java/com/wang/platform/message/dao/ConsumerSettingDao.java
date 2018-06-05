package com.wang.platform.message.dao;


import com.wang.platform.message.entity.ConsumerSetting;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface ConsumerSettingDao extends BaseDao<ConsumerSetting, Integer> {

    List<ConsumerSetting> selectByEntity(@Param("obj") ConsumerSetting e,
                                         @Param("startTime") Date startTime,@Param("endTime") Date endTime);
}
