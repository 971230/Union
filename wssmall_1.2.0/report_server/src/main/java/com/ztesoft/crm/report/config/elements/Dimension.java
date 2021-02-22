/*    */ package com.ztesoft.crm.report.config.elements;
/*    */ 
/*    */ public class Dimension extends Element
/*    */ {
/*    */   private static final long serialVersionUID = -223298966704364248L;
/*    */   private String name;
/*    */   private String type;
/*    */   private String dataSource;
/*    */ 
/*    */   public String getName()
/*    */   {
/* 30 */     return this.name; }
/*    */ 
/*    */   public void setName(String name) {
/* 33 */     this.name = name; }
/*    */ 
/*    */   public String getType() {
/* 36 */     return this.type; }
/*    */ 
/*    */   public void setType(String type) {
/* 39 */     this.type = type; }
/*    */ 
/*    */   public String getDataSource() {
/* 42 */     return this.dataSource; }
/*    */ 
/*    */   public void setDataSource(String dataSource) {
/* 45 */     this.dataSource = dataSource;
/*    */   }
/*    */ }

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.config.elements.Dimension
 * JD-Core Version:    0.5.3
 */