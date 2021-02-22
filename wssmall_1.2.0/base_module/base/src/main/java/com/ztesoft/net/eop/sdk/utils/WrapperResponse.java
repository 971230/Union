package com.ztesoft.net.eop.sdk.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * @see http://bianbian.sunshow.net/
 * @author dannyzhu, bianbian
 * @version 1.0
 */
public class WrapperResponse extends HttpServletResponseWrapper {

    private ByteArrayOutputStream buffer = null;
    private ServletOutputStream out = null;
    private PrintWriter writer = null;

    public WrapperResponse(HttpServletResponse resp) {
        super(resp);
        buffer = new ByteArrayOutputStream(1024);//真正存储数据的流
        try {
			out = new WapperedOutputStream(buffer);
			 writer = new PrintWriter(new OutputStreamWriter(buffer,"UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
     
    }

    //重载父类获取outputstream的方法
    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return out;
    }

    //重载父类获取writer的方法
    @Override
    public PrintWriter getWriter() {
        return writer;
    }

    //重载父类获取flushBuffer的方法
    @Override
    public void flushBuffer() throws IOException {
        if (out != null) {
            out.flush();
        }
        if (writer != null) {
            writer.flush();
        }
    }

    @Override
    public void reset() {
        buffer.reset();
    }

    public String getContent() {
        try {
			flushBuffer();
			return buffer.toString("UTF-8");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//将out、writer中的数据强制输出到WapperedResponse的buffer里面，否则取不到数据
       return "";
    }

    //内部类，对ServletOutputStream进行包装
    private class WapperedOutputStream extends ServletOutputStream {
        private ByteArrayOutputStream bos = null;

        public WapperedOutputStream(ByteArrayOutputStream stream) throws IOException {
            bos = stream;
        }

        @Override
        public void write(int b) throws IOException {
            bos.write(b);
        }
    }
}
