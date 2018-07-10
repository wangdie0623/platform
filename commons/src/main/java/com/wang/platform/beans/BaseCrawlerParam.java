package com.wang.platform.beans;

import com.wang.platform.exceptions.ServiceException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

@Getter
@Setter
@NoArgsConstructor
public class BaseCrawlerParam {
    protected String token;
    protected String siteName;
    public BaseCrawlerParam(String token, String siteName) {
        if (StringUtils.isBlank(token) || StringUtils.isBlank(siteName)) {
            throw new ServiceException("token,siteName不能为空");
        }
        this.token = token;
        this.siteName = siteName;
    }
}
