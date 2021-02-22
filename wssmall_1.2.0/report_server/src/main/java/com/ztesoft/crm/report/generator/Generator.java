/*    */ package com.ztesoft.crm.report.generator;
/*    */ 
/*    */ import com.ztesoft.crm.report.actions.ParameterMap;
/*    */ import com.ztesoft.crm.report.config.elements.Box;
/*    */ import com.ztesoft.crm.report.config.elements.Element;
/*    */ import com.ztesoft.crm.report.config.elements.Panel;
/*    */ import com.ztesoft.crm.report.config.elements.Report;
/*    */ import com.ztesoft.crm.report.document.Node;
/*    */ import java.util.Iterator;
/*    */ 
/*    */ public abstract class Generator
/*    */ {
/*    */   public final Node generate(Report report, Element el, ParameterMap map)
/*    */     throws Exception
/*    */   {
/* 31 */     Node n = generateNode(report, el, map);
/* 32 */     if (n != null)
/* 33 */       generateChild(n, report, el, map);
/* 34 */     return n;
/*    */   }
/*    */ 
/*    */   private final void generateChild(Node node, Report report, Element el, ParameterMap map)
/*    */     throws Exception
/*    */   {
/* 40 */     for (Iterator objs = el.childs().iterator(); objs.hasNext(); ) {
/* 41 */       Element o = (Element)objs.next();
/* 42 */       if ((o instanceof Box) || (o instanceof Panel))
/* 43 */         node.add(GeneratorFactory.generate(report, o, map));
/*    */     }
/*    */   }
/*    */ 
/*    */   protected abstract Node generateNode(Report paramReport, Element paramElement, ParameterMap paramParameterMap)
/*    */     throws Exception;
/*    */ }

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.generator.Generator
 * JD-Core Version:    0.5.3
 */