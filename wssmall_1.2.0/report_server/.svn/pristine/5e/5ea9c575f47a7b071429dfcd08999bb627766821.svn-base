/*    */ package com.ztesoft.crm.report.io;
/*    */ 
/*    */ import com.ztesoft.crm.report.lang.StreamUtils;
/*    */ import java.io.BufferedInputStream;
/*    */ import java.io.ByteArrayOutputStream;
/*    */ import java.io.InputStream;
/*    */ import java.util.zip.ZipEntry;
/*    */ import java.util.zip.ZipInputStream;
/*    */ 
/*    */ public class FileZipInputStream
/*    */ {
/*    */   public void unzip(InputStream in, FileZipEntry fze)
/*    */     throws Exception
/*    */   {
/* 28 */     ZipInputStream zis = new ZipInputStream(new BufferedInputStream(in));
/*    */     try
/*    */     {
/*    */       ZipEntry entry;
/* 32 */       while ((entry = zis.getNextEntry()) != null)
/*    */       {
/* 34 */         if (entry.isDirectory()) {
/* 35 */           if (fze != null)
/* 36 */             fze.doRead(entry.getName(), null);
/*    */         }
/*    */         else
/*    */         {
/* 40 */           byte[] data = new byte[1024];
/*    */ 
/* 43 */           ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
/*    */           int count;
/* 45 */           while ((count = zis.read(data, 0, 1024)) > 0) {
/* 46 */             out.write(data, 0, count);
/*    */           }
/*    */ 
/* 49 */           out.close();
/*    */ 
/* 51 */           if (fze != null)
/*    */           {
/* 53 */             fze.doRead(entry.getName(), out.toByteArray());
/*    */           }
/* 55 */           out = null;
/*    */         }
/*    */       }
/*    */     }
/*    */     catch (Exception e)
/*    */     {
/* 61 */       throw e;
/*    */     } finally {
/* 63 */       StreamUtils.close(zis);
/*    */     }
/*    */   }
/*    */ }

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.io.FileZipInputStream
 * JD-Core Version:    0.5.3
 */