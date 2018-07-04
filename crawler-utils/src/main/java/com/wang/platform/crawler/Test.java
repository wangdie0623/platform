package com.wang.platform.crawler;

public class Test {
    public static void main(String[] args) {
        IHttpHelper helper = HttpHelperBuilder.builderDefault();
        String str = helper.doGet("http://localhost:8080").respStr();
        System.out.println(str);
    }
}
