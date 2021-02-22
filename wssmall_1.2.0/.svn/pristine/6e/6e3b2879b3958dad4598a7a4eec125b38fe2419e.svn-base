// Source file: D:\\WLSApp\\Eclipse\\workspace\\ibss\\src\\com\\powerise\\ibss\\framework\\XMLTool.java
package com.powerise.ibss.framework;

import com.powerise.ibss.util.GlobalNames;
import com.powerise.ibss.util.SysSet;
import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.StringTokenizer;
public class XMLTool
{
	public Document m_Doc;
	private Element m_RootElem;
	private String m_Encoding;
	public XMLTool()
	{
		Init();
		SetDefaultEncoding();
	}
	public XMLTool(String encoding)
	{
		Init();
		m_Encoding = encoding;
	}
	//创建新DOM文档，rootName为document元素
	public int New(String rootName) throws FrameException
	{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = null;
		try
		{
			db = dbf.newDocumentBuilder();
		}
		catch (ParserConfigurationException e)
		{
			throw new FrameException(-22992001, "创建XML生成器时出现异常。");
		}
		DOMImplementation impl = db.getDOMImplementation();
		try
		{
			m_Doc = impl.createDocument(null, rootName, null);
		}
		catch (DOMException e)
		{
			throw new FrameException(-22992002, "创建XML文档时出现异常。");
		}
		m_RootElem = m_Doc.getDocumentElement();
		return 0;
	}
	public String GetXML() throws FrameException
	{
		return GetXML(null);
	}
	//从DOM文档转换成XML格式字符串
	public String GetXML(String strEncoding) throws FrameException
	{
		TransformerFactory transf = TransformerFactory.newInstance();
		Transformer trans = null;
		try
		{
			trans = transf.newTransformer();
		}
		catch (TransformerConfigurationException e)
		{
			throw new FrameException(-22992003, "转换XML文档时出现配置异常。");
		}
		if (strEncoding != null)			//       if(m_Encoding != null) //指定encoding
			trans.setOutputProperty(javax.xml.transform.OutputKeys.ENCODING, strEncoding);
		DOMSource in = new DOMSource(m_Doc);
		StringWriter outsw = new StringWriter();
		StreamResult out = new StreamResult(outsw);
		try
		{
			trans.transform(in, out);
		}
		catch (TransformerException e)
		{
			throw new FrameException(-22992013, "转换XML文档时出现转换异常。");
		}
		outsw.flush();
		return outsw.toString();
	}
	//
	public String GetMessage()
	{
		return null;
	}
	//从XML格式字符串转换成DOM文档,strXML为输入字符串
	public int ReadXML(String strXML) throws FrameException
	{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = null;
		try
		{
			db = dbf.newDocumentBuilder();
		}
		catch (ParserConfigurationException e)
		{
			throw new FrameException(-22992004, "创建XML生成器时出现异常。");
		}
		Reader sr = new StringReader(strXML);
		InputSource is = new InputSource(sr);
		try
		{
			m_Doc = db.parse(is);
		}
		catch (SAXException se)
		{
	
			throw new FrameException(-22992005, "SAX解析XML串时出错。",se.getMessage());
		}
		catch (IOException ioe)
		{
			throw new FrameException(-22992006, "解析XML串时出现IO异常。");
		}
		catch (IllegalArgumentException e)
		{
			throw new FrameException(-22992007, "解析XML串时出现参数设置异常。");
		}
		m_RootElem = m_Doc.getDocumentElement();
		return 0;
	}
	//从XML格式磁盘文件转换成DOM文档,strFileName为文件路径名
	public int ReadXMLFile(String strFileName) throws FrameException
	{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = null;
		try
		{
			db = dbf.newDocumentBuilder();
		}
		catch (ParserConfigurationException e)
		{
			throw new FrameException(-22992004, "创建XML生成器时出现异常。");
		}
		try
		{
			m_Doc = db.parse(new File(strFileName));
		}
		catch (SAXException se)
		{
			throw new FrameException(-22992005, "SAX解析XML文件时出错,文件是:"+strFileName,se.getMessage());
		}
		catch (IOException ioe)
		{
			throw new FrameException(-22992006, "解析XML文件时出现IO异常,文件是:"+strFileName);
		}
		catch (IllegalArgumentException e)
		{
			throw new FrameException(-22992008, "解析XML文件时出现参数设置异常,文件是:"+strFileName);
		}
		m_RootElem = m_Doc.getDocumentElement();
		return 0;
	}
	//从XML格式磁盘文件转换成DOM文档
	public Document ReadXMLByte(byte[] xmlByte) throws FrameException
	{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = null;
		Document doc = null;
		try
		{
			db = dbf.newDocumentBuilder();
		}
		catch (ParserConfigurationException e)
		{
			throw new FrameException(-22992004, "创建XML生成器时出现异常。");
		}
		try
		{
			doc = db.parse(new ByteArrayInputStream(xmlByte));
		}
		catch (SAXException se)
		{
			se.printStackTrace();
			throw new FrameException(-22992005, "SAX解析XML文件时出错。");
		}
		catch (IOException ioe)
		{
			throw new FrameException(-22992006, "解析XML文件时出现IO异常。");
		}
		catch (IllegalArgumentException e)
		{
			throw new FrameException(-22992008, "解析XML文件时出现参数设置异常。");
		}
		return doc;
	}
	public void Reset() throws FrameException
	{
		Init();
	}
	public int WriteXMLFile(String strFileName) throws FrameException
	{
		return WriteXMLFile(strFileName,"UTF-8");
	}
	//从DOM文档生成XML格式磁盘文件，strFileName为文件路径名
	public int WriteXMLFile(String strFileName,String strEncoding) throws FrameException
	{
		TransformerFactory transf = TransformerFactory.newInstance();
		Transformer trans = null;
		try
		{
			trans = transf.newTransformer();
		}
		catch (TransformerConfigurationException e)
		{
			throw new FrameException(-22992003, "转换XML文档时出现配置异常。");
		}
		//       if(m_Encoding!=null) //指定encoding
		 
		trans.setOutputProperty(javax.xml.transform.OutputKeys.ENCODING,strEncoding);
		trans.setOutputProperty(javax.xml.transform.OutputKeys.METHOD, "xml");
		DOMSource in = new DOMSource(m_Doc);
		StreamResult out = new StreamResult(new File(strFileName));
		try
		{
			trans.transform(in, out);
		}
		catch (TransformerException e)
		{
			throw new FrameException(-22992013, "转换XML文档时出现转换异常。");
		}
		return 0;
	}
	//从DOM文档生成XML格式磁盘文件，strFileName为文件路径名
	public int WriteXMLFile(Document doc, String strFileName) throws FrameException
	{
		TransformerFactory transf = TransformerFactory.newInstance();
		Transformer trans = null;
		try
		{
			trans = transf.newTransformer();
		}
		catch (TransformerConfigurationException e)
		{
			throw new FrameException(-22992003, "转换XML文档时出现配置异常。");
		}
		//       if(m_Encoding!=null) //指定encoding
		//          trans.setOutputProperty(javax.xml.transform.OutputKeys.ENCODING,m_Encoding);
		m_Doc = doc;
		DOMSource in = new DOMSource(doc);
		StreamResult out = new StreamResult(new File(strFileName));
		try
		{
			trans.transform(in, out);
		}
		catch (TransformerException e)
		{
			throw new FrameException(-22992013, "转换XML文档时出现转换异常。");
		}
		return 0;
	}
	//在DOM文档中查子元素值，aElemParent为元素，aName为子元素名，aAttrName为子元素属性名
	public String GetElemValue(Node aElemParent, String aName, String aAttrName) throws FrameException
	{
		String s = null;
		//查找子节点
		Node node = FindChildByName(aName, aElemParent, false);
		Attr attr = null;
		if (node != null)
		{
			if (aAttrName != null)
			{ //如果指定查找属性，则继续查找属性值
				attr = FindAttrByName(aAttrName, (Element) node, false);
				if (attr != null)
					s = attr.getValue();
			}
			else //否则首先考虑节点值，其次考虑textnode
				{
				s = node.getNodeValue();
				if (node.getFirstChild() != null)
					s = node.getFirstChild().getNodeValue();
			}
		}
		return s;
	}
	private int Init()
	{
		m_Doc = null;
		m_RootElem = null;
		return 0;
	}
	private void Close()
	{
	}
	private int CreateParser()
	{
		return 0;
	}
	public boolean Check() throws FrameException
	{
		if ((m_RootElem != null) && (m_Doc != null))
			return true;
		else
			return false;
	}
	//在DOM文档中创建子元素，aElemName为子元素名，aElemParent为元素，aElemVale为子元素值
	public Node CreateElement(String aElemName, Node aElemParent, String aElemValue) throws FrameException
	{
		Element newelem = null;
		try
		{
			newelem = m_Doc.createElement(aElemName);
		}
		catch (DOMException e)
		{
			throw new FrameException(-22992021, "创建元素时出现异常。");
		}
		aElemParent.appendChild(newelem);
		Text text = null;
		if (aElemValue != null)
		{
			text = m_Doc.createTextNode(aElemValue);
			newelem.appendChild(text);
		}
		return newelem;
	}
	//在DOM文档中查子元素，aName为子元素名，aElemParent为元素，aCreate表示若不存在是否创建
	public Node FindChildByName(String aName, Node aElemParent, boolean aCreate) throws FrameException
	{
		NodeList list = aElemParent.getChildNodes();
		int count = list.getLength();
		for (int i = 0; i < count; i++)
		{
			if (list.item(i).getNodeName().equals(aName))
			{
				return list.item(i);
			}
		}
		if (aCreate)
			return CreateElement(aName, aElemParent, null);
		else
			return null;
	}
	//在DOM文档中创建元素属性，aAttrName为属性名，aElemParent为元素，aAttrValue为属性值
	public Attr CreateAttr(String aAttrName, Element aElemParent, String aAttrValue) throws FrameException
	{
		Attr attr = null;
		try
		{
			attr = m_Doc.createAttribute(aAttrName);
			if (aAttrValue != null)
			{
				attr.setValue(aAttrValue);
			}
			aElemParent.setAttributeNode(attr);
		}
		catch (DOMException e)
		{
			throw new FrameException(-22992022, "创建属性时出现异常。");
		}
		return attr;
	}
	//在DOM文档中查元素属性，aName为属性名，aElemParent为元素，aCreate表示若不存在是否创建
	public Attr FindAttrByName(String aName, Element aElemParent, boolean aCreate) throws FrameException
	{
		Attr attr = aElemParent.getAttributeNode(aName);
		if (attr == null)
		{
			if (aCreate)
			{
				attr = CreateAttr(aName, aElemParent, null);
			}
		}
		return attr;
	}
	//取根元素
	public Element GetRootElem()
	{
		return m_RootElem;
	}
	//根据当前系统属性返回需设定的encoding
	private void SetDefaultEncoding()
	{
		//String code = System.getProperty("file.encoding");
		String os = System.getProperty("os.name");
		if (os.lastIndexOf("Windows") < 0)
			m_Encoding = "GB2312";
		else
			m_Encoding = null;
	}
	// 在指定节点下通过属性值查找同名的节点
	public Node GetNodeByAttr(String aName, Element aElemParent, String aAttrName, String aAttrValue)
	{
		NodeList list = aElemParent.getChildNodes();
		Node node;
		Attr attr;
		int count = list.getLength();
		for (int i = 0; i < count; i++)
		{
			node = list.item(i);
			if (node.getNodeName().equals(aName)) // 找同名节点
			{
				try
				{
					attr = FindAttrByName(aAttrName, (Element) node, false); //找属性名
					if (attr.getNodeValue().equals(aAttrValue)) //属性值相等，查找成功
						return node;
				}
				catch (FrameException e) //不影响其他节点匹配
				{
				}
			}
		}
		return null;
	}
	/*
	 * 转换XML document到字符串 @roseuid 3B861B80010A
	 */
	public static String getStrFromDoc(Document docXml) throws FrameException
	{
		DOMSource domSource = new DOMSource(docXml);
		StringWriter out = new StringWriter();
		StreamResult streamResult = new StreamResult(out);
		TransformerFactory tf = TransformerFactory.newInstance();
		try
		{
			Transformer transformer = tf.newTransformer();
			transformer.setOutputProperty(javax.xml.transform.OutputKeys.METHOD, "xml");
			transformer.setOutputProperty(javax.xml.transform.OutputKeys.ENCODING, "gb2312");
			transformer.transform(domSource, streamResult);
		}
		catch (Exception ie)
		{
			ie.printStackTrace();
		}
		out.flush();
		return out.toString();
	}
	public static byte[] getDOCByteArray(Document docXml) throws FrameException
	{
		ByteArrayOutputStream bs = new ByteArrayOutputStream();
		try
		{
			javax.xml.transform.Transformer transformer = javax.xml.transform.TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(javax.xml.transform.OutputKeys.ENCODING, GlobalNames.EAI_ENCODING);
			transformer.setOutputProperty(javax.xml.transform.OutputKeys.METHOD, "xml");
			//transformer.setOutputProperty(javax.xml.transform.OutputKeys.INDENT, "yes");
			transformer.transform(new javax.xml.transform.dom.DOMSource(docXml), new javax.xml.transform.stream.StreamResult(bs));
			return bs.toByteArray();
		}
		catch (Exception e)
		{
			throw new FrameException(-22992122, "得到Document 二进制出错！", SysSet.getStackMsg(e));
		}
	}
	public static byte[] getDOCByteArray(Document docXml,String encoding) throws FrameException
	{
		ByteArrayOutputStream bs = new ByteArrayOutputStream();
		try
		{
			javax.xml.transform.Transformer transformer = javax.xml.transform.TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(javax.xml.transform.OutputKeys.ENCODING, encoding);
			transformer.setOutputProperty(javax.xml.transform.OutputKeys.METHOD, "xml");
			//transformer.setOutputProperty(javax.xml.transform.OutputKeys.INDENT, "yes");
			transformer.transform(new javax.xml.transform.dom.DOMSource(docXml), new javax.xml.transform.stream.StreamResult(bs));
			return bs.toByteArray();
		}
		catch (Exception e)
		{
			throw new FrameException(-22992122, "得到Document 二进制出错！", SysSet.getStackMsg(e));
		}
	}
	 public Element locateElem(String strPath) throws FrameException {
	        int iCnt = 0, iSize = 0;
	        String strPropName, strOrigName;
	        Node curNode = null;
	        Element curElem = null;
	        String strName = null;
	        int count  = 0;
	        StringTokenizer strToken;
	        //对path进行解析，分成多个包
	        strToken = new StringTokenizer(strPath, ".");
	        iSize = strToken.countTokens();
	        iCnt = 1;//找到属性，缺省是一个
	        curElem = m_RootElem;
	        for (int i = 0; i < iSize; i++) {
	            strName = strToken.nextToken();
	            NodeList list = curElem.getChildNodes();
	            curNode = null;
	    		count= list.getLength();
	    		for (int j = 0; j < count; j++)
	    		{
	    			if (list.item(j).getNodeName().equals(strName))
	    			{
	    			    curNode = list.item(j);
	    				if(curNode.getNodeType()!= Node.ELEMENT_NODE)
	    				    throw new FrameException(-22143006,strName+"格式错误，不是Element类型");
	    				curElem = (Element)curNode;
	    				break;
	    			}
	    		}
	    		if(curNode == null)
	    		    throw new FrameException(-22143007,strName+"没有找到，在"+strPath);
	    		
	        }
	        return curElem;
	    }
}
