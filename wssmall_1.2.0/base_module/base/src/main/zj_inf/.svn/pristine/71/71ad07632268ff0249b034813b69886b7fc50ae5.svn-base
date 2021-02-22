package zte.net.ecsord.params.ems.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteRequest;

public class EmsLogisticsNumberGetReq extends ZteRequest {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5242612502214212324L;
	
	@ZteSoftCommentAnnotationParam(name = "订单号", type = "String", isNecessary = "Y", desc = "订单系统内部订单")
	private String notNeedReqStrOrderId;
	
	@ZteSoftCommentAnnotationParam(name = "获取数量", type = "int", isNecessary = "Y", desc = "获取数量")
	private String count;
	@ZteSoftCommentAnnotationParam(name = "运单号类型代码", type = "String", isNecessary = "Y", desc = "运单号类型代码:非邮业务：05,标准快递：06,快递包裹：07")
	private String bizcode;
	
	@Override
	public void check() throws ApiRuleException {

	}

	@Override
	public String getApiMethodName() {
		return "com.zte.unicomService.ems.getEmsLogisticsNumber";
	}

	public String getCount() {
		count = "1";
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getBizcode() {
		bizcode = "06";
		return bizcode;
	}

	public void setBizcode(String bizcode) {
		this.bizcode = bizcode;
	}

	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

}
