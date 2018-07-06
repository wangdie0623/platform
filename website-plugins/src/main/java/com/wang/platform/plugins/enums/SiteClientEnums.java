package com.wang.platform.plugins.enums;

import com.wang.platform.crawler.CustomCookieStore;
import com.wang.platform.crawler.HttpPoolFactory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.impl.client.CloseableHttpClient;

@Slf4j
@AllArgsConstructor
@Getter
public enum SiteClientEnums {
    BOOK_17K("book17k", HttpPoolFactory.getHttpClient(new CustomCookieStore()));
    private String name;
    private CloseableHttpClient client;

    public static SiteClientEnums getEnum(String name) {
        for (SiteClientEnums item : values()) {
            if (item.name.equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }
}
