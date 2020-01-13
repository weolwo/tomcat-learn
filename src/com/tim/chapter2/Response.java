package com.tim.chapter2;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

/**
 * by poplar created on 2020/1/11
 */
public class Response implements ServletResponse {

    private OutputStream output = null;

    private static final int BUFFER_SIZE = 1 << 10;

    private Request request = null;

    public Response(OutputStream output) {
        this.output = output;
    }

    public void sendStaticResource() throws Exception {
        byte[] bytes = new byte[BUFFER_SIZE];
        File file = new File(HttpServer1.WEB_APP, request.getUrl());
        if (file.isFile() && file.exists()) {
            try (FileInputStream fis = new FileInputStream(file)) {
                output.write("HTTP1.1 200 OK\r\n".getBytes(StandardCharsets.UTF_8));
                output.write("content-Type: text/html\r\n".getBytes(StandardCharsets.UTF_8));
                output.write("\r\n".getBytes(StandardCharsets.UTF_8));
                int ch;
                while ((ch = fis.read(bytes, 0, BUFFER_SIZE)) != -1) {
                    output.write(bytes);
                    ch = fis.read(bytes, 0, BUFFER_SIZE);
                }
            }
        } else {
            // file not found
            String errorMessage = "HTTP/1.1 404 File Not Found\r\n" +
                    "Content-Type: text/html\r\n" +
                    "Content-Length: 23\r\n" +
                    "\r\n" +
                    "<h1>File Not Found !</h1>";
            output.write(errorMessage.getBytes(StandardCharsets.UTF_8));
        }
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    @Override
    public String getCharacterEncoding() {
        return null;
    }

    @Override
    public String getContentType() {
        return null;
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return null;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        //传递 true 作为第二个参数将会使任何 println 方法的调用都会刷新输出(output)
        return new PrintWriter(output,true);
    }

    @Override
    public void setCharacterEncoding(String charset) {

    }

    @Override
    public void setContentLength(int len) {

    }

    @Override
    public void setContentType(String type) {

    }

    @Override
    public void setBufferSize(int size) {

    }

    @Override
    public int getBufferSize() {
        return 0;
    }

    @Override
    public void flushBuffer() throws IOException {

    }

    @Override
    public void resetBuffer() {

    }

    @Override
    public boolean isCommitted() {
        return false;
    }

    @Override
    public void reset() {

    }

    @Override
    public void setLocale(Locale loc) {

    }

    @Override
    public Locale getLocale() {
        return null;
    }
}
