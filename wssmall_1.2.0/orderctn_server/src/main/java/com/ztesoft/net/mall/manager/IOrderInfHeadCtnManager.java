package com.ztesoft.net.mall.manager;

import zte.net.ord.params.busi.req.InfHeadBusiRequest;


/**
 * 
 * @Package com.ztesoft.net.mall.manager
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhouqiangang 
 * @date 2015年11月18日 下午5:19:21
 */
public interface IOrderInfHeadCtnManager {

	public InfHeadBusiRequest saveInfHead(InfHeadBusiRequest infHead) throws Exception ;
	
	
	public InfHeadBusiRequest saveInfHeadHis(InfHeadBusiRequest infHeadHis) throws Exception ;
}
