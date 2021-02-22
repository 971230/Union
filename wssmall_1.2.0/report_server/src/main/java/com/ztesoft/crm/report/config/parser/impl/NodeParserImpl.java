/*    */ package com.ztesoft.crm.report.config.parser.impl;
/*    */ 
/*    */ import com.ztesoft.crm.report.config.elements.Element;
/*    */ import com.ztesoft.crm.report.config.parser.NodeParser;
/*    */ import com.ztesoft.crm.report.config.parser.NodeParserFactory;
/*    */ import com.ztesoft.crm.report.lang.Utils;
/*    */ import com.ztesoft.crm.report.lang.XMLParser;
/*    */ import org.w3c.dom.Node;
/*    */ import org.w3c.dom.NodeList;
/*    */ 
/*    */ public class NodeParserImpl
/*    */   implements NodeParser
/*    */ {
/*    */   private int maxId;
/*    */ 
/*    */   protected Element createElement(String nodeName)
/*    */   {
/* 28 */     StringBuffer sb = new StringBuffer(
/* 29 */       "com.ztesoft.crm.report.config.elements.");
/* 30 */     sb.append(nodeName.substring(0, 1).toUpperCase());
/* 31 */     sb.append(nodeName.substring(1));
/*    */     try {
/* 33 */       return ((Element)Class.forName(sb.toString()).newInstance());
/*    */     } catch (Exception localException) {
/*    */     }
/* 36 */     return null;
/*    */   }
/*    */ 
/*    */   @Override
public Element parse(Node node)
/*    */   {
/* 48 */     Element el = createElement(node.getNodeName());
/* 49 */     XMLParser.copyAttributes(el, node);
/* 50 */     NodeList list = node.getChildNodes();
/* 51 */     int len = list.getLength();
/* 52 */     for (int i = 0; i < len; ++i) {
/* 53 */       Node n = list.item(i);
/* 54 */       if ((n.getNodeType() == 4) || 
/* 55 */         (n.getNodeType() == 3)) {
/* 56 */         el.appendContent(Utils.encode(n.getNodeValue()));
/*    */       }
/*    */       else
/* 59 */         el.add(NodeParserFactory.getParser(n.getNodeName()).parse(n));
/*    */     }
/* 61 */     return el;
/*    */   }
/*    */ 
/*    */   public int getMaxId()
/*    */   {
/* 66 */     return this.maxId;
/*    */   }
/*    */ }

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.config.parser.impl.NodeParserImpl
 * JD-Core Version:    0.5.3
 */