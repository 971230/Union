/*    */ package com.ztesoft.crm.report.lang;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.io.Reader;
/*    */ 
/*    */ public final class StreamUtils
/*    */ {
/*    */   public static final void close(OutputStream out)
/*    */   {
/*    */     try
/*    */     {
/* 24 */       if (out != null)
/* 25 */         out.close();
/*    */     } catch (Exception localException) {
/*    */     }
/*    */   }
/*    */ 
/*    */   public static final void close(Reader out) {
/*    */     try {
/* 32 */       if (out != null)
/* 33 */         out.close();
/*    */     } catch (Exception localException) {
/*    */     }
/*    */   }
/*    */ 
/*    */   public static final void close(InputStream out) {
/*    */     try {
/* 40 */       if (out != null)
/* 41 */         out.close();
/*    */     } catch (Exception localException) {
/*    */     }
/*    */   }
/*    */ 
/*    */   public static final void write(OutputStream out, InputStream in) throws Exception {
/*    */     try {
/* 48 */       byte[] buff = new byte[1024];
/* 49 */       int len = 0;
/* 50 */       while ((len = in.read(buff)) > 0)
/* 51 */         out.write(buff, 0, len);
/*    */     }
/*    */     catch (Exception e) {
/* 54 */       throw e;
/*    */     }
/*    */   }
/*    */ 
/*    */   public static final void closeAfterWrite(OutputStream out, InputStream in) throws Exception {
/*    */     try {
/* 60 */       byte[] buff = new byte[1024];
/* 61 */       int len = 0;
/* 62 */       while ((len = in.read(buff)) > 0)
/* 63 */         out.write(buff, 0, len);
/*    */     }
/*    */     catch (Exception e) {
/* 66 */       throw e;
/*    */     } finally {
/* 68 */       close(in);
/* 69 */       close(out);
/*    */     }
/*    */   }
/*    */ }

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.lang.StreamUtils
 * JD-Core Version:    0.5.3
 */