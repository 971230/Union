/*     */ package com.ztesoft.crm.report.actions;
/*     */ 
/*     */ import com.ztesoft.crm.report.lang.ObjectPropertyUtils;
/*     */ import com.ztesoft.crm.report.lang.Utils;
/*     */ import com.ztesoft.crm.report.lang.XMLParser;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import org.apache.commons.beanutils.LazyDynaBean;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ 
/*     */ public class XMLRequestReader
/*     */ {
/*     */   private XMLParser xml;
/*     */ 
/*     */   public XMLRequestReader(InputStream io)
/*     */     throws Exception
/*     */   {
/*  50 */     this.xml = XMLParser.parser(io);
/*     */   }
/*     */ 
/*     */   public HashMap read()
/*     */   {
/*  59 */     HashMap map = new HashMap();
/*     */ 
/*  61 */     Node[] elements = this.xml.getChildsByTagName(this.xml.getRootElement(), "element");
/*     */ 
/*  63 */     for (int i = 0; i < elements.length; ++i) {
/*  64 */       Node el = elements[i];
/*  65 */       map.put(XMLParser.getAttribute(el, "name"), read(el));
/*     */     }
/*     */ 
/*  68 */     return map;
/*     */   }
/*     */ 
/*     */   private Object read(Node el) {
/*  72 */     String type = XMLParser.getAttribute(el, "type");
/*     */ 
/*  74 */     if ("string".equals(type)) {
/*  75 */       return readString(el);
/*     */     }
/*     */ 
/*  78 */     if ("object".equals(type)) {
/*  79 */       return readObject(el);
/*     */     }
/*     */ 
/*  82 */     if ("array".equals(type)) {
/*  83 */       return readArray(el);
/*     */     }
/*  85 */     return "";
/*     */   }
/*     */ 
/*     */   private Object readString(Node el)
/*     */   {
/*  90 */     return Utils.customDecode(XMLParser.getNodeCDATA(el));
/*     */   }
/*     */ 
/*     */   private Object readObject(Node el)
/*     */   {
/*  95 */     Node[] objs = this.xml.getChildsByTagName(el, "element");
/*  96 */     LazyDynaBean obj = new LazyDynaBean();
/*     */ 
/*  98 */     for (int i = 0; i < objs.length; ++i)
/*     */     {
/* 100 */       ObjectPropertyUtils.setProperty(obj, XMLParser.getAttribute(objs[i], "name"), 
/* 101 */         read(objs[i]));
/*     */     }
/*     */ 
/* 104 */     return obj;
/*     */   }
/*     */ 
/*     */   private Object readArray(Node el) {
/* 108 */     ArrayList arr = new ArrayList();
/*     */ 
/* 110 */     NodeList els = el.getChildNodes();
/* 111 */     for (int i = 0; i < els.getLength(); ++i) {
/* 112 */       Node e = els.item(i);
/*     */ 
/* 114 */       if (e.getNodeType() == 4) {
/* 115 */         arr.add(XMLParser.getNodeCDATA(e));
/*     */       }
/* 119 */       else if ("value".equals(e.getNodeName())) {
/* 120 */         arr.add(XMLParser.getNodeCDATA(e));
/*     */       }
/* 125 */       else if ("row".equals(e.getNodeName())) {
/* 126 */         Node[] objs = this.xml.getChildsByTagName(e, "element");
/* 127 */         LazyDynaBean obj = new LazyDynaBean();
/*     */ 
/* 129 */         for (int a = 0; a < objs.length; ++a)
/*     */         {
/* 131 */           ObjectPropertyUtils.setProperty(obj, XMLParser.getAttribute(objs[a], "name"), 
/* 132 */             read(objs[a]));
/*     */         }
/*     */ 
/* 135 */         arr.add(obj);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 140 */     return arr;
/*     */   }
/*     */ }

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.actions.XMLRequestReader
 * JD-Core Version:    0.5.3
 */