package com.wang.platform.plugins.beans;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class LoginParam {
    private String code;//登录方式标识
    private String cn;//中文说明
    private String en;//英文说明
    private List<Field> fields;//参数

    private LoginParam() {
    }

    public static final LoginParam S_PHONE =
            new LoginParam("1201", "手机号，密码登录",
                    "phone,password login", Arrays.asList(Field.PHONE, Field.PWD));
    public static final LoginParam S_EMAIL =
            new LoginParam("1202", "邮箱，密码登录",
                    "email,password login", Arrays.asList(Field.EMAIL, Field.PWD));
    public static final LoginParam S_ACCOUNT =
            new LoginParam("1203", "账号，密码登录",
                    "account,password login", Arrays.asList(Field.ACCOUNT, Field.PWD));
    public static final LoginParam S_QQ =
            new LoginParam("1204", "qq号，密码登录",
                    "qq,password login", Arrays.asList(Field.QQ, Field.PWD));
    public static final LoginParam PHONE_CODE =
            new LoginParam("1205", "手机号，短信验证码登录",
                    "phone,phoneCode login", Arrays.asList(Field.PHONE, Field.PHONE_CODE));

    public static final LoginParam PHONE_IMG_PWD =
            new LoginParam("1301", "手机号，图片验证码,密码登录",
                    "phone,img-code,password login", Arrays.asList(Field.PHONE, Field.CODE, Field.PWD));
    public static final LoginParam EMAIL_IMG_PWD =
            new LoginParam("1302", "邮箱，图片验证码,密码登录",
                    "email,img-code,password login", Arrays.asList(Field.EMAIL, Field.CODE, Field.PWD));
    public static final LoginParam ACCOUNT_IMG_PWD =
            new LoginParam("1303", "账号，图片验证码,密码登录",
                    "account,img-code,password login", Arrays.asList(Field.ACCOUNT, Field.CODE, Field.PWD));
    public static final LoginParam QQ_IMG_PWD =
            new LoginParam("1304", "qq号，图片验证码,密码登录",
                    "qq,img-code,password login", Arrays.asList(Field.QQ, Field.CODE, Field.PWD));
}
