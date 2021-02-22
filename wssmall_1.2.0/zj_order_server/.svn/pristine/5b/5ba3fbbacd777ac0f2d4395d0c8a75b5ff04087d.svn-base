package com.ztesoft.net.ecsord.params.ecaop.resp;

import java.util.List;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.ecsord.params.ecaop.vo.IntentOrderInfo;

import params.ZteResponse;

public class IntentOrderQueryResp extends ZteResponse{

	@ZteSoftCommentAnnotationParam(name="返回代码",type="String",isNecessary="N",desc="1未查询到意向单0查询成功-1 系统异常")
	private String resp_code;
	@ZteSoftCommentAnnotationParam(name="返回描述",type="String",isNecessary="N",desc="失败原因")
	private String resp_msg;
	@ZteSoftCommentAnnotationParam(name="意向单信息",type="String",isNecessary="N",desc="意向单信息")
	private List<IntentOrderInfo> intent_order_info;
	
	public String getResp_code() {
		return resp_code;
	}
	public void setResp_code(String resp_code) {
		this.resp_code = resp_code;
	}
	public String getResp_msg() {
		return resp_msg;
	}
	public void setResp_msg(String resp_msg) {
		this.resp_msg = resp_msg;
	}
	public List<IntentOrderInfo> getIntent_order_info() {
		return intent_order_info;
	}
	public void setIntent_order_info(List<IntentOrderInfo> intent_order_info) {
		this.intent_order_info = intent_order_info;
	}
	
}
