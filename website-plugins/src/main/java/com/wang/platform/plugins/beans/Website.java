package com.wang.platform.plugins.beans;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class Website {
    private String name;//数据源名称
    private List<LoginParam> loginParams;//登录方式集合

    private Website() {
    }

    public static final Website BOOK17K = new Website("book17k", Arrays.asList(LoginParam.ACCOUNT_IMG_PWD));
}
