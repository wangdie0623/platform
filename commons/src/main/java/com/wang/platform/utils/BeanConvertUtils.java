package com.wang.platform.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;

import java.io.*;
import java.util.Map;

@Slf4j
public class BeanConvertUtils {
    /**
     * 字节转对象
     *
     * @param data
     * @return
     */
    public static Object byte2Obj(byte[] data) {
        try (ObjectInputStream objIS = new ObjectInputStream(new ByteArrayInputStream(data))) {
            return objIS.readObject();
        } catch (Exception e) {
            log.error("字节转对象异常", e);
        }
        return null;
    }

    /**
     * 字节转对象 泛型
     *
     * @param data
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T byte2Obj(byte[] data, Class<T> clazz) {
        return (T) byte2Obj(data);
    }

    /**
     * 对象转字节
     *
     * @param obj
     * @return
     */
    public static byte[] obj2Byte(Object obj) {
        try (ByteArrayOutputStream bOut = new ByteArrayOutputStream();
             ObjectOutputStream out = new ObjectOutputStream(bOut);) {
            out.writeObject(obj);
            out.flush();
            return bOut.toByteArray();
        } catch (IOException e) {
            log.error("对象转字节异常", e);
        }
        return null;
    }

    /**
     * 将对象转换成map
     *
     * @param obj
     * @return
     */
    public static Map<String, String> toMap(Object obj) {
        try {
            Map<String, String> map = BeanUtils.describe(obj);
            map.remove("class");
            map.remove("declaringClass");
            return map;
        } catch (Exception e) {
            log.error("bean转换map异常", e);
        }
        return null;
    }
}
