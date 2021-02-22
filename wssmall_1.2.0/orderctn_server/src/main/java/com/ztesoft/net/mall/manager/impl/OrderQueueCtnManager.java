package com.ztesoft.net.mall.manager.impl;

import zte.net.ord.params.busi.req.OrderQueueBusiRequest;
import zte.net.ord.params.busi.req.OrderQueueFailBusiRequest;
import zte.net.ord.params.busi.req.OrderQueueHisBusiRequest;

import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.utils.CacheUtil;
import com.ztesoft.net.mall.manager.IOrderQueueCtnManager;
//import com.ztesoft.net.search.common.ESearchData;
//import com.ztesoft.net.search.common.ESearchFactory;

/**
 * 
 * @Package com.ztesoft.net.mall.manager.impl
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author zhouqiangang
 * @date 2015年11月18日 下午5:19:34
 */
@SuppressWarnings("rawtypes")
public class OrderQueueCtnManager extends BaseSupport implements IOrderQueueCtnManager {

	/**
	 * 
	 * Description: 保存队列表
	 * 
	 * @param orderQueue
	 * @return
	 * @throws Exception
	 * @see com.ztesoft.net.mall.manager.IOrderQueueCtnManager#saveOrderQueue(zte.net.ord.params.busi.req.OrderQueueBusiRequest)
	 * @author zhouqiangang
	 * @date 2015年11月24日 下午5:38:58
	 */
	@Override
	public OrderQueueBusiRequest saveOrderQueue(OrderQueueBusiRequest orderQueue) throws Exception {
		final String reqMsgPersistentType = getReqMsgPersistentType();
		String reqMsgStr = "";
		if (Consts.REQ_MSG_PERSISTENT_TYPE_ES.equals(reqMsgPersistentType)) {
			reqMsgStr = orderQueue.getContents();
			orderQueue.setContents(null);
		}
		this.baseDaoSupport.insert("es_order_queue", orderQueue);

		if (Consts.REQ_MSG_PERSISTENT_TYPE_ES.equals(reqMsgPersistentType)) {
//			ESearchData esData = new ESearchData();
//			esData.setLog_id(orderQueue.getCo_id());
//			esData.setOperation_code("es_order_queue");
//			esData.setIn_param(reqMsgStr);// 写入报文
//			ESearchFactory.getSearchInst().insertInParam(esData);
		}
		return orderQueue;
	}

	/**
	 * 
	 * Description: 保存失败队列
	 * 
	 * @param orderQueueFail
	 * @return
	 * @throws Exception
	 * @see com.ztesoft.net.mall.manager.IOrderQueueCtnManager#saveOrderQueuefail(zte.net.ord.params.busi.req.OrderQueueFailBusiRequest)
	 * @author zhouqiangang
	 * @date 2015年11月24日 下午5:39:11
	 */
	@Override
	public OrderQueueFailBusiRequest saveOrderQueuefail(OrderQueueFailBusiRequest orderQueueFail) throws Exception {

		final String reqMsgPersistentType = getReqMsgPersistentType();
		String reqMsgStr = "";
		if (Consts.REQ_MSG_PERSISTENT_TYPE_ES.equals(reqMsgPersistentType)) {
			reqMsgStr = orderQueueFail.getContents();
			orderQueueFail.setContents(null);
		}
		this.baseDaoSupport.insert("es_order_queue_fail", orderQueueFail);
		if (Consts.REQ_MSG_PERSISTENT_TYPE_ES.equals(reqMsgPersistentType)) {
//			ESearchData esData = new ESearchData();
//			esData.setLog_id(orderQueueFail.getCo_id());
//			esData.setOperation_code("es_order_queue_fail");
//			esData.setIn_param(reqMsgStr);// 写入报文
//			ESearchFactory.getSearchInst().insertInParam(esData);
		}

		return orderQueueFail;
	}

	/**
	 * 
	 * Description: 保存队列历史表
	 * 
	 * @param orderQueueHis
	 * @return
	 * @throws Exception
	 * @see com.ztesoft.net.mall.manager.IOrderQueueCtnManager#saveOrderQueueHis(zte.net.ord.params.busi.req.OrderQueueHisBusiRequest)
	 * @author zhouqiangang
	 * @date 2015年11月24日 下午5:39:23
	 */
	@Override
	public OrderQueueHisBusiRequest saveOrderQueueHis(OrderQueueHisBusiRequest orderQueueHis) throws Exception {
		final String reqMsgPersistentType = getReqMsgPersistentType();
		String reqMsgStr = "";
		if (Consts.REQ_MSG_PERSISTENT_TYPE_ES.equals(reqMsgPersistentType)) {
			reqMsgStr = orderQueueHis.getContents();
			orderQueueHis.setContents(null);
		}
		this.baseDaoSupport.insert("es_order_queue_his", orderQueueHis);

		if (Consts.REQ_MSG_PERSISTENT_TYPE_ES.equals(reqMsgPersistentType)) {
//			ESearchData esData = new ESearchData();
//			esData.setLog_id(orderQueueHis.getCo_id());
//			esData.setOperation_code("es_order_queue_his");
//			esData.setIn_param(reqMsgStr);
//			ESearchFactory.getSearchInst().insertInParam(esData);
		}
		return orderQueueHis;
	}

	/**
	 * 
	 * @Description: 获取队列表报文持久化类型
	 * @return
	 * @author zhouqiangang
	 * @date 2015年12月2日 上午10:39:16
	 */
	private String getReqMsgPersistentType() {
		CacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		String reqMsgPersistentType = cacheUtil.getConfigInfo(Consts.REQ_MSG_PERSISTENT_TYPE);
		if (StringUtil.isEmpty(reqMsgPersistentType)) {
			reqMsgPersistentType = Consts.REQ_MSG_PERSISTENT_TYPE_DB;
		}
		return reqMsgPersistentType;
	}

}
