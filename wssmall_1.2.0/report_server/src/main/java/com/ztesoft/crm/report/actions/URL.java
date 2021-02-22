/*     */ package com.ztesoft.crm.report.actions;
/*     */ 
/*     */ import com.ztesoft.crm.report.console.ConsoleAction;
/*     */ import com.ztesoft.crm.report.console.ConsoleUiAction;
/*     */ import com.ztesoft.crm.report.log.ReportLogger;
/*     */ 
/*     */ public class URL
/*     */ {
/*     */   private String actionName;
/*     */   private String contentType;
/*     */   private String url;
/*     */ 
/*     */   public URL(String url)
/*     */   {
/*  24 */     this.url = url;
/*  25 */     this.actionName = ReportAction.class.getName();
/*     */ 
/*  27 */     this.contentType = "text/html";
/*     */ 
/*  31 */     if (url.startsWith("/console")) {
/*  32 */       if ((url.equalsIgnoreCase("/console")) || (url.endsWith(".jsp"))) {
/*  33 */         this.contentType = "text/html";
/*  34 */         this.actionName = ConsoleUiAction.class.getName();
/*     */       }
/*  36 */       if ((url.startsWith("/console")) && (url.endsWith(".do"))) {
/*  37 */         this.contentType = "application/json";
/*     */ 
/*  39 */         this.actionName = ConsoleAction.class.getName();
/*     */       }
/*     */     }
/*     */ 
/*  43 */     if ((url.toLowerCase().startsWith("/report")) && 
/*  44 */       (url.toLowerCase().endsWith(".jsp"))) {
/*  45 */       this.contentType = "text/html";
/*  46 */       this.actionName = JSPAction.class.getName();
/*     */     }
/*     */ 
/*  49 */     if (url.toLowerCase().endsWith(".swf")) {
/*  50 */       this.contentType = "application/x-shockwave-flash";
/*  51 */       this.actionName = ResourceAction.class.getName();
/*     */     }
/*  53 */     if (url.toLowerCase().endsWith(".css")) {
/*  54 */       this.contentType = "text/css";
/*  55 */       this.actionName = ResourceAction.class.getName();
/*     */     }
/*  57 */     if (url.toLowerCase().endsWith(".js")) {
/*  58 */       this.contentType = "text/javascript";
/*  59 */       this.actionName = ResourceAction.class.getName();
/*     */     }
/*  61 */     if (url.toLowerCase().endsWith(".gif")) {
/*  62 */       this.contentType = "image/gif";
/*  63 */       if (!(this.url.startsWith("/com/ztesoft/"))) {
/*  64 */         this.url = 
/*  65 */           "/com/ztesoft/crm/report/resouce/html" + this.url;
/*     */       }
/*     */ 
/*  68 */       this.actionName = ResourceAction.class.getName();
/*     */     }
/*  70 */     if (url.toLowerCase().endsWith(".png")) {
/*  71 */       this.contentType = "image/png";
/*  72 */       if (!(this.url.startsWith("/com/ztesoft/"))) {
/*  73 */         this.url = 
/*  74 */           "/com/ztesoft/crm/report/resouce/html" + this.url;
/*     */       }
/*     */ 
/*  77 */       this.actionName = ResourceAction.class.getName();
/*     */     }
/*  79 */     if (url.toLowerCase().endsWith(".xml")) {
/*  80 */       this.contentType = "text/xml";
/*  81 */       this.actionName = ReportXMLAction.class.getName();
/*     */     }
/*  83 */     if (url.toLowerCase().endsWith(".zip"))
/*     */     {
/*  85 */       this.contentType = "application/octet-stream";
/*  86 */       this.actionName = ReportZipAction.class.getName();
/*     */     }
/*  88 */     if (url.toLowerCase().endsWith(".st")) {
/*  89 */       this.contentType = "text/html";
/*  90 */       this.actionName = StaticDataAction.class.getName();
/*     */     }
/*     */ 
/*  93 */     if (url.toLowerCase().endsWith(".ew")) {
/*  94 */       this.contentType = "text/html";
/*  95 */       this.actionName = ReportPromptAction.class.getName();
/*     */     }
/*  97 */     ReportLogger.getLogger().debug("请求路径：{0}", url);
/*     */   }
/*     */ 
/*     */   public final String getActionName()
/*     */   {
/* 105 */     return this.actionName;
/*     */   }
/*     */ 
/*     */   public final String getContentType()
/*     */   {
/* 112 */     return this.contentType;
/*     */   }
/*     */ 
/*     */   public String getUrl()
/*     */   {
/* 119 */     return this.url;
/*     */   }
/*     */ 
/*     */   @Override
public String toString()
/*     */   {
/* 129 */     return this.url;
/*     */   }
/*     */ }

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.actions.URL
 * JD-Core Version:    0.5.3
 */