package com.ztesoft.servlet;
//package com.ztesoft.istore;
//
//import java.io.IOException;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import com.ztesoft.net.framework.util.FileBaseUtil;
//import commons.CommonTools;
///**
// * 界面环节处理器入口
// * @author wu.i
// *
// */
//public class ITraceServlet extends HttpServlet {
//	
//	protected void doGet(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
//		doPost(req, resp);
//	}
//
//	protected void doPost(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
//		String jsonStr ="";
//		try {
//			init_context(req, resp);
//			jsonStr=exec_context(req,resp);
//		} catch (Exception e) {
//			jsonStr =destory_context(req,resp);
//			e.printStackTrace();
//		}finally{
//			resp.getWriter().write(jsonStr);
//		}
//	}
//	
//	/**
//	 * init操作
//	 * @param req
//	 * @param resp
//	 * @throws IOException
//	 */
//	public String init_context(HttpServletRequest req, HttpServletResponse resp) throws IOException{
//		resp.setContentType("text/html;charset=UTF-8");
//		resp.setHeader("Cache-Control", "no-cache");
//		ICmsLineService cmsService = CmsServiceFactory.getCmsLineService();
//		String jsonReq =FileBaseUtil.readStreamToString(req.getInputStream());
//		cmsService.getCmsContext().setJsonReq(jsonReq);
//		return jsonReq;
//		
//	}
//	/**
//	 * 内容执行
//	 * @param req
//	 * @param resp
//	 * @return
//	 * @throws Exception
//	 */
//	public String exec_context(HttpServletRequest req,HttpServletResponse resp) throws Exception{
//		
//		
//		String uri = req.getRequestURI();
//		
//		//页面执行处理器
//		UrlPraser praser = CmsUrlFactory.getInstance().getUrlParser(uri);
//		return praser.perform(req,resp,uri);
//		
//	}
//	
//	public String destory_context(HttpServletRequest req, HttpServletResponse resp){
//		String jsonStr="{\"success\":\"1\",\"error\",\""+CommonTools.getErrorStr()+"\"}";
//		return jsonStr;
//	}
//	
//	public static void main(String[] args) {
//		
//	}
//}
