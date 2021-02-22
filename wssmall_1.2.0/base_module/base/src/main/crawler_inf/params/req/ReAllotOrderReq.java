package params.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteRequest;

public class ReAllotOrderReq extends ZteRequest {
	@ZteSoftCommentAnnotationParam(name="外部订单号",type="String",isNecessary="Y",desc="外部订单号")
	private String orderNo;
	@ZteSoftCommentAnnotationParam(name="外部订单ID",type="String",isNecessary="Y",desc="外部订单ID")
	private String orderId;
	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
	}

	@Override
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return "zte.net.iservice.impl.ZteCrawlerOpenService.reAllotOrder";
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
}