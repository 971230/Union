package com.ztesoft.inf.operation.vo;

import java.util.Map;

public class OperationArg {

	private String argType;
	private String argIndex;
	private String xpath;
	private String xmlMapper;

	public OperationArg(Map map) {
		argType = (String) map.get("arg_type");
		argIndex = map.get("arg_index")+"";
		xpath = (String) map.get("xpath");
		xmlMapper = (String) map.get("xml_mapper");
//		setValues(map);
	}

	public String getArgType() {
		return argType;
	}

	public void setArgType(String argType) {
		this.argType = argType;
	}

	public String getArgIndex() {
		return argIndex;
	}

	public void setArgIndex(String argIndex) {
		this.argIndex = argIndex;
	}

	public String getXpath() {
		return xpath;
	}

	public void setXpath(String xpath) {
		this.xpath = xpath;
	}

	public String getXmlMapper() {
		return xmlMapper;
	}

	public void setXmlMapper(String xmlMapper) {
		this.xmlMapper = xmlMapper;
	}
}

