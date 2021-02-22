/*     */ package com.ztesoft.crm.report.generator.html.elements;
/*     */ 
/*     */ import com.ztesoft.crm.report.ReportContext;
/*     */ import com.ztesoft.crm.report.actions.ParameterMap;
/*     */ import com.ztesoft.crm.report.config.elements.Event;
/*     */ import com.ztesoft.crm.report.config.elements.Report;
/*     */ import com.ztesoft.crm.report.document.Document;
/*     */ import com.ztesoft.crm.report.document.Node;
/*     */ import com.ztesoft.crm.report.lang.ArrayListEx;
/*     */ import com.ztesoft.crm.report.lang.Utils;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.OutputStream;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ 
/*     */ public class HTMLDocument extends Document
/*     */ {
/*  26 */   private String layoutObjects = "";
/*  27 */   private String resourcePrifix = "/com/ztesoft/crm/report/resouce";
/*     */   private String title;
/*     */   private String requestParameter;
/*     */   private List events;
/*     */ 
/*     */   private String getRequestParameter()
/*     */   {
/*  32 */     return ((Utils.isEmpty(this.requestParameter)) ? "{}" : this.requestParameter);
/*     */   }
/*     */ 
/*     */   protected HTMLNode createJavaScript(String path)
/*     */   {
/*  44 */     HTMLNode node = new HTMLNode("script");
/*  45 */     node.setAttribute("src", toURL(path));
/*  46 */     node.setAttribute("language", "javascript");
/*  47 */     return node;
/*     */   }
/*     */ 
/*     */   protected ArrayListEx createMetas() {
/*  51 */     ArrayListEx arr = new ArrayListEx();
/*  52 */     HTMLNode meta = new HTMLNode("meta");
/*  53 */     meta.setAttribute("http-equiv", "Content-Type");
/*  54 */     meta.setAttribute("content", "text/html; charset=gbk");
/*  55 */     arr.add(meta);
/*  56 */     return arr;
/*     */   }
/*     */ 
/*     */   protected HTMLNode createStyleSheet(String path) {
/*  60 */     HTMLNode node = new HTMLNode("link");
/*  61 */     node.setAttribute("href", toURL(path));
/*  62 */     node.setAttribute("rel", "stylesheet");
/*  63 */     node.setAttribute("type", "text/css");
/*  64 */     return node;
/*     */   }
/*     */ 
/*     */   private void doEventStart(HTMLNode scripts) {
/*  68 */     for (Iterator es = this.events.iterator(); es.hasNext(); ) {
/*  69 */       Event e = (Event)es.next();
/*  70 */       HTMLFormFieldEvent fe = new HTMLFormFieldEvent();
/*  71 */       fe.setId(e.getParentId());
/*  72 */       fe.setName(e.getName());
/*  73 */       fe.setContent(e.getContent());
/*  74 */       fe.setFunctionName(e.getFunctionName());
/*  75 */       scripts.add(fe);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void finish() {
/*  80 */     HTMLNode html = new HTMLNode("html");
/*  81 */     HTMLNode head = new HTMLNode("head");
/*  82 */     html.add(head);
/*     */ 
/*  84 */     head.addAll(createMetas());
/*  85 */     head.add(new HTMLNode("title", this.title));
/*     */ 
/*  87 */     HTMLNode srp = new HTMLNode("script");
/*  88 */     srp.setAttribute("language", "javascript");
/*  89 */     String ss = 
/*  90 */       Utils.format(
/*  91 */       "window.toURL=function(path,rp){if(!path) path='';if(path.indexOf('/')!=0 && path.length>0) path='/'+path;return ((rp==false)?'{0}':'{1}')+path;};", 
/*  92 */       new String[] { 
/*  93 */       ReportContext.getContext().getAppContextPath(), 
/*  94 */       ReportContext.getContext().getRequestURI("", 
/*  95 */       true) });
/*  96 */     srp.setInnerHTML(
/*  97 */       ss + this.layoutObjects + "\nwindow.request=" + getRequestParameter() + 
/*  98 */       ";");
/*     */ 
/* 100 */     doEventStart(srp);
/* 101 */     head.add(srp);
/* 102 */     loadLib(head);
/*     */ 
/* 104 */     HTMLNode body = new HTMLNode("body");
/* 105 */     HTMLNode div = new HTMLNode("div");
/* 106 */     div.addAll(childs());
/* 107 */     div.setStyle("overflow-x:hidden;overflow-y:auto;");
/* 108 */     body.add(div);
/*     */ 
/* 110 */     body.setStyle("overflow-x", "hidden");
/* 111 */     body.setStyle("overflow-y", "hidden");
/* 112 */     body.setStyle("overflow", "hidden");
/* 113 */     body.setStyle("padding", "0");
/* 114 */     body.setStyle("margin", "0");
/* 115 */     body.setAttribute("onresize", "documentBodyStartResize();");
/* 116 */     body.add(HTMLTags.createScript(
/* 117 */       "Report.onDocumentReady();documentBodyStartResize(true);"));
/*     */ 
/* 120 */     html.add(body);
/*     */ 
/* 122 */     clear();
/* 123 */     add(html);
/*     */   }
/*     */ 
/*     */   @Override
public String getTitle()
/*     */   {
/* 130 */     return this.title;
/*     */   }
/*     */ 
/*     */   @Override
public void init(Report report, ParameterMap map)
/*     */   {
/* 140 */     this.layoutObjects = new HTMLReportConfig().getReportConfig(report);
/* 141 */     this.events = report.getView().getElements(Event.class);
/* 142 */     this.requestParameter = map.getUrlParameter(true);
/*     */   }
/*     */ 
/*     */   protected void loadLib(HTMLNode body)
/*     */   {
/*     */     try {
/* 148 */       body.add(createStyleSheet("/html/report-all.css"));
/* 149 */       body.add(
/* 150 */         createStyleSheet(Utils.format("/html/{0}/theme.css", new String[] { 
/* 151 */         ReportContext.getContext().getDefaultTheme() })));
/* 152 */       BufferedReader in = new BufferedReader(
/* 153 */         new InputStreamReader(super.getClass().getResourceAsStream(
/* 154 */         this.resourcePrifix + 
/* 155 */         "/System.lib")));
/* 156 */       String line = null;
/* 157 */       while ((line = in.readLine()) != null) {
/* 158 */         if (line.trim().length() == 0)
/*     */           continue;
/* 160 */         if (line.toLowerCase().endsWith(".css")) {
/* 161 */           body.add(createStyleSheet(line));
/*     */         }
/* 163 */         if (line.toLowerCase().endsWith(".js")) {
/* 164 */           body.add(createJavaScript(line));
/*     */         }
/*     */       }
/* 167 */       in.close();
/*     */     }
/*     */     catch (Exception localException)
/*     */     {
/*     */     }
/*     */   }
/*     */ 
/*     */   @Override
public void output(OutputStream output, String encoding)
/*     */     throws Exception
/*     */   {
/* 180 */     finish();
/*     */ 
/* 184 */     for (Iterator objs = children(); objs.hasNext(); ) {
/* 185 */       Node n = (Node)objs.next();
/* 186 */       n.output(output, encoding);
/*     */     }
/*     */   }
/*     */ 
/*     */   @Override
public void setTitle(String title)
/*     */   {
/* 195 */     this.title = title;
/*     */   }
/*     */ 
/*     */   private String toURL(String path) {
/* 199 */     return ReportContext.getContext().getRequestURI(
/* 200 */       this.resourcePrifix + path, true);
/*     */   }
/*     */ }

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.generator.html.elements.HTMLDocument
 * JD-Core Version:    0.5.3
 */