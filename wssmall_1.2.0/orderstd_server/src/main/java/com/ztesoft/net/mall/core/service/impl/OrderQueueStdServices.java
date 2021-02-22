package com.ztesoft.net.mall.core.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.reflect.ConstructorUtils;

import params.ZteRequest;
import params.ZteResponse;
import params.orderqueue.resp.OrderCollectionResp;
import params.orderqueue.resp.OrderQueueFailSaveResp;
import params.orderqueue.resp.OrderQueueHisSaveResp;
import services.DefaultServiceContext;
import services.ServiceBase;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.iservice.IOrderQueueBaseManager;
import zte.net.ord.params.busi.req.InfHeadBusiRequest;
import zte.net.ord.params.busi.req.OrderQueueBusiRequest;
import zte.net.ord.params.busi.req.OrderQueueFailBusiRequest;

import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.mall.core.consts.OrderConsts;
import com.ztesoft.net.mall.core.service.IDcPublicInfoManager;
import com.ztesoft.net.mall.core.service.IOrderQueueStdServices;
import com.ztesoft.net.mall.core.service.impl.cache.DcPublicInfoCacheProxy;
import com.ztesoft.net.mall.core.utils.CacheUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.orderstd.req.OrderCollectionStdReq;
import com.ztesoft.orderstd.resp.OrderCollectionStdResp;
import com.ztesoft.rop.common.ServiceMethodHandler;
import com.ztesoft.util.HttpClientUtil;

import commons.CommonTools;
import consts.ConstsCore;

/**
 * 
 * @author wanpeng 2015-12-05
 * 
 */
public class OrderQueueStdServices extends ServiceBase implements IOrderQueueStdServices {
	
	/**
	 * 格式固定为xml
	 */
	private static final String format = "xml";
	
	/**
	 * 服务版本号固定为1.0
	 */
	private static final String version = "1.0";
	
	private IDcPublicInfoManager dcPublicInfoManager;
	
	@Resource
	private IOrderQueueBaseManager orderQueueBaseManager;
	
	private void initBean(){
		if(orderQueueBaseManager ==null)
			orderQueueBaseManager = SpringContextHolder.getBean("orderQueueBaseManager");
	}

	@Override
	public OrderQueueFailSaveResp saveOrderQueueToFail(OrderQueueBusiRequest orderQueue) throws Exception {
		this.initBean();
		OrderQueueFailSaveResp resp=new OrderQueueFailSaveResp();
		orderQueueBaseManager.saveOrderQueueToFail(orderQueue);
		resp.setError_code("0");
		resp.setError_msg("成功");
//		this.addReturn(orderQueue, resp);
		return resp;
	}

	@Override
	public OrderQueueHisSaveResp saveOrderQueueToHis(OrderQueueBusiRequest orderQueue) throws Exception {
		this.initBean();
		OrderQueueHisSaveResp resp = new OrderQueueHisSaveResp();
		orderQueueBaseManager.saveOrderQueueToHis(orderQueue);
		resp.setError_code("0");
		resp.setError_msg("成功");
//		this.addReturn(orderQueue, resp);
		return resp;
	}

	@Override
	public OrderQueueBusiRequest queryOrderQueueBusiRequest(String order_id, String co_id) {
		this.initBean();
		OrderQueueBusiRequest req = orderQueueBaseManager.getOrderQueue(order_id, co_id);
		return req;
	}

	@Override
	public OrderCollectionStdResp orderStd(OrderQueueFailBusiRequest orderQueueReq, OrderCollectionStdReq req) throws Exception {
		String service_code = orderQueueReq.getService_code();
		String inReq = req.getInReq();
		OrderCollectionStdResp resp = new OrderCollectionStdResp();
		// 第一步 取开关设置
		String cf_id = OrderConsts.ORDER_STD_OLD_SYS_CF_ID;
		CacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		String cf_value = cacheUtil.getConfigInfo(cf_id);
		
		// 是否需要判断无开关设置 采取默认按本地标准化
		if (OrderConsts.ORDER_STD_OLD_SYS_YES.equals(cf_value)) {
			String requestUrl = "";
			// 发POST请求按旧系统标准化订单
			if (OrderConsts.SERVICE_CODE_NEWMALLORDERSTANDARD.equals(service_code)) {// 新商城
				requestUrl = cacheUtil.getConfigInfo(OrderConsts.SERVER_IP_ADDR_NEWMALLORDER);
				if (StringUtil.isEmpty(requestUrl)) {
					CommonTools.addError(ConstsCore.ERROR_FAIL, "新商城订单标准化地址未配置！");
				} else {
					HttpClientUtil.getResult(requestUrl, inReq, "utf-8");
				}
				
			} else if (OrderConsts.SERVICE_CODE_CENTERMALLORDERSTANDARD.equals(service_code)) {// 总商
				requestUrl = cacheUtil.getConfigInfo(OrderConsts.SERVER_IP_ADDR_CENTERMALLORDER);
				if (StringUtil.isEmpty(requestUrl)) {
					CommonTools.addError(ConstsCore.ERROR_FAIL, "总商订单标准化地址未配置！");
				} else {
					HttpClientUtil.getResult(requestUrl, inReq, "utf-8");
				}
			} else {
				CommonTools.addError(ConstsCore.ERROR_FAIL, "[服务类型:" + service_code + "]未知！");
			}
			
		} else {
			resp = dubboExecute(orderQueueReq);
		}

		return resp;
	}
	
	/**
	 * 
	 * @Description: 根据外部编码获取OpenService配置
	 * @param req
	 * @return   
	 * @author zhouqiangang
	 * @date 2015年11月30日 下午4:03:05
	 */
	@Override
	public Map<String, String> getOpenServiceCfgByServiceCode(OrderCollectionStdReq req) {
		if (null == dcPublicInfoManager) {
			dcPublicInfoManager = SpringContextHolder.getBean("dcPublicInfoManager");
		}
		DcPublicInfoCacheProxy proxy = new DcPublicInfoCacheProxy(dcPublicInfoManager);
		return proxy.getOpenServiceCfgByServiceCode(req.getService_code(), req.getVersion());
	}
	
	/**
	 * 执行MQ发送消息
	 */
	@SuppressWarnings("unchecked")
	public OrderCollectionStdResp mqExecute(String service_code, String co_id, String order_id, String topic)
			throws Exception {
		// 1、接收参数
		DefaultServiceContext context = DefaultServiceContext.getInstance();
		ServiceMethodHandler serviceMethodHandler = context.getServiceMethodHandler(service_code, version);
		Class zteReqClass = serviceMethodHandler.getRequestType();
		Class<ZteResponse> zteResClass = (Class<ZteResponse>) serviceMethodHandler.getRespType();

		// 2、请求对象生成
		Object[] objs = null;
		ZteRequest<ZteResponse> zteRequest = (ZteRequest<ZteResponse>) ConstructorUtils.invokeConstructor(zteReqClass,
				objs);

		// 3、封装参数
		zteRequest.setBase_co_id(co_id);
		zteRequest.setBase_order_id(order_id);
		zteRequest.setFormat(format);
		zteRequest.setMqTopic(topic);
		// 4、MQ调用(生产消息)
		ZteClient client = ClientFactory.getZteMqClient(ManagerUtils.getSourceFrom());// mq客户端
		OrderCollectionStdResp resp = (OrderCollectionStdResp) client.execute(zteRequest, zteResClass);
		logger.info("[MQ生产者] 生产MQ消息返回对象: " + resp);
		return resp;
	}

	/**
	 * 
	 * @Description: 执行dubbo调用
	 * @param service_code
	 * @param co_id
	 * @param order_id
	 * @return
	 * @throws Exception
	 * @author zhouqiangang
	 * @date 2015年11月24日 下午4:14:38
	 */
	@SuppressWarnings("unchecked")
	private OrderCollectionStdResp dubboExecute(OrderQueueFailBusiRequest orderQueueReq) throws Exception {
		String service_code = orderQueueReq.getService_code();
		String co_id = orderQueueReq.getCo_id();
		String order_id = orderQueueReq.getOrder_id();
		OrderCollectionStdResp resp = new OrderCollectionStdResp();
		
		DefaultServiceContext context = DefaultServiceContext.getInstance();
		ServiceMethodHandler serviceMethodHandler = context.getServiceMethodHandler(service_code, "1.0");
		Class zteReqClass = serviceMethodHandler.getRequestType();
		Object[] objs = null;
		ZteRequest zteRequest = null;
		try {
			zteRequest = (ZteRequest<ZteResponse>) ConstructorUtils.invokeConstructor(zteReqClass, objs);
		} catch (Exception e) {
			resp.setError_code(ConstsCore.ERROR_FAIL);
			resp.setError_msg("构建请求对象失败!");
			e.printStackTrace();
		}
		zteRequest.setBase_co_id(co_id);
		zteRequest.setBase_order_id(order_id);
		
		if (OrderConsts.SERVICE_CODE_TEMPLATESORDERSTANDARD.equals(service_code)) {
			//获取头部信息
			InfHeadBusiRequest infHead = CommonDataFactory.getInstance().getInfHeadFor(order_id, co_id);
			if (null == infHead) {
				resp.setError_code(ConstsCore.ERROR_FAIL);
				resp.setError_msg("获取报文头部信息失败!");
			}
			com.ztesoft.common.util.BeanUtils.setProperty(zteRequest, "template_code", infHead.getTemplate_code());
			com.ztesoft.common.util.BeanUtils.setProperty(zteRequest, "template_version", infHead.getTemplate_version());
		}
		// 淘宝订单，需要传入淘宝参数字符串
		if (OrderConsts.SERVICE_CODE_TAOBAOMALLORDERSTANDARD.equals(service_code)) {
			com.ztesoft.common.util.BeanUtils.setProperty(zteRequest, "params", orderQueueReq.getDeal_contents());
		}
		zteRequest.setFormat(orderQueueReq.getType());
		
		OrderCollectionResp zteResponse = null;
		try {
			zteResponse = (OrderCollectionResp) serviceMethodHandler.getHandlerMethod().invoke(
					serviceMethodHandler.getHandler(), zteRequest);
		} catch (Exception e) {
			resp.setError_code(ConstsCore.ERROR_FAIL);
			resp.setError_msg("队列["+co_id+"]标准化失败!");
			e.printStackTrace();
		} 
		if(null != zteResponse && ConstsCore.ERROR_SUCC.equals(zteResponse.getError_code())){
			resp.setError_code(ConstsCore.ERROR_SUCC);
			resp.setError_msg("队列["+co_id+"]标准化成功!");
			
		}else{
			resp.setError_code(ConstsCore.ERROR_FAIL);
			resp.setError_msg("队列["+co_id+"]标准化失败!");
		}
		return resp;
	}

}
