package com.ztesoft.net.ecsord.params.ecaop.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.util.ZjCommonUtils;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;

public class TabuserBtocbSubReq extends ZteRequest{
	
	@ZteSoftCommentAnnotationParam(name = "订单号", type = "String", isNecessary = "Y", desc = "订单系统内部订单")
	private String notNeedReqStrOrderId;
	@ZteSoftCommentAnnotationParam(name = "BSS工号", type = "String", isNecessary = "Y", desc = "BSS工号")
	private String operator_id;

	
	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

	public String getOperator_id() {
		ICacheUtil cacheUtil = (ICacheUtil) SpringContextHolder.getBean("cacheUtil");
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(this.notNeedReqStrOrderId);
		String flag = cacheUtil.getConfigInfo("WORK_CUSTOM_OPERATOR_FLAG");
		
		if(orderTree!=null && "1".equals(orderTree.getOrderExtBusiRequest().getIs_work_custom())
				&& "1".equals(flag)){
			String deal_method = CommonDataFactory.getInstance().getAttrFieldValue(this.notNeedReqStrOrderId,
					"order_deal_method");
			
			//自定义流程
			if("2".equals(deal_method)){
				String OUT_OPERATOR = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.OUT_OPERATOR);
				if(!StringUtil.isEmpty(OUT_OPERATOR)){
					operator_id = OUT_OPERATOR;
				}else{
					operator_id = ZjCommonUtils.getGonghaoInfoByOrderId(notNeedReqStrOrderId).getUser_code();
				}
			}else{
				operator_id = ZjCommonUtils.getGonghaoInfoByOrderId(notNeedReqStrOrderId).getUser_code();
			}
		}else{
			String OUT_OPERATOR = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.OUT_OPERATOR);
			if(!StringUtil.isEmpty(OUT_OPERATOR)){
				operator_id = OUT_OPERATOR;
			}else{
				operator_id = ZjCommonUtils.getGonghaoInfoByOrderId(notNeedReqStrOrderId).getUser_code();
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
		return "com.zte.unicomService.zj.broadband.tabuserBtocbSubReq";
	}
	
}
