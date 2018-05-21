package com.wang.platform.crawler;

import org.apache.http.Header;
import org.apache.http.client.CookieStore;
import org.apache.http.entity.mime.FormBodyPart;

import java.util.List;
import java.util.Map;

public interface IHttpHelper {
    /**
     * 修改全局请求配置
     *
     * @param host             代理地址
     * @param port             代理端口
     * @param applyPoolTimeout 连接池申请超时时间
     * @param timeout          请求连接超时时间
     * @param readTimeout      请求响应读取超时时间
     */
    void setGlobalConfig(String host, Integer port, Integer applyPoolTimeout, Integer timeout, Integer readTimeout);

    /**
     * 修改单次请求配置
     *
     * @param host
     * @param port
     * @param applyPoolTimeout
     * @param timeout
     * @param readTimeout
     */
    void setConfig(String host, Integer port, Integer applyPoolTimeout, Integer timeout, Integer readTimeout);

    /**
     * 初始化为默认配置
     */
    void initDefault();

    /**
     * 添加全局请求头
     *
     * @param key
     * @param value
     * @return
     */
    IHttpHelper addGlobalHeader(String key, String value);

    /**
     * 删除全局请求头
     *
     * @param key
     * @return
     */
    IHttpHelper removeGlobalHeader(String key);

    /**
     * 添加单次请求头
     *
     * @param key
     * @param value
     */
    IHttpHelper addHeader(String key, String value);

    /**
     * 删除单次请求头
     *
     * @param key
     */
    IHttpHelper removeHeader(String key);

    /**
     * get 请求
     *
     * @param url
     * @param params
     * @return
     */
    IHttpHelper doGet(String url, String params);

    IHttpHelper doGet(String url);
    /**
     * json请求
     *
     * @param url
     * @param params
     * @return
     */
    IHttpHelper doPostJSON(String url, String params);

    /**
     * 文件相关请求
     *
     * @param url
     * @param files
     * @param params
     * @return
     */
    IHttpHelper doPostAny(String url, List<FormBodyPart> files, String params);

    /**
     * 普通post请求
     *
     * @param url
     * @param params
     * @return
     */
    IHttpHelper doPostForm(String url, String params);

    /**
     * 获取字节数组形式响应内容
     *
     * @return
     */
    byte[] getRespData();

    /**
     * 获取指定编码字符串形式响应内容
     * 注：若响应内容包含编码 以响应内容为准
     *
     * @param charset
     * @return
     */
    String respStr(String charset);

    /**
     * 获取UTF-8编码字符串形式响应内容
     *
     * @return
     */
    String respStr();

    /**
     * 获取map形式响应内容
     *
     * @return
     */
    Map respMap();

    /**
     * 获取list形式响应内容
     *
     * @return
     */
    List respList();

    /**
     * 获取指定class对象形式响应内容
     *
     * @param tClass
     * @param <T>
     * @return
     */
    <T> T respObj(Class<T> tClass);

    /**
     * 获取cookie
     *
     * @return
     */
    CookieStore getCookieStore();

    /**
     * 获取响应类型
     *
     * @return
     */
    String getRespContentType();

    /**
     * 获取响应字符类型
     *
     * @return
     */
    String getRespCharSet();

    /**
     * 获取响应主体大小
     *
     * @return
     */
    Long getRespContentLength();

    /**
     * 获取响应头
     *
     * @return
     */
    Header[] getRespHeaders();
}
