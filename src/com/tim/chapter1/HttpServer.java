package com.tim.chapter1;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;

/**
 * by poplar created on 2020/1/11
 */
public class HttpServer {
    public static final String WEB_APP = System.getProperty("user.dir") + File.separator + "webapp";
    private final String SHUTDOWN_COMMAND = "/shutdown";
    private boolean shutdown = false;

    public static void main(String[] args) {
        HttpServer httpServer = new HttpServer();
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
                response.sendStaticResource();
                socket.close();
                shutdown = request.getUrl().equals(SHUTDOWN_COMMAND);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
