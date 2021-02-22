/*     */ package com.ztesoft.crm.report.lang;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ 
/*     */ public class ArrayListEx extends ArrayList
/*     */ {
/*     */   private static final long serialVersionUID = 4145521248923435311L;
/*     */ 
/*     */   public ArrayListEx()
/*     */   {
/*     */   }
/*     */ 
/*     */   public ArrayListEx(Collection arg0)
/*     */   {
/*  32 */     super((arg0 == null) ? new ArrayList() : arg0);
/*     */   }
/*     */ 
/*     */   public ArrayListEx(int initialCapacity)
/*     */   {
/*  40 */     super(initialCapacity);
/*     */   }
/*     */ 
/*     */   @Override
public boolean add(Object o)
/*     */   {
/*  51 */     if (o == null)
/*  52 */       return false;
/*  53 */     return super.add(o);
/*     */   }
/*     */ 
/*     */   @Override
public boolean addAll(Collection c)
/*     */   {
/*  63 */     if ((c == null) || (c.isEmpty()))
/*  64 */       return false;
/*  65 */     return super.addAll(c);
/*     */   }
/*     */ 
/*     */   public void addNoExist(Object o) {
/*  69 */     if (o == null)
/*  70 */       return;
/*  71 */     if (contains(o))
/*  72 */       return;
/*  73 */     add(o);
/*     */   }
/*     */ 
/*     */   public boolean addNoExistAll(Collection c)
/*     */   {
/*  83 */     if ((c == null) || (c.isEmpty()))
/*  84 */       return false;
/*  85 */     for (Iterator objs = c.iterator(); objs.hasNext(); ) {
/*  86 */       addNoExist(objs.next());
/*     */     }
/*  88 */     return true;
/*     */   }
/*     */ 
/*     */   public Object find(ArrayListFilter filter) {
/*  92 */     for (Iterator objs = iterator(); objs.hasNext(); ) {
/*  93 */       Object o = objs.next();
/*  94 */       if (filter.filter(o)) {
/*  95 */         return o;
/*     */       }
/*     */     }
/*  98 */     return null;
/*     */   }
/*     */ 
/*     */   public String join(String s) {
/* 102 */     StringBuffer sb = new StringBuffer();
/* 103 */     for (Iterator objs = iterator(); objs.hasNext(); ) {
/* 104 */       sb.append((String)objs.next());
/* 105 */       if (objs.hasNext())
/* 106 */         sb.append(s);
/*     */     }
/* 108 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String join(String s, String propName) {
/* 112 */     StringBuffer sb = new StringBuffer();
/* 113 */     int i = 0;
/* 114 */     for (Iterator objs = iterator(); objs.hasNext(); ) {
/* 115 */       String v = ObjectPropertyUtils.getString(objs.next(), propName);
/* 116 */       if (Utils.isEmpty(v))
/*     */         continue;
/* 118 */       if (i > 0)
/* 119 */         sb.append(s);
/* 120 */       ++i;
/* 121 */       sb.append(v);
/*     */     }
/* 123 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public void push(String s) {
/* 127 */     if (s == null)
/* 128 */       return;
/* 129 */     if (s.length() == 0)
/* 130 */       return;
/* 131 */     super.add(s);
/*     */   }
/*     */ }

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.lang.ArrayListEx
 * JD-Core Version:    0.5.3
 */