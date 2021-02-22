package com.ztesoft.net.mall.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.reflect.ConstructorUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import params.ZteBusiRequest;
import params.ZteRequest;
import params.ZteResponse;
import params.orderqueue.resp.OrderCollectionResp;
import params.req.CodePurchaseMallOrderStandardReq;
import params.req.GroupOrderStandardReq;
import params.req.InternetGroupOrderStandardReq;
import params.req.FixBusiOrderStandardReq;
import params.req.MobileBusiCtnStandardReq;
import params.req.NewMallOrderStandardReq;
import params.req.StdChannelConvertReq;
import params.req.StdStartWorkflowReq;
import params.req.StdWorkflowMatchReq;
import services.DefaultServiceContext;
import zte.net.common.annontion.context.request.DefaultActionRequestDefine;
import zte.net.common.annontion.context.request.RequestBeanDefinition;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.ecaop.resp.ChannelConvertQrySubResp;
import zte.net.ecsord.params.order.resp.StartWorkflowRsp;
import zte.net.ecsord.params.order.resp.WorkflowMatchRsp;
import zte.net.iservice.IOrderCtnService;
import zte.net.iservice.IOrderQueueBaseManager;
import zte.net.iservice.consts.InfoConsts;
import zte.net.ord.params.busi.req.InfHeadBusiRequest;
import zte.net.ord.params.busi.req.OrderQueueBusiRequest;
import zte.params.orderctn.req.ChannelConvertQryCtnReq;
import zte.params.orderctn.req.CtnStartWorkflowReq;
import zte.params.orderctn.req.CtnWorkflowMatchReq;
import zte.params.orderctn.req.HSOrderCtnB2BReq;
import zte.params.orderctn.req.HSOrderCtnReq;
import zte.params.orderctn.req.OrderCtnReq;
import zte.params.orderctn.req.TbTianjiTestReq;
import zte.params.orderctn.resp.HSOrderCtnB2BResp;
import zte.params.orderctn.resp.HSOrderCtnResp;
import zte.params.orderctn.resp.OrderCtnResp;
import zte.params.orderctn.resp.TbTianjiTestResp;
import zte.params.template.req.TemplateAccessConvertReq;
import zte.params.template.resp.TemplateAccessConvertResp;

import com.powerise.ibss.framework.Const;
import com.ztesoft.api.AppKeyEnum;
import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.DefaultZteRopClient;
import com.ztesoft.api.ZteClient;
import com.ztesoft.common.util.SequenceTools;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.cache.common.INetCache;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.mall.consts.OrderCtnConsts;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.consts.OrderQueueConsts;
import com.ztesoft.net.mall.core.service.IDcPublicInfoManager;
import com.ztesoft.net.mall.core.service.impl.cache.DcPublicInfoCacheProxy;
import com.ztesoft.net.mall.core.utils.CacheUtil;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.mall.core.utils.IJSONUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.mall.outter.inf.exe.ThreadExecuter;
import com.ztesoft.net.mall.outter.inf.exe.ThreadExecuter.OrderSyncThread;
import com.ztesoft.net.mall.outter.inf.iservice.IOutterECSTmplCtnManager;
import com.ztesoft.net.mall.outter.inf.model.OutterTmpl;
import com.ztesoft.net.mall.server.servlet.HSMallServer;
import com.ztesoft.rop.common.ServiceMethodHandler;

import commons.CommonNTools;
import commons.CommonTools;
import consts.ConstsCore;

/**
 * 
 * @Package com.ztesoft.net.mall.service.impl
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author zhouqiangang
 * @date 2015年11月24日 下午4:09:42
 */
@SuppressWarnings("rawtypes")
public class OrderCtnService extends BaseSupport implements IOrderCtnService {

	private static Logger logger = Logger.getLogger(OrderCtnService.class);
	@Resource
	private ICacheUtil cacheUtil;
	/**
	 * 服务版本号固定为1.0
	 */
	private static final String version = "1.0";

	private IDcPublicInfoManager dcPublicInfoManager;

	private IOrderQueueBaseManager orderQueueBaseManager;

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public OrderCtnResp orderCtn(OrderCtnReq req) throws Exception {
		OrderCtnResp resp = new OrderCtnResp();

		// 根据 ServiceCode 获取对应配置
		Map<String, String> openServiceCfgMap = getOpenServiceCfgByOutServiceCode(req);
//		openServiceCfgMap.put("write_queue_flag", "0");
		String rpcType = req.getRpcType();

		final String serviceCode = Const.getStrValue(openServiceCfgMap,
				"service_code");

		logger.info("[OrderCtnService] 服务编码:[" + req.getOutServiceCode()
				+ "] 获取 OpenServiceCfg对象:[" + openServiceCfgMap + "]");

		if (openServiceCfgMap == null) {
			CommonTools.addError(ConstsCore.ERROR_FAIL, "找不到服务！");
		}

		// 判断是否已经存在rpcType,不存在则取配置库中的RpcType
		if (StringUtil.isEmpty(rpcType)) {
			rpcType = Const.getStrValue(openServiceCfgMap, "rpc_type");
		}
		if (StringUtil.isEmpty(rpcType)) {
			CommonTools.addError(ConstsCore.ERROR_FAIL, "服务[RpcType]未配置！");
		}

		// 写入队列表
		OrderQueueBusiRequest orderQueue = saveOrderqueue(req,
				openServiceCfgMap, serviceCode);

		// 写订单主机日志
		ordBindEvnLog(req, orderQueue);

		// 调用模版转换写报文头部信息
		InfHeadBusiRequest infHead = null;
		if (req.getIsTemplateCoversion()) {
			infHead = saveInfHead(orderQueue, req);
		}

		final String co_id = orderQueue.getCo_id();
		resp.setBase_co_id(co_id);
		final String order_id = orderQueue.getOrder_id();
		resp.setRpc_type(rpcType);
		OrderCollectionResp execResp = null;
		logger.info("[OrderCtnService] "
				+ Const.getStrValue(openServiceCfgMap, "remark") + " 队列id:["
				+ co_id + "] 外部订单号:[" + req.getOutOrderId() + "] 调用标准化系统方式为:["
				+ rpcType + "]");
		if (OrderCtnConsts.RPC_TYPE_DUBBO.equalsIgnoreCase(rpcType)) {
			// dubbo调用-订单标准化返回结果.
			execResp = dubboExecute(req, infHead, serviceCode, co_id, order_id,openServiceCfgMap);
			List<OrderCollectionResp> orderCollectList = new ArrayList<OrderCollectionResp>();
			orderCollectList.add(execResp);
			resp.setOrderCollectList(orderCollectList);
			resp.setError_code(execResp.getError_code());
			resp.setError_msg(execResp.getError_msg());
		} else if (OrderCtnConsts.RPC_TYPE_MQ.equalsIgnoreCase(rpcType)) {
			// mq消费 组装报文[订单接收成功]
			execResp = mqExecute(req, infHead, serviceCode, co_id, order_id,
					getHandleSys(),openServiceCfgMap);
			// mq消息返回的时候需要判断订单写入状态
			resp.setError_code(execResp.getError_code());
			resp.setError_msg(execResp.getError_msg());
		} else {
			resp.setError_code(ConstsCore.ERROR_FAIL);
			resp.setError_msg("RpcType:[" + rpcType + "]未知！");
			CommonTools.addError(ConstsCore.ERROR_FAIL, "RpcType:[" + rpcType
					+ "]未知！");
		}
		resp.setOrder_id(execResp.getOrder_id());

		// 组装返回结果
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
	public Map<String, String> getOpenServiceCfgByOutServiceCode(OrderCtnReq req) {
		if (null == dcPublicInfoManager) {
			dcPublicInfoManager = SpringContextHolder
					.getBean("dcPublicInfoManager");
		}
		DcPublicInfoCacheProxy proxy = new DcPublicInfoCacheProxy(
				dcPublicInfoManager);
		return proxy.getOpenServiceCfgByOutServiceCode(req.getOutServiceCode(),
				req.getVersion());
	}

	/**
	 * 
	 * @Description: 获取当前主机环境
	 * @return
	 * @author zhouqiangang
	 * @date 2015年12月15日 下午3:10:19
	 */
	private String getHandleSys() {
		// Map<String,String> envMap =
		// com.ztesoft.common.util.BeanUtils.getCurrHostEnv();
		// if(null != envMap && envMap.containsKey("env_code")){
		// return envMap.get("env_code");
		// }else{
		// CommonTools.addError(ConstsCore.ERROR_FAIL, "主机环境未配置！");
		// return null;
		// }
		// return "ecsord_kaifa";
		return com.ztesoft.common.util.BeanUtils
				.getHostEnvCodeByEnvStatus(ConstsCore.MACHINE_EVN_CODE_ECSORD_STD);

	}

	public InfHeadBusiRequest saveInfHead(OrderQueueBusiRequest orderQueue,
			OrderCtnReq req) {

		InfHeadBusiRequest infHead = new InfHeadBusiRequest();
		// 通过模版转换入库头部信息
		String inf_head_id = SequenceTools.getdefualt22PrimaryKey();
		String order_id = orderQueue.getOrder_id();

		TemplateAccessConvertReq tmpCovReq = new TemplateAccessConvertReq();
		Map<String, Map<String, Object>> busiParamMap = new HashMap<String, Map<String, Object>>();
		Map<String, Object> valMap = new HashMap<String, Object>();
		valMap.put("co_id", orderQueue.getCo_id());
		valMap.put("inf_head_id", inf_head_id);
		busiParamMap.put("InfHeadBusiRequest", valMap);
		tmpCovReq.setBusiParamMap(busiParamMap);
		tmpCovReq.setInMap(req.getReqParamsMap());
		tmpCovReq.setOrder_id(order_id);
		tmpCovReq.setTpl_code(OrderCtnConsts.TEMPLATE_CONVERT_HEAD_CODE);
		tmpCovReq.setTpl_ver(OrderCtnConsts.TEMPLATE_CONVERT_CODE_VERSION);
		tmpCovReq.setTpl_inout_type(OrderCtnConsts.ORDER_TEMPLATE_TYPE_IN);

		// dubbo
		// ZteClient client =
		// ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		// TemplateAccessConvertResp tempResp = client.execute(tmpCovReq,
		// TemplateAccessConvertResp.class);

		// rest
		// String reqJsonStr = JSONObject.fromObject(tmpCovReq).toString();
		// TemplateAccessConvertResp tempResp = null;
		// try {
		// String tempRespStr =
		// HttpUtil.send("http://10.45.54.4:8090/restservices/infoServices/tplAccessConvert",
		// reqJsonStr, "application/json", "UTF-8", "UTF-8", 10000, 10000);
		// } catch (IOException e) {
		// e.printStackTrace();
		// }

		// rop
		/**
		 * 1.需要配置：select * from pm_app; ----app_key必须为： wssmall
		 * 2、配环境变量：-DREMOTE_ROP_ADDRESS=http://10.45.54.87:8080/router
		 * 3.如果没配置jvm参数 则走数据库配置. 如果数据库也没配置地址则报错处理 值为业务系统（如重庆电信订单中心）应用的ip、端口
		 */
		String url = System.getProperty("REMOTE_ROP_ADDRESS");
		if (StringUtil.isEmpty(url)) {
			url = getConfigInfoValueByCfId(OrderCtnConsts.TEMPLATE_REMOTE_ROP_ADDRESS);
		}
		if (StringUtil.isEmpty(url)) {
			CommonTools.addError(ConstsCore.ERROR_FAIL, "模版转换服务未配置！");
		}
		// String url = "http://10.45.54.3:8090/router";
		AppKeyEnum appKeyEnum = AppKeyEnum.APP_KEY_WSSMALL_ESC;// ClientFactory.getAppKeyEnum(ManagerUtils.getSourceFrom());
		ZteClient client = new DefaultZteRopClient(url, appKeyEnum.getAppKey(),
				appKeyEnum.getAppSec(), "1.0");
		TemplateAccessConvertResp tempResp = client.execute(tmpCovReq,
				TemplateAccessConvertResp.class);
		if (!InfoConsts.API_RET_SUCC_FLAG.equals(tempResp.getError_code())) {
			CommonNTools.addFailError("模版转化报文头部信息失败！");
		}

		List<Map<String, Map<String, String>>> list = (List<Map<String, Map<String, String>>>) tempResp
				.getOutObjMap().get("es_inf_head");
		for (Map<String, Map<String, String>> tmpMap : list) {
			for (Map.Entry<String, Map<String, String>> innerTmpMap : tmpMap
					.entrySet()) {
				RequestBeanDefinition beanDef = DefaultActionRequestDefine
						.getInstance().getActionRequestTableNameMap(
								innerTmpMap.getKey());
				if (null != beanDef) {
					ZteBusiRequest beanInst = (ZteBusiRequest) BeanUtils
							.instantiateClass(beanDef.getBeanClass());
					try {
						innerTmpMap.getValue().put("co_id",
								orderQueue.getCo_id());
						innerTmpMap.getValue().put("inf_head_id", inf_head_id);
						innerTmpMap.getValue().put("order_id", order_id);
						commons.BeanUtils.mapToBean(innerTmpMap.getValue(),
								beanInst);
					} catch (Exception e) {
						e.printStackTrace();
					}
					beanInst.setDb_action(ConstsCore.DB_ACTION_INSERT);
					beanInst.setIs_dirty(ConstsCore.IS_DIRTY_YES);
					infHead = (InfHeadBusiRequest) beanInst;
					try {
						infHead.store();
					} catch (Exception e) {
						e.printStackTrace();
						CommonNTools.addFailError("报文头部信息入库失败！");
					}
				}
			}

		}
		// 头部信息写入缓存.
		INetCache cache = com.ztesoft.net.cache.common.CacheFactory
				.getCacheByType("");
		if (null != cache) {
			cache.set(EcsOrderConsts.ORDER_QUEUE_NAMESPACE,
					OrderQueueConsts.INF_HEAD_CACHE_KEY + infHead.getCo_id(),
					infHead, OrderQueueConsts.CACHE_KEEP_TIME);
		}
		return infHead;
	}

	/**
	 * 
	 * @Description: 保存队列数据
	 * @param req
	 * @param openCfgMap
	 * @param serviceCode
	 * @return
	 * @author zhouqiangang
	 * @date 2015年12月16日 上午11:47:52
	 */
	private OrderQueueBusiRequest saveOrderqueue(OrderCtnReq req,
			Map<String, String> openCfgMap, String serviceCode) {
		String order_id = SequenceTools.getdefualt22PrimaryKey();
		String co_id = SequenceTools.getdefualt22PrimaryKey();
		OrderQueueBusiRequest orderQueue = new OrderQueueBusiRequest();
		orderQueue.setCo_id(co_id);
		orderQueue.setService_code(serviceCode);
		orderQueue.setAction_code(Const.getStrValue(openCfgMap, "action_code"));
		orderQueue.setContents(req.getReqMsgStr());
		orderQueue.setStatus(OrderCtnConsts.CO_QUEUE_STATUS_WFS);
		orderQueue.setStatus_date(OrderCtnConsts.DB_ORACLE_CURRENT_TIMER);
		orderQueue.setCreated_date(OrderCtnConsts.DB_ORACLE_CURRENT_TIMER);
		orderQueue.setSource_from(ManagerUtils.getSourceFrom());
		orderQueue.setService_version(Const.getStrValue(openCfgMap, "version"));
		orderQueue.setObject_id(req.getOutOrderId());
		orderQueue.setQueue_type(OrderQueueConsts.QUEUE_TYPE_CTN);
		// 模版转换设置order_id. 新商城/总商/淘宝 不设置order_id
		if (req.getIsTemplateCoversion()) {
			orderQueue.setOrder_id(order_id);
		}
		orderQueue.setType(req.getFormat());
		orderQueue.setHandle_sys(getHandleSys());
		orderQueue.setWork_state(OrderCtnConsts.CO_QUEUE_WORK_STATE_0);
		orderQueue.setBusi_type(OrderCtnConsts.CO_QUEUE_BUSI_TYPE_ADD);
		orderQueue.setDeal_contents(req.getParams());
		// 增加是否写队列表判断
		if ("1".equals(openCfgMap.get("write_queue_flag"))) {
			try {
				if (null == orderQueueBaseManager) {
					orderQueueBaseManager = SpringContextHolder
							.getBean("orderQueueBaseManager");
				}
				orderQueue = orderQueueBaseManager.saveOrderQueue(orderQueue);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return orderQueue;
	}

	/**
	 * 执行MQ发送消息
	 */
	@SuppressWarnings("unchecked")
	public OrderCollectionResp mqExecute(OrderCtnReq req,
			InfHeadBusiRequest infHead, String service_code, String co_id,
			String order_id, String topic,Map openServiceCfgMap) throws Exception {
		// 1、接收参数
		DefaultServiceContext context = DefaultServiceContext.getInstance();
		ServiceMethodHandler serviceMethodHandler = context
				.getServiceMethodHandler(service_code, version);
		Class zteReqClass = serviceMethodHandler.getRequestType();// 得到dubbo服务入参
		Class<ZteResponse> zteResClass = (Class<ZteResponse>) serviceMethodHandler
				.getRespType();// 得到dubbo服务出参

		// 2、请求对象生成
		Object[] objs = null;
		ZteRequest<ZteResponse> zteRequest = (ZteRequest<ZteResponse>) ConstructorUtils
				.invokeConstructor(zteReqClass, objs);

		// 3、封装参数
		zteRequest.setBase_co_id(co_id);
		zteRequest.setBase_order_id(order_id);
		zteRequest.setFormat(req.getFormat());
		zteRequest.setMqTopic(topic);
		// 如果是模版转换则设置模版编码/版本
		if (req.getIsTemplateCoversion()) {
			com.ztesoft.common.util.BeanUtils.setProperty(zteRequest,
					"template_code", infHead.getTemplate_code());
			com.ztesoft.common.util.BeanUtils.setProperty(zteRequest,
					"template_version", infHead.getTemplate_version());
		}
		// 淘宝订单，需要传入淘宝参数字符串
		if (OrderCtnConsts.OUT_SERVICE_CODE_TAOBAOMALLORDERSTANDARD.equals(req
				.getOutServiceCode())
				|| OrderCtnConsts.OUT_SERVICE_CODE_TAOBAOTJORDERSTANDARD
						.equals(req.getOutServiceCode())) {
			com.ztesoft.common.util.BeanUtils.setProperty(zteRequest, "params",
					req.getParams());
			com.ztesoft.common.util.BeanUtils.setProperty(zteRequest,
					"taobaoOrderId", req.getOutOrderId());
			// ----暂时增量方法------以后需完善参数传入
			com.ztesoft.common.util.BeanUtils.setProperty(zteRequest,
					"dyn_field", req.getDyn_field());
			// ----暂时增量方法------以后需完善参数传入
		}

		com.ztesoft.common.util.BeanUtils.setProperty(zteRequest, "out_id",
				req.getOutOrderId());
		if("1".equals(openServiceCfgMap.get("write_queue_flag"))){//增加是否写队列表标识
			com.ztesoft.common.util.BeanUtils.setProperty(zteRequest, "req_content",
					req.getReqMsgStr());
		}
		// 4、MQ调用(生产消息)
		ZteClient client = ClientFactory.getZteMqClient(ManagerUtils
				.getSourceFrom());// mq客户端
		OrderCollectionResp resp = (OrderCollectionResp) client.execute(
				zteRequest, zteResClass);
		if (null == resp || !ConstsCore.ERROR_SUCC.equals(resp.getError_code())) {
			// 写失败记录
			String deal_desc = "生产MQ消息失败！";
			String error_stack_msg = "";
			if (resp != null && resp.getError_stack_msg() != null
					&& !"".equals(resp.getError_stack_msg())) {
				error_stack_msg = resp.getError_stack_msg();
				if (error_stack_msg.length() > 1000) {
					error_stack_msg = error_stack_msg.substring(0, 1000);
				}
			}
			deal_desc += error_stack_msg;
			if("1".equals(openServiceCfgMap.get("write_queue_flag"))){//增加是否写队列表标识
				CommonDataFactory.getInstance().saveOrderQueueToFail(order_id,
						co_id, deal_desc);
			}
			// 处理mq消息统一返回成功
			if (null == resp) {
				resp = new OrderCollectionResp();
			}
			resp.setError_code(ConstsCore.ERROR_SUCC);
		}
		logger.info("[OrderCtnService] 执行MQ调用返回对象: " + resp);
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
	private OrderCollectionResp dubboExecute(OrderCtnReq req,
			InfHeadBusiRequest infHead, String service_code, String co_id,
			String order_id,Map openServiceCfgMap) throws Exception {

		DefaultServiceContext context = DefaultServiceContext.getInstance();
		ServiceMethodHandler serviceMethodHandler = context
				.getServiceMethodHandler(service_code, version);
		// 生成请求对象
		Class zteReqClass = serviceMethodHandler.getRequestType();
		Object[] objs = null;
		ZteRequest zteRequest = (ZteRequest<ZteResponse>) ConstructorUtils
				.invokeConstructor(zteReqClass, objs);
		zteRequest.setBase_co_id(co_id);
		zteRequest.setBase_order_id(order_id);
		// 如果是模版转换则设置模版编码/版本
		if (req.getIsTemplateCoversion()) {
			com.ztesoft.common.util.BeanUtils.setProperty(zteRequest,
					"template_code", infHead.getTemplate_code());
			com.ztesoft.common.util.BeanUtils.setProperty(zteRequest,
					"template_version", infHead.getTemplate_version());
		}
		// 淘宝订单，需要传入淘宝参数字符串
		if (OrderCtnConsts.OUT_SERVICE_CODE_TAOBAOMALLORDERSTANDARD.equals(req
				.getOutServiceCode())
				|| OrderCtnConsts.OUT_SERVICE_CODE_TAOBAOTJORDERSTANDARD
						.equals(req.getOutServiceCode())) {
			com.ztesoft.common.util.BeanUtils.setProperty(zteRequest, "params",
					req.getParams());
			com.ztesoft.common.util.BeanUtils.setProperty(zteRequest,
					"taobaoOrderId", req.getOutOrderId());
			// ----暂时增量方法------以后需完善参数传入
			com.ztesoft.common.util.BeanUtils.setProperty(zteRequest,
					"dyn_field", req.getDyn_field());
			// ----暂时增量方法------以后需完善参数传入
		}

		if(zteRequest instanceof MobileBusiCtnStandardReq){
			MobileBusiCtnStandardReq temp = (MobileBusiCtnStandardReq)zteRequest;
			temp.setOrder_from(req.getReqParamsMap().get("orderSource").toString());
		}
		if(zteRequest instanceof NewMallOrderStandardReq){
			NewMallOrderStandardReq temp = (NewMallOrderStandardReq)zteRequest;
			temp.setOrder_from(req.getReqParamsMap().get("orderSource").toString());
		}
		if(zteRequest instanceof InternetGroupOrderStandardReq){
		    InternetGroupOrderStandardReq temp = (InternetGroupOrderStandardReq)zteRequest;
            temp.setOrder_from(req.getReqParamsMap().get("orderSource").toString());
        }
		if(zteRequest instanceof GroupOrderStandardReq){
		    GroupOrderStandardReq temp = (GroupOrderStandardReq)zteRequest;
            temp.setOrder_from(req.getReqParamsMap().get("orderSource").toString());
        }
		
		if(zteRequest instanceof FixBusiOrderStandardReq){
			FixBusiOrderStandardReq temp = (FixBusiOrderStandardReq)zteRequest;
			temp.setOrder_from(req.getReqParamsMap().get("orderSource").toString());
		}
		if(zteRequest instanceof CodePurchaseMallOrderStandardReq){
		    CodePurchaseMallOrderStandardReq temp = (CodePurchaseMallOrderStandardReq)zteRequest;
            temp.setOrder_from(req.getReqParamsMap().get("orderSource").toString());
        }
		
		com.ztesoft.common.util.BeanUtils.setProperty(zteRequest, "out_id",
				req.getOutOrderId());
		if(!"1".equals(openServiceCfgMap.get("write_queue_flag"))){//增加是否写队列表标识
			com.ztesoft.common.util.BeanUtils.setProperty(zteRequest, "req_content",
					req.getReqMsgStr());
		}
		

		zteRequest.setFormat(req.getFormat());
		// dubbo调用
		OrderCollectionResp zteResponse = null;
		try {
			zteResponse = (OrderCollectionResp) serviceMethodHandler
					.getHandlerMethod().invoke(
							serviceMethodHandler.getHandler(), zteRequest);
		} catch (Exception e) {
			if (null == zteResponse) {
				zteResponse = new OrderCollectionResp();
				zteResponse.setError_code(ConstsCore.ERROR_FAIL);
				zteResponse.setError_msg("Dubbo调用失败！");
			}
			e.printStackTrace();
		} finally {
			if("1".equals(openServiceCfgMap.get("write_queue_flag"))){//增加是否写队列表标识
				if (null == orderQueueBaseManager) {
					orderQueueBaseManager = SpringContextHolder
							.getBean("orderQueueBaseManager");
				}
				OrderQueueBusiRequest orderQueue = orderQueueBaseManager
						.getOrderQueue(order_id, co_id);
				if (null != orderQueue) {
					if (null != zteResponse
							&& ConstsCore.ERROR_SUCC.equals(zteResponse
									.getError_code())) {
						orderQueue.setDeal_code(OrderQueueConsts.DEAL_SUCCEED);
						orderQueue.setDeal_desc("Dubbo调用成功！");
					} else {
						String deal_desc = "Dubbo调用失败！";
						if (null != zteResponse) {
							deal_desc = zteResponse.getError_msg();
						}
						orderQueue.setDeal_code(OrderQueueConsts.DEAL_FAIL);
						orderQueue.setDeal_desc(deal_desc);
					}
					orderQueueBaseManager.saveOrderQueueToHis(orderQueue);
				}
			}
		}

		logger.info("[OrderCtnService] 执行dubbo调用返回对象: " + zteResponse);
		return zteResponse;
	}

	/**
	 * 根据数据库配置的路径从报文Map对象解析对外的接口编码
	 * 
	 * @Description:
	 * @param @param map 报文Map对象
	 * @param @return
	 * @return String 接口编码
	 * @throws
	 */
	public String getOutServiceCode(Map<String, Object> map) {
		String out_service_code = null;
		try {
			// 从报文获取接口编码
			ICacheUtil cacheUtil1 = SpringContextHolder.getBean("cacheUtil");
			String service_code_paths = cacheUtil1
					.getConfigInfo(OrderCtnConsts.SERVICE_CODE_PATH);
			String[] service_code_path_array = service_code_paths.split("/");
			for (int i = 0; i < service_code_path_array.length; i++) {
				if (service_code_path_array.length - 1 != i) {
					map = (Map<String, Object>) map
							.get(service_code_path_array[i]);
				} else {
					out_service_code = (String) map
							.get(service_code_path_array[i]);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return out_service_code;
	}

	/**
	 * 
	 * @Description: 获取模版转换rop服务器地址
	 * @param cfId
	 * @return
	 * @author zhouqiangang
	 * @date 2015年12月4日 上午11:06:11
	 */
	public String getConfigInfoValueByCfId(String cfId) {
		CacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		return cacheUtil.getConfigInfo(cfId);
	}

	/**
	 * 
	 * @Description: 订单流转主机日志写入
	 * @param req
	 * @param orderQueue
	 * @author zhouqiangang
	 * @date 2015年12月16日 上午11:46:20
	 */
	public void ordBindEvnLog(OrderCtnReq req, OrderQueueBusiRequest orderQueue) {
		String is_run_abgray = "0";// 默认为0，取消灰度环境使用
		is_run_abgray = cacheUtil.getConfigInfo(EcsOrderConsts.IS_RUN_ABGRAY);// 是否取消前置校验
																				// 0：关闭
																				// 1：开启
		if (StringUtils.isNotBlank(is_run_abgray) && "1".equals(is_run_abgray)) {
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("source_from", ManagerUtils.getSourceFrom());
			String orderId = null == orderQueue.getOrder_id() ? "0000000000"
					: orderQueue.getOrder_id();
			paramMap.put("order_id", orderId);
			paramMap.put("op_code",
					OrderCtnConsts.ABGRAY_ORD_ENV_REL_LOG_OPCODE);
			paramMap.put("out_tid", req.getOutOrderId());
			paramMap.put("zb_inf_id", "");
			com.ztesoft.common.util.BeanUtils.ordBindEvnLog((HashMap) paramMap);
		}
	}

	@Override
	public HSOrderCtnResp hsOrderCtn(HSOrderCtnReq req) throws Exception {
		HSMallServer hsMall = new HSMallServer();
		HSOrderCtnResp resp = hsMall.hsOrderStand(req.getReqMsgStr());
		return resp;
	}

	@Override
	public HSOrderCtnB2BResp hsOrderB2BCtn(HSOrderCtnB2BReq req)
			throws Exception {
		HSMallServer hsMall = new HSMallServer();
		HSOrderCtnB2BResp resp = hsMall.hsOrderB2BStand(req.getReqMsgStr());
		return resp;
	}

	@Override
	public TbTianjiTestResp testTbTianji(TbTianjiTestReq req) {
		IOutterECSTmplCtnManager outterECSTmplCtnManager = SpringContextHolder
				.getBean("outterECSTmplCtnManager");
		List<OutterTmpl> list = outterECSTmplCtnManager.listOuterTmpl(0);
		logger.info("[OuterInfCtnTimerService] 淘宝抓单定时任务开始执行,获取淘宝请求参数个数"
				+ list.size());
		for (OutterTmpl tmpl : list) {
			try {
				logger.info("[OuterInfCtnTimerService] 淘宝抓单定时任务开始执行,当前执行参数:"
						+ IJSONUtil.beanToJson(tmpl));
				outterECSTmplCtnManager.updateRunStatus("00R",
						tmpl.getTmpl_id());
				OrderSyncThread ot = new OrderSyncThread(tmpl);
				ThreadExecuter.executeOrderTask(ot);
			} catch (Exception ex) {
				ex.printStackTrace();
				outterECSTmplCtnManager.updateRunStatus("00A",
						tmpl.getTmpl_id());
				continue;
			}
		}
		return null;
	}

	@Override
	public WorkflowMatchRsp doWorkflowMatch(CtnWorkflowMatchReq req)
			throws Exception {
		StdWorkflowMatchReq matchReq = new StdWorkflowMatchReq();
		
		matchReq.setOrderIntent(req.getOrderIntent());
		matchReq.setCfg_type(req.getCfg_type());
		
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		WorkflowMatchRsp matchRsp = client.execute(matchReq, WorkflowMatchRsp.class);
		
		return matchRsp;
	}

	@Override
	public StartWorkflowRsp startWorkflow(CtnStartWorkflowReq req)
			throws Exception {
		StdStartWorkflowReq flowReq = new StdStartWorkflowReq();
		
		flowReq.setOrderIntent(req.getOrderIntent());
		flowReq.setCfg(req.getCfg());
        
        ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
        StartWorkflowRsp flowRsp = client.execute(flowReq, StartWorkflowRsp.class);
        
        return flowRsp;
	}

    @Override
    public ChannelConvertQrySubResp channelConvertCtn( ChannelConvertQryCtnReq req) throws Exception {
        StdChannelConvertReq stdChannelConvertReq = new StdChannelConvertReq();
        stdChannelConvertReq.setOp_type(req.getOp_type());
        stdChannelConvertReq.setOp_value(req.getOp_value());
        ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
        ChannelConvertQrySubResp resp = client.execute(stdChannelConvertReq, ChannelConvertQrySubResp.class);
        return resp;
    }

}
