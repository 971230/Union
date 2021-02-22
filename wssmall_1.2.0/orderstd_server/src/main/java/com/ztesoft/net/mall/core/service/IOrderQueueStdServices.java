package com.ztesoft.net.mall.core.service;

import java.util.Map;

import params.orderqueue.resp.OrderQueueFailSaveResp;
import params.orderqueue.resp.OrderQueueHisSaveResp;
import zte.net.ord.params.busi.req.OrderQueueBusiRequest;
import zte.net.ord.params.busi.req.OrderQueueFailBusiRequest;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotation;
import com.ztesoft.orderstd.req.OrderCollectionStdReq;
import com.ztesoft.orderstd.resp.OrderCollectionStdResp;

/**
 * 
 * @author wanpeng 2015-12-03
 *
 */
public interface IOrderQueueStdServices {
	
	@ZteSoftCommentAnnotation(type="method",desc="消息队列数据转移到失败表",summary="消息队列数据转移到失败表")
	public OrderQueueFailSaveResp saveOrderQueueToFail(OrderQueueBusiRequest orderQueue) throws Exception;
	
	@ZteSoftCommentAnnotation(type="method",desc="消息队列数据归档",summary="消息队列数据归档")
	public OrderQueueHisSaveResp saveOrderQueueToHis(OrderQueueBusiRequest orderQueue) throws Exception;
	
	@ZteSoftCommentAnnotation(type="method",desc="获取订单队列业务对象",summary="获取订单队列业务对象")
	public OrderQueueBusiRequest queryOrderQueueBusiRequest(String order_id, String co_id);
	
	/**
	 * 订单处理
	 * @param req
	 * @return
	 */
	public OrderCollectionStdResp orderStd(OrderQueueFailBusiRequest orderQueueReq, OrderCollectionStdReq req) throws Exception;
	
	/**
	 * 
	 * @Description: 根据内部编码获取OpenService配置
	 * @param req
	 * @return   
	 * @author zhouqiangang
	 * @date 2015年11月30日 下午4:03:05
	 */
	public Map<String, String> getOpenServiceCfgByServiceCode(OrderCollectionStdReq req) ;

}
