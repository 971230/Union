package params.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteRequest;
import params.resp.ZbOrderDistributeResp;

public class OpenAccountSubmitOrderReq extends ZteRequest<ZbOrderDistributeResp>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6584059702079968248L;
	@ZteSoftCommentAnnotationParam(name="外部订单号",type="String",isNecessary="Y",desc="外部订单号")
	private String orderNo;
	@ZteSoftCommentAnnotationParam(name="外部订单ID",type="String",isNecessary="Y",desc="外部订单ID")
	private String orderId;
	@ZteSoftCommentAnnotationParam(name="USIM卡号",type="String",isNecessary="Y",desc="USIM卡号")
	private String manualUsimCardNum;
	@ZteSoftCommentAnnotationParam(name="开户单号(BSS/ESS/CBSS)",type="String",isNecessary="Y",desc="开户单号(BSS/ESS/CBSS)")
	private String manualOrderNo;
	
	
	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return "zte.net.iservice.impl.ZteCrawlerOpenService.openAccountSubmitOrder";
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
	public String getManualUsimCardNum() {
		return manualUsimCardNum;
	}
	public void setManualUsimCardNum(String manualUsimCardNum) {
		this.manualUsimCardNum = manualUsimCardNum;
	}
	public String getManualOrderNo() {
		return manualOrderNo;
	}
	public void setManualOrderNo(String manualOrderNo) {
		this.manualOrderNo = manualOrderNo;
	}

}
