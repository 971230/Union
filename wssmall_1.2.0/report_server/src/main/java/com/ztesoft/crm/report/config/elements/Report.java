/*     */ package com.ztesoft.crm.report.config.elements;
/*     */ 
/*     */ import com.ztesoft.crm.report.db.getter.DimensionGetter;
/*     */ import com.ztesoft.crm.report.lang.ArrayListEx;
/*     */ import com.ztesoft.crm.report.lang.Utils;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ 
/*     */ public final class Report extends Element
/*     */ {
/*     */   public static final String CACHE_DATE = "date";
/*     */   public static final String CACHE_ALWAYS = "always";
/*  22 */   private HashMap dimensionMap = new HashMap();
/*     */   private static final long serialVersionUID = -6972146026609829794L;
/*     */   private String author;
/*     */   private List boxs;
/*     */   private String cache;
/*     */   private String path;
/*     */   private String title;
/*     */   private String type;
/*     */   private String version;
/*     */   private String autoLoad;
/*     */   private Parameters parameters;
/*     */   private View view;
/*     */ 
/*     */   public static long getSerialVersionUID()
/*     */   {
/*  32 */     return -6972146026609829794L;
/*     */   }
/*     */ 
/*     */   public String getAutoLoad()
/*     */   {
/*  45 */     return this.autoLoad;
/*     */   }
/*     */ 
/*     */   public void setAutoLoad(String autoLoad) {
/*  49 */     this.autoLoad = autoLoad;
/*     */   }
/*     */ 
/*     */   public Parameters getParameters()
/*     */   {
/*  55 */     return this.parameters;
/*     */   }
/*     */ 
/*     */   public String getAuthor()
/*     */   {
/*  69 */     return this.author;
/*     */   }
/*     */ 
/*     */   public List getBoxs()
/*     */   {
/*  76 */     return this.boxs;
/*     */   }
/*     */ 
/*     */   public String getCache()
/*     */   {
/*  83 */     return this.cache;
/*     */   }
/*     */ 
/*     */   public String getPath()
/*     */   {
/*  90 */     return this.path;
/*     */   }
/*     */ 
/*     */   public String getTitle()
/*     */   {
/*  97 */     return this.title;
/*     */   }
/*     */ 
/*     */   public String getType()
/*     */   {
/* 104 */     return this.type;
/*     */   }
/*     */ 
/*     */   public String getVersion()
/*     */   {
/* 111 */     return this.version;
/*     */   }
/*     */ 
/*     */   public boolean hasCache() {
/* 115 */     return Utils.isTrue(this.cache);
/*     */   }
/*     */ 
/*     */   @Override
protected void init(Element top)
/*     */   {
/* 127 */     Element el = getChildElement(View.class);
/* 128 */     if (el != null) {
/* 129 */       this.boxs = new ArrayListEx(el.childs());
/*     */     }
/* 131 */     this.parameters = ((Parameters)getChildElement(Parameters.class));
/* 132 */     this.view = ((View)getChildElement(View.class));
/* 133 */     for (Iterator models = getElements(Model.class).iterator(); models
/* 134 */       .hasNext(); )
/*     */     {
/* 135 */       ((Model)models.next()).setReportPath(getPath());
/*     */     }
/*     */ 
/* 138 */     DimensionMap dm = (DimensionMap)
/* 139 */       getChildElement(DimensionMap.class);
/*     */ 
/* 141 */     if (dm != null) {
/* 142 */       DimensionGetter dg = new DimensionGetter();
/* 143 */       for (Iterator objs = dm.childs().iterator(); objs.hasNext(); ) {
/* 144 */         Dimension d = (Dimension)objs.next();
/* 145 */         this.dimensionMap.put(d.getName(), dg.get(d));
/*     */       }
/* 147 */       dg = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public HashMap getDimensionValues(String name)
/*     */   {
/* 153 */     return ((HashMap)this.dimensionMap.get(name));
/*     */   }
/*     */ 
/*     */   public View getView() {
/* 157 */     return this.view;
/*     */   }
/*     */ 
/*     */   public void setAuthor(String author)
/*     */   {
/* 165 */     this.author = author;
/*     */   }
/*     */ 
/*     */   public void setCache(String cache)
/*     */   {
/* 173 */     this.cache = cache;
/*     */   }
/*     */ 
/*     */   public void setPath(String path)
/*     */   {
/* 181 */     this.path = path;
/*     */   }
/*     */ 
/*     */   public void setTitle(String title)
/*     */   {
/* 189 */     this.title = title;
/*     */   }
/*     */ 
/*     */   public void setType(String type)
/*     */   {
/* 197 */     this.type = type;
/*     */   }
/*     */ 
/*     */   public void setVersion(String version)
/*     */   {
/* 205 */     this.version = version;
/*     */   }
/*     */ }

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.config.elements.Report
 * JD-Core Version:    0.5.3
 */