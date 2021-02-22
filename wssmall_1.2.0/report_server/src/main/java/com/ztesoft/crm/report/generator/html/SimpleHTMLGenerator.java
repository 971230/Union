/*    */ package com.ztesoft.crm.report.generator.html;
/*    */ 
/*    */ import com.ztesoft.crm.report.ReportContext;
/*    */ import com.ztesoft.crm.report.actions.ParameterMap;
/*    */ import com.ztesoft.crm.report.config.elements.Element;
/*    */ import com.ztesoft.crm.report.config.elements.Panel;
/*    */ import com.ztesoft.crm.report.config.elements.Report;
/*    */ import com.ztesoft.crm.report.document.Node;
/*    */ import com.ztesoft.crm.report.generator.Generator;
/*    */ import com.ztesoft.crm.report.generator.html.elements.HTMLChartSimpleFlash;
/*    */ import com.ztesoft.crm.report.lang.Utils;
/*    */ import java.net.URLEncoder;
/*    */ 
/*    */ public class SimpleHTMLGenerator extends Generator
/*    */ {
/*    */   @Override
protected Node generateNode(Report report, Element el, ParameterMap map)
/*    */     throws Exception
/*    */   {
/* 43 */     HTMLChartSimpleFlash flash = new HTMLChartSimpleFlash();
/* 44 */     flash.setId(el.getId());
/* 45 */     flash.setParameter("url", URLEncoder.encode(
/* 46 */       ReportContext.getContext().getRequestURI(report.getPath(), true), "utf8"));
/* 47 */     flash.setParameter("axisCn", ((Panel)el).getAxisCn());
/* 48 */     flash.setParameter("autoLoad", String.valueOf(!(map.isNoData())));
/* 49 */     flash.setParameter("reportPanelId", el.getId());
/* 50 */     flash.setParameter("replyType", "chart");
/* 51 */     flash.setParameter("replyEncoding", "utf8");
/* 52 */     flash.setParameter("requestEncoding", "utf8");
/* 53 */     flash.setParameter("type", ((Panel)el).getDefaultChartType());
/* 54 */     flash.setParameter("legendFormat", ((Panel)el).getLegendFormat());
/* 55 */     flash.setParameter("labelPosition", (Utils.isEmpty(((Panel)el).getLabelPosition())) ? "none" : ((Panel)el).getLabelPosition());
/* 56 */     flash.setParameter("labelFormat", ((Panel)el).getLabelFormat());
/* 57 */     return flash;
/*    */   }
/*    */ }

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.generator.html.SimpleHTMLGenerator
 * JD-Core Version:    0.5.3
 */