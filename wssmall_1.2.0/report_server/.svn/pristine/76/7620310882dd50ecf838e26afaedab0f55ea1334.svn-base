/*    */ package com.ztesoft.crm.edp.utils;
/*    */ 
/*    */ import java.io.UnsupportedEncodingException;
/*    */ import java.net.URLDecoder;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import javax.servlet.http.Cookie;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ 
/*    */ public class CommonUtil
/*    */ {
/*    */   public static final String COOKIE_LOGINKEY = "com.ztesoft.iportal.cookie.loginkey";
/*    */   public static final String COOKIE_USERINFO = "com.ztesoft.iportal.cookie.userinfo";
/*    */ 
/*    */   public static boolean isSignedOn(HttpServletRequest hreq)
/*    */   {
/* 17 */     return (getCookie(hreq, "com.ztesoft.iportal.cookie.loginkey") != null); }
/*    */ 
/*    */   public static String getCookie(HttpServletRequest hreq, String name) {
/* 20 */     Cookie[] cks = hreq.getCookies();
/* 21 */     String v = "";
/*    */ 
/* 23 */     if (cks != null) {
/* 24 */       for (int i = 0; i < cks.length; ++i) {
/* 25 */         Cookie ck = cks[i];
/*    */ 
/* 27 */         if (!(ck.getName().equals(name))) continue;
/*    */         try {
/* 29 */           v = URLDecoder.decode(ck.getValue(), "UTF-8");
/*    */         } catch (UnsupportedEncodingException e) {
/* 31 */           e.printStackTrace();
/*    */         }
/* 33 */         break;
/*    */       }
/*    */ 
/*    */     }
/*    */ 
/* 38 */     return v;
/*    */   }
/*    */ 
/*    */   public static String getLoginKey(HttpServletRequest request) {
/* 42 */     String loginKey = null;
/* 43 */     Cookie[] cks = request.getCookies();
/* 44 */     if (cks != null) {
/* 45 */       for (int i = 0; i < cks.length; ++i) {
/* 46 */         Cookie ck = cks[i];
/* 47 */         if (ck.getName().equals("com.ztesoft.iportal.cookie.loginkey")) {
/* 48 */           String v = ck.getValue();
/* 49 */           loginKey = v;
/* 50 */           break;
/*    */         }
/*    */       }
/*    */     }
/* 54 */     return loginKey;
/*    */   }
/*    */ 
/*    */   public static Map getSession(HttpServletRequest request)
/*    */     throws Exception
/*    */   {
/* 63 */     String userInfoStr = getCookie(
/* 64 */       request, "com.ztesoft.iportal.cookie.userinfo");
/* 65 */     Map loginInfo = convertStrToMap(userInfoStr);
/*    */ 
/* 67 */     return loginInfo;
/*    */   }
/*    */ 
/*    */   private static Map convertStrToMap(String userInfoStr) {
/* 71 */     String[] arr = userInfoStr.split("&");
/* 72 */     Map m = new HashMap();
/* 73 */     for (int i = 0; i < arr.length; ++i) {
/* 74 */       String knv = arr[i];
/* 75 */       String[] tmp = knv.split("=");
/* 76 */       if ((tmp != null) && (tmp.length == 2)) {
/* 77 */         m.put(tmp[0], tmp[1]);
/*    */       }
/*    */     }
/*    */ 
/* 81 */     return m;
/*    */   }
/*    */ }

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.edp.utils.CommonUtil
 * JD-Core Version:    0.5.3
 */