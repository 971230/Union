package zte.net.ecsord.params.bss.vo;

import java.io.Serializable;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class AGENCY_ACCT_PAY_NOTIFY_RSP implements Serializable {
	@ZteSoftCommentAnnotationParam(name = "应答编码0000成功1544 账户余额不足8888 其它", type = "String", isNecessary = "Y", desc = "应答编码0000成功1544 账户余额不足8888 其它")
	private String RESP_CODE;	

	@ZteSoftCommentAnnotationParam(name = "应答描述", type = "String", isNecessary = "Y", desc = "应答描述")
	private String RESP_DESC;

	@ZteSoftCommentAnnotationParam(name = "返回信息", type = "String", isNecessary = "Y", desc = "返回信息")
	private RESP_INFO RESP_INFO;

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

	public RESP_INFO getRESP_INFO() {
		return RESP_INFO;
	}

	public void setRESP_INFO(RESP_INFO rESP_INFO) {
		RESP_INFO = rESP_INFO;
	}	
	
}
