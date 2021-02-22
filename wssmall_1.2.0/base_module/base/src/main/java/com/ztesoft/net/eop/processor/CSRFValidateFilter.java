package com.ztesoft.net.eop.processor;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

public class CSRFValidateFilter implements Filter {
	private final static String CSRF_TOKEN_ATTR_NAME ="CSRFToken";
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest request =(HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)resp;
		String token = request.getParameter(CSRF_TOKEN_ATTR_NAME);
		String session_token = (String)request.getSession().getAttribute(CSRF_TOKEN_ATTR_NAME);
		if(StringUtils.isNotBlank(token)&& StringUtils.isNotBlank(session_token) && session_token.equals(token)){
			chain.doFilter(request, resp);
		}else{
			response.setContentType("text/html; charset=UTF-8");
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			req.getRequestDispatcher("/error/404.html").forward(req,resp);
		}
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
