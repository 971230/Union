/*    */ package com.ztesoft.crm.report.document;
/*    */ 
/*    */ import com.ztesoft.crm.report.generator.excel.elements.ExcelDocument;
/*    */ import com.ztesoft.crm.report.generator.html.elements.HTMLDocument;
/*    */ 
/*    */ public class DocumentFactory
/*    */ {
/*    */   public static final Document createDocument(String type, String encoding)
/*    */   {
/* 16 */     Document doc = new HTMLDocument();
/* 17 */     if ("excel".equals(type)) {
/* 18 */       doc = new ExcelDocument();
/*    */     }
/* 20 */     doc.setEncoding(encoding);
/* 21 */     return doc;
/*    */   }
/*    */ }

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.document.DocumentFactory
 * JD-Core Version:    0.5.3
 */