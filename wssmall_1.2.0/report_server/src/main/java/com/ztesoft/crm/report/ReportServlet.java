/*     */package com.ztesoft.crm.report;

/*     */
/*     */import java.io.IOException;
import java.text.MessageFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ztesoft.crm.report.actions.Action;
import com.ztesoft.crm.report.actions.URL;
import com.ztesoft.crm.report.log.ReportLogger;

/*     */
/*     */public class ReportServlet extends HttpServlet
/*     */{
	/*     */private static final long serialVersionUID = -4885670659070872950L;

	/*     */
	/*     */@Override
	public void destroy()
	/*     */{
		/* 42 */super.destroy();
		/* 43 */ReportContext.getContext().release();
		/*     */}

	/*     */
	/*     */@Override
	public void init()
	/*     */throws ServletException
	/*     */{
		/* 53 */super.init();
		/*     */
		/* 55 */ReportContext context = ReportContext.getContext();
		/*     */
		/* 58 */context.serverConfig = getInitParameter("config");
		/* 59 */context.setAppPath(getServletContext().getRealPath(""));
		/*     */try
		/*     */{
			/* 62 */context.load(getServletContext()
			/* 63 */.getResourceAsStream(getInitParameter("config")));
			/*     */} catch (Exception e) {
			/* 65 */throw new ServletException(e);
			/*     */}
		/*     */
		/* 68 */context.initLogPropertiesConfig();
		/*     */}

	/*     */
	/*     */@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response)
	/*     */throws ServletException, IOException
	/*     */{
		URL url = new URL(
		/* 82 */request.getRequestURI().replaceFirst(
				request.getContextPath(), "").replaceFirst("/report", ""));
//		if(url.getUrl().indexOf("report-all")>-1)
//			logger.info("1111111111");
		/* 83 */Action action = null;
		/* 84 */String message = null;
		/* 85 */ReportContext.getContext().setAppContextPath(
				request.getContextPath());
		/*     */try {
			/* 87 */action = (Action) Class.forName(url.getActionName())
					.newInstance();
			/*     */} catch (Exception e) {
			/* 89 */e.printStackTrace();
			/*     */}
		/*     */
		/* 92 */if (action == null)
		/*     */{
			/* 94 */message = MessageFormat.format("请求路径[0]不对，请确认您输入的路径地址！",
			/* 95 */new String[] { url.toString() });
			/*     */}
		/*     */else
			try {
				/* 98 */action.setUrl(url.getUrl());
				/* 99 */action.setContentType(url.getContentType());
				/* 100 */action
						.execute(getServletContext(), request, response);
				/*     */}
			/*     */catch (Exception e) {
				/* 103 */ReportLogger.getLogger().error("系统出错", e);
				/* 104 */message = MessageFormat.format("系统执行请求出错,原因:{0}",
				/* 105 */new String[] { e.getMessage() });
				/*     */} finally {
				/* 107 */action.release();
				/*     */}
		/*     */
		/* 110 */if (message != null)
			/* 111 */ReportLogger.getLogger().info(message);
		/*     */}
	/*     */
}

/*
 * Location: F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name: com.ztesoft.crm.report.ReportServlet JD-Core Version: 0.5.3
 */