/*     */ package org.springframework.web.util;
/*     */ 
/*     */ class HtmlCharacterEntityDecoder
/*     */ {
/*     */   private static final int MAX_REFERENCE_SIZE = 10;
/*     */   private final HtmlCharacterEntityReferences characterEntityReferences;
/*     */   private final String originalMessage;
/*     */   private final StringBuffer decodedMessage;
/*  38 */   private int currentPosition = 0;
/*     */ 
/*  40 */   private int nextPotentialReferencePosition = -1;
/*     */ 
/*  42 */   private int nextSemicolonPosition = -2;
/*     */ 
/*     */   public HtmlCharacterEntityDecoder(HtmlCharacterEntityReferences paramHtmlCharacterEntityReferences, String paramString)
/*     */   {
/*  46 */     this.characterEntityReferences = paramHtmlCharacterEntityReferences;
/*  47 */     this.originalMessage = paramString;
/*  48 */     this.decodedMessage = new StringBuffer(this.originalMessage.length());
/*     */   }
/*     */ 
/*     */   public String decode() {
/*  52 */     while (this.currentPosition < this.originalMessage.length()) {
/*  53 */       findNextPotentialReference(this.currentPosition);
/*  54 */       copyCharactersTillPotentialReference();
/*  55 */       processPossibleReference();
/*     */     }
/*  57 */     return this.decodedMessage.toString();
/*     */   }
/*     */ 
/*     */   private void findNextPotentialReference(int paramInt) {
/*  61 */     this.nextPotentialReferencePosition = Math.max(paramInt, this.nextSemicolonPosition - 10);
/*     */     do
/*     */     {
/*  64 */       this.nextPotentialReferencePosition = this.originalMessage.indexOf('&', this.nextPotentialReferencePosition);
/*     */ 
/*  67 */       if ((this.nextSemicolonPosition != -1) && (this.nextSemicolonPosition < this.nextPotentialReferencePosition))
/*     */       {
/*  69 */         this.nextSemicolonPosition = this.originalMessage.indexOf(';', this.nextPotentialReferencePosition + 1);
/*     */       }
/*  71 */       int i = (this.nextPotentialReferencePosition != -1) && (this.nextSemicolonPosition != -1) && (this.nextPotentialReferencePosition - this.nextSemicolonPosition < 10) ? 1 : 0;
/*     */ 
/*  76 */       if (i != 0) {
/*     */         break;
/*     */       }
/*  79 */       if (this.nextPotentialReferencePosition == -1) {
/*     */         break;
/*     */       }
/*  82 */       if (this.nextSemicolonPosition == -1) {
/*  83 */         this.nextPotentialReferencePosition = -1;
/*  84 */         break;
/*     */       }
/*     */ 
/*  87 */       this.nextPotentialReferencePosition += 1;
/*     */     }
/*  89 */     while (this.nextPotentialReferencePosition != -1);
/*     */   }
/*     */ 
/*     */   private void copyCharactersTillPotentialReference()
/*     */   {
/*  94 */     if (this.nextPotentialReferencePosition != this.currentPosition) {
/*  95 */       int i = this.nextPotentialReferencePosition != -1 ? this.nextPotentialReferencePosition : this.originalMessage.length();
/*     */ 
/*  97 */       if (i - this.currentPosition > 3) {
/*  98 */         this.decodedMessage.append(this.originalMessage.substring(this.currentPosition, i));
/*  99 */         this.currentPosition = i;
/*     */       }
/*     */       else {
/* 102 */         while (this.currentPosition < i)
/* 103 */           this.decodedMessage.append(this.originalMessage.charAt(this.currentPosition++));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void processPossibleReference() {
/* 109 */     if (this.nextPotentialReferencePosition != -1) {
/* 110 */       int i = this.originalMessage.charAt(this.currentPosition + 1) == '#' ? 1 : 0;
/* 111 */       boolean bool = i != 0 ? processNumberedReference() : processNamedReference();
/* 112 */       if (bool) {
/* 113 */         this.currentPosition = (this.nextSemicolonPosition + 1);
/*     */       }
/*     */       else {
/* 116 */         char c = this.originalMessage.charAt(this.currentPosition);
/* 117 */         this.decodedMessage.append(c);
/* 118 */         this.currentPosition += 1;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private boolean processNumberedReference() {
/* 124 */     int i = (this.originalMessage.charAt(this.nextPotentialReferencePosition + 2) == 'x') || (this.originalMessage.charAt(this.nextPotentialReferencePosition + 2) == 'X') ? 1 : 0;
/*     */     try
/*     */     {
/* 128 */       int j = i == 0 ? Integer.parseInt(getReferenceSubstring(2)) : Integer.parseInt(getReferenceSubstring(3), 16);
/*     */ 
/* 131 */       this.decodedMessage.append((char)j);
/* 132 */       return true;
/*     */     } catch (NumberFormatException localNumberFormatException) {
/*     */     }
/* 135 */     return false;
/*     */   }
/*     */ 
/*     */   private boolean processNamedReference()
/*     */   {
/* 140 */     String str = getReferenceSubstring(1);
/* 141 */     int i = this.characterEntityReferences.convertToCharacter(str);
/* 142 */     if (i != 65535) {
/* 143 */       this.decodedMessage.append(i);
/* 144 */       return true;
/*     */     }
/* 146 */     return false;
/*     */   }
/*     */ 
/*     */   private String getReferenceSubstring(int paramInt) {
/* 150 */     return this.originalMessage.substring(this.nextPotentialReferencePosition + paramInt, this.nextSemicolonPosition);
/*     */   }
/*     */ }

/* Location:           C:\Users\Administrator\Desktop\spring-1.2.6.jar
 * Qualified Name:     org.springframework.web.util.HtmlCharacterEntityDecoder
 * JD-Core Version:    0.6.0
 */