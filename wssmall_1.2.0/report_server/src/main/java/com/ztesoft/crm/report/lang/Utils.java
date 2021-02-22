/*     */ package com.ztesoft.crm.report.lang;
/*     */ 
/*     */ import com.ztesoft.crm.report.ReportContext;
/*     */ import java.io.File;
/*     */ import java.text.DecimalFormat;
/*     */ import java.text.ParseException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ public final class Utils
/*     */ {
/*     */   public static final String encode(String v)
/*     */   {
/*  25 */     return v;
/*     */   }
/*     */ 
/*     */   public static final float parseFloat(String v, float df) {
/*     */     try {
/*  30 */       return Float.parseFloat(v);
/*     */     } catch (Exception localException) {
/*     */     }
/*  33 */     return df;
/*     */   }
/*     */ 
/*     */   public static final String encode(String v, String destEncoding)
/*     */   {
/*  38 */     return encode(v, ReportContext.getContext().getSystemEncoding(), 
/*  39 */       destEncoding);
/*     */   }
/*     */ 
/*     */   public static final String customEncode(String s) {
/*  43 */     char[] buff = s.toCharArray();
/*  44 */     StringBuffer sb = new StringBuffer();
/*  45 */     for (int i = 0; i < buff.length; ++i) {
/*  46 */       char c = buff[i];
/*  47 */       if (((c >= '0') && (c <= '9')) || ((c >= 'A') && (c <= 'Z')) || (
/*  48 */         (c >= 'a') && (c <= 'z'))) {
/*  49 */         sb.append(c);
/*     */       } else {
/*  51 */         String a = Integer.toHexString(c);
/*  52 */         sb.append("$");
/*  53 */         if (a.length() == 1)
/*  54 */           sb.append("000");
/*  55 */         if (a.length() == 2)
/*  56 */           sb.append("00");
/*  57 */         if (a.length() == 3)
/*  58 */           sb.append("0");
/*  59 */         sb.append(a);
/*     */       }
/*     */     }
/*  62 */     buff = null;
/*  63 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static final String customDecode(String s) {
/*  67 */     if (s == null)
/*  68 */       return "";
/*  69 */     if (isEmpty(s)) {
/*  70 */       return s;
/*     */     }
/*  72 */     if (s.indexOf("$") < 0) {
/*  73 */       return s;
/*     */     }
/*  75 */     char[] buff = s.toCharArray();
/*  76 */     StringBuffer sb = new StringBuffer();
/*  77 */     for (int i = 0; i < buff.length; ++i) {
/*  78 */       char c = buff[i];
/*     */ 
/*  80 */       if (c == '$') {
/*  81 */         StringBuffer word = new StringBuffer();
/*  82 */         for (int n = 0; n < 4; ++n)
/*     */         {
/*  84 */           word.append(buff[(++i)]);
/*     */         }
/*     */ 
/*  87 */         sb.append((char)Integer.parseInt(word.toString(), 16));
/*     */       }
/*     */       else {
/*  90 */         sb.append(c);
/*     */       }
/*     */     }
/*  93 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static final String formatFilePath(String[] args) {
/*  97 */     StringBuffer p = new StringBuffer();
/*  98 */     for (int i = 0; i < args.length; ++i) {
/*  99 */       if ((i > 0) && 
/* 100 */         (!(p.toString().endsWith("/"))) && 
/* 101 */         (!(p.toString().endsWith(
/* 102 */         String.valueOf(File.separatorChar)))) && 
/* 103 */         (!(args[i].endsWith("/"))) && 
/* 104 */         (!(args[i].endsWith(
/* 105 */         String.valueOf(File.separatorChar))))) {
/* 106 */         p.append(File.separatorChar);
/*     */       }
/*     */ 
/* 110 */       p.append(args[i]);
/*     */     }
/* 112 */     return p.toString();
/*     */   }
/*     */ 
/*     */   public static final String formatUrlPath(String[] args) {
/* 116 */     StringBuffer p = new StringBuffer();
/* 117 */     for (int i = 0; i < args.length; ++i)
/*     */     {
/* 119 */       args[i] = args[i].replaceAll("\\\\", "/");
/* 120 */       args[i] = args[i].replaceAll("//", "/");
/* 121 */       if ((i > 0) && (!(p.toString().endsWith("/"))) && 
/* 122 */         (!(args[i].startsWith("/")))) {
/* 123 */         p.append("/");
/*     */       }
/*     */ 
/* 126 */       p.append(args[i]);
/*     */     }
/* 128 */     return p.toString().replaceAll("//", "/");
/*     */   }
/*     */ 
/*     */   public static final String format(String str, String[] args) {
/* 132 */     Matcher m = Pattern.compile("[{][0-9]+[}]").matcher(str);
/* 133 */     StringBuffer sb = new StringBuffer();
/* 134 */     while (m.find()) {
/* 135 */       String b = m.group();
/* 136 */       b = b.replaceFirst("[{]", "");
/* 137 */       b = b.replaceFirst("[}]", "");
/* 138 */       int i = Integer.parseInt(b);
/*     */ 
/* 140 */       if ((i < 0) || (i >= args.length))
/*     */         continue;
/* 142 */       m.appendReplacement(sb, (args[i] == null) ? "" : 
/* 143 */         customDecode(args[i]));
/*     */     }
/*     */ 
/* 147 */     m.appendTail(sb);
/* 148 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static final String format(Date d, String p) {
/*     */     try {
/* 153 */       return new SimpleDateFormat(p).format(d);
/*     */     } catch (Exception localException) {
/*     */     }
/* 156 */     return d.toString();
/*     */   }
/*     */ 
/*     */   public static final String format(String str, Object o) {
/* 160 */     if (isEmpty(str))
/* 161 */       return "";
/* 162 */     if (isEmpty(o))
/* 163 */       return str;
/* 164 */     Matcher m = Pattern.compile("[{][A-Za-z0-9_]+[}]").matcher(str);
/* 165 */     StringBuffer sb = new StringBuffer();
/* 166 */     while (m.find()) {
/* 167 */       String b = m.group();
/* 168 */       b = b.replaceAll("[{]|[}]", "");
/*     */ 
/* 170 */       m.appendReplacement(sb, customDecode(
/* 171 */         ObjectPropertyUtils.getString(o, b)));
/*     */     }
/*     */ 
/* 174 */     m.appendTail(sb);
/* 175 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static final String replace(String sb, String name, String value) {
/* 179 */     if (isEmpty(name))
/* 180 */       return sb;
/* 181 */     if (isEmpty(sb)) {
/* 182 */       return sb;
/*     */     }
/* 184 */     if (value == null) {
/* 185 */       value = "";
/*     */     }
/* 187 */     return sb.replaceAll(
/* 188 */       "[{]" + name + "[}]", value);
/*     */   }
/*     */ 
/*     */   public static final String numberFormat(String v, String format) {
/*     */     try {
/* 193 */       return new DecimalFormat(format).format(Double.parseDouble(v)); } catch (Exception e) {
/*     */     }
/* 195 */     return v;
/*     */   }
/*     */ 
/*     */   public static final String encode(String v, String srcEncoding, String destEncoding)
/*     */   {
/* 201 */     if ((isEmpty(srcEncoding)) || (isEmpty(destEncoding)))
/* 202 */       return v;
/* 203 */     if (srcEncoding.equals(destEncoding))
/* 204 */       return v;
/* 205 */     if (v == null)
/* 206 */       return v;
/*     */     try {
/* 208 */       return new String(v.getBytes(srcEncoding), destEncoding);
/*     */     } catch (Exception localException) {
/*     */     }
/* 211 */     return v;
/*     */   }
/*     */ 
/*     */   public static final boolean isEmpty(Object v) {
/* 215 */     if (v == null)
/* 216 */       return true;
/* 217 */     if (v instanceof String)
/* 218 */       return "".equals(((String)v).trim());
/* 219 */     if (v instanceof String[]) {
/* 220 */       return (((String[])v).length == 0);
/*     */     }
/* 222 */     if (v instanceof List)
/* 223 */       return (((List)v).size() == 0);
/* 224 */     if (v instanceof Map)
/* 225 */       return ((Map)v).isEmpty();
/* 226 */     return false;
/*     */   }
/*     */ 
/*     */   public static final boolean isEmpty(String v) {
/* 230 */     return ((v == null) || (v.length() == 0));
/*     */   }
/*     */ 
/*     */   public static final boolean isTrue(String t)
/*     */   {
/* 240 */     return "true".equalsIgnoreCase(t);
/*     */   }
/*     */ 
/*     */   public static final boolean isFalse(String t) {
/* 244 */     return "false".equalsIgnoreCase(t);
/*     */   }
/*     */ 
/*     */   public static final Date parseDate(String v, String partten) throws ParseException
/*     */   {
/* 249 */     return new SimpleDateFormat(partten).parse(v);
/*     */   }
/*     */ 
/*     */   public static final int parseInt(String v, int f)
/*     */   {
/*     */     try
/*     */     {
/* 261 */       return Integer.parseInt(v);
/*     */     } catch (Exception localException) {
/*     */     }
/* 264 */     return f;
/*     */   }
/*     */ 
/*     */   public static final String format(String[] args, char c) {
/* 268 */     StringBuffer sb = new StringBuffer();
/* 269 */     for (int i = 0; i < args.length; ++i) {
/* 270 */       sb.append(args[i]);
/* 271 */       if (i < args.length - 1)
/* 272 */         sb.append(c);
/*     */     }
/* 274 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static final int compare(String v1, String v2) {
/* 278 */     if ((v1 == null) && (v2 == null))
/* 279 */       return 0;
/* 280 */     return v1.compareTo(v2);
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/* 291 */     System.out.println(92);
/* 292 */     System.out
/* 293 */       .println(
/* 294 */       customDecode("$0028$4e00$0029$5c40$65b9$7ebf$8def$539f$56e0$002d$003e$8d44$6e90$95ee$9898$88c5$79fb$673a$63d0$901f$672a$5b9e$73b0$002d$003e$6ca1$6709$7ebf$8def$002f$7aef$53e3$002f$8bbe$5907$539f$56e0"));
/*     */   }
/*     */ }

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.lang.Utils
 * JD-Core Version:    0.5.3
 */