package zte.net.ecsord.params.zb.req;

import java.util.List;
import java.util.Map;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.StypeConsts;
import zte.net.ecsord.params.zb.vo.Sender;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;

public class NotifyDeliveryZBRequest extends ZteRequest {
	@ZteSoftCommentAnnotationParam(name = "访问流水", type = "String", isNecessary = "Y", desc = "ActiveNo：访问流水")
	private String ActiveNo;

	@ZteSoftCommentAnnotationParam(name = "订单编号", type = "String", isNecessary = "Y", desc = "OrderId：订单编号")
	private String OrderId;

	@ZteSoftCommentAnnotationParam(name = "是否发送物流", type = "String", isNecessary = "Y", desc = "SendExpCom：是否发送物流")
	private String SendExpCom;

	@ZteSoftCommentAnnotationParam(name = "物流公司编码", type = "String", isNecessary = "Y", desc = "ExpComCode：物流公司编码")
	private String ExpComCode;

	@ZteSoftCommentAnnotationParam(name = "物流公司名称", type = "String", isNecessary = "Y", desc = "ExpComName：物流公司名称")
	private String ExpComName;

	@ZteSoftCommentAnnotationParam(name = "物流员工", type = "String", isNecessary = "N", desc = "ExpStaff：物流员工")
	private String ExpStaff;

	@ZteSoftCommentAnnotationParam(name = "物流单号", type = "String", isNecessary = "Y", desc = "ExpNo：物流单号")
	private String ExpNo;

	@ZteSoftCommentAnnotationParam(name = "物流员工联系电话", type = "String", isNecessary = "N", desc = "ExpPhone：物流员工联系电话")
	private String ExpPhone;

	@ZteSoftCommentAnnotationParam(name = "物流备注", type = "String", isNecessary = "N", desc = "ExpRemark：物流备注")
	private String ExpRemark;

	@ZteSoftCommentAnnotationParam(name = "寄件人节点 ", type = "Sender", isNecessary = "N", desc = "Sender：寄件人节点 ")
	private Sender Sender;
	
	@ZteSoftCommentAnnotationParam(name = "内部订单号 ", type = "String", isNecessary = "Y", desc = "notNeedReqStrOrderId：内部订单号 ")
	private String notNeedReqStrOrderId;

	public String getActiveNo() {
		boolean isZbOrder = false;
		String order_from = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ORDER_FROM);
		if(EcsOrderConsts.ORDER_FROM_10003.equals(order_from)){
			isZbOrder = true;
		}
		return CommonDataFactory.getInstance().getActiveNo(isZbOrder);
	}

	public void setActiveNo(String activeNo) {
		ActiveNo = activeNo;
	}

	public String getOrderId() {
		OrderId = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ZB_INF_ID);
		return OrderId;
	}

	public void setOrderId(String orderId) {
		OrderId = orderId;
	}

	public String getSendExpCom() {
		return CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.IS_SEND_GOODS);
	}

	public void setSendExpCom(String sendExpCom) {
		SendExpCom = sendExpCom;
	}

	public String getExpComCode() {
		String logi_company_id = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.SHIPPING_COMPANY);
		String logi_company_code = CommonDataFactory.getInstance().getLogiCompanyCode(logi_company_id);
		return CommonDataFactory.getInstance().getOtherDictVodeValue(StypeConsts.ZB_LOGISTICS_COMPANY_CODE, logi_company_code);
	}

	public void setExpComCode(String expComCode) {
		ExpComCode = expComCode;
	}

	public String getExpComName() {
		return CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.SHIPPING_COMPANY_NAME);
	}

	public void setExpComName(String expComName) {
		ExpComName = expComName;
	}

	public String getExpStaff() {
		String shipping_company = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.SHIPPING_COMPANY);
		String order_city_code = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ORDER_CITY_CODE);
		Map logi_company = CommonDataFactory.getInstance().getLogiCompPersonData(shipping_company, order_city_code);
		ExpStaff = (logi_company==null || logi_company.get("post_linkman")==null) ? "收件员" : logi_company.get("post_linkman").toString();
		return ExpStaff;
	}

	public void setExpStaff(String expStaff) {
		ExpStaff = expStaff;
	}

	public String getExpNo() {
		return CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.LOGI_NO);
	}

	public void setExpNo(String expNo) {
		ExpNo = expNo;
	}

	public String getExpPhone() {
		String shipping_company = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.SHIPPING_COMPANY);
		String order_city_code = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ORDER_CITY_CODE);
		Map logi_company = CommonDataFactory.getInstance().getLogiCompPersonData(shipping_company, order_city_code);
		ExpPhone = (logi_company==null || logi_company.get("post_tel")==null) ? "4008111111" : logi_company.get("post_tel").toString();
		return ExpPhone;
	}

	public void setExpPhone(String expPhone) {
		ExpPhone = expPhone;
	}

	public String getExpRemark() {
		ExpRemark = "";
		return ExpRemark;
	}

	public void setExpRemark(String expRemark) {
		ExpRemark = expRemark;
	}

	public Sender getSender() {
		Sender sender = new Sender();
		String order_city_code = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ORDER_CITY_CODE);
		Map postRegion = CommonDataFactory.getInstance().getPostRegion(order_city_code);
		sender.setSedName(postRegion.get("poster")==null?"":postRegion.get("poster").toString());
		sender.setSedTel(postRegion.get("post_tel")==null?"":postRegion.get("post_tel").toString());
		sender.setSedProvince(postRegion.get("province_code")==null?"":postRegion.get("province_code").toString());
		sender.setSedCity(postRegion.get("city_code")==null?"":postRegion.get("city_code").toString());
		sender.setSedCounty(postRegion.get("district_code")==null?"":postRegion.get("district_code").toString());
		sender.setSedAddress(postRegion.get("post_address")==null?"":postRegion.get("post_address").toString());
		return sender;
	}

	public void setSender(Sender sender) {
		Sender = sender;
	}

	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
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
		return "com.zte.unicomService.zb.NotifyDeliveryZB";
	}

}
