/*     */ package com.ztesoft.crm.report.storage.impl;
/*     */ 
/*     */ import com.ztesoft.crm.report.storage.ReportFile;
/*     */ import com.ztesoft.crm.report.storage.Storage;
/*     */ import com.ztesoft.crm.report.storage.StorageStream;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.List;
/*     */ import javax.servlet.ServletContext;
/*     */ 
/*     */ public class FtpStorageStream
/*     */   implements StorageStream
/*     */ {
/*     */   private Storage storage;
/*     */ 
/*     */   @Override
public List deploy(ServletContext context, String parent, InputStream zip)
/*     */     throws Exception
/*     */   {
/*  26 */     return null;
/*     */   }
/*     */ 
/*     */   @Override
public boolean zip(ServletContext context, String path, OutputStream out)
/*     */     throws Exception
/*     */   {
/*  39 */     return false;
/*     */   }
/*     */ 
/*     */   @Override
public boolean createDirectory(ServletContext context, String parent, String name)
/*     */     throws Exception
/*     */   {
/*  52 */     return false;
/*     */   }
/*     */ 
/*     */   @Override
public InputStream getReport(ServletContext context, String path)
/*     */     throws Exception
/*     */   {
/*  58 */     return null;
/*     */   }
/*     */ 
/*     */   @Override
public boolean delete(ServletContext context, String path)
/*     */     throws Exception
/*     */   {
/*  69 */     return false;
/*     */   }
/*     */ 
/*     */   @Override
public ReportFile[] getDirectory(ServletContext context, String parent)
/*     */     throws Exception
/*     */   {
/*  82 */     return null;
/*     */   }
/*     */ 
/*     */   @Override
public Storage getStorage()
/*     */   {
/*  92 */     return this.storage;
/*     */   }
/*     */ 
/*     */   @Override
public boolean rename(ServletContext context, String oldPath, String newPath)
/*     */     throws Exception
/*     */   {
/* 104 */     return false;
/*     */   }
/*     */ 
/*     */   @Override
public void setStorage(Storage storage)
/*     */   {
/* 116 */     this.storage = storage;
/*     */   }
/*     */ 
/*     */   @Override
public boolean save(ServletContext context, String dir, InputStream xmlContent)
/*     */     throws Exception
/*     */   {
/* 123 */     return false;
/*     */   }
/*     */ 
/*     */   @Override
public boolean save(ServletContext context, String dir, String xmlContent)
/*     */     throws Exception
/*     */   {
/* 129 */     return false;
/*     */   }
/*     */ }

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.storage.impl.FtpStorageStream
 * JD-Core Version:    0.5.3
 */