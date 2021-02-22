package com.ztesoft.net.service;

import java.util.List;

import zte.net.ecsord.params.base.req.BusiCompRequest;
import zte.net.ecsord.params.base.resp.BusiCompResponse;
import zte.net.ecsord.params.wyg.req.StatuSynchReq;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.net.model.BusiDealResult;



public interface IOrdWriteCardTacheManager {
	
	/**
	 * 接收WMS 写卡机编号
	 * @param busiCompRequest
	 * @return
	 */
	public BusiDealResult recWriteCardInf(BusiCompRequest busiCompRequest);
	
	/**
	 * 获取ICCID（调用森锐接口）
	 * @param busiCompRequest
	 * @return
	 */
	public BusiDealResult getICCIDInf(String order_id);
	
	/**
	 * 获取总部写卡数据
	 * @param order_id
	 * @return
	 */
	public BusiDealResult getWriteCardInfFromZB(String order_id);
	
	/**
	 * 通知信息到新商城
	 * @param statuSyn
	 * @return
	 */
	public BusiDealResult notifyWYG(StatuSynchReq statuSyn);
	
	/**
	 * 同步写卡数据到森锐
	 * @param order_id
	 * @return
	 */
	public BusiDealResult synWriteCardInfToSR(String order_id);
	
	/**
	 * 通知总部写卡结果
	 * @param order_id
	 * @return
	 */
	public BusiDealResult synWriteCardInfToZB(String order_id);
	
	/**
	 * 写卡完成通知WMS
	 * @param order_id
	 * @return
	 */
	public BusiDealResult synWriteCardInfoToWMS(String order_id,String status);
	
	/**
	 * 通知WMS退卡
	 * @param order_id
	 * @return
	 */
	public BusiDealResult refundCardToWMS(String order_id);
	
	/**
	 * 通知森锐退卡、回收卡
	 * @param order_id
	 * @return
	 */
	public BusiDealResult refundCardToSR(String order_id);
	
	/**
	 * 通过AOP获取写卡数据
	 * @param order_id
	 * @return
	 * @throws Exception 
	 */
	public BusiDealResult queryCardDataFromAop(String order_id) throws ApiBusiException;
	
	/**
	 * 通过AOP同步开户卡数据
	 * @param order_id
	 * @return
	 * @throws ApiBusiException 
	 * @throws Exception 
	 */
	public BusiDealResult syncCardDataToAop(String order_id) throws ApiBusiException;
	
	/**
	 * 通知总部写卡结果(AOP)
	 * @param order_id
	 * @return
	 * @throws Exception 
	 */
	public BusiDealResult noticeWriteCardResultToAop(String order_id) throws ApiBusiException;
	
	/**
	 * 通知总部写卡结果(AOP)ZJ
	 * @param order_id
	 * @return
	 * @throws Exception 
	 */
	public BusiDealResult noticeWriteCardResultToAopZJ(String order_id) throws ApiBusiException;
	/**
	 * 从BSS获取写卡数据
	 * @param order_id
	 * @return
	 * @throws ApiBusiException
	 */
	public BusiDealResult getCardDataFromBSS(String order_id) throws ApiBusiException;
		
	/**
	 * 通知总部写卡结果(BSS)
	 * @param order_id
	 * @return
	 * @throws Exception 
	 */
	public BusiDealResult noticeWriteCardResultToBSS(String order_id) throws ApiBusiException;

	/**
	 * 写卡数据同步给BSS
	 * @param order_id
	 * @return
	 * @throws ApiBusiException
	 */
	public BusiDealResult writeCardDataSync2BSS(String order_id) throws ApiBusiException;
	
	/**
	 * 处理料箱队列
	 * @return
	 * @throws ApiBusiException
	 */
	public BusiDealResult dealMaterielBox(String work_station) throws ApiBusiException;
	
	/**
	 * 多写卡修改状态
	 * @param busiCompRequest
	 * @return
	 */
	public BusiDealResult recWriteCardInfNew(BusiCompRequest busiCompRequest);
	
	/**
	 * 踢出自动化生产线
	 * @param orderId
	 * @return
	 */
	public void shotOffAutoProduction(String order_id);
	
	/**
	 * 同步写卡数据到森锐(非流水线，批量写卡)
	 * @param order_id
	 * @return
	 */
	public BusiDealResult synWriteCardInfToSRRG(String order_id);
	
	/**
	 * 通知森锐退卡、回收卡(非流水线，批量写卡)
	 * @param order_id
	 * @return
	 */
	public BusiDealResult refundCardToSRRG(String order_id);

	public BusiDealResult getBlankCardInf(String order_id)  throws ApiBusiException;

	public BusiDealResult SynWriteCardResults(String order_id) throws ApiBusiException;
	
	/**
	 * [爬虫]ICCID回填到总商
	 * @param order_id
	 * @return
	 * @throws ApiBusiException
	 */
	public BusiDealResult synIccidToZS(String order_id) throws ApiBusiException;
	/**
	 * [爬虫]调总商获取写卡数据
	 * @param order_id
	 * @return
	 * @throws ApiBusiException
	 */
	public BusiDealResult getCardDataFromZS(String order_id) throws ApiBusiException;
	/**
	 * [爬虫]写卡完成提交订单(写卡环节)
	 * @param order_id
	 * @return
	 */
	public BusiDealResult writeCardFinishSubmitOrderToZS(String order_id);
	/**
	 * [爬虫]写卡确认处理完毕(写卡环节)
	 * @param order_id
	 * @return
	 */
	public BusiDealResult writeCardConfirmDealFinishToZS(String order_id);
	
	/**
	 * [AOP]卡数据同步CBSS(写卡环节)
	 * @param order_id
	 * @return
	 */
	public BusiDealResult syncCardDataToCBss(String order_id) throws ApiBusiException;
	
	/**
	 * [AOP]卡数据同步CBSS(写卡环节)zj
	 * @param order_id
	 * @return
	 */
	public BusiDealResult syncCardDataToCBssZJ(String order_id) throws ApiBusiException;

	/**
	 * 异步通过AOP同步开户卡数据
	 * @param order_id
	 * @return
	 * @throws ApiBusiException 
	 * @throws Exception 
	 */
	public BusiDealResult syncCardDataToAopNew(String order_id) throws ApiBusiException;

	/**
	 * [BSS]卡数据同步(写卡环节)
	 * @param order_id
	 * @return
	 */
	BusiDealResult CardDataSyncBss(String order_id) throws ApiBusiException;
	/**
	 * 获取森锐自动写卡队列
	 * @param orderId
	 * @return
	 */
	public List getAutoOrderList(String orderId);
	/**
	 * PC自动写卡
	 * @param orderId
	 * @return
	 */
	public BusiDealResult autoWriteCard(String orderId);
	/**
	 * PC自动写卡写入写卡 队列
	 * @param orderId
	 * @return
	 */
	public BusiDealResult insertWriteCardQueueByPc(String orderId);
	/**
	 * 爬虫写卡完成
	 * @param busiCompRequest
	 * @return
	 * @throws ApiBusiException
	 */
	public BusiDealResult  crawlerWriteCardSuc(String orderId);
}
