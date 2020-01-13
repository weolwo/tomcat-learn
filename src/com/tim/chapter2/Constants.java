package com.tim.chapter2;

import java.io.File;

/**
 * by poplar created on 2020/1/12
 */
public final class Constants {
    private Constants() {
        throw new AssertionError();
    }

    /**
     * web resource root path
     */
    public static final String WEB_APP = System.getProperty("user.dir") + File.separator + "webapp";

    public static final String HTTP_OK = "HTTP/1.1 200 OK\n";

    public static final String HTTP_NOT_FOUND = "HTTP/1.1 404 File Not Found\n";
}
