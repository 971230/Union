package com.ztesoft.net.service;

import java.util.List;

import zte.net.ecsord.params.busi.req.OrderPhoneInfoFukaBusiRequest;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.net.model.BusiDealResult;

public interface IOrdCollectManagerManager {
	
	/**
	 * 同步订单信息到总部
	 * @param order_id
	 * @return
	 */
	public BusiDealResult synOrdInfoToZB(String order_id);
	
	/**
	 * 主卡号码状态变更aop
	 * @param order_id
	 * @param occupiedFlag
	 * @return
	 */
	public BusiDealResult numberStatesChangeAop(String order_id,String occupiedFlag,String proKey)throws ApiBusiException;

	/**
	 * 主卡号码状态变更bss
	 * @param order_id
	 * @param occupiedFlag
	 * @return
	 */
	public BusiDealResult numberStatesChangeBss(String order_id,String occupiedFlag)throws ApiBusiException;
	
	/**
	 * 副卡批量变更（预占、预定）aop
	 * @param order_id
	 * @param occupiedFlag
	 * @param phonelist 指定副卡列表
	 * @return
	 * @throws ApiBusiException
	 */
	public BusiDealResult numberChangeBatchAopFuKa(String order_id, String occupiedFlag,List<OrderPhoneInfoFukaBusiRequest> phonelist) throws ApiBusiException;

	/**
	 * 副卡批量释放aop
	 * @param order_id
	 * @param occupiedFlag 操作码（释放））
	 * @param phonelist 待释放的副卡列表
	 * @return
	 */
	public BusiDealResult numberReleaseBatchAopFuKa(String order_id, String occupiedFlag,List<OrderPhoneInfoFukaBusiRequest> phonelist)throws ApiBusiException;
	/**
	 * 副卡号卡变更（预占、释放）bss
	 * @param order_id
	 * @param phoneNum 号码
	 * @param occupiedFlag 操作码（预占、预定）
	 * @return
	 * @throws ApiBusiException
	 */
	public BusiDealResult numberStatesChangeSingleBssFuka(String order_id, OrderPhoneInfoFukaBusiRequest selvo,String occupiedFlag) throws ApiBusiException;
	
	/**
	 * 按环境标识过滤是否属于解藕环境的订单
	 * @param order_id
	 * @return
	 */
	public void setOrderHide(String order_id);
	
	/**
	 * 隐藏订单
	 * @param order_id
	 * @param flag
	 * @param reason
	 */
	public void setOrderHide(String order_id, String flag, String reason);
	
	/**
	 * 
	 * 浙江省份号码预占
	 */
	public BusiDealResult numberPreOccupyZjBss(String order_id,String occupiedFlag) throws ApiBusiException;
	
	
	/**
	 * 订单是否隐藏
	 * @param order_id
	 * @return
	 */
	public boolean orderISHide(String order_id);
	
	/**
	 * 取消隐藏
	 * @param order_id
	 */
	public void cancelOrderHide(String order_id);
	   /**
     * 移网号码状态变更bss
     * @param order_id
     * @param occupiedFlag
     * @return
     */
    public BusiDealResult numberBssPreempted(String order_id)throws ApiBusiException;
    
}
