package com.ztesoft.form;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author copy wui
 * @version 1.0
 */
public class RequestWrapper implements Request {
	protected Log logger = LogFactory.getLog(getClass());
	protected Request request;


	/**
	 * 
	 * @param request
	 */
	public RequestWrapper(Request request) {
		this.request = request;
	}

	/**
	 * 
	 * @param uri
	 * @param httpResponse
	 * @param httpRequest
	 */
	@Override
	public Response execute(String uri, HttpServletResponse httpResponse,
			HttpServletRequest httpRequest) {
		
		return this.request.execute(uri, httpResponse, httpRequest);
	}

	/**
	 * 
	 * @param uri
	 */
	@Override
	public Response execute(String uri) {
		return this.request.execute(uri);
	}

	public Request getRequest(){
		return this.request;
	}

	
	@Override
	public void setExecuteParams(Map params) {
		 this.request.setExecuteParams(params);
		
	}
}
