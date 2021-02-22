/*    */ package org.springframework.web.util;
/*    */ 
/*    */

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*    */
/*    */
/*    */
/*    */
/*    */ 
/*    */ public class Log4jConfigServlet extends HttpServlet
/*    */ {
/*    */   @Override
public void init()
/*    */   {
/* 58 */     Log4jWebConfigurer.initLogging(getServletContext());
/*    */   }
/*    */ 
/*    */   @Override
public void destroy() {
/* 62 */     Log4jWebConfigurer.shutdownLogging(getServletContext());
/*    */   }
/*    */ 
/*    */   @Override
public void service(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
/*    */     throws IOException
/*    */   {
/* 72 */     getServletContext().log("Attempt to call service method on Log4jConfigServlet as [" + paramHttpServletRequest.getRequestURI() + "] was ignored");
/*    */ 
/* 75 */     paramHttpServletResponse.sendError(400);
/*    */   }
/*    */ 
/*    */   @Override
public String getServletInfo()
/*    */   {
/* 80 */     return "Log4jConfigServlet for Servlet API 2.2/2.3 (deprecated in favor of Log4jConfigListener for Servlet API 2.4)";
/*    */   }
/*    */ }

/* Location:           C:\Users\Administrator\Desktop\spring-1.2.6.jar
 * Qualified Name:     org.springframework.web.util.Log4jConfigServlet
 * JD-Core Version:    0.6.0
 */