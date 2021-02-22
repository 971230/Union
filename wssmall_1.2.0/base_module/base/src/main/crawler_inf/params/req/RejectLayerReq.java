package params.req;

import com.ztesoft.api.ApiRuleException;

import params.ZteRequest;

public class RejectLayerReq extends ZteRequest{

	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return "zte.net.iservice.impl.ZteCrawlerOpenService.rejectLayer";
	}
	private String notNeedReqStrOrderId;
	private String orderId;//总商订单id
	
	private String rejectReasonDesc;//驳回意见

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getRejectReasonDesc() {
		return rejectReasonDesc;
	}

	public void setRejectReasonDesc(String rejectReasonDesc) {
		this.rejectReasonDesc = rejectReasonDesc;
	}

	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}
	

}
