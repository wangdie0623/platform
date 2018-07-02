package com.wang.platform.utils;

import org.apache.commons.lang3.StringUtils;

public class RedisKeyUtils {

    /**
     * listKey 相互转化
     * 非listKey 加list:头
     * listKey 去list:头
     * @param key
     * @return
     */
    public static String listKey(String key) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        if (key.startsWith("list")) {
            return key.replace("list:", "");
        }
        return layerKey("list", key);
    }

    /**
     * objKey 相互转化
     * 非objKey 加obj:头
     * objKey 去obj:头
     * @param key
     * @return
     */
    public static String objKey(String key){
        if (StringUtils.isBlank(key)) {
            return null;
        }
        if (key.startsWith("obj")) {
            return key.replace("obj:", "");
        }
        return layerKey("obj", key);
    }

    /**
     * 获取多层key 以:分隔
     * @param layers
     * @return
     */
    public static String layerKey(String... layers) {
        if (layers == null || layers.length == 0) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        for (String layer : layers) {
            builder.append(layer + ":");
        }
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }


    /**
     * 添加新key时使用
     * @param key
     * @return
     */
    public static boolean invalidKey(String key){
        if (StringUtils.isBlank(key)||key.contains("obj:")||key.contains("list:")){
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(invalidKey("wang"));
    }
}
