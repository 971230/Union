/*    */ package com.ztesoft.crm.report.generator.html;
/*    */ 
/*    */ import com.ztesoft.crm.report.ReportContext;
/*    */ import com.ztesoft.crm.report.actions.ParameterMap;
/*    */ import com.ztesoft.crm.report.config.elements.Element;
/*    */ import com.ztesoft.crm.report.config.elements.Panel;
/*    */ import com.ztesoft.crm.report.config.elements.Report;
/*    */ import com.ztesoft.crm.report.document.Node;
/*    */ import com.ztesoft.crm.report.generator.Generator;
/*    */ import com.ztesoft.crm.report.generator.html.elements.HTMLChartFlash;
/*    */ import java.net.URLEncoder;
/*    */ 
/*    */ public class ChartHTMLGenerator extends Generator
/*    */ {
/*    */   @Override
protected Node generateNode(Report report, Element el, ParameterMap map)
/*    */     throws Exception
/*    */   {
/* 42 */     HTMLChartFlash flash = new HTMLChartFlash();
/* 43 */     flash.setId(el.getId());
/* 44 */     flash.setParameter("url", URLEncoder.encode(
/* 45 */       ReportContext.getContext().getRequestURI(report.getPath(), true), "utf8"));
/* 46 */     flash.setParameter("axisCn", ((Panel)el).getAxisCn());
/* 47 */     flash.setParameter("autoLoad", String.valueOf(!(map.isNoData())));
/* 48 */     flash.setParameter("reportPanelId", el.getId());
/* 49 */     flash.setParameter("replyType", "chart");
/* 50 */     flash.setParameter("replyEncoding", "utf8");
/* 51 */     flash.setParameter("requestEncoding", "utf8");
/* 52 */     return flash;
/*    */   }
/*    */ }

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.generator.html.ChartHTMLGenerator
 * JD-Core Version:    0.5.3
 */