package params.req;

import com.ztesoft.api.ApiRuleException;
import params.ZteRequest;

/**
 * 退单申请
 * @Description
 * @author  zhangJun
 * @date    2017年1月19日
 */
public class SingleApplicationReq extends ZteRequest{

	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return "zte.net.iservice.impl.ZteCrawlerOpenService.singleApplication";
	}

	private String notNeedReqStrOrderId; 
	
	//退单编码
	private String reasonCode;
	
	//退单描述
	private String reasonDesc;
	
	//退单备注
	private String cancelRemark;
	
	//总商订单id
	private String orderId;
	
	//总商订单编号
	private String orderNo;
	
	//申请环节(orderReview：订单审核环节，automatic：自动开户环节，deliverGoods：发货环节,callOutCharge:外呼确认环节)
	private String link;

	public String getReasonCode() {
		return reasonCode;
	}

	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}

	public String getReasonDesc() {
		return reasonDesc;
	}

	public void setReasonDesc(String reasonDesc) {
		this.reasonDesc = reasonDesc;
	}

	public String getCancelRemark() {
		return cancelRemark;
	}

	public void setCancelRemark(String cancelRemark) {
		this.cancelRemark = cancelRemark;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	
}
