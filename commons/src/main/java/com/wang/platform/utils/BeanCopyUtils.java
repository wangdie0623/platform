package com.wang.platform.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaProperty;

import java.beans.PropertyDescriptor;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class BeanCopyUtils {
    private static BeanUtilsBean util = BeanUtilsBean.getInstance();

    /**
     * 拷贝orig对象非null值到dest对象
     *
     * @param dest 待拷贝对象
     * @param orig 被被拷贝
     */
    public static void copyProperties(Object dest, Object orig) {
        try {
            copyProperties(dest, orig, true);
        } catch (Exception e) {
            log.error("执行非空bean拷贝异常", e);
        }
    }

    /**
     * 对象 覆盖 orig对象值覆盖dest对象值 是否执行null过滤
     *
     * @param dest
     * @param orig
     * @param ignoreNullFlag
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public static void copyProperties(Object dest, Object orig, boolean ignoreNullFlag)
            throws IllegalAccessException, InvocationTargetException {


        // Validate existence of the specified beans
        if (dest == null) {
            throw new IllegalArgumentException
                    ("No destination bean specified");
        }
        if (orig == null) {
            throw new IllegalArgumentException("No origin bean specified");
        }
        if (log.isDebugEnabled()) {
            log.debug("BeanUtils.copyProperties(" + dest + ", " +
                    orig + "," + ignoreNullFlag + ")");
        }


        // Copy the properties, converting as necessary
        if (orig instanceof DynaBean) {
            DynaProperty[] origDescriptors =
                    ((DynaBean) orig).getDynaClass().getDynaProperties();
            for (int i = 0; i < origDescriptors.length; i++) {
                String name = origDescriptors[i].getName();
                // Need to check isReadable() for WrapDynaBean
                // (see Jira issue# BEANUTILS-61)
                if (util.getPropertyUtils().isReadable(orig, name) &&
                        util.getPropertyUtils().isWriteable(dest, name)) {
                    Object value = ((DynaBean) orig).get(name);
                    if (ignoreNullFlag) {
                        if (value != null) {
                            util.copyProperty(dest, name, value);
                        }
                    } else {
                        util.copyProperty(dest, name, value);
                    }
                }
            }
        } else if (orig instanceof Map) {
            @SuppressWarnings("unchecked")
            // Map properties are always of type <String, Object>
                    Map<String, Object> propMap = (Map<String, Object>) orig;
            for (Map.Entry<String, Object> entry : propMap.entrySet()) {
                String name = entry.getKey();
                Object value = entry.getValue();
                if (util.getPropertyUtils().isWriteable(dest, name)) {
                    if (ignoreNullFlag) {
                        if (value != null) {
                            util.copyProperty(dest, name, value);
                        }
                    } else {
                        util.copyProperty(dest, name, value);
                    }
                }
            }
        } else /* if (orig is a standard JavaBean) */ {
            PropertyDescriptor[] origDescriptors =
                    util.getPropertyUtils().getPropertyDescriptors(orig);
            for (int i = 0; i < origDescriptors.length; i++) {
                String name = origDescriptors[i].getName();
                if ("class".equals(name)) {
                    continue; // No point in trying to set an object's class
                }
                if (util.getPropertyUtils().isReadable(orig, name) &&
                        util.getPropertyUtils().isWriteable(dest, name)) {
                    try {
                        Object value =
                                util.getPropertyUtils().getSimpleProperty(orig, name);
                        if (ignoreNullFlag) {
                            if (value != null) {
                                util.copyProperty(dest, name, value);
                            }
                        } else {
                            util.copyProperty(dest, name, value);
                        }
                    } catch (NoSuchMethodException e) {
                        // Should not happen
                    }
                }
            }
        }
    }


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


}

