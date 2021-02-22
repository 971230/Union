package com.ztesoft.net.ecsord.params.ecaop.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteRequest;

public class IntentOrderQueryReq extends ZteRequest{

	@ZteSoftCommentAnnotationParam(name="外部系统订单号",type="String",isNecessary="N",desc="意向单收单单号")
	private String intent_order_id;
	@ZteSoftCommentAnnotationParam(name="意向单订单号",type="String",isNecessary="N",desc="订单中心意向单收单单号")
	private String order_id;
	@ZteSoftCommentAnnotationParam(name="客户联系电话",type="String",isNecessary="N",desc="客户联系电话")
	private String ship_tel;
	@ZteSoftCommentAnnotationParam(name="推荐人电话",type="String",isNecessary="N",desc="推荐人电话")
	private String referee_num;
	@ZteSoftCommentAnnotationParam(name="创建开始时间",type="String",isNecessary="N",desc="创建开始时间")
	private String start_date;
	@ZteSoftCommentAnnotationParam(name="创建结束时间",type="String",isNecessary="N",desc="创建结束时间")
	private String end_date;
	@ZteSoftCommentAnnotationParam(name="意向单状态",type="String",isNecessary="N",desc="意向单状态") 
	private String intent_type;
	@ZteSoftCommentAnnotationParam(name="操作员电话",type="String",isNecessary="N",desc="操作员电话")
	private String operator_num;
	@ZteSoftCommentAnnotationParam(name="工单状态",type="String",isNecessary="N",desc="工单状态 0 — 未处理 1 — 处理成功 2 — 处理失败")
	private String work_order_status;
	/*
	 * add by wcl
	 * 营销用户ID	否	market_user_id	20
	        种子用户id	否	Seed_user_id	20

	 */
	@ZteSoftCommentAnnotationParam(name="营销用户ID",type="String",isNecessary="N",desc="营销用户ID")
	private String market_user_id;
	@ZteSoftCommentAnnotationParam(name="种子用户id",type="String",isNecessary="N",desc="种子用户id")
	private String seed_user_id;
	@ZteSoftCommentAnnotationParam(name="种子用户id",type="String",isNecessary="N",desc="种子用户id")
	private String share_svc_num ;
	@ZteSoftCommentAnnotationParam(name="商品类型",type="String",isNecessary="N",desc="商品类型")
    private String cat_id ;
	@ZteSoftCommentAnnotationParam(name="订单类型",type="String",isNecessary="N",desc="订单类型")
    private String order_type ;
	@ZteSoftCommentAnnotationParam(name="商品编码",type="String",isNecessary="N",desc="商品编码")
    private String prod_code ;
	@ZteSoftCommentAnnotationParam(name="证件号",type="String",isNecessary="N",desc="证件号")
    private String cert_num ;
	
	
	public String getCert_num() {
		return cert_num;
	}

	public void setCert_num(String cert_num) {
		this.cert_num = cert_num;
	}

	public String getOrder_type() {
		return order_type;
	}

	public void setOrder_type(String order_type) {
		this.order_type = order_type;
	}

	public String getProd_code() {
		return prod_code;
	}

	public void setProd_code(String prod_code) {
		this.prod_code = prod_code;
	}

	public String getMarket_user_id() {
		return market_user_id;
	}

	public void setMarket_user_id(String market_user_id) {
		this.market_user_id = market_user_id;
	}

	public String getSeed_user_id() {
		return seed_user_id;
	}

	public void setSeed_user_id(String seed_user_id) {
		this.seed_user_id = seed_user_id;
	}

	
	public String getShare_svc_num() {
		return share_svc_num;
	}

	public void setShare_svc_num(String share_svc_num) {
		this.share_svc_num = share_svc_num;
	}

	public String getIntent_order_id() {
		return intent_order_id;
	}

	public void setIntent_order_id(String intent_order_id) {
		this.intent_order_id = intent_order_id;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getShip_tel() {
		return ship_tel;
	}

	public void setShip_tel(String ship_tel) {
		this.ship_tel = ship_tel;
	}

	public String getReferee_num() {
		return referee_num;
	}

	public void setReferee_num(String referee_num) {
		this.referee_num = referee_num;
	}

	public String getStart_date() {
		return start_date;
	}

	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

	public String getEnd_date() {
		return end_date;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}

	public String getIntent_type() {
		return intent_type;
	}

	public void setIntent_type(String intent_type) {
		this.intent_type = intent_type;
	}

	public String getOperator_num() {
		return operator_num;
	}

	public void setOperator_num(String operator_num) {
		this.operator_num = operator_num;
	}

	public String getWork_order_status() {
		return work_order_status;
	}

	public void setWork_order_status(String work_order_status) {
		this.work_order_status = work_order_status;
	}
	
	public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    @Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return "com.ztesoft.net.ecsord.params.ecaop.req.IntentOrderQueryReq";
	}

}
