package zte.params.orderctn.req;

import params.ZteRequest;
import zte.params.orderctn.resp.HSOrderCtnB2BResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class HSOrderCtnB2BReq extends ZteRequest<HSOrderCtnB2BResp> {

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
		return "zte.net.iservice.impl.ZteOrderCtnOpenService.hsOrderCtnB2B";
	}
	
}
