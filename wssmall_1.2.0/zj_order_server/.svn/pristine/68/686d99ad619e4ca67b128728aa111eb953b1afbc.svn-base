package com.ztesoft.net.ecsord.params.ecaop.req;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.common.util.StringUtils;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.net.util.ZjCommonUtils;

public class UserActivationReq extends ZteRequest {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ZteSoftCommentAnnotationParam(name = "订单号", type = "String", isNecessary = "Y", desc = "订单系统内部订单")
	private String notNeedReqStrOrderId;
	@ZteSoftCommentAnnotationParam(name = "压单开户订单号,可空", type = "String", isNecessary = "Y", desc = "压单开户订单号,可空")
	private String order_id;
	@ZteSoftCommentAnnotationParam(name = "联通服务号码", type = "String", isNecessary = "Y", desc = "service_num:联通服务号码")
	private String service_num;
	@ZteSoftCommentAnnotationParam(name = "业务类型", type = "String", isNecessary = "Y", desc = "oper_type:业务类型")
	private String oper_type;//0:激活;1:撤销,可空默认激活
	@ZteSoftCommentAnnotationParam(name = "操作点", type = "String", isNecessary = "Y", desc = "office_id：时间")
	private String office_id;
	@ZteSoftCommentAnnotationParam(name = "操作员", type = "String", isNecessary = "Y", desc = "operator_id：发起方系统标识")
	private String operator_id;

	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

	public String getService_num() {
	    if(StringUtils.isNotEmpty(this.service_num)){
	        return service_num;
	    }else{
	        return CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.PHONE_NUM);
	    }
	}

	public void setService_num(String service_num) {
		this.service_num = service_num;
	}

	public String getOffice_id() {
	    if(StringUtils.isNotEmpty(this.office_id)){
	        return office_id;
	    }else{
	        return ZjCommonUtils.getGonghaoInfoByOrderId(notNeedReqStrOrderId).getDept_id();
	    }
	}

	public void setOffice_id(String office_id) {
		this.office_id = office_id;
	}

	public String getOperator_id() {
	    if(StringUtils.isNotEmpty(this.operator_id)){
            return operator_id;
        }else{
            return ZjCommonUtils.getGonghaoInfoByOrderId(notNeedReqStrOrderId).getUser_code();
        }
	}

	public void setOperator_id(String operator_id) {
		this.operator_id = operator_id;
	}

	public String getOper_type() {
		return oper_type;
	}

	public void setOper_type(String oper_type) {
		this.oper_type = oper_type;
	}

	public String getOrder_id() {
		return CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.BSSORDERID);
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.net.iservice.impl.ZJInfServices.callUserActivation";
	}

}
