package com.ztesoft.net.ecsord.params.ecaop.req;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

public class OldUserCheckReq extends ZteRequest {

	private static final long serialVersionUID = 1L;

	@ZteSoftCommentAnnotationParam(name = "订单中心订单ID", type = "String", isNecessary = "Y", desc = "order_id：订单中心订单ID")
	private String notNeedReqStrOrderId;

	@ZteSoftCommentAnnotationParam(name = "服务号码", type = "String", isNecessary = "Y", desc = "serial_num：服务号码")
	private String serial_num;

	@ZteSoftCommentAnnotationParam(name = "服务号码串", type = "String", isNecessary = "Y", desc = "serial_num_list：服务号码串")
	private String serial_num_list;

	@ZteSoftCommentAnnotationParam(name = "业务编码", type = "String", isNecessary = "Y", desc = "busi_code：业务编码")
	private String busi_code;

	@ZteSoftCommentAnnotationParam(name = "操作员", type = "String", isNecessary = "Y", desc = "operator_id：操作员")
	private String operator_id;

	@ZteSoftCommentAnnotationParam(name = "操作点", type = "String", isNecessary = "Y", desc = "office_id：操作点")
	private String office_id;

	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

	public String getSerial_num() {
		serial_num = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.PHONE_NUM);
		return serial_num;
	}

	public void setSerial_num(String serial_num) {
		this.serial_num = serial_num;
	}

	public String getSerial_num_list() {
		serial_num_list = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.PHONE_NUM);
		return serial_num_list;
	}

	public void setSerial_num_list(String serial_num_list) {
		this.serial_num_list = serial_num_list;
	}

	public String getBusi_code() {
		busi_code = "900015_1";
		return busi_code;
	}

	public void setBusi_code(String busi_code) {
		this.busi_code = busi_code;
	}

	public String getOperator_id() {
		operator_id = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.OUT_OPERATOR);
		return operator_id;
	}

	public void setOperator_id(String operator_id) {
		this.operator_id = operator_id;
	}

	public String getOffice_id() {
		office_id = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.OUT_OFFICE);
		return office_id;
	}

	public void setOffice_id(String office_id) {
		this.office_id = office_id;
	}

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.net.iservice.impl.ZJInfServices.oldUserCheck";
	}

}
