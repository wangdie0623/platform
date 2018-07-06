package com.wang.platform.plugins.config;

import com.wang.platform.crawler.HttpHelperBuilder;
import com.wang.platform.crawler.IHttpHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public IHttpHelper httpHelper() {
        return HttpHelperBuilder.builderDefault();
    }
}
