/*    */ package com.ztesoft.crm.report.generator.excel;
/*    */ 
/*    */ import com.ztesoft.crm.report.actions.ParameterMap;
/*    */ import com.ztesoft.crm.report.config.elements.Element;
/*    */ import com.ztesoft.crm.report.config.elements.Panel;
/*    */ import com.ztesoft.crm.report.config.elements.Report;
/*    */ import com.ztesoft.crm.report.document.Node;
/*    */ import com.ztesoft.crm.report.generator.Generator;
/*    */ import com.ztesoft.crm.report.generator.excel.elements.ExcelFormNode;
/*    */ import com.ztesoft.crm.report.generator.excel.elements.ExcelNode;
/*    */ import com.ztesoft.crm.report.lang.Utils;
/*    */ 
/*    */ public class FormExcelGenertor extends Generator
/*    */ {
/*    */   @Override
protected Node generateNode(Report report, Element el, ParameterMap map)
/*    */     throws Exception
/*    */   {
/* 42 */     Panel p = (Panel)el;
/* 43 */     if (!(Utils.isTrue(p.getExportEnable()))) {
/* 44 */       return new ExcelNode();
/*    */     }
/*    */ 
/* 47 */     ExcelFormNode node = new ExcelFormNode();
/* 48 */     node.setMap(map);
/* 49 */     node.setPanel(p);
/* 50 */     return node;
/*    */   }
/*    */ }

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.generator.excel.FormExcelGenertor
 * JD-Core Version:    0.5.3
 */