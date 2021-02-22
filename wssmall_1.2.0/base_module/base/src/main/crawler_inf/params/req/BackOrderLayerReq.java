package params.req;

import com.ztesoft.api.ApiRuleException;

import params.ZteRequest;

public class BackOrderLayerReq extends ZteRequest{

	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return "zte.net.iservice.impl.ZteCrawlerOpenService.backOrderLayer";
	}
	private String notNeedReqStrOrderId;
	private String orderNo;//总商订单编号
	
	private String backReasonDesc;//退单意见is_refund
	
	private String orderDelayTag;//是否退款 1:退款，2：不退款

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderId(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getBackReasonDesc() {
		return backReasonDesc;
	}

	public void setBackReasonDesc(String backReasonDesc) {
		this.backReasonDesc = backReasonDesc;
	}

	public String getOrderDelayTag() {
		return orderDelayTag;
	}

	public void setOrderDelayTag(String orderDelayTag) {
		this.orderDelayTag = orderDelayTag;
	}

	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}
	
	
	
}
