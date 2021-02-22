package com.ztesoft.form;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;


/**
 * @author copy wui
 * @version 1.0
 */
public class StringResponse implements Response {
	
    private String content;
    private String contentType;
	public StringResponse(){
		contentType= ContextType.HTML;
	}

	@Override
	public void finalize() throws Throwable {

	}

	@Override
	public String getContent(){
		
		return content;
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
		this.content = content;
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
	 
		try {
			InputStream  in = new   ByteArrayInputStream(this.content.getBytes("UTF-8"));
			return  in;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

}
