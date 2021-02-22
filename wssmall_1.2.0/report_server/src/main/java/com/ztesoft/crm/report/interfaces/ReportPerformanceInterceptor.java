/*     */ package com.ztesoft.crm.report.interfaces;
/*     */ 
/*     */ import com.ztesoft.crm.edp.utils.CommonUtil;
/*     */ import com.ztesoft.crm.report.ReportContext;
/*     */ import com.ztesoft.crm.report.actions.ParameterMap;
/*     */ import com.ztesoft.crm.report.lang.JdbcUtils;
/*     */ import com.ztesoft.crm.report.lang.Utils;
/*     */ import com.ztesoft.crm.report.log.ReportLogger;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.Timestamp;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.Map;
/*     */ import javax.servlet.ServletContext;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ 
/*     */ public class ReportPerformanceInterceptor
/*     */   implements Interceptor
/*     */ {
/*     */   private Date startTime;
/*     */ 
/*     */   @Override
public boolean doEnd(ServletContext context, HttpServletRequest request, String reportURL, ParameterMap map, String dataSource)
/*     */     throws Exception
/*     */   {
/*  49 */     Date endTime = new Date();
/*  50 */     StringBuffer sql = new StringBuffer()
/*  51 */       .append("insert into EDP_REPORT_PERFORMANCE");
/*  52 */     sql
/*  53 */       .append("(MENU_ID,REPORT_URL,PARTY_ROLE_ID,START_TIME,END_TIME,ELAPSED_TIME,DATE_NO,TIME_SLICE)");
/*  54 */     sql.append(" values(?,?,?,?,?,?,?,?)");
/*     */ 
/*  56 */     Map session = CommonUtil.getSession(request);
/*     */ 
/*  58 */     Connection conn = null;
/*  59 */     int i = 1;
/*  60 */     PreparedStatement stmt = null;
/*  61 */     Calendar calendar = Calendar.getInstance();
/*  62 */     calendar.setTime(new Date(this.startTime.getTime()));
/*  63 */     String dateSlice = Utils.format(this.startTime, "HH:00");
/*  64 */     if (calendar.get(12) > 30) {
/*  65 */       dateSlice = Utils.format(this.startTime, "HH:30");
/*     */     }
/*  67 */     if (calendar.get(10) >= 21) {
/*  68 */       calendar.add(5, 1);
/*  69 */       dateSlice = "08:00";
/*     */     }
/*  71 */     if (calendar.get(10) < 8) {
/*  72 */       dateSlice = "08:00";
/*     */     }
/*     */     try
/*     */     {
/*  76 */       conn = ReportContext.getContext().getDataSource(dataSource)
/*  77 */         .getConnection();
/*  78 */       stmt = conn.prepareStatement(sql.toString());
/*  79 */       stmt.setString(i++, map.getString("menu_id", "-1"));
/*  80 */       stmt.setString(i++, reportURL);
/*  81 */       stmt.setString(i++, (String)session.get("partyRoleId"));
/*  82 */       stmt.setTimestamp(i++, new Timestamp(this.startTime.getTime()));
/*  83 */       stmt.setTimestamp(i++, new Timestamp(endTime.getTime()));
/*  84 */       stmt.setString(i++, 
/*  85 */         String.valueOf(endTime.getTime() - 
/*  85 */         this.startTime.getTime()));
/*  86 */       stmt.setString(i++, Utils.format(calendar.getTime(), "yyyyMMdd"));
/*  87 */       stmt.setString(i++, dateSlice);
/*     */ 
/*  89 */       stmt.execute();
/*     */     }
/*     */     catch (Exception e) {
/*  92 */       ReportLogger.getLogger().error("记录性能日志失败:", e);
/*     */     } finally {
/*  94 */       JdbcUtils.close(conn, stmt, null);
/*     */     }
/*     */ 
/*  97 */     return true;
/*     */   }
/*     */ 
/*     */   @Override
public boolean doStart(ServletContext context, HttpServletRequest request, String reportURL, ParameterMap map, String dataSource)
/*     */     throws Exception
/*     */   {
/* 111 */     this.startTime = new Date();
/* 112 */     return true;
/*     */   }
/*     */ 
/*     */   @Override
public void release()
/*     */   {
/* 122 */     this.startTime = null;
/*     */   }
/*     */ }

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.interfaces.ReportPerformanceInterceptor
 * JD-Core Version:    0.5.3
 */