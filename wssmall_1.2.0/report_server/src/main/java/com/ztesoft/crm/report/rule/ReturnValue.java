/*    */ package com.ztesoft.crm.report.rule;
/*    */ 
/*    */ public class ReturnValue
/*    */ {
/*    */   private Object value;
/*    */ 
/*    */   public ReturnValue(Object str)
/*    */   {
/* 17 */     this.value = str; }
/*    */ 
/*    */   public Object getValue() {
/* 20 */     return this.value;
/*    */   }
/*    */ 
/*    */   @Override
public String toString() {
/* 24 */     return ((this.value != null) ? this.value.toString() : "无返回");
/*    */   }
/*    */ }

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.rule.ReturnValue
 * JD-Core Version:    0.5.3
 */