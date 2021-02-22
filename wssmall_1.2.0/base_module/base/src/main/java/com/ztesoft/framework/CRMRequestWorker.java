package com.ztesoft.framework;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.buffalo.request.ValidationException;

import com.ztesoft.common.exception.CrmException;

public interface CRMRequestWorker {

	/**
	 * Initialize the request
	 * 
	 * @param requestContext
	 */
	public void initRequest(ServletContext context,HttpServletRequest request, HttpServletResponse response);
	
	/**
	 * validate the request
	 * 
	 * @throws ValidationException
	 */
	public void validate(HttpServletRequest request, HttpServletResponse response) throws ValidationException,CrmException;
	
	/**
	 * Process the request
	 * 
	 * @throws IOException
	 */
	public void processRequest(ServletContext context ,
			HttpServletRequest request, HttpServletResponse response) throws Throwable,CrmException;;

}
