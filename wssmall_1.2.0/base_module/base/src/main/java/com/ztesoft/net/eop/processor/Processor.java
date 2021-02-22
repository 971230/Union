package com.ztesoft.net.eop.processor;

import com.ztesoft.net.eop.processor.core.Response;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author kingapex
 * @version 1.0
 * @created 13-十月-2009 11:25:48
 */
public interface Processor {

	/**
	 * 
	 * @param model
	 * @param httpResponse
	 * @param httpRequest
	 */
	public Response process(int mode, HttpServletResponse httpResponse, HttpServletRequest httpRequest);

}