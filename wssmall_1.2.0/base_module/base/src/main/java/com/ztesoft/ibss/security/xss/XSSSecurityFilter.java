package com.ztesoft.ibss.security.xss;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * @author winnie
 * @date 
 * @describe 安全信息审核类
 */
public class XSSSecurityFilter implements Filter{

	private static Logger logger = Logger.getLogger(XSSSecurityFilter.class);
	
	/**
	 * 销毁操作
	 */
	@Override
	public void destroy() {
		logger.info("XSSSecurityFilter destroy() begin");
		XSSSecurityManager.destroy();
		logger.info("XSSSecurityFilter destroy() end");
	}

	/**
	 * 安全审核
	 * 读取配置信息
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// 判断是否使用HTTP
        checkRequestResponse(request, response);
        // 转型
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        // http信息封装类
		XSSHttpRequestWrapper xssRequest = new XSSHttpRequestWrapper(httpRequest);
        
        // 对request信息进行封装并进行校验工作，若校验失败（含非法字符），根据配置信息进行日志记录和请求中断处理
        if(xssRequest.validateParameter(httpResponse)){
//        	if(XSSSecurityConfig.IS_LOG){
//        		// 记录攻击访问日志
//        		// 可使用数据库、日志、文件等方式
//        	}
        	if(XSSSecurityConfig.IS_CHAIN){
//        		httpRequest.getRequestDispatcher(XSSSecurityCon.FILTER_ERROR_PAGE).forward( httpRequest, httpResponse);
        		return;
    		}
        }
        chain.doFilter(xssRequest, response);
	}

	/**
	 * 初始化操作
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		XSSSecurityManager.init(filterConfig);
	}

	/**
     * 判断Request ,Response 类型
     * @param request
     *            ServletRequest
     * @param response
     *            ServletResponse
     * @throws ServletException 
     */
    private void checkRequestResponse(ServletRequest request,
            ServletResponse response) throws ServletException {
        if (!(request instanceof HttpServletRequest)) {
            throw new ServletException("Can only process HttpServletRequest");

        }
        if (!(response instanceof HttpServletResponse)) {
            throw new ServletException("Can only process HttpServletResponse");
        }
    }
}
