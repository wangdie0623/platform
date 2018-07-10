package com.wang.platform.plugins.utils;

import com.wang.platform.crawler.HttpHelperBuilder;
import com.wang.platform.crawler.IHttpHelper;
import com.wang.platform.plugins.annotations.Site;
import com.wang.platform.plugins.enums.SiteClientEnums;
import com.wang.platform.plugins.proxy.HttpHelperProxy;
import com.wang.platform.plugins.website.IWebsitePlugin;
import com.wang.platform.utils.AnnotationsSearch;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.impl.client.CloseableHttpClient;

import java.lang.reflect.Constructor;
import java.lang.reflect.Proxy;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class WebsiteFactory {

    private static final Map<String, Class> SITE_MAP = getMap();

    /**
     * 获取站点插件映射
     *
     * @return
     */
    private static Map<String, Class> getMap() {
        try {
            List<Class> classes = AnnotationsSearch
                    .searchAnnotationClass(IWebsitePlugin.class.getPackage().getName(), Site.class);
            Map<String, Class> map = new HashMap<>();
            for (Class item : classes) {
                Site site = (Site) item.getAnnotation(Site.class);
                map.put(site.value(), item);
            }
            return map;
        } catch (Exception e) {
            log.error("加载站点映射异常", e);
            return Collections.emptyMap();
        }
    }


    /**
     * 获取站点对应对象
     *
     * @param name
     * @return
     */
    public static IWebsitePlugin getSite(String name) {
        IWebsitePlugin bean = null;
        try {
            Class clazz = SITE_MAP.get(name);
            if (clazz == null) {
                return null;
            }
            Constructor constructor = clazz.getConstructor(new Class[]{IHttpHelper.class});
            bean = (IWebsitePlugin) constructor.newInstance(getHelper(name));
        } catch (Exception e) {
            log.error("创建站点插件异常", e);
        }
        return bean;
    }

    /**
     * 获取name对应httpHelper对象
     *
     * @param name
     * @return
     */
    private static IHttpHelper getHelper(String name) {
        SiteClientEnums siteEnum = SiteClientEnums.getEnum(name);
        CloseableHttpClient client = siteEnum.getClient();
        IHttpHelper target = HttpHelperBuilder.builderDefault(client);
        HttpHelperProxy proxy = new HttpHelperProxy(target);
        IHttpHelper helper = (IHttpHelper) Proxy.newProxyInstance(
                Thread.currentThread().getContextClassLoader(),
                target.getClass().getInterfaces(),
                proxy);
        return helper;
    }


}