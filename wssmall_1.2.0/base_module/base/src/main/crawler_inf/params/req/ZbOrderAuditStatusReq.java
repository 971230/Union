package params.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteRequest;
import params.ZteResponse;

public class ZbOrderAuditStatusReq extends ZteRequest<ZteResponse>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5109953989954223218L;
	@ZteSoftCommentAnnotationParam(name="外部订单号",type="String",isNecessary="Y",desc="外部订单号")
	private String orderNo;
	@ZteSoftCommentAnnotationParam(name="外部订单ID",type="String",isNecessary="Y",desc="外部订单ID")
	private String orderId;
	@ZteSoftCommentAnnotationParam(name="证件类型 ",type="String",isNecessary="Y",desc="证件类型01:15位身份证,02:18位身份证  ")
	private String certType;
	@ZteSoftCommentAnnotationParam(name="是否校验通过 ",type="String",isNecessary="Y",desc="是否校验通过，0：不通过，1：通过  ")
	private String isAuditPass;
	@ZteSoftCommentAnnotationParam(name="联系人手机 ",type="String",isNecessary="Y",desc="联系人手机，用于校验不通过时发送短信 ")
	private String contactsPhoneNo;
	@ZteSoftCommentAnnotationParam(name="是否发短信 ",type="String",isNecessary="Y",desc="用于校验不通过时确定是否发送短信  0：不发短信，1：发短信")
	private String isSendMessage;
	@ZteSoftCommentAnnotationParam(name="短信内容 ",type="String",isNecessary="Y",desc="短信内容,用于校验不通过时发送短信")
	private String sendMessage;
	@ZteSoftCommentAnnotationParam(name="是否已经外呼 ",type="String",isNecessary="Y",desc="是否已经外呼0：未外呼，1：已外呼")
	private String isOutboundCall;
	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return "zte.net.iservice.impl.ZteCrawlerOpenService.orderAuditStatusModify";
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

	public String getCertType() {
		return certType;
	}

	public void setCertType(String certType) {
		this.certType = certType;
	}

	public String getIsAuditPass() {
		return isAuditPass;
	}

	public void setIsAuditPass(String isAuditPass) {
		this.isAuditPass = isAuditPass;
	}

	public String getContactsPhoneNo() {
		return contactsPhoneNo;
	}

	public void setContactsPhoneNo(String contactsPhoneNo) {
		this.contactsPhoneNo = contactsPhoneNo;
	}

	public String getIsSendMessage() {
		return isSendMessage;
	}

	public void setIsSendMessage(String isSendMessage) {
		this.isSendMessage = isSendMessage;
	}

	public String getSendMessage() {
		return sendMessage;
	}

	public void setSendMessage(String sendMessage) {
		this.sendMessage = sendMessage;
	}

	public String getIsOutboundCall() {
		return isOutboundCall;
	}

	public void setIsOutboundCall(String isOutboundCall) {
		this.isOutboundCall = isOutboundCall;
	}

}
