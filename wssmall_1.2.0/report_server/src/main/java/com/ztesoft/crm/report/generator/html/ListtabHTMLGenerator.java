/*    */ package com.ztesoft.crm.report.generator.html;
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
/*    */ import com.ztesoft.crm.report.generator.html.elements.HTMLFixedTable;
/*    */ 
/*    */ public class ListtabHTMLGenerator extends Generator
/*    */ {
/*    */   @Override
protected Node generateNode(Report report, Element el, ParameterMap map)
/*    */     throws Exception
/*    */   {
/* 42 */     Panel p = (Panel)el;
/* 43 */     Model model = p.getCurrentModel();
/*    */ 
/* 46 */     if (model != null)
/*    */     {
/* 48 */       HTMLFixedTable tab = new HTMLFixedTable(el.getId());
/* 49 */       tab.setId(el.getId());
/* 50 */       tab.setColumnAlign(p.getColumnAlign());
/* 51 */       tab.setColumnWidth(p.getColumnWidth());
/* 52 */       tab
/* 53 */         .setAttribute("columnMinWidth", 
/* 54 */         ((Panel)el).getColumnMinWidth());
/*    */ 
/* 57 */       tab.setAttribute("totalRows", "0");
/* 58 */       tab.setAttribute("totalPage", "0");
/* 59 */       tab.setAttribute("pageRows", "0");
/* 60 */       tab.setAttribute("pageIndex", String.valueOf(map.getPageIndex(p.getId())));
/* 61 */       tab.setAttribute("page", p.getPage());
/* 62 */       tab.setAttribute("totalRows", "0");
/* 63 */       tab.setAttribute("totalPage", "0");
/* 64 */       tab.setAttribute("pageRows", p.getPageSize());
/* 65 */       if (!(map.isNoData())) {
/* 66 */         Actuator actuator = new ListtabAcutuator();
/*    */ 
/* 69 */         DataSet ds = actuator.execute(report, p, map);
/*    */ 
/* 71 */         if (ds.getPageData() != null) {
/* 72 */           tab.setAttribute("totalRows", String.valueOf(
/* 73 */             ds.getPageData().getTotalRows()));
/* 74 */           tab.setAttribute("totalPage", String.valueOf(
/* 75 */             ds.getPageData().getTotalPage()));
/* 76 */           tab.setAttribute("pageRows", String.valueOf(
/* 77 */             ds.getPageData().getPageRows()));
/*    */         }
/* 79 */         tab.set(ds, map, true);
/*    */       }
/*    */ 
/* 82 */       tab.setAttribute("pageSize", String.valueOf(map.getPageSize(p.getId())));
/* 83 */       return tab;
/*    */     }
/* 85 */     return null;
/*    */   }
/*    */ }

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.generator.html.ListtabHTMLGenerator
 * JD-Core Version:    0.5.3
 */