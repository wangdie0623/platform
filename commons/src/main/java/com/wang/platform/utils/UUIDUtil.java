package com.wang.platform.utils;

import java.util.UUID;

/**
 * Created by amosli on 5/21/16.
 */
public class UUIDUtil {

    private UUIDUtil(){
        throw new IllegalAccessError("Utility class");
    }

    public static String generate() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
