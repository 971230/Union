package com.ztesoft.net.ecsord.params.ecaop.req;

import org.apache.commons.lang3.StringUtils;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.util.ZjCommonUtils;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;

public class WotvBroadbandBindReq extends ZteRequest{
	
	@ZteSoftCommentAnnotationParam(name = "订单号", type = "String", isNecessary = "Y", desc = "订单系统内部订单")
	private String notNeedReqStrOrderId;
	@ZteSoftCommentAnnotationParam(name = "绑定方式", type = "String", isNecessary = "N", desc = "1:根据号码绑定;2:根据客户绑定")
	private String op_type="1";
	@ZteSoftCommentAnnotationParam(name = "业务号码", type = "String", isNecessary = "N", desc = "当op_type=1 时不可为空")
	private String service_num;
	@ZteSoftCommentAnnotationParam(name = "证件类型", type = "String", isNecessary = "N", desc = "当op_type=2 时不可为空")
	private String cert_type;
	@ZteSoftCommentAnnotationParam(name = "证件号码", type = "String", isNecessary = "N", desc = "当op_type=3 时不可为空")
	private String cert_num;
	@ZteSoftCommentAnnotationParam(name = "沃TV ID", type = "String", isNecessary = "N", desc = "沃TV ID")
	private String wotv_id;
	@ZteSoftCommentAnnotationParam(name = "对端操作点", type = "String", isNecessary = "Y", desc = "对端操作点")
	private String office_id;
	@ZteSoftCommentAnnotationParam(name = "对端操作员", type = "String", isNecessary = "Y", desc = "对端操作员")
	private String operator_id;
	
	private OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId);

	public String getOffice_id() {
		String source_from = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderExtBusiRequest().getOrder_from();
		String OUT_OFFICE = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.OUT_OFFICE);
		if(!StringUtils.isEmpty(source_from)&&StringUtil.equals(source_from, "10071")&&!StringUtil.isEmpty(OUT_OFFICE)){
			office_id = OUT_OFFICE;
		}else{
			office_id = ZjCommonUtils.getGonghaoInfoByOrderId(notNeedReqStrOrderId).getDept_id();
		}
		return office_id;
	}

	public void setOffice_id(String office_id) {
		this.office_id = office_id;
	}

	public String getOperator_id() {
		String source_from = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderExtBusiRequest().getOrder_from();
		String OUT_OPERATOR = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.OUT_OPERATOR);
		if(!StringUtils.isEmpty(source_from)&&StringUtil.equals(source_from, "10071")&&!StringUtil.isEmpty(OUT_OPERATOR)){
			operator_id = OUT_OPERATOR;
		}else{
			operator_id = ZjCommonUtils.getGonghaoInfoByOrderId(notNeedReqStrOrderId).getUser_code();
		}
		return operator_id;
	}

	public void setOperator_id(String operator_id) {
		this.operator_id = operator_id;
	}

	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

	public String getOp_type() {
		return op_type;
	}

	public void setOp_type(String op_type) {
		this.op_type = op_type;
	}

	public String getService_num() {
		service_num = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderAdslBusiRequest().get(0).getAdsl_number();
		return service_num;
	}

	public void setService_num(String service_num) {
		this.service_num = service_num;
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

	public String getWotv_id() {
		wotv_id = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderAdslBusiRequest().get(0).getWotv_num();
		return wotv_id;
	}

	public void setWotv_id(String wotv_id) {
		this.wotv_id = wotv_id;
	}
	
	public OrderTreeBusiRequest getOrderTree() {
		return orderTree;
	}

	public void setOrderTree(OrderTreeBusiRequest orderTree) {
		this.orderTree = orderTree;
	}

	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getApiMethodName() {
		return "com.zte.unicomService.zj.broadband.wotvBroadbandBindReq";
	}

}
