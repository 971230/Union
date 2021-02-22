package com.ztesoft.net.ecsord.params.ecaop.req;

import com.ztesoft.api.ApiRuleException; 
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import params.ZteRequest;

public class ObjectReplacePreSubReq extends ZteRequest{
	@ZteSoftCommentAnnotationParam(name = "内部单号", type = "String", isNecessary = "Y", desc = "内部单号")
	private String notNeedReqStrOrderId;
	@ZteSoftCommentAnnotationParam(name = "业务号码", type = "String", isNecessary = "Y", desc = "宽带账号")
	private String service_num;
	@ZteSoftCommentAnnotationParam(name = "业务类型", type = "String", isNecessary = "Y", desc = "业务类型")
	private String service_type;
	@ZteSoftCommentAnnotationParam(name = "操作员", type = "String", isNecessary = "Y", desc = "操作员")
	private String operator_id;
	@ZteSoftCommentAnnotationParam(name = "办理操作点", type = "String", isNecessary = "Y", desc = "办理操作点")
	private String office_id;
	@ZteSoftCommentAnnotationParam(name = "光猫id", type = "String", isNecessary = "Y", desc = "光猫id")
	private String object_id;
	@ZteSoftCommentAnnotationParam(name = "地市编码", type = "String", isNecessary = "Y", desc = "地市编码")
	private String region_id;
	@ZteSoftCommentAnnotationParam(name = "销售模式", type = "String", isNecessary = "Y", desc = "01：免费租用 06：用户自购 07：用户自备用户自备")
	private String sale_mode ;
	@ZteSoftCommentAnnotationParam(name = "新设备档位", type = "String", isNecessary = "N", desc = "00：标准版(光猫) 01：加强版(光猫)")
	private String devic_gear ;
	@ZteSoftCommentAnnotationParam(name = "用户当前终端串号", type = "String", isNecessary = "N", desc = "用户当前终端串号")
	private String old_obj_esn ;
	@ZteSoftCommentAnnotationParam(name = "待拆终端产权", type = "String", isNecessary = "N", desc = "待拆终端产权")
	private String old_obj_owner;
	@ZteSoftCommentAnnotationParam(name = "费用列表", type = "String", isNecessary = "N", desc = "费用列表")
	private String fee_list ;
	@ZteSoftCommentAnnotationParam(name = "费用备注", type = "String", isNecessary = "N", desc = "费用备注")
	private String fee_remark ;
	@ZteSoftCommentAnnotationParam(name = "支付方式", type = "String", isNecessary = "N", desc = "支付方式")
	private String fund_source ;
	@ZteSoftCommentAnnotationParam(name = "是否收费", type = "String", isNecessary = "N", desc = "是否收费")
	private String is_pay ;
	
	public ObjectReplacePreSubReq() {
		
	}

	public ObjectReplacePreSubReq(Builder builder) {
		this.service_num = builder.service_num;
		this.service_type = builder.service_type;
		this.operator_id = builder.operator_id;
		this.office_id = builder.office_id;
		this.object_id = builder.object_id;
		this.region_id = builder.region_id;
		this.sale_mode = builder.sale_mode;
		this.devic_gear = builder.devic_gear;
		this.old_obj_esn = builder.old_obj_esn;
		this.old_obj_owner = builder.old_obj_owner;
		this.fee_list = builder.fee_list ;
		this.fee_remark = builder.fee_remark ;
		this.fund_source = builder.fund_source ;
		this.is_pay = builder.is_pay ;
		this.notNeedReqStrOrderId = builder.notNeedReqStrOrderId;
	}
	
	public static class Builder {
		private String service_num;
		private String service_type;
		private String operator_id;
		private String office_id;
		private String object_id;
		private String region_id;
		private String sale_mode ;
		private String devic_gear ;
		private String old_obj_esn ;
		private String old_obj_owner;
		private String fee_list ;
		private String fee_remark ;
		private String fund_source ;
		private String is_pay ;
		private String notNeedReqStrOrderId;
		
		public Builder notNeedReqStrOrderId(String notNeedReqStrOrderId) {
			this.notNeedReqStrOrderId = notNeedReqStrOrderId ;
			return this ;
		}
		public Builder isPay(String is_pay) {
			this.is_pay = is_pay ;
			return this ;
		}
		
		public Builder feeRemark(String fee_remark) {
			this.fee_remark = fee_remark ;
			return this ;
		}
		
		public Builder fundSource(String fund_source) {
			this.fund_source = fund_source ;
			return this ;
		}
		
		public Builder feeList(String fee_list) {
			this.fee_list = fee_list ;
			return this ;
		}
		
		public Builder oldObjEsn(String old_obj_esn) {
			this.old_obj_esn = old_obj_esn ;
			return this ;
		}
		
		public Builder oldObjOwner(String old_obj_owner) {
			this.old_obj_owner = old_obj_owner ;
			return this ;
		}
		
		public Builder serviceNum(String service_num) {
			this.service_num = service_num ;
			return this ;
		}
		
		public Builder serviceType(String service_type) {
			this.service_type = service_type ;
			return this ;
		}
		
		public Builder operatorId(String operator_id) {
			this.operator_id = operator_id ;
			return this ;
		}
		
		public Builder officeId(String office_id) {
			this.office_id = office_id ;
			return this ;
		}
		
		public Builder objectId(String object_id) {
			this.object_id = object_id ;
			return this ;
		}
		
		public Builder regionId(String region_id) {
			this.region_id = region_id ;
			return this ;
		}
		
		public Builder saleMode(String sale_mode) {
			this.sale_mode = sale_mode ;
			return this ;
		}
		
		public Builder devicGear(String devic_gear) {
			this.devic_gear = devic_gear ;
			return this ;
		}
		
		public ObjectReplacePreSubReq build() {
			return new ObjectReplacePreSubReq(this);
		}
		
	}
	
	
	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

	
	public String getService_num() {
		return service_num;
	}

	public void setService_num(String service_num) {
		this.service_num = service_num;
	}

	public String getService_type() {
		return service_type;
	}

	public void setService_type(String service_type) {
		this.service_type = service_type;
	}

	public String getOperator_id() {
		return operator_id;
	}

	public void setOperator_id(String operator_id) {
		this.operator_id = operator_id;
	}

	public String getOffice_id() {
		return office_id;
	}

	public void setOffice_id(String office_id) {
		this.office_id = office_id;
	}

	public String getObject_id() {
		return object_id;
	}

	public void setObject_id(String object_id) {
		this.object_id = object_id;
	}

	public String getRegion_id() {
		return region_id;
	}

	public void setRegion_id(String region_id) {
		this.region_id = region_id;
	}

	public String getSale_mode() {
		return sale_mode;
	}

	public void setSale_mode(String sale_mode) {
		this.sale_mode = sale_mode;
	}

	public String getDevic_gear() {
		return devic_gear;
	}

	public void setDevic_gear(String devic_gear) {
		this.devic_gear = devic_gear;
	}

	public String getOld_obj_esn() {
		return old_obj_esn;
	}

	public void setOld_obj_esn(String old_obj_esn) {
		this.old_obj_esn = old_obj_esn;
	}

	public String getOld_obj_owner() {
		return old_obj_owner;
	}

	public void setOld_obj_owner(String old_obj_owner) {
		this.old_obj_owner = old_obj_owner;
	}

	public String getFee_list() {
		return fee_list;
	}

	public void setFee_list(String fee_list) {
		this.fee_list = fee_list;
	}

	public String getFee_remark() {
		return fee_remark;
	}

	public void setFee_remark(String fee_remark) {
		this.fee_remark = fee_remark;
	}

	public String getFund_source() {
		return fund_source;
	}

	public void setFund_source(String fund_source) {
		this.fund_source = fund_source;
	}

	public String getIs_pay() {
		return is_pay;
	}

	public void setIs_pay(String is_pay) {
		this.is_pay = is_pay;
	}

	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String getApiMethodName() {
		return "com.zte.unicomService.zj.ZJInfServices.objectReplace";
	}
}
