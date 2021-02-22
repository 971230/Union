/*    */ package com.ztesoft.crm.report.config.elements;
/*    */ 
/*    */ public class InterceptorsConfig extends Element
/*    */ {
/*    */   private static final long serialVersionUID = -8327976267794931409L;
/*    */   private String replace;
/* 36 */   private String extend = "true";
/*    */ 
/*    */   public String getReplace()
/*    */   {
/* 27 */     return this.replace;
/*    */   }
/*    */ 
/*    */   public void setReplace(String replace) {
/* 31 */     this.replace = replace;
/*    */   }
/*    */ 
/*    */   public String getExtend()
/*    */   {
/* 39 */     return this.extend;
/*    */   }
/*    */ 
/*    */   public void setExtend(String extend) {
/* 43 */     this.extend = extend;
/*    */   }
/*    */ }

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.config.elements.InterceptorsConfig
 * JD-Core Version:    0.5.3
 */