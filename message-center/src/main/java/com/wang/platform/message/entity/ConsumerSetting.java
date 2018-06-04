package com.wang.platform.message.entity;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

@Getter
@Setter
public class ConsumerSetting {
    private Integer id;
    private String topic;
    private String appName;
    private String uri;
    private Integer status; // 0不可用,1 可用
    private Date createTime;
    private Date updateTime;

    public boolean invalid() {
        return StringUtils.isAnyBlank(topic, appName, uri);
    }

}
