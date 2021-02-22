package com.ztesoft.net.ecsord.params.ecaop.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteRequest;

public class DeliveryInfoUpdateReq extends ZteRequest {

	@ZteSoftCommentAnnotationParam(name = "订单号", type = "String", isNecessary = "Y", desc = "order_id  订单中心订单编号")
	private String order_id;

	@ZteSoftCommentAnnotationParam(name = "物流编号", type = "String", isNecessary = "Y", desc = "logi_no  物流公司物流编码")
	private String logi_no;

	@ZteSoftCommentAnnotationParam(name = "订单号", type = "String", isNecessary = "Y", desc = "logi_company_code  物流公司编码")
	private String logi_company_code;

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getLogi_no() {
		return logi_no;
	}

	public void setLogi_no(String logi_no) {
		this.logi_no = logi_no;
	}

	public String getLogi_company_code() {
		return logi_company_code;
	}

	public void setLogi_company_code(String logi_company_code) {
		this.logi_company_code = logi_company_code;
	}

	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub

	}

	@Override
	public String getApiMethodName() {
		return "zte.net.iservice.impl.ZJInfServices.DeliveryInfoUpdateReq";
	}

}
