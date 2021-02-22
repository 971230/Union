/*    */ package com.ztesoft.crm.report.config.elements;
/*    */ 
/*    */ public class Table extends Element
/*    */ {
/*    */   private static final long serialVersionUID = 7529054647035756829L;
/*    */   private String name;
/*    */   private Sql sql;
/*    */   private String text;
/*    */ 
/*    */   public static final String formatId(String id)
/*    */   {
/* 20 */     return "el" + id;
/*    */   }
/*    */ 
/*    */   @Override
public void setId(String id)
/*    */   {
/* 32 */     super.setId(formatId(id));
/*    */   }
/*    */ 
/*    */   public String getName()
/*    */   {
/* 39 */     return this.name;
/*    */   }
/*    */ 
/*    */   public Sql getSql()
/*    */   {
/* 46 */     return this.sql;
/*    */   }
/*    */ 
/*    */   public String getText()
/*    */   {
/* 53 */     return this.text;
/*    */   }
/*    */ 
/*    */   @Override
protected void init(Element top)
/*    */   {
/* 65 */     this.sql = ((Sql)getChildElement(Sql.class));
/*    */   }
/*    */ 
/*    */   public void setName(String name)
/*    */   {
/* 73 */     this.name = name;
/*    */   }
/*    */ 
/*    */   public void setText(String text)
/*    */   {
/* 81 */     this.text = text;
/*    */   }
/*    */ }

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.config.elements.Table
 * JD-Core Version:    0.5.3
 */