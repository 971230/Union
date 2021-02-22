/*     */ package com.ztesoft.crm.report.actions;
/*     */ 
/*     */ import com.ztesoft.crm.report.ReportContext;
/*     */ import com.ztesoft.crm.report.ReportFactory;
/*     */ import com.ztesoft.crm.report.ReportSession;
/*     */ import com.ztesoft.crm.report.config.elements.Element;
/*     */ import com.ztesoft.crm.report.config.elements.InterceptorConfig;
/*     */ import com.ztesoft.crm.report.config.elements.InterceptorsConfig;
/*     */ import com.ztesoft.crm.report.config.elements.Report;
/*     */ import com.ztesoft.crm.report.core.InterceptorInvoker;
/*     */ import com.ztesoft.crm.report.db.getter.ReportParameterSetter;
/*     */ import com.ztesoft.crm.report.document.Document;
/*     */ import com.ztesoft.crm.report.document.Node;
/*     */ import com.ztesoft.crm.report.generator.GeneratorFactory;
/*     */ import com.ztesoft.crm.report.lang.ArrayListEx;
/*     */ import com.ztesoft.crm.report.lang.Utils;
/*     */ import com.ztesoft.crm.report.log.ReportLogger;
/*     */ import com.ztesoft.crm.report.storage.StorageStreamFactory;

/*     */ import java.io.OutputStream;
/*     */ import java.text.MessageFormat;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;

import org.apache.log4j.Logger;
/*     */ 
/*     */ public class ReportAction extends AbstractAction
/*     */ {private static Logger logger = Logger.getLogger(ReportAction.class);
/*     */   private List getInterceptors(Report report, String activateStyle, String scope)
/*     */   {
/*  44 */     InterceptorsConfig ints = ReportContext.getContext()
/*  45 */       .getInterceptorsConfig();
/*  46 */     List arr = new ArrayListEx();
/*  47 */     HashMap temp = new HashMap();
/*     */     Iterator objs;
/*     */     InterceptorConfig config;
/*  49 */     if (ints != null) {
/*  50 */       for (objs = ints.childs().iterator(); objs.hasNext(); ) {
/*  51 */         config = (InterceptorConfig)objs.next();
/*  52 */         if ((!("all".equals(config.getActivateStyle()))) && 
/*  53 */           (!(activateStyle.equals(config.getActivateStyle())))) {
/*     */           continue;
/*     */         }
/*     */ 
/*  57 */         if ((!("all".equals(config.getScope()))) && 
/*  58 */           (!(scope.equals(config.getScope())))) {
/*     */           continue;
/*     */         }
/*     */ 
/*  62 */         arr.add(config);
/*  63 */         temp.put(config.getName(), config);
/*     */       }
/*     */     }
/*     */ 
/*  67 */     ints = (InterceptorsConfig)report.getElement(InterceptorsConfig.class);
/*     */ 
/*  69 */     if (ints != null) {
/*  70 */       if (Utils.isFalse(ints.getExtend())) {
/*  71 */         arr.clear();
/*  72 */         temp.clear();
/*     */       }
/*  74 */       for (objs = ints.childs().iterator(); objs.hasNext(); ) {
/*  75 */         config = (InterceptorConfig)objs.next();
/*  76 */         if ((!("all".equals(config.getActivateStyle()))) && 
/*  77 */           (!(activateStyle.equals(config.getActivateStyle())))) {
/*     */           continue;
/*     */         }
/*     */ 
/*  81 */         if ((!("all".equals(config.getScope()))) && 
/*  82 */           (!(scope.equals(config.getScope())))) {
/*     */           continue;
/*     */         }
/*     */ 
/*  86 */         if ((temp.containsKey(config.getName())) && 
/*  87 */           (Utils.isTrue(ints.getReplace()))) {
/*  88 */           InterceptorConfig ic = (InterceptorConfig)temp.get(
/*  89 */             config.getName());
/*  90 */           arr.remove(ic);
/*     */         }
/*     */ 
/*  93 */         arr.add(config);
/*  94 */         temp.put(config.getName(), config);
/*     */       }
/*     */     }
/*  97 */     temp.clear();
/*     */ 
/*  99 */     return arr;
/*     */   }
/*     */ 
/*     */   @Override
protected void execute(OutputStream out, ParameterMap map)
/*     */     throws Exception
/*     */   {
/* 113 */     ReportLogger.getLogger().debug("报表路径：{0}", map.getUrl());
/* 114 */     Report report = ReportFactory.getInstance().getReport(
/* 115 */       map.getUrl(), 
/* 116 */       StorageStreamFactory.createStream().getReport(
/* 117 */       getContext(), 
/* 118 */       map.getUrl() + ".xml"));
/*     */ 
/* 121 */     Element el = null;
/* 122 */     if (report == null) {
/* 123 */       throw new Exception(
/* 124 */         MessageFormat.format("报表[0]不存在", 
/* 124 */         new String[] { map.getUrl() + ".xml" }));
/*     */     }
/*     */ 
/* 127 */     if (map.getReportPanelId() != null) {
/* 128 */       el = report.getElement(map.getReportPanelId());
/*     */     }
/* 130 */     ReportLogger.getLogger().debug("请求类型：{0}", map.getReplyType());
/* 131 */     long start = System.currentTimeMillis();
/* 132 */     List interceptors = null;
/* 133 */     InterceptorInvoker ii = new InterceptorInvoker();
/*     */     try
/*     */     {
/* 136 */       map.putAllEmpty(new ReportParameterSetter()
/* 137 */         .getParameters(report, 
/* 137 */         getContext(), getRequest()));
/*     */ 
/* 139 */       if ("excel".equals(map.getReplyType())) {
/* 140 */         getResponse().setHeader(
/* 141 */           "Content-Disposition", 
/* 142 */           "attachment;filename=" + 
/* 143 */           Utils.encode(report.getTitle(), 
/* 144 */           map.getReplyEncoding(), "ISO-8859-1") + 
/* 145 */           ".xls");
/*     */       }
/*     */ 
/* 149 */       ReportSession.setReport(report);
/*     */ 
/* 151 */       if (el == null) {
/* 152 */         interceptors = getInterceptors(report, 
/* 153 */           "report", map.getReplyType());
/*     */ 
/* 155 */         if (!(ii.doStart(getContext(), getRequest(), report, map, 
/* 156 */           interceptors))) {
/*     */           return;
/*     */         }
/* 159 */         if (("html".equals(map.getReplyType())) && 
/* 161 */           (Utils.isFalse(report.getAutoLoad()))) {
/* 162 */           map.setNoData(true);
/*     */         }
/*     */ 
/* 166 */         Document doc = GeneratorFactory.generate(report, map);
/* 167 */         doc.setTitle(report.getTitle());
/* 168 */         doc.output(out, map.getReplyEncoding());
/* 169 */         clear(doc);
/*     */       } else {
/* 171 */         interceptors = getInterceptors(report, 
/* 172 */           "panel", map.getReplyType());
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*     */     }
/*     */     finally
/*     */     {
/*     */       Node node;
/* 192 */       ii.release();
/* 193 */       ReportSession.release();
/* 194 */       if (interceptors != null)
/* 195 */         interceptors.clear();
/* 196 */       map.clear();
/*     */ 
/* 198 */       if ("html".equals(map.getReplyType()))
/*     */       {
/* 200 */         logger.info("当前报表时间:" + 
/* 201 */           (System.currentTimeMillis() - start));
/*     */       }
/*     */     }
/* 192 */     ii.release();
/* 193 */     ReportSession.release();
/* 194 */     if (interceptors != null)
/* 195 */       interceptors.clear();
/* 196 */     map.clear();
/*     */ 
/* 198 */     if (!("html".equals(map.getReplyType())))
/*     */       return;
/* 200 */     logger.info("当前报表时间:" + 
/* 201 */       (System.currentTimeMillis() - start));
/*     */   }
/*     */ 
/*     */   private void clear(Document doc)
/*     */   {
/* 207 */     for (Iterator objs = doc.childs().iterator(); objs.hasNext(); ) {
/* 208 */       Node node = (Node)objs.next();
/* 209 */       clear(node);
/*     */     }
/* 211 */     doc.clear();
/*     */   }
/*     */ 
/*     */   private void clear(Node node) {
/* 215 */     for (Iterator objs = node.childs().iterator(); objs.hasNext(); ) {
/* 216 */       Node n = (Node)objs.next();
/* 217 */       clear(n);
/*     */     }
/* 219 */     node.clear();
/*     */   }
/*     */ }

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.actions.ReportAction
 * JD-Core Version:    0.5.3
 */