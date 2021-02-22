/*    */ package com.ztesoft.crm.report.console.db;
/*    */ 
/*    */ import com.ztesoft.crm.report.lang.ArrayListEx;
/*    */ import com.ztesoft.crm.report.lang.JdbcUtils;
/*    */ import java.sql.Connection;
/*    */ import java.sql.PreparedStatement;
/*    */ import java.sql.ResultSet;
/*    */ import java.sql.ResultSetMetaData;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ 
/*    */ public class DataBaseStruct
/*    */ {
/*    */   private Connection connection;
/*    */   private HashMap types;
/*    */ 
/*    */   public DataBaseStruct()
/*    */   {
/* 34 */     this.types = new HashMap();
/*    */ 
/* 37 */     this.types.put(String.valueOf(-5), "number");
/* 38 */     this.types.put(String.valueOf(-7), "number");
/* 39 */     this.types.put(String.valueOf(3), "number");
/* 40 */     this.types.put(String.valueOf(8), "number");
/* 41 */     this.types.put(String.valueOf(6), "number");
/* 42 */     this.types.put(String.valueOf(4), "number");
/* 43 */     this.types.put(String.valueOf(2), "number");
/* 44 */     this.types.put(String.valueOf(7), "number");
/* 45 */     this.types.put(String.valueOf(5), "number");
/* 46 */     this.types.put(String.valueOf(1), "string");
/* 47 */     this.types.put(String.valueOf(12), "string");
/* 48 */     this.types.put(String.valueOf(91), "date");
/* 49 */     this.types.put(String.valueOf(93), "date");
/*    */   }
/*    */ 
/*    */   public final Connection getConnection()
/*    */   {
/* 27 */     return this.connection;
/*    */   }
/*    */ 
/*    */   public final void setConnection(Connection connection) {
/* 31 */     this.connection = connection;
/*    */   }
/*    */ 
/*    */   public List getResultSetColumns(String sql)
/*    */     throws Exception
/*    */   {
/* 53 */     PreparedStatement stmt = null;
/* 54 */     ResultSet res = null;
/* 55 */     ArrayListEx ex = new ArrayListEx();
/*    */     try
/*    */     {
/* 58 */       stmt = this.connection.prepareStatement(sql);
/* 59 */       stmt.setMaxRows(1);
/* 60 */       res = stmt.executeQuery();
/* 61 */       ResultSetMetaData data = res.getMetaData();
/* 62 */       for (int i = 1; i <= data.getColumnCount(); ++i)
/* 63 */         ex.add(
/* 65 */           new DataBaseColumn(data.getColumnName(i).toLowerCase(), 
/* 64 */           data.getColumnLabel(i), 
/* 65 */           (String)this.types
/* 65 */           .get(String.valueOf(data.getColumnType(i)))));
/*    */     }
/*    */     catch (Exception e)
/*    */     {
/* 69 */       e.printStackTrace();
/*    */     } finally {
/* 71 */       JdbcUtils.close(this.connection, stmt, res);
/*    */     }
/* 73 */     return ex;
/*    */   }
/*    */ 
/*    */   public List getTableColumns(String tabName) throws Exception {
/* 77 */     return getResultSetColumns(
/* 78 */       "select * from " + tabName + " where 1<>1");
/*    */   }
/*    */ }

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.console.db.DataBaseStruct
 * JD-Core Version:    0.5.3
 */