package com.wang.platform.beans;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Getter
@Slf4j
@Setter
public class LoginInfo {
    private String token;
    private List<Field> fields;

    private static LoginInfo simple(String token, Field primaryField) {
        LoginInfo info = new LoginInfo();
        info.token = token;
        List<Field> list = new ArrayList<>();
        info.fields = list;
        list.add(primaryField);
        list.add(Field.password());
        return info;
    }

    public static LoginInfo simplePhone(String token) {
        return simple(token, Field.phone());
    }

    public static LoginInfo simpleEmail(String token) {
        return simple(token, Field.email());
    }

    public static LoginInfo simpleAccount(String token) {
        return simple(token, Field.account());
    }

    public static LoginInfo simpleQQ(String token) {
        return simple(token, Field.qq());
    }

}
