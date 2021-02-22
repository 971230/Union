package com.ztesoft.inf.operation.vo;

import java.util.List;
import java.util.Map;

public class Operation {

	private String className;
	private String methodName;
	private String operationCode;
	private String operationName;
	private List operationInArgs;
	private OperationArg resultArg;

	public Operation(Map map) {
		setValues(map);
	}
	private void setValues(Map map) {
		className = (String) map.get("class_name");
		methodName = (String) map.get("method_name");
		operationCode = (String) map.get("operation_code");
		operationName = (String) map.get("operation_name");
	}
	public OperationArg getResultArg() {
		return resultArg;
	}
	public void setResultArg(OperationArg resultArgument) {
		this.resultArg = resultArgument;
	}
	public List getOperationInArgs() {
		return operationInArgs;
	}
	public void setOperationInArgs(List operationInArgs) {
		this.operationInArgs = operationInArgs;
	}

	private String state;

	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getOperationName() {
		return operationName;
	}
	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

	private String description;

	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public String getOperationCode() {
		return operationCode;
	}
	public void setOperationCode(String operationCode) {
		this.operationCode = operationCode;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}

