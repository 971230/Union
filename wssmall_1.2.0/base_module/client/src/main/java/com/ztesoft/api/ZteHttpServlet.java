package com.ztesoft.api;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * <pre>
 *  http入口
 * </pre>
 */
@SuppressWarnings("serial")
public class ZteHttpServlet extends HttpServlet {
	
	public ZteHttpServlet() {}

	@Override
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		
//		request.setCharacterEncoding("UTF-8");
//		response.setContentType("text/html;charset=UTF-8");
//		ApiThreadLocalHolder.getInstance().setHttpRequest(request);
//        ApiThreadLocalHolder.getInstance().setHttpResponse(response);
//       // ApiThreadLocalHolder.getInstance().getSessionContext().setSession(request.getSession());
//
//		WebContext ctx = WebContextFactory.getInstance(WebContextFactory.HTTP_WEB);
//		
//		String rst = "";
//		try{
//			String methodName = request.getParameter("method");
//			String version = request.getParameter("version");
//			
//			//获取方法
//			DefaultServiceContext context = new DefaultServiceContext();
//			ServiceMethodHandler serviceMethodHandler = context.getServiceMethodHandler(methodName, version);
//			
//			ZteRequest<ZteResponse> zteRequest = ApiTools.jsonToBean(ApiTools.getParamStr(), serviceMethodHandler.getHandlerMethod().getParameterTypes()[0]);
//            ApiThreadLocalHolder.getInstance().getZteCommonData().setZteRequest((ZteRequest<ZteResponse>)zteRequest);
//			
//			//对象init
//			ctx.initContext(zteRequest);
//			
//			//对象执行
//			ctx.execContext(zteRequest);
//			
//			//返回对象处理
//			rst = ApiTools.beanToJson( ApiThreadLocalHolder.getInstance().getZteCommonData().getZteResponse());
//			
//			//对象销毁
//			ctx.destoryContext(zteRequest);
			
//		}catch (RuntimeException e) {
//			e.printStackTrace();
//			rst = ApiTools.getErrorStr();
//			
//		}
//		returnStr(rst, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

	/**
	 * 构造http返回对象
	 * 
	 * @param rst
	 * @param response
	 * @throws IOException
	 */
	private void returnStr(String rst, HttpServletResponse response) throws IOException {
		PrintWriter printWriter = response.getWriter();
		response.addHeader("Content-Type", "text/html;charset=UTF-8");
		response.addHeader("charset", "UTF-8");
		// response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		printWriter.write(rst);
		printWriter.flush();
		if (printWriter != null) {
			printWriter.close();
		}
	}

}