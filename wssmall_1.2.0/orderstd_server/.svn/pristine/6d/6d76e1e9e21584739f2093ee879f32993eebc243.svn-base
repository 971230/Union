package com.ztesoft.net.mall.core.manager.impl;

import zte.net.ord.params.busi.req.InfHeadBusiRequest;

import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.mall.core.manager.IOrderInfHeadStdManager;

public class OrderInfHeadStdManager extends BaseSupport implements IOrderInfHeadStdManager {

	@Override
	public InfHeadBusiRequest saveInfHead(InfHeadBusiRequest infHead) throws Exception {
		this.baseDaoSupport.insert("es_inf_head", infHead);
		return infHead;
	}

	@Override
	public InfHeadBusiRequest saveInfHeadHis(InfHeadBusiRequest infHeadHis) throws Exception {
		this.baseDaoSupport.insert("es_inf_head_his", infHeadHis);
		return infHeadHis;
	}
	
	@Override
	public InfHeadBusiRequest getInfHead(String orderId,String coId) throws Exception {
		return null;
	}
	
	@Override
	public InfHeadBusiRequest getInfHeadByCoId(String coId) throws Exception {
		return null;
	}
	
	@Override
	public InfHeadBusiRequest getInfHeadByOrderId(String orderId) throws Exception {
		return null;
	}


	

}
