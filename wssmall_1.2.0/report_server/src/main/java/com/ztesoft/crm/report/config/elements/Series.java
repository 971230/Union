/*     */ package com.ztesoft.crm.report.config.elements;
/*     */ 
/*     */ public class Series extends Element
/*     */ {
/*     */   private static final long serialVersionUID = 8873690189002251236L;
/*     */   private String column;
/*     */   private String displayName;
/*     */   private String field;
/*     */   private Column label;
/*     */   private String labelColumn;
/*     */   private String labelField;
/*     */   private String labelPosition;
/*     */   private Column value;
/*     */ 
/*     */   public static long getSerialVersionUID()
/*     */   {
/*  20 */     return 8873690189002251236L;
/*     */   }
/*     */ 
/*     */   public String getColumn()
/*     */   {
/*  43 */     return this.column;
/*     */   }
/*     */ 
/*     */   public String getDisplayName()
/*     */   {
/*  50 */     return this.displayName;
/*     */   }
/*     */ 
/*     */   public String getField()
/*     */   {
/*  57 */     return this.field;
/*     */   }
/*     */ 
/*     */   public Column getLabel()
/*     */   {
/*  64 */     return this.label;
/*     */   }
/*     */ 
/*     */   public String getLabelColumn()
/*     */   {
/*  71 */     return this.labelColumn;
/*     */   }
/*     */ 
/*     */   public String getLabelField()
/*     */   {
/*  78 */     return this.labelField;
/*     */   }
/*     */ 
/*     */   public String getLabelPosition()
/*     */   {
/*  85 */     return this.labelPosition;
/*     */   }
/*     */ 
/*     */   public Column getValue()
/*     */   {
/*  92 */     return this.value;
/*     */   }
/*     */ 
/*     */   @Override
protected void init(Element top)
/*     */   {
/* 104 */     this.value = ((Column)top.getElement(this.column));
/* 105 */     this.label = ((Column)top.getElement(this.labelColumn));
/*     */   }
/*     */ 
/*     */   public void setColumn(String column)
/*     */   {
/* 113 */     this.column = column;
/*     */   }
/*     */ 
/*     */   public void setDisplayName(String displayName)
/*     */   {
/* 121 */     this.displayName = displayName;
/*     */   }
/*     */ 
/*     */   public void setField(String field)
/*     */   {
/* 129 */     this.field = field;
/*     */   }
/*     */ 
/*     */   public void setLabelColumn(String labelColumn)
/*     */   {
/* 137 */     this.labelColumn = labelColumn;
/*     */   }
/*     */ 
/*     */   public void setLabelField(String labelField)
/*     */   {
/* 145 */     this.labelField = labelField;
/*     */   }
/*     */ 
/*     */   public void setLabelPosition(String labelPosition)
/*     */   {
/* 153 */     this.labelPosition = labelPosition;
/*     */   }
/*     */ }

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.config.elements.Series
 * JD-Core Version:    0.5.3
 */