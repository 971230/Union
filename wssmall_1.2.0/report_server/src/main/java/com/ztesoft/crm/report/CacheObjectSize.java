/*     */ package com.ztesoft.crm.report;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ 
/*     */ public class CacheObjectSize
/*     */ {
/*     */   public int sizeOfBoolean()
/*     */   {
/*  29 */     return 1;
/*     */   }
/*     */ 
/*     */   public int sizeOfChar()
/*     */   {
/*  38 */     return 2;
/*     */   }
/*     */ 
/*     */   public int sizeOfDate()
/*     */   {
/*  47 */     return 12;
/*     */   }
/*     */ 
/*     */   public int sizeOfDouble()
/*     */   {
/*  56 */     return 8;
/*     */   }
/*     */ 
/*     */   public int sizeOfInt()
/*     */   {
/*  65 */     return 4;
/*     */   }
/*     */ 
/*     */   public int sizeOfLong()
/*     */   {
/*  74 */     return 8;
/*     */   }
/*     */ 
/*     */   public int sizeOfMap(Map map)
/*     */   {
/*  86 */     if (map == null) {
/*  87 */       return 0;
/*     */     }
/*     */ 
/*  90 */     int size = 36;
/*     */ 
/*  92 */     Iterator iter = map.values().iterator();
/*  93 */     while (iter.hasNext()) {
/*  94 */       String value = (String)iter.next();
/*  95 */       size += sizeOfString(value);
/*     */     }
/*     */ 
/*  98 */     iter = map.keySet().iterator();
/*  99 */     while (iter.hasNext()) {
/* 100 */       String key = (String)iter.next();
/* 101 */       size += sizeOfString(key);
/*     */     }
/* 103 */     return size;
/*     */   }
/*     */ 
/*     */   public int sizeOfObject()
/*     */   {
/* 113 */     return 4;
/*     */   }
/*     */ 
/*     */   public int sizeOfProperties(Properties properties)
/*     */   {
/* 125 */     if (properties == null) {
/* 126 */       return 0;
/*     */     }
/*     */ 
/* 129 */     int size = 36;
/*     */ 
/* 131 */     Enumeration enm = properties.elements();
/*     */     String prop;
/* 132 */     while (enm.hasMoreElements()) {
/* 133 */       prop = (String)enm.nextElement();
/* 134 */       size += sizeOfString(prop);
/*     */     }
/*     */ 
/* 137 */     enm = properties.propertyNames();
/* 138 */     while (enm.hasMoreElements()) {
/* 139 */       prop = (String)enm.nextElement();
/* 140 */       size += sizeOfString(prop);
/*     */     }
/* 142 */     return size;
/*     */   }
/*     */ 
/*     */   public int sizeOfString(String string)
/*     */   {
/* 153 */     if (string == null) {
/* 154 */       return 0;
/*     */     }
/* 156 */     return (4 + string.length() * 2);
/*     */   }
/*     */ }

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.CacheObjectSize
 * JD-Core Version:    0.5.3
 */