package com.ztesoft.net.ecsord.params.ecaop.req;

import params.ZteRequest;
import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/**
 * 2.6.12 客户下用户数据查询接口
 * 
 * @author 宋琪
 */
public class UserDataQryReq extends ZteRequest {

	private static final long serialVersionUID = 1L;

	@ZteSoftCommentAnnotationParam(name = "订单号", type = "String", isNecessary = "Y", desc = "订单号")
	private String notNeedReqStrOrderId;
	@ZteSoftCommentAnnotationParam(name = "办理操作员", type = "String", isNecessary = "Y", desc = "办理操作员")
	private String operator_id;
	@ZteSoftCommentAnnotationParam(name = "办理操作点", type = "String", isNecessary = "Y", desc = "办理操作点")
	private String office_id;
	@ZteSoftCommentAnnotationParam(name = "证件类型", type = "String", isNecessary = "Y", desc = "证件类型")
	private String cert_type;
	@ZteSoftCommentAnnotationParam(name = "证件号码", type = "String", isNecessary = "Y", desc = "证件号码")
	private String cert_num;
	@ZteSoftCommentAnnotationParam(name = "附加用户数，默认1", type = "String", isNecessary = "Y", desc = "附加用户数，默认1")
	private String add_num;
	@ZteSoftCommentAnnotationParam(name = "客户名称", type = "String", isNecessary = "Y", desc = "客户名称")
	private String cust_name;

	public String getOperator_id() {
		return operator_id;
	}

	public void setOperator_id(String operator_id) {
		this.operator_id = operator_id;
	}

	public String getOffice_id() {
		return office_id;
	}

	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

	public void setOffice_id(String office_id) {
		this.office_id = office_id;
	}

	public String getCert_type() {
		return cert_type;
	}

	public void setCert_type(String cert_type) {
		this.cert_type = cert_type;
	}

	public String getCert_num() {
		return cert_num;
	}

	public void setCert_num(String cert_num) {
		this.cert_num = cert_num;
	}

	public String getAdd_num() {
		return add_num;
	}

	public void setAdd_num(String add_num) {
		this.add_num = add_num;
	}

	public String getCust_name() {
		return cust_name;
	}

	public void setCust_name(String cust_name) {
		this.cust_name = cust_name;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public void check() throws ApiRuleException {

	}

	@Override
	public String getApiMethodName() {
		return "zte.net.iservice.impl.ZJInfServices.userDataQry";
	}
}