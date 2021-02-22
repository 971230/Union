package com.ztesoft.cms.admin;

import com.ztesoft.cms.login.acions.LoginAction.KEY;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/*
<filter>
	<filter-name>CmsAdminFilter</filter-name>
	<filter-class>
		com.ztesoft.cms.admin.CmsAdminFilter
	</filter-class>
	<init-param>
		<param-name>redrect_page</param-name>
		<param-value>/</param-value>
	</init-param>
</filter>
<filter-mapping>
	<filter-name>HttpFilter</filter-name>
	<url-pattern>/admin/*</url-pattern>
</filter-mapping>
*/
/**
 * @author Reason.Yea
 * @version Created Nov 8, 2012
 */
public class CmsAdminFilter implements Filter {
	//非法访问重定向页面
	private String redrect_page="/";
	
	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;
		String req_url = request.getRequestURI();
		//校验登陆权限
		if(!checkAdminSession(request) && !checkStaticResource(req_url)){
			if(!req_url.equals("/admin/")&& !req_url.equals("/admin/index.jsp") ){
				response.sendRedirect(redrect_page);
				return;
			}
		}
		arg2.doFilter(arg0, arg1);
	}
	private static boolean checkStaticResource(String req_url) {
		if(req_url.lastIndexOf(".jsp")>0)return false;
		return true;
	}
	private boolean checkAdminSession(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Object staff = session.getAttribute(KEY.CURRENT_USER);
		if(staff!=null){
			return true;
		}
		return false;
	}
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		String page= arg0.getInitParameter("redrect_page");
		if(page!=null&& page.equals(""))redrect_page=page;
	}

}
