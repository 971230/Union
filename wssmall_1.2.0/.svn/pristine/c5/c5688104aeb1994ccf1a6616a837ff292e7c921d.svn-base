package com.ztesoft.net.eop.sdk.context;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ztesoft.net.app.base.core.model.MultiSite;
import com.ztesoft.net.app.base.core.service.IMultiSiteManager;
import com.ztesoft.net.eop.processor.core.freemarker.FreeMarkerPaser;
import com.ztesoft.net.eop.resource.ISiteManager;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.eop.resource.model.EopSite;
import com.ztesoft.net.eop.sdk.context.EopContext;
import com.ztesoft.net.eop.sdk.context.EopSetting;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.context.webcontext.ThreadContextHolder;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

/**
 * Eop上下文初始化
 * @author kingapex
 *
 */
public class EopContextIniter {
	
	public static void init(HttpServletRequest httpRequest,
			HttpServletResponse httpResponse){
		FreeMarkerPaser.set(new FreeMarkerPaser());
		FreeMarkerPaser fmp = FreeMarkerPaser.getInstance();
		/**
		 * 将requst response及静态资源域名加入到上下文
		 */
		HttpSession session = httpRequest.getSession();
		ThreadContextHolder.getHttpSessionContext().setSession(session);
		EopContext.setHttpRequest(httpRequest);
		
		ThreadContextHolder.setHttpRequest(httpRequest);
		ThreadContextHolder.setHttpResponse(httpResponse);
		httpRequest.setAttribute("staticserver", EopSetting.IMG_SERVER_DOMAIN);
		httpRequest.setAttribute("ext", EopSetting.EXTENSION);
		
		AdminUser adminUser = ManagerUtils.getAdminUser();
		if(adminUser !=null)
			httpRequest.setAttribute("staff_no", adminUser.getUserid());
		
		String servletPath = httpRequest.getServletPath();

		// logger.info("uri : "+ RequestUtil.getRequestUrl(httpRequest));
		if (servletPath.startsWith("/statics") || servletPath.startsWith("/publics"))
			return;

		if( servletPath.startsWith("/install") ){
			EopSite site = new EopSite();
			site.setUserid(1 + "");
			site.setId(1 + "");
			site.setThemeid(1 + "");
			EopContext context = new EopContext();
			context.setCurrentSite(site);
			EopContext.setContext(context);
		}else{
			EopContext context = new EopContext();
			EopSite site = new EopSite();
			site.setUserid(1 + "");
			site.setId(1 + "");
			site.setThemeid(1 + "");
			context.setCurrentSite(site);
			EopContext.setContext(context);
			
			ISiteManager siteManager = SpringContextHolder.getBean("siteManager");
			site = siteManager.getByDomain("localhost");		 
		     
			context.setCurrentSite(site); 
			if(site.getMulti_site()==1){ //开启多站点功能
				String domain = httpRequest.getServerName();
				IMultiSiteManager multiSiteManager =  SpringContextHolder.getBean("multiSiteManager");
				MultiSite multiSite = multiSiteManager.get(domain);
				context.setCurrentChildSite(multiSite);
			}
			
			EopContext.setContext(context);
			fmp.putData("site", site);
		}
		
		/**
		 * 设置freemarker的相关常量
		 */
		fmp.putData("ctx", httpRequest.getContextPath());
		fmp.putData("ext", EopSetting.EXTENSION);
		fmp.putData("staticserver", EopSetting.IMG_SERVER_DOMAIN);
	}
	
	
}
