package zte.net.ecsord.params.sf.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteRequest;

@SuppressWarnings("rawtypes")
public class OrderSearchServiceRequest extends ZteRequest {

	private static final long serialVersionUID = -2621365551403754715L;
	
	@ZteSoftCommentAnnotationParam(name="订单号",type="String",isNecessary="Y",desc="orderid：订单号")
	private String orderid;
	@ZteSoftCommentAnnotationParam(name="订单号",type="String",isNecessary="Y",desc="orderid：订单号")
	private String verifyCode;

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	@Override
	public void check() throws ApiRuleException {
	}

	@Override
	public String getApiMethodName() {
		return "com.zte.unicomService.sf.orderSearchService";
	}
	
	
}
