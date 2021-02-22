package com.ztesoft.net.service;

import zte.net.ecsord.params.base.req.BusiCompRequest;

import com.ztesoft.net.model.BusiDealResult;


public interface IOrdShipTacheManager {
	/**
	 * 通知总部质检稽核完成
	 * @param order_id
	 * @return
	 */
	public BusiDealResult notifyQCStatusToZB(String order_id);
	
	/**
	 * 通知WMS进行发货
	 * @param order_id
	 * @return
	 */
	public BusiDealResult notifyCheckFinishToWMS(String order_id) throws Exception ;
	
	
	/**
	 * 接收WMS出货通知
	 * @param busiCompRequest
	 * @return
	 */
	public BusiDealResult recShipFromWMS(BusiCompRequest busiCompRequest);
	
	/**
	 * 接收WMS发货结果通知
	 * @param busiCompRequest
	 * @return
	 */
	public BusiDealResult recShipFinshFromWMS(BusiCompRequest busiCompRequest);
	
	/**
	 * 发货通知给用户
	 * @param busiCompRequest
	 * @return
	 */
	public BusiDealResult smsSendNotify(String order_id);
	
	/**
	 * 订单变更通知总部(发货)
	 * @param order_id
	 * @return
	 */
	public BusiDealResult notifyShipToZB(String order_id);
	/**
	 * [爬虫]物流单号回填总商
	 * @param order_id
	 * @return
	 */
	public BusiDealResult orderUpdateLogiNoToZS(String order_id);
	
	/**
	 * 订单变更通知新商城(发货)
	 * @param order_id
	 * @return
	 */
	public BusiDealResult notifyShipToOuterShop(String order_id);
	
	/**
	 * 订单变更通知淘宝(发货)
	 * @param order_id
	 * @return
	 */
	public BusiDealResult notifyShipToTB(String order_id);
	
	/**
	 * 订单变更通天猫天机(发货)
	 * @param order_id
	 * @return
	 */
	public BusiDealResult syncOrderDeliveryToTaobaoTianji(String order_id);
	
	public BusiDealResult notifyWMSShipFinish(String order_id);
	
	public BusiDealResult send3NetSms(String order_id,String smsTemplateName);
	
	/**
	 * 发货信息回填总商（爬虫）
	 * @param order_id
	 * @return
	 */
	public BusiDealResult noticeDeliveryInfoToZS(String order_id);
	/**
	 * 获取总商物流单号（爬虫）
	 * @param order_id
	 * @return
	 */
	public BusiDealResult getZbLogiNoByCrawler(String order_id);
	
}
