package com.wang.platform.crawler;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.FormBodyPart;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.protocol.HTTP;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Setter
class SimpleHttpHelper implements IHttpHelper {

    private Map<String, String> defaultHeaders = getDefaultHeaders();
    private static final String DEFAULT_CHARSET = "UTF-8";
    private CookieStore cookieStore;
    private final Map<String, String> globalHeaders = getDefaultHeaders();
    private final Map<String, String> addHeaders = getDefaultHeaders();
    private final Set<String> removeHeaders = ConcurrentHashMap.newKeySet();
    private int state = 0; //0 待机状态 1 响应成功 2 响应失败
    private CloseableHttpClient client;//http请求池
    private String respContentType;//响应数据类型
    private String respCharSet;//响应数据字符类型
    private Long respContentLength;//响应数据大小
    private byte[] respData;//响应主体
    private Header[] respHeaders;//响应头
    private RequestConfig globalConfig = RequestConfig
            .copy(HttpPoolFactory.DEFAULT_CONF)
            .build();//全局请求参数设置 如代理，超时
    private RequestConfig config = globalConfig;//单次请求设置

    public SimpleHttpHelper(CookieStore cookieStore) {
        this.cookieStore = cookieStore;
        this.client = HttpPoolFactory.getHttpClient(cookieStore);
    }

    /**
     * 默认请求头
     *
     * @return
     */
    private static Map<String, String> getDefaultHeaders() {
        Map<String, String> map = new ConcurrentHashMap<>();
        map.put(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");
        map.put(HTTP.USER_AGENT, "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.181 Safari/537.36");
        return map;
    }

    /**
     * 清空响应数据
     */
    private void clear() {
        this.respContentType = null;
        this.respCharSet = null;
        this.respContentLength = null;
        this.respData = null;
        this.state = 0;
    }

    /**
     * 清空单次请求头
     */
    private void clearHeaders() {
        this.addHeaders.clear();
        this.removeHeaders.clear();
        this.addHeaders.putAll(globalHeaders);
    }

    /**
     * 清空单次请求配置
     */
    private void clearConfig() {
        this.config = this.globalConfig;
    }

    /**
     * 请求异常清空操作
     */
    private void errorClear() {
        clear();
        clearHeaders();
        clearConfig();
    }

    /**
     * 加载请求头信息
     *
     * @param request
     */
    private void loadHeaders(HttpUriRequest request) {
        addHeaders.entrySet().forEach(it -> {
            String key = it.getKey();
            String value = it.getValue();
            request.addHeader(key, value);
        });
        removeHeaders.forEach(it -> {
            request.removeHeaders(it);
        });
    }

    /**
     * 加载请求配置信息
     *
     * @param reqBuilder
     */
    private void loadConfig(RequestBuilder reqBuilder) {
        if (this.globalConfig != config) {
            reqBuilder.setConfig(config);
        } else {
            reqBuilder.setConfig(globalConfig);
        }
    }

    /**
     * 获取响应基本信息
     *
     * @param response
     */
    private void recordRespInfo(CloseableHttpResponse response) {
        this.respHeaders = response.getAllHeaders();
        this.respContentLength = response.getEntity().getContentLength();
        Header contentType = response.getLastHeader(HTTP.CONTENT_TYPE);
        if (contentType != null) {
            this.respContentType = contentType.getElements()[0].getName();
            NameValuePair charset = contentType.getElements()[0].getParameterByName("charset");
            this.respCharSet = charset == null ? null : charset.getValue();
        }

    }

    /**
     * 获取响应主体
     *
     * @param response
     */
    private void getRespContent(CloseableHttpResponse response) {
        clearHeaders();
        clearConfig();
        recordRespInfo(response);
        try (InputStream in = response.getEntity().getContent();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            byte[] temp = new byte[1024];
            for (int index = in.read(temp); index != -1; index = in.read(temp)) {
                out.write(temp, 0, index);
            }
            out.flush();
            respData = out.toByteArray();
        } catch (IOException e) {
            log.error("响应流转换字符串异常", e);
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 状态验证
     */
    private void verifyState() {
        switch (state) {
            case 0:
                throw new ResponseNotSuccessException("请求未执行");
            case 2:
                throw new ResponseNotSuccessException();
            default:
        }
    }

    public void setGlobalConfig(String host, Integer port, Integer applyPoolTimeout, Integer timeout, Integer readTimeout) {
        RequestConfig.Builder copy = RequestConfig
                .copy(HttpPoolFactory.DEFAULT_CONF);
        if (!StringUtils.isBlank(host)) {
            copy.setProxy(new HttpHost(host, port, "http"));
        }
        if (applyPoolTimeout != null) {
            copy.setConnectionRequestTimeout(applyPoolTimeout);
        }
        if (timeout != null) {
            copy.setConnectTimeout(timeout);
        }
        if (readTimeout != null) {
            copy.setSocketTimeout(readTimeout);
        }
        this.globalConfig = copy
                .build();
    }

    public void setConfig(String host, Integer port, Integer applyPoolTimeout, Integer timeout, Integer readTimeout) {
        RequestConfig.Builder copy = RequestConfig
                .copy(globalConfig);
        if (!StringUtils.isBlank(host)) {
            copy.setProxy(new HttpHost(host, port, "http"));
        }
        if (applyPoolTimeout != null) {
            copy.setConnectionRequestTimeout(applyPoolTimeout);
        }
        if (timeout != null) {
            copy.setConnectTimeout(timeout);
        }
        if (readTimeout != null) {
            copy.setSocketTimeout(readTimeout);
        }
        this.config = copy
                .build();
    }

    public void initDefault() {
        this.globalHeaders.clear();
        this.addHeaders.clear();
        this.globalHeaders.putAll(defaultHeaders);
        this.addHeaders.putAll(defaultHeaders);
        this.globalConfig = RequestConfig
                .copy(HttpPoolFactory.DEFAULT_CONF)
                .build();
        this.config = globalConfig;
    }

    public SimpleHttpHelper addGlobalHeader(String key, String value) {
        this.globalHeaders.put(key, value);
        this.addHeader(key, value);
        return this;
    }

    public SimpleHttpHelper removeGlobalHeader(String key) {
        globalHeaders.remove(key);
        removeHeaders.remove(key);
        return this;
    }

    public SimpleHttpHelper addHeader(String key, String value) {
        addHeaders.put(key, value);
        return this;
    }

    public SimpleHttpHelper removeHeader(String key) {
        removeHeaders.add(key);
        return this;
    }

    public SimpleHttpHelper doGet(String url, String params) {
        log.trace("get请求URL:{},Params:{}", url, params);
        clear();
        try {
            RequestBuilder requestBuilder = RequestBuilder.get();
            loadConfig(requestBuilder);
            HttpUriRequest method = requestBuilder.setUri(url + "?" + params).build();
            loadHeaders(method);
            CloseableHttpResponse response = client.execute(method);
            getRespContent(response);
            this.state = 1;
            return this;
        } catch (IOException e) {
            log.error("get请求异常", e);
            this.state = 2;
            errorClear();
        }
        return this;
    }

    @Override
    public IHttpHelper doGet(String url) {
        return doGet(url, null);
    }

    public SimpleHttpHelper doPostJSON(String url, String params) {
        try {
            clear();
            StringEntity entity = new StringEntity(HttpConvertUtils.httpParamsToJson(params), "UTF-8");
            RequestBuilder builder = RequestBuilder.post();
            loadConfig(builder);
            HttpUriRequest request = builder
                    .setUri(new URI(url))
                    .addHeader(HTTP.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString())
                    .setEntity(entity)
                    .build();
            loadHeaders(request);
            CloseableHttpResponse response = client.execute(request);
            getRespContent(response);
            state = 1;
            return this;
        } catch (Exception e) {
            log.error("提交json异常", e);
            state = 2;
            errorClear();
        }
        return this;
    }

    public SimpleHttpHelper doPostAny(String url, List<FormBodyPart> files, String params) {
        try {
            clear();
            RequestBuilder builder = RequestBuilder.post();
            loadConfig(builder);
            HttpUriRequest request = builder
                    .setUri(url)
                    .setEntity(HttpConvertUtils.convertAnyEntity(params, files))
                    .build();
            loadHeaders(request);
            CloseableHttpResponse response = client.execute(request);
            getRespContent(response);
            state = 1;
            return this;
        } catch (Exception e) {
            log.error("提交文件异常", e);
            state = 2;
            errorClear();
        }
        return this;


    }

    public SimpleHttpHelper doPostForm(String url, String params) {
        try {
            clear();
            RequestBuilder builder = RequestBuilder.post();
            loadConfig(builder);
            HttpUriRequest request = builder
                    .setUri(new URI(url))
                    .addParameters(HttpConvertUtils.httpParamsToFormParams(params))
                    .addHeader(HTTP.CONTENT_TYPE, ContentType.APPLICATION_FORM_URLENCODED.toString())
                    .build();
            loadHeaders(request);
            CloseableHttpResponse response = client.execute(request);
            getRespContent(response);
            state = 1;
            return this;
        } catch (Exception e) {
            log.error("提交表单异常", e);
            state = 2;
            errorClear();
        }
        return this;
    }

    public String respStr(String charset) {
        verifyState();
        if (StringUtils.isBlank(charset)) {
            charset = DEFAULT_CHARSET;
        }
        charset = respCharSet != null ? respCharSet : charset;
        try {
            return new String(respData, charset);
        } catch (UnsupportedEncodingException e) {
            log.error("字节数组转字符串异常", e);
        }
        return null;
    }

    public String respStr() {
        return respStr(null);
    }

    public Map respMap() {
        String content = respStr();
        return JSON.parseObject(content, Map.class, Feature.OrderedField);
    }

    public List respList() {
        String content = respStr();
        return JSON.parseObject(content, List.class, Feature.OrderedField);
    }

    public <T> T respObj(Class<T> tClass) {
        try {
            Map map = respMap();
            T obj = tClass.newInstance();
            BeanUtils.populate(obj, map);
            return obj;
        } catch (Exception e) {
            log.error("响应对象转换Obj异常", e);
        }
        return null;
    }

    public CookieStore getCookieStore() {
        return cookieStore;
    }

    public String getRespContentType() {
        return respContentType;
    }

    public String getRespCharSet() {
        return respCharSet;
    }

    public Long getRespContentLength() {
        return respContentLength;
    }

    public byte[] getRespData() {
        return respData;
    }

    public Header[] getRespHeaders() {
        return respHeaders;
    }
}
