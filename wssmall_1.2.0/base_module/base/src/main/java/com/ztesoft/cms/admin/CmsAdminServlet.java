package com.ztesoft.cms.admin;

import com.powerise.ibss.framework.servlet.SystemInit;
import com.ztesoft.cms.page.ShowPage;
import com.ztesoft.cms.page.cache.CmsCacheUtil;
import com.ztesoft.ibss.common.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
/*
 *   
 web.xml配置
<servlet>
	<servlet-name>CmsAdminServlet</servlet-name>
	<servlet-class>com.ztesoft.cms.admin.CmsAdminServlet</servlet-class>
	<load-on-startup>10</load-on-startup>
</servlet>
<servlet-mapping>
	<servlet-name>CmsAdminServlet</servlet-name>
	<url-pattern>/cms</url-pattern>
</servlet-mapping>
<servlet-mapping>
	<servlet-name>CmsAdminServlet</servlet-name>
	<url-pattern>/cms/*</url-pattern>
</servlet-mapping> 
*/
/**
 * @author Reason.Yea
 * @version Created Oct 25, 2012
 */
public class CmsAdminServlet extends HttpServlet {
	private String m_Msg = null;
	//非法访问重定向页面
	private String redrect_page="/";
	private String not_found_page="/public/404.jsp";
	/**
	 * Constructor of the object.
	 */
	public CmsAdminServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	@Override
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(request.getRequestURI().lastIndexOf("html")>0){//静态页面拦截
			try {
				boolean flag =ShowPage.show(request, response);
				if(!flag)response.sendRedirect(not_found_page);
			} catch (Exception e) {
				response.sendRedirect(not_found_page);
			}
			return;
		}
		
		//缓存刷新
		if(!SystemInit.checkAdminIp(request)){
			response.sendRedirect(redrect_page);
		}
		
		PrintWriter pr = response.getWriter();
		//缓存刷新
		String type = request.getParameter("type");
		if(StringUtils.isNotEmpty(type)){
			CmsCacheUtil.init(type);
			m_Msg = "CMS Cache refresh success with type:"+type;
		}else{
			m_Msg = "False";
		}
		pr.println(m_Msg);
		pr.flush();
	}


	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request,response);
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	@Override
	public void init() throws ServletException {
		CmsCacheUtil.init("0");
//		ServletConfig config = this.getServletConfig();
//		String page = config.getInitParameter("redrect_page");
//		if(page!=null && page.equals("")){
//			this.redrect_page=page;
//		}
	}

}