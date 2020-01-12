package com.tim.chapter1;

import java.io.IOException;
import java.io.InputStream;

/**
 * by poplar created on 2020/1/11
 */
public class Request {

    private String url;

    private InputStream inputStream = null;

    public Request(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    //解析请求地址
    public void parse() {
        StringBuilder builder = new StringBuilder(1024);
        byte[] buffer = new byte[1024];
        int i = 0;
        try {
            i = inputStream.read(buffer);
        } catch (IOException e) {
            i = -1;
            e.printStackTrace();
        }
        for (int j = 0; j < i; j++) {
            builder.append((char) buffer[j]);
        }
        System.out.println(builder.toString());
        url = parseUrl(builder.toString());
    }

    //获取uri
    public String parseUrl(String request) {
        int index1, index2;
        index1 = request.indexOf(" ");
        if (index1 != -1) {
            index2 = request.indexOf(" ", index1 + 1);
            if (index2 > index1) {
                return request.substring(index1 + 1, index2);
            }
        }
        return null;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
