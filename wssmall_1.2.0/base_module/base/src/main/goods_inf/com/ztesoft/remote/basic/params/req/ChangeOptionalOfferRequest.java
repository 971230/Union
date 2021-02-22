package com.ztesoft.remote.basic.params.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.remote.basic.params.resp.ChangeOptionalOfferResponse;

import params.ZteRequest;

public class ChangeOptionalOfferRequest extends
		ZteRequest<ChangeOptionalOfferResponse> {

	@ZteSoftCommentAnnotationParam(name = "业务请求类型", type = "Integer", isNecessary = "Y", desc = "0（正式单）、1（校验单）")
	private Integer busi_type;

	@ZteSoftCommentAnnotationParam(name = "请求交易流水", type = "String", isNecessary = "Y", desc = "请求交易流水")
	private String transation_nbr;

	@ZteSoftCommentAnnotationParam(name = "受理员工ID", type = "Long", isNecessary = "Y", desc = "受理员工ID")
	private Long staff_id;

	@ZteSoftCommentAnnotationParam(name = "地区ID", type = "Long", isNecessary = "Y", desc = "地区ID")
	private Long common_region_id;

	@ZteSoftCommentAnnotationParam(name = "受理渠道ID", type = "Long", isNecessary = "Y", desc = "受理渠道ID")
	private Long channel_id;

	@ZteSoftCommentAnnotationParam(name = "客户编码", type = "String", isNecessary = "Y", desc = "客户编码")
	private String cust_number;

	@ZteSoftCommentAnnotationParam(name = "产品id", type = "String", isNecessary = "Y", desc = "产品id(查询)")
	private String product_id;

	@ZteSoftCommentAnnotationParam(name = "规则编码", type = "String", isNecessary = "Y", desc = "规则编码")
	private String service_code;

	@ZteSoftCommentAnnotationParam(name = "销售品实例ID", type = "String", isNecessary = "Y", desc = "销售品实例ID")
	private String prod_offer_inst_id;

	@ZteSoftCommentAnnotationParam(name = "主销售品业务编码", type = "String", isNecessary = "N", desc = "主销售品业务编码")
	private String prod_offe_nbr;

	@ZteSoftCommentAnnotationParam(name = "所属销售品实例ID", type = "String", isNecessary = "N", desc = "所属销售品实例ID")
	private String par_prod_offer_inst_id;

	@ZteSoftCommentAnnotationParam(name = "销售品与所属主销售品之间的关系类型", type = "String", isNecessary = "N", desc = "销售品与所属主销售品之间的关系类型")
	private String relationTypeCd;

	@Override
	@NotDbField
	public void check() throws ApiRuleException {

	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return null;
	}

	public Integer getBusi_type() {
		return busi_type;
	}

	public void setBusi_type(Integer busi_type) {
		this.busi_type = busi_type;
	}

	public String getTransation_nbr() {
		return transation_nbr;
	}

	public void setTransation_nbr(String transation_nbr) {
		this.transation_nbr = transation_nbr;
	}

	public Long getStaff_id() {
		return staff_id;
	}

	public void setStaff_id(Long staff_id) {
		this.staff_id = staff_id;
	}

	public Long getCommon_region_id() {
		return common_region_id;
	}

	public void setCommon_region_id(Long common_region_id) {
		this.common_region_id = common_region_id;
	}

	public Long getChannel_id() {
		return channel_id;
	}

	public void setChannel_id(Long channel_id) {
		this.channel_id = channel_id;
	}

	public String getProd_offer_inst_id() {
		return prod_offer_inst_id;
	}

	public void setProd_offer_inst_id(String prod_offer_inst_id) {
		this.prod_offer_inst_id = prod_offer_inst_id;
	}

	public String getProd_offe_nbr() {
		return prod_offe_nbr;
	}

	public void setProd_offe_nbr(String prod_offe_nbr) {
		this.prod_offe_nbr = prod_offe_nbr;
	}

	public String getPar_prod_offer_inst_id() {
		return par_prod_offer_inst_id;
	}

	public void setPar_prod_offer_inst_id(String par_prod_offer_inst_id) {
		this.par_prod_offer_inst_id = par_prod_offer_inst_id;
	}

	public String getRelationTypeCd() {
		return relationTypeCd;
	}

	public void setRelationTypeCd(String relationTypeCd) {
		this.relationTypeCd = relationTypeCd;
	}

	public String getCust_number() {
		return cust_number;
	}

	public void setCust_number(String cust_number) {
		this.cust_number = cust_number;
	}

	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public String getService_code() {
		return service_code;
	}

	public void setService_code(String service_code) {
		this.service_code = service_code;
	}

}
