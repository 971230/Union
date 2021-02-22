package com.ztesoft.net.eop.processor;

import com.ztesoft.net.eop.processor.core.Response;
import com.ztesoft.net.eop.processor.core.freemarker.FreeMarkerPaser;
import com.ztesoft.net.eop.sdk.context.EopSetting;
import com.ztesoft.net.eop.sdk.context.SaasEopContextIniter;
import com.ztesoft.net.eop.sdk.utils.JspUtil;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class SaasDispatcherFilter implements Filter {

	protected final Logger logger = Logger.getLogger(getClass());
	
	@Override
	public void init(FilterConfig config) {
		// String widgetPackage = config.getInitParameter("widgetPackage");
		// WidgetFactory.initWidget(widgetPackage);

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		
		try {
		
			httpRequest= new SafeHttpRequestWrapper(httpRequest);//包装安全request

			

			String uri = httpRequest.getServletPath();

			if(uri.startsWith("/statics") || uri.startsWith("/publics")) { 
				chain.doFilter(httpRequest, httpResponse);
				return; 
			}
			
			if(!uri.startsWith("/install") && EopSetting.INSTALL_LOCK.toUpperCase().equals("NO")   ){
				httpResponse.sendRedirect(httpRequest.getContextPath()+"/install");
				return;
			}
			if( !uri.startsWith("/install.html") && uri.startsWith("/install")
			  && !uri.startsWith("/install/images")		
			  && EopSetting.INSTALL_LOCK.toUpperCase().equals("YES")){
				httpResponse.getWriter().write("如要重新安装，请先删除/install/install.lock文件，并重起web容器");
				return ;
			}
			SaasEopContextIniter.init(httpRequest, httpResponse);
			Processor processor = ProcessorFactory.newProcessorInstance(uri,
					httpRequest);

			if (processor == null) {
				chain.doFilter(request, response);
			} else {

				Response eopResponse = processor.process(0, httpResponse,
						httpRequest);

				InputStream in = eopResponse.getInputStream();

				if (in != null) {
					BufferedInputStream bis = new BufferedInputStream(in);// 输入缓冲流
					response.setContentType(eopResponse.getContentType()
							+ "; charset=UTF-8");
					OutputStream output = response.getOutputStream();
					BufferedOutputStream bos = new BufferedOutputStream(output);// 输出缓冲流

					byte data[] = new byte[4096];// 缓冲字节数

					int size = 0;

					if (bis != null) {
						size = bis.read(data);
						while (size != -1) {
							bos.write(data, 0, size);
							size = bis.read(data);
						}
					}
					bis.close();
					bos.flush();
					bos.close();
					// output.close();

				} else {

					chain.doFilter(request, response);
				}

			}
			FreeMarkerPaser.remove();
		} catch (RuntimeException exception) {
			this.logger.info( exception.getMessage(),exception);
			request.setAttribute("message", exception.getMessage());
			String content = JspUtil.getJspOutput("/commons/error.jsp", httpRequest, httpResponse);
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().print(content);
//			response.flushBuffer();
		}
	}

	@Override
	public void destroy() {

	}

 
}
