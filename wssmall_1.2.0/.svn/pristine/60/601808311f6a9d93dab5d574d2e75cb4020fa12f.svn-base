/*     */ package org.springframework.web.util;
/*     */ 
/*     */

import org.springframework.util.StringUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

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
/*     */
/*     */
/*     */
/*     */ 
/*     */ public abstract class WebUtils
/*     */ {
/*     */   public static final String DEFAULT_CHARACTER_ENCODING = "ISO-8859-1";
/*     */   public static final String TEMP_DIR_CONTEXT_ATTRIBUTE = "javax.servlet.context.tempdir";
/*     */   public static final String HTML_ESCAPE_CONTEXT_PARAM = "defaultHtmlEscape";
/*     */   public static final String WEB_APP_ROOT_KEY_PARAM = "webAppRootKey";
/*     */   public static final String DEFAULT_WEB_APP_ROOT_KEY = "webapp.root";
/*  72 */   public static final String[] SUBMIT_IMAGE_SUFFIXES = { ".x", ".y" };
/*     */ 
/*     */   public static void setWebAppRootSystemProperty(ServletContext paramServletContext)
/*     */     throws IllegalStateException
/*     */   {
/*  90 */     String str1 = paramServletContext.getRealPath("/");
/*  91 */     if (str1 == null) {
/*  92 */       throw new IllegalStateException("Cannot set web app root system property when WAR file is not expanded");
/*     */     }
/*     */ 
/*  95 */     String str2 = paramServletContext.getInitParameter("webAppRootKey");
/*  96 */     String str3 = str2 != null ? str2 : "webapp.root";
/*  97 */     String str4 = System.getProperty(str3);
/*  98 */     if ((str4 != null) && (!StringUtils.pathEquals(str4, str1))) {
/*  99 */       throw new IllegalStateException("Web app root system property already set to different value: '" + str3 + "' = [" + str4 + "] instead of [" + str1 + "] - " + "Choose unique values for the 'webAppRootKey' context-param in your web.xml files!");
/*     */     }
/*     */ 
/* 104 */     System.setProperty(str3, str1);
/* 105 */     paramServletContext.log("Set web app root system property: '" + str3 + "' = [" + str1 + "]");
/*     */   }
/*     */ 
/*     */   public static void removeWebAppRootSystemProperty(ServletContext paramServletContext)
/*     */   {
/* 115 */     String str1 = paramServletContext.getInitParameter("webAppRootKey");
/* 116 */     String str2 = str1 != null ? str1 : "webapp.root";
/* 117 */     System.getProperties().remove(str2);
/*     */   }
/*     */ 
/*     */   public static boolean isDefaultHtmlEscape(ServletContext paramServletContext)
/*     */   {
/* 127 */     String str = paramServletContext.getInitParameter("defaultHtmlEscape");
/* 128 */     return Boolean.valueOf(str).booleanValue();
/*     */   }
/*     */ 
/*     */   public static File getTempDir(ServletContext paramServletContext)
/*     */   {
/* 138 */     return (File)paramServletContext.getAttribute("javax.servlet.context.tempdir");
/*     */   }
/*     */ 
/*     */   public static String getRealPath(ServletContext paramServletContext, String paramString)
/*     */     throws FileNotFoundException
/*     */   {
/* 155 */     if (!paramString.startsWith("/")) {
/* 156 */       paramString = "/" + paramString;
/*     */     }
/* 158 */     String str = paramServletContext.getRealPath(paramString);
/* 159 */     if (str == null) {
/* 160 */       throw new FileNotFoundException("ServletContext resource [" + paramString + "] cannot be resolved to absolute file path - " + "web application archive not expanded?");
/*     */     }
/*     */ 
/* 164 */     return str;
/*     */   }
/*     */ 
/*     */   public static String getSessionId(HttpServletRequest paramHttpServletRequest)
/*     */   {
/* 174 */     HttpSession localHttpSession = paramHttpServletRequest.getSession(false);
/* 175 */     return localHttpSession != null ? localHttpSession.getId() : null;
/*     */   }
/*     */ 
/*     */   public static Object getSessionAttribute(HttpServletRequest paramHttpServletRequest, String paramString)
/*     */   {
/* 187 */     HttpSession localHttpSession = paramHttpServletRequest.getSession(false);
/* 188 */     return localHttpSession != null ? localHttpSession.getAttribute(paramString) : null;
/*     */   }
/*     */ 
/*     */   public static Object getRequiredSessionAttribute(HttpServletRequest paramHttpServletRequest, String paramString)
/*     */     throws IllegalStateException
/*     */   {
/* 203 */     Object localObject = getSessionAttribute(paramHttpServletRequest, paramString);
/* 204 */     if (localObject == null) {
/* 205 */       throw new IllegalStateException("No session attribute '" + paramString + "' found");
/*     */     }
/* 207 */     return localObject;
/*     */   }
/*     */ 
/*     */   public static void setSessionAttribute(HttpServletRequest paramHttpServletRequest, String paramString, Object paramObject)
/*     */   {
/* 218 */     if (paramObject != null) {
/* 219 */       paramHttpServletRequest.getSession().setAttribute(paramString, paramObject);
/*     */     }
/*     */     else {
/* 222 */       HttpSession localHttpSession = paramHttpServletRequest.getSession(false);
/* 223 */       if (localHttpSession != null)
/* 224 */         localHttpSession.removeAttribute(paramString);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static Object getOrCreateSessionAttribute(HttpSession paramHttpSession, String paramString, Class paramClass)
/*     */     throws IllegalArgumentException
/*     */   {
/* 242 */     Object localObject = paramHttpSession.getAttribute(paramString);
/* 243 */     if (localObject == null) {
/*     */       try {
/* 245 */         localObject = paramClass.newInstance();
/*     */       }
/*     */       catch (InstantiationException localInstantiationException) {
/* 248 */         throw new IllegalArgumentException("Could not instantiate class [" + paramClass.getName() + "] for session attribute '" + paramString + "': " + localInstantiationException.getMessage());
/*     */       }
/*     */       catch (IllegalAccessException localIllegalAccessException)
/*     */       {
/* 253 */         throw new IllegalArgumentException("Could not access default constructor of class [" + paramClass.getName() + "] for session attribute '" + paramString + "': " + localIllegalAccessException.getMessage());
/*     */       }
/*     */ 
/* 257 */       paramHttpSession.setAttribute(paramString, localObject);
/*     */     }
/* 259 */     return localObject;
/*     */   }
/*     */ 
/*     */   public static void exposeRequestAttributes(ServletRequest paramServletRequest, Map paramMap)
/*     */     throws IllegalArgumentException
/*     */   {
/* 273 */     Iterator localIterator = paramMap.entrySet().iterator();
/* 274 */     while (localIterator.hasNext()) {
/* 275 */       Map.Entry localEntry = (Map.Entry)localIterator.next();
/* 276 */       if (!(localEntry.getKey() instanceof String)) {
/* 277 */         throw new IllegalArgumentException("Invalid key [" + localEntry.getKey() + "] in attributes Map - only Strings allowed as attribute keys");
/*     */       }
/*     */ 
/* 280 */       paramServletRequest.setAttribute((String)localEntry.getKey(), localEntry.getValue());
/*     */     }
/*     */   }
/*     */ 
/*     */   public static Cookie getCookie(HttpServletRequest paramHttpServletRequest, String paramString)
/*     */   {
/* 291 */     Cookie[] arrayOfCookie = paramHttpServletRequest.getCookies();
/* 292 */     if (arrayOfCookie != null) {
/* 293 */       for (int i = 0; i < arrayOfCookie.length; i++) {
/* 294 */         if (paramString.equals(arrayOfCookie[i].getName())) {
/* 295 */           return arrayOfCookie[i];
/*     */         }
/*     */       }
/*     */     }
/* 299 */     return null;
/*     */   }
/*     */ 
/*     */   public static boolean hasSubmitParameter(ServletRequest paramServletRequest, String paramString)
/*     */   {
/* 312 */     if (paramServletRequest.getParameter(paramString) != null) {
/* 313 */       return true;
/*     */     }
/* 315 */     for (int i = 0; i < SUBMIT_IMAGE_SUFFIXES.length; i++) {
/* 316 */       String str = SUBMIT_IMAGE_SUFFIXES[i];
/* 317 */       if (paramServletRequest.getParameter(paramString + str) != null) {
/* 318 */         return true;
/*     */       }
/*     */     }
/* 321 */     return false;
/*     */   }
/*     */ 
/*     */   public static Map getParametersStartingWith(ServletRequest paramServletRequest, String paramString)
/*     */   {
/* 341 */     Enumeration localEnumeration = paramServletRequest.getParameterNames();
/* 342 */     TreeMap localTreeMap = new TreeMap();
/* 343 */     if (paramString == null) {
/* 344 */       paramString = "";
/*     */     }
/* 346 */     while ((localEnumeration != null) && (localEnumeration.hasMoreElements())) {
/* 347 */       String str1 = (String)localEnumeration.nextElement();
/* 348 */       if (("".equals(paramString)) || (str1.startsWith(paramString))) {
/* 349 */         String str2 = str1.substring(paramString.length());
/* 350 */         String[] arrayOfString = paramServletRequest.getParameterValues(str1);
/* 351 */         if (arrayOfString != null)
/*     */         {
/* 354 */           if (arrayOfString.length > 1) {
/* 355 */             localTreeMap.put(str2, arrayOfString);
/*     */           }
/*     */           else
/* 358 */             localTreeMap.put(str2, arrayOfString[0]);
/*     */         }
/*     */       }
/*     */     }
/* 362 */     return localTreeMap;
/*     */   }
/*     */ 
/*     */   public static String extractFilenameFromUrlPath(String paramString)
/*     */   {
/* 372 */     int i = paramString.lastIndexOf('/') + 1;
/* 373 */     int j = paramString.indexOf(';');
/* 374 */     if (j == -1) {
/* 375 */       j = paramString.indexOf('?');
/* 376 */       if (j == -1) {
/* 377 */         j = paramString.length();
/*     */       }
/*     */     }
/* 380 */     String str = paramString.substring(i, j);
/* 381 */     int k = str.lastIndexOf('.');
/* 382 */     if (k != -1) {
/* 383 */       str = str.substring(0, k);
/*     */     }
/* 385 */     return str;
/*     */   }
/*     */ }

/* Location:           C:\Users\Administrator\Desktop\spring-1.2.6.jar
 * Qualified Name:     org.springframework.web.util.WebUtils
 * JD-Core Version:    0.6.0
 */