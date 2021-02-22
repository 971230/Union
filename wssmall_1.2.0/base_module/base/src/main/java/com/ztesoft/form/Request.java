package com.ztesoft.form;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author copy wui
 * @version 1.0
 */
public interface Request {



	/**
	 * 
	 * @param uri
	 * @param httpResponse
	 * @param httpRequest
	 */
	public Response execute(String uri, HttpServletResponse httpResponse, HttpServletRequest httpRequest);

	/**
	 * 
	 * @param uri
	 */
	public Response execute(String uri);

	
	/**
	 * 
	 * @param params
	 */
	public void setExecuteParams(Map params);
	
}
