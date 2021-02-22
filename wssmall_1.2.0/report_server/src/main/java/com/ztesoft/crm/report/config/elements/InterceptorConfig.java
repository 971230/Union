/*     */ package com.ztesoft.crm.report.config.elements;
/*     */ 
/*     */ import com.ztesoft.crm.report.lang.Utils;
/*     */ 
/*     */ public class InterceptorConfig extends Element
/*     */ {
/*     */   public static final String SQL = "sql";
/*     */   public static final String FUNCTION = "function";
/*     */   public static final String PROCEDURE = "procedure";
/*     */   public static final String CLASS = "class";
/*     */   public static final String REPORT_ACTIVATE = "report";
/*     */   public static final String PANEL_ACTIVATE = "panel";
/*     */   public static final String ALL = "all";
/*     */   private String returnType;
/*     */   private static final long serialVersionUID = -4905321389620196067L;
/*     */   private String type;
/*     */   private String name;
/*     */   private String impl;
/*  78 */   private String activateStyle = "report";
/*  79 */   private String scope = "html";
/*     */   private String dataSource;
/*     */ 
/*     */   public String getReturnType()
/*     */   {
/*  25 */     return this.returnType;
/*     */   }
/*     */ 
/*     */   public void setReturnType(String returnType) {
/*  29 */     this.returnType = returnType;
/*     */   }
/*     */ 
/*     */   @Override
public String toString()
/*     */   {
/*  38 */     return this.impl;
/*     */   }
/*     */ 
/*     */   public String getType()
/*     */   {
/*  51 */     return this.type;
/*     */   }
/*     */ 
/*     */   public void setType(String type) {
/*  55 */     this.type = type;
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/*  61 */     return this.name;
/*     */   }
/*     */ 
/*     */   public void setName(String name) {
/*  65 */     this.name = name;
/*     */   }
/*     */ 
/*     */   public String getImpl()
/*     */   {
/*  71 */     return this.impl;
/*     */   }
/*     */ 
/*     */   public void setImpl(String impl) {
/*  75 */     this.impl = impl;
/*     */   }
/*     */ 
/*     */   public String getScope()
/*     */   {
/*  82 */     return this.scope;
/*     */   }
/*     */ 
/*     */   public void setScope(String scope) {
/*  86 */     this.scope = scope;
/*     */   }
/*     */ 
/*     */   public String getActivateStyle() {
/*  90 */     return this.activateStyle;
/*     */   }
/*     */ 
/*     */   public void setActivateStyle(String activateStyle) {
/*  94 */     this.activateStyle = activateStyle;
/*     */   }
/*     */ 
/*     */   public String getDataSource()
/*     */   {
/*  99 */     return this.dataSource;
/*     */   }
/*     */ 
/*     */   public void setDataSource(String dataSource) {
/* 103 */     this.dataSource = dataSource;
/*     */   }
/*     */ 
/*     */   @Override
public boolean equals(Object obj)
/*     */   {
/* 109 */     if (obj == null)
/* 110 */       return false;
/* 111 */     if (obj instanceof InterceptorConfig) {
/* 112 */       InterceptorConfig ic = (InterceptorConfig)obj;
/* 113 */       if (Utils.isEmpty(ic.name)) {
/* 114 */         return false;
/*     */       }
/* 116 */       return ic.name.equals(this.name);
/*     */     }
/*     */ 
/* 119 */     return false;
/*     */   }
/*     */ 
/*     */   @Override
protected void init(Element top)
/*     */   {
/* 125 */     Impl il = (Impl)getChildElement(Impl.class);
/* 126 */     if (il != null)
/* 127 */       this.impl = il.getContent();
/*     */   }
/*     */ }

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.config.elements.InterceptorConfig
 * JD-Core Version:    0.5.3
 */