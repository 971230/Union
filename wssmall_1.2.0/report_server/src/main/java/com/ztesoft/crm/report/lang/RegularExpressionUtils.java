/*    */ package com.ztesoft.crm.report.lang;
/*    */ 
/*    */ public final class RegularExpressionUtils
/*    */ {
/*    */   public static final boolean isNumber(String v)
/*    */   {
/* 12 */     return ((v == null) ? false : v.matches("^(\\-)?\\d+(.\\d+)?$")); }
/*    */ 
/*    */   public static final boolean isNumber(Object v) {
/* 15 */     return ((v == null) ? false : isNumber(v.toString()));
/*    */   }
/*    */ }

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.lang.RegularExpressionUtils
 * JD-Core Version:    0.5.3
 */