package com.ztesoft.form;

import com.ztesoft.form.util.StringUtil;

import java.io.InputStream;



/**
 * @author copy wui
 * @version 1.0
 */
public class InputStreamResponse implements Response {
	
	private String contentType;
	private InputStream inputStream;
	public InputStreamResponse(InputStream in){
		this.inputStream = in;
	}



	@Override
	public String getContent(){
		if(inputStream==null)
			return "";
		else
			return StringUtil.inputStream2String(this.inputStream);
	}

	@Override
	public String getStatusCode(){
		return "";
	}

	@Override
	public String getContentType(){
		return this.contentType;
	}

	/**
	 * 
	 * @param content
	 */
	@Override
	public void setContent(String content){

	}

	/**
	 * 
	 * @param code
	 */
	@Override
	public void setStatusCode(String code){

	}

	/**
	 * 
	 * @param contentType
	 */
	@Override
	public void setContentType(String contentType){
		this.contentType = contentType;
	}

	
	@Override
	public InputStream getInputStream() {
		
		return this.inputStream;
	}

}
