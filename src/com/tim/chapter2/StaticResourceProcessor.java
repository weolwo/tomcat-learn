package com.tim.chapter2;

/**
 * by poplar created on 2020/1/12
 */
public class StaticResourceProcessor {

    public void processor(Request request, Response response) throws Exception {
        response.sendStaticResource();
    }
}
