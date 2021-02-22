/*    */ package com.ztesoft.crm.report.generator.html.elements;
/*    */ 
/*    */ public class HTMLBox extends HTMLNode
/*    */ {
/*    */   public HTMLBox()
/*    */   {
/* 17 */     this("div");
/* 18 */     setStyle("overflow-x", "hidden");
/* 19 */     setStyle("overflow-y", "hidden");
/* 20 */     setStyle("margin", "0");
/*    */   }
/*    */ 
/*    */   public HTMLBox(String tagName)
/*    */   {
/* 27 */     super(tagName);
/*    */   }
/*    */ 
/*    */   public HTMLBox(String tagName, String innerHTML)
/*    */   {
/* 36 */     super(tagName, innerHTML);
/*    */   }
/*    */ }

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.generator.html.elements.HTMLBox
 * JD-Core Version:    0.5.3
 */