/*    */ package com.ztesoft.crm.report.generator.html.elements;
/*    */ 
/*    */ public final class HTMLTags
/*    */ {
/*    */   public static final HTMLNode createBox()
/*    */   {
/* 12 */     HTMLNode node = new HTMLNode();
/* 13 */     node.setTagName("div");
/* 14 */     return node;
/*    */   }
/*    */ 
/*    */   public static final HTMLChartFlash createChartFlash(String id) {
/* 18 */     HTMLChartFlash flash = new HTMLChartFlash();
/* 19 */     flash.setId(id);
/* 20 */     return flash;
/*    */   }
/*    */ 
/*    */   public static final HTMLNode createScript(String context) {
/* 24 */     HTMLNode node = new HTMLNode("script");
/* 25 */     node.setAttribute("language", "javascript");
/* 26 */     node.setInnerHTML(context);
/* 27 */     return node;
/*    */   }
/*    */ 
/*    */   public static final HTMLNode createTable(String id) {
/* 31 */     HTMLNode n = new HTMLNode();
/* 32 */     n.setAttribute("cellspacing", "0");
/* 33 */     n.setAttribute("cellpadding", "0");
/* 34 */     n.setAttribute("border", "0");
/* 35 */     n.setId(id);
/* 36 */     n.setTagName("table");
/* 37 */     return n;
/*    */   }
/*    */ 
/*    */   public static final HTMLTd createTableCell() {
/* 41 */     HTMLTd n = new HTMLTd();
/* 42 */     n.setTagName("td");
/* 43 */     return n;
/*    */   }
/*    */ 
/*    */   public static final HTMLNode createTableRow() {
/* 47 */     HTMLNode n = new HTMLNode();
/* 48 */     n.setTagName("tr");
/* 49 */     return n;
/*    */   }
/*    */ }

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.generator.html.elements.HTMLTags
 * JD-Core Version:    0.5.3
 */