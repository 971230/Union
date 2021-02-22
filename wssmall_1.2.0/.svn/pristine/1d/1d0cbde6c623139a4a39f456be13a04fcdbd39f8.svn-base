package com.ztesoft.inf.extend.xstream.mapper;

import java.util.HashMap;
import java.util.Map;


public class XPathMapper extends MapperWrapper {

	public XPathMapper(Mapper wrapped) {
		super(wrapped);
	}

	private final Map<String, String> xpathToType = new HashMap();
	private final Map<String, String> xpathToCollFieldName = new HashMap();
	private final Map<String, String> xpathToAliasName = new HashMap();
	private final Map<String, String> xpathToImplicitItemName = new HashMap();
	private String mapNodeElementSuffix;

	public void addXpathType(String xpath, String typeName) {
		xpathToType.put(xpath, typeName);
	}

	public void addXpathType(String xpath, Class type) {
		xpathToType.put(xpath, type.getName());
	}

	public void addXpathAlias(String xpath, String nodeName) {
		xpathToAliasName.put(xpath, nodeName);
	}

	@Override
	public Class realClassByPath(String path) {
		String mappedName = null;
		for (String key : xpathToType.keySet()) {
			if (match(path, key)) {
				mappedName = xpathToType.get(key);
				break;
			}
		}
		if (mappedName != null) {
			return super.realClass(mappedName);
		}
		return super.realClassByPath(path);
	}

	private boolean match(String xpath, String toMatch) {
		return toMatch.equals(xpath.replaceAll("\\[\\d*\\]", ""));
	}

	public void addXpathImplicitCollection(String xpath, String nodeName,
			String fieldName, Class itemType) {
		if (itemType != null)
			xpathToType.put(xpath + "/" + nodeName, itemType.getName());
		xpathToCollFieldName.put(xpath + "/" + nodeName, fieldName);
		xpathToImplicitItemName.put(xpath + "/" + fieldName, nodeName);
	}

	@Override
	public String getColFieldNameByPath(String path) {
		String mappedName = null;
		for (String key : xpathToCollFieldName.keySet()) {
			if (match(path, key)) {
				mappedName = xpathToCollFieldName.get(key);
				break;
			}
		}
		return mappedName;
	}

	@Override
	public String getAliasNameByPath(String path) {
		String mappedName = null;
		for (String key : xpathToAliasName.keySet()) {
			if (match(path, key)) {
				mappedName = xpathToAliasName.get(key);
				break;
			}
		}
		return mappedName;
	}

	@Override
	public String getImplicitCollectionItemNameByPath(String path) {
		String mappedName = null;
		for (String key : xpathToImplicitItemName.keySet()) {
			if (match(path, key)) {
				mappedName = xpathToImplicitItemName.get(key);
				break;
			}
		}
		return mappedName;
	}

	@Override
	public String genMapNodeNameByPath(String path) {
		int i = path.toString().lastIndexOf("/");
		return path.substring(i + 1)
				+ (org.springframework.util.StringUtils.hasLength(mapNodeElementSuffix) ? mapNodeElementSuffix
						: "_Item");
	}

	public void setMapNodeElementSuffix(String suffix) {
		this.mapNodeElementSuffix = suffix;
	}
}
