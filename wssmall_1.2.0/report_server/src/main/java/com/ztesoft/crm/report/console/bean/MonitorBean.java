/*    */ package com.ztesoft.crm.report.console.bean;
/*    */ 
/*    */ import com.ztesoft.crm.report.ReportMonitor;
/*    */ import com.ztesoft.crm.report.actions.ParameterMap;
/*    */ import com.ztesoft.crm.report.console.Bean;
/*    */ import com.ztesoft.crm.report.console.vo.ConfigXMLNode;
/*    */ import com.ztesoft.crm.report.lang.ObjectPropertyUtils;
/*    */ import java.io.UnsupportedEncodingException;
/*    */ import java.util.List;
/*    */ 
/*    */ public class MonitorBean extends Bean
/*    */ {
/*    */   public List getReportTree(ParameterMap map)
/*    */   {
/* 29 */     return ReportMonitor.getMonitor().getReportTree();
/*    */   }
/*    */ 
/*    */   public byte[] getMonitorHeap(ParameterMap map)
/*    */     throws UnsupportedEncodingException
/*    */   {
/* 35 */     List list = ReportMonitor.getMonitor().getLogs(map.getString("path"));
/* 36 */     ConfigXMLNode root = new ConfigXMLNode();
/* 37 */     root.setTagName("monitor");
/* 38 */     if (list != null) {
/* 39 */       for (int i = list.size() - 1; i >= 0; --i) {
/* 40 */         ConfigXMLNode n = new ConfigXMLNode();
/* 41 */         Object o = list.get(i);
/* 42 */         n.setTagName("log");
/* 43 */         n.setAttribute("startTime", ObjectPropertyUtils.getString(o, "startTime"));
/* 44 */         n.setAttribute("endTime", ObjectPropertyUtils.getString(o, "endTime"));
/* 45 */         n.setAttribute("elapsedTime", ObjectPropertyUtils.getString(o, "elapsedTime"));
/* 46 */         n.setAttribute("error", ObjectPropertyUtils.getString(o, "error"));
/* 47 */         n.add(new ConfigXMLNode("sql", ObjectPropertyUtils.getString(o, "sql")));
/* 48 */         n.add(new ConfigXMLNode("errorMessage", ObjectPropertyUtils.getString(o, "errorMessage")));
/* 49 */         root.add(n);
/*    */       }
/*    */     }
/*    */ 
/* 53 */     return root.toXMLString().getBytes(map.getReplyEncoding());
/*    */   }
/*    */ 
/*    */   public void clearReportLog(ParameterMap map)
/*    */   {
/* 58 */     ReportMonitor.getMonitor().clearLogs(map.getString("path"));
/*    */   }
/*    */ }

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.console.bean.MonitorBean
 * JD-Core Version:    0.5.3
 */