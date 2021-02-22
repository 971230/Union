package com.ztesoft.net.eop.sdk.utils;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public abstract class JspUtil {
	public static String getJspOutput(String jsppath, HttpServletRequest request,
			HttpServletResponse response)  {
		WrapperResponse wrapperResponse = new WrapperResponse(response);
		try {
			//logger.info("1-1:"+request);
			response.setContentType("text/html;charset=UTF-8");
			request.getRequestDispatcher(jsppath).include(request, wrapperResponse);
			
		} catch (ServletException e) {
		 
			e.printStackTrace();
		} catch (IOException e) {
			 
			e.printStackTrace();
		}
		return wrapperResponse.getContent();
	}
	
	public static String getJspOutput1(String jsppath, HttpServletRequest request,
			HttpServletResponse response)  {
		WrapperResponse wrapperResponse = new WrapperResponse(response);
		try {
			response.setContentType("text/html;charset=UTF-8");
			request.getRequestDispatcher(jsppath).forward(request, wrapperResponse);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return wrapperResponse.getContent();
	}
	
	
}
