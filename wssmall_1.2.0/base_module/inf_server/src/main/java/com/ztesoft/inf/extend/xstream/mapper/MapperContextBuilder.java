package com.ztesoft.inf.extend.xstream.mapper;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


public class MapperContextBuilder {

	public MapperContext build(String xml) {
		try {
			return buildImpl(xml);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected MapperContext buildImpl(String xml) throws DocumentException,
			IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		MapperContext ctx = new MapperContext();
		if (!org.springframework.util.StringUtils.hasLength(xml)) {
			return ctx;
		}
		SAXReader saxReader = new SAXReader();
		InputStream in = new ByteArrayInputStream(xml.getBytes());
		Document doc = saxReader.read(in);
		List<Element> children = doc.getRootElement().elements();
		for (Element child : children) {
			String name = child.getName();
			String methodName = "process" + name;
			try {
				Method method = this.getClass().getDeclaredMethod(methodName,
						Element.class, MapperContext.class);
				method.invoke(this, child, ctx);
			} catch (NoSuchMethodException e) {
				throw new RuntimeException("XSrteam配置错误,没有该配置项!", e);
			}
		}
		return ctx;
	}

	protected void processUseGlobalImplicitCollection(Element child,
			MapperContext ctx) {
		String value = (noNull(child, "value"));
		ctx.useGlobalImplicitCollection(Boolean.valueOf(value));
	}

	protected void processRootType(Element child, MapperContext ctx) {
		String clz = null;
		try {
			clz = noNull(child, "value");
			ctx.setRootType(Class.forName(clz));
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("XSrteam配置错误,指定的类不存在!" + clz);
		}
	}

	protected void processAddImplicitCollection(Element child, MapperContext ctx) {
		String itemTypeStr = nullable(child, "itemType");
		String ownerTypeStr = noNull(child, "ownerType");
		Class itemType, ownerType;
		try {
			itemType = !org.springframework.util.StringUtils.hasLength(itemTypeStr) ? Object.class
					: Class.forName(itemTypeStr);
			ownerType = Class.forName(ownerTypeStr);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("XSrteam配置错误,指定的类不存在!" + e.getMessage());
		}
		String fieldName = noNull(child, "fieldName");
		String itemName = noNull(child, "itemName");
		ctx.addImplicitCollection(ownerType, fieldName, itemName, itemType);
	}

	protected void processRootNodeName(Element child, MapperContext ctx) {
		ctx.setRootNodeName(noNull(child, "value"));
	}

	protected void processXpathToCollection(Element child, MapperContext ctx) {
		ctx.xpathToCollection(noNull(child, "xpath"),
				noNull(child, "nodeName"), nullable(child, "itemType"));
	}

	protected void processXpathImplicitCollection(Element child,
			MapperContext ctx) {
		ctx.addXpathImplicitCollection(noNull(child, "xpath"),
				noNull(child, "nodeName"), noNull(child, "fieldName"),
				nullable(child, "itemType"));
	}

	//
	private String nullable(Element child, String key) {
		Attribute attr = child.attribute(key);
		String value;
		if (attr != null) {
			value = attr.getText();
		} else
			value = child.getText();
		return value;
	}

	private String noNull(Element child, String key) {
		String value = nullable(child, key);
		if (!org.springframework.util.StringUtils.hasLength(value))
			throw new RuntimeException("XSteam配置错误,元素{" + child.getName()
					+ "}的属性{" + key + "}值不能为空!");
		return value;
	}
}
