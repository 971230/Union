/*    */ package com.ztesoft.crm.report.interfaces;
/*    */ 
/*    */ import com.ztesoft.crm.report.ReportContext;
/*    */ import com.ztesoft.crm.report.actions.ParameterMap;
/*    */ import com.ztesoft.crm.report.lang.Utils;
/*    */ import javax.servlet.ServletContext;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ 
/*    */ public class PromptInterceptor
/*    */   implements Interceptor
/*    */ {
/*    */   @Override
public boolean doEnd(ServletContext context, HttpServletRequest request, String reportURL, ParameterMap map, String dataSource)
/*    */     throws Exception
/*    */   {
/* 37 */     return true;
/*    */   }
/*    */ 
/*    */   @Override
public boolean doStart(ServletContext context, HttpServletRequest request, String reportURL, ParameterMap map, String dataSource)
/*    */     throws Exception
/*    */   {
/* 51 */     Prompter p = null;
/*    */     try
/*    */     {
/* 54 */       if (Utils.isEmpty(ReportContext.getContext().getPrompterClass())) return true;
/* 55 */       p = (Prompter)Class.forName(
/* 56 */         ReportContext.getContext().getPrompterClass())
/* 57 */         .newInstance();
/*    */ 
/* 59 */       map.setPromptConfig(p.getPromptConfig(context, request, reportURL));
/*    */     }
/*    */     catch (Exception localException)
/*    */     {
/*    */     }
/* 64 */     return true;
/*    */   }
/*    */ 
/*    */   @Override
public void release()
/*    */   {
/*    */   }
/*    */ }

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.interfaces.PromptInterceptor
 * JD-Core Version:    0.5.3
 */