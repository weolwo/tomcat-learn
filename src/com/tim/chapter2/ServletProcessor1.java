package com.tim.chapter2;

import javax.servlet.Servlet;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;

/**
 * by poplar created on 2020/1/12
 */
public class ServletProcessor1 {

    public void processor(Request request, Response response) {
        //获取url
        String url = request.getUrl();
        //获取servlet名字
        String servletName = url.substring(url.indexOf("/") + 1);

        try {
            URLClassLoader classLoader = getServletClassLoader();
            Class clazz = classLoader.loadClass(servletName);
            Servlet servlet = (Servlet) clazz.newInstance();
            servlet.service(request, response);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private URLClassLoader getServletClassLoader() throws IOException {
        try {
            URL[] urls = new URL[1];
            URLStreamHandler handler = null;
            File classPath = new File(Constants.WEB_APP);
            String repository = new URL("file", null, classPath.getCanonicalFile() + File.separator).toString();
            urls[0] = new URL(null, repository, handler);
            return new URLClassLoader(urls);
        } catch (IOException e) {
            throw new IOException("Failed to initialize servlet class loader.");
        }
    }
}
