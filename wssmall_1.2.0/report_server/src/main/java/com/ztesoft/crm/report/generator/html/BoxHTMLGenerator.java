/*    */ package com.ztesoft.crm.report.generator.html;
/*    */ 
/*    */ import com.ztesoft.crm.report.actions.ParameterMap;
/*    */ import com.ztesoft.crm.report.config.elements.Box;
/*    */ import com.ztesoft.crm.report.config.elements.Element;
/*    */ import com.ztesoft.crm.report.config.elements.Report;
/*    */ import com.ztesoft.crm.report.document.Node;
/*    */ import com.ztesoft.crm.report.generator.Generator;
/*    */ import com.ztesoft.crm.report.generator.html.elements.HTMLBox;
/*    */ 
/*    */ public class BoxHTMLGenerator extends Generator
/*    */ {
/*    */   @Override
protected Node generateNode(Report report, Element el, ParameterMap map)
/*    */     throws Exception
/*    */   {
/* 38 */     Box b = (Box)el;
/* 39 */     HTMLBox box = new HTMLBox();
/* 40 */     box.setHeight(b.getHeight());
/* 41 */     box.setWidth(b.getWidth());
/* 42 */     box.setInnerHTML(b.getContent());
/* 43 */     box.setClassName(b.getCss());
/*    */ 
/* 46 */     box.setId(b.getId());
/* 47 */     return box;
/*    */   }
/*    */ }

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.generator.html.BoxHTMLGenerator
 * JD-Core Version:    0.5.3
 */