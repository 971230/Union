/*    */ package com.ztesoft.crm.report.config.elements;
/*    */ 
/*    */ public class On extends Element
/*    */ {
/*    */   private static final long serialVersionUID = -6696874060233251160L;
/*    */   private String column;
/*    */   private String joinColumn;
/*    */   private String columnLabel;
/*    */   private String joinColumnLabel;
/*    */   private String operator;
/*    */ 
/*    */   public String getOperator()
/*    */   {
/* 32 */     return this.operator;
/*    */   }
/*    */ 
/*    */   public void setOperator(String operator) {
/* 36 */     this.operator = operator;
/*    */   }
/*    */ 
/*    */   public String getColumn() {
/* 40 */     return this.column;
/*    */   }
/*    */ 
/*    */   public void setColumn(String column) {
/* 44 */     this.column = column;
/*    */   }
/*    */ 
/*    */   public String getJoinColumn() {
/* 48 */     return this.joinColumn;
/*    */   }
/*    */ 
/*    */   public void setJoinColumn(String joinColumn) {
/* 52 */     this.joinColumn = joinColumn;
/*    */   }
/*    */ 
/*    */   public String getColumnLabel() {
/* 56 */     return this.columnLabel;
/*    */   }
/*    */ 
/*    */   public void setColumnLabel(String columnLabel) {
/* 60 */     this.columnLabel = columnLabel;
/*    */   }
/*    */ 
/*    */   public String getJoinColumnLabel() {
/* 64 */     return this.joinColumnLabel;
/*    */   }
/*    */ 
/*    */   public void setJoinColumnLabel(String joinColumnLabel) {
/* 68 */     this.joinColumnLabel = joinColumnLabel;
/*    */   }
/*    */ }

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.config.elements.On
 * JD-Core Version:    0.5.3
 */