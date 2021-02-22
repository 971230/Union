package zte.net.ecsord.params.zb.req;

import params.ZteRequest;
import zte.net.ecsord.params.zb.vo.Sender;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

public class NotifyDeliveryGDRequest extends ZteRequest{
	@ZteSoftCommentAnnotationParam(name="访问流水",type="String",isNecessary="Y",desc="ActiveNo：访问流水")
	private String ActiveNo;
	
	@ZteSoftCommentAnnotationParam(name="订单编号",type="String",isNecessary="Y",desc="OrderId：订单编号")
	private String OrderId;
	
	@ZteSoftCommentAnnotationParam(name="是否发送物流",type="String",isNecessary="Y",desc="SendExpCom：是否发送物流")
	private String SendExpCom;
	
	@ZteSoftCommentAnnotationParam(name="物流公司编码",type="String",isNecessary="Y",desc="ExpComCode：物流公司编码")
	private String ExpComCode;
	
	@ZteSoftCommentAnnotationParam(name="物流公司名称",type="String",isNecessary="Y",desc="ExpComName：物流公司名称")
	private String ExpComName;
	
	@ZteSoftCommentAnnotationParam(name="物流员工",type="String",isNecessary="N",desc="ExpStaff：物流员工")
	private String ExpStaff;
	
	@ZteSoftCommentAnnotationParam(name="物流单号",type="String",isNecessary="Y",desc="ExpNo：物流单号")
	private String ExpNo;
	
	@ZteSoftCommentAnnotationParam(name="物流员工联系电话",type="String",isNecessary="N",desc="ExpPhone：物流员工联系电话")
	private String ExpPhone;
	
	@ZteSoftCommentAnnotationParam(name="物流备注",type="String",isNecessary="N",desc="ExpRemark：物流备注")
	private String ExpRemark;
	
	@ZteSoftCommentAnnotationParam(name="寄件人节点 ",type="String",isNecessary="N",desc="Sender：寄件人节点 ")
	private Sender Sender;
	
	
	public String getActiveNo() {
		return ActiveNo;
	}

	public void setActiveNo(String activeNo) {
		ActiveNo = activeNo;
	}

	public String getOrderId() {
		return OrderId;
	}

	public void setOrderId(String orderId) {
		OrderId = orderId;
	}

	public String getSendExpCom() {
		return SendExpCom;
	}

	public void setSendExpCom(String sendExpCom) {
		SendExpCom = sendExpCom;
	}

	public String getExpComCode() {
		return ExpComCode;
	}

	public void setExpComCode(String expComCode) {
		ExpComCode = expComCode;
	}

	public String getExpComName() {
		return ExpComName;
	}

	public void setExpComName(String expComName) {
		ExpComName = expComName;
	}

	public String getExpStaff() {
		return ExpStaff;
	}

	public void setExpStaff(String expStaff) {
		ExpStaff = expStaff;
	}

	public String getExpNo() {
		return ExpNo;
	}

	public void setExpNo(String expNo) {
		ExpNo = expNo;
	}

	public String getExpPhone() {
		return ExpPhone;
	}

	public void setExpPhone(String expPhone) {
		ExpPhone = expPhone;
	}

	public String getExpRemark() {
		return ExpRemark;
	}

	public void setExpRemark(String expRemark) {
		ExpRemark = expRemark;
	}

	public Sender getSender() {
		return Sender;
	}

	public void setSender(Sender sender) {
		Sender = sender;
	}

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return "com.zte.unicomService.zb.NotifyDeliveryGD";
	}

}
