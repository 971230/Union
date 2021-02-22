/*    */ package com.ztesoft.crm.report;
/*    */ 
/*    */ import com.ztesoft.crm.report.config.elements.Report;
/*    */ import java.util.Collections;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ public final class ReportSession
/*    */ {
/* 26 */   private static final ThreadLocal local = new ThreadLocal()
/*    */   {
/*    */     @Override
protected Object initialValue()
/*    */     {
/* 30 */       return Collections.synchronizedMap(new HashMap());
/*    */     }
/* 26 */   };
/*    */ 
/*    */   private static final void setSession(String name, Object obj)
/*    */   {
/* 37 */     Map map = (Map)local.get();
/*    */ 
/* 39 */     map.put(name, obj);
/*    */   }
/*    */ 
/*    */   private static final Object getSession(String name)
/*    */   {
/* 45 */     Map map = (Map)local.get();
/*    */ 
/* 47 */     if (map == null) {
/* 48 */       return null;
/*    */     }
/* 50 */     return map.get(name);
/*    */   }
/*    */ 
/*    */   public static final void setReport(Report r) {
/* 54 */     setSession(Report.class.getName(), r);
/*    */   }
/*    */ 
/*    */   public static final Report getReport()
/*    */   {
/* 59 */     return ((Report)getSession(Report.class.getName()));
/*    */   }
/*    */ 
/*    */   public static final void release()
/*    */   {
/* 65 */     Map map = (Map)local.get();
/* 66 */     if (map != null)
/* 67 */       map.clear();
/*    */   }
/*    */ }

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.ReportSession
 * JD-Core Version:    0.5.3
 */