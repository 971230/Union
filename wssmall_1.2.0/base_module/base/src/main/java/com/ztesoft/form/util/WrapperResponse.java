package com.ztesoft.form.util;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @see http://bianbian.sunshow.net/
 * @author copy wui
 * @version 1.0
 */
public class WrapperResponse extends HttpServletResponseWrapper {
	private MyPrintWriter tmpWriter;

	private ByteArrayOutputStream output;

	public WrapperResponse(HttpServletResponse httpServletResponse) {
		super(httpServletResponse);

		output = new ByteArrayOutputStream();
		
		
		tmpWriter = new MyPrintWriter(output);
	}

	@Override
	public void finalize() throws Throwable {
		super.finalize();
		output.close();
		tmpWriter.close();
	}

	public String getContent() {
	 
			tmpWriter.flush();
			String s="";
//			try {
				s = tmpWriter.getByteArrayOutputStream().toString();
				
//			} catch (UnsupportedEncodingException e) {
//				e.printStackTrace();
//			}
////			tmpWriter.getByteArrayOutputStream().toString(charsetName)

			return s;
		 
	}

	@Override
	public PrintWriter getWriter() throws IOException {
		return tmpWriter;
	}

	public void close() throws IOException {
		tmpWriter.close();
	}

	private static class MyPrintWriter extends PrintWriter {
		ByteArrayOutputStream myOutput;

		public MyPrintWriter(ByteArrayOutputStream output) {
			super(output);
			myOutput = output;
		}

		public ByteArrayOutputStream getByteArrayOutputStream() {
			return myOutput;
		}
	}

}
