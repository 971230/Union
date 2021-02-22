/*     */ package org.springframework.web.util;
/*     */ 
/*     */

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/*     */
/*     */
/*     */
/*     */ 
/*     */ public class CookieGenerator
/*     */ {
/*     */   public static final String DEFAULT_COOKIE_PATH = "/";
/*     */   public static final int DEFAULT_COOKIE_MAX_AGE = 2147483647;
/*  53 */   protected final Log logger = LogFactory.getLog(getClass());
/*     */   private String cookieName;
/*     */   private String cookieDomain;
/*  59 */   private String cookiePath = "/";
/*     */ 
/*  61 */   private int cookieMaxAge = 2147483647;
/*     */ 
/*     */   public void setCookieName(String paramString)
/*     */   {
/*  68 */     this.cookieName = paramString;
/*     */   }
/*     */ 
/*     */   public String getCookieName()
/*     */   {
/*  75 */     return this.cookieName;
/*     */   }
/*     */ 
/*     */   public void setCookieDomain(String paramString)
/*     */   {
/*  83 */     this.cookieDomain = paramString;
/*     */   }
/*     */ 
/*     */   public String getCookieDomain()
/*     */   {
/*  90 */     return this.cookieDomain;
/*     */   }
/*     */ 
/*     */   public void setCookiePath(String paramString)
/*     */   {
/*  98 */     this.cookiePath = paramString;
/*     */   }
/*     */ 
/*     */   public String getCookiePath()
/*     */   {
/* 105 */     return this.cookiePath;
/*     */   }
/*     */ 
/*     */   public void setCookieMaxAge(int paramInt)
/*     */   {
/* 113 */     this.cookieMaxAge = paramInt;
/*     */   }
/*     */ 
/*     */   public int getCookieMaxAge()
/*     */   {
/* 120 */     return this.cookieMaxAge;
/*     */   }
/*     */ 
/*     */   public void addCookie(HttpServletResponse paramHttpServletResponse, String paramString)
/*     */   {
/* 137 */     Cookie localCookie = createCookie(paramString);
/* 138 */     localCookie.setMaxAge(getCookieMaxAge());
/* 139 */     paramHttpServletResponse.addCookie(localCookie);
/* 140 */     if (this.logger.isDebugEnabled())
/* 141 */       this.logger.debug("Added cookie with name [" + getCookieName() + "] and value [" + paramString + "]");
/*     */   }
/*     */ 
/*     */   public void removeCookie(HttpServletResponse paramHttpServletResponse)
/*     */   {
/* 156 */     Cookie localCookie = createCookie("");
/* 157 */     localCookie.setMaxAge(0);
/* 158 */     paramHttpServletResponse.addCookie(localCookie);
/* 159 */     if (this.logger.isDebugEnabled())
/* 160 */       this.logger.debug("Removed cookie with name [" + getCookieName() + "]");
/*     */   }
/*     */ 
/*     */   protected Cookie createCookie(String paramString)
/*     */   {
/* 174 */     Cookie localCookie = new Cookie(getCookieName(), paramString);
/* 175 */     if (getCookieDomain() != null) {
/* 176 */       localCookie.setDomain(getCookieDomain());
/*     */     }
/* 178 */     localCookie.setPath(getCookiePath());
/* 179 */     return localCookie;
/*     */   }
/*     */ }

/* Location:           C:\Users\Administrator\Desktop\spring-1.2.6.jar
 * Qualified Name:     org.springframework.web.util.CookieGenerator
 * JD-Core Version:    0.6.0
 */