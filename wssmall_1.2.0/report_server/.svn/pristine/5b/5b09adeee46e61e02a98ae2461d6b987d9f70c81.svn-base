/*    */ package com.ztesoft.crm.report.console.db;
/*    */ 
/*    */ import com.ztesoft.crm.report.lang.Utils;
/*    */ 
/*    */ public final class DataBaseColumn
/*    */ {
/*    */   private String name;
/*    */   private String text;
/*    */   private String dataType;
/*    */ 
/*    */   public DataBaseColumn()
/*    */   {
/*    */   }
/*    */ 
/*    */   public String getName()
/*    */   {
/* 27 */     return this.name;
/*    */   }
/*    */ 
/*    */   public void setName(String name) {
/* 31 */     this.name = name;
/*    */   }
/*    */ 
/*    */   public String getText() {
/* 35 */     return this.text;
/*    */   }
/*    */ 
/*    */   public void setText(String text) {
/* 39 */     this.text = text;
/*    */   }
/*    */ 
/*    */   public String getDataType() {
/* 43 */     return this.dataType;
/*    */   }
/*    */ 
/*    */   public void setDataType(String dataType) {
/* 47 */     this.dataType = dataType;
/*    */   }
/*    */ 
/*    */   public DataBaseColumn(String name, String text, String dataType)
/*    */   {
/* 52 */     this.name = name;
/* 53 */     this.text = text;
/*    */ 
/* 55 */     if (Utils.isEmpty(dataType))
/* 56 */       dataType = "string";
/* 57 */     this.dataType = dataType;
/*    */ 
/* 59 */     if (!(Utils.isEmpty(text))) return; this.text = this.name;
/*    */   }
/*    */ }

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.console.db.DataBaseColumn
 * JD-Core Version:    0.5.3
 */