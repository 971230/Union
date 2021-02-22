package com.ztesoft.net.service;

import java.util.List;
import java.util.Map;

import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.ecsord.params.sf.vo.WaybillRoute;

import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.model.CoQueue;
import com.ztesoft.net.mall.core.model.Logi;
import com.ztesoft.net.model.BusiDealResult;
import com.ztesoft.net.model.EntityInfoVO;
import com.ztesoft.net.model.OrdReceipt;
import com.ztesoft.net.model.OrderBtn;
import com.ztesoft.net.model.OrderQueryParams;

public interface IOrdCenterManager {

	/**
	 * 查询订单列表
	 * @作者 MoChunrun
	 * @创建日期 2014-9-30 
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Page queryOrderForPageBuyFlow(int pageNo,int pageSize,OrderQueryParams params);
	
	/**
	 * 按流程ID查询订单处理按钮
	 * @作者 MoChunrun
	 * @创建日期 2014-9-30 
	 * @param trace_id
	 * @return
	 */
	public OrderBtn getOrderFlowBtns(String trace_id);
	
	/**
	 * 查询订单流程所有按钮
	 * @作者 MoChunrun
	 * @创建日期 2014-9-30 
	 * @param trace_id
	 * @return
	 */
	public List<OrderBtn> listOrderFlowBtns(String trace_id);
	
	/**
	 * 订单挂起
	 * @作者 ZX
	 * @创建时间 2014-10-14
	 * @param order_id
	 */
	OrderTreeBusiRequest suspend_save(String order_id, String pending_reason);
	
	/**
	 * 订单锁定
	 * @作者 ZX
	 * @创建时间 2014-10-16
	 * @param order_id
	 */
	void order_lock(String order_id);
	
	/**
	 * 订单列表预警信息
	 * @作者 zhangjun
	 * @创建时间 2014-10-16
	 * @param order_id
	 */
	String getWarning(String order_ids);
	
	/**
	 * @作者 ZX
	 * @创建时间 2014-10-16
	 * @param obj_name
	 * @return
	 */
	Page adminUserList(int pageNO, int pageSize, String obj_name);
	
	/**
	 * @作者 ZX
	 * @创建时间 2014-10-16
	 * @param order_id
	 * @param userid
	 * @param username
	 * @return
	 */
	String entrust_save(String order_id, String userid, String username);

	/**
	 * @作者 ZX
	 * @创建时间 2014-10-23
	 * 物流公司
	 * @return
	 */
	List<Logi> logi_company ();
	
	/**
	 * @作者 ZX
	 * @创建时间 2014-10-23
	 * 物流公司
	 * @return
	 */
	List<Map> logi_company_regions (String logi_post_id);
	
	/**
	 * @作者 ZX
	 * @创建时间 2014-10-24
	 * 回收资料
	 * @return
	 */
	List<Map> dic_material_retrieve();
	
	/**
	 * @作者 ZX
	 * @创建时间 2014-10-25
	 * 回单确认
	 * @param order_id
	 */
	void affirm_receipt(String order_id, OrdReceipt ordReceipt);
	
	
	
	/**
	 * 
	 * @param order_id
	 * @param ordReceipt
	 */
	boolean oldOrderArchivesForStanding(CoQueue coQueue);
	
	
	
	/**
	 * @作者 ZX
	 * @创建时间 2014-10-25
	 * 回单确认
	 * @param order_id
	 */
	void newOrderArchivesForStanding(CoQueue coQueue);
	
	/**
	 * 获取es_dc_sql下拉框
	 * @作者ZX
	 * @param dcName
	 * @return
	 */
	List<Map> getDcSqlByDcName(String dcName);
	
	List<EntityInfoVO> entityInfoList(String order_id, String serial_num);
	/***
	 * 查询当前用户被锁定的单
	 * @param param 参数
	 * @param pageNo 
	 * @param pageSize
	 * @param params
	 * @return 
	 */
	public Page getLockOrdList(int pageNo,int pageSize,OrderQueryParams params);
	/**
	 * 查询当前用户被锁定的订单ID
	 * @return
	 */
	public List getLockOrderIds(OrderQueryParams params);
	
	public String unLock(String order_id);
	/**
	 * 根据用户ID锁定订单
	 * @param order_id
	 * @param userId
	 */
	public Boolean orderLockByWl(String order_id,String userId);
	/**
	 * 异常列表查询
	 * @param order_id 订单ID
	 * @return
	 */
	public List getExceptionLogList(String order_id);
	
	/**
	 * 顺丰物流信息处理
	 * @param waybillRouteList
	 */
	public BusiDealResult doSFLogisticsInfo(List<WaybillRoute> waybillRouteList);
}
