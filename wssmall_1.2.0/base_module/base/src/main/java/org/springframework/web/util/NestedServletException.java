/*     */ package org.springframework.web.util;
/*     */ 
/*     */

import javax.servlet.ServletException;
import java.io.PrintStream;
import java.io.PrintWriter;

/*     */
/*     */
/*     */ 
/*     */ public class NestedServletException extends ServletException
/*     */ {
/*     */   public NestedServletException(String paramString)
/*     */   {
/*  48 */     super(paramString);
/*     */   }
/*     */ 
/*     */   public NestedServletException(String paramString, Throwable paramThrowable)
/*     */   {
/*  58 */     super(paramString, paramThrowable);
/*     */   }
/*     */ 
/*     */   @Override
public Throwable getCause()
/*     */   {
/*  69 */     return getRootCause() == this ? null : getRootCause();
/*     */   }
/*     */ 
/*     */   @Override
public String getMessage()
/*     */   {
/*  77 */     if (getCause() == null) {
/*  78 */       return super.getMessage();
/*     */     }
/*     */ 
/*  81 */     return super.getMessage() + "; nested exception is " + getRootCause().getClass().getName() + ": " + getRootCause().getMessage();
/*     */   }
/*     */ 
/*     */   @Override
public void printStackTrace(PrintStream paramPrintStream)
/*     */   {
/*  91 */     if (getCause() == null) {
/*  92 */       super.printStackTrace(paramPrintStream);
/*     */     }
/*     */     else {
/*  95 */       paramPrintStream.println(this);
/*  96 */       getCause().printStackTrace(paramPrintStream);
/*     */     }
/*     */   }
/*     */ 
/*     */   @Override
public void printStackTrace(PrintWriter paramPrintWriter)
/*     */   {
/* 105 */     if (getCause() == null) {
/* 106 */       super.printStackTrace(paramPrintWriter);
/*     */     }
/*     */     else {
/* 109 */       paramPrintWriter.println(this);
/* 110 */       getCause().printStackTrace(paramPrintWriter);
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Administrator\Desktop\spring-1.2.6.jar
 * Qualified Name:     org.springframework.web.util.NestedServletException
 * JD-Core Version:    0.6.0
 */