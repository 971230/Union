package com.ztesoft.cms.page;

import com.powerise.ibss.framework.FrameException;
import com.ztesoft.cms.page.cache.CmsCacheUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author Reason.Yea
 * @version Created Nov 14, 2012
 */
public class ShowPage {
	
	/**
	 * 静态页面访问格式：/cms/xx/yy.html
	 * xx表示/public/page/xx.jsp
	 * yy表示fun_id
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 * @throws FrameException 
	 */
	public static boolean show(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String reqPage = request.getRequestURI();
		String sp[] = reqPage.split("/");
		if(sp.length!=4)return false;
		String pageTemplate = "/public/page/"+sp[2]+".jsp";
		String val = sp[3].substring(0,sp[3].length()-5);
		HashMap cmsFun=CmsCacheUtil.getCacheContent(val, request.getSession());
		String req_url = (String)cmsFun.get("path");
		if(req_url==null || req_url.equals(""))return false;
		
		//设置参数
		request.setAttribute("val", val);
		request.setAttribute("kind", cmsFun.get("kind"));
		//跳转
		request.getRequestDispatcher(pageTemplate).forward(request, response);
		return true;
	}
}
