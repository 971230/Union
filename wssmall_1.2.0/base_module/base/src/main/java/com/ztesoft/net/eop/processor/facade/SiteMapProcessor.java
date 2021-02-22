package com.ztesoft.net.eop.processor.facade;

import com.ztesoft.net.app.base.core.service.ISitemapManager;
import com.ztesoft.net.eop.processor.Processor;
import com.ztesoft.net.eop.processor.core.ContextType;
import com.ztesoft.net.eop.processor.core.Response;
import com.ztesoft.net.eop.processor.core.StringResponse;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class SiteMapProcessor implements Processor {

	@Override
	public Response process(int mode, HttpServletResponse httpResponse,
			HttpServletRequest httpRequest) {
		ISitemapManager siteMapManager = SpringContextHolder.getBean("sitemapManager"); 
		String siteMapStr = siteMapManager.getsitemap();
		Response response = new StringResponse();
		response.setContent(siteMapStr);
		response.setContentType(ContextType.XML);
		return response;
	}

}