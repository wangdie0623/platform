package com.wang.platform.plugins.beans;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Field {
    public static final Field PHONE = new Field("phone", "text", "请输入手机号", null);

    public static final Field EMAIL = new Field("email", "text", "请输入邮箱", null);

    public static final Field QQ = new Field("qq", "text", "请输入qq号", null);

    public static final Field CODE = new Field("code", "text", "请输入图片验证码", null);

    public static final Field Q_CODE = new Field("qcode", "img", null, null);

    public static final Field PHONE_CODE = new Field("phoneCode", "text", "请输入手机短信码", null);

    public static final Field ACCOUNT = new Field("account", "text", "请输入账号", null);

    public static final Field PWD = new Field("pwd", "password", "请输入密码", null);

    public static final Field NAME = new Field("name", "text", "请输入姓名", null);

    public static final Field ID_CARD_NUM = new Field("idCardNum", "text", "请输入身份证", null);


    private String name;
    private String type;
    private String desc;
    private String defaultValue;


}
