/*    */ package com.ztesoft.crm.report.config.elements;
/*    */ 
/*    */ public class Axis extends Element
/*    */ {
/*    */   private static final long serialVersionUID = 9107100875726561089L;
/*    */   private String column;
/*    */   private String name;
/*    */   private String text;
/*    */   private String title;
/*    */   private Column value;
/*    */ 
/*    */   public String getTitle()
/*    */   {
/* 20 */     return this.title;
/*    */   }
/*    */ 
/*    */   public void setTitle(String title) {
/* 24 */     this.title = title;
/*    */   }
/*    */ 
/*    */   public String getColumn()
/*    */   {
/* 40 */     return this.column;
/*    */   }
/*    */ 
/*    */   public String getName()
/*    */   {
/* 47 */     return this.name;
/*    */   }
/*    */ 
/*    */   public String getText()
/*    */   {
/* 54 */     return this.text;
/*    */   }
/*    */ 
/*    */   public Column getValue()
/*    */   {
/* 61 */     return this.value;
/*    */   }
/*    */ 
/*    */   @Override
protected void init(Element top)
/*    */   {
/* 73 */     this.value = ((Column)top.getElement(this.column));
/*    */   }
/*    */ 
/*    */   public void setColumn(String column)
/*    */   {
/* 81 */     this.column = column;
/*    */   }
/*    */ 
/*    */   public void setName(String name)
/*    */   {
/* 89 */     this.name = name;
/*    */   }
/*    */ 
/*    */   public void setText(String text)
/*    */   {
/* 97 */     this.text = text;
/*    */   }
/*    */ }

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.config.elements.Axis
 * JD-Core Version:    0.5.3
 */