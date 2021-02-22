package com.ztesoft.net.mall.core.service;

import params.orderqueue.req.OrderCollectionReq;

import com.ztesoft.net.mall.core.model.OpenServiceCfg;
import com.ztesoft.net.mall.core.model.OrderCfgInfo;
import com.ztesoft.net.mall.core.model.OrderHandleLogs;
import com.ztesoft.net.mall.core.model.OrderQueue;
import com.ztesoft.net.mall.core.model.OrderWork;


/**
 * 订单归集处理类
 *
 * @author zhangJun
 */

public interface IOrderQueueManager  {

	
	/**
	 * 订单报文记录：ES_ORDER_QUEUE
	 * */
	public String OrderQueueSave(OrderQueue orderQueue)throws Exception;
	 
	/**
	 * 订单创建：ES_ORDER_WORK
	 *  
	 * */
	public String OrderMainSave(OrderWork orderWork) throws Exception;
	
	/**
	 * 根据服务方法名和版本号获取开放服务配置记录:ES_OPEN_SERVICE_CFG
	 * */
	public OpenServiceCfg getOpenServiceCfgByName(String methodName,String version);
	
	
	/**
	 * 根据服务名称、版本号、报文 入库并返还调用方式
	 * */
	public OrderCfgInfo OrderColl(String method,String version,String req)throws Exception;
	
	
	
	/**
	 * 根据队列id，把数据从待发送表转移到失败表->ES_ORDER_QUEUE_FAIL
	 * */
	public void OrderQueueFailSave(String co_id);
	
	/**
	 * 根据队列id，归档报文队列表的数据到历史表->ES_ORDER_QUEUE_HIS
	 * */
	public void OrderQueueHisSave(String co_id);
	
	/**
	 * 记录订单日志->ES_ORDER_QUEUE_HIS
	 * */
	public void OrderHandLogSave(OrderHandleLogs log);
	
	
	/**
	 * 订单归集总能力
	 * */
	public String orderCollection(OrderCollectionReq ocReq);
	
	
	
	public String dubboExecute(OrderCollectionReq ocReq,String co_id,String order_id);
	
	public String mqExecute(OrderCollectionReq ocReq,String co_id,String order_id);
	
	/**
	 * 订单标准化
	 * */
	public String OrderCreate(String co_id, String order_id);
	
	
	/**
	 * 订单更改
	 * */
	public String OrderUpdate(String co_id, String order_id);
	
	
	/**
	 * 读取队列失败表数据处理，定时任务用到。
	 * */
	public void orderCollectionFailData();
	
	
	

}
