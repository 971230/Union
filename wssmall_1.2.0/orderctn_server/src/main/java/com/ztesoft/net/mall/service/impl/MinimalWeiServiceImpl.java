package com.ztesoft.net.mall.service.impl;

import javax.jws.WebService;

import org.apache.log4j.Logger;

import com.ztesoft.common.util.JsonUtil;
import com.ztesoft.common.util.MD5Util;
import com.ztesoft.common.util.StringUtils;
import com.ztesoft.net.cache.common.CacheFactory;
import com.ztesoft.net.cache.common.INetCache;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.mall.consts.OrderCtnConsts;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.mall.core.utils.IJSONUtil;
import com.ztesoft.net.mall.service.IMinimalWeiService;
import com.ztesoft.net.mall.service.beans.MinimalWeiCtnRequest;
import com.ztesoft.net.mall.service.beans.MinimalWeiCtnResponse;

import consts.ConstsCore;
import zte.net.iservice.IOrderCtnService;
import zte.net.iservice.IOrderQueueBaseManager;
import zte.params.orderctn.req.OrderCtnReq;
import zte.params.orderctn.resp.OrderCtnResp;

@WebService(targetNamespace="http://service.com/",endpointInterface="com.ztesoft.net.mall.service.IMinimalWeiService",serviceName="MinimalWeiService")
public class MinimalWeiServiceImpl implements IMinimalWeiService {

	private static Logger logger = Logger.getLogger(MinimalWeiServiceImpl.class);
	
	private IOrderCtnService orderCtnService;
	private IOrderQueueBaseManager orderQueueBaseManager;
	private ICacheUtil cacheUtil;

	private void initBean() {
		if(null == orderCtnService) {
			orderCtnService = SpringContextHolder.getBean("orderCtnService");
		}
		if (null == orderQueueBaseManager) {
			orderQueueBaseManager = SpringContextHolder.getBean("orderQueueBaseManager");
		}
		if(null == cacheUtil) {
			cacheUtil = SpringContextHolder.getBean("cacheUtil");
		}
	}
	
	@Override
	public MinimalWeiCtnResponse minimalWeiOrderCtn(MinimalWeiCtnRequest request) {
		MinimalWeiCtnResponse response = new MinimalWeiCtnResponse();
		final String orderId;
		String orderSource = "";
		String serialNumber = "";
		try {
			if(null != request){
				logger.info("[minimalWeiOrderCtn] 完整请求报文:" + request);
				orderId = request.getORDER_ID();
				if(null == orderId){//缺少关键参数
					response.setRESP_CODE("8888");
					response.setRESP_DESC("订单[" + orderId + "]同步失败,缺少订单关键参数");
					response.setSERIAL_NUMBER(serialNumber);
					response.setTRADE_ID("");
				}
				// 订单唯一性校验-校验订单是否存在
			    INetCache cache = CacheFactory.getCacheByType("");
			    String key = new StringBuffer().append(orderSource).append(orderId).toString();
				key = MD5Util.MD5(key);
			    String def = String.valueOf(cache.get(OrderCtnConsts.ORDER_DUPLICATE_CHECK_NAMESPACE, key));
			    if (null != def && def.equals(key)) {//订单重复
			    	response.setRESP_CODE("8888");
					response.setRESP_DESC("订单[" + orderId + "]同步失败,订单已接收.");
					response.setSERIAL_NUMBER(serialNumber);
					response.setTRADE_ID("");
			    }else{

			    	// 写缓存
			    	cache.set(OrderCtnConsts.ORDER_DUPLICATE_CHECK_NAMESPACE,key, key,OrderCtnConsts.ORDER_DUPLICATE_CHECK_NAMESPACE_CACHE_TIMEOUT);
			    	initBean();
			    	//调用订单归集接口
					OrderCtnReq req = new OrderCtnReq();
					req.setOutServiceCode(OrderCtnConsts.OUT_SERVICE_CODE_HBBROADBANDORDERSTANDARD);
					req.setVersion(OrderCtnConsts.VERSION);
					req.setReqMsgStr(JsonUtil.toJson(request));
					req.setFormat(OrderCtnConsts.ORDER_QUEUE_MSG_TYPE_JSON);
					req.setOutOrderId(orderId);
					long begin = System.currentTimeMillis();
					final OrderCtnResp resp = orderCtnService.orderCtn(req);
					long end = System.currentTimeMillis();
					logger.info("[LocalMallServlet] 执行标准化总时间为:[" + (end-begin) +"] 返回对象: "+ IJSONUtil.beanToJson(resp));
					if (null != resp) {
						String rpc_type = resp.getRpc_type();
						if (OrderCtnConsts.RPC_TYPE_DUBBO.equals(rpc_type)) {// dubbo 调用返回
							String error_code = resp.getError_code();
							if (ConstsCore.ERROR_SUCC.equals(error_code)) {// 同步成功
								response.setRESP_CODE("0000");
								response.setRESP_DESC("成功");
								response.setSERIAL_NUMBER(serialNumber);
								response.setTRADE_ID(resp.getBase_order_id());
							} else {
								response.setRESP_CODE("8888");
								response.setRESP_DESC("订单[" + orderId + "]同步失败.");
								response.setSERIAL_NUMBER(serialNumber);
								response.setTRADE_ID(resp.getBase_order_id());
							}
						} else if (OrderCtnConsts.RPC_TYPE_MQ.equals(rpc_type)) {// mq 返回
							String error_code = resp.getError_code();
							if (ConstsCore.ERROR_SUCC.equals(error_code)) {// 同步成功
								response.setRESP_CODE("0000");
								response.setRESP_DESC("成功");
								response.setSERIAL_NUMBER(serialNumber);
								response.setTRADE_ID(resp.getBase_order_id());
							} else {
								response.setRESP_CODE("8888");
								response.setRESP_DESC("订单[" + orderId + "]同步失败.");
								response.setSERIAL_NUMBER(serialNumber);
								response.setTRADE_ID(resp.getBase_order_id());
							}
						} else {
							response.setRESP_CODE("8888");
							response.setRESP_DESC("订单[" + orderId + "]同步失败.");
							response.setSERIAL_NUMBER(serialNumber);
							response.setTRADE_ID(resp.getBase_order_id());
						}
						
						String esearch_flag = "0";//默认关闭
						esearch_flag = cacheUtil.getConfigInfo(EcsOrderConsts.ESEARCH_FLAG);//是否取消前置校验 0：关闭 1：开启
						if("1".equals(esearch_flag)) {
							final String outJsonStr = response.toString();
							final String inJsonStr = request.toString();
							if (StringUtils.isNotEmpty(resp.getBase_co_id())) {
								Thread thread = new Thread(new Runnable() {
									@Override
									public void run() {
										orderQueueBaseManager.writeEsearch(resp.getBase_co_id(), inJsonStr, outJsonStr,orderId);
									}
								});
								thread.setName("HBBroadbandWriteEsearchThread");
								thread.start();
							}
						}
						
					} else {
						response.setRESP_CODE("8888");
						response.setRESP_DESC("订单[" + orderId + "]同步失败.");
						response.setSERIAL_NUMBER(serialNumber);
						response.setTRADE_ID(resp.getBase_order_id());					}
			    
			    }
			}else{//请求报文为空

				response.setRESP_CODE("8888");
				response.setRESP_DESC("同步失败.请求报文为空");
				response.setSERIAL_NUMBER(serialNumber);
				response.setTRADE_ID("");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response.setRESP_CODE("8888");
			response.setRESP_DESC("同步失败.报文处理异常");
			response.setSERIAL_NUMBER(serialNumber);
			response.setTRADE_ID("");
		}

		logger.info("[HbSingleBroadbandCtnServlet] 返回报文:"+response.toString());
		return response;
	}

}
