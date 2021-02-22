package com.ztesoft.form;
import java.io.InputStream;

/**
 * @author copy wui
 * @version 1.0
 */
public interface Response {

	public String getContent();
	
	public InputStream getInputStream();

	public String getStatusCode();

	public String getContentType();
	
	

	/**
	 * 
	 * @param content
	 */
	public void setContent(String content);

	/**
	 * 
	 * @param code
	 */
	public void setStatusCode(String code);

	/**
	 * 
	 * @param contentType
	 */
	public void setContentType(String contentType);

}
