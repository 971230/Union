/*     */ package com.ztesoft.crm.report.config.elements;
/*     */ 
/*     */ import com.ztesoft.crm.report.lang.ArrayListEx;
/*     */ import java.util.List;
/*     */ 
/*     */ public final class Model extends Element
/*     */ {
/*     */   private static final long serialVersionUID = 8583744574956728662L;
/*     */   private String reportPath;
/*     */   private String dataSource;
/*     */   private List joins;
/*     */   private List tables;
/*  41 */   private String type = "two";
/*     */   private Conditions conditions;
/*     */ 
/*     */   public static long getSerialVersionUID()
/*     */   {
/*  25 */     return 8583744574956728662L;
/*     */   }
/*     */ 
/*     */   public String getReportPath()
/*     */   {
/*  31 */     return this.reportPath;
/*     */   }
/*     */ 
/*     */   public void setReportPath(String reportPath) {
/*  35 */     this.reportPath = reportPath;
/*     */   }
/*     */ 
/*     */   public String getDataSource()
/*     */   {
/*  54 */     return this.dataSource;
/*     */   }
/*     */ 
/*     */   public List getJoins()
/*     */   {
/*  61 */     return this.joins;
/*     */   }
/*     */ 
/*     */   public List getTables()
/*     */   {
/*  68 */     return this.tables;
/*     */   }
/*     */ 
/*     */   public String getType()
/*     */   {
/*  75 */     return this.type;
/*     */   }
/*     */ 
/*     */   public Conditions getConditions()
/*     */   {
/*  81 */     return this.conditions;
/*     */   }
/*     */ 
/*     */   @Override
protected void init(Element top)
/*     */   {
/*  93 */     this.tables = new ArrayListEx();
/*  94 */     this.joins = new ArrayListEx();
/*  95 */     this.tables.addAll(getChildElements(Table.class));
/*  96 */     this.joins.addAll(getChildElements(Join.class));
/*  97 */     this.conditions = ((Conditions)getChildElement(Conditions.class));
/*     */   }
/*     */ 
/*     */   public void setDataSource(String dataSource)
/*     */   {
/* 106 */     this.dataSource = dataSource;
/*     */   }
/*     */ 
/*     */   public void setType(String type)
/*     */   {
/* 114 */     this.type = type;
/*     */   }
/*     */ }

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.config.elements.Model
 * JD-Core Version:    0.5.3
 */