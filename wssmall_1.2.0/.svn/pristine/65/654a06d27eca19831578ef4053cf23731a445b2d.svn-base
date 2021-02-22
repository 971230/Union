package com.ztesoft.remote.basic.params.req;

import params.ZteRequest;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.api.internal.utils.ApiUtils;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.remote.basic.params.resp.QryCustOrderResponse;

public class QryCustOrderRequest extends ZteRequest<QryCustOrderResponse> {

	@ZteSoftCommentAnnotationParam(name="产品接入号码",type="String",isNecessary="Y",desc="产品接入号码")
	private String phoneNum;

	@ZteSoftCommentAnnotationParam(name="客户证件类型",type="String",isNecessary="Y",desc="客户证件类型 ，如不传默认为身份证")
	private String certType;
	
	@ZteSoftCommentAnnotationParam(name="客户证件号码",type="String",isNecessary="Y",desc="客户证件号码")
	private String certNumber;

	@ZteSoftCommentAnnotationParam(name="客户编码",type="String",isNecessary="Y",desc="客户编码")
	private String custNumber;

	@ZteSoftCommentAnnotationParam(name="开始时间",type="String",isNecessary="Y",desc="查询时间段：开始时间")
	private String startDt;

	@ZteSoftCommentAnnotationParam(name="结束时间",type="String",isNecessary="Y",desc="查询时间段：结束时间")
	private String endDt;

	@ZteSoftCommentAnnotationParam(name="客户级订单流水",type="String",isNecessary="Y",desc="客户级订单流水")
	private String custSoNumber;
	
	@ZteSoftCommentAnnotationParam(name = "产品id", type = "String", isNecessary = "Y", desc = "产品id(订单进度查询)")
	private String product_id;

	@ZteSoftCommentAnnotationParam(name = "规则编码", type = "String", isNecessary = "Y", desc = "规则编码")
	private String service_code;

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		if(ApiUtils.isBlank(phoneNum)){
			throw new ApiRuleException("-1", "查询号码不能为空!");
		}
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return null;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getCertType() {
		return certType;
	}

	public void setCertType(String certType) {
		this.certType = certType;
	}

	public String getCertNumber() {
		return certNumber;
	}

	public void setCertNumber(String certNumber) {
		this.certNumber = certNumber;
	}

	public String getCustNumber() {
		return custNumber;
	}

	public void setCustNumber(String custNumber) {
		this.custNumber = custNumber;
	}

	public String getStartDt() {
		return startDt;
	}

	public void setStartDt(String startDt) {
		this.startDt = startDt;
	}

	public String getEndDt() {
		return endDt;
	}

	public void setEndDt(String endDt) {
		this.endDt = endDt;
	}

	public String getCustSoNumber() {
		return custSoNumber;
	}

	public void setCustSoNumber(String custSoNumber) {
		this.custSoNumber = custSoNumber;
	}

	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public String getService_code() {
		return service_code;
	}

	public void setService_code(String service_code) {
		this.service_code = service_code;
	}

	
}
