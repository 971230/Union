package zte.net.ecsord.params.zb.resp;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;


import zte.net.ecsord.params.base.resp.ZteBusiResponse;
import zte.net.ecsord.params.zb.vo.AccountRsp;

public class PrecheckOpenAcctResponse extends ZteBusiResponse {
	@ZteSoftCommentAnnotationParam(name = "访问流水", type = "String", isNecessary = "Y", desc = "ActiveNo：访问流水")
	private String ActiveNo;

	@ZteSoftCommentAnnotationParam(name = "返回信息", type = "AccountRsp", isNecessary = "Y", desc = "AccountRsp：返回信息")
	private AccountRsp AccountRsp;

	public String getActiveNo() {
		return ActiveNo;
	}

	public void setActiveNo(String activeNo) {
		ActiveNo = activeNo;
	}

	public AccountRsp getAccountRsp() {
		return AccountRsp;
	}

	public void setAccountRsp(AccountRsp accountRsp) {
		AccountRsp = accountRsp;
	}

}
