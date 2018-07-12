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
    private String token;
    private boolean success;
    private String code;
    private Object detail;

    private static ResultInfo create(String msg, String code, Object detail, boolean success) {
        ResultInfo result = new ResultInfo();
        result.setCode(code);
        result.setMsg(msg);
        result.setSuccess(success);
        result.setDetail(detail);
        return result;
    }

    public static ResultInfo fail(String msg, String code, Object detail) {
        return create(msg, code, detail, false);
    }

    public static ResultInfo fail(String msg, String code) {
        return fail(msg, code, null);
    }

    public static ResultInfo fail(String msg, Object detail) {

        return fail(msg, DEFAULT_FAIL_CODE, detail);
    }

    public static ResultInfo fail(String msg) {
        return fail(msg, DEFAULT_FAIL_CODE);
    }


    public static ResultInfo ok(String msg, String code, Object detail) {
        return create(msg, code, detail, true);
    }

    public static ResultInfo ok(String msg, String code) {
        return ok(msg, code, null);
    }

    public static ResultInfo ok(String msg, Object detail) {

        return ok(msg, DEFAULT_SUCCESS_CODE, detail);
    }

    public static ResultInfo ok(String msg) {
        return ok(msg, DEFAULT_SUCCESS_CODE);
    }

    public static ResultInfo okStr(String msg, String detail) {
        ResultInfo ok = ok(msg);
        ok.setDetail(detail);
        return ok;
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

    public static ResultInfo create(CrawlerCodeEnum codeEnum, String msg, Object detail) {
        ResultInfo info = create(codeEnum, msg);
        info.setDetail(detail);
        return info;
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
