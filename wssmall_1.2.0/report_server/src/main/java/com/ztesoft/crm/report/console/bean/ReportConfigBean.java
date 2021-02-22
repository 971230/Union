/*    */ package com.ztesoft.crm.report.console.bean;
/*    */ 
/*    */ import com.ztesoft.crm.report.ReportContext;
/*    */ import com.ztesoft.crm.report.actions.ParameterMap;
/*    */ import com.ztesoft.crm.report.console.Bean;
/*    */ import com.ztesoft.crm.report.console.db.DataBaseStruct;
/*    */ import com.ztesoft.crm.report.lang.Utils;
/*    */ import java.util.List;
/*    */ 
/*    */ public class ReportConfigBean extends Bean
/*    */ {
/*    */   public List getTableColumns(ParameterMap map)
/*    */     throws Exception
/*    */   {
/* 28 */     DataBaseStruct dbc = new DataBaseStruct();
/*    */ 
/* 30 */     dbc.setConnection(
/* 31 */       ReportContext.getContext().getDataSource(map.getString("dataSource")).getConnection());
/*    */ 
/* 33 */     if (!(map.isEmpty("name"))) {
/* 34 */       return dbc.getTableColumns(map.getString("name"));
/*    */     }
/* 36 */     return dbc.getResultSetColumns(Utils.format(map.getString("sql"), map));
/*    */   }
/*    */ }

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.console.bean.ReportConfigBean
 * JD-Core Version:    0.5.3
 */