package com.ztesoft.net.service;

import java.util.List;
import java.util.Map;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.net.ecsord.params.ecaop.req.OrderListByWorkQueryReq;
import com.ztesoft.net.ecsord.params.ecaop.resp.OrderListByWorkQueryResp;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.model.OrderQueryParams;

import params.order.req.OrderHandleLogsReq;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.ecsord.params.busi.req.OrderWorksBusiRequest;

public interface IOrdWorkManager {

	/**
	 * 工单相关订单列表查询接口
	 * 
	 * @param requ
	 * @return
	 */
	public OrderListByWorkQueryResp queryOrderListByWork(OrderListByWorkQueryReq requ);

	/**
	 * 根据订单id查询工单列表
	 * 
	 * @param requ
	 * @return
	 */
	public List<OrderWorksBusiRequest> queryWorkListByOrderId(String order_id);

	/**
	 * 根据工单id查询工单
	 * 
	 * @param requ
	 * @return
	 */
	public OrderWorksBusiRequest queryWorkByWorkOrderId(String work_order_id);

	/**
	 * 审核校验工单派送情况
	 * 
	 * @param order_id
	 * @return
	 */
	public String orderCheck(String order_id);

	/**
	 * 工单回收关闭
	 * 
	 * @param order_id
	 * @return
	 */
	public String closeOrdWork(String order_id, String work_reason);

	/**
	 * 页面工单列表查询 展示工单综合处理情况
	 * 
	 * @param order_id
	 * @return
	 */
	public Page queryWorkListByOrder_id(OrderQueryParams params, int pageNo, int pageSize);

	/**
	 * 单个订单工单列表
	 * 
	 * @param order_id
	 * @return
	 */
	public Page getWorkListByOrderId(String order_id, int pageNo, int pageSize);

	/**
	 * 意向单处理页面
	 * 
	 * @param order_id
	 * @return
	 */
	public Page queryIntent(OrderQueryParams params, int pageNo, int pageSize);

	/**
	 * 意向单查询页面
	 * 
	 * @param order_id
	 * @return
	 */
	public Page intentHandleQuery(OrderQueryParams params, int pageNo, int pageSize);

	public List<Map<String, Object>> getIntentStatusList(String type);

	public List<Map<String, Object>> getIntentLogs(String order_id);

	public Map<String, Object> getIntentDetail(String order_id);

	/**
	 * @see 获取行政县分，order_city_code为空则取全省
	 * @param order_city_code
	 * @return
	 */
	public List<Map<String, Object>> getIntentCountyList(String order_city_code);

	/**
	 * @see 获取营业县分，order_city_code为空则取全省
	 * @param order_city_code
	 * @return
	 */
	public List<Map<String, Object>> getCountyList(String order_city_code);

	/**
	 * 订单领取新页面显示的详情
	 * 
	 * @param order_id
	 * @return
	 */
	public Map<String, Object> getOrderDetail(String order_id);

	/**
	 * 订单领取新页面显示的详情下的订单记录日志
	 * 
	 * @param order_id
	 * @return
	 */
	public List<OrderHandleLogsReq> getOrderLogs(String order_id);

	/**
	 * 意向单客户联系结果列表
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List getContactResultsList();
	
	/**
	 * 意向单客户联系二级结果列表
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List getContactSecondResultsList(String linkId);

	/**
	 * 判断是否为宽带一期流程
	 * 
	 * @param order_id
	 * @param orderTree
	 * @return
	 */
	public Boolean isKDYQ(String order_id, OrderTreeBusiRequest orderTree) throws ApiBusiException;

	/**
	 * 判断是否为预约单
	 * 
	 * @param order_id
	 * @param orderTree
	 * @return
	 */
	public Boolean isKDYYD(String order_id, OrderTreeBusiRequest orderTree) throws ApiBusiException;

	/**
	 * 修改订单状态，新增同步消息
	 * 
	 * @throws ApiBusiException
	 */
	public void updateOrderState(String order_id, String order_state, String msg) throws ApiBusiException;

	/**
	 * 退单处理 退单选择
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getDisposeResultsList();

	/**
	 * <p>
	 * Title: 自定义工单操作日志
	 * </p>
	 * <p>
	 * Description: TODO
	 * </p>
	 * 
	 * @author sgf
	 * @time 2018年9月13日 上午10:49:26
	 * @version 1.0
	 * @param trim
	 * @return
	 */
	public List<Map<String, Object>> getWorkCustomLogs(String trim);

	/**
	 * 查询县分名称
	 * 
	 * @param county_code
	 * @return
	 */
	public String getCountyName(String county_code);

	/**
	 * 意向单转正式订单，工单状态转处理中
	 * 
	 * @param order_id
	 * @return
	 */
	public String upWorkStatus(String order_id);
	
	/**
	 * 根据营业县分编号查询营业县分信息
	 * @param regionIdList
	 * @param countyIdList
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getBusiCountyByIds(List<String> regionIdList,
			List<String> countyIdList) throws Exception;
	/**
	 * 
	 * @param order_id
	 * @return
	 */
	public String isMixOrd(String order_id);
	
	public List<Map<String, Object>> getTopSeedProfessionalLine(String type);
	public List<Map<String, Object>> getTopSeedType(String type);
	public List<Map<String, Object>> getTopSeedGroupId(String type);

}
