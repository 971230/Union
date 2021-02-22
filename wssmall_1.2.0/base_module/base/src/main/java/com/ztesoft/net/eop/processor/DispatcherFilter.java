package com.ztesoft.net.eop.processor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.drools.core.util.StringUtils;

import utils.CoreThreadLocalHolder;

import com.ztesoft.net.eop.processor.core.Response;
import com.ztesoft.net.eop.processor.core.freemarker.FreeMarkerPaser;
import com.ztesoft.net.eop.sdk.context.EopContextIniter;
import com.ztesoft.net.eop.sdk.context.EopSetting;
import com.ztesoft.net.eop.sdk.context.SaasEopContextIniter;
import com.ztesoft.net.eop.sdk.utils.JspUtil;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

/**
 * 独立版的filter
 * 
 * @author kingapex
 * @version 1.0
 * @created 12-十月-2009 10:30:23
 */
public class DispatcherFilter implements Filter {
	Logger logger = Logger.getLogger(this.getClass());
	private Pattern safePattern = null;
	
	@Override
	public void init(FilterConfig config) {
		String contextInfo = System.getProperty(Consts.SERVLET_CONTEXT_INFO);
		if(contextInfo==null){
			/**
			 * 设置servlet容器版本信息，以后代码会用到 mochunrun 20130917
			 */
			System.setProperty(Consts.SERVLET_CONTEXT_INFO,config.getServletContext().getServerInfo());
		}
		//使用正则表达式校验是否需要过滤
        String enableURL=config.getInitParameter("enableURL");
        enableURL= enableURL.replaceAll("\\\\", "\\");
        safePattern = Pattern.compile("^"+enableURL+"$");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		CoreThreadLocalHolder.getInstance().clear(); //add by wui每次请求清楚处理
		try {
			//String myServerPort=httpRequest.getServerPort() + "";
			String myServerPort = httpRequest.getLocalPort()+"";
            String portSysProp = System.getProperty(Consts.PORT_SYS_PROP);
           // logger.info("进入过滤器");
			if (portSysProp == null) {
				logger.info("初始化端口"+myServerPort);
				System.setProperty(Consts.PORT_SYS_PROP, myServerPort);//记住端口号 用来定位定时任务服务器端口}
			}
			
			
            String portSysPropEnv = System.getProperty(Consts.PORT_SYS_PROP_ENV);
           // logger.info("进入过滤器");
			if (portSysPropEnv == null) {
				System.setProperty(Consts.PORT_SYS_PROP_ENV, myServerPort);//记住端口号 用来定位定时任务服务器端口}
				logger.info("主机端口初始化端口"+myServerPort);
			}
			
			//rop调用地址配置,无用屏蔽 xiao.ruidan
//			String remoteRopAdss =  System.getProperty(Consts.REMOTE_ROP_ADDRESS);
//			if(StringUtils.isEmpty(remoteRopAdss)){
//				CacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
//				remoteRopAdss = cacheUtil.getConfigInfo(Consts.REMOTE_ROP_ADDRESS);
//				System.setProperty(Consts.REMOTE_ROP_ADDRESS, remoteRopAdss);
//			}
			
			String uri = httpRequest.getRequestURI();
			
			//add musoon
			String sourceFrom = ManagerUtils.getSourceFrom();
			if ("LLKJ_AGENT".equals(sourceFrom) && "/".equals(uri)) {
				uri = "/mgWeb/";
			}
//			if(uri.indexOf("toolbar_back_tmpl.jsp")>-1)
//				logger.info("11111111");
			//edit Reason.Yea 2014.2.13
			
			String excel = "";
			if ((uri.indexOf("/servlet/HttpServiceHandle") == -1) && (uri.indexOf("/servlet/ZBMallOrderServlet") == -1) && (uri.indexOf("/servlet/InformationServerServlet") == -1) && (uri.indexOf("/servlet/CommonServlet") == -1)) {
				excel = httpRequest.getParameter("excel");
			}
			
            if(safePattern.matcher(uri).matches() || uri.indexOf("webservice")>=0 
            		|| uri.indexOf("/services")>=0 || uri.indexOf("/apidoc/")>=0 || uri.indexOf("/report")>=0 || !StringUtils.isEmpty(excel) || uri.indexOf("/servlet/orderGet")>=0 || uri.indexOf("router")>-1 || uri.indexOf("istoreself")>=0 
            		|| uri.indexOf("/servlet/batchAcceptExcelServlet")>=0 ||uri.indexOf("getRemoteImg.do")>=0
            		|| uri.indexOf("/servlet/importTemplateServlet")>=0||uri.indexOf("/servlet/CommonServlet")>=0
            		|| uri.startsWith("/resource/cms/") || uri.indexOf("/servlet/goodsImageUploadServlet")>=0
            		|| uri.indexOf("/ordAuto!getSmsValidCode.do")>=0){
                chain.doFilter(request,response);
                return;
            }
            
		    if(uri.equals("/mgWeb"))uri="/mgWeb/";
		    if(uri.equals("/partner"))uri="/partner/";
	        if(uri.equals("/login.do"))uri="/mgWeb/login.do";
			// add by wui was路径处理
			if (uri.indexOf(httpRequest.getContextPath()) > -1)
				uri = uri.substring(uri.indexOf(httpRequest.getContextPath())
						+ httpRequest.getContextPath().length());
			if (uri!=null && (uri.indexOf("/shop/admin/printTmpl!saveAdd.do")==-1 && uri.indexOf("/shop/admin/printTmpl!saveEdit.do")==-1 && uri.indexOf("/shop/admin/rule!saveConfigRule.do")==-1)) {
				httpRequest = this.wrapRequest(httpRequest, uri);
			}
			if ("2".equals(EopSetting.RUNMODE)) {
				SaasEopContextIniter.init(httpRequest, httpResponse);
			} else {
				EopContextIniter.init(httpRequest, httpResponse);
			}
			Processor processor = ProcessorFactory.newProcessorInstance(uri,httpRequest);
			if (processor == null) {
				chain.doFilter(request, response);
			} else {

				Response eopResponse = processor.process(0, httpResponse,
						httpRequest);
				InputStream in = eopResponse.getInputStream();
				 if (in != null) {
					 byte[] inbytes= IOUtils.toByteArray(in);
					 httpResponse.setContentType(eopResponse.getContentType());
					 httpResponse.setCharacterEncoding("UTF-8");
					 httpResponse.setHeader("Content-Length",""+inbytes.length);
					 OutputStream output = httpResponse.getOutputStream();
					 IOUtils.write(inbytes, output) ;
				 } else {
				 chain.doFilter(request, response);
				 }
				 
//				Response eopResponse = processor.process(0, httpResponse, httpRequest);
//				InputStream in = eopResponse.getInputStream();
//
//				if (in != null) {
//					httpResponse.setContentType(eopResponse.getContentType());
//					httpResponse.setCharacterEncoding("UTF-8");
//
//					OutputStream output = httpResponse.getOutputStream();
//
//					byte bufferArray[] = new byte[1024000];
//					if (in != null) {
//						int byteLength = in.read(bufferArray);
//						while (byteLength != -1) {
//							output.write(bufferArray, 0, byteLength);
//							byteLength = in.read(bufferArray);
//						}
//					}
//					httpResponse.flushBuffer();
//					in.close();
//					output.close();
//
//				} else {
//					chain.doFilter(request, response);
//				}

			}
			FreeMarkerPaser.remove();
		} catch (RuntimeException exception) {
			exception.printStackTrace();
			response.setContentType("text/html; charset=UTF-8");
			request.setAttribute("message", exception.getMessage());
			String content = JspUtil.getJspOutput("/error/404.html",httpRequest, httpResponse);
			response.getWriter().print(content);
		}
	}

	@Override
	public void destroy() {

	}

	private HttpServletRequest wrapRequest(HttpServletRequest request,
			String url) {
		List<String> safeUrlList = EopSetting.safeUrlList; //delete drop
		for (String safeUrl : safeUrlList) {
			if (safeUrl.equals(url)) {
				return request;
			}
		}
		return new SafeHttpRequestWrapper(request);// 包装安全request
	}
}