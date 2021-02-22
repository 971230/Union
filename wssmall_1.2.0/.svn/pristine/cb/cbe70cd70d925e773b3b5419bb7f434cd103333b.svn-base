package com.ztesoft.net.eop.processor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.ztesoft.net.eop.sdk.user.IUserService;
import com.ztesoft.net.eop.sdk.user.UserServiceFactory;
/**
 * 越权访问过滤器
 * @author ricky
 *
 */
public class ExceedAuthorityFilter implements Filter{
	private static Logger logger = Logger.getLogger(ExceedAuthorityFilter.class);
	private Pattern notSafePattern = null;
	private Pattern safePattern = null;
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest httpRequest= (HttpServletRequest)request;
		HttpServletResponse httpResponse = (HttpServletResponse)response;
		String uri = httpRequest.getRequestURI();
		if(notSafePattern.matcher(uri).matches()){
			httpResponse.setContentType("text/html; charset=UTF-8");
			httpResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
			httpRequest.getRequestDispatcher("/error/404.html").forward(httpRequest,httpResponse);
			logger.info(uri+"===非安全路径");
			return ;
		}else if(!safePattern.matcher(uri).matches()){
			//需要进行越权过滤的请求
			IUserService userService = UserServiceFactory.getUserService();
			if(!userService.isUserLoggedIn()){
				List<String> msgs = new ArrayList<String>();
			 	Map<String ,String> urls = new HashMap<String,String>();
			 	msgs.add("您尚未登录或登录已经超时，请重新登录。");
			 	String ctx = httpRequest.getContextPath();
			 	urls.put("<span style='color:green;'>点进这里进入登录页面</span>", ctx+"/mgWeb");
			 	httpRequest.setAttribute("msgs", msgs);
			 	httpRequest.setAttribute("urls", urls);
				httpRequest.setAttribute("target", "_top");
				//Request localRequest = new LocalRequest();
				httpResponse.setContentType("text/html; charset=UTF-8");
				httpResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
				httpRequest.getRequestDispatcher("/admin/message.jsp").forward(httpRequest,httpResponse);
				return ;
			} 
		}
		
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		// TODO Auto-generated method stub
		//使用正则表达式校验是否需要过滤
		String disableURL=config.getInitParameter("disableURL");
		disableURL= disableURL.replaceAll("\\u002A", ".*");//如果有配置*,则对其进行路径转换
        safePattern = Pattern.compile("^"+disableURL+"$");
		notSafePattern =Pattern.compile("^.*(/)\\1{1}.*$");
	}
}
