package com.wang.platform.beans;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class CrawlerLoginParam extends BaseCrawlerParam {

    private Map<String, Object> data=new HashMap<>();


    public CrawlerLoginParam(String token, String siteName) {
        super(token, siteName);
    }
}
