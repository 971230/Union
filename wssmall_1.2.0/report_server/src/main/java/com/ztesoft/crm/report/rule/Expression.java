/*     */ package com.ztesoft.crm.report.rule;
/*     */ 
/*     */ import com.ztesoft.crm.report.lang.RegularExpressionUtils;
/*     */ import com.ztesoft.crm.report.lang.Utils;
/*     */ import com.ztesoft.crm.report.log.ReportLogger;
/*     */ import java.io.PrintStream;
/*     */ import java.math.BigDecimal;
/*     */ import java.text.MessageFormat;
/*     */ 
/*     */ public class Expression extends Token
/*     */ {
/*     */   private Token left;
/*     */   private Token right;
/*     */   private String operator;
/*     */ 
/*     */   @Override
public void list(PrintStream out)
/*     */   {
/*  30 */     this.left.list(out);
/*  31 */     out.println("右");
/*  32 */     this.right.list(out);
/*     */   }
/*     */ 
/*     */   private int compare(Object v1, Object v2)
/*     */   {
/*  37 */     if ((Utils.isEmpty(v1)) && (!(Utils.isEmpty(v2))))
/*  38 */       return -1;
/*  39 */     if ((!(Utils.isEmpty(v1))) && (Utils.isEmpty(v2)))
/*  40 */       return 1;
/*  41 */     if ((Utils.isEmpty(v1)) && (Utils.isEmpty(v2))) {
/*  42 */       return 0;
/*     */     }
/*  44 */     if ((RegularExpressionUtils.isNumber(v1.toString())) && 
/*  45 */       (RegularExpressionUtils.isNumber(v2.toString())))
/*     */     {
/*  47 */       return new BigDecimal(v1.toString()).compareTo(
/*  48 */         new BigDecimal(v2.toString()));
/*     */     }
/*     */ 
/*  51 */     return v1.toString().compareTo(v2.toString());
/*     */   }
/*     */ 
/*     */   @Override
public Object execute()
/*     */   {
/*  63 */     Object l = (this.left != null) ? this.left.execute() : null;
/*  64 */     Object r = (this.right != null) ? this.right.execute() : null;
/*     */ 
/*  66 */     boolean lnumber = RegularExpressionUtils.isNumber(l);
/*  67 */     boolean rnumber = RegularExpressionUtils.isNumber(r);
/*     */     boolean ll;
/*     */     boolean rr;
/*  69 */     if ("||".equals(this.operator)) {
/*  70 */       ll = (l == null) ? false : ((Boolean)l).booleanValue();
/*  71 */       rr = (r == null) ? false : ((Boolean)r).booleanValue();
/*     */ 
/*  73 */       return new Boolean((ll) || (rr));
/*     */     }
/*     */ 
/*  77 */     if ("&&".equals(this.operator))
/*     */     {
/*  79 */       ll = (l == null) ? false : ((Boolean)l).booleanValue();
/*  80 */       rr = (r == null) ? false : ((Boolean)r).booleanValue();
/*     */ 
/*  82 */       return new Boolean((ll) && (rr));
/*     */     }
/*     */ 
/*  85 */     int cv = compare(l, r);
/*     */ 
/*  87 */     if (">=".equals(this.operator)) {
/*  88 */       return new Boolean(cv >= 0);
/*     */     }
/*  90 */     if ("<=".equals(this.operator)) {
/*  91 */       return new Boolean(cv <= 0);
/*     */     }
/*  93 */     if (">".equals(this.operator)) {
/*  94 */       return new Boolean(cv == 1);
/*     */     }
/*  96 */     if ("<".equals(this.operator)) {
/*  97 */       return new Boolean(cv == -1);
/*     */     }
/*     */ 
/* 100 */     if ("!=".equals(this.operator)) {
/* 101 */       return new Boolean(cv != 0);
/*     */     }
/* 103 */     if ("==".equals(this.operator)) {
/* 104 */       return new Boolean(cv == 0);
/*     */     }
/*     */ 
/* 107 */     if ("=".equals(this.operator))
/*     */     {
/* 109 */       this.left.setVariateValue(((Variable)this.left).getName(), r);
/* 110 */       return r;
/*     */     }
/*     */ 
/* 113 */     if ("+".equals(this.operator)) {
/* 114 */       if ((lnumber) && (rnumber)) {
/* 115 */         return new Float(Float.parseFloat(l.toString()) + 
/* 116 */           Float.parseFloat(r.toString()));
/*     */       }
/*     */ 
/* 119 */       return ((l != null) ? l.toString() : "") + 
/* 120 */         ((r != null) ? r.toString() : "");
/*     */     }
/*     */ 
/* 124 */     if ((!(lnumber)) || (!(rnumber))) {
/* 125 */       String f = "值:[{0}]和[{1}]存在空值或者不是数字型，不能进行算术计算";
/* 126 */       ReportLogger.getLogger().warn(
/* 127 */         MessageFormat.format(f, new Object[] { l, r }));
/*     */     }
/*     */ 
/* 130 */     if ("-".equals(this.operator)) {
/* 131 */       return new Float(Float.parseFloat(l.toString()) - 
/* 132 */         Float.parseFloat(r.toString()));
/*     */     }
/* 134 */     if ("*".equals(this.operator)) {
/* 135 */       return new Float(Float.parseFloat(l.toString()) * 
/* 136 */         Float.parseFloat(r.toString()));
/*     */     }
/* 138 */     if ("/".equals(this.operator)) {
/* 139 */       return new Float(Float.parseFloat(l.toString()) / 
/* 140 */         Float.parseFloat(r.toString()));
/*     */     }
/*     */ 
/* 143 */     return null;
/*     */   }
/*     */ 
/*     */   public Token getLeft()
/*     */   {
/* 151 */     return this.left;
/*     */   }
/*     */ 
/*     */   public void setLeft(Token left)
/*     */   {
/* 156 */     this.left = left;
/* 157 */     if (this.left != null)
/* 158 */       this.left.setParent(this);
/*     */   }
/*     */ 
/*     */   public Token getRight() {
/* 162 */     return this.right;
/*     */   }
/*     */ 
/*     */   public void setRight(Token right) {
/* 166 */     this.right = right;
/* 167 */     if (this.right != null)
/* 168 */       this.right.setParent(this);
/*     */   }
/*     */ 
/*     */   public String getOperator() {
/* 172 */     return this.operator;
/*     */   }
/*     */ 
/*     */   @Override
public String toString()
/*     */   {
/* 177 */     return this.left + this.operator + this.right;
/*     */   }
/*     */ 
/*     */   public void setOperator(String operator) {
/* 181 */     this.operator = operator;
/*     */   }
/*     */ }

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.rule.Expression
 * JD-Core Version:    0.5.3
 */