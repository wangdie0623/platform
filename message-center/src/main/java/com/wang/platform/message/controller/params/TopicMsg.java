package com.wang.platform.message.controller.params;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

@Getter
@Setter
public class TopicMsg {
    private String topic;
    private Object data;

    public boolean invalid() {
        if (StringUtils.isBlank(topic) || data == null) {
            return true;
        }
        return false;
    }
}
