/*    */ package org.springframework.web.util;
/*    */ 
/*    */ public class JavaScriptUtils
/*    */ {
/*    */   public static String javaScriptEscape(String paramString)
/*    */   {
/* 40 */     if (paramString == null) {
/* 41 */       return paramString;
/*    */     }
/*    */ 
/* 44 */     StringBuffer localStringBuffer = new StringBuffer(paramString.length());
/* 45 */     int i = 0;
/*    */ 
/* 47 */     for (int j = 0; j < paramString.length(); j++) {
/* 48 */       char c = paramString.charAt(j);
/* 49 */       if (c == '"') {
/* 50 */         localStringBuffer.append("\\\"");
/*    */       }
/* 52 */       else if (c == '\'') {
/* 53 */         localStringBuffer.append("\\'");
/*    */       }
/* 55 */       else if (c == '\\') {
/* 56 */         localStringBuffer.append("\\\\");
/*    */       }
/* 58 */       else if (c == '\t') {
/* 59 */         localStringBuffer.append("\\t");
/*    */       }
/* 61 */       else if (c == '\n') {
/* 62 */         if (i != 13) {
/* 63 */           localStringBuffer.append("\\n");
/*    */         }
/*    */       }
/* 66 */       else if (c == '\r') {
/* 67 */         localStringBuffer.append("\\n");
/*    */       }
/* 69 */       else if (c == '\f') {
/* 70 */         localStringBuffer.append("\\f");
/*    */       }
/*    */       else {
/* 73 */         localStringBuffer.append(c);
/*    */       }
/* 75 */       i = c;
/*    */     }
/*    */ 
/* 78 */     return localStringBuffer.toString();
/*    */   }
/*    */ }

/* Location:           C:\Users\Administrator\Desktop\spring-1.2.6.jar
 * Qualified Name:     org.springframework.web.util.JavaScriptUtils
 * JD-Core Version:    0.6.0
 */