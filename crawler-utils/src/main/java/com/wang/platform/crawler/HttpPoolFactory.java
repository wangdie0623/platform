package com.wang.platform.crawler;

import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import javax.net.ssl.SSLContext;
import java.security.NoSuchAlgorithmException;

public class HttpPoolFactory {
    //从链接池获取链接超时时间5秒
    private static final int GET_POOL_CLIENT_TIME = 5000;
    //链接到服务器超时时间5秒
    private static final int MAX_CONNECT_SERVER_TIME = 5000;
    //响应获取数据超时时间
    private static final int MAX_READ_TIME_OUT = 5000;
    //默认http客户端配置
    public static final RequestConfig DEFAULT_CONF = RequestConfig.custom()
            .setConnectionRequestTimeout(GET_POOL_CLIENT_TIME)
            .setConnectTimeout(MAX_CONNECT_SERVER_TIME)
            .setSocketTimeout(MAX_READ_TIME_OUT)
            .build();

    private HttpPoolFactory() {
    }

    //连接池管理对象
    private static final PoolingHttpClientConnectionManager pollManger = getPollManger();

    //获取连接池关联对象
    private static PoolingHttpClientConnectionManager getPollManger() {
        LayeredConnectionSocketFactory sslsf = null;
        PoolingHttpClientConnectionManager pollManger = null;
        try {
            sslsf = new SSLConnectionSocketFactory(SSLContext.getDefault());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("https", sslsf)
                .register("http", new PlainConnectionSocketFactory())
                .build();
        pollManger = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        pollManger.setMaxTotal(200);
        pollManger.setDefaultMaxPerRoute(20);
        return pollManger;
    }


    /**
     * 获取链接池对象
     *
     * @param cookieStore
     * @return
     */
    public static CloseableHttpClient getHttpClient(CookieStore cookieStore) {
        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(pollManger)
                .setDefaultCookieStore(cookieStore)
                .setDefaultRequestConfig(DEFAULT_CONF)
                .build();
        return httpClient;
    }

}
