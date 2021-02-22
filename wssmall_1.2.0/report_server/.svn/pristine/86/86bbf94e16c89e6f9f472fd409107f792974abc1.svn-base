/*    */ package com.ztesoft.crm.report;
/*    */ 
/*    */ import com.ztesoft.crm.report.config.elements.Report;
/*    */ import com.ztesoft.crm.report.config.parser.ReportParser;
/*    */ import com.ztesoft.crm.report.lang.Utils;
/*    */ import java.io.InputStream;
/*    */ import java.util.HashMap;
/*    */ 
/*    */ public class ReportFactory
/*    */ {
/* 18 */   private static final ReportFactory rf = new ReportFactory();
/*    */ 
/* 24 */   private HashMap map = new HashMap();
/*    */ 
/*    */   public static final synchronized ReportFactory getInstance()
/*    */   {
/* 21 */     return rf;
/*    */   }
/*    */ 
/*    */   public final Report getReport(String path, InputStream in)
/*    */     throws Exception
/*    */   {
/* 36 */     return parseReport(path, in);
/*    */   }
/*    */ 
/*    */   private Report parseReport(String path, InputStream in) throws Exception {
/* 40 */     Report r = (Report)this.map.get(path);
/* 41 */     if ((r == null) || 
/* 42 */       (!(Utils.isTrue(ReportContext.getContext().getCacheReport())))) {
/* 43 */       r = new ReportParser().parse(in);
/*    */ 
/* 45 */       r.setPath(path);
/* 46 */       r.initialize(r);
/* 47 */       this.map.put(path, r);
/*    */     }
/* 49 */     return r;
/*    */   }
/*    */ }

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.ReportFactory
 * JD-Core Version:    0.5.3
 */