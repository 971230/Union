package com.ztesoft.net.ecsord.params.ecaop.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.util.ZjCommonUtils;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.SpecConsts;
import zte.net.ecsord.common.StypeConsts;

public class QryBroadbandFeeReq extends ZteRequest{

	@ZteSoftCommentAnnotationParam(name = "订单号", type = "String", isNecessary = "Y", desc = "订单系统内部订单")
	private String notNeedReqStrOrderId;
	@ZteSoftCommentAnnotationParam(name = "地区编码", type = "String", isNecessary = "Y", desc = "地区编码")
	private String region_id;
	@ZteSoftCommentAnnotationParam(name = "地市编码", type = "String", isNecessary = "Y", desc = "地市编码")
	private String county_id;
	@ZteSoftCommentAnnotationParam(name = "业务类型", type = "String", isNecessary = "Y", desc = "业务类型")
	private String service_type;
	@ZteSoftCommentAnnotationParam(name = "宽带速率", type = "String", isNecessary = "Y", desc = "宽带速率")
	private String low_rate;
	@ZteSoftCommentAnnotationParam(name = "用户种类", type = "String", isNecessary = "Y", desc = "用户种类")
	private String user_kind;
	@ZteSoftCommentAnnotationParam(name = "活动ID", type = "String", isNecessary = "Y", desc = "活动ID")
	private String scheme_id;
	@ZteSoftCommentAnnotationParam(name = "办理操作员", type = "String", isNecessary = "Y", desc = "办理操作员")
	private String deal_operator;
	@ZteSoftCommentAnnotationParam(name = "办理操作点", type = "String", isNecessary = "Y", desc = "办理操作点")
	private String deal_office_id;
	
	
	
	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

	public String getRegion_id() {
		region_id = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ORDER_CITY_CODE);
		region_id = CommonDataFactory.getInstance().getOtherDictVodeValue(StypeConsts.BSS_AREA_CODE, region_id);
		return region_id;
	}

	public void setRegion_id(String region_id) {
		this.region_id = region_id;
	}

	public String getCounty_id() {
		String OUT_OPERATOR = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.OUT_OPERATOR);
		if(!StringUtil.isEmpty(OUT_OPERATOR)){
			county_id = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderAdslBusiRequest().get(0).getCounty_id();
		}else{
			county_id = ZjCommonUtils.getGonghaoInfoByOrderId(notNeedReqStrOrderId).getCounty_id();
		}
		return county_id;
		//return CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderAdslBusiRequest().get(0).getCounty_id();
	}

	public void setCounty_id(String county_id) {
		this.county_id = county_id;
	}

	public String getService_type() {
		String goods_id=CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderItemBusiRequests().get(0).getGoods_id();
		String service_type1 = CommonDataFactory.getInstance().getGoodSpec(notNeedReqStrOrderId, goods_id, "service_type");
		service_type = service_type1;
		return service_type;
	}

	public void setService_type(String service_type) {
		this.service_type = service_type;
	}
	
	public String getLow_rate() {
		return CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderAdslBusiRequest().get(0).getAdsl_speed();
	}

	public void setLow_rate(String low_rate) {
		this.low_rate = low_rate;
	}

	public String getUser_kind() {
		return CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderAdslBusiRequest().get(0).getUser_kind();
	}

	public void setUser_kind(String user_kind) {
		this.user_kind = user_kind;
	}

	public String getScheme_id() {
		String goods_id=CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderItemBusiRequests().get(0).getGoods_id();
		String p_code = CommonDataFactory.getInstance().getGoodSpec(notNeedReqStrOrderId, goods_id, SpecConsts.P_CODE);
		return scheme_id;
	}

	public void setScheme_id(String scheme_id) {
		this.scheme_id = scheme_id;
	}

	public String getDeal_operator() {
		return ZjCommonUtils.getGonghaoInfoByOrderId(notNeedReqStrOrderId).getUser_code();
	}

	public void setDeal_operator(String deal_operator) {
		this.deal_operator = deal_operator;
	}

	public String getDeal_office_id() {
		return ZjCommonUtils.getGonghaoInfoByOrderId(notNeedReqStrOrderId).getDept_id();
	}

	public void setDeal_office_id(String deal_office_id) {
		this.deal_office_id = deal_office_id;
	}
	
	


	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getApiMethodName() {
		return "com.zte.unicomService.zj.broadband.qryBroadbandFeeReq";
	}
}
