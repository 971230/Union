package com.ztesoft.net.outter.inf.util;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class PeripheryHttpClient {

    //    private static final String USER_AGENT_VALUE = "Mozilla/4.0 (compatible; MSIE 6.0; Windows XP)";

    //    private String reqContent;

    /** 应答内容 */
    private String resContent;

    /** 请求方法 */
    //    private String method;

    /** 超时时间,以秒为单位 */
    private int timeOut;

    /** 错误信息 */
    private String errInfo;

    /** 字符编码 */
    private String charset;

    private InputStream inputStream;

    public PeripheryHttpClient() {
        //        reqContent = "";
        resContent = "";
        //        method = "POST";
        errInfo = "";
        timeOut = 30;// 30秒
        inputStream = null;
        charset = "UTF-8";
    }

    public String doPost(String url, String param) throws IOException {
        httpPostMethod(url, param.getBytes(charset));
        return getResContent();
    }

    /**
     * 以http get方式通信
     * 
     * @param addr
     * @throws IOException
     */
    protected void httpGetMethod(String addr) throws IOException {

        URL url = new URL(addr);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

        httpURLConnection.setRequestMethod("GET");

        // 设置连接超时时间
        httpURLConnection.setConnectTimeout(timeOut * 1000);
        // User-Agent
        //httpURLConnection.setRequestProperty("User-Agent", USER_AGENT_VALUE);
        // 不使用缓存
        httpURLConnection.setUseCaches(false);
        // 允许输入输出
        httpURLConnection.setDoInput(true);
        httpURLConnection.setDoOutput(true);

        inputStream = httpURLConnection.getInputStream();

    }

    /**
     * 以http post方式通信
     * 
     * @param addr
     * @param postData
     * @throws IOException
     */
    protected void httpPostMethod(String addr, byte[] postData) throws IOException {

        URL url = new URL(addr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        // 以post方式通信
        conn.setRequestMethod("POST");

        // 设置请求默认属性
        // 设置连接超时时间
        conn.setConnectTimeout(timeOut * 1000);
        // User-Agent
        //        conn.setRequestProperty("User-Agent", USER_AGENT_VALUE);
        // 不使用缓存
        conn.setUseCaches(false);
        // 允许输入输出
        conn.setDoInput(true);
        conn.setDoOutput(true);

        // Content-Type
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        BufferedOutputStream out = new BufferedOutputStream(conn.getOutputStream());

        //        final int len = 1024; // 1KB
        //        doOutput(out, postData, len);

        out.write(postData);
        // 刷新缓冲区
        out.flush();

        // 关闭流
        out.close();

        // 获取应答输入流
        inputStream = conn.getInputStream();
    }

    /**
     * 处理输出<br/>
     * 注意:流关闭需要自行处理
     * 
     * @param out
     * @param data
     * @param len
     * @throws IOException
     */
    private static void doOutput(OutputStream out, byte[] data, int len) throws IOException {
        int dataLen = data.length;
        int off = 0;
        while (off < dataLen) {
            if (len >= dataLen) {
                out.write(data, off, dataLen);
            } else {
                out.write(data, off, len);
            }

            // 刷新缓冲区
            out.flush();

            off += len;

            dataLen -= len;
        }

    }

    /**
     * 处理应答
     * 
     * @throws IOException
     */
    protected void doResponse() throws IOException {

        if (null == inputStream) {
            return;
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, charset));

        StringBuffer buf = new StringBuffer();
        String line = null;
        while ((line = reader.readLine()) != null) {
            buf.append(line);
            buf.append("\r\n");
        }
        // 获取应答内容
        resContent = buf.toString();

        // 关闭流
        reader.close();

        // 关闭输入流
        inputStream.close();

    }

    /**
     * 设置请求内容
     * 
     * @param reqContent
     *            请求内容
     */
    //    public void setReqContent(String reqContent) {
    //        this.reqContent = reqContent;
    //    }

    /**
     * 获取结果内容
     * 
     * @return String
     * @throws IOException
     */
    public String getResContent() {
        try {
            doResponse();
        } catch (IOException e) {
            errInfo = e.getMessage();
        }

        return resContent;
    }

    /**
     * 获取错误信息
     * 
     * @return String
     */
    public String getErrInfo() {
        return errInfo;
    }

    /**
     * 设置超时时间,以秒为单位
     * 
     * @param timeOut
     *            超时时间,以秒为单位
     */
    public void setTimeOut(int timeOut) {
        this.timeOut = timeOut;
    }

}