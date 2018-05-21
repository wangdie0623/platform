package com.wang.platform.crawler;

import lombok.Getter;
import org.apache.http.entity.mime.FormBodyPart;
import org.apache.http.entity.mime.FormBodyPartBuilder;
import org.apache.http.entity.mime.content.ByteArrayBody;

@Getter
public class HttpFilePartBuilder {
    private String postKey;
    private String fileName;
    private byte[] fileData;

    private HttpFilePartBuilder() {
    }

    public static HttpFilePartBuilder create() {
        return new HttpFilePartBuilder();
    }

    public FormBodyPart builder() {
        return FormBodyPartBuilder.create()
                .setName(postKey)
                .setBody(new ByteArrayBody(fileData, fileName))
                .build();
    }

    public HttpFilePartBuilder setPostKey(String postKey) {
        this.postKey = postKey;
        return this;
    }

    public HttpFilePartBuilder setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public HttpFilePartBuilder setFileData(byte[] fileData) {
        this.fileData = fileData;
        return this;
    }
}
