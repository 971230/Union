/*    */ package org.springframework.web.multipart.support;
/*    */ 
/*    */

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.propertyeditors.ByteArrayPropertyEditor;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/*    */
/*    */
/*    */
/*    */
/*    */ 
/*    */ public class ByteArrayMultipartFileEditor extends ByteArrayPropertyEditor
/*    */ {
/* 35 */   protected final Log logger = LogFactory.getLog(getClass());
/*    */ 
/*    */   @Override
public void setValue(Object paramObject) {
/* 38 */     if ((paramObject instanceof MultipartFile)) {
/* 39 */       MultipartFile localMultipartFile = (MultipartFile)paramObject;
/*    */       try {
/* 41 */         super.setValue(localMultipartFile.getBytes());
/*    */       }
/*    */       catch (IOException localIOException) {
/* 44 */         this.logger.info("Cannot read contents of multipart file", localIOException);
/* 45 */         throw new IllegalArgumentException("Cannot read contents of multipart file: " + localIOException.getMessage());
/*    */       }
/*    */     }
/* 48 */     else if ((paramObject instanceof byte[])) {
/* 49 */       super.setValue(paramObject);
/*    */     }
/*    */     else {
/* 52 */       super.setValue(paramObject != null ? paramObject.toString().getBytes() : null);
/*    */     }
/*    */   }
/*    */ 
/*    */   @Override
public String getAsText() {
/* 57 */     byte[] arrayOfByte = (byte[])getValue();
/* 58 */     return arrayOfByte != null ? new String(arrayOfByte) : "";
/*    */   }
/*    */ }

/* Location:           C:\Users\Administrator\Desktop\spring-1.2.6.jar
 * Qualified Name:     org.springframework.web.multipart.support.ByteArrayMultipartFileEditor
 * JD-Core Version:    0.6.0
 */