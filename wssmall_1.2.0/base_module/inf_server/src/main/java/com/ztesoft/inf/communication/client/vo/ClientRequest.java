package com.ztesoft.inf.communication.client.vo;

import java.io.Serializable;
import java.util.Map;

public class ClientRequest implements Serializable {

	private String requestId;
	private String globalVarsId;
	private String template;
	private String classPath;
	private String qnameEncode;
	private String qname;
	private String operClassName;
	private String operMethod;

	public ClientRequest() {
	}

	public ClientRequest(Map map) {
		setValues(map);
	}

	private void setValues(Map map) {
		requestId = (String) map.get("req_id");
		globalVarsId = (String) map.get("gvar_id");
		classPath = (String) map.get("class_path");
		qnameEncode = (String) map.get("qname_encode");
		qname = (String) map.get("qname");
		operClassName = (String) map.get("oper_classname");
		operMethod = (String) map.get("oper_method");
		byte[] tpl = (byte[]) map.get("req_tpl");
		if (tpl != null) {
			template = new String(tpl);
		} else {
			template = "";
		}
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getGlobalVarsId() {
		return globalVarsId;
	}

	public void setGlobalVarsId(String globalVarsId) {
		this.globalVarsId = globalVarsId;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public String getLogCol() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getClassPath() {
		return classPath;
	}

	public void setClassPath(String classPath) {
		this.classPath = classPath;
	}

	public String getQnameEncode() {
		return qnameEncode;
	}

	public void setQnameEncode(String qnameEncode) {
		this.qnameEncode = qnameEncode;
	}

	public String getQname() {
		return qname;
	}

	public void setQname(String qname) {
		this.qname = qname;
	}

	public String getOperClassName() {
		return operClassName;
	}

	public void setOperClassName(String operClassName) {
		this.operClassName = operClassName;
	}

	public String getOperMethod() {
		return operMethod;
	}

	public void setOperMethod(String operMethod) {
		this.operMethod = operMethod;
	}

}
