/*     */ package com.ztesoft.crm.report.interfaces.prompt;
/*     */ 
/*     */ import com.ztesoft.crm.report.lang.ArrayListEx;
/*     */ import com.ztesoft.crm.report.lang.ObjectPropertyUtils;
/*     */ import java.io.Serializable;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ 
/*     */ public final class PromptConfig
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1719436713064957715L;
/*     */   private String columnName;
/*     */   private String styleName;
/*     */   private String styleValue;
/*  50 */   private List matchs = new ArrayListEx();
/*     */   private String columnText;
/*     */ 
/*     */   public PromptConfig()
/*     */   {
/*     */   }
/*     */ 
/*     */   public PromptConfig(Object o)
/*     */   {
/*  32 */     ObjectPropertyUtils.copyProperties(this, o, null);
/*  33 */     for (Iterator objs = ((List)ObjectPropertyUtils.getProperty(o, "matchers")).iterator(); objs.hasNext(); )
/*  34 */       addMatcher(new PromptMatcher(objs.next()));
/*     */   }
/*     */ 
/*     */   @Override
public String toString()
/*     */   {
/*  42 */     return getMatchExpression();
/*     */   }
/*     */ 
/*     */   public PromptMatcher[] matchers()
/*     */   {
/*  53 */     return ((PromptMatcher[])this.matchs
/*  54 */       .toArray(new PromptMatcher[this.matchs.size()]));
/*     */   }
/*     */ 
/*     */   public List getMatchers() {
/*  58 */     return this.matchs;
/*     */   }
/*     */ 
/*     */   public void addMatcher(PromptMatcher matcher) {
/*  62 */     this.matchs.add(matcher);
/*     */   }
/*     */ 
/*     */   public String getColumnText()
/*     */   {
/*  68 */     return this.columnText;
/*     */   }
/*     */ 
/*     */   public void setColumnText(String columnText) {
/*  72 */     this.columnText = columnText;
/*     */   }
/*     */ 
/*     */   public String getColumnName() {
/*  76 */     return this.columnName;
/*     */   }
/*     */ 
/*     */   public void setColumnName(String columnName) {
/*  80 */     this.columnName = columnName;
/*     */   }
/*     */ 
/*     */   public String getStyleName() {
/*  84 */     return this.styleName;
/*     */   }
/*     */ 
/*     */   public void setStyleName(String styleName) {
/*  88 */     this.styleName = styleName;
/*     */   }
/*     */ 
/*     */   public String getStyleValue() {
/*  92 */     return this.styleValue;
/*     */   }
/*     */ 
/*     */   public void setStyleValue(String styleValue) {
/*  96 */     this.styleValue = styleValue;
/*     */   }
/*     */ 
/*     */   public String getMatchExpression() {
/* 100 */     StringBuffer sb = new StringBuffer();
/* 101 */     sb.append("foreach(");
/* 102 */     sb.append("var v in getRange('").append(this.columnName).append(
/* 103 */       "',null)");
/*     */ 
/* 105 */     sb.append("){");
/* 106 */     sb.append("if(");
/* 107 */     for (Iterator iterator = this.matchs.iterator(); iterator.hasNext(); ) {
/* 108 */       PromptMatcher m = (PromptMatcher)iterator.next();
/* 109 */       sb.append("value(v)").append(m.getOperator()).append("'").append(
/* 110 */         m.getValue()).append("' ");
/* 111 */       if (iterator.hasNext()) {
/* 112 */         sb.append(m.getRelatType()).append(" ");
/*     */       }
/*     */     }
/*     */ 
/* 116 */     sb.append("){");
/* 117 */     sb.append("setStyle(v,'").append(getStyleName()).append("','").append(
/* 118 */       getStyleValue()).append("');");
/* 119 */     sb.append("};}");
/* 120 */     return sb.toString();
/*     */   }
/*     */ }

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.interfaces.prompt.PromptConfig
 * JD-Core Version:    0.5.3
 */