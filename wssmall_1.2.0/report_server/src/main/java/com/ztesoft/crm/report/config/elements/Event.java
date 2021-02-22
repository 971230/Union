/*    */ package com.ztesoft.crm.report.config.elements;
/*    */ 
/*    */ public class Event extends Element
/*    */ {
/*    */   private static final long serialVersionUID = 4529337809834451411L;
/*    */   private String name;
/*    */ 
/*    */   public String getName()
/*    */   {
/* 26 */     return this.name;
/*    */   }
/*    */ 
/*    */   public void setName(String name) {
/* 30 */     this.name = name;
/*    */   }
/*    */ 
/*    */   public String getFunctionName() {
/* 34 */     return getParentId() + "_" + this.name;
/*    */   }
/*    */ }

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.config.elements.Event
 * JD-Core Version:    0.5.3
 */