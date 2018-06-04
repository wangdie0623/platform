package com.wang.platform.beans;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Field {
    private String name;
    private String type;
    private String defaultValue;

    public static Field phone() {
        Field field = new Field();
        field.name = "phone";
        field.type = "text";
        return field;
    }

    public static Field password() {
        Field field = new Field();
        field.name = "password";
        field.type = "password";
        return field;
    }

    public static Field email() {
        Field field = new Field();
        field.name = "email";
        field.type = "text";
        return field;
    }

    public static Field qq() {
        Field field = new Field();
        field.name = "qq";
        field.type = "text";
        return field;
    }

    public static Field account() {
        Field field = new Field();
        field.name = "account";
        field.type = "text";
        return field;
    }
}
