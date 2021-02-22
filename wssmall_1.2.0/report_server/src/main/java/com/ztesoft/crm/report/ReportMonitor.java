/*     */ package com.ztesoft.crm.report;
/*     */ 
/*     */ import com.ztesoft.crm.report.lang.ArrayListEx;
/*     */ import com.ztesoft.crm.report.lang.Utils;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ 
/*     */ public final class ReportMonitor
/*     */ {
/*  26 */   private static final ReportMonitor monitor = new ReportMonitor();
/*     */ 
/*  32 */   private HashMap map = new HashMap();
/*     */ 
/*     */   public static final synchronized ReportMonitor getMonitor()
/*     */   {
/*  29 */     return monitor;
/*     */   }
/*     */ 
/*     */   public void log(String path, String sql, Date startTime, Date endTime, boolean error, String errorMessage)
/*     */   {
/*  36 */     if (!(Utils.isTrue(ReportContext.getContext().getEnableMonitor()))) {
/*  37 */       return;
/*     */     }
/*  39 */     HashMap log = new HashMap();
/*     */ 
/*  41 */     List logs = (List)this.map.get(path);
/*  42 */     if (logs == null) {
/*  43 */       logs = new ArrayListEx();
/*  44 */       this.map.put(path, logs);
/*     */     }
/*  46 */     if (logs.size() >= 20) {
/*  47 */       logs.remove(0);
/*     */     }
/*  49 */     logs.add(log);
/*  50 */     log.put("sql", sql);
/*  51 */     log.put("startTime", Utils.format(startTime, "yyyy-MM-dd HH:mm:ss"));
/*  52 */     log.put("endTime", Utils.format(endTime, "yyyy-MM-dd HH:mm:ss"));
/*  53 */     log.put("elapsedTime", 
/*  54 */       String.valueOf(endTime.getTime() - 
/*  54 */       startTime.getTime()));
/*  55 */     log.put("error", String.valueOf(error));
/*  56 */     log.put("errorMessage", errorMessage);
/*     */   }
/*     */ 
/*     */   public List getReportTree()
/*     */   {
/*  61 */     ArrayListEx arr = new ArrayListEx();
/*  62 */     HashMap temp = new HashMap();
/*     */ 
/*  64 */     for (Iterator objs = this.map.keySet().iterator(); objs.hasNext(); ) {
/*  65 */       String key = (String)objs.next();
/*     */ 
/*  67 */       HashMap o = createFile(temp, key);
/*  68 */       if (o != null)
/*  69 */         arr.add(o);
/*     */     }
/*  71 */     temp.clear();
/*  72 */     return arr;
/*     */   }
/*     */ 
/*     */   public List getLogs(String path) {
/*  76 */     return ((List)this.map.get(path));
/*     */   }
/*     */ 
/*     */   public void clearLogs(String path) {
/*  80 */     if (Utils.isEmpty(path))
/*  81 */       this.map.clear();
/*     */     else
/*  83 */       this.map.remove(path);
/*     */   }
/*     */ 
/*     */   private HashMap createFile(HashMap temp, String key)
/*     */   {
/*  88 */     String[] args = key.split("[/]");
/*  89 */     if (args.length < 2) {
/*  90 */       return null;
/*     */     }
/*  92 */     HashMap folder = (HashMap)temp.get(args[1]);
/*  93 */     if (folder == null) {
/*  94 */       folder = new HashMap();
/*  95 */       folder.put("text", args[1]);
/*  96 */       folder.put("value", key);
/*  97 */       folder.put("leaf", "true");
/*     */     }
/*  99 */     HashMap parent = folder;
/* 100 */     for (int i = 2; i < args.length; ++i) {
/* 101 */       parent.put("leaf", "false");
/* 102 */       HashMap child = new HashMap();
/*     */ 
/* 104 */       child.put("text", args[i]);
/* 105 */       child.put("value", key);
/* 106 */       child.put("leaf", "true");
/*     */ 
/* 108 */       List childs = (List)parent.get("children");
/* 109 */       if (childs == null) {
/* 110 */         childs = new ArrayListEx();
/* 111 */         parent.put("children", childs);
/*     */       }
/* 113 */       childs.add(child);
/* 114 */       parent = child;
/*     */     }
/*     */ 
/* 117 */     if (temp.containsKey(args[1])) {
/* 118 */       return null;
/*     */     }
/* 120 */     temp.put(args[1], folder);
/* 121 */     return folder;
/*     */   }
/*     */ }

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.ReportMonitor
 * JD-Core Version:    0.5.3
 */