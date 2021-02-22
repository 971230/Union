package com.ztesoft.net.service;

import zte.net.ecsord.params.base.req.BusiCompRequest;

import java.util.Map;

import zte.net.ecsord.params.bss.req.PreCommitReq;
import zte.net.ecsord.params.sf.resp.NotifyOrderInfoSFResponse;
import zte.net.ecsord.params.zb.req.StateUtil;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.net.model.BusiDealResult;
import com.ztesoft.net.model.RouteInfoVO;

public interface IOrdVisitTacheManager {
	/**
	 * 身份证信息校验
	 * @param order_id
	 * @return
	 */
	public BusiDealResult certiValide(String order_id);
	/**
	 * 客户下用户数据查询接口
	 * @param order_id
	 * @return
	 */
	public BusiDealResult userDataQry(String order_id);
	/**
	 * 宋琪
	 * 订单异常通知
	 * 通知/接收异常状态，挂起或恢复订单生产
	 * @param order_id
	 * @return
	 */
	public BusiDealResult notifyOrderAbnormalToZB(BusiCompRequest busiCompRequest,String order_id);
	
	/**
	 * 12岁校验 宋琪2017年6月24日 15:41:33
	 * @param order_id
	 * @return
	 */
	public BusiDealResult ageValide(String order_id,int age);
	
	/**
	 * 业务类型校验 喻天琦2018年01月29日 10:35:33
	 * @param order_id
	 * @return
	 */
	public BusiDealResult serviceTypeCheck(String order_id);
	
	/**
	 * 用户返档接口
	 * @param order_id
	 * @return
	 */
	public BusiDealResult returnFile(String order_id);
	
	
	/**
	 * Ess开户预校验
	 * @param order_id
	 * @return
	 */
	public BusiDealResult preOpenAccountValide(String order_id);
	
	/**
	 * 同步订单变更信息到总部
	 * @param order_id
	 * @return
	 */
	public BusiDealResult synOrdChangeToZB(String order_id);
	
	/**
	 * 通知总部审单完成
	 * @param order_id
	 * @return
	 */
	public BusiDealResult notifyAuditStatusToZB(String order_id);
	
	/**
	 * 同步订单信息到物流公司
	 * @param order_id
	 * @return
	 */
	public BusiDealResult orderSynToWl(String order_id);
	
	/**
	 * 照片信息查询
	 * @param order_id
	 * @return
	 */
	public BusiDealResult queryPicinfo(String order_id);

	RouteInfoVO querySfRouteStatus(String order_id);
	
	public BusiDealResult synOrderInfoSF(String order_id,String insureValue);
	
	public NotifyOrderInfoSFResponse querySfOrder(String order_id);
		
	/**
	 * 发展人查询
	 * @param order_id
	 * @return
	 */
	public BusiDealResult developPersonCheck(String order_id);

	/**
	 * ess工号信息获取
	 * @param order_id
	 * @return
	 */	
	public BusiDealResult essOperatorInfoQuery(String opId,String orderGonghao,String orderId)throws ApiBusiException;

	/**
	 * [AOP]订单返销
	 * @return
	 */
	public BusiDealResult orderReverseSales(String order_id)throws ApiBusiException;
	
	/**
	 * [AOP]2-3G转4G校验
	 * @param order_id
	 * @return
	 */	
	public BusiDealResult preCheck23to4(String order_id) throws ApiBusiException;
	
	/**
	 * [AOP]2-3G转4G退单
	 * @param order_id
	 * @return
	 */	
	public BusiDealResult cannel23to4(String order_id)  throws ApiBusiException;
	
	
	/**
	 * [AOP]套餐变更,撤单
	 * @param order_id
	 * @return
	 * @throws ApiBusiException 
	 */	
	public BusiDealResult prodChangeCancel(String order_id) throws ApiBusiException;
	
	/**
	 * [AOP]3G老用户业务校验
	 * @param order_id
	 * @return
	 * @throws ApiBusiException
	 */
	public BusiDealResult oldUserCheck3G(String order_id) throws ApiBusiException;
	
	/**
	 * [AOP]用户资料校验三户返回
	 * @param order_id
	 * @return
	 * @throws ApiBusiException
	 */
	public BusiDealResult userInfoCheck3Back(String order_id) throws ApiBusiException;
	/**
	 * [AOP]用户资料校验三户返回
	 * @param order_id
	 * @return
	 * @throws ApiBusiException
	 */
	public BusiDealResult userInfoCheck3BackZJ(String order_id) throws ApiBusiException;
	/**
	 * [AOP]订单查询
	 * @param order_id
	 * @return
	 * @throws ApiBusiException
	 */
	public BusiDealResult orderQuery(String order_id) throws ApiBusiException;
	/**
	 * [AOP]客户校验
	 * @param order_id
	 * @return
	 * @throws ApiBusiException
	 */
	public BusiDealResult custInfoCheck(String order_id) throws ApiBusiException;
	/**
	 * [AOP]ESS客户校验
	 * @param order_id
	 * @return
	 * @throws ApiBusiException
	 */
	public BusiDealResult custInfoCheckFromESS(String order_id, String opeSysType) throws ApiBusiException;
	/**
	 * [AOP]CB客户校验
	 * @param order_id
	 * @return
	 * @throws ApiBusiException
	 */
	public BusiDealResult custInfoCheckFromCB(String order_id, String opeSysType) throws ApiBusiException;
	
	/**
	 * [BSS]客户资料校验
	 * @param order_id
	 * @return
	 * @throws ApiBusiException
	 */
	public BusiDealResult custInfoCheckBSS(String order_id) throws ApiBusiException;
	
	/**
	 * [BSS]订单返销
	 * @return
	 */
	public BusiDealResult orderReverseSalesBSS(String order_id)throws ApiBusiException;

	/**
	 * BSS使用人数量校验接口
	 * @param order_id
	 * @return
	 * @throws ApiBusiException
	 */
	public BusiDealResult userCountCheckFromBSS(String order_id) throws ApiBusiException;

	/**
	 * 状态通知总部
	 * @param order_id
	 * @param StateUtil vo 状态参数
	 * @return
	 * @throws ApiBusiException
	 */
	public BusiDealResult StatusNoticeZB(String order_id, StateUtil vo)throws ApiBusiException;

	/**
	 * 获取EMS物流单号
	 * @param order_id
	 * @return
	 */
	public BusiDealResult getEmsLogisticsNumber(String order_id);
	
	/**
	 * 获取EMS自助服务平台物流单号
	 * @param order_id
	 * @return
	 */
	public BusiDealResult getLogisticsNumber(String order_id);
	
	/**
	 * 物流信息同步EMS
	 * @param order_id
	 * @return
	 */
	public BusiDealResult syncLogisticsInfo(String order_id);

	/**
	 * 用户资料校验
	 * @param order_id
	 * @return
	 */
	public BusiDealResult blackListCheck(String order_id);
	
	/**
	 * 开户预提交
	 * @param req
	 * @return
	 */
	public BusiDealResult bssPreCommit(PreCommitReq req);
	
	/**
	 * 模拟总商外呼确认（爬虫）
	 * @param req
	 * @return
	 */
	public BusiDealResult zbOutCallConfirm(String order_id);
	/**
	 * [爬虫]退单调总商
	 * @param req
	 * @return
	 */
	public BusiDealResult crawlerStateSynReturnStaToZS(String order_id);
	/**
	 * 查询订单审核表数量
	 * @param outTid
	 * @return
	 */
	public int queryZbOrderAuditNum(String outTid);
	/**
	 * 爬虫写订单审核表
	 * @param outId
	 * @param orderNum
	 */
	public void insertZbOrderAudit(String outId,String orderNum,String audit_status,String audit_num,String crawler_status,String distribute_status);
	public void upZbOrderAuditStatus(Map map);

	/**
	 * 爬虫写入总商订单状态表
	 * @param orderId 订单id
	 * @param outTid 外部单号
	 * @param zbId 总商订单id
	 * @param status 总商状态
	 * @param create_time 创建时间
	 * @param zbLastModifyTime 总商最后操作时间
	 * @param remark 备注
	 * @param sourceFrom 
	 */
	public void insertZbOrderStatus(String orderId,String outTid,String zbId,String status,String create_time,String zbLastModifyTime,String remark, String sourceFrom);
    /**
     * 客户资料创建接口
     * <p>Description: TODO</p>
     * @author Name
     * @time 2019年1月25日 下午4:00:17
     * @version 1.0
     * @param order_id
     * @return
     * @throws ApiBusiException 
     */
	public BusiDealResult custInfoCreate(String order_id)  throws ApiBusiException;

}
