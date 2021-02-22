/*     */ package org.springframework.web.multipart.support;
/*     */ 
/*     */

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
/*     */ public class MultipartFilter extends OncePerRequestFilter
/*     */ {
/*     */   public static final String DEFAULT_MULTIPART_RESOLVER_BEAN_NAME = "filterMultipartResolver";
/*  64 */   private String multipartResolverBeanName = "filterMultipartResolver";
/*     */ 
/*     */   public void setMultipartResolverBeanName(String paramString)
/*     */   {
/*  72 */     this.multipartResolverBeanName = paramString;
/*     */   }
/*     */ 
/*     */   protected String getMultipartResolverBeanName()
/*     */   {
/*  80 */     return this.multipartResolverBeanName;
/*     */   }
/*     */ 
/*     */   @Override
protected void doFilterInternal(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, FilterChain paramFilterChain)
/*     */     throws ServletException, IOException
/*     */   {
/*  95 */     MultipartResolver localMultipartResolver = lookupMultipartResolver(paramHttpServletRequest);
/*     */ 
/*  97 */     Object localObject1 = paramHttpServletRequest;
/*  98 */     if (localMultipartResolver.isMultipart((HttpServletRequest)localObject1)) {
/*  99 */       if (this.logger.isDebugEnabled()) {
/* 100 */         this.logger.debug("Resolving multipart request [" + ((HttpServletRequest)localObject1).getRequestURI() + "] with MultipartFilter");
/*     */       }
/*     */ 
/* 103 */       localObject1 = localMultipartResolver.resolveMultipart((HttpServletRequest)localObject1);
/*     */     }
/* 106 */     else if (this.logger.isDebugEnabled()) {
/* 107 */       this.logger.debug("Request [" + ((HttpServletRequest)localObject1).getRequestURI() + "] is not a multipart request");
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 112 */       paramFilterChain.doFilter((ServletRequest)localObject1, paramHttpServletResponse);
/*     */     }
/*     */     finally {
/* 115 */       if ((localObject1 instanceof MultipartHttpServletRequest))
/* 116 */         localMultipartResolver.cleanupMultipart((MultipartHttpServletRequest)localObject1);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected MultipartResolver lookupMultipartResolver(HttpServletRequest paramHttpServletRequest)
/*     */   {
/* 130 */     return lookupMultipartResolver();
/*     */   }
/*     */ 
/*     */   protected MultipartResolver lookupMultipartResolver()
/*     */   {
/* 142 */     if (this.logger.isDebugEnabled()) {
/* 143 */       this.logger.debug("Using MultipartResolver '" + getMultipartResolverBeanName() + "' for MultipartFilter");
/*     */     }
/* 145 */     WebApplicationContext localWebApplicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
/*     */ 
/* 147 */     return localWebApplicationContext.getBean(getMultipartResolverBeanName(), MultipartResolver.class);
/*     */   }
/*     */ }

/* Location:           C:\Users\Administrator\Desktop\spring-1.2.6.jar
 * Qualified Name:     org.springframework.web.multipart.support.MultipartFilter
 * JD-Core Version:    0.6.0
 */