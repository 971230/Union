/*    */ package org.springframework.web.multipart.support;
/*    */ 
/*    */

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */ 
/*    */ public abstract class AbstractMultipartHttpServletRequest extends HttpServletRequestWrapper
/*    */   implements MultipartHttpServletRequest
/*    */ {
/*    */   private Map multipartFiles;
/*    */ 
/*    */   protected AbstractMultipartHttpServletRequest(HttpServletRequest paramHttpServletRequest)
/*    */   {
/* 46 */     super(paramHttpServletRequest);
/*    */   }
/*    */ 
/*    */   protected void setMultipartFiles(Map paramMap)
/*    */   {
/* 54 */     this.multipartFiles = Collections.unmodifiableMap(paramMap);
/*    */   }
/*    */ 
/*    */   @Override
public Iterator getFileNames() {
/* 58 */     return this.multipartFiles.keySet().iterator();
/*    */   }
/*    */ 
/*    */   @Override
public MultipartFile getFile(String paramString) {
/* 62 */     return (MultipartFile)this.multipartFiles.get(paramString);
/*    */   }
/*    */ 
/*    */   @Override
public Map getFileMap() {
/* 66 */     return this.multipartFiles;
/*    */   }
/*    */ }

/* Location:           C:\Users\Administrator\Desktop\spring-1.2.6.jar
 * Qualified Name:     org.springframework.web.multipart.support.AbstractMultipartHttpServletRequest
 * JD-Core Version:    0.6.0
 */