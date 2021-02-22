/*     */ package org.springframework.web.multipart.commons;
/*     */ 
/*     */

import org.apache.commons.fileupload.DefaultFileItem;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */ 
/*     */ public class CommonsMultipartFile
/*     */   implements MultipartFile, Serializable
/*     */ {
/*  43 */   protected static final Log logger = LogFactory.getLog(CommonsMultipartFile.class);
/*     */   private final FileItem fileItem;
/*     */   private final long size;
/*     */ 
/*     */   protected CommonsMultipartFile(FileItem paramFileItem)
/*     */   {
/*  54 */     this.fileItem = paramFileItem;
/*  55 */     this.size = this.fileItem.getSize();
/*     */   }
/*     */ 
/*     */   public FileItem getFileItem()
/*     */   {
/*  63 */     return this.fileItem;
/*     */   }
/*     */ 
/*     */   @Override
public String getName() {
/*  67 */     return this.fileItem.getFieldName();
/*     */   }
/*     */ 
/*     */   @Override
public boolean isEmpty() {
/*  71 */     return this.size == 0L;
/*     */   }
/*     */ 
/*     */   @Override
public String getOriginalFilename() {
/*  75 */     if (this.fileItem.getName() == null) {
/*  76 */       return null;
/*     */     }
/*     */ 
/*  79 */     int i = this.fileItem.getName().lastIndexOf("/");
/*  80 */     if (i == -1)
/*     */     {
/*  82 */       i = this.fileItem.getName().lastIndexOf("\\");
/*     */     }
/*  84 */     if (i != -1)
/*     */     {
/*  86 */       return this.fileItem.getName().substring(i + 1);
/*     */     }
/*     */ 
/*  90 */     return this.fileItem.getName();
/*     */   }
/*     */ 
/*     */   @Override
public String getContentType()
/*     */   {
/*  95 */     return this.fileItem.getContentType();
/*     */   }
/*     */ 
/*     */   @Override
public long getSize() {
/*  99 */     return this.size;
/*     */   }
/*     */ 
/*     */   @Override
public byte[] getBytes() {
/* 103 */     if (!isAvailable()) {
/* 104 */       throw new IllegalStateException("File has been moved - cannot be read again");
/*     */     }
/* 106 */     byte[] arrayOfByte = this.fileItem.get();
/* 107 */     return arrayOfByte != null ? arrayOfByte : new byte[0];
/*     */   }
/*     */ 
/*     */   @Override
public InputStream getInputStream() throws IOException {
/* 111 */     if (!isAvailable()) {
/* 112 */       throw new IllegalStateException("File has been moved - cannot be read again");
/*     */     }
/* 114 */     InputStream localInputStream = this.fileItem.getInputStream();
/* 115 */     return localInputStream != null ? localInputStream : new ByteArrayInputStream(new byte[0]);
/*     */   }
/*     */ 
/*     */   @Override
public void transferTo(File paramFile) throws IOException, IllegalStateException {
/* 119 */     if (!isAvailable()) {
/* 120 */       throw new IllegalStateException("File has already been moved - cannot be transferred again");
/*     */     }
/*     */ 
/* 123 */     if ((paramFile.exists()) && (!paramFile.delete())) {
/* 124 */       throw new IOException("Destination file [" + paramFile.getAbsolutePath() + "] already exists and could not be deleted");
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 129 */       this.fileItem.write(paramFile);
/* 130 */       if (logger.isDebugEnabled()) {
/* 131 */         String str = "transferred";
/* 132 */         if (!this.fileItem.isInMemory()) {
/* 133 */           str = isAvailable() ? "copied" : "moved";
/*     */         }
/* 135 */         logger.debug("Multipart file '" + getName() + "' with original filename [" + getOriginalFilename() + "], stored " + getStorageDescription() + ": " + str + " to [" + paramFile.getAbsolutePath() + "]");
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (FileUploadException localFileUploadException)
/*     */     {
/* 141 */       throw new IllegalStateException(localFileUploadException.getMessage());
/*     */     }
/*     */     catch (IOException localIOException) {
/* 144 */       throw localIOException;
/*     */     }
/*     */     catch (Exception localException) {
/* 147 */       logger.info("Could not transfer to file", localException);
/* 148 */       throw new IOException("Could not transfer to file: " + localException.getMessage());
/*     */     }
/*     */   }
/*     */ 
/*     */   protected boolean isAvailable()
/*     */   {
/* 158 */     if (this.fileItem.isInMemory()) {
/* 159 */       return true;
/*     */     }
/*     */ 
/* 162 */     if ((this.fileItem instanceof DefaultFileItem)) {
/* 163 */       return ((DefaultFileItem)this.fileItem).getStoreLocation().exists();
/*     */     }
/*     */ 
/* 166 */     return this.fileItem.getSize() == this.size;
/*     */   }
/*     */ 
/*     */   protected String getStorageDescription()
/*     */   {
/* 175 */     if (this.fileItem.isInMemory()) {
/* 176 */       return "in memory";
/*     */     }
/* 178 */     if ((this.fileItem instanceof DefaultFileItem)) {
/* 179 */       return "at [" + ((DefaultFileItem)this.fileItem).getStoreLocation().getAbsolutePath() + "]";
/*     */     }
/*     */ 
/* 182 */     return "on disk";
/*     */   }
/*     */ }

/* Location:           C:\Users\Administrator\Desktop\spring-1.2.6.jar
 * Qualified Name:     org.springframework.web.multipart.commons.CommonsMultipartFile
 * JD-Core Version:    0.6.0
 */