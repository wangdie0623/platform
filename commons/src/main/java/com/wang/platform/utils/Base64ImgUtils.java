package com.wang.platform.utils;

import com.wang.platform.enums.Base64ImageType;

import java.nio.charset.Charset;
import java.util.Base64;

public class Base64ImgUtils {

    /**
     * 转换图片数据为base64格式字符串（字符集UTF-8）
     *
     * @param imgData
     * @param type
     * @return
     */
    public static String getImgStr(byte[] imgData, Base64ImageType type) {
        byte[] encode = Base64.getEncoder().encode(imgData);
        String dataStr = new String(encode, Charset.forName("UTF-8"));
        return type.getValue() + dataStr;
    }

    /**
     * 转换图片字符串为原始图片数据
     *
     * @param imgStr
     * @param type
     * @return
     */
    public static byte[] getImgData(String imgStr, Base64ImageType type) {
        String dataStr = imgStr.replace(type.getValue(), "");
        byte[] bytes = dataStr.getBytes(Charset.forName("UTF-8"));
        return Base64.getDecoder().decode(bytes);
    }


    public static void main(String[] args) {

    }
}
