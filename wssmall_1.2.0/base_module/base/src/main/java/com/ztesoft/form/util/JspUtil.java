package com.ztesoft.form.util;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public abstract class JspUtil {
	public static String getJspOutput(String jsppath, HttpServletRequest request,
			HttpServletResponse response)  {
		WrapperResponse wrapperResponse = new WrapperResponse(response);
		try {
			request.getRequestDispatcher(jsppath).include(request, wrapperResponse);
		} catch (ServletException e) {
		 
			e.printStackTrace();
		} catch (IOException e) {
			 
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return wrapperResponse.getContent();
	}
	
	public static String getJspOutput1(String jsppath, HttpServletRequest request,
			HttpServletResponse response)  {
		WrapperResponse wrapperResponse = new WrapperResponse(response);
		try {
			
			request.getRequestDispatcher(jsppath).forward(request, wrapperResponse);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return wrapperResponse.getContent();
	}
	
	
}
