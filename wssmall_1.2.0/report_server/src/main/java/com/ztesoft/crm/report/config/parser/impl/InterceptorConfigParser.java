/*    */ package com.ztesoft.crm.report.config.parser.impl;
/*    */ 
/*    */ import com.ztesoft.crm.report.config.elements.Element;
/*    */ import com.ztesoft.crm.report.config.elements.InterceptorConfig;
/*    */ 
/*    */ public class InterceptorConfigParser extends NodeParserImpl
/*    */ {
/*    */   @Override
protected Element createElement(String nodeName)
/*    */   {
/* 24 */     return new InterceptorConfig();
/*    */   }
/*    */ }

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.config.parser.impl.InterceptorConfigParser
 * JD-Core Version:    0.5.3
 */