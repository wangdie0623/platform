package com.wang.platform.beans;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public enum Field {
    PHONE("phone", "text", "请输入手机号",null),
    EMAIL("email", "text", "请输入邮箱",null),
    QQ("qq", "text", "请输入qq号",null),
    CODE("CODE", "text", "请输入图片验证码",null),
    Q_CODE("QCODE", "img", null,null),
    PHONE_CODE("phoneCode", "text", "请输入手机短信码",null),
    ACCOUNT("account", "text", "请输入账号",null),
    PWD("pwd", "password", "请输入密码",null),
    NAME("name", "text", "请输入姓名",null),
    ID_CARD_NUM("idCardNum", "text", "请输入身份证",null);


    private String name;
    private String type;
    private String desc;
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
