/*    */ package com.ztesoft.crm.report.storage;
/*    */ 
/*    */ import com.ztesoft.crm.report.ReportContext;
/*    */ import com.ztesoft.crm.report.storage.impl.DefaultDBStorageStream;
/*    */ import com.ztesoft.crm.report.storage.impl.FileStorageStream;
/*    */ import com.ztesoft.crm.report.storage.impl.FtpStorageStream;
/*    */ 
/*    */ public class StorageStreamFactory
/*    */ {
/*    */   public static final StorageStream createStream()
/*    */   {
/* 25 */     Storage storage = ReportContext.getContext().getStorage();
/* 26 */     StorageStream stream = null;
/* 27 */     if ("file".equalsIgnoreCase(storage.getType())) {
/* 28 */       stream = new FileStorageStream();
/*    */     }
/*    */ 
/* 31 */     if ("ftp".equalsIgnoreCase(storage.getType())) {
/* 32 */       stream = new FtpStorageStream();
/*    */     }
/*    */ 
/* 35 */     if ("database".equalsIgnoreCase(storage.getType())) {
/*    */       try {
/* 37 */         stream = (StorageStream)Class.forName(storage.getClassName())
/* 38 */           .newInstance();
/*    */       } catch (Exception e) {
/* 40 */         stream = new DefaultDBStorageStream();
/*    */       }
/*    */     }
/*    */ 
/* 44 */     stream.setStorage(storage);
/* 45 */     return stream;
/*    */   }
/*    */ }

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.storage.StorageStreamFactory
 * JD-Core Version:    0.5.3
 */