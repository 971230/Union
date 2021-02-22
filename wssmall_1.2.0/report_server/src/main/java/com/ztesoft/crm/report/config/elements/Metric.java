/*    */ package com.ztesoft.crm.report.config.elements;
/*    */ 
/*    */ public class Metric extends Element
/*    */ {
/*    */   private static final long serialVersionUID = -8469797408226507183L;
/*    */   private String column;
/*    */   private String name;
/*    */   private String text;
/*    */   private Column value;
/*    */   private String align;
/*    */ 
/*    */   public String getAlign()
/*    */   {
/* 23 */     return this.align;
/*    */   }
/*    */ 
/*    */   public void setAlign(String align) {
/* 27 */     this.align = align;
/*    */   }
/*    */ 
/*    */   public String getColumn()
/*    */   {
/* 41 */     return this.column;
/*    */   }
/*    */ 
/*    */   public String getName()
/*    */   {
/* 48 */     return this.name;
/*    */   }
/*    */ 
/*    */   public String getText()
/*    */   {
/* 55 */     return this.text;
/*    */   }
/*    */ 
/*    */   public Column getValue()
/*    */   {
/* 62 */     return this.value;
/*    */   }
/*    */ 
/*    */   @Override
protected void init(Element top)
/*    */   {
/* 74 */     this.value = ((Column)top.getElement(this.column));
/*    */   }
/*    */ 
/*    */   public void setColumn(String column)
/*    */   {
/* 82 */     this.column = column;
/*    */   }
/*    */ 
/*    */   public void setName(String name)
/*    */   {
/* 90 */     this.name = name;
/*    */   }
/*    */ 
/*    */   public void setText(String text)
/*    */   {
/* 98 */     this.text = text;
/*    */   }
/*    */ }

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.config.elements.Metric
 * JD-Core Version:    0.5.3
 */