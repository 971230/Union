package zte.params.orderctn.req;

import params.ZteRequest;
import zte.params.orderctn.resp.HSOrderCtnResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class HSOrderCtnReq extends ZteRequest<HSOrderCtnResp> {

	@ZteSoftCommentAnnotationParam(name = "请求报文", type = "String", isNecessary = "Y", desc = "请求报文")
	private String reqMsgStr;

	public String getReqMsgStr() {
		return reqMsgStr;
	}

	public void setReqMsgStr(String reqMsgStr) {
		this.reqMsgStr = reqMsgStr;
	}

	@Override
	public void check() throws ApiRuleException {
	}

	@Override
	public String getApiMethodName() {
		return "zte.net.iservice.impl.ZteOrderCtnOpenService.hsOrderCtn";
	}
	
}
