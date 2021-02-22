/*    */ package com.ztesoft.crm.report.generator.excel;
/*    */ 
/*    */ import com.ztesoft.crm.report.actions.ParameterMap;
/*    */ import com.ztesoft.crm.report.config.elements.Element;
/*    */ import com.ztesoft.crm.report.config.elements.Panel;
/*    */ import com.ztesoft.crm.report.config.elements.Report;
/*    */ import com.ztesoft.crm.report.db.data_new.DataSet;
/*    */ import com.ztesoft.crm.report.db.model.ListtabAcutuator;
/*    */ import com.ztesoft.crm.report.document.Node;
/*    */ import com.ztesoft.crm.report.generator.Generator;
/*    */ import com.ztesoft.crm.report.generator.excel.elements.ExcelDataSetNode;
/*    */ 
/*    */ public class ListtabExcelGenerator extends Generator
/*    */ {
/*    */   @Override
protected Node generateNode(Report report, Element el, ParameterMap map)
/*    */     throws Exception
/*    */   {
/* 42 */     map.setExport(true);
/* 43 */     map.setMaxRows(8000);
/*    */ 
/* 45 */     DataSet ds = new ListtabAcutuator().execute(report, (Panel)el, map);
/* 46 */     ExcelDataSetNode node = new ExcelDataSetNode();
/* 47 */     node.setColumnAlign(((Panel)el).getColumnAlign());
/* 48 */     node.setTitle(((Panel)el).getTitle());
/* 49 */     node.setDataSet(ds);
/* 50 */     return node;
/*    */   }
/*    */ }

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.generator.excel.ListtabExcelGenerator
 * JD-Core Version:    0.5.3
 */