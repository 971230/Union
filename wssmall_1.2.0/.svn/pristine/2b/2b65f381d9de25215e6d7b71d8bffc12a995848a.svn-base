/*    */ package org.springframework.web.util;
/*    */ 
/*    */ public abstract class TagUtils
/*    */ {
/*    */   public static final String SCOPE_PAGE = "page";
/*    */   public static final String SCOPE_REQUEST = "request";
/*    */   public static final String SCOPE_SESSION = "session";
/*    */   public static final String SCOPE_APPLICATION = "application";
/*    */ 
/*    */   public static int getScope(String paramString)
/*    */   {
/* 60 */     if (paramString == null) {
/* 61 */       throw new IllegalArgumentException("Scope to search for cannot be null");
/*    */     }
/*    */ 
/* 64 */     if (paramString.equals("request")) {
/* 65 */       return 2;
/*    */     }
/* 67 */     if (paramString.equals("session")) {
/* 68 */       return 3;
/*    */     }
/* 70 */     if (paramString.equals("application")) {
/* 71 */       return 4;
/*    */     }
/*    */ 
/* 74 */     return 1;
/*    */   }
/*    */ }

/* Location:           C:\Users\Administrator\Desktop\spring-1.2.6.jar
 * Qualified Name:     org.springframework.web.util.TagUtils
 * JD-Core Version:    0.6.0
 */