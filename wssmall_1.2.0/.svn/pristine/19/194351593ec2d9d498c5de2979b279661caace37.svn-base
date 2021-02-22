/*     */ package org.springframework.web.util;
/*     */ 
/*     */

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.JdkVersion;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */ 
/*     */ public class UrlPathHelper
/*     */ {
/*     */   public static final String INCLUDE_URI_REQUEST_ATTRIBUTE = "javax.servlet.include.request_uri";
/*     */   public static final String INCLUDE_CONTEXT_PATH_REQUEST_ATTRIBUTE = "javax.servlet.include.context_path";
/*     */   public static final String INCLUDE_SERVLET_PATH_REQUEST_ATTRIBUTE = "javax.servlet.include.servlet_path";
/*  56 */   private final Log logger = LogFactory.getLog(getClass());
/*     */ 
/*  58 */   private boolean alwaysUseFullPath = false;
/*     */ 
/*  60 */   private boolean urlDecode = false;
/*     */ 
/*  62 */   private String defaultEncoding = "ISO-8859-1";
/*     */ 
/*     */   public void setAlwaysUseFullPath(boolean paramBoolean)
/*     */   {
/*  72 */     this.alwaysUseFullPath = paramBoolean;
/*     */   }
/*     */ 
/*     */   public void setUrlDecode(boolean paramBoolean)
/*     */   {
/*  93 */     this.urlDecode = paramBoolean;
/*     */   }
/*     */ 
/*     */   public void setDefaultEncoding(String paramString)
/*     */   {
/* 110 */     this.defaultEncoding = paramString;
/*     */   }
/*     */ 
/*     */   protected String getDefaultEncoding()
/*     */   {
/* 117 */     return this.defaultEncoding;
/*     */   }
/*     */ 
/*     */   public String getLookupPathForRequest(HttpServletRequest paramHttpServletRequest)
/*     */   {
/* 132 */     if (this.alwaysUseFullPath) {
/* 133 */       return getPathWithinApplication(paramHttpServletRequest);
/*     */     }
/*     */ 
/* 136 */     String str = getPathWithinServletMapping(paramHttpServletRequest);
/* 137 */     if (!"".equals(str)) {
/* 138 */       return str;
/*     */     }
/*     */ 
/* 141 */     return getPathWithinApplication(paramHttpServletRequest);
/*     */   }
/*     */ 
/*     */   public String getPathWithinServletMapping(HttpServletRequest paramHttpServletRequest)
/*     */   {
/* 157 */     String str1 = getPathWithinApplication(paramHttpServletRequest);
/* 158 */     String str2 = getServletPath(paramHttpServletRequest);
/* 159 */     if (str1.startsWith(str2))
/*     */     {
/* 161 */       return str1.substring(str2.length());
/*     */     }
/*     */ 
/* 167 */     return str2;
/*     */   }
/*     */ 
/*     */   public String getPathWithinApplication(HttpServletRequest paramHttpServletRequest)
/*     */   {
/* 178 */     String str1 = getContextPath(paramHttpServletRequest);
/* 179 */     String str2 = getRequestUri(paramHttpServletRequest);
/* 180 */     if (StringUtils.startsWithIgnoreCase(str2, str1))
/*     */     {
/* 182 */       String str3 = str2.substring(str1.length());
/* 183 */       return StringUtils.hasText(str3) ? str3 : "/";
/*     */     }
/*     */ 
/* 187 */     return str2;
/*     */   }
/*     */ 
/*     */   public String getServletPath(HttpServletRequest paramHttpServletRequest)
/*     */   {
/* 200 */     String str = (String)paramHttpServletRequest.getAttribute("javax.servlet.include.servlet_path");
/* 201 */     if (str == null) {
/* 202 */       str = paramHttpServletRequest.getServletPath();
/*     */     }
/* 204 */     return str;
/*     */   }
/*     */ 
/*     */   public String getContextPath(HttpServletRequest paramHttpServletRequest)
/*     */   {
/* 216 */     String str = (String)paramHttpServletRequest.getAttribute("javax.servlet.include.context_path");
/* 217 */     if (str == null) {
/* 218 */       str = paramHttpServletRequest.getContextPath();
/*     */     }
/* 220 */     return decodeRequestString(paramHttpServletRequest, str);
/*     */   }
/*     */ 
/*     */   public String getRequestUri(HttpServletRequest paramHttpServletRequest)
/*     */   {
/* 235 */     String str = (String)paramHttpServletRequest.getAttribute("javax.servlet.include.request_uri");
/* 236 */     if (str == null) {
/* 237 */       str = paramHttpServletRequest.getRequestURI();
/*     */     }
/* 239 */     str = decodeRequestString(paramHttpServletRequest, str);
/* 240 */     int i = str.indexOf(';');
/* 241 */     return i != -1 ? str.substring(0, i) : str;
/*     */   }
/*     */ 
/*     */   public String decodeRequestString(HttpServletRequest paramHttpServletRequest, String paramString)
/*     */   {
/* 259 */     if (this.urlDecode) {
/* 260 */       String str = determineEncoding(paramHttpServletRequest);
/*     */       try {
/* 262 */         if (JdkVersion.getMajorJavaVersion() < 1) {
/* 263 */           throw new UnsupportedEncodingException("JDK 1.3 URLDecoder does not support custom encoding");
/*     */         }
/* 265 */         return URLDecoder.decode(paramString, str);
/*     */       }
/*     */       catch (UnsupportedEncodingException localUnsupportedEncodingException) {
/* 268 */         if (this.logger.isWarnEnabled()) {
/* 269 */           this.logger.warn("Could not decode request string [" + paramString + "] with encoding '" + str + "': falling back to platform default encoding; exception message: " + localUnsupportedEncodingException.getMessage());
/*     */         }
/*     */ 
/* 272 */         return URLDecoder.decode(paramString);
/*     */       }
/*     */     }
/* 275 */     return paramString;
/*     */   }
/*     */ 
/*     */   protected String determineEncoding(HttpServletRequest paramHttpServletRequest)
/*     */   {
/* 289 */     String str = paramHttpServletRequest.getCharacterEncoding();
/* 290 */     if (str == null) {
/* 291 */       str = getDefaultEncoding();
/*     */     }
/* 293 */     return str;
/*     */   }
/*     */ }

/* Location:           C:\Users\Administrator\Desktop\spring-1.2.6.jar
 * Qualified Name:     org.springframework.web.util.UrlPathHelper
 * JD-Core Version:    0.6.0
 */