package com.wang.platform.message.entity;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

@Getter
@Setter
public class Topic {
    private String name;//主题名
    private Integer numPartitions;//分区数
    private Short replicationFactor;//备份分区数
    private Date createTime;
    private Date updateTime;

    public boolean invalid() {
        if (StringUtils.isBlank(name) || numPartitions == null || replicationFactor == null) {
            return true;
        }
        return false;
    }
}
