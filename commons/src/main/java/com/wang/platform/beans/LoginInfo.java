package com.wang.platform.beans;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class LoginInfo {
    private String siteName;
    private List fields;

    private static LoginInfo simple(String token, Field primaryField) {
        LoginInfo info = new LoginInfo();
        info.siteName = token;
        List<Field> list = new ArrayList<>();
        info.fields = list;
        list.add(primaryField);
        list.add(Field.PWD);
        return info;
    }

    public static LoginInfo simplePhone(String siteName) {
        return simple(siteName, Field.PHONE);
    }

    public static LoginInfo simpleEmail(String siteName) {
        return simple(siteName, Field.EMAIL);
    }

    public static LoginInfo simpleAccount(String siteName) {
        return simple(siteName, Field.ACCOUNT);
    }

    public static LoginInfo simpleQQ(String siteName) {
        return simple(siteName, Field.QQ);
    }

}
