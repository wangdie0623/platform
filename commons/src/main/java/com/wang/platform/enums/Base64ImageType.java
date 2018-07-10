package com.wang.platform.enums;

/**
 * data:image/gif;base64,base64编码的gif图片数据
 * data:image/png;base64,base64编码的png图片数据
 * data:image/jpeg;base64,base64编码的jpeg图片数据
 * data:image/x-icon;base64,base64编码的icon图片数据
 */
public enum Base64ImageType {

    GIF("data:image/gif;base64,"),
    PNG("data:image/png;base64,"),
    JPG("data:image/jpeg;base64,"),
    ICON("data:image/x-icon;base64,"),;
    private String value;

    Base64ImageType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
