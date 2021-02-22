/*     */ package com.ztesoft.crm.report.io;
/*     */ 
/*     */ import com.ztesoft.crm.report.lang.StreamUtils;
/*     */ import com.ztesoft.crm.report.storage.impl.DefaultDBFile;
/*     */ import java.io.File;
/*     */ import java.io.FileFilter;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.Date;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.zip.ZipEntry;
/*     */ import java.util.zip.ZipOutputStream;
/*     */ 
/*     */ public class FileZipOutputStream
/*     */ {
/*     */   public void zip(String zipFileName, String file)
/*     */     throws Exception
/*     */   {
/*  35 */     zip(new FileOutputStream(zipFileName), file);
/*     */   }
/*     */ 
/*     */   public void zip(OutputStream out, String file) throws Exception {
/*  39 */     ZipOutputStream zout = null;
/*     */     try {
/*  41 */       zout = new ZipOutputStream(out);
/*  42 */       File[] fs = listFiles(new File(file));
/*  43 */       for (int i = 0; i < fs.length; ++i) {
/*  44 */         zip(zout, fs[i], "");
/*     */       }
/*     */ 
/*  48 */       zout.flush();
/*     */     } catch (Exception e) {
/*  50 */       throw e;
/*     */     } finally {
/*  52 */       StreamUtils.close(zout);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void zip(OutputStream out, List list) throws Exception {
/*  57 */     ZipOutputStream zout = null;
/*     */     try {
/*  59 */       zout = new ZipOutputStream(out);
/*     */ 
/*  61 */       for (Iterator objs = list.iterator(); objs.hasNext(); ) {
/*  62 */         DefaultDBFile f = (DefaultDBFile)objs.next();
/*  63 */         ZipEntry ze = new ZipEntry(f.getPath().replaceFirst("/", ""));
/*  64 */         byte[] arr = f.getXmlcontent().getBytes();
/*  65 */         ze.setTime(new Date().getTime());
/*  66 */         ze.setSize(arr.length);
/*  67 */         zout.putNextEntry(ze);
/*  68 */         zout.write(arr);
/*  69 */         zout.flush();
/*  70 */         arr = null;
/*     */       }
/*     */ 
/*  74 */       zout.flush();
/*     */     } catch (Exception e) {
/*  76 */       throw e;
/*     */     } finally {
/*  78 */       StreamUtils.close(zout);
/*     */     }
/*     */   }
/*     */ 
/*     */   private File[] listFiles(File f) {
/*  83 */     File[] fs = f.listFiles(new FileFilter()
/*     */     {
/*     */       @Override
public boolean accept(File pathname)
/*     */       {
/*  87 */         String s = pathname.getName().toLowerCase();
/*  88 */         if (s.equals(".svn")) {
/*  89 */           return false;
/*     */         }
/*  91 */         return (!(s.startsWith(".")));
/*     */       }
/*     */     });
/*  96 */     return ((fs == null) ? new File[0] : fs);
/*     */   }
/*     */ 
/*     */   private void zip(ZipOutputStream out, File f, String base) throws Exception {
/* 100 */     if (!(f.exists())) {
/* 101 */       return;
/*     */     }
/*     */ 
/* 105 */     if (f.isDirectory()) {
/* 106 */       File[] fs = listFiles(f);
/*     */ 
/* 110 */       for (int i = 0; i < fs.length; ++i)
/*     */       {
/* 112 */         zip(out, fs[i], 
/* 113 */           base + f.getName() + "/");
/*     */       }
/*     */     } else {
/* 116 */       ZipEntry ze = new ZipEntry(
/* 117 */         base + f.getName());
/* 118 */       ze.setTime(f.lastModified());
/* 119 */       ze.setSize(f.length());
/* 120 */       out.putNextEntry(ze);
/* 121 */       write(out, new FileInputStream(f));
/*     */     }
/*     */   }
/*     */ 
/*     */   private void write(ZipOutputStream out, InputStream in) throws Exception {
/* 126 */     byte[] buff = new byte[1024];
/* 127 */     int len = 0;
/*     */     try {
/* 129 */       while ((len = in.read(buff)) > 0)
/*     */       {
/* 131 */         out.write(buff, 0, len);
/*     */       }
/* 133 */       out.flush();
/*     */     } catch (Exception e) {
/* 135 */       throw e;
/*     */     } finally {
/* 137 */       StreamUtils.close(in);
/*     */     }
/*     */   }
/*     */ }

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.io.FileZipOutputStream
 * JD-Core Version:    0.5.3
 */