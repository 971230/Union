/*    */ package com.ztesoft.crm.report.lang;
/*    */ 
/*    */ import com.ztesoft.crm.report.ReportContext;
/*    */ import java.io.ByteArrayOutputStream;
/*    */ import java.io.PrintStream;
/*    */ 
/*    */ public final class ExceptionUtils
/*    */ {
/*    */   public static final String trace(Exception e)
/*    */   {
/* 25 */     ByteArrayOutputStream out = new ByteArrayOutputStream();
/* 26 */     e.printStackTrace(new PrintStream(out));
/*    */     try
/*    */     {
/* 29 */       out.close();
/* 30 */       return new String(out.toByteArray(), 
/* 31 */         ReportContext.getContext().getSystemEncoding());
/*    */     }
/*    */     catch (Exception localException)
/*    */     {
/*    */     }
/* 36 */     return new String(out.toByteArray());
/*    */   }
/*    */ }

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.lang.ExceptionUtils
 * JD-Core Version:    0.5.3
 */