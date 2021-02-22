/*    */ package com.ztesoft.crm.report.console.bean;
/*    */ 
/*    */ import com.ztesoft.crm.report.actions.ParameterMap;
/*    */ import com.ztesoft.crm.report.console.Bean;
/*    */ import com.ztesoft.crm.report.console.ConsoleReply;
/*    */ import com.ztesoft.crm.report.storage.StorageStreamFactory;
/*    */ 
/*    */ public class ReportDirectoryTreeBean extends Bean
/*    */ {
/*    */   public ConsoleReply list(ParameterMap map)
/*    */     throws Exception
/*    */   {
/* 25 */     String parent = map.getString("parentValue");
/*    */ 
/* 45 */     return createConsoleReply(
/* 46 */       StorageStreamFactory.createStream().getDirectory(getContext(), parent));
/*    */   }
/*    */ 
/*    */   public void createDir(ParameterMap map) throws Exception
/*    */   {
/* 51 */     String dir = map.getString("report_dir");
/* 52 */     String parent = map.getString("value");
/* 53 */     StorageStreamFactory.createStream().createDirectory(getContext(), 
/* 54 */       parent, dir);
/*    */   }
/*    */ }

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.console.bean.ReportDirectoryTreeBean
 * JD-Core Version:    0.5.3
 */