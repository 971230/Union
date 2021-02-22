package com.ztesoft.net.eop.processor.backend.support;

import com.ztesoft.net.eop.processor.AbstractFacadeProcessor;
import com.ztesoft.net.eop.processor.FacadePage;
import com.ztesoft.net.eop.processor.core.Response;
import com.ztesoft.net.eop.processor.core.StringResponse;
import com.ztesoft.net.eop.resource.IAdminThemeManager;
import com.ztesoft.net.eop.resource.model.AdminTheme;
import com.ztesoft.net.eop.resource.model.EopSite;
import com.ztesoft.net.eop.sdk.context.EopContext;
import com.ztesoft.net.eop.sdk.context.EopSetting;
import com.ztesoft.net.eop.sdk.utils.JspUtil;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.util.HttpUtil;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
/**
 * 后台页面获取器
 * @author kingapex
 * 2010-9-13下午12:13:24
 */
public class BackPageGetter extends AbstractFacadeProcessor {
	
	private IAdminThemeManager adminThemeManager;

	public BackPageGetter(FacadePage page) {
		super(page);
	}

	
	@Override
	protected Response process() {
		long time1 = System.currentTimeMillis();
		EopSite site = EopContext.getContext().getCurrentSite();//this.page.getSite();
		
		adminThemeManager = SpringContextHolder.getBean("adminThemeManager");
		
		//读取后台使用的模板
		AdminTheme theme = adminThemeManager.get( site.getAdminthemeid());
		String path ="default";
		if(theme!=null){
			path = theme.getPath();
		}
		StringBuffer context = new StringBuffer();

		// context.append(EopSetting.IMG_SERVER_DOMAIN);
		//当前用户的上下文
		//String contextPath  = EopContext.getContext().getContextPath();
//		context.append(contextPath);
		
//		WebSessionContext sessonContext = ThreadContextHolder.getSessionContext();
//		AppInfo appinfo = (AppInfo) sessonContext.getAttribute("current_app_info");	
//		if(appinfo !=null)
//			
//		String themeSourceFrom = appinfo.getThemeSourceFrom();
//			
			
		context.append(EopSetting.ADMINTHEMES_STORAGE_PATH);
		context.append("/");
		context.append(path);
		
		StringBuffer  staticdomain = new StringBuffer();
		
		/***********************20100920修改：***************************/
//		//静态资源分离模式
//		if(EopSetting.RESOURCEMODE.equals("1")){
//			staticdomain.append(EopSetting.IMG_SERVER_DOMAIN);
//		}
		//静态资源合并模式
//		if(EopSetting.RESOURCEMODE.equals("2")){
			if("/".equals(EopSetting.CONTEXT_PATH) )
				staticdomain.append("");
			else	
				staticdomain.append(EopSetting.CONTEXT_PATH);
//		}
		
		// 设置页面上变量的值
		httpRequest.setAttribute("context", staticdomain + context.toString()); //静态资源上下文		
		httpRequest.setAttribute("title",site.getSitename()); //站点名称
		httpRequest.setAttribute("ico",site.getIcofile());    //ico文件
		httpRequest.setAttribute("logo", site.getLogofile()) ; //logo文件
		//httpRequest.setAttribute("version", EopSetting.VERSION) ; //版本
		//httpRequest.setAttribute("themes",EopSetting.THEMES_STORAGE_PATH_F);
		httpRequest.setAttribute("bkloginpicfile", site.getBkloginpicfile()); //后台登录logo
		httpRequest.setAttribute("bklogo", site.getBklogofile()==null?site.getLogofile():site.getBklogofile()); //后台主界面logo
		
		String source_from = EopSetting.SOURCE_FROM;//应用来源	
		String db_source_from = EopSetting.DB_SOURCE_FROM;//数据库source_from 字段
		if(source_from.equals("")){
			source_from = Consts.WSSMALL;
		}
		httpRequest.setAttribute("source_from", source_from);
		httpRequest.setAttribute("db_source_from", db_source_from);
		
		//获取验证方式(验证码/短信)---zengxianlian
		ICacheUtil util = (ICacheUtil)SpringContextHolder.getBean("cacheUtil");
		String checkType = util.getConfigInfo("IS_VALID_CODE_OR_MESSAGE");
		httpRequest.setAttribute("checkType", checkType);
		
		String uri = page.getUri();
		if (uri.startsWith("/mgWeb/main")) { //后台首页
			
			uri = context.toString() + "/main.jsp";
//			request  = new GetPointJsWrapper(page, request); //包装积分获取js
			request = new HelpDivWrapper(page, request);
		
		} else if (uri.equals("/partner/") || uri.equals("/partner")) { //登录页面
			//读取记住的用户名
			String username  = HttpUtil.getCookieValue(httpRequest, "loginname");
			httpRequest.setAttribute("username", username);
			//uri =  context.toString() + "/login.jsp";
			uri =  context.toString() + "/local_login.jsp";
		}else if (uri.equals("/mgWeb/") || uri.equals("/mgWeb") || uri.equals("/mgWeb/index.jsp")) { //登录页面
			//如果是接口则不能访问登陆页面
			String isLogin = System.getProperty("IS_LOGIN", "Y");
			if("N".equals(isLogin)){
				httpResponse.setContentType("text/html; charset=UTF-8");
				String content = JspUtil.getJspOutput("/error/404.html",httpRequest, httpResponse);
				Response response = new StringResponse();
				response.setContent(content);
				return response;
			}else{
				//读取记住的用户名
				String username  = HttpUtil.getCookieValue(httpRequest, "loginname");
				httpRequest.setAttribute("username", username);
				httpRequest.setAttribute("desencrptKey", Consts.DESENCRYPT_KEY);
				uri =  context.toString() + "/login.jsp";
			}
			
			//uri = "login!localLogin.do";
			//uri =  context.toString() + "/local_login.jsp";
		} else if(uri.startsWith("/shop/admin/st_callback")){
			//银行回调不需要包装后台页面，不然银通无法解析 add by pzh  有问题请联系
		}else{
			
			if(EopSetting.EXTENSION.equals("action")){
				uri = uri.replace(".do", ".action");
			}
			String ajax = httpRequest.getParameter("ajax");
			String iframeall = httpRequest.getParameter("iframeall");
			String dwr = httpRequest.getParameter("dwr");
			String menuStatus = httpRequest.getParameter("noneMenu");
			if("yes".equals(iframeall)){
				request = new BackAllTemplateWrapper(page, request);
				request = new HelpDivWrapper(page, request);
			}else if("yes".equals(dwr)){ //add by wui dwr访问后台
				request = new BackDwrTemplateWrapper(page, request); 
				request = new HelpDivWrapper(page, request);
			}else if("yes".equals(menuStatus)){
				request = new BackNMenuTemplateWrapper(page, request); 
				request = new HelpDivWrapper(page, request);
			}else{
				if(!"yes".equals(ajax)){ //非异步包装后台内容界面
					request = new BackTemplateWrapper(page, request);
					request = new HelpDivWrapper(page, request);
				}
			}
		}
		long time2= System.currentTimeMillis();
		Response response = request.execute(uri, httpResponse, httpRequest);
		long time3= System.currentTimeMillis();
		return response;

	}
}



