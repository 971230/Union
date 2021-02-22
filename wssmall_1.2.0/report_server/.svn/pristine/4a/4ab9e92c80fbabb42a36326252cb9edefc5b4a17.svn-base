/*    */ package com.ztesoft.crm.report.config.parser;
/*    */ 
/*    */ import com.ztesoft.crm.report.config.parser.impl.InterceptorConfigParser;
/*    */ import com.ztesoft.crm.report.config.parser.impl.InterceptorsConfigParser;
/*    */ import com.ztesoft.crm.report.config.parser.impl.NodeParserImpl;
/*    */ import java.util.HashMap;
/*    */ 
/*    */ public class NodeParserFactory
/*    */ {
/* 17 */   private static HashMap parsers = new HashMap();
/*    */ 
/*    */   static { parsers.put("interceptor", InterceptorConfigParser.class.getName());
/* 20 */     parsers.put("interceptors", InterceptorsConfigParser.class.getName());
/*    */   }
/*    */ 
/*    */   public static final NodeParser getParser(String nodeName)
/*    */   {
/* 30 */     String cn = (String)parsers.get(nodeName);
/*    */     try
/*    */     {
/* 33 */       if (cn != null)
/* 34 */         return ((NodeParser)Class.forName(cn).newInstance());
/*    */     }
/*    */     catch (Exception localException)
/*    */     {
/*    */     }
/* 39 */     return new NodeParserImpl();
/*    */   }
/*    */ }

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.config.parser.NodeParserFactory
 * JD-Core Version:    0.5.3
 */