/*     */ package com.ztesoft.crm.report.console.vo;
/*     */ 
/*     */ import com.ztesoft.crm.report.ReportContext;
/*     */ import com.ztesoft.crm.report.io.JSONSerializer;
/*     */ import com.ztesoft.crm.report.lang.ArrayListEx;
/*     */ import com.ztesoft.crm.report.lang.ObjectPropertyUtils;
/*     */ import com.ztesoft.crm.report.lang.Utils;
/*     */ import com.ztesoft.crm.report.lang.XMLParser;
/*     */ import java.io.RandomAccessFile;
/*     */ import java.net.URLDecoder;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ 
/*     */ public class ConfigXMLNode
/*     */   implements JSONSerializer
/*     */ {
/*     */   private String tagName;
/*     */   private HashMap attributes;
/*     */   private String content;
/*     */   private List childs;
/*     */   private String name;
/*     */ 
/*     */   public ConfigXMLNode()
/*     */   {
/*  84 */     this.attributes = new HashMap();
/*     */ 
/* 140 */     this.childs = new ArrayListEx();
/*     */   }
/*     */ 
/*     */   public ConfigXMLNode(String tagName, String content)
/*     */   {
/*  84 */     this.attributes = new HashMap();
/*     */ 
/* 140 */     this.childs = new ArrayListEx();
/*     */ 
/*  35 */     this.tagName = tagName;
/*  36 */     this.content = content;
/*     */   }
/*     */ 
/*     */   public ConfigXMLNode(Node node)
/*     */   {
/*  84 */     this.attributes = new HashMap();
/*     */ 
/* 140 */     this.childs = new ArrayListEx();
/*     */ 
/*  40 */     ConfigXMLNode cmn = this;
/*  41 */     cmn.setTagName(node.getNodeName());
/*  42 */     cmn.setName(XMLParser.getAttribute(node, "name"));
/*  43 */     cmn.setContent(XMLParser.getNodeCDATA(node));
/*  44 */     XMLParser.copyAttributes(cmn.getAttributes(), node);
/*  45 */     NodeList nodes = node.getChildNodes();
/*  46 */     for (int i = 0; i < nodes.getLength(); ++i) {
/*  47 */       Node n = nodes.item(i);
/*  48 */       if ((n.getNodeType() != 1) && 
/*  49 */         (n.getNodeType() != 6)) continue;
/*  50 */       cmn.add(new ConfigXMLNode(nodes.item(i)));
/*     */     }
/*     */ 
/*  53 */     this.attributes.remove("name");
/*     */   }
/*     */ 
/*     */   public void setAttribute(String name, String value) {
/*  57 */     if (this.attributes == null)
/*  58 */       this.attributes = new HashMap();
/*  59 */     this.attributes.put(name, value);
/*     */   }
/*     */ 
/*     */   public ConfigXMLNode(String path) throws Exception
/*     */   {
/*  64 */     this(XMLParser.parser(path).getRootElement());
/*     */   }
/*     */ 
/*     */   public String getTagName()
/*     */   {
/*  73 */     return this.tagName;
/*     */   }
/*     */ 
/*     */   public void setTagName(String tagName)
/*     */   {
/*  81 */     this.tagName = tagName;
/*     */   }
/*     */ 
/*     */   public String getContent()
/*     */   {
/*  91 */     return ((this.content == null) ? "" : this.content);
/*     */   }
/*     */ 
/*     */   public void setContent(String content)
/*     */   {
/*  99 */     this.content = content;
/*     */   }
/*     */ 
/*     */   public HashMap getAttributes()
/*     */   {
/* 106 */     return this.attributes;
/*     */   }
/*     */ 
/*     */   public void setAttributes(HashMap attributes)
/*     */   {
/* 114 */     this.attributes = attributes;
/*     */   }
/*     */ 
/*     */   public void copy(Object o) {
/* 118 */     String[] props = ObjectPropertyUtils.getPropertyNames(o);
/* 119 */     for (int i = 0; i < props.length; ++i) {
/* 120 */       String v = ObjectPropertyUtils.getString(o, props[i]);
/*     */       try {
/* 122 */         v = URLDecoder.decode(v, "utf8"); } catch (Exception localException) {
/*     */       }
/* 124 */       if (props[i].equals("content")) {
/* 125 */         setContent(v);
/*     */       }
/* 129 */       else if (props[i].equals("tagName")) {
/* 130 */         this.tagName = v;
/*     */       }
/*     */       else
/*     */       {
/* 134 */         setAttribute(props[i], v);
/*     */       }
/*     */     }
/* 137 */     this.name = ((String)this.attributes.remove("name"));
/*     */   }
/*     */ 
/*     */   public void add(ConfigXMLNode node)
/*     */   {
/* 144 */     this.childs.add(node);
/*     */   }
/*     */ 
/*     */   public void clearChild() {
/* 148 */     this.childs.clear();
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/* 155 */     return this.name;
/*     */   }
/*     */ 
/*     */   public void setName(String name)
/*     */   {
/* 163 */     this.name = name;
/*     */   }
/*     */ 
/*     */   @Override
public String toJSONString() {
/* 167 */     StringBuffer sb = new StringBuffer();
/* 168 */     sb.append("{");
/* 169 */     sb.append("\"name\":\"").append(this.name).append("\"");
/* 170 */     for (Iterator keys = this.attributes.keySet().iterator(); keys
/* 171 */       .hasNext(); )
/*     */     {
/* 172 */       String key = (String)keys.next();
/* 173 */       sb.append(",\"").append(key).append("\":\"").append(
/* 174 */         this.attributes.get(key)).append("\"");
/*     */     }
/* 176 */     sb.append(",\"content\":\"").append(
/* 177 */       getContent().replaceAll("[\n]|[\r]", "<br>"));
/* 178 */     sb.append("\"}");
/*     */ 
/* 180 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toXMLString() {
/* 184 */     StringBuffer sb = new StringBuffer();
/* 185 */     sb.append("<").append(this.tagName);
/* 186 */     if (!(Utils.isEmpty(this.name)))
/* 187 */       sb.append(" name=\"").append(this.name).append("\"");
/* 188 */     for (Iterator keys = this.attributes.keySet().iterator(); keys
/* 189 */       .hasNext(); )
/*     */     {
/* 190 */       String key = (String)keys.next();
/* 191 */       sb.append(" ").append(key).append("=\"").append(
/* 192 */         this.attributes.get(key)).append("\"");
/*     */     }
/* 194 */     sb.append(">");
/* 195 */     if (this.content == null) this.content = "";
/*     */ 
/* 197 */     if (this.childs.size() < 1) {
/* 198 */       sb.append("<![CDATA[").append(getContent()).append("]]>");
/*     */     }
/*     */ 
/* 201 */     for (Iterator objs = this.childs.iterator(); objs.hasNext(); ) {
/* 202 */       ConfigXMLNode n = (ConfigXMLNode)objs.next();
/* 203 */       sb.append(n.toXMLString());
/*     */     }
/*     */ 
/* 206 */     return "</" + this.tagName + ">";
/*     */   }
/*     */ 
/*     */   public void write(String path, boolean document) throws Exception {
/* 210 */     String encoding = ReportContext.getContext().getSystemEncoding();
/* 211 */     String tpl = "<?xml version=\"1.0\" encoding=\"GBK\"?>";
/* 212 */     RandomAccessFile raf = new RandomAccessFile(path, "rw");
/*     */     try {
/* 214 */       if (document) {
/* 215 */         raf.write(tpl.getBytes(encoding));
/*     */       }
/* 217 */       raf.write(toXMLString().getBytes(encoding));
/*     */     } catch (Exception e) {
/* 219 */       throw e;
/*     */     } finally {
/*     */       try {
/* 222 */         raf.close();
/*     */       } catch (Exception localException1) {
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public List getElementsByTagName(String tagName) {
/* 229 */     ArrayListEx arr = new ArrayListEx();
/* 230 */     if (this.tagName.equals(tagName))
/* 231 */       arr.add(this);
/* 232 */     for (Iterator objs = this.childs.iterator(); objs.hasNext(); ) {
/* 233 */       arr.addAll(
/* 234 */         ((ConfigXMLNode)objs.next()).getElementsByTagName(tagName));
/*     */     }
/* 236 */     return arr;
/*     */   }
/*     */ 
/*     */   public ConfigXMLNode getElementByTagName(String tagName)
/*     */   {
/* 241 */     if (this.tagName.equals(tagName))
/* 242 */       return this;
/* 243 */     for (Iterator objs = this.childs.iterator(); objs.hasNext(); ) {
/* 244 */       ConfigXMLNode n = ((ConfigXMLNode)objs.next())
/* 245 */         .getElementByTagName(tagName);
/* 246 */       if (n != null)
/* 247 */         return n;
/*     */     }
/* 249 */     return null;
/*     */   }
/*     */ 
/*     */   public ConfigXMLNode getElementByName(String name) {
/* 253 */     if ((this.name != null) && (this.name.equals(name)))
/* 254 */       return this;
/* 255 */     for (Iterator objs = this.childs.iterator(); objs.hasNext(); ) {
/* 256 */       ConfigXMLNode n = ((ConfigXMLNode)objs.next())
/* 257 */         .getElementByName(name);
/* 258 */       if (n != null)
/* 259 */         return n;
/*     */     }
/* 261 */     return null;
/*     */   }
/*     */ }

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.console.vo.ConfigXMLNode
 * JD-Core Version:    0.5.3
 */