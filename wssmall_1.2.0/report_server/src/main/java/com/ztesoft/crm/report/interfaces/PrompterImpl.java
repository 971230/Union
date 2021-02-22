/*    */ package com.ztesoft.crm.report.interfaces;
/*    */ 
/*    */ import com.ztesoft.crm.report.interfaces.prompt.PromptConfig;
/*    */ import javax.servlet.ServletContext;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ 
/*    */ public class PrompterImpl
/*    */   implements Prompter
/*    */ {
/*    */   @Override
public PromptConfig[] getPromptConfig(ServletContext content, HttpServletRequest request, String report)
/*    */   {
/* 27 */     return ((PromptConfig[])request.getSession().getAttribute(PromptConfig.class.getName()));
/*    */   }
/*    */ 
/*    */   @Override
public void savePromptConfig(ServletContext content, HttpServletRequest request, String report, PromptConfig[] matchers)
/*    */   {
/* 33 */     request.getSession().setAttribute(PromptConfig.class.getName(), matchers);
/*    */   }
/*    */ }

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.interfaces.PrompterImpl
 * JD-Core Version:    0.5.3
 */