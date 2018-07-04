package com.wang.platform.beans;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public enum Field {
    PHONE("phone", "text", null),
    EMAIL("email", "text", null),
    QQ("qq", "text", null),
    CODE("CODE", "text", null),
    Q_CODE("QCODE", "img", null),
    PHONE_CODE("phoneCode", "text", null),
    ACCOUNT("account", "text", null),
    PWD("pwd", "password", null),
    ID_CARD_NUM("idCardNum", "text", null);

    private String name;
    private String type;
    private String defaultValue;

    public static Field getEnum(String name) {
        for (Field field : values()) {
            if (field.getName().equalsIgnoreCase(name)) {
                return field;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        getEnum("email").getName();
    }
}
