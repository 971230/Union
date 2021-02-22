package com.ztesoft.net.service;


import com.ztesoft.net.mall.core.model.DeliveryPrints;


public interface IDeliveryPrintsManager {

	
	/**
	 * 根据主键获取记录
	 * @param delvery_print_id
	 * @return
	 */
	public DeliveryPrints get(String delvery_print_id);
		
}
