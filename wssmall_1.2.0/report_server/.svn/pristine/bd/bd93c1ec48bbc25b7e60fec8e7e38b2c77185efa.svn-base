/*    */ package com.ztesoft.crm.report.rule;
/*    */ 
/*    */ public final class CharStreamReader
/*    */ {
/*    */   private char[] stream;
/* 12 */   private int index = 0;
/*    */   private int length;
/* 31 */   private int mark = 0;
/*    */ 
/*    */   public CharStreamReader(String str)
/*    */   {
/* 20 */     if (str == null)
/* 21 */       this.stream = new char[0];
/*    */     else
/* 23 */       this.stream = str.toCharArray();
/* 24 */     this.length = this.stream.length;
/*    */   }
/*    */ 
/*    */   public void close() {
/* 28 */     this.stream = null;
/*    */   }
/*    */ 
/*    */   public void mark()
/*    */   {
/* 34 */     this.mark = this.index;
/*    */   }
/*    */ 
/*    */   public void backup() {
/* 38 */     this.index = this.mark;
/*    */   }
/*    */ 
/*    */   public boolean unclosed() {
/* 42 */     return (this.index < this.length);
/*    */   }
/*    */ 
/*    */   public char readChar() {
/* 46 */     char c = this.stream[this.index];
/* 47 */     this.index += 1;
/* 48 */     return c;
/*    */   }
/*    */ 
/*    */   public int read() {
/* 52 */     return readChar();
/*    */   }
/*    */ 
/*    */   public String readAll() {
/* 56 */     String s = new String(this.stream, this.index, this.length - this.index);
/* 57 */     this.index = this.length;
/* 58 */     return s;
/*    */   }
/*    */ }

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.rule.CharStreamReader
 * JD-Core Version:    0.5.3
 */