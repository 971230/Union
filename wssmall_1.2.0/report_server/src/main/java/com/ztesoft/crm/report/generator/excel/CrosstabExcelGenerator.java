/*    */ package com.ztesoft.crm.report.generator.excel;
/*    */ 
/*    */ import com.ztesoft.crm.report.actions.ParameterMap;
/*    */ import com.ztesoft.crm.report.config.elements.Element;
/*    */ import com.ztesoft.crm.report.config.elements.Panel;
/*    */ import com.ztesoft.crm.report.config.elements.Report;
/*    */ import com.ztesoft.crm.report.db.data_new.DataSet;
/*    */ import com.ztesoft.crm.report.db.model.CrosstabActuator;
/*    */ import com.ztesoft.crm.report.document.Node;
/*    */ import com.ztesoft.crm.report.generator.Generator;
/*    */ import com.ztesoft.crm.report.generator.excel.elements.ExcelDataSetNode;
/*    */ 
/*    */ public class CrosstabExcelGenerator extends Generator
/*    */ {
/*    */   @Override
protected Node generateNode(Report report, Element el, ParameterMap map)
/*    */     throws Exception
/*    */   {
/* 35 */     DataSet ds = new CrosstabActuator().execute(report, (Panel)el, map);
/* 36 */     ExcelDataSetNode en = new ExcelDataSetNode();
/* 37 */     en.setColumnAlign(((Panel)el).getColumnAlign());
/* 38 */     en.setTitle(((Panel)el).getTitle());
/* 39 */     en.setDataSet(ds);
/* 40 */     return en;
/*    */   }
/*    */ }

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.generator.excel.CrosstabExcelGenerator
 * JD-Core Version:    0.5.3
 */