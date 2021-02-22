/*    */ package com.ztesoft.crm.report.console;
/*    */ 
/*    */ import com.ztesoft.crm.report.ReportContext;
/*    */ import com.ztesoft.crm.report.actions.Action;
/*    */ import com.ztesoft.crm.report.actions.ParameterMap;
/*    */ import com.ztesoft.crm.report.console.vo.User;
/*    */ import com.ztesoft.crm.report.generator.html.elements.HTMLConsoleDocument;
/*    */ import com.ztesoft.crm.report.lang.StreamUtils;
/*    */ import java.io.OutputStream;
/*    */ import javax.servlet.ServletContext;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ 
/*    */ public class ConsoleUiAction
/*    */   implements Action
/*    */ {
/*    */   private String url;
/*    */   private String contentType;
/*    */   public static final String PREFFIX = "/console";
/*    */ 
/*    */   @Override
public final void setUrl(String url)
/*    */   {
/* 34 */     this.url = url;
/*    */   }
/*    */ 
/*    */   @Override
public final void setContentType(String contentType)
/*    */   {
/* 42 */     this.contentType = contentType;
/*    */   }
/*    */ 
/*    */   @Override
public void execute(ServletContext context, HttpServletRequest request, HttpServletResponse response)
/*    */     throws Exception
/*    */   {
/* 55 */     if (!(this.url.toLowerCase().endsWith(".jsp"))) {
/* 56 */       this.url += ".jsp";
/*    */     }
/*    */ 
/* 59 */     if ((!(this.url.endsWith("/console.jsp"))) && 
/* 60 */       (!(this.url.endsWith("/console_nologin.jsp"))) && ((
/* 61 */       (request.getSession() == null) || (
/* 62 */       request.getSession().getAttribute(User.class.getName()) == null)))) {
/* 63 */       response.sendRedirect(ReportContext.getContext()
/* 64 */         .getRequestURI("console/console_nologin.jsp", true));
/* 65 */       return;
/*    */     }
/* 67 */     ParameterMap map = new ParameterMap(request);
/* 68 */     HTMLConsoleDocument doc = new HTMLConsoleDocument();
/*    */ 
/* 70 */     doc.setBodyContent(super.getClass().getResourceAsStream(this.url));
/* 71 */     response.setContentType(
/* 72 */       this.contentType + ";charset=" + map.getReplyEncoding());
/* 73 */     OutputStream out = response.getOutputStream();
/*    */     try {
/* 75 */       doc.output(out, map.getReplyEncoding());
/*    */     } catch (Exception e) {
/* 77 */       e.printStackTrace();
/*    */     } finally {
/* 79 */       StreamUtils.close(out);
/*    */     }
/*    */   }
/*    */ 
/*    */   @Override
public void release()
/*    */   {
/*    */   }
/*    */ }

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.console.ConsoleUiAction
 * JD-Core Version:    0.5.3
 */