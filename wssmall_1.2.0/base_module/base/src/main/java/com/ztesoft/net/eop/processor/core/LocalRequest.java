package com.ztesoft.net.eop.processor.core;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ztesoft.net.eop.sdk.utils.JspUtil;

/**
 * @author copy wui
 * @version 1.0
 */
public class LocalRequest implements Request {

	public LocalRequest(){

	}

	@Override
	public void setExecuteParams(Map<String,String> params){
	}

	/**
	 * 
	 * @param uri
	 * @param httpResponse
	 * @param httpRequest
	 */
	@Override
	public Response execute(String uri, HttpServletResponse httpResponse, HttpServletRequest httpRequest){
		
		String content = JspUtil.getJspOutput(uri, httpRequest, httpResponse);
		//logger.info(uri+"==========================springspringspringspringspringspringspringspringspringspringspringspringspringspringspring========");
		//content= StringUtil.compressHtml(content);
		Response response = new StringResponse();
		response.setContent(content);
		return response;
	}

	/**
	 * 
	 * @param uri
	 */
	@Override
	public Response execute(String uri){
		return null;
	}

}