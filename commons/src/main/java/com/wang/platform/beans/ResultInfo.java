package com.wang.platform.beans;

import com.wang.platform.enums.CrawlerCodeEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultInfo {
    private static final String DEFAULT_FAIL_CODE = "500";
    private static final String DEFAULT_SUCCESS_CODE = "200";
    private String msg;
    private boolean success;
    private String code;
    private Object detail;

    public static ResultInfo fail(String msg) {
        return fail(msg, DEFAULT_FAIL_CODE);
    }

    public static ResultInfo fail(String msg, Object detail) {
        ResultInfo fail = fail(msg);
        fail.setDetail(detail);
        return fail;
    }

    public static ResultInfo fail(String msg, String code) {
        ResultInfo result = new ResultInfo();
        result.setCode(code);
        result.setMsg(msg);
        result.setSuccess(false);
        return result;
    }

    public static ResultInfo ok(String msg) {
        return ok(msg, DEFAULT_SUCCESS_CODE);
    }

    public static ResultInfo ok(String msg, Object detail) {
        ResultInfo ok = ok(msg);
        ok.setDetail(detail);
        return ok;
    }

    public static ResultInfo ok(String msg, String code) {
        ResultInfo result = fail(msg, code);
        result.setSuccess(true);
        return result;
    }

    public static ResultInfo create(CrawlerCodeEnum codeEnum, String msg) {
        boolean flag = false;
        if (codeEnum == CrawlerCodeEnum.INFO || codeEnum.name().endsWith("SUCCESS")) {
            flag = true;
        }
        if (flag) {
            return ok(msg, codeEnum.getCode());
        }
        return fail(msg, codeEnum.getCode());
    }

    public static ResultInfo create(CrawlerCodeEnum codeEnum) {
        return create(codeEnum, null);
    }

    public static ResultInfo create(CrawlerCodeEnum codeEnum, Object detail) {
        ResultInfo result = create(codeEnum, null);
        result.setDetail(detail);
        return result;
    }
}
