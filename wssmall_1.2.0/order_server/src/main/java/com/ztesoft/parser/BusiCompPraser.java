package com.ztesoft.parser;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import oracle.net.aso.e;
import params.ZteError;
import params.ZteRequest;
import params.ZteResponse;
import params.orderqueue.req.AsynExecuteMsgWriteMqReq;
import params.req.OrderExpWriteForBusReq;
import params.resp.OrderExpWriteResp;
import utils.CoreThreadLocalHolder;
import zte.net.common.annontion.context.action.DefaultActionBeanDefine;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.base.req.BusiCompRequest;
import zte.net.ecsord.params.base.resp.BusiCompResponse;
import zte.net.iservice.IOrderQueueBaseManager;
import zte.net.iservice.IRuleService;

import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ThreadPoolFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.ibss.common.util.StringUtils;
import com.ztesoft.net.eop.sdk.context.MqEnvGroupConfigSetting;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.rop.common.ServiceMethodHandler;
import commons.CommonTools;
import consts.ConstsCore;


/**
 * 
 * @author wu.i
 * 
 * 流程环节、动作、操作入口
 * 
 * 调用入口
 */
public class BusiCompPraser{
	private static Logger logger = Logger.getLogger(BusiCompPraser.class);
	private IRuleService ruleService;

	private void init(){
		ruleService= SpringContextHolder.getBean("ruleService");
    }

	
	/**
	 * 启动业务业务组件
	 * @param prequest
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws e 
	 */
	public static BusiCompResponse  performBusiComp(BusiCompRequest prequest) throws Exception {
		//组装参数
		Map params = prequest.getQueryParams();
		String enginePath =prequest.getEnginePath();
		DefaultActionBeanDefine context = DefaultActionBeanDefine.getInstance();
		String methodName = enginePath,version="1.0";
		BusiCompResponse resp = new BusiCompResponse();

		try{
			CoreThreadLocalHolder.getInstance().getZteCommonData().setZteError(null);
			if (context.isValidMethodVersion(enginePath, version)) {
				ServiceMethodHandler serviceMethodHandler = context.getServiceMethodHandler(enginePath, version);
				long start = System.currentTimeMillis();
				resp =  (BusiCompResponse) serviceMethodHandler.getHandlerMethod().invoke(serviceMethodHandler.getHandler(),prequest); //todo
				long end = System.currentTimeMillis();
				if(resp!=null&&EcsOrderConsts.IS_WRITE_EXCEPTION_1.equals(resp.getIs_write_exception())){
					try{//写入异常单
						exeExceptionOrd(prequest.getOrder_id(),resp.getError_msg());
					}catch(Exception writeExp){
							writeExp.printStackTrace();
				    }
				}

				return resp;
			}
			//找不到服务去除,避免报错
			else {
				resp.setError_code(ConstsCore.ERROR_FAIL);
				resp.setError_msg(enginePath+"找不到服务");
				CommonTools.addError("-1", "找不到服务");

			}
		}catch(Exception e){
			resp.setError_code(ConstsCore.ERROR_FAIL);
			String msg =e.getMessage();
			logger.info("order_id:" + prequest.getOrder_id() + ";error_msg:" + msg);
			e.printStackTrace();
			ZteError et =  CoreThreadLocalHolder.getInstance().getZteCommonData().getZteError();
			if(et!=null){
				resp.setError_msg(et.getError_msg());
				//prequest.getOrder_id()
				if(EcsOrderConsts.IS_WRITE_EXCEPTION_1.equals(et.getIs_write_exception())){
					try{//写入异常单
					exeExceptionOrd(prequest.getOrder_id(),et.getError_msg());
					}catch(Exception writeExp){
						writeExp.printStackTrace();
					}
				}
			}else{
				
				 if (e instanceof InvocationTargetException) {
		             InvocationTargetException inve = (InvocationTargetException) e;
		             resp.setError_msg(inve.getTargetException().getMessage());
//		             CommonTools.addBusiError(new ZteError(ApiConsts.ERROR_FAIL,inve.getTargetException().getMessage()));
		         }
		    }
			
//			String plan_id = params.get("plan_id") == null ? "" : params.get("plan_id").toString();
//			Plan plan = params.get("plan") == null ? null : (Plan)params.get("plan");
//			boolean flag = false;
//			if (!"zteCommonTraceRule.notifyWYG".equals(enginePath)) flag = true; else flag = false;
//			if (flag && !"zteCustomVisitTraceRule.preCheckFromBSS".equals(enginePath)) flag = true; else flag = false;
//			if (flag && !"zteCustomVisitTraceRule.preCheckFromAop".equals(enginePath)) flag = true; else flag = false; // 因为这个组件里面是调《通用规则》中的《AOP订单预校验》方案,所以不用通知新商城错误,《AOP订单预校验》方案中的规则会通知新商城错误,避免通知两次
//			if (flag && !EcsOrderConsts.ORDER_ARCHIVE_PLAN.equals(plan_id)) flag = true; else flag = false;
//			if (flag && plan != null) flag = true; else flag = false;
//			if (flag && !EcsOrderConsts.ORDER_ORDER_MANAGER_MATCH_DIR.equals(plan.getCatalogue_id())) flag = true; else flag = false;
//			if (flag && (!EcsOrderConsts.ORDER_COMMON_RULE_MATCH_DIR.equals(plan.getCatalogue_id()) 
//							|| EcsOrderConsts.WORDER__PRE_DEAL_PLAN.equals(plan_id) 
//							|| EcsOrderConsts.ORDER_PRE_VALIDATE_AOP.equals(plan_id)
//							|| EcsOrderConsts.ORDER_PRE_VALIDATE_BSS.equals(plan_id))) flag = true; else flag = false;
//			
//			if (flag) {
//				if (!StringUtil.isEmpty(prequest.getOrder_id())) {
//					OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(prequest.getOrder_id());
//					String is_aop  = orderTree.getOrderExtBusiRequest().getIs_aop();
//					String system = "";
//					if(EcsOrderConsts.ACCOUNT_OPEN_CHANNEL_AOP.equals(is_aop)){
//						system = EcsOrderConsts.NOTIFY_WYG_PROD_PEER_AOP;
//					}else if(EcsOrderConsts.ACCOUNT_OPEN_CHANNEL_BSS.equals(is_aop)){
//						system = EcsOrderConsts.NOTIFY_WYG_PROD_PEER_BSS;
//					}else{
//						system = EcsOrderConsts.NOTIFY_WYG_PROD_PEER_ZB;
//					}
//					HashMap para = new HashMap();
//					para.put("order_id", prequest.getOrder_id());
//					para.put("error_msg", resp.getError_msg());
//					if(EcsOrderConsts.ORDER_OPEN_AUDIT_RULE_PATH.equals(enginePath)){
//						//开户审核规则执行失败需要修改通知给商城的环节信息,在map中加个特殊标记用于区分
//						para.put(EcsOrderConsts.ORDER_OPEN_AUDIT_RULE_PATH, "1");
//					}
//					CommonDataFactory.getInstance().notifyBusiException(para);
//				}
//			}
			if("ZteActTraceRule.nextStep".equals(enginePath) || 
					"ZteWriteCardTraceRule.nextStep".equals(enginePath) || 
					"ZteOrderPickingTraceRule.nextStep".equals(enginePath)){
				if (!StringUtil.isEmpty(prequest.getOrder_id())) {
					String order_model = CommonDataFactory.getInstance().getAttrFieldValue(prequest.getOrder_id(), AttrConsts.ORDER_MODEL);
					boolean is_excep_flag = false;
					if(StringUtils.equals(EcsOrderConsts.OPER_MODE_ZD, order_model)
							|| StringUtils.equals(EcsOrderConsts.OPER_MODE_XK, order_model)){
						is_excep_flag = true;
					}else if(StringUtils.equals(EcsOrderConsts.OPER_MODE_XK, order_model) 
							&& !"ZteOrderPickingTraceRule.nextStep".equals(enginePath)){
						//集中写卡模式下一步标记异常
						is_excep_flag = true;
					}
					if(is_excep_flag){
						Map paramsExcp = new HashMap();
						BusiCompRequest bcr = new BusiCompRequest();
						bcr.setEnginePath("zteCommonTraceRule.markedException");
						bcr.setOrder_id(prequest.getOrder_id());
						paramsExcp.put(EcsOrderConsts.EXCEPTION_FROM, EcsOrderConsts.EXCEPTION_FROM_ORD);
						paramsExcp.put(EcsOrderConsts.EXCEPTION_REMARK, "下一步执行失败标记异常");
						bcr.setQueryParams(paramsExcp);
						CommonDataFactory.getInstance().execBusiComp(bcr);
					}
				}
			}

			return resp;
		}
		return resp;
	}
	public static BusiCompResponse  execOrderStandingPlanBusiComp(BusiCompRequest prequest){
		//组装参数
		String enginePath =prequest.getEnginePath();
		DefaultActionBeanDefine context = DefaultActionBeanDefine.getInstance();
		String version="1.0";
		BusiCompResponse resp = new BusiCompResponse();

		try{
			CoreThreadLocalHolder.getInstance().getZteCommonData().setZteError(null);
			if (context.isValidMethodVersion(enginePath, version)) {
				ServiceMethodHandler serviceMethodHandler = context.getServiceMethodHandler(enginePath, version);
				resp =  (BusiCompResponse) serviceMethodHandler.getHandlerMethod().invoke(serviceMethodHandler.getHandler(),prequest); //todo
				return resp;
			}else {//找不到服务去除,避免报错
				resp.setError_code(ConstsCore.ERROR_FAIL);
				resp.setError_msg(enginePath+"找不到服务");
				CommonTools.addError("-1", "找不到服务");

			}
		}catch(Exception e){
			resp.setError_code(ConstsCore.ERROR_FAIL);
			e.printStackTrace();
			CommonTools.addError("-1", e.getMessage());
		}
		return resp;
	}
	
	//调用异常单系统执行异常单方案
	public static void exeExceptionOrd(String order_id, String error_msg){
			try{
			final String _order_id = order_id;
			final String _error_msg = error_msg;
			Runnable run = new Runnable() {
				@Override
				public void run() {
					ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
					String is_exception_run = cacheUtil.getConfigInfo(EcsOrderConsts.IS_EXCEPTION_RUN);//是否启用异常系统
					if(!StringUtil.isEmpty(is_exception_run) && new Integer(is_exception_run).intValue() != 0 ){//异常系统启动标识
				   		final OrderExpWriteForBusReq req = new OrderExpWriteForBusReq();
				    	req.setObj_id(_order_id); //队列id
						req.setObj_type("order"); //对象类型（order、order_queue）
						req.setSearch_id(EcsOrderConsts.EXP_ORD_RULE_ID); //搜索id
						req.setSearch_code(EcsOrderConsts.EXP_ORD_RULE_CODE);//搜索编码
						req.setError_msg("规则执行失败异常写入"+_error_msg);
						req.setError_stack_msg(_error_msg);//错误堆栈
						String exe_type = MqEnvGroupConfigSetting.ORD_EXP_EXEC;//调用方式 m/d  m是走mq d是走dubbo
						//消息推送异常单方案
						if(ConstsCore.DECOUPLING_EXEC_D.equals(exe_type)){//dubbo
							Thread thread = new Thread(new Runnable() {
								@Override
								public void run() {
								 	ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
								 	OrderExpWriteResp orderExpWriteResp = client.execute(req, OrderExpWriteResp.class);
								}
							});
							thread.setName("规则执行调用异常单线程");
							thread.start();
						}else{//mq
							ZteResponse mqResp= exeOrdMq(req,ConstsCore.MACHINE_EVN_CODE_ECSORD_EXP);
						}
				}
			}};
			ThreadPoolFactory.getOrderExceThreadPoolExector().execute(run);
			}catch(Exception e){
			    e.printStackTrace();
			}	
		}
		// 正常单系统执行正常单标准化方案
		public static  ZteResponse  exeOrdMq(ZteRequest zteReq,String envCode) {
			ZteResponse resp = null;
			try {
				AsynExecuteMsgWriteMqReq req = new AsynExecuteMsgWriteMqReq();
				req.setService_code(zteReq.getApiMethodName());
				req.setVersion(ConstsCore.DUBBO_DEFAULT_VERSION);
				req.setZteRequest(zteReq);
				req.setConsume_env_code(com.ztesoft.common.util.BeanUtils.getHostEnvCodeByEnvStatus(envCode));
			    
				IOrderQueueBaseManager	orderQueueBaseManager = SpringContextHolder.getBean("orderQueueBaseManager");
				resp = orderQueueBaseManager.asynExecuteMsgWriteMq(req);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			return resp;
		}
}
