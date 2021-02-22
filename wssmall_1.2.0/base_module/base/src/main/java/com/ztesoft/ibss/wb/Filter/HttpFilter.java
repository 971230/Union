package com.ztesoft.ibss.wb.Filter;

import com.ztesoft.ibss.security.xss.XSSHttpRequestWrapper;
import com.ztesoft.ibss.security.xss.XSSSecurityConfig;
import com.ztesoft.ibss.security.xss.XSSSecurityManager;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
/*
  	 <filter>
		 <filter-name>HttpFilter</filter-name>
		 <filter-class>com.ztesoft.ibss.wb.Filter.HttpFilter</filter-class>
		 <init-param> 
		 	<!--错误页面-->
		  	<param-name>errorJsp</param-name> 
		  	<param-value>/public/500.jsp</param-value>
		 </init-param>
		 <init-param> 
		 	<!--跳转地址-->
		  	<param-name>newServerURL</param-name> 
		  	<param-value>http://yn.189.cn</param-value>
		 </init-param>
		 <init-param>
		  	<!--旧域名-->
		  	<param-name>oldServerURL</param-name> 
		  	<param-value>yn.ct10000.com,www.yn.ct10000.com</param-value>
		 </init-param>
	</filter>
	
	<filter-mapping>
	 <filter-name>HttpFilter</filter-name>
	 <url-pattern>/*</url-pattern>
	</filter-mapping>	
	
*/
public class HttpFilter implements Filter {

	private FilterConfig filterConfig;
	private String ErrorJsp = "";
	private List oldServerURL;
	private String newServerURL;
//	private static String adslXyRecommendURL = "/R/";
	
	private static String ERRORJSP_404 = "/public/404.jsp";
	private static String ERRORJSP_403 = "/public/403.jsp";
	private static String ERRORJSP_500 = "/public/500.jsp";
	private static String VIL_IMAGE = "/public/image.jsp";
	private static String ERRORHTM_403 = "/public/403.htm";
	private static String ERRORHTM_404 = "/public/404.htm";
	private static String ERRORHTM_500 = "/public/500.htm";
	public static Logger logger = Logger.getLogger(HttpFilter.class);

	
	@Override
	public void init(FilterConfig config) throws ServletException {
		this.filterConfig = config;
		this.ErrorJsp = config.getInitParameter("errorJsp"); // 需要重定向的jsp
		this.newServerURL = config.getInitParameter("newServerURL");//新服务器域名
		this.oldServerURL = getOldServerURL(config.getInitParameter("oldServerURL"));
		XSSSecurityManager.init(filterConfig);
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		String reqUrl = req.getRequestURI();
		String serverName =request.getServerName();
		if (reqUrl!=null && (reqUrl.indexOf("/shop/admin/printTmpl!saveAdd.do")!=-1 || reqUrl.indexOf("/shop/admin/printTmpl!saveEdit.do")!=-1 || reqUrl.indexOf("/shop/admin/rule!saveConfigRule.do")!=-1)) {
			chain.doFilter(request, response);
			return;
		}
		for (Iterator iterator = oldServerURL.iterator(); iterator.hasNext();) {
			String serverUrl = (String) iterator.next();
			if(serverName.equals(serverUrl)){
				((HttpServletResponse)response).sendRedirect(newServerURL+reqUrl);
				return;
			}
		}
		if (doNoFilter(request, response, chain)) {
			chain.doFilter(request, response);
			return;
		}
		try {
			
			String strQueryString = req.getQueryString();
			Map params = req.getParameterMap() ;
			if((strQueryString != null && strQueryString.trim().length() > 0 && !"null".equals(strQueryString)) || 
					(params != null && !params.isEmpty())){
				 // http信息封装类
				XSSHttpRequestWrapper xssRequest = new XSSHttpRequestWrapper(req);
		        
		        // 对request信息进行封装并进行校验工作，若校验失败（含非法字符），根据配置信息进行日志记录和请求中断处理
		        if(xssRequest.validateParameter(res)){
//		        	if(XSSSecurityConfig.IS_LOG){
//		        		// 记录攻击访问日志
//		        		// 可使用数据库、日志、文件等方式
//		        	}
		        	if(XSSSecurityConfig.IS_CHAIN){
		        		res.sendRedirect(this.ErrorJsp);
		        		return;
		    		}
		        }
			}

			chain.doFilter(request, response);
			// logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>过滤器访问结束...");
		} catch (IOException ioe) {
			ioe.printStackTrace();

		} catch (ServletException se) {
			se.printStackTrace();

		}
	}

	@Override
	public void destroy() {
		this.filterConfig = null;
		XSSSecurityManager.destroy();
	}

	public String codeToString(String str) {
		String strString = str;
		try {
			byte tempB[] = strString.getBytes("ISO-8859-1");
			strString = new String(tempB);
			return strString;
		} catch (Exception e) {
			return strString;
		}
	}

	/**
	 * 处理不需要通过过滤器的页面
	 * 
	 * @param request
	 * @param response
	 * @param chain
	 */
	private boolean doNoFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
		// if(true)return true;
		HttpServletRequest hreq = (HttpServletRequest) request;
		String targetURL = hreq.getRequestURI();

		if ((targetURL.endsWith(".gif")) || (targetURL.endsWith(".jpg")) || (targetURL.endsWith(".png"))) {
			//logger.debug("####No Filter url #" + targetURL);
			return true;
		}

		if (ERRORJSP_404.equalsIgnoreCase(targetURL) || ERRORJSP_500.equalsIgnoreCase(targetURL) || VIL_IMAGE.equalsIgnoreCase(targetURL) || ERRORHTM_404.equalsIgnoreCase(targetURL) || ERRORHTM_500.equalsIgnoreCase(targetURL) || ERRORJSP_403.equalsIgnoreCase(targetURL) || ERRORHTM_403.equalsIgnoreCase(targetURL)) {
			//logger.debug("####NO Filter url #" + targetURL);
			return true;

		}
		logger.debug("####Do Filter url #" + targetURL);
		return false;

	}

	private List getOldServerURL(String oldUrl) {
		List ret = new ArrayList();
		if(oldUrl==null || oldUrl.equals(""))return ret;
		String urls[] = oldUrl.split(",");
		if(urls.length>0)ret=Arrays.asList(urls);
		return ret;
	}
}
