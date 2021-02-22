package com.ztesoft.api.mq;

import org.apache.log4j.Logger;

import params.ZteRequest;
import params.ZteResponse;
import utils.CoreThreadLocalHolder;
import zte.net.iservice.IOrderExpServices;
import zte.net.iservice.IOrderPlanService;
import zte.net.iservice.IOrderstdService;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.api.consts.ApiConsts;
import com.ztesoft.common.util.JsonUtil;
import com.ztesoft.mq.client.EventMessageExt;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import commons.CommonTools;

import context.WebContext;
import context.WebContextFactory;

/**
 * @author zhangJun
 * 
 * @date 2014-9-16
 */
public class ExcuteReceiveMsg {

	private static Logger logger = Logger.getLogger(ExcuteReceiveMsg.class);

	private boolean needCheckRequest = true;
	
	private IOrderstdService orderstdService;
	
	private IOrderPlanService orderPlanService;
	
	private IOrderExpServices orderExpServices;

	/**
	 * 把消息转换为ZteRequest对象并通过DUBBO方式调用API
	 * 
	 * @param msg
	 */

	public ZteResponse executeDubbo(ZteRequest request) {
		ZteResponse resp = null;
		if (this.needCheckRequest) {
			try {
				request.check();// if check failed,will throw ApiRuleException.
			} catch (ApiRuleException e) {
				e.printStackTrace();
				return null;
			}
		}
		WebContext ctx = WebContextFactory.getInstance(WebContextFactory.DUBBO_WEB);
		try {
			 ZteClient client =
			 ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());//验证作用
			 request.setRemote_type(ApiConsts.ZTE_CLIENT_DUBBO);
			 ctx.initContext(request);
			 ctx.execContext(request);
			 resp= CommonTools.getZteResponse();
//			if (request instanceof NewMallOrderStandardReq) {
//				if(null == orderstdService) orderstdService = SpringContextHolder.getBean("orderstdService");
//				resp = orderstdService.newMallOrderStandard((NewMallOrderStandardReq) request);
//			} else if (request instanceof CenterMallOrderStandardReq) {
//				if(null == orderstdService) orderstdService = SpringContextHolder.getBean("orderstdService");
//				resp = orderstdService.centerMallOrderStandard((CenterMallOrderStandardReq) request);
//			} else if (request instanceof TaoBaoMallOrderStandardReq) {
//				if(null == orderstdService) orderstdService = SpringContextHolder.getBean("orderstdService");
//				resp = orderstdService.taoBaoMallOrderStandard((TaoBaoMallOrderStandardReq) request);
//			} else if (request instanceof TemplatesOrderStandardReq) {
//				if(null == orderstdService) orderstdService = SpringContextHolder.getBean("orderstdService");
//				resp = orderstdService.templatesOrderStandard((TemplatesOrderStandardReq) request);
//			}else if (request instanceof StartOrderPlanReq) {
//				if (null == orderPlanService) orderPlanService = ApiContextHolder.getBean("orderPlanService");
//				resp = orderPlanService.startOrderStandingPlan((StartOrderPlanReq) request);
//			}else if (request instanceof OrderExpWriteForInfReq) {
//				if (null == orderExpServices) orderExpServices = ApiContextHolder.getBean("orderExpServices");
//				resp = orderExpServices.exeOrderExpWrite((OrderExpWriteForInfReq) request);
//			}else if (request instanceof OrderExpWriteForBusReq) {
//				if (null == orderExpServices) orderExpServices = ApiContextHolder.getBean("orderExpServices");
//				resp = orderExpServices.exeOrderExpWrite((OrderExpWriteForBusReq) request);
//			}else if (request instanceof OrderExpMarkProcessedReq) {
//				if (null == orderExpServices) orderExpServices = ApiContextHolder.getBean("orderExpServices");
//				resp = orderExpServices.orderExpMarkProcessed((OrderExpMarkProcessedReq) request);
//			}			else{
//				CommonTools.addError(ConstsCore.ERROR_FAIL, "MQ消费失败！请求对象:"+IJSONUtil.beanToJson(request));
//			}
			logger.info("[MQ消费者] 执行Dubbo调用返回对象: " + JsonUtil.toJson(resp));
		} catch (RuntimeException e) {
			e.printStackTrace();
		} finally {
//			ctx.destoryContext(request);
			CoreThreadLocalHolder.getInstance().getZteCommonData().removeAppKey();
		}
		return resp;

	}

	/**
	 * 保存日志
	 * 
	 * @param msg
	 */
	private void saveReceiveLog(EventMessageExt msg) {
		try {
			ZteRequest request = null;
			if (msg.getDeSerializeData() != null) {
				request = (ZteRequest) msg.getDeSerializeData();
				if (request != null) {
					// String
					// JsonText=CommonTools.beanToJsonFilterNull(request);
					// MsgReceiveRecord msgReceive=new MsgReceiveRecord();
					// msgReceive.setAction_name(msg.getAction());
					// msgReceive.setMsg_key(msg.getKey());
					// msgReceive.setReceive_obj(JsonText);
					// msgReceive.setTopic(msg.getTopic());
					// msgReceive.setSource_from(ManagerUtils.getSourceFrom());
					//
					// ZteVoInsertRequest zte=new
					// ZteVoInsertRequest();//ZteVoInsertRequest 与sdk里面的保持一致
					// zte.setTable_name("es_msg_receive_record");
					// zte.setSerObject(msgReceive);
					// ZteClient client =
					// ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
					// ZteVoInsertResponse
					// zviResp=client.execute(zte,ZteVoInsertResponse.class);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
