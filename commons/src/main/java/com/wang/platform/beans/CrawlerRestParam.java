package com.wang.platform.beans;

import com.wang.platform.enums.CrawlerRestTypeEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CrawlerRestParam extends BaseCrawlerParam {
    private CrawlerRestTypeEnum restType;

    public CrawlerRestParam(String token, String siteName) {
        super(token, siteName);
    }
}
