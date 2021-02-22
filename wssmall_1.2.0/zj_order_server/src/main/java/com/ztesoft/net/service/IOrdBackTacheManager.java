package com.ztesoft.net.service;

import zte.net.ecsord.params.base.req.BusiCompRequest;

import com.ztesoft.net.model.BusiDealResult;


public interface IOrdBackTacheManager {
	/**
	 * 接收总部回单完成
	 * @param busiCompRequest
	 * @return
	 */
	public BusiDealResult recReceiptFromZB(BusiCompRequest busiCompRequest)throws Exception;
	
	public void insertAopQuery(String order_id);
	
	/**
	 * 激活状态回填总商【爬虫】
	 * @param order_id
	 * @return
	 */
	public BusiDealResult noticeActiveStatusToZS(String order_id);
	
	/**
	 * 接收码上购协议照片
	 * @param busiCompRequest
	 * @return
	 */
	public BusiDealResult receiveCodePurchaseAgrtImgs(BusiCompRequest busiCompRequest);

	/**
	 * 关闭工单意向单
	 * @param busiCompRequest
	 * @return
	 */
	public BusiDealResult orderBack(String orderId);
	
	/**
	 * 订单结果通知外围
	 * @param order_id
	 * @return
	 */
	public BusiDealResult orderResultNotify(String order_id);
}
