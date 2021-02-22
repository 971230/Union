package com.ztesoft.net.service;

import java.util.List;

import zte.net.ecsord.params.base.req.BusiCompRequest;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.net.model.BusiDealResult;

public interface IOrdPickingTacheManager {
	/**
	 * 同步订单信息到WMS
	 * @param order_id
	 * @return
	 */
	public BusiDealResult synOrdInfToWMS(String order_id);
	
	/**
	 * 获取终端串号
	 * @param order_id
	 * @return
	 */
	public BusiDealResult recProdInfFromWMS(BusiCompRequest busiCompRequest) throws ApiBusiException;
	
	/**
	 * 货品信息通知总部，拣货完成通知总部
	 * @param order_id
	 * @return
	 */
	public BusiDealResult notifyProdInfToZB(String order_id);
	
	/**
	 * 同步备注信息到淘宝
	 * @param order_id
	 * @return
	 */
	public BusiDealResult synOrdToTBMall(String order_id);
	
	/**
	 * 终端变更（预占、查询）操作（对当前串号）
	 * @param order_id
	 * @return
	 * @throws ApiBusiException 
	 */
	public BusiDealResult synQueryTmResourceInfo(String order_id,String occupiedFlag) throws ApiBusiException;
	
	/**
	 * 终端变更释放操作（对当前串号）
	 * @param order_id
	 * @param occupiedFlag
	 * @return
	 * @throws ApiBusiException 
	 */
	public BusiDealResult releaseResource(String order_id,String occupiedFlag) throws ApiBusiException;
	
	/**
	 * 拣货换终端时的释放操作（对旧串号）
	 * @param order_id
	 * @param occupiedFlag
	 * @return
	 * @throws ApiBusiException 
	 */
	public BusiDealResult releaseResourceForOldTerminal(String order_id,String occupiedFlag) throws ApiBusiException;/**
	 * 批量预占终端
	 * @param order_id
	 * @param occupiedFlag
	 * @return
	 * @throws ApiBusiException
	 */
	public BusiDealResult termiResourcePreOccBatchAop(String order_id,String occupiedFlag) throws ApiBusiException ;
	/**
	 * 批量释放
	 * @param order_id
	 * @param occupiedFlag
	 * @return
	 * @throws ApiBusiException 
	 */
	public BusiDealResult releaseResourceBatch(String order_id,String occupiedFlag,List<String> releaseCodes) throws ApiBusiException;
	
}
