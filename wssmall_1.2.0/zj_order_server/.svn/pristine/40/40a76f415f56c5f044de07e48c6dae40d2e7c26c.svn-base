package com.ztesoft.net.ecsord.params.ecaop.req;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.mall.core.service.IDcPublicInfoManager;
import com.ztesoft.net.mall.core.service.impl.cache.DcPublicInfoCacheProxy;
import com.ztesoft.net.service.IOrderInfManager;
import com.ztesoft.net.util.ZjCommonUtils;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.busi.req.OrderAdslBusiRequest;

public class BusinessFeeQryReq extends ZteRequest {

	@ZteSoftCommentAnnotationParam(name = "订单号", type = "String", isNecessary = "Y", desc = "订单系统内部订单")
	private String notNeedReqStrOrderId;
	@ZteSoftCommentAnnotationParam(name = "地市编码", type = "String", isNecessary = "Y", desc = "地市编码")
	private String region_id;
	@ZteSoftCommentAnnotationParam(name = "业务编码", type = "String", isNecessary = "Y", desc = "业务编码")
	private String busi_id;
	@ZteSoftCommentAnnotationParam(name = "用户业务类型", type = "String", isNecessary = "Y", desc = "用户业务类型")
	private String service_type;
	@ZteSoftCommentAnnotationParam(name = "业务参数", type = "String", isNecessary = "Y", desc = "业务参数")
	private String param_value; 
	@ZteSoftCommentAnnotationParam(name = "办理操作员", type = "String", isNecessary = "Y", desc = "办理操作员")
	private String operator_id;
	@ZteSoftCommentAnnotationParam(name = "办理操作点", type = "String", isNecessary = "Y", desc = "办理操作点")
	private String office_id;
	

	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

	public String getRegion_id() {
		region_id = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderExtBusiRequest()
				.getOrder_city_code();
		IDcPublicInfoManager dcPublicInfoManager = SpringContextHolder.getBean("dcPublicInfoManager");
		DcPublicInfoCacheProxy dcPublicCache = new DcPublicInfoCacheProxy(dcPublicInfoManager);
		List<Map> list = dcPublicCache.getList("BSS_ACCOUNT_OPERATOR");
        for(int i=0;i<list.size();i++){
        	if(StringUtil.equals(region_id, (String)list.get(i).get("pkey"))){
        		region_id = (String)list.get(i).get("pname");
        		break;
        	}
        }
        region_id = region_id.replace("市", "");
        list = dcPublicCache.getList("uni_area_id");
        for(int i=0;i<list.size();i++){
        	if(StringUtil.equals(region_id, (String)list.get(i).get("pname"))){
        		region_id = (String)list.get(i).get("pkey");
        		break;
        	}
        }
		return region_id;
	}

	public void setRegion_id(String region_id) {
		this.region_id = region_id;
	}

	public String getBusi_id() {
		busi_id = "900001";//固网新装
		
		return busi_id;
	}

	public void setBusi_id(String busi_id) {
		this.busi_id = busi_id;
	}

	public String getService_type() {
//		service_type = "6130";//沃TV
//		List<OrderAdslBusiRequest> list = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderAdslBusiRequest();
//		if(list != null && list.size() > 0){
//			OrderAdslBusiRequest orderAdslBusiRequest = list.get(0);
//			service_type = "".equals(orderAdslBusiRequest.getService_type())||orderAdslBusiRequest.getService_type()==null?"6130":orderAdslBusiRequest.getService_type();
//		}

		return service_type;
	}

	public void setService_type(String service_type) {
		this.service_type = service_type;
	}

	@SuppressWarnings("rawtypes")
	public String getParam_value() {
//		busi_id = "900001";
		
		
		if("900001".equals(busi_id)){
			// "{scheme_id *} {county_id AYH} {user_knd 11} {low_rate *} {group_scheme_id 764050}";
			// “*”号表示全部匹配，字段缺省值默认填“*”
			String scheme_id = "*";// scheme_id:宽带活动id
			String county_id = "*";// county_id:县分
			String user_knd = "*";// user_knd:用户类型
			String low_rate = "*";// low_rate:速率
			String group_scheme_id = "*";// group_scheme_id:融合活动id
			
			/**
			 * Add by wcl
			 * 营业费用查询param_value修改
			 */
			String sale_mode = "*"; //销售模式  01：免费租用 06：用户自购 07：用户自备用户自备
			String devic_gear = "*"; //新设备档位    00：标准版(光猫) 01：加强版(光猫)

			List<OrderAdslBusiRequest> list = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderAdslBusiRequest();
			if(list != null && list.size()>0){
				OrderAdslBusiRequest orderAdslBusiRequest = list.get(0);
				String OUT_OPERATOR = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.OUT_OPERATOR);
				if(!StringUtil.isEmpty(OUT_OPERATOR)){
					county_id = "".equals(orderAdslBusiRequest.getCounty_id())||orderAdslBusiRequest.getCounty_id()==null?"*":orderAdslBusiRequest.getCounty_id();
				}else{
					county_id = "".equals(ZjCommonUtils.getGonghaoInfoByOrderId(notNeedReqStrOrderId).getCounty_id())||ZjCommonUtils.getGonghaoInfoByOrderId(notNeedReqStrOrderId).getCounty_id()==null?"*":ZjCommonUtils.getGonghaoInfoByOrderId(notNeedReqStrOrderId).getCounty_id();
				}
				
				user_knd = "".equals(orderAdslBusiRequest.getUser_kind())||orderAdslBusiRequest.getUser_kind()==null?"*":orderAdslBusiRequest.getUser_kind();
				low_rate = "".equals(orderAdslBusiRequest.getAdsl_speed())||orderAdslBusiRequest.getAdsl_speed()==null?"*":orderAdslBusiRequest.getAdsl_speed();
				sale_mode = "".equals(orderAdslBusiRequest.getSale_mode())||orderAdslBusiRequest.getSale_mode()==null?"*":orderAdslBusiRequest.getSale_mode();
				devic_gear = "".equals(orderAdslBusiRequest.getDevic_gear())||orderAdslBusiRequest.getDevic_gear()==null?"*":orderAdslBusiRequest.getDevic_gear();
			}
			IOrderInfManager orderInfManager = SpringContextHolder.getBean("orderInfManager");
			Map map = orderInfManager.qryGoodsDtl(notNeedReqStrOrderId);
			
			group_scheme_id = com.ztesoft.ibss.common.util.Const.getStrValue(map, "p_code")+"";//产品编码
			
			param_value = "{scheme_id " + scheme_id + "} {county_id " + county_id + "} {user_knd " + user_knd
					+ "} {low_rate " + low_rate + "} {group_scheme_id " + group_scheme_id + "} {sale_mode "+sale_mode+"} {devic_gear "+devic_gear+"}";
		}
		return param_value;
	}

	public void setParam_value(String param_value) {
		this.param_value = param_value;
	}

	public String getOperator_id() {
		String source_from = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderExtBusiRequest()
				.getOrder_from();
		String OUT_OPERATOR = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
				AttrConsts.OUT_OPERATOR);
		if (!StringUtils.isEmpty(source_from) && StringUtil.equals(source_from, "10071")
				&& !StringUtil.isEmpty(OUT_OPERATOR)) {
			operator_id = OUT_OPERATOR;
		} else {
			operator_id = ZjCommonUtils.getGonghaoInfoByOrderId(notNeedReqStrOrderId).getUser_code();
		}
		return operator_id;
	}

	public void setOperator_id(String operator_id) {
		this.operator_id = operator_id;
	}

	public String getOffice_id() {
		String source_from = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderExtBusiRequest()
				.getOrder_from();
		String OUT_OFFICE = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
				AttrConsts.OUT_OFFICE);
		if (!StringUtils.isEmpty(source_from) && StringUtil.equals(source_from, "10071")
				&& !StringUtil.isEmpty(OUT_OFFICE)) {
			office_id = OUT_OFFICE;
		} else {
			office_id = ZjCommonUtils.getGonghaoInfoByOrderId(notNeedReqStrOrderId).getDept_id();
		}
		return office_id;
	}

	public void setOffice_id(String office_id) {
		this.office_id = office_id;
	}

	@Override
	public void check() throws ApiRuleException {
	}

	@Override
	public String getApiMethodName() {
		return "com.zte.unicomService.zj.broadband.businessFeeQry";
	}
}
