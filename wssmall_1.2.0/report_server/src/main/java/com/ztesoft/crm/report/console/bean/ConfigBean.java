/*     */ package com.ztesoft.crm.report.console.bean;
/*     */ 
/*     */ import com.ztesoft.crm.report.ReportContext;
/*     */ import com.ztesoft.crm.report.actions.ParameterMap;
/*     */ import com.ztesoft.crm.report.console.Bean;
/*     */ import com.ztesoft.crm.report.console.vo.ConfigXMLNode;
/*     */ import com.ztesoft.crm.report.lang.ObjectPropertyUtils;
/*     */ import com.ztesoft.crm.report.lang.Utils;
/*     */ import com.ztesoft.crm.report.lang.XMLParser;
/*     */ import java.io.FileOutputStream;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ 
/*     */ public class ConfigBean extends Bean
/*     */ {
/*     */   public List listDataSources(ParameterMap map)
/*     */     throws Exception
/*     */   {
/*  31 */     XMLParser xml = XMLParser.parser(getContext().getResourceAsStream(ReportContext.getContext().getServerConfig()));
/*  32 */     return new ConfigXMLNode(xml.getRootElement()).getElementsByTagName("datasource");
/*     */   }
/*     */ 
/*     */   private void saveConfigFile(ConfigXMLNode config) throws Exception
/*     */   {
/*  37 */     FileOutputStream out = new FileOutputStream(getContext()
/*  38 */       .getRealPath(ReportContext.getContext().getServerConfig()));
/*     */     try {
/*  40 */       out.write("<?xml version=\"1.0\" encoding=\"GB2312\"?>".getBytes());
/*  41 */       out.write(config.toXMLString().getBytes("GB2312"));
/*     */     } catch (Exception e) {
/*  43 */       throw e;
/*     */     } finally {
/*     */       try {
/*  46 */         if (out != null)
/*  47 */           out.close();
/*     */       } catch (Exception localException1) {
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public String saveDataSources(ParameterMap map) throws Exception {
/*  54 */     if (Utils.isEmpty(ReportContext.getContext().getAppPath())) {
/*  55 */       return "当前配置文件是打包在war文件里面，因此不能修改这个配置文件！";
/*     */     }
/*  57 */     List dataSources = (List)map.get("datasource");
/*     */ 
/*  59 */     ConfigXMLNode node = new ConfigXMLNode(getContext()
/*  60 */       .getRealPath(ReportContext.getContext().getServerConfig()));
/*     */ 
/*  62 */     ConfigXMLNode ds = node.getElementByTagName("datasource-map");
/*  63 */     ds.clearChild();
/*  64 */     for (Iterator objs = dataSources.iterator(); objs.hasNext(); ) {
/*  65 */       ConfigXMLNode n = new ConfigXMLNode();
/*  66 */       n.copy(objs.next());
/*  67 */       ds.add(n);
/*     */     }
/*     */ 
/*  70 */     saveConfigFile(node);
/*     */ 
/*  72 */     return "保存数据源配置成功！";
/*     */   }
/*     */ 
/*     */   public String saveSystemVariables(ParameterMap map) throws Exception {
/*  76 */     if (Utils.isEmpty(ReportContext.getContext().getAppPath())) {
/*  77 */       return "当前配置文件是打包在war文件里面，因此不能修改这个配置文件！";
/*     */     }
/*     */ 
/*  80 */     Object o = map;
/*  81 */     map.remove("id");
/*  82 */     ConfigXMLNode node = new ConfigXMLNode(getContext()
/*  83 */       .getRealPath(ReportContext.getContext().getServerConfig()));
/*     */ 
/*  85 */     ConfigXMLNode ds = node.getElementByTagName("e-variables");
/*  86 */     ds.clearChild();
/*  87 */     String[] props = ObjectPropertyUtils.getPropertyNames(o);
/*  88 */     for (int i = 0; i < props.length; ++i) {
/*  89 */       ConfigXMLNode n = new ConfigXMLNode();
/*  90 */       String v = ObjectPropertyUtils.getString(o, props[i]);
/*  91 */       n.setTagName("var");
/*  92 */       n.setAttribute("name", props[i]);
/*  93 */       n.setAttribute("value", v);
/*  94 */       ds.add(n);
/*     */     }
/*     */ 
/*  98 */     saveConfigFile(node);
/*     */ 
/* 100 */     return "保存数据源配置成功！";
/*     */   }
/*     */ 
/*     */   public String apply(ParameterMap map) throws Exception {
/* 104 */     if (Utils.isEmpty(ReportContext.getContext().getAppPath())) {
/* 105 */       return "当前配置文件是打包在war文件里面，因此不能应用这个配置文件，请通过发布来实现！";
/*     */     }
/* 107 */     ReportContext.getContext().load(
/* 108 */       getContext().getResourceAsStream(
/* 109 */       ReportContext.getContext().getServerConfig()));
/*     */ 
/* 111 */     return "应用设置成功";
/*     */   }
/*     */ }

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.console.bean.ConfigBean
 * JD-Core Version:    0.5.3
 */