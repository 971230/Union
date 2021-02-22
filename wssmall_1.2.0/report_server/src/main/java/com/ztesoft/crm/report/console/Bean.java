/*    */ package com.ztesoft.crm.report.console;
/*    */ 
/*    */ import javax.servlet.ServletContext;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ 
/*    */ public abstract class Bean
/*    */ {
/*    */   private HttpServletRequest request;
/*    */   private ServletContext context;
/*    */ 
/*    */   protected final String getForward(String url)
/*    */   {
/* 18 */     return 
/* 19 */       "/console" + "/" + url;
/*    */   }
/*    */ 
/*    */   protected ServletContext getContext()
/*    */   {
/* 25 */     return this.context;
/*    */   }
/*    */ 
/*    */   void setContext(ServletContext context) {
/* 29 */     this.context = context;
/*    */   }
/*    */ 
/*    */   protected final HttpServletRequest getRequest()
/*    */   {
/* 36 */     return this.request;
/*    */   }
/*    */ 
/*    */   protected final ConsoleReply createConsoleReply(Object data) {
/* 40 */     ConsoleReply rpy = new ConsoleReply();
/* 41 */     rpy.setData(data);
/* 42 */     return rpy;
/*    */   }
/*    */ 
/*    */   final void setRequest(HttpServletRequest request)
/*    */   {
/* 50 */     this.request = request;
/*    */   }
/*    */ }

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.console.Bean
 * JD-Core Version:    0.5.3
 */