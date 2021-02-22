package com.ztesoft.net.service;

import com.ztesoft.net.model.BusiDealResult;

public interface IOrdQualityTacheManager {
	/**
	 * 物流处理完成签收
	 * @param order_id
	 * @return
	 */
	public BusiDealResult wlFinishSignIn(String order_id) throws Exception;
	
	/**
	 * 通知WMS质检稽核
	 * @param order_id
	 * @return
	 */
	public BusiDealResult notifyWMSBeginQualityCheck(String order_id)throws Exception;
	
	

	
	/**
	 * Bss对账信息同步
	 * @param order_id
	 * @return
	 */
	public BusiDealResult generateBssBillInfo(String order_id);
	
}
