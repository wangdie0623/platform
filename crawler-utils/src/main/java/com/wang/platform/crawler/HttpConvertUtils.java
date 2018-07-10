package com.wang.platform.crawler;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.FormBodyPart;
import org.apache.http.entity.mime.FormBodyPartBuilder;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.message.BasicNameValuePair;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Slf4j
public class HttpConvertUtils {

    /**
     * 将 map转换成httpParams
     *
     * @param map
     * @return
     */
    private static String mapToHttpParams(Map map) {
        if (map == null || map.size() == 0) {
            return null;
        }
        Set<Map.Entry> set = map.entrySet();
        StringBuilder builder = new StringBuilder();
        for (Map.Entry entry : set) {
            builder.append(entry.getKey() + "=" + entry.getValue() + "&");
        }
        if (builder.length() != 0) {
            builder.deleteCharAt(builder.length() - 1);
        }
        return builder.toString();
    }

    /**
     * 转换httpParams为map
     *
     * @param httpParams
     * @return
     */
    private static Map httpParamsToMap(String httpParams) {
        String[] arr = httpParams.split("&");
        if (arr == null || arr.length == 0) {
            return null;
        }
        Map<String, Object> map = new HashMap<>();
        for (String item : arr) {
            String[] itemArr = item.split("=");
            if (itemArr.length == 1) {
                map.put(itemArr[0], "");
                continue;
            }
            String val = itemArr[1];
            try {
                Object o = JSON.parse(val);
                map.put(itemArr[0], o);
                continue;
            } catch (Exception e) {
                //log.error("值非json");
            }
            map.put(itemArr[0], val);
        }
        return map;
    }

    /**
     * 将对象转换成httpParams
     *
     * @param o
     * @return
     */
    public static String toHttpParams(Object o) {
        if (isEmpty(o)) {
            return null;
        }
        try {
            if (o instanceof Map) {
                return mapToHttpParams((Map) o);
            }

            return mapToHttpParams((Map) JSON.toJSON(o));
        } catch (Exception e) {
            log.error("对象转换httpString异常", e);
        }
        return null;
    }

    /**
     * 判断对象是否为空
     *
     * @param o
     * @return
     */
    public static boolean isEmpty(Object o) {
        if (o == null || o.equals("")) {
            return true;
        }
        return false;
    }

    /**
     * 将httpParams转成json格式字符串
     *
     * @param httpParams
     * @return
     */
    public static String httpParamsToJson(String httpParams) {
        if (isEmpty(httpParams)) {
            return null;
        }
        return JSON.toJSONString(httpParamsToMap(httpParams));
    }


    /**
     * 将流转字节
     *
     * @param in
     * @return
     */
    public static byte[] streamToByte(InputStream in) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            byte[] temp = new byte[1024];
            for (int index = in.read(temp); index != -1; index = in.read(temp)) {
                out.write(temp, 0, index);
            }
            out.flush();
            return out.toByteArray();
        } catch (IOException e) {
            log.error("将流转字节异常", e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * httpParams转换为formPair
     *
     * @param params
     * @return
     */
    public static NameValuePair[] httpParamsToFormParams(String params) {
        if (isEmpty(params)) {
            return null;
        }
        Map<String, Object> map = httpParamsToMap(params);
        if (map == null || map.isEmpty()) {
            return null;
        }
        NameValuePair[] result = new NameValuePair[map.size()];
        int index = 0;
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            BasicNameValuePair pair = new BasicNameValuePair(entry.getKey(),
                    entry.getValue() == null ? "" : entry.getValue().toString());
            result[index] = pair;
            index++;
        }
        return result;
    }

    /**
     * 将httpParams转成part集合
     *
     * @param params
     * @return
     */
    public static List<FormBodyPart> getStringParts(String params) {
        if (isEmpty(params)) {
            return new ArrayList<>();
        }
        String[] arr = params.split("&");
        List<FormBodyPart> result = new ArrayList<>();
        for (String item : arr) {
            String[] itemArr = item.split("=");
            if (itemArr.length == 1) {
                FormBodyPart part = FormBodyPartBuilder.create()
                        .setName(itemArr[0])
                        .setBody(null)
                        .build();
                result.add(part);
                continue;
            }

            FormBodyPart part = FormBodyPartBuilder.create()
                    .setName(itemArr[0])
                    .setBody(new StringBody(itemArr[1], ContentType.TEXT_PLAIN))
                    .build();
            result.add(part);
        }
        return result;
    }

    /**
     * 将httpParams，文件数据集合，组装成httpEntity
     *
     * @param params
     * @param files
     * @return
     */
    public static HttpEntity convertAnyEntity(String params, List<FormBodyPart> files) {
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        List<FormBodyPart> stringParts = getStringParts(params);
        stringParts.forEach(part -> {
            builder.addPart(part);
        });
        if (files == null || files.isEmpty()) {
            return builder.build();
        }

        files.forEach(item -> {
            builder.addPart(item);
        });
        return builder.build();
    }


}
