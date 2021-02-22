/*    */ package com.ztesoft.crm.report.generator.json;
/*    */ 
/*    */ import com.ztesoft.crm.report.actions.ParameterMap;
/*    */ import com.ztesoft.crm.report.config.elements.Element;
/*    */ import com.ztesoft.crm.report.config.elements.Model;
/*    */ import com.ztesoft.crm.report.config.elements.Panel;
/*    */ import com.ztesoft.crm.report.config.elements.Report;
/*    */ import com.ztesoft.crm.report.db.data_new.DataSet;
/*    */ import com.ztesoft.crm.report.db.model.Actuator;
/*    */ import com.ztesoft.crm.report.db.model.ListtabAcutuator;
/*    */ import com.ztesoft.crm.report.document.Node;
/*    */ import com.ztesoft.crm.report.generator.Generator;
/*    */ import com.ztesoft.crm.report.generator.json.elements.JsonNode;
/*    */ 
/*    */ public class ListtabJsonGenerator extends Generator
/*    */ {
/*    */   @Override
protected Node generateNode(Report report, Element el, ParameterMap map)
/*    */     throws Exception
/*    */   {
/* 43 */     Panel p = (Panel)el;
/* 44 */     Model model = p.getCurrentModel();
/*    */ 
/* 46 */     if (model != null) {
/* 47 */       Actuator actuator = new ListtabAcutuator();
/* 48 */       DataSet ds = actuator.execute(report, p, map);
/* 49 */       JsonNode tab = new JsonNode();
/*    */ 
/* 51 */       tab.setAttribute("page", p.getPage());
/* 52 */       tab.setAttribute("pageSize", p.getPageSize());
/* 53 */       tab.setAttribute("pageIndex", String.valueOf(map.getPageIndex(p.getId())));
/* 54 */       tab.setAttribute("totalRows", String.valueOf(
/* 55 */         ds.getPageData().getTotalRows()));
/* 56 */       tab.setAttribute("totalPage", String.valueOf(
/* 57 */         ds.getPageData().getTotalPage()));
/* 58 */       tab.setAttribute("pageRows", String.valueOf(
/* 59 */         ds.getPageData().getPageRows()));
/*    */ 
/* 61 */       tab.setAttribute("data", ds.getData());
/* 62 */       return tab;
/*    */     }
/* 64 */     return null;
/*    */   }
/*    */ }

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.generator.json.ListtabJsonGenerator
 * JD-Core Version:    0.5.3
 */