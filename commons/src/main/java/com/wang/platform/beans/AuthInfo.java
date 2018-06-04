package com.wang.platform.beans;

import lombok.Getter;

import java.util.Map;

@Getter
public class AuthInfo {
    private String token;
    private Map<String, String> data;

    public AuthInfo(Map<String, String> data) {
        if (!data.containsKey("token")) {
            throw new RuntimeException("token不能为空");
        }
        this.token = data.get("token");
        this.data = data;
    }
}
