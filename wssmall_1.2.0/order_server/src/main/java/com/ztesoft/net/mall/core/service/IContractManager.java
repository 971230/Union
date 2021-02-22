package com.ztesoft.net.mall.core.service;

import com.ztesoft.net.mall.core.action.order.OrderRequst;
import com.ztesoft.net.mall.core.action.order.OrderResult;
import com.ztesoft.net.mall.core.model.OrderChange;


/**
 * 合约机受理处理类
 * 
 * @author wui
 */
public interface IContractManager {
	
	//合约机号码调拨
	//public void transfer_contract(OrderRequst orderRequst, OrderResult orderResult) ;

	//合约机订购
	public void order(OrderRequst orderRequst, OrderResult orderResult);
	
	//合约机退订
	public void cancel(OrderRequst orderRequst, OrderResult orderResult) ;
	
	public void change(OrderRequst orderRequst, OrderResult orderResult,OrderChange orderChange);
	
	
}