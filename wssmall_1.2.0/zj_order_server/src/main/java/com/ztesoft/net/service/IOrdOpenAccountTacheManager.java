package com.ztesoft.net.service;

import zte.net.ecsord.params.base.req.BusiCompRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.net.model.BusiDealResult;

public interface IOrdOpenAccountTacheManager {
	/**
	 * 总部开户结果接收
	 * 
	 * @param busiCompRequest
	 * @return
	 */
	public BusiDealResult recOpenAcctResult(BusiCompRequest busiCompRequest);

	/**
	 * 通知WMS开户结果
	 * 
	 * @param order_id
	 * @return
	 */
	public BusiDealResult notifyOpenAcctToWMS(String order_id);

	/**
	 * AOP开户结果接收
	 * 
	 * @param busiCompRequest
	 * @return
	 */
	public BusiDealResult openApplyToAop(String order_id) throws ApiBusiException;

	/**
	 * 通过AOP开户处理提交
	 * 
	 * @param order_id
	 * @return
	 * @throws ApiBusiException
	 * @throws Exception
	 */
	public BusiDealResult openDealSubmitToAop(String order_id) throws ApiBusiException;

	/**
	 * (AOP)套餐变更申请
	 * 
	 * @param order_id
	 * @return
	 * @throws ApiBusiException
	 * @throws Exception
	 */
	public BusiDealResult productChangeApply(String order_id) throws ApiBusiException;

	/**
	 * (AOP)套餐变更提交
	 * 
	 * @param order_id
	 * @return
	 * @throws ApiBusiException
	 * @throws Exception
	 */
	public BusiDealResult productChangeSubmit(String order_id) throws ApiBusiException;

	/**
	 * [AOP]老用户优惠购机处理申请
	 * 
	 * @param order_id
	 * @return
	 * @throws ApiBusiException
	 */
	public BusiDealResult oldActivityMobileApply(String order_id) throws ApiBusiException;

	/**
	 * [AOP]老用户优惠购机处理申请
	 * 
	 * @param order_id
	 * @return
	 * @throws ApiBusiException
	 */
	public BusiDealResult oldActivityMobileSubmit(String order_id) throws ApiBusiException;

	/**
	 * (AOP)23转4申请
	 * 
	 * @param order_id
	 * @return
	 * @throws ApiBusiException
	 * @throws Exception
	 */
	public BusiDealResult openAccApply23to4(String order_id) throws ApiBusiException;

	/**
	 * (AOP)23转4提交
	 * 
	 * @param order_id
	 * @return
	 * @throws ApiBusiException
	 * @throws Exception
	 */
	public BusiDealResult openAccSubmit23to4(String order_id) throws ApiBusiException;

	/**
	 * (AOP)退机申请
	 * 
	 * @param order_id
	 * @return
	 * @throws ApiBusiException
	 * @throws Exception
	 */
	public BusiDealResult returnMachineApply(String order_id) throws ApiBusiException;

	/**
	 * (AOP)退机提交
	 * 
	 * @param order_id
	 * @return
	 * @throws ApiBusiException
	 * @throws Exception
	 */
	public BusiDealResult returnMachineSubmit(String order_id) throws ApiBusiException;

	/**
	 * 4G老用户存费送费业务
	 * 
	 * @param order_id
	 * @return
	 * @throws ApiBusiException
	 * @throws Exception
	 */
	public BusiDealResult cunFeeSongFee4GAop(String order_id) throws ApiBusiException;

	/**
	 * 4G老用户存费送费正式提交
	 * 
	 * @param order_id
	 * @return
	 * @throws ApiBusiException
	 */
	public BusiDealResult cunFeeSongFee4GSubmitAop(String order_id) throws ApiBusiException;

	/**
	 * BSS开户结果接收
	 * 
	 * @param busiCompRequest
	 * @return
	 */
	public BusiDealResult openApplyToBSS(String order_id) throws ApiBusiException;

	/**
	 * 通过BSS开户处理提交
	 * 
	 * @param order_id
	 * @return
	 * @throws ApiBusiException
	 * @throws Exception
	 */
	public BusiDealResult openDealSubmitToBSS(String order_id) throws ApiBusiException;

	/**
	 * 老用户续约申请（AOP）
	 * 
	 * @param order_id
	 * @return
	 * @throws ApiBusiException
	 * @throws Exception
	 */
	public BusiDealResult oldRenActivityApply(String order_id) throws ApiBusiException;

	/**
	 * 老用户续约提交（AOP）
	 * 
	 * @param order_id
	 * @return
	 * @throws ApiBusiException
	 * @throws Exception
	 */
	public BusiDealResult oldRenActivitySubmit(String order_id) throws ApiBusiException;

	/**
	 * 更新套餐业务包的办理状态
	 * 
	 * @param order_id
	 * @param status
	 * @return
	 */
	public String modifyAttrPackageStatus(String order_id, String status);

	/**
	 * BSS开户正式提交
	 * 
	 * @param order_id
	 * @return
	 * @throws ApiBusiException
	 * @throws Exception
	 */
	public BusiDealResult subAccountOfficial(String order_id) throws ApiBusiException;

	/**
	 * 开户预提交（BSS）
	 * 
	 * @param order_id
	 * @return
	 * @throws ApiBusiException
	 * @throws Exception
	 */
	public BusiDealResult bssPreCommit(String order_id) throws ApiBusiException;
	
	/**
	 * 回填配送信息到总商（爬虫）
	 * 
	 * @param order_id
	 * @return
	 * @throws ApiBusiException
	 * @throws Exception
	 */
	public BusiDealResult modifyDeliveryInfoToZb(String order_id) throws ApiBusiException;

	/**
	 * 回填商品信息到总商（爬虫）
	 * 
	 * @param order_id
	 * @return
	 * @throws ApiBusiException
	 * @throws Exception
	 */
	public BusiDealResult modifyGoodsInfoToZb(String order_id) throws ApiBusiException;

	/**
	 * 总商订单二次分配（爬虫）
	 * 
	 * @param order_id
	 * @return
	 * @throws ApiBusiException
	 * @throws Exception
	 */
	public BusiDealResult zbReAllotOrder(String order_id) throws ApiBusiException;

	// /**
	// * 总商进入开户详情页（爬虫）
	// * @param order_id
	// * @return
	// * @throws ApiBusiException
	// * @throws Exception
	// */
	// public BusiDealResult zbEnterAccountDetailPage(String order_id) throws
	// ApiBusiException;

	/**
	 * 模拟总商开会校验（爬虫）
	 * 
	 * @param order_id
	 * @return
	 * @throws ApiBusiException
	 * @throws Exception
	 */
	public BusiDealResult openAccountValidateZb(String order_id) throws ApiBusiException;

	/**
	 * PC批量写卡队列插入
	 */
	public BusiDealResult writeCardSetQueueByPc(String order_id, String queue_code) throws ApiBusiException;

	/**
	 * [宽带用户]宽带受理费用查询
	 * 
	 * @param order_id
	 * @return
	 * @throws ApiBusiException
	 */
	public BusiDealResult openAccountBroadbandFee(String order_id) throws ApiBusiException;

	/**
	 * [宽带用户]开户预受理申请
	 * 
	 * @param order_id
	 * @return
	 * @throws ApiBusiException
	 */
	public BusiDealResult openAccountApplyBroadband(String order_id) throws ApiBusiException;

	/**
	 * [宽带用户]正式开户提交
	 * 
	 * @param order_id
	 * @return
	 * @throws ApiBusiException
	 */
	public BusiDealResult openAccountSubmitBroadband(String order_id) throws ApiBusiException;

	/**
	 * [宽带用户]开户状态同步码上购系统
	 * 
	 * @param order_id
	 * @return
	 * @throws ApiBusiException
	 */
	public BusiDealResult accountStatusSynToO2M(String order_id) throws ApiBusiException;

	/**
	 * [宽带用户]接收省分BSS施工结果
	 * 
	 * @param order_id
	 * @return
	 * @throws ApiBusiException
	 */
	public BusiDealResult receiveConstructionStatus(BusiCompRequest busiCompRequest) throws ApiBusiException;

	/**
	 * [宽带用户]BSS施工结果同步码上购
	 * 
	 * @param order_id
	 * @return
	 * @throws ApiBusiException
	 */
	public BusiDealResult constructionStatusSynToO2M(String order_id) throws ApiBusiException;

	/**
	 * AOP号卡开户预提交
	 * 
	 * @param busiCompRequest
	 * @return
	 */
	public BusiDealResult openAccountPreCommitApplyAop(String order_id) throws ApiBusiException;

	/**
	 * BSS号卡开户预提交
	 * 
	 * @param busiCompRequest
	 * @return
	 */
	public BusiDealResult openAccountPreCommitApplyBss(String order_id) throws ApiBusiException;

	/**
	 * AOP号卡开户正式提交
	 * 
	 * @param busiCompRequest
	 * @return
	 */
	public BusiDealResult openAccountFormalCommitApplyAop(String order_id) throws ApiBusiException;

	/**
	 * AOP 卡信息获取
	 * 
	 * @param busiCompRequest
	 * @return
	 */
	public BusiDealResult cardInfoGet(String order_id) throws ApiBusiException;

	/**
	 * BSS 卡信息获取
	 * 
	 * @param busiCompRequest
	 * @return
	 */
	public BusiDealResult cardInfoGetBss(String order_id) throws ApiBusiException;

	/**
	 * AOP 写卡结果通知
	 * 
	 * @param busiCompRequest
	 * @return
	 */
	public BusiDealResult writeCardResult(String order_id) throws ApiBusiException;

	/**
	 * BSS 号卡开户正式提交
	 * 
	 * @param busiCompRequest
	 * @return
	 */
	BusiDealResult OrderFormalSub(String order_id) throws ApiBusiException;

	// 沃TV绑定
	public BusiDealResult wotvBroadbandBind(String order_id) throws ApiBusiException;

	public BusiDealResult openAccountPreCommitApplyAopZJ(String order_id) throws ApiBusiException;

	public BusiDealResult openAccountFormalCommitApplyAopZJ(String order_id) throws ApiBusiException;

	public BusiDealResult cardInfoGetZJ(String order_id) throws ApiBusiException;

	public BusiDealResult cunFeeSongFee4GAopZJ(String order_id) throws ApiBusiException;
	
	public BusiDealResult electronicVolumeSend(String order_id)throws ApiBusiException;

	/**
	 * 爬虫总商手动开户提交
	 * 
	 * @param order_id
	 * @return
	 * @throws ApiBusiException
	 */
	public BusiDealResult manualOpenAccountSubmit(String order_id) throws ApiBusiException;

	public BusiDealResult cunFeeSongFee4GSubmitAopZJ(String order_id) throws ApiBusiException;
	
	/**
	 * [AOP]宽带开户预受理申请
	 * 
	 * @param order_id
	 * @return
	 * @throws ApiBusiException
	 */
	public BusiDealResult broadBandOpenApplyAop(String order_id) throws ApiBusiException;

	/**
	 * [AOP]宽带正式开户提交
	 * 
	 * @param order_id
	 * @return
	 * @throws ApiBusiException
	 */
	public BusiDealResult broadBandOpenSubmitAop(String order_id) throws ApiBusiException;
	
	/**
	 * [AOP]号卡副卡产品变更、、、开户提交
	 * 
	 * @param order_id
	 * @return
	 * @throws ApiBusiException
	 */
	public BusiDealResult openAccountSubmit(String order_id) throws ApiBusiException;
	
	/**
	 * [AOP]老用户加入主副卡
	 * 
	 * @param order_id
	 * @return
	 * @throws ApiBusiException
	 */
	public BusiDealResult joinMVCard(String order_id) throws ApiBusiException;
	
	

	
	
	public BusiDealResult kuKaPreOpenBSS(String order_id) throws ApiBusiException;

	public BusiDealResult changeAppPre(String order_id) throws ApiBusiException;
	
	public BusiDealResult changeSub(String order_id) throws ApiBusiException;
	
	public BusiDealResult mainViceOpen(String order_id, OrderTreeBusiRequest orderTree) throws ApiBusiException;
	
    public BusiDealResult openAccountMasterSubmit(String order_id) throws ApiBusiException;

    /**
	 * 固网用户更换光猫/机顶盒接口
	 */
	public BusiDealResult objectReplaceSub(String order_id) throws ApiBusiException;
}
