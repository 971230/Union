/*    */ package com.ztesoft.crm.report.generator.html;
/*    */ 
/*    */ import com.ztesoft.crm.report.actions.ParameterMap;
/*    */ import com.ztesoft.crm.report.config.elements.Element;
/*    */ import com.ztesoft.crm.report.config.elements.Panel;
/*    */ import com.ztesoft.crm.report.config.elements.Report;
/*    */ import com.ztesoft.crm.report.db.data_new.DataSet;
/*    */ import com.ztesoft.crm.report.db.model.CrosstabActuator;
/*    */ import com.ztesoft.crm.report.document.Node;
/*    */ import com.ztesoft.crm.report.generator.Generator;
/*    */ import com.ztesoft.crm.report.generator.html.elements.HTMLFixedTable;
/*    */ 
/*    */ public class CrosstabHTMLGenerator extends Generator
/*    */ {
/*    */   @Override
protected Node generateNode(Report report, Element el, ParameterMap map)
/*    */     throws Exception
/*    */   {
/* 32 */     HTMLFixedTable node = new HTMLFixedTable(el.getId());
/* 33 */     node.setColumnWidth(((Panel)el).getColumnWidth());
/* 34 */     node.setColumnAlign(((Panel)el).getColumnAlign());
/* 35 */     node.setAttribute("columnMinWidth", ((Panel)el).getColumnMinWidth());
/* 36 */     node.setId(el.getId());
/* 37 */     if (!(map.isNoData()))
/*    */     {
/* 39 */       DataSet ds = new CrosstabActuator().execute(report, (Panel)el, map);
/* 40 */       node.setMetric(((Panel)el).getMetric());
/* 41 */       node.set(ds, map, true);
/*    */     }
/* 43 */     return node;
/*    */   }
/*    */ }

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.generator.html.CrosstabHTMLGenerator
 * JD-Core Version:    0.5.3
 */