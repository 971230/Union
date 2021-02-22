/*    */ package com.ztesoft.crm.report.lang;
/*    */ 
/*    */ import java.sql.Connection;
/*    */ import java.sql.ResultSet;
/*    */ import java.sql.Statement;
/*    */ 
/*    */ public final class JdbcUtils
/*    */ {
/*    */   public static final void close(Connection conn, Statement stmt, ResultSet res)
/*    */   {
/*    */     try
/*    */     {
/* 26 */       if (res != null)
/* 27 */         res.close();
/*    */     } catch (Exception localException) {
/*    */     }
/*    */     try {
/* 31 */       if (stmt != null)
/* 32 */         stmt.close();
/*    */     } catch (Exception localException1) {
/*    */     }
/*    */     try {
/* 36 */       if (conn != null)
/* 37 */         conn.setAutoCommit(true);
/*    */     } catch (Exception localException2) {
/*    */     }
/*    */     try {
/* 41 */       if (conn != null)
/* 42 */         conn.close();
/*    */     }
/*    */     catch (Exception localException3)
/*    */     {
/*    */     }
/*    */   }
/*    */ }

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.lang.JdbcUtils
 * JD-Core Version:    0.5.3
 */