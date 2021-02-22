package com.ztesoft.net.mall.manager.impl;

import zte.net.ord.params.busi.req.InfHeadBusiRequest;

import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.mall.manager.IOrderInfHeadCtnManager;

public class OrderInfHeadCtnManager extends BaseSupport implements IOrderInfHeadCtnManager {

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

}
