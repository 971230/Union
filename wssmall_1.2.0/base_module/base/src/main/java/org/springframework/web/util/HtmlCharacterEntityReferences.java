/*     */ package org.springframework.web.util;
/*     */ 
/*     */

import org.springframework.util.Assert;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */ 
/*     */ class HtmlCharacterEntityReferences
/*     */ {
/*     */   static final char REFERENCE_START = '&';
/*     */   static final String DECIMAL_REFERENCE_START = "&#";
/*     */   static final String HEX_REFERENCE_START = "&#x";
/*     */   static final char REFERENCE_END = ';';
/*     */   static final char CHAR_NULL = '￿';
/*     */   private static final String PROPERTIES_FILE = "HtmlCharacterEntityReferences.properties";
/*  55 */   private final String[] characterToEntityReferenceMap = new String[3000];
/*     */ 
/*  57 */   private final Map entityReferenceToCharacterMap = new HashMap(252);
/*     */ 
/*     */   public HtmlCharacterEntityReferences()
/*     */   {
/*  64 */     Properties localProperties = new Properties();
/*     */ 
/*  67 */     InputStream localInputStream = HtmlCharacterEntityReferences.class.getResourceAsStream("HtmlCharacterEntityReferences.properties");
/*  68 */     if (localInputStream == null)
/*  69 */       throw new IllegalStateException("Cannot find reference definition file [HtmlCharacterEntityReferences.properties] as class path resource");
/*     */     try
/*     */     {
/*     */       try
/*     */       {
/*  74 */         localProperties.load(localInputStream);
/*     */       }
/*     */       finally {
/*  77 */         localInputStream.close();
/*     */       }
/*     */     }
/*     */     catch (IOException localIOException) {
/*  81 */       throw new IllegalStateException("Failed to parse reference definition file [HtmlCharacterEntityReferences.properties]: " + localIOException.getMessage());
/*     */     }
/*     */ 
/*  86 */     Enumeration localEnumeration = localProperties.propertyNames();
/*  87 */     while (localEnumeration.hasMoreElements()) {
/*  88 */       String str1 = (String)localEnumeration.nextElement();
/*  89 */       int i = Integer.parseInt(str1);
/*  90 */       Assert.isTrue((i < 1000) || ((i >= 8000) && (i < 10000)), "Invalid reference to special HTML entity: " + i);
/*     */ 
/*  92 */       int j = i < 1000 ? i : i - 7000;
/*  93 */       String str2 = localProperties.getProperty(str1);
/*  94 */       this.characterToEntityReferenceMap[j] = ('&' + str2 + ';');
/*  95 */       this.entityReferenceToCharacterMap.put(str2, new Character((char)i));
/*     */     }
/*     */   }
/*     */ 
/*     */   public int getSupportedReferenceCount()
/*     */   {
/* 103 */     return this.entityReferenceToCharacterMap.size();
/*     */   }
/*     */ 
/*     */   public boolean isMappedToReference(char paramChar)
/*     */   {
/* 110 */     return convertToReference(paramChar) != null;
/*     */   }
/*     */ 
/*     */   public String convertToReference(char paramChar)
/*     */   {
/* 117 */     if ((paramChar < 'Ϩ') || ((paramChar >= 'ὀ') && (paramChar < '✐'))) {
/* 118 */       int i = paramChar < 'Ϩ' ? paramChar : paramChar - '᭘';
/* 119 */       String str = this.characterToEntityReferenceMap[i];
/* 120 */       if (str != null) {
/* 121 */         return str;
/*     */       }
/*     */     }
/* 124 */     return null;
/*     */   }
/*     */ 
/*     */   public char convertToCharacter(String paramString)
/*     */   {
/* 131 */     Character localCharacter = (Character)this.entityReferenceToCharacterMap.get(paramString);
/* 132 */     if (localCharacter != null) {
/* 133 */       return localCharacter.charValue();
/*     */     }
/* 135 */     return 65535;
/*     */   }
/*     */ }

/* Location:           C:\Users\Administrator\Desktop\spring-1.2.6.jar
 * Qualified Name:     org.springframework.web.util.HtmlCharacterEntityReferences
 * JD-Core Version:    0.6.0
 */