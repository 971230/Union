package params.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteRequest;
import params.resp.ZbOrderDistributeResp;

public class ZbOrderDistributeReq extends ZteRequest<ZbOrderDistributeResp>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6584059702079968248L;
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
		return "zte.net.iservice.impl.ZteCrawlerOpenService.orderDistribute";
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

}
