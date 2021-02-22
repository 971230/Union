package zte.net.iservice.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Timestamp;

import params.ZteRequest;
import params.ZteResponse;
import params.orderqueue.req.AsynExecuteMsgWriteMqReq;
import params.req.OrderExpWriteForBusReq;
import params.resp.OrderExpWriteResp;
import services.ServiceBase;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.iservice.IEccServices;
import zte.net.iservice.IOrderQueueBaseManager;
import zte.params.req.CheckFunReq;
import zte.params.req.CheckReq;
import zte.params.resp.CheckFunResp;
import zte.params.resp.CheckResp;

import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.check.common.factory.LogDataFatory;
import com.ztesoft.check.model.FunDealLog;
import com.ztesoft.check.service.EccCheckFunHandler;
import com.ztesoft.check.service.EccCheckHandler;
import com.ztesoft.common.util.StringUtils;
import com.ztesoft.net.consts.EccConsts;
import com.ztesoft.net.eop.sdk.context.MqEnvGroupConfigSetting;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.rop.annotation.ServiceMethodBean;

import consts.ConstsCore;

@ServiceMethodBean(version = "1.0")
public class EccServices extends ServiceBase implements IEccServices{
	
	private IOrderQueueBaseManager orderQueueBaseManager;

	@Override
	public CheckResp check(CheckReq req) {
		CheckResp resp = new CheckResp();
		FunDealLog log = new FunDealLog();
		wrapperBLog(log,req);
		resp.setError_code(ConstsCore.ERROR_SUCC);
		resp.setError_msg("接口调用成功！");
		EccCheckHandler handler = new EccCheckHandler();
		try{
			resp = handler.handler(req);
		}catch(Exception e){
			String expMessage =  CommonDataFactory.getInstance().getErrorStactMsg(e);
			resp.setError_code(ConstsCore.ERROR_FAIL);
			resp.setStack_trace_info(expMessage);
			resp.setError_msg("预校验系统未知错误");
		}
		wrapperALog(log,resp);
		LogDataFatory.getInstance().log(log);
		
		
		final String obj_id = req.getObj_id();
		final String obj_type = req.getObj_type(); 
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		
		if(ConstsCore.ERROR_SUCC.equals(resp.getError_code())){//继续走标准化
			resp.setError_code(ConstsCore.ERROR_SUCC);
			resp.setError_msg("校验通过");
		}else{
			resp.setError_code(ConstsCore.ERROR_FAIL);
			if(ConstsCore.TRACE_V.equals(req.getTrace_code()) && EccConsts.EXE_TIME_BEFORE.equals(req.getExe_time())){
				return resp;
			}
			
			String is_exception_run = cacheUtil.getConfigInfo(EcsOrderConsts.IS_EXCEPTION_RUN);//是否启用异常系统
			if(StringUtil.isEmpty(is_exception_run) || new Integer(is_exception_run).intValue() == 0 ){//没启动
				resp.setStack_trace_code(EccConsts.IDENTI_9009);
				resp.setStack_trace_info("异常系统未启动！");
			}else{
				//调用异常系统
				final String stack_trace_info = resp.getStack_trace_info();//错误堆栈信息
				final String error_msg = resp.getError_msg();//抽取关键字
//				Thread t = new Thread(new Runnable(){
//					@Override
//					public void run() {
						OrderExpWriteForBusReq ordExpWReq = new OrderExpWriteForBusReq();
						ordExpWReq.setObj_id(obj_id); //对象id
						ordExpWReq.setObj_type(obj_type); //对象类型（order、order_queue）
						ordExpWReq.setSearch_id(EcsOrderConsts.EXP_STANDARD_CHECK_id); //搜索id
						ordExpWReq.setSearch_code(EcsOrderConsts.EXP_STANDARD_CHECK_CODE);//搜索编码
						ordExpWReq.setError_msg(error_msg);//错误堆栈
						ordExpWReq.setError_stack_msg(stack_trace_info);
						try{
							ZteResponse zteResponse = null;
							if(ConstsCore.DECOUPLING_EXEC_D.equals(MqEnvGroupConfigSetting.ORD_EXP_EXEC)){
								zteResponse = checkProxyWriteExpByDubbo(ordExpWReq);
							}else{
								zteResponse = checkProxyWriteExpByMq(ordExpWReq);
							}
							
//							OrderExpWriteResp resp = c.execute(ordExpWReq, OrderExpWriteResp.class);
							if(ConstsCore.ERROR_SUCC.equals(zteResponse.getError_code())){
								/*rsp.setStack_trace_code(EccConsts.IDENTI_9000);
								rsp.setStack_trace_info("通知异常系统成功！");*/
							}else{
								/*rsp.setStack_trace_code(EccConsts.IDENTI_9001);
								rsp.setStack_trace_info(rsp.getError_msg() + resp.error_msg);*/
							}
						}catch(Exception e){
							String expMessage = getErrorStactMsg(e);
							/*rsp.setStack_trace_code(EccConsts.IDENTI_9001);
							rsp.setStack_trace_info(expMessage);*/
						}
					}
					
//				});
//				t.setName("调用异常系统");
//				t.start();
//			}
		}
		
		
		return resp;
	}

	
	public String getErrorStactMsg(Exception e){
		ByteArrayOutputStream buf = new java.io.ByteArrayOutputStream();
		e.printStackTrace(new java.io.PrintWriter(buf, true));
		String expMessage = buf.toString();
		try {
			buf.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}	
		return expMessage;
	}
	
	private OrderExpWriteResp checkProxyWriteExpByDubbo(OrderExpWriteForBusReq ordExpWReq){
		ZteClient c = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		return  c.execute(ordExpWReq, OrderExpWriteResp.class);
	}
	
	private ZteResponse checkProxyWriteExpByMq(OrderExpWriteForBusReq ordExpWReq){
		if(null == orderQueueBaseManager){
			orderQueueBaseManager = SpringContextHolder.getBean("orderQueueBaseManager");
		}
		AsynExecuteMsgWriteMqReq req = new AsynExecuteMsgWriteMqReq();
		req.setService_code(ordExpWReq.getApiMethodName());
		req.setVersion(ConstsCore.DUBBO_DEFAULT_VERSION);
		req.setZteRequest((ZteRequest)ordExpWReq);
		req.setConsume_env_code(com.ztesoft.common.util.BeanUtils.getHostEnvCodeByEnvStatus(ConstsCore.MACHINE_EVN_CODE_ECSORD_EXP));
		return orderQueueBaseManager.asynExecuteMsgWriteMq(req);
	}
	
	@Override
	public CheckFunResp checkFun(CheckFunReq req) {
		CheckFunResp resp = new CheckFunResp();
		resp.setError_code(ConstsCore.ERROR_SUCC);
		resp.setError_msg("接口调用成功！");
		EccCheckFunHandler handler = new EccCheckFunHandler();
		try{
			resp = handler.handler(req);
		}catch(Exception e){
			String expMessage = CommonDataFactory.getInstance().getErrorStactMsg(e);
			resp.setError_code(ConstsCore.ERROR_FAIL);
			resp.setStack_trace_info(expMessage);
			resp.setError_msg("预校验系统未知错误");
		}
		return resp;
	}
	
	private void wrapperBLog(FunDealLog log ,CheckReq req){
		log.setOrder_id(req.getObj_id());
		log.setExe_time(req.getExe_time());
		log.setTache_code(req.getTrace_code());
		log.setReq_time(new Timestamp(System.currentTimeMillis()));
	}
	
	private void wrapperALog(FunDealLog log ,CheckResp resp){
		log.setResp_time(new Timestamp(System.currentTimeMillis()));
		log.setResult_code(resp.getError_code());
		log.setResult_msg(StringUtils.substr(resp.getStack_trace_info(), 2000));
	}
	
}
