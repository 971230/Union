package com.ztesoft.net.ecsord.params.ecaop.req;

import java.util.List;
import java.util.Map;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.mall.core.service.IDcPublicInfoManager;
import com.ztesoft.net.mall.core.service.impl.cache.DcPublicInfoCacheProxy;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;

public class CbssrefundFeeQryReq extends ZteRequest{

	@ZteSoftCommentAnnotationParam(name = "订单号", type = "String", isNecessary = "Y", desc = "订单系统内部订单")
	private String notNeedReqStrOrderId;
	@ZteSoftCommentAnnotationParam(name = "CBSS订单号", type = "String", isNecessary = "Y", desc = "CBSS订单号")
	private String order_id;
	@ZteSoftCommentAnnotationParam(name = "业务类型", type = "String", isNecessary = "Y", desc = "业务类型（1：开户类，2：存费送）")
	private String busi_type;
	
	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

	public String getOrder_id() {
		order_id = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderItemBusiRequests().get(0).getOrderItemExtBusiRequest().getActive_no();
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getBusi_type() {
		String goods_type = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.GOODS_TYPE);
		IDcPublicInfoManager dcPublicInfoManager = SpringContextHolder.getBean("dcPublicInfoManager");
		DcPublicInfoCacheProxy dcPublicCache = new DcPublicInfoCacheProxy(dcPublicInfoManager);
		List<Map> list = dcPublicCache.getList("DIC_CB_BUSI_TYPE");
        String pname = "";
        for(int i=0;i<list.size();i++){
        	if(StringUtil.equals(goods_type, (String)list.get(i).get("pkey"))){
        		pname = (String)list.get(i).get("pname");
        		break;
        	}
        }
		if(!StringUtil.isEmpty(pname)){
			busi_type = pname;
		}
		return busi_type;
	}

	public void setBusi_type(String busi_type) {
		this.busi_type = busi_type;
	}

	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getApiMethodName() {
		return "com.zte.unicomService.zj.broadband.cbssrefundFeeQryReq";
	}
}
