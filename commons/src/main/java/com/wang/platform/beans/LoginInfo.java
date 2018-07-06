package com.wang.platform.beans;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class LoginInfo {
    private String token;
    private List fields;

    private static LoginInfo simple(String token, Field primaryField) {
        LoginInfo info = new LoginInfo();
        info.token = token;
        List<Field> list = new ArrayList<>();
        info.fields = list;
        list.add(primaryField);
        list.add(Field.PWD);
        return info;
    }

    public static LoginInfo simplePhone(String token) {
        return simple(token, Field.PHONE);
    }

    public static LoginInfo simpleEmail(String token) {
        return simple(token, Field.EMAIL);
    }

    public static LoginInfo simpleAccount(String token) {
        return simple(token, Field.ACCOUNT);
    }

    public static LoginInfo simpleQQ(String token) {
        return simple(token, Field.QQ);
    }

}
