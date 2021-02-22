/*    */ package com.ztesoft.crm.report.config.parser.impl;
/*    */ 
/*    */ import com.ztesoft.crm.report.config.elements.Element;
/*    */ import com.ztesoft.crm.report.config.elements.InterceptorsConfig;
/*    */ 
/*    */ public class InterceptorsConfigParser extends NodeParserImpl
/*    */ {
/*    */   @Override
protected Element createElement(String nodeName)
/*    */   {
/* 24 */     return new InterceptorsConfig();
/*    */   }
/*    */ }

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.config.parser.impl.InterceptorsConfigParser
 * JD-Core Version:    0.5.3
 */