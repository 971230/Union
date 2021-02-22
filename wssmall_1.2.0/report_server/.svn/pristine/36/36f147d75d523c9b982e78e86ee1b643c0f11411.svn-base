/*    */ package com.ztesoft.crm.report.config.parser;
/*    */ 
/*    */ import com.ztesoft.crm.report.config.elements.Report;
/*    */ import com.ztesoft.crm.report.lang.XMLParser;
/*    */ import java.io.InputStream;
/*    */ import org.w3c.dom.Node;
/*    */ 
/*    */ public class ReportParser
/*    */ {
/*    */   public Report parse(InputStream in)
/*    */     throws Exception
/*    */   {
/* 26 */     Node n = XMLParser.parser(in).getRootElement();
/* 27 */     return ((Report)NodeParserFactory.getParser(n.getNodeName()).parse(n));
/*    */   }
/*    */ }

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.config.parser.ReportParser
 * JD-Core Version:    0.5.3
 */