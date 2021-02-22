/*    */ package com.ztesoft.crm.report.rule;
/*    */ 
/*    */ import com.ztesoft.crm.report.lang.RegularExpressionUtils;
/*    */ import java.io.PrintStream;
/*    */ import java.math.BigDecimal;
/*    */ 
/*    */ public class Constant extends Variable
/*    */ {
/*    */   private String value;
/*    */ 
/*    */   public Constant(String str)
/*    */   {
/* 23 */     this.value = str;
/*    */   }
/*    */ 
/*    */   @Override
public String getName()
/*    */   {
/* 29 */     return this.value.trim();
/*    */   }
/*    */ 
/*    */   @Override
public void list(PrintStream out)
/*    */   {
/* 34 */     out.println(this.value);
/*    */   }
/*    */ 
/*    */   @Override
public Object execute()
/*    */   {
/* 41 */     String a = this.value;
/*    */ 
/* 43 */     if (a == null) {
/* 44 */       return null;
/*    */     }
/* 46 */     if ((a.trim().startsWith("'")) && (a.trim().endsWith("'"))) {
/* 47 */       a = a.trim().substring(1, a.trim().length() - 1);
/*    */     }
/*    */     else {
/* 50 */       if (RegularExpressionUtils.isNumber(a.trim())) {
/* 51 */         return new BigDecimal(a.trim());
/*    */       }
/*    */ 
/* 54 */       return getVariateValue(a.trim());
/*    */     }
/*    */ 
/* 58 */     return a;
/*    */   }
/*    */ 
/*    */   @Override
public String toString()
/*    */   {
/* 63 */     return this.value;
/*    */   }
/*    */ }

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.rule.Constant
 * JD-Core Version:    0.5.3
 */