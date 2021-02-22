package zte.net.ecsord.params.sf.resp;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteResponse;

public class OrderFilterServiceResponse  extends ZteResponse {
	
	@ZteSoftCommentAnnotationParam(name="返回代码",type="String",isNecessary="Y",desc="resp_code：业务返还码 0000-成功；0001-失败")
	private String resp_code;
	
	@ZteSoftCommentAnnotationParam(name="订单号 ",type="String",isNecessary="Y",desc="orderid：如果需要顺丰提供人工筛单，需要提供此项值")
	private String orderid;
	
	@ZteSoftCommentAnnotationParam(name="筛单结果",type="String",isNecessary="Y",desc="filter_result：1-人工确认，2-可收派，3-不可以收派")
	private String filter_result;
	
	@ZteSoftCommentAnnotationParam(name="原寄地代码",type="String",isNecessary="Y",desc="origincode：原寄地代码")
	private String origincode;
	
	@ZteSoftCommentAnnotationParam(name="目的地代码 ",type="String",isNecessary="Y",desc="destcode：如果可收派，此项不能为空")
	private String destcode;
	
	@ZteSoftCommentAnnotationParam(name="备注",type="String",isNecessary="Y",desc="remark：1-收方超范围，2-派方超范围，3-其他原因")
	private String remark;

	public String getResp_code() {
		return resp_code;
	}

	public void setResp_code(String resp_code) {
		this.resp_code = resp_code;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getFilter_result() {
		return filter_result;
	}

	public void setFilter_result(String filter_result) {
		this.filter_result = filter_result;
	}

	public String getOrigincode() {
		return origincode;
	}

	public void setOrigincode(String origincode) {
		this.origincode = origincode;
	}

	public String getDestcode() {
		return destcode;
	}

	public void setDestcode(String destcode) {
		this.destcode = destcode;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
