package com.ztesoft.net.ecsord.params.ecaop.vo;

import java.util.List;

public class BROADBANDSELFQUERYRSPVO {

	private String RESP_CODE;
	private String RESP_DESC;
	private ORDERINFOVO ORDER_INFO;
	private List<PARAVO> PARA;
	
	public String getRESP_CODE() {
		return RESP_CODE;
	}
	public void setRESP_CODE(String rESP_CODE) {
		RESP_CODE = rESP_CODE;
	}
	public String getRESP_DESC() {
		return RESP_DESC;
	}
	public void setRESP_DESC(String rESP_DESC) {
		RESP_DESC = rESP_DESC;
	}
	public ORDERINFOVO getORDER_INFO() {
		return ORDER_INFO;
	}
	public void setORDER_INFO(ORDERINFOVO oRDER_INFO) {
		ORDER_INFO = oRDER_INFO;
	}
	public List<PARAVO> getPARA() {
		return PARA;
	}
	public void setPARA(List<PARAVO> pARA) {
		PARA = pARA;
	}
	
}
