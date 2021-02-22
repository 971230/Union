package com.ztesoft.inf.communication.client.vo;

import java.io.Serializable;
import java.util.Map;

import com.powerise.ibss.framework.Const;
import com.ztesoft.form.util.StringUtil;

public class ClientOperation implements Serializable{

	private String operationId;
	private String operationCode;
	private String endpointId;
	private String requestId;
	private String responseId;
	private String description;
	private String logLevel;
	private String reqUserId;
	private boolean closed;
	private String deal_success_flag;
	private String charset;

	public ClientOperation() {
	}

	public ClientOperation(Map map) {
		setValues(map);
	}

	private void setValues(Map map) {
		operationId = (String) map.get("op_id");
		operationCode = (String) map.get("op_code");
		endpointId = (String) map.get("ep_id");
		requestId = (String) map.get("req_id");
		responseId = (String) map.get("rsp_id");
		description = (String) map.get("op_desc");
		logLevel = (String) map.get("log_level");
		closed = "T".equals(map.get("close_flag"));
		reqUserId = (String) ((map.get("req_user_id")==null||"".equals(map.get("req_user_id")))?"-1":map.get("req_user_id"));
		String dealSuccess = Const.getStrValue(map, "deal_success_flag");
		deal_success_flag = StringUtil.isEmpty(dealSuccess) ? "1" : dealSuccess;
		charset = (String) map.get("charset");
	}

	public String getOperationId() {
		return operationId;
	}

	public void setOperationId(String operationId) {
		this.operationId = operationId;
	}

	public String getOperationCode() {
		return operationCode;
	}

	public void setOperationCode(String operationCode) {
		this.operationCode = operationCode;
	}

	public String getEndpointId() {
		return endpointId;
	}

	public void setEndpointId(String endpointId) {
		this.endpointId = endpointId;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getResponseId() {
		return responseId;
	}

	public void setResponseId(String responseId) {
		this.responseId = responseId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLogLevel() {
		return logLevel;
	}

	public void setLogLevel(String logLevel) {
		this.logLevel = logLevel;
	}

	public boolean isClosed() {
		return closed;
	}

	public String getReqUserId() {
		return reqUserId;
	}

	public String getDeal_success_flag() {
		return deal_success_flag;
	}

	public void setDeal_success_flag(String deal_success_flag) {
		this.deal_success_flag = deal_success_flag;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}
	
}
