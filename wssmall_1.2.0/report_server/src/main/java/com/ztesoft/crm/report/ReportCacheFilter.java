/*    */ package com.ztesoft.crm.report;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import javax.servlet.Filter;
/*    */ import javax.servlet.FilterChain;
/*    */ import javax.servlet.FilterConfig;
/*    */ import javax.servlet.ServletException;
/*    */ import javax.servlet.ServletRequest;
/*    */ import javax.servlet.ServletResponse;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ 
/*    */ public class ReportCacheFilter
/*    */   implements Filter
/*    */ {
/* 40 */   private String[] miniTypes = { ".swf", ".png", "gif", "jpg", "css", "js" };
/*    */ 
/* 68 */   private FilterConfig fc = null;
/*    */ 
/*    */   @Override
public void destroy()
/*    */   {
/*    */   }
/*    */ 
/*    */   @Override
public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
/*    */     throws IOException, ServletException
/*    */   {
/* 51 */     String seconds = this.fc.getInitParameter("maxAge");
/*    */ 
/* 53 */     String url = ((HttpServletRequest)request).getRequestURI()
/* 54 */       .toLowerCase();
/*    */ 
/* 56 */     for (int i = 0; i < this.miniTypes.length; ++i)
/*    */     {
/* 58 */       if (url.endsWith(this.miniTypes[i])) {
/* 59 */         ((HttpServletResponse)response).addHeader("Cache-Control", 
/* 60 */           "public, max-age=" + 
/* 61 */           seconds);
/*    */       }
/*    */     }
/* 64 */     chain.doFilter(request, response);
/*    */   }
/*    */ 
/*    */   @Override
public void init(FilterConfig arg0)
/*    */     throws ServletException
/*    */   {
/* 77 */     this.fc = arg0;
/*    */   }
/*    */ }

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.ReportCacheFilter
 * JD-Core Version:    0.5.3
 */