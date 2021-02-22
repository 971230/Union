/*    */ package com.ztesoft.crm.report.generator.html.elements;
/*    */ 
/*    */ public class HTMLTableLayout extends HTMLNode
/*    */ {
/*    */   public HTMLNode a;
/*    */   public HTMLNode b;
/*    */   public HTMLNode c;
/*    */   public HTMLNode d;
/*    */   public String tabId;
/*    */ 
/*    */   public HTMLTableLayout(String id, boolean list)
/*    */   {
/* 22 */     this.tabId = id;
/* 23 */     if (list) {
/* 24 */       this.b = createDiv();
/* 25 */       this.b.setId(id + "_b");
/*    */ 
/* 27 */       this.b.setClassName("crosstab-header");
/* 28 */       this.d = createDiv();
/* 29 */       this.d.setClassName("crosstab-body");
/* 30 */       this.d.setId(id + "_d");
/*    */     }
/*    */     else {
/* 33 */       this.a = createDiv();
/* 34 */       this.a.setClassName("crosstab-metric");
/* 35 */       this.a.setId(id + "_a");
/*    */ 
/* 37 */       this.b = createDiv();
/* 38 */       this.b.setClassName("crosstab-header");
/* 39 */       this.b.setId(id + "_b");
/*    */ 
/* 41 */       this.c = createDiv();
/* 42 */       this.c.setClassName("crosstab-header");
/* 43 */       this.c.setId(id + "_c");
/*    */ 
/* 45 */       this.d = createDiv();
/* 46 */       this.d.setClassName("crosstab-body");
/* 47 */       this.d.setId(id + "_d");
/*    */     }
/*    */ 
/* 50 */     HTMLNode tr = new HTMLNode("tr");
/* 51 */     HTMLNode td = HTMLTags.createTableCell();
/* 52 */     if (this.a != null) {
/* 53 */       td.add(this.a);
/* 54 */       td.add(this.c);
/* 55 */       tr.add(td);
/* 56 */       td = HTMLTags.createTableCell();
/*    */     }
/* 58 */     td.add(this.b);
/* 59 */     td.add(this.d);
/* 60 */     tr.add(td);
/* 61 */     add(tr);
/* 62 */     setTagName("table");
/* 63 */     setAttribute("cellspacing", "0");
/* 64 */     setAttribute("cellpadding", "0");
/* 65 */     setClassName("crosstab");
/*    */   }
/*    */ 
/*    */   private HTMLNode createDiv() {
/* 69 */     HTMLNode dv = new HTMLNode("table");
/* 70 */     dv.setAttribute("cellspacing", "0");
/* 71 */     dv.setAttribute("cellpadding", "0");
/* 72 */     dv.setAttribute("border", "0");
/* 73 */     return dv;
/*    */   }
/*    */ }

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.generator.html.elements.HTMLTableLayout
 * JD-Core Version:    0.5.3
 */