/*    */ package com.ztesoft.crm.report.config.elements;
/*    */ 
/*    */ public class Join extends Element
/*    */ {
/*    */   private static final long serialVersionUID = -6712354868452194209L;
/*    */   private String joinTable;
/*    */   private String onTable;
/*    */   private String type;
/*    */ 
/*    */   public String getJoinTable()
/*    */   {
/* 30 */     return this.joinTable;
/*    */   }
/*    */ 
/*    */   public String getOnTable()
/*    */   {
/* 37 */     return this.onTable;
/*    */   }
/*    */ 
/*    */   public String getType()
/*    */   {
/* 44 */     return this.type;
/*    */   }
/*    */ 
/*    */   public void setJoinTable(String joinTable)
/*    */   {
/* 52 */     this.joinTable = Table.formatId(joinTable);
/*    */   }
/*    */ 
/*    */   public void setOnTable(String onTable)
/*    */   {
/* 60 */     this.onTable = Table.formatId(onTable);
/*    */   }
/*    */ 
/*    */   public void setType(String type)
/*    */   {
/* 68 */     this.type = type;
/*    */   }
/*    */ }

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.config.elements.Join
 * JD-Core Version:    0.5.3
 */