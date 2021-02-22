package com.ztesoft.net.eop.processor;

import java.io.IOException;
import java.security.SecureRandom;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

public class CSRFGenerateTokenFilter implements  Filter {
	private final SecureRandom random = new SecureRandom();
	private final static String CSRF_TOKEN_ATTR_NAME ="CSRFToken";
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chian) throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest request =(HttpServletRequest) req;
		String token = (String)request.getSession().getAttribute(CSRF_TOKEN_ATTR_NAME);
		if(StringUtils.isBlank(token)){
			token = generateToken();
			request.getSession().setAttribute(CSRF_TOKEN_ATTR_NAME, token);
		}
		request.setAttribute(CSRF_TOKEN_ATTR_NAME, token);
		chian.doFilter(req, resp);
	}
	private String generateToken(){
		final byte[] bytes = new byte[32];
		random.nextBytes(bytes);
		return Base64.encodeBase64URLSafeString(bytes);
		
	}
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
