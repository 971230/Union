/*    */ package org.springframework.web.multipart;
/*    */ 
/*    */ public class MaxUploadSizeExceededException extends MultipartException
/*    */ {
/*    */   private final long maxUploadSize;
/*    */ 
/*    */   public MaxUploadSizeExceededException(long paramLong)
/*    */   {
/* 35 */     this(paramLong, null);
/*    */   }
/*    */ 
/*    */   public MaxUploadSizeExceededException(long paramLong, Throwable paramThrowable)
/*    */   {
/* 44 */     super("Maximum upload size of " + paramLong + " bytes exceeded", paramThrowable);
/* 45 */     this.maxUploadSize = paramLong;
/*    */   }
/*    */ 
/*    */   public long getMaxUploadSize()
/*    */   {
/* 52 */     return this.maxUploadSize;
/*    */   }
/*    */ }

/* Location:           C:\Users\Administrator\Desktop\spring-1.2.6.jar
 * Qualified Name:     org.springframework.web.multipart.MaxUploadSizeExceededException
 * JD-Core Version:    0.6.0
 */