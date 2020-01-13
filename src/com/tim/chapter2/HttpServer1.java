package com.tim.chapter2;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * by poplar created on 2020/1/11
 */
public class HttpServer1 {
    public static final String WEB_APP = System.getProperty("user.dir") + File.separator + "webapp";
    private static final String SHUTDOWN_COMMAND = "/shutdown";
    private boolean shutdown = false;

    public static void main(String[] args) {
        HttpServer1 httpServer = new HttpServer1();
        httpServer.await();
    }

    private void await() {
        ServerSocket serverSocket = null;
        int port = 8080;
        try {
            //backlog 参数是套接字上请求的最大挂起连接数,<=0则将使用特定于实现的默认值
            serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress("localhost", port));
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        while (!shutdown) {
            try {
                Socket socket = serverSocket.accept();
                InputStream inputStream = socket.getInputStream();
                OutputStream outputStream = socket.getOutputStream();
                Request request = new Request(inputStream);
                request.parse();
                Response response = new Response(outputStream);
                response.setRequest(request);
                //初步根据请求的url来判断，如果请求的是servlet资源
                if (request.getUrl().startsWith("/servlet/")) {
                    ServletProcessor1 servletProcessor1 = new ServletProcessor1();
                    servletProcessor1.processor(request,response);
                } else {
                    //静态资源
                    StaticResourceProcessor staticResourceProcessor = new StaticResourceProcessor();
                    staticResourceProcessor.processor(request,response);
                }
                response.sendStaticResource();
                socket.close();
                shutdown = request.getUrl().equals(SHUTDOWN_COMMAND);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
