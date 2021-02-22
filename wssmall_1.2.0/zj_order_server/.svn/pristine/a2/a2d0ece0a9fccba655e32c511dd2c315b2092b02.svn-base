package com.ztesoft.net.ecsord.params.ecaop.req;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.service.IOrderInfManager;

import params.ZteRequest;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.busi.req.OrderPhoneInfoBusiRequest;

public class SchemeFeeQryReq extends ZteRequest{

	@ZteSoftCommentAnnotationParam(name = "订单号", type = "String", isNecessary = "Y", desc = "订单系统内部订单")
	private String notNeedReqStrOrderId;
	@ZteSoftCommentAnnotationParam(name = "活动编码", type = "String", isNecessary = "Y", desc = "活动编码")
	private String scheme_id;
	@ZteSoftCommentAnnotationParam(name = "是否靓号活动", type = "String", isNecessary = "Y", desc = "是否靓号活动,1是0否,可空")
	private String is_lhscheme;
	@ZteSoftCommentAnnotationParam(name = "融合活动业务类型", type = "String", isNecessary = "Y", desc = "融合活动业务类型")
	private String service_type;
	
	
	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

	public String getScheme_id() {
		IOrderInfManager orderInfManager = SpringContextHolder.getBean("orderInfManager");
		Map map = orderInfManager.qryGoodsDtl(notNeedReqStrOrderId);
		scheme_id = com.ztesoft.ibss.common.util.Const.getStrValue(map, "p_code")+"";
		return scheme_id;
	}

	public void setScheme_id(String scheme_id) {
		this.scheme_id = scheme_id;
	}

	public String getIs_lhscheme() {
		OrderPhoneInfoBusiRequest phoneInfo =CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getPhoneInfoBusiRequest();
		String classid = phoneInfo.getClassId()+"";
		if(!StringUtils.isEmpty(classid)&&StringUtils.equals(classid, "9")){
			is_lhscheme ="1";
		}else{
			is_lhscheme = "0";
		}
		return is_lhscheme;
	}

	public void setIs_lhscheme(String is_lhscheme) {
		this.is_lhscheme = is_lhscheme;
	}

	public String getService_type() {
		service_type = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, "line_type");
		return service_type;
	}

	public void setService_type(String service_type) {
		this.service_type = service_type;
	}

	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getApiMethodName() {
		return "com.zte.unicomService.zj.broadband.schemeFeeQry";
	}
}
