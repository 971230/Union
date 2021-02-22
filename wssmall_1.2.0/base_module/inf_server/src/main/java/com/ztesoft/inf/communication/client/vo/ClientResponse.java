package com.ztesoft.inf.communication.client.vo;

import java.io.Serializable;
import java.util.Map;

public class ClientResponse implements Serializable{

	private String responseId;
	private String cdataPath;
	private String transform;
	private String xmlMapper;
	private boolean transformFault;
	private String rspPath;
	private String rspType;

	public ClientResponse() {
	}

	public ClientResponse(Map map) {
		setValues(map);
	}

	private void setValues(Map map) {
		responseId = (String) map.get("rsp_id");
		cdataPath = (String) map.get("cdata_path");
		byte[] tpl = (byte[]) map.get("trans_tpl");
		if (tpl != null) {
			transform = new String(tpl);
		} else {
			transform = "";
		}
/*		byte[] mapper = (byte[]) map.get("xml_mapper");
		if (mapper != null) {
			xmlMapper = new String(mapper);
		} else {
			xmlMapper = "";
		}*/
		xmlMapper = (String) map.get("xml_mapper");
		String str = (String) map.get("trans_fault");
		transformFault = "1".equals(str) ? true : false;
		rspPath = (String) map.get("rsp_classpath");
		rspType = (String) map.get("rsp_type"); // add by wen.jinyang 2012-08-23
	}

	public String getResponseId() {
		return responseId;
	}

	public void setResponseId(String responseId) {
		this.responseId = responseId;
	}

	public String getCdataPath() {
		return cdataPath;
	}

	public void setCdataPath(String subPath) {
		this.cdataPath = subPath;
	}

	public String getTransform() {
		return transform;
	}

	public void setTransform(String transform) {
		this.transform = transform;
	}

	public String getXmlMapper() {
		return xmlMapper;
	}

	public void setXmlMapper(String xmlMapper) {
		this.xmlMapper = xmlMapper;
	}

	public boolean isTransformFault() {
		return transformFault;
	}

	public String getRspType() {
		return rspType;
	}

	public void setRspType(String rspType) {
		this.rspType = rspType;
	}

	public String getRspPath() {
		return rspPath;
	}

	public void setRspPath(String rspPath) {
		this.rspPath = rspPath;
	}
}
