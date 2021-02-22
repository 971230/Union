package com.ztesoft.net.service;

import java.util.Map;

import com.ztesoft.net.framework.database.Page;

public interface IOrderEcsManager {
	
	/**
	 * 查询订单信息
	 * @param pageNo
	 * @param pageSize
	 * @param terminal_name
	 * @return
	 */
	public Page getOrderList(Map map);
	
}
