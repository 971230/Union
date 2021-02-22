/*    */ package com.ztesoft.crm.report.actions;
/*    */ 
/*    */ import com.ztesoft.crm.report.generator.html.elements.HTMLConsoleDocument;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ 
/*    */ public class JSPAction extends AbstractAction
/*    */ {
/*    */   @Override
protected void execute(OutputStream out, ParameterMap map)
/*    */     throws Exception
/*    */   {
/* 26 */     InputStream in = super.getClass().getResourceAsStream(getUrl());
/* 27 */     HTMLConsoleDocument doc = new HTMLConsoleDocument();
/* 28 */     doc.setBodyContent(in);
/* 29 */     doc.output(out, map.getReplyEncoding());
/*    */   }
/*    */ }

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.actions.JSPAction
 * JD-Core Version:    0.5.3
 */