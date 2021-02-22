package com.ztesoft.net.ecsord.params.ecaop.req;


import java.util.HashMap;
import java.util.Map;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.util.ZjCommonUtils;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.StypeConsts;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;

public class CardInfoGetBSSReq extends ZteRequest{
	@ZteSoftCommentAnnotationParam(name = "订单号", type = "String", isNecessary = "Y", desc = "订单系统内部订单")
	private String notNeedReqStrOrderId;
	@ZteSoftCommentAnnotationParam(name = "预占号码", type = "String", isNecessary = "Y", desc = "白卡获取->service_num:预占号码")
	private String service_num = "";
	@ZteSoftCommentAnnotationParam(name = "地区编码", type = "String", isNecessary = "Y", desc = "白卡获取->region_id:地区编码")
	private String region_id = "";	
	@ZteSoftCommentAnnotationParam(name = "白卡卡号", type = "String", isNecessary = "Y", desc = "白卡获取->iccid:白卡卡号")
	private String iccid = "";
	@ZteSoftCommentAnnotationParam(name = "操作点", type = "String", isNecessary = "Y", desc = "白卡获取->office_id:操作点")
	private String office_id = "";
	@ZteSoftCommentAnnotationParam(name = "操作员", type = "String", isNecessary = "Y", desc = "白卡获取->operator_id:操作员")
	private String operator_id = "";

	
	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

	public String getService_num() {
		return CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.PHONE_NUM);
	}

	public void setService_num(String service_num) {
		this.service_num = service_num;
	}

	public String getRegion_id() {
		String region_id = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,AttrConsts.ORDER_CITY_CODE);
		region_id = CommonDataFactory.getInstance().getOtherDictVodeValue(StypeConsts.BSS_AREA_CODE, region_id);
		return region_id;
	}

	public void setRegion_id(String region_id) {
		this.region_id = region_id;
	}

	public String getIccid() {
		return CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ICCID);
	}

	public void setIccid(String iccid) {
		this.iccid = iccid;
	}

	public String getOffice_id() {
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(this.notNeedReqStrOrderId);
		
		if(orderTree!=null && "1".equals(orderTree.getOrderExtBusiRequest().getIs_work_custom())){
			String deal_method = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
					"order_deal_method");
			
			//自定义流程
			if("2".equals(deal_method)){
				//线下方式受理业务，先取传入的操作点
				this.office_id = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
						AttrConsts.OUT_OFFICE);
				
				if (StringUtil.isEmpty(this.office_id)) {
					//操作点没传的取配置的操作点
					this.office_id = ZjCommonUtils.getGonghaoInfoByOrderId(notNeedReqStrOrderId).getDept_id();
				}
			}else{
				//线上方式直接取配置的操作点
				this.office_id = ZjCommonUtils.getGonghaoInfoByOrderId(notNeedReqStrOrderId).getDept_id();
			}
		}else{
			if (cacheUtil.getConfigInfo("FUKA_ORDER_FROM").contains(
					CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ORDER_FROM))) {
				office_id = ZjCommonUtils.getGonghaoInfoByOrderId(notNeedReqStrOrderId).getDept_id();
			} else {
				String OUT_OFFICE = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
						AttrConsts.OUT_OFFICE);
				if (!StringUtil.isEmpty(OUT_OFFICE)) {
					office_id = OUT_OFFICE;
				} else {
					office_id = ZjCommonUtils.getGonghaoInfoByOrderId(notNeedReqStrOrderId).getDept_id();
				}
			}
		}
		
		return office_id;
	}

	public void setOffice_id(String office_id) {
		this.office_id = office_id;
	}

	public String getOperator_id() {
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(this.notNeedReqStrOrderId);
		
		if(orderTree!=null && "1".equals(orderTree.getOrderExtBusiRequest().getIs_work_custom())){
			String deal_method = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
					"order_deal_method");
			
			//自定义流程
			if("2".equals(deal_method)){
				//线下方式受理业务，先取传入的操作员
				this.operator_id = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
						AttrConsts.OUT_OPERATOR);
				
				if (StringUtil.isEmpty(this.operator_id)) {
					//操作员没传的取配置的操作员
					this.operator_id = ZjCommonUtils.getGonghaoInfoByOrderId(notNeedReqStrOrderId).getUser_code();
				}
			}else{
				//线上方式直接取配置的操作员
				this.operator_id = ZjCommonUtils.getGonghaoInfoByOrderId(notNeedReqStrOrderId).getUser_code();
			}
		}else{
			if (cacheUtil.getConfigInfo("FUKA_ORDER_FROM").contains(
					CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ORDER_FROM))) {
				operator_id = ZjCommonUtils.getGonghaoInfoByOrderId(notNeedReqStrOrderId).getUser_code();
			} else {
				String OUT_OPERATOR = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
						AttrConsts.OUT_OPERATOR);
				if (!StringUtil.isEmpty(OUT_OPERATOR)) {
					operator_id = OUT_OPERATOR;
				} else {
					operator_id = ZjCommonUtils.getGonghaoInfoByOrderId(notNeedReqStrOrderId).getUser_code();
				}
			}
		}
		
		return operator_id;
	}

	public void setOperator_id(String operator_id) {
		this.operator_id = operator_id;
	}

	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return "com.ztesoft.net.ecsord.params.ecaop.req.WhiteCardInfoReq";
	}
	
	

	
	/**
	* get the value from Map
	*/
	public void fromMap(Map map) {
		setService_num((map.get("service_num").toString().equals("null")?"":(map.get("service_num").toString())));
		setRegion_id((map.get("region_id").toString().equals("null")?"":(map.get("region_id").toString())));
		setIccid((map.get("iccid").toString().equals("null")?"":(map.get("iccid").toString())));
		setOffice_id((map.get("office_id").toString().equals("null")?"":(map.get("office_id").toString())));
		setOperator_id((map.get("operator_id").toString().equals("null")?"":(map.get("operator_id").toString())));
	}
	/**
	* set the value from Map
	*/
	public Map toMap() {
		Map map = new HashMap();
		map.put("service_num",getService_num());
		map.put("region_id",getRegion_id());
		map.put("iccid",getIccid());
		map.put("office_id",getOffice_id());
		map.put("operator_id",getOperator_id());
		return map;
	}
}
