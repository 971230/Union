/*     */ package org.springframework.web.util;
/*     */ 
/*     */ public abstract class HtmlUtils
/*     */ {
/*  45 */   private static final HtmlCharacterEntityReferences characterEntityReferences = new HtmlCharacterEntityReferences();
/*     */ 
/*     */   public static String htmlEscape(String paramString)
/*     */   {
/*  62 */     if (paramString == null) {
/*  63 */       return null;
/*     */     }
/*  65 */     StringBuffer localStringBuffer = new StringBuffer(paramString.length() * 2);
/*  66 */     for (int i = 0; i < paramString.length(); i++) {
/*  67 */       char c = paramString.charAt(i);
/*  68 */       String str = characterEntityReferences.convertToReference(c);
/*  69 */       if (str != null) {
/*  70 */         localStringBuffer.append(str);
/*     */       }
/*     */       else {
/*  73 */         localStringBuffer.append(c);
/*     */       }
/*     */     }
/*  76 */     return localStringBuffer.toString();
/*     */   }
/*     */ 
/*     */   public static String htmlEscapeDecimal(String paramString)
/*     */   {
/*  92 */     if (paramString == null) {
/*  93 */       return null;
/*     */     }
/*  95 */     StringBuffer localStringBuffer = new StringBuffer(paramString.length() * 2);
/*  96 */     for (int i = 0; i < paramString.length(); i++) {
/*  97 */       char c = paramString.charAt(i);
/*  98 */       if (characterEntityReferences.isMappedToReference(c)) {
/*  99 */         localStringBuffer.append("&#");
/* 100 */         localStringBuffer.append(c);
/* 101 */         localStringBuffer.append(';');
/*     */       }
/*     */       else {
/* 104 */         localStringBuffer.append(c);
/*     */       }
/*     */     }
/* 107 */     return localStringBuffer.toString();
/*     */   }
/*     */ 
/*     */   public static String htmlEscapeHex(String paramString)
/*     */   {
/* 123 */     if (paramString == null) {
/* 124 */       return null;
/*     */     }
/* 126 */     StringBuffer localStringBuffer = new StringBuffer(paramString.length() * 2);
/* 127 */     for (int i = 0; i < paramString.length(); i++) {
/* 128 */       char c = paramString.charAt(i);
/* 129 */       if (characterEntityReferences.isMappedToReference(c)) {
/* 130 */         localStringBuffer.append("&#x");
/* 131 */         localStringBuffer.append(Integer.toString(c, 16));
/* 132 */         localStringBuffer.append(';');
/*     */       }
/*     */       else {
/* 135 */         localStringBuffer.append(c);
/*     */       }
/*     */     }
/* 138 */     return localStringBuffer.toString();
/*     */   }
/*     */ 
/*     */   public static String htmlUnescape(String paramString)
/*     */   {
/* 161 */     if (paramString == null) {
/* 162 */       return null;
/*     */     }
/* 164 */     return new HtmlCharacterEntityDecoder(characterEntityReferences, paramString).decode();
/*     */   }
/*     */ }

/* Location:           C:\Users\Administrator\Desktop\spring-1.2.6.jar
 * Qualified Name:     org.springframework.web.util.HtmlUtils
 * JD-Core Version:    0.6.0
 */