package com.ztesoft.newstd.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import params.ZteRequest;
import params.ZteResponse;
import params.orderqueue.req.AsynExecuteMsgWriteMqReq;
import params.req.CodePurchaseMallOrderStandardReq;
import params.req.DepositOrderStandardReq;
import params.req.GroupOrderStandardReq;
import params.req.InternetGroupOrderStandardReq;
import params.req.FixBusiOrderStandardReq;
import params.req.MobileBusiCtnStandardReq;
import params.req.NewMallOrderStandardReq;
import params.req.ObjectReplaceStandardReq;
import params.req.OrderExpWriteForBusReq;
import params.resp.CodePurchaseMallOrderStandardResp;
import params.resp.DepositOrderStandardResp;
import params.resp.GroupOrderStandardResp;
import params.resp.InternetGroupStandardResp;
import params.resp.FixBusiOrderStandardResp;
import params.resp.MobileBusiCtnStandardResp;
import params.resp.NewMallOrderStandardResp;
import params.resp.ObjectReplaceStandardResp;
import params.resp.OrderExpWriteResp;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.ecsord.params.order.req.NewStartOrderPlanReq;
import zte.net.ecsord.params.order.req.RunWorkflowReq;
import zte.net.ecsord.params.order.req.StartWorkflowReq;
import zte.net.ecsord.params.order.resp.RunWorkflowRsp;
import zte.net.ecsord.params.order.resp.StartOrderPlanResp;
import zte.net.ecsord.params.order.resp.StartWorkflowRsp;
import zte.net.ecsord.params.workCustom.po.ES_WORK_CUSTOM_CFG;
import zte.net.iservice.IOrderQueueBaseManager;
import zte.net.ord.params.busi.req.OrderQueueBusiRequest;
import zte.params.req.CheckReq;
import zte.params.resp.CheckResp;

import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.form.util.MarkTime;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.net.consts.EccConsts;
import com.ztesoft.net.eop.sdk.context.MqEnvGroupConfigSetting;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.outter.inf.service.SDBUtils;
import com.ztesoft.net.server.CodePurchaseMallOrderUtil;
import com.ztesoft.net.server.DepositOrderUtil;
import com.ztesoft.net.server.GroupOrderUtil;
import com.ztesoft.net.server.InternetGroupOrderUtil;
import com.ztesoft.net.server.FixBusiOrderUtil;
import com.ztesoft.net.server.NewMallOrderUtil;
import com.ztesoft.newstd.service.NewStdOrderService;
import com.ztesoft.newstd.validate.BusiOrderValidate;
import com.ztesoft.newstd.validate.ObjectReplaceBusiOrderValidate;
import com.ztesoft.orderstd.service.INewOrderStandingManager;

import consts.ConstsCore;

public class OrderStandingManagerImpl implements INewOrderStandingManager{
	private static Logger logger = Logger.getLogger(OrderStandingManagerImpl.class);
    @Resource
    private NewStdOrderService newStdOrderService;
    @Resource
    private IOrderQueueBaseManager orderQueueBaseManager;
    @Resource
	private ICacheUtil cacheUtil;
    @SuppressWarnings("rawtypes")
	@Resource
    private IDaoSupport baseDaoSupport ;
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public MobileBusiCtnStandardResp mobileBusiCtnStandard(MobileBusiCtnStandardReq req) {
		
		MarkTime validate_mark = new MarkTime("mobileBusiCtnStandard Validate out_order_id="+req.getOut_id());
		
		MobileBusiCtnStandardResp resp = new MobileBusiCtnStandardResp();
		String co_id = req.getBase_co_id();
		
		//2.订单收单本地化校验
		try{
			resp = BusiOrderValidate.orderStandardValidate(req);
			//校验失败调用异常单方案
			if(ConstsCore.ERROR_FAIL.equals(resp.getError_code())){
				resp.setError_stack_msg("队列id:"+co_id+resp.getError_msg());
				this.exeExceptionOrd(req.getOut_id(),co_id, resp.getError_msg(),resp.getError_stack_msg());//失败调用异常单
				return resp;
			}
		}catch(Exception e){
			logger.error("mobileBusiCtnStandard Validate error,out_id="+req.getOut_id()+",error_info:"+e.getMessage(), e);
			
			String error_msg = CommonDataFactory.getInstance().getErrorStactMsg(e);
			resp.setError_msg("订单收单本地化校验失败");
			resp.setError_stack_msg("队列id:"+co_id+error_msg);
			resp.setError_code(ConstsCore.ERROR_FAIL);
			this.exeExceptionOrd(req.getOut_id(),co_id, resp.getError_msg(),resp.getError_stack_msg());//失败调用异常单
			return resp;
		}
		
		validate_mark.markEndTime();
		
		try{
			MarkTime save_mark = new MarkTime("mobileBusiCtnStandard saveOrder out_order_id="+req.getOut_id());
			
			List orderIdList =newStdOrderService.saveOrder(resp.getOut_order_List());
			resp.setError_code(ConstsCore.ERROR_SUCC);
            resp.setError_msg("标准化成功");
            resp.setOrder_id(orderIdList.get(0).toString());//设置内部单号
			
			save_mark.markEndTime();
            
			MarkTime rule_mark = new MarkTime("mobileBusiCtnStandard exeOrd out_order_id="+req.getOut_id());
			
    		//this.exeOrd(orderIdList);
			this.startWorkflow(orderIdList,resp.getOut_order_List().get(0).getFlowCfg());
    		rule_mark.markEndTime();
    		
            //写入主机环境日志
    		MarkTime param_mark = new MarkTime("mobileBusiCtnStandard insertSysParams out_order_id="+req.getOut_id());
    		
            this.insertSysParams(orderIdList);
            
            param_mark.markEndTime();
		}catch(Exception e){
			logger.error("mobileBusiCtnStandard error,out_id="+req.getOut_id()+",error_info:"+e.getMessage(), e);
			
			String error_stack_msg = "队列id:"+co_id+CommonDataFactory.getInstance().getErrorStactMsg(e);
			resp.setError_msg("标准化失败");
			resp.setError_stack_msg(error_stack_msg);
			resp.setError_code(ConstsCore.ERROR_FAIL);
			
			//4.2 标准化失败调用异常单方案
			this.exeExceptionOrd(req.getOut_id(),co_id, resp.getError_msg(),resp.getError_stack_msg());//失败调用异常单

			return resp;
		}
		return resp ;
	}
	
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public NewMallOrderStandardResp newMallOrderStandard(NewMallOrderStandardReq req){
		NewMallOrderStandardResp resp = new NewMallOrderStandardResp();
		String co_id = req.getBase_co_id();

		MarkTime validate_mark = new MarkTime("newMallOrderStandard Validate out_order_id="+req.getOut_id());
		
		//2.订单收单本地化校验
		
		try{
			resp = NewMallOrderUtil.orderStandardValidate(req);
			//校验失败调用异常单方案
			if(ConstsCore.ERROR_FAIL.equals(resp.getError_code())){
				resp.setError_stack_msg("队列id:"+co_id+resp.getError_msg());
				this.exeExceptionOrd(req.getOut_id(),co_id, resp.getError_msg(),resp.getError_stack_msg());//失败调用异常单
				
				return resp;
			}
		}catch(Exception e){
			logger.error("newMallOrderStandard Validate error,out_id="+req.getOut_id()+",error_info:"+e.getMessage(), e);
			
			String error_msg = CommonDataFactory.getInstance().getErrorStactMsg(e);
			resp.setError_msg("订单收单本地化校验失败");
			resp.setError_stack_msg("队列id:"+co_id+error_msg);
			resp.setError_code(ConstsCore.ERROR_FAIL);
			this.exeExceptionOrd(req.getOut_id(),co_id, resp.getError_msg(),resp.getError_stack_msg());//失败调用异常单

			return resp;
		}
		
		validate_mark.markEndTime();
		
		try{
			MarkTime save_mark = new MarkTime("newMallOrderStandard saveOrder out_order_id="+req.getOut_id());
			
			List orderIdList =newStdOrderService.saveOrder(resp.getOut_order_List());
			
			save_mark.markEndTime();
			
            resp.setError_code(ConstsCore.ERROR_SUCC);
            resp.setError_msg("标准化成功");
            resp.setOrder_id(orderIdList.get(0).toString());//设置内部单号
    		
        	MarkTime rule_mark = new MarkTime("newMallOrderStandard startWorkflow out_order_id="+req.getOut_id());
        	
        	this.startWorkflow(orderIdList,resp.getOut_order_List().get(0).getFlowCfg());
            
            rule_mark.markEndTime();
            
            //写入主机环境日志
            MarkTime param_mark = new MarkTime("newMallOrderStandard insertSysParams out_order_id="+req.getOut_id());
            
            this.insertSysParams(orderIdList);
            
            param_mark.markEndTime();
            
		}catch(Exception e){
			logger.error("newMallOrderStandard error,out_id="+req.getOut_id()+",error_info:"+e.getMessage(), e);
			
			String error_stack_msg = "队列id:"+co_id+CommonDataFactory.getInstance().getErrorStactMsg(e);
			resp.setError_msg("标准化失败--" + e.getMessage());
			resp.setError_stack_msg(error_stack_msg);
			resp.setError_code(ConstsCore.ERROR_FAIL);
			//4.2 标准化失败调用异常单方案
			this.exeExceptionOrd(req.getOut_id(),co_id, resp.getError_msg(),resp.getError_stack_msg());//失败调用异常单

			return resp;
		}
		
		return resp;
	}
	
	@Override
	public ObjectReplaceStandardResp objectReplaceStandard(ObjectReplaceStandardReq req) {
		ObjectReplaceStandardResp resp = new ObjectReplaceStandardResp();
		
		String co_id = req.getBase_co_id();
		
		//2.订单收单本地化校验
		long val_start = System.currentTimeMillis();
		try{
			resp = ObjectReplaceBusiOrderValidate.orderStandardValidate(req);
			//校验失败调用异常单方案
			if(ConstsCore.ERROR_FAIL.equals(resp.getError_code())){
				resp.setError_stack_msg("队列id:"+co_id+resp.getError_msg());
				this.exeExceptionOrd(req.getOut_id(),co_id, resp.getError_msg(),resp.getError_stack_msg());//失败调用异常单
				return resp;
			}
		}catch(Exception e){
				String error_msg = CommonDataFactory.getInstance().getErrorStactMsg(e);
				resp.setError_msg("订单收单本地化校验失败");
				resp.setError_stack_msg("队列id:"+co_id+error_msg);
				resp.setError_code(ConstsCore.ERROR_FAIL);
				this.exeExceptionOrd(req.getOut_id(),co_id, resp.getError_msg(),resp.getError_stack_msg());//失败调用异常单
				return resp;
		}
		long val_end = System.currentTimeMillis();
		logger.info(Thread.currentThread().getId()+"本地校验耗时：" + (val_end - val_start));
		
		try{
			List orderIdList =newStdOrderService.saveOrder(resp.getOut_order_List());
            resp.setError_code(ConstsCore.ERROR_SUCC);
            resp.setError_msg("标准化成功");
            resp.setOrder_id(orderIdList.get(0).toString());//设置内部单号
        	long val_end2 = System.currentTimeMillis();
    		logger.info(Thread.currentThread().getId()+"标准化成功验耗时：" + (val_end2 - val_end)+"====开始时间"+val_end+"结束时间"+val_end2);
            //4.1标准化成功调用正常单标准化方案
            this.exeOrd(orderIdList);//标准化成功执行正常单方案
            //写入主机环境日志
            this.insertSysParams(orderIdList);
		}catch(Exception e){
			e.printStackTrace();
			String error_stack_msg = "队列id:"+co_id+CommonDataFactory.getInstance().getErrorStactMsg(e);
			resp.setError_msg("标准化失败");
			resp.setError_stack_msg(error_stack_msg);
			resp.setError_code(ConstsCore.ERROR_FAIL);
			//4.2 标准化失败调用异常单方案
			this.exeExceptionOrd(req.getOut_id(),co_id, resp.getError_msg(),resp.getError_stack_msg());//失败调用异常单
//			//4.3 标准化失败写入outError表
//			if(resp.getOut_order_List().size()>0){
//				insertOuterError(resp.getOut_order_List());
//			}
			return resp;
		}
		
		return resp ;
	}
	
	/**
	 * 是否取消前置校验判断
	 * @param req
	 * @param resp
	 * @return
	 */
	public ZteResponse validatePreCheck(ZteRequest<ZteResponse> req,ZteResponse resp){
		String co_id = req.getBase_co_id();
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		String is_pre_check_run = "0";//默认为0，取消前置校验
		is_pre_check_run = cacheUtil.getConfigInfo(EcsOrderConsts.IS_PRE_CHECK_RUN);//是否取消前置校验 0：关闭 1：开启
		//默认配置在es_config_info为0，关闭前置校验，如需要打开需要配置为1
		if("1".equals(is_pre_check_run)){
			//1.调用校验系统进行校验
			CheckResp checkResp = this.validateOrder(co_id, req.getFormat());//调用校验系统进行校验
			//校验失败直接返回 校验系统已写入异常单,这里不用重复写入异常单
			if(ConstsCore.ERROR_FAIL.equals(checkResp.getError_code())){
			resp.setError_msg("标准化前置校验失败");
			resp.setError_stack_msg("队列id:"+co_id+checkResp.getError_msg());
			resp.setError_code(ConstsCore.ERROR_FAIL);
			return resp;
			}
		}
		return resp;
	}
	//订单标准化以前埋点调用校验系统
		public CheckResp validateOrder(String coId,String format){
			long start_validate = System.currentTimeMillis();
			CheckReq checkReq = new CheckReq();
			checkReq.setTrace_code(ConstsCore.TRACE_V);
			checkReq.setBiz_id(EccConsts.ORDER_STANDER_BIZ);
			checkReq.setExe_time(EccConsts.EXE_TIME_BEFORE);
			checkReq.setObj_id(coId);
			checkReq.setObj_type("order_queue");

			HashMap<String,String> extParam = new HashMap<String,String>();
			extParam.put("msgFormatType", format);//报文格式
			checkReq.setExt_param(extParam);
	        CheckResp checkRsp = CommonDataFactory.getInstance().checkProxy(checkReq);
	        long end_validate = System.currentTimeMillis();
			logger.info("前置校验耗时：" + (end_validate - start_validate));
	        return checkRsp;
		}
		//调用异常单系统执行异常单方案
		public void exeExceptionOrd(final String out_tid,final String co_id,final String error_msg,final String error_stack_msg){
			try{
				ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
				String is_exception_run = cacheUtil.getConfigInfo(EcsOrderConsts.IS_EXCEPTION_RUN);//是否启用异常系统
				if(!StringUtil.isEmpty(is_exception_run) && new Integer(is_exception_run).intValue() != 0 ){//异常系统启动标识
				    final OrderExpWriteForBusReq req = new OrderExpWriteForBusReq();
					req.setObj_id(co_id); //队列id
					req.setOut_tid(out_tid);
					req.setObj_type("order_queue"); //对象类型（order、order_queue）
					req.setSearch_id(EcsOrderConsts.EXP_STANDARD_CHECK_id); //搜索id
					req.setSearch_code(EcsOrderConsts.EXP_STANDARD_CHECK_CODE);//搜索编码
					req.setError_msg(error_msg);
					req.setError_stack_msg(error_stack_msg);//错误堆栈
					
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
						thread.setName("订单标准化业务异常线程");
						thread.start();
					}else{//mq
						ZteResponse mqResp= this.exeOrdMq(req,ConstsCore.MACHINE_EVN_CODE_ECSORD_EXP);
					}
				}
			}catch(Exception e){
			    e.printStackTrace();
			}	
		}
		// 正常单系统执行正常单标准化方案
		public ZteResponse exeOrdMq(ZteRequest zteReq,String envCode) {
			long ord_start = System.currentTimeMillis();
			ZteResponse resp = null;
			try {
				AsynExecuteMsgWriteMqReq req = new AsynExecuteMsgWriteMqReq();
				req.setService_code(zteReq.getApiMethodName());
				req.setVersion(ConstsCore.DUBBO_DEFAULT_VERSION);
				req.setZteRequest(zteReq);
				req.setConsume_env_code(com.ztesoft.common.util.BeanUtils.getHostEnvCodeByEnvStatus(envCode));
				if(orderQueueBaseManager==null){
					orderQueueBaseManager = SpringContextHolder.getBean("orderQueueBaseManager");
				}
				resp = orderQueueBaseManager.asynExecuteMsgWriteMq(req);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			long ord_end = System.currentTimeMillis();
			logger.info("订单执行耗时：" + (ord_end - ord_start));
			return resp;
		}
		
		//正常单系统执行正常单标准化方案
	    public void exeOrd(List orderIdList){
	        long ord_start = System.currentTimeMillis();
	        final NewStartOrderPlanReq planreq = new NewStartOrderPlanReq();
	        planreq.setOrderIdList(orderIdList);
	        String exe_type = MqEnvGroupConfigSetting.ORD_STANDING_EXEC;//调用方式 m/d  m是走mq d是走dubbo
	        try{   //消息推送正常单标准化方案
	            
	            boolean flag = false;//dubbo或MQ失败时使用线程异常调用
	            if(ConstsCore.DECOUPLING_EXEC_D.equals(exe_type)){//消息开关关闭走dubbo
	                flag = true;
	            }else{
	                ZteResponse resp = this.exeOrdMq(planreq,ConstsCore.MACHINE_EVN_CODE_ECSORD);
	                //消息调用失败就走dubbo
	                if(resp==null||ConstsCore.ERROR_FAIL.equals(resp.getError_code())){
	                    flag = true;
	                }
	            }
	            if(flag){
	            	ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
	            	ZteResponse pResp =  client.execute(planreq, StartOrderPlanResp.class);
	            }
	        }catch(Exception e){
	                e.printStackTrace();
	        }
	        long ord_end = System.currentTimeMillis();
	        logger.info("订单执行耗时：" + (ord_end - ord_start));
	    }
	    private void insertSysParams(List<String> orderIdList){
	    	String is_run_abgray = "0";// 默认为0，取消灰度环境使用
			is_run_abgray = cacheUtil.getConfigInfo(EcsOrderConsts.IS_RUN_ABGRAY);// 是否启用灰度 0：关闭 1：开启
			if (StringUtils.isNotBlank(is_run_abgray) && "1".equals(is_run_abgray)) {
				 try{
						String source_from = ManagerUtils.getSourceFrom();
				  		for(String order_id : orderIdList){
				  			OrderTreeBusiRequest ordertree = CommonDataFactory.getInstance().getOrderTree(order_id);
				  			String out_tid = ordertree.getOrderExtBusiRequest().getOut_tid();
				  			String ship_name = ordertree.getOrderDeliveryBusiRequests().get(0).getShip_name();
				  			String remark = ordertree.getOrderBusiRequest().getRemark();
				  			String order_channel = ordertree.getOrderExtBusiRequest().getOrder_channel();
				  			String zb_inf_id = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ZB_INF_ID);
				  			String env_code = "";
				  			if(Consts.TAOBAO_CODE_ID.equals(order_channel)){//淘宝单需要单独处理测试单和灰度单，淘宝单只在1.0中存在
				  				if(Consts.ZTE_CESHI.equals(ship_name)||Consts.ZTE_CESHI.equals(remark)){
				  					 env_code =  Consts.ZTE_CESH_ENV_TYPE_SERVER;
				  				}else if(Consts.ZTE_GRAY.equals(ship_name)||Consts.ZTE_GRAY.equals(remark)){
				  					String sql ="select env_code from es_abgray_hostenv where env_type='"+Consts.ZTE_ENV_TYPE_SERVER+"'  and env_status='00X' and rownum=1";
				  					env_code = baseDaoSupport.queryForString(sql);
				  				}
				  	        }
				  			if(StringUtil.isEmpty(env_code)){
				  				env_code = (String)com.ztesoft.common.util.BeanUtils.getCurrHostEnv().get("env_code");
				  			}
				  			// 订单关联环境信息<<begin
				  			HashMap p = new HashMap();
				  			p.put("source_from", source_from);
				  			p.put("order_id", order_id);
				  			p.put("op_code", "ordstd");
				  			p.put("out_tid", out_tid);
				  			p.put("ship_name", ship_name);
				  			p.put("remark", remark);
				  			p.put("order_channel", order_channel);
				  			p.put("zb_inf_id", zb_inf_id);
				  			p.put("env_code", env_code);
				  			logger.info("OrderStandardizing:新订单标准化写入主机环境信息：外部单号"
				  					+ out_tid + ",订单信息" + order_id + ",订单渠道:"
				  					+ order_channel + "," + "收货人：" + ship_name + ",客户留言:" + remark
				  					+ "总部单号：" + zb_inf_id);
				  			long start = System.currentTimeMillis();
				  			com.ztesoft.common.util.BeanUtils.ordBindEvn(p);
				  			com.ztesoft.common.util.BeanUtils.ordBindEvnLog(p);
				  			long end = System.currentTimeMillis();
				              logger.info("新商城写入主机环境：" + (end - start));
				  			// 订单关联环境信息<<endstart
				  		}	
				  	  }catch(Exception e){
				  		  e.printStackTrace();
				  	  }
			}
	  	 
	  	}
	    
        @SuppressWarnings({ "rawtypes", "unchecked" })
		@Override
        public InternetGroupStandardResp internetGroupOrderStandard( InternetGroupOrderStandardReq req) {
            InternetGroupStandardResp resp = new InternetGroupStandardResp();
            String co_id = req.getBase_co_id();
           
            MarkTime validate_mark = new MarkTime("internetGroupOrderStandard Validate out_order_id="+req.getOut_id());
            
            try {
                resp = InternetGroupOrderUtil.orderStandardValidate(req);
                // 校验失败调用异常单方案
                if (ConstsCore.ERROR_FAIL.equals(resp.getError_code())) {
                    resp.setError_stack_msg("队列id:" + co_id + resp.getError_msg());
                    this.exeExceptionOrd(req.getOut_id(), co_id, resp.getError_msg(), resp.getError_stack_msg());// 失败调用异常单
                    return resp;
                }
            } catch (Exception e) {
            	logger.error("internetGroupOrderStandard Validate error,out_id="+req.getOut_id()+",error_info:"+e.getMessage(), e);
            	
                String error_msg = CommonDataFactory.getInstance().getErrorStactMsg(e);
                resp.setError_msg("订单收单本地化校验失败");
                resp.setError_stack_msg("队列id:" + co_id + error_msg);
                resp.setError_code(ConstsCore.ERROR_FAIL);
                this.exeExceptionOrd(req.getOut_id(), co_id, resp.getError_msg(), resp.getError_stack_msg());// 失败调用异常单
                return resp;
            }
            
            validate_mark.markEndTime();

            try {
            	MarkTime save_mark = new MarkTime("internetGroupOrderStandard saveOrder out_order_id="+req.getOut_id());
            	
                List orderIdList = newStdOrderService.saveOrder(resp.getOut_order_List());
                resp.setError_code(ConstsCore.ERROR_SUCC);
                resp.setError_msg("标准化成功");
                resp.setOrder_id(orderIdList.get(0).toString());//设置内部单号
                
                save_mark.markEndTime();
                
                MarkTime rule_mark = new MarkTime("internetGroupOrderStandard exeOrd out_order_id="+req.getOut_id());
                
                // 4.1标准化成功调用正常单标准化方案
                this.exeOrd(orderIdList);// 标准化成功执行正常单方案
                
                rule_mark.markEndTime();
                
                MarkTime param_mark = new MarkTime("internetGroupOrderStandard insertSysParams out_order_id="+req.getOut_id());
                
                // 写入主机环境日志
                this.insertSysParams(orderIdList);
                
                param_mark.markEndTime();
            } catch (Exception e) {
            	logger.error("internetGroupOrderStandard error,out_id="+req.getOut_id()+",error_info:"+e.getMessage(), e);
            	
                String error_stack_msg = "队列id:" + co_id + CommonDataFactory.getInstance().getErrorStactMsg(e);
                resp.setError_msg("标准化失败");
                resp.setError_stack_msg(error_stack_msg);
                resp.setError_code(ConstsCore.ERROR_FAIL);
                // 4.2 标准化失败调用异常单方案
                this.exeExceptionOrd(req.getOut_id(), co_id, resp.getError_msg(), resp.getError_stack_msg());// 失败调用异常单

                return resp;
            }
            
            return resp;
        }

        @SuppressWarnings({ "rawtypes", "unchecked" })
		@Override
        public GroupOrderStandardResp groupOrderStandard( GroupOrderStandardReq req) {
            GroupOrderStandardResp resp = new GroupOrderStandardResp();
            String co_id = req.getBase_co_id();
            
            MarkTime validate_mark = new MarkTime("groupOrderStandard Validate out_order_id="+req.getOut_id());
            
            // 2.订单收单本地化校验--邵初
            try {
                resp = GroupOrderUtil.orderStandardValidate(req);
                // 校验失败调用异常单方案
                if (ConstsCore.ERROR_FAIL.equals(resp.getError_code())) {
                    this.exeExceptionOrd(req.getOut_id(), co_id, resp.getError_msg(), resp.getError_stack_msg());// 失败调用异常单
                    resp.setError_stack_msg("队列id:" + co_id + resp.getError_msg());
                    return resp;
                }
            } catch (Exception e) {
            	logger.error("groupOrderStandard Validate error,out_id="+req.getOut_id()+",error_info:"+e.getMessage(), e);
            	
                String error_msg = "队列id:" + co_id + CommonDataFactory.getInstance().getErrorStactMsg(e);
                resp.setError_msg("订单收单本地化校验失败");
                resp.setError_stack_msg(error_msg);
                resp.setError_code(ConstsCore.ERROR_FAIL);
                this.exeExceptionOrd(req.getOut_id(), co_id, resp.getError_msg(), resp.getError_stack_msg());// 失败调用异常单
               
                return resp;
            }
            
            validate_mark.markEndTime();
            
            // 3.进行标准化
            try {
            	MarkTime save_mark = new MarkTime("groupOrderStandard saveOrder out_order_id="+req.getOut_id());
            	
                List orderIdList = newStdOrderService.saveOrder(resp.getOut_order_List());
                resp.setError_code(ConstsCore.ERROR_SUCC);
                resp.setError_msg("标准化成功");
                resp.setOrder_id(orderIdList.get(0).toString());//设置内部单号
                
                save_mark.markEndTime();
                
                MarkTime rule_mark = new MarkTime("groupOrderStandard exeOrd out_order_id="+req.getOut_id());
                
                // 4.1标准化成功调用正常单标准化方案
                this.exeOrd(orderIdList);// 标准化成功执行正常单方案
                
                rule_mark.markEndTime();
                
                MarkTime param_mark = new MarkTime("groupOrderStandard insertSysParams out_order_id="+req.getOut_id());
                
                // 写入主机环境日志
                this.insertSysParams(orderIdList);
                
                param_mark.markEndTime();
            } catch (Exception e) {
            	logger.error("groupOrderStandard error,out_id="+req.getOut_id()+",error_info:"+e.getMessage(), e);
            	
                String error_stack_msg = "队列id:" + co_id + CommonDataFactory.getInstance().getErrorStactMsg(e);
                resp.setError_msg("标准化失败");
                resp.setError_stack_msg(error_stack_msg);
                resp.setError_code(ConstsCore.ERROR_FAIL);
                
                // 4.2 标准化失败调用异常单方案
                this.exeExceptionOrd(req.getOut_id(), co_id, resp.getError_msg(), resp.getError_stack_msg());// 失败调用异常单
                
                // 4.3总部标准化失败写入es_fail_order_msg表
                try {
                    String out_tid = resp.getOut_order_List().get(0).getOut_tid();
                    String req_content = this.getReqContent(null, req.getBase_co_id(), req.isIs_exception());
                    insertFailOrd("ZB", out_tid, req_content, "队列id:" + co_id + resp.getError_msg());
                } catch (Exception e1) {
                	logger.error("groupOrderStandard insertFailOrd error,out_id="+req.getOut_id()+",error_info:"+e1.getMessage(), e1);
                }
                
                return resp;
            }
            
            return resp;
        }
        // 获取归集系统请求报文
        public String getReqContent(String order_id, String co_id, boolean is_exception) {
            String content = "";
            OrderQueueBusiRequest orderQueue = new OrderQueueBusiRequest();
            if (is_exception) {
                orderQueue = CommonDataFactory.getInstance().getOrderQueueFor(order_id, co_id);
            } else {
                orderQueue = CommonDataFactory.getInstance().getOrderQueue(order_id, co_id);
            }
            if (orderQueue != null) {
                content = orderQueue.getContents();
            }
            return content;
        }
        public static void insertFailOrd(String source_system, String out_tid, String inJson, String checkResult) {
            SDBUtils dbUtils = SpringContextHolder.getBean("sdbUtils");
            Map fieldsMap = new HashMap();
            fieldsMap.put("out_tid", out_tid);
            fieldsMap.put("source_from", ManagerUtils.getSourceFrom());
            fieldsMap.put("source_system", source_system);
            fieldsMap.put("dealnum", "1");
            fieldsMap.put("failure_desc", checkResult);
            fieldsMap.put("jsonclob", inJson);
            dbUtils.insertOrderData("es_fail_order_msg", fieldsMap);
        }

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public FixBusiOrderStandardResp fixBusiOrderStandard(
			FixBusiOrderStandardReq req) {
		FixBusiOrderStandardResp resp = new FixBusiOrderStandardResp();
		String co_id = req.getBase_co_id();
		
		MarkTime validate_mark = new MarkTime("fixBusiOrderStandard Validate out_order_id="+req.getOut_id());
		
		try{
			//收单数据校验，生成标准化数据
			resp = FixBusiOrderUtil.orderStandardValidate(req);
			
			//校验失败调用异常单方案
			if(ConstsCore.ERROR_FAIL.equals(resp.getError_code())){
				resp.setError_stack_msg("队列id:"+co_id+resp.getError_msg());
				
				this.exeExceptionOrd(req.getOut_id(),co_id, resp.getError_msg(),resp.getError_stack_msg());//失败调用异常单
				
				return resp;
			}
		}catch(Exception e){
			logger.error("fixBusiOrderStandard Validate error,out_id="+req.getOut_id()+",error_info:"+e.getMessage(), e);
			
			String error_msg = CommonDataFactory.getInstance().getErrorStactMsg(e);
			resp.setError_msg("订单收单本地化校验失败");
			resp.setError_stack_msg("队列id:"+co_id+error_msg);
			resp.setError_code(ConstsCore.ERROR_FAIL);
			
			this.exeExceptionOrd(req.getOut_id(),co_id, resp.getError_msg(),resp.getError_stack_msg());//失败调用异常单
			
			return resp;
		}
		
		validate_mark.markEndTime();
		
		try{
			//保存订单
			MarkTime save_mark = new MarkTime("fixBusiOrderStandard saveOrder out_order_id="+req.getOut_id());
			
			List orderIdList = newStdOrderService.saveOrder(resp.getOut_order_List());
			
            resp.setError_code(ConstsCore.ERROR_SUCC);
            resp.setError_msg("标准化成功");
            resp.setOrder_id(orderIdList.get(0).toString());//设置内部单号
            
            save_mark.markEndTime();
    		
            MarkTime rule_mark = new MarkTime("fixBusiOrderStandard exeOrd out_order_id="+req.getOut_id());
            
            //执行标准化前置规则，启动工作流
            this.exeOrd(orderIdList);
            
            rule_mark.markEndTime();
            
            MarkTime param_mark = new MarkTime("fixBusiOrderStandard insertSysParams out_order_id="+req.getOut_id());
            //写入主机环境日志
            this.insertSysParams(orderIdList);
            
            param_mark.markEndTime();
            
		}catch(Exception e){
			
			logger.error("fixBusiOrderStandard error,out_id="+req.getOut_id()+",error_info:"+e.getMessage(), e);
			
			String error_stack_msg = "队列id:"+co_id+CommonDataFactory.getInstance().getErrorStactMsg(e);
			resp.setError_msg("标准化失败");
			resp.setError_stack_msg(error_stack_msg);
			resp.setError_code(ConstsCore.ERROR_FAIL);
			
			//4.2 标准化失败调用异常单方案
			this.exeExceptionOrd(req.getOut_id(),co_id, resp.getError_msg(),resp.getError_stack_msg());//失败调用异常单
			
			return resp;
		}
		
		return resp ;
	}

    @SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
    public CodePurchaseMallOrderStandardResp codePurchaseMallOrderStandard( CodePurchaseMallOrderStandardReq req) {

    	MarkTime validate_mark = new MarkTime("codePurchaseMallOrderStandard Validate out_order_id="+req.getOut_id());

        CodePurchaseMallOrderStandardResp resp = new CodePurchaseMallOrderStandardResp();
        String co_id = req.getBase_co_id();
        // 2.订单收单本地化校验--邵初
        try {
            resp = CodePurchaseMallOrderUtil.orderStandardValidate(req);
            // 校验失败调用异常单方案
            if (ConstsCore.ERROR_FAIL.equals(resp.getError_code())) {
                this.exeExceptionOrd(req.getOut_id(), co_id, resp.getError_msg(), resp.getError_stack_msg());// 失败调用异常单
                resp.setError_stack_msg("队列id:" + co_id + resp.getError_msg());
                return resp;
            }
        } catch (Exception e) {
        	logger.error("codePurchaseMallOrderStandard Validate error,out_id="+req.getOut_id()+",error_info:"+e.getMessage(), e);
        	
            String error_msg = "队列id:" + co_id + CommonDataFactory.getInstance().getErrorStactMsg(e);
            resp.setError_msg("订单收单本地化校验失败");
            resp.setError_stack_msg(error_msg);
            resp.setError_code(ConstsCore.ERROR_FAIL);
            this.exeExceptionOrd(req.getOut_id(), co_id, resp.getError_msg(), resp.getError_stack_msg());// 失败调用异常单
            
            return resp;
        }
        
        validate_mark.markEndTime();
        
        // 3.进行标准化
        try {
        	MarkTime save_mark = new MarkTime("codePurchaseMallOrderStandard saveOrder out_order_id="+req.getOut_id());
        	
            List orderIdList = newStdOrderService.saveOrder(resp.getOut_order_List());
            resp.setError_code(ConstsCore.ERROR_SUCC);
            resp.setError_msg("标准化成功");
            resp.setOrder_id(orderIdList.get(0).toString());
            
            save_mark.markEndTime();
            
            MarkTime rule_mark = new MarkTime("codePurchaseMallOrderStandard exeOrd out_order_id="+req.getOut_id());
            // 4.1标准化成功调用正常单标准化方案
            this.exeOrd(orderIdList);// 标准化成功执行正常单方案
            rule_mark.markEndTime();
            
            MarkTime param_mark = new MarkTime("codePurchaseMallOrderStandard insertSysParams out_order_id="+req.getOut_id());
            // 写入主机环境日志
            this.insertSysParams(orderIdList);
            
            param_mark.markEndTime();
            
        } catch (Exception e) {
        	logger.error("codePurchaseMallOrderStandard error,out_id="+req.getOut_id()+",error_info:"+e.getMessage(), e);
        	
            String error_stack_msg = "队列id:" + co_id + CommonDataFactory.getInstance().getErrorStactMsg(e);
            resp.setError_msg("标准化失败");
            resp.setError_stack_msg(error_stack_msg);
            resp.setError_code(ConstsCore.ERROR_FAIL);
            
            // 4.2 标准化失败调用异常单方案
            this.exeExceptionOrd(req.getOut_id(), co_id, resp.getError_msg(), resp.getError_stack_msg());// 失败调用异常单
            
            // 4.3总部标准化失败写入es_fail_order_msg表
            try {
                String out_tid = resp.getOut_order_List().get(0).getOut_tid();
                String req_content = this.getReqContent(null, req.getBase_co_id(), req.isIs_exception());
                insertFailOrd("ZB", out_tid, req_content, "队列id:" + co_id + resp.getError_msg());
            } catch (Exception e1) {
            	logger.error("codePurchaseMallOrderStandard insertFailOrd error,out_id="+req.getOut_id()+",error_info:"+e1.getMessage(), e1);
            }
            return resp;
        }
        return resp;
    }

	@Override
	public NewMallOrderStandardResp orderUpdate(NewMallOrderStandardReq req) throws Exception{
		NewMallOrderStandardResp resp = new NewMallOrderStandardResp();
		
		MarkTime validate_mark = new MarkTime("orderUpdate Validate out_order_id="+req.getOut_id());
		
		//订单收单校验
		try{
			resp = NewMallOrderUtil.orderStandardValidate(req);
			
			//校验失败调用异常单方案
			if(ConstsCore.ERROR_FAIL.equals(resp.getError_code())){
				return resp;
			}
		}catch(Exception e){
			logger.error("orderUpdate Validate error,out_id="+req.getOut_id()+",error_info:"+e.getMessage(), e);
			
			resp.setError_msg("订单收单本地化校验失败："+e.getMessage());
			resp.setError_code(ConstsCore.ERROR_FAIL);

			return resp;
		}
		
		validate_mark.markEndTime();
		
		try{
			MarkTime save_mark = new MarkTime("orderUpdate saveOrder out_order_id="+req.getOut_id());
			
			String order_id = this.newStdOrderService.getExistOrderId(resp.getOut_order_List().get(0));
			String instance_id = this.newStdOrderService.getNodeInstanceId(order_id);
			
			this.newStdOrderService.updateOrder(order_id,resp.getOut_order_List().get(0));
			
			save_mark.markEndTime();
			
            resp.setError_code(ConstsCore.ERROR_SUCC);
            resp.setError_msg("标准化成功");
            resp.setOrder_id(order_id);//设置内部单号
            
            List<String> orderIdList = new ArrayList<String>();
            orderIdList.add(order_id);
    		
        	MarkTime rule_mark = new MarkTime("orderUpdate startWorkflow out_order_id="+req.getOut_id());
        	
        	this.runWorkFlow(instance_id,Const.UPDATE_ORDER);
            
            rule_mark.markEndTime();
            
            //写入主机环境日志
            MarkTime param_mark = new MarkTime("orderUpdate insertSysParams out_order_id="+req.getOut_id());
            
            this.insertSysParams(orderIdList);
            
            param_mark.markEndTime();
            
		}catch(Exception e){
			logger.error("orderUpdate error,out_id="+req.getOut_id()+",error_info:"+e.getMessage(), e);
			
			resp.setError_msg("标准化失败："+e.getMessage());
			resp.setError_code(ConstsCore.ERROR_FAIL);

			return resp;
		}
		
		return resp;
	}
	
	/**
	 * 启动工作流
	 * @param orderIdList
	 * @param flowCfg
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	private void startWorkflow(List orderIdList, ES_WORK_CUSTOM_CFG flowCfg) throws Exception{
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		String cfg = cacheUtil.getConfigInfo("WORKFLOW_CONTROL");//自定义流程开关 0：关闭 1：开启
		
		if("1".equals(cfg) && flowCfg!=null){
			//启动自定义流程
			StartWorkflowReq flowReq = new StartWorkflowReq();
			
			flowReq.setOrder_id(String.valueOf(orderIdList.get(0)));
			flowReq.setCfg(flowCfg);
			flowReq.setCfg_type(flowCfg.getCfg_type());
	        
	        ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
	        StartWorkflowRsp flowRsp = client.execute(flowReq, StartWorkflowRsp.class);
	        
	        String workflow_id = flowRsp.getWorkflow_id();
			
	        if("-1".equals(flowRsp.getError_code()) || "-1".equals(workflow_id)){
	        	//调用DUBBO异常或者是流程启动失败，抛出异常
	        	throw new Exception(flowRsp.getError_msg());
	        }else if("0".equals(workflow_id)){
	        	//未匹配到自定义流程，启动规则
	        	this.exeOrd(orderIdList);
	        }
		}else{
			//开关关闭，启动规则
        	this.exeOrd(orderIdList);
		}
	}
	
	/**
	 * 继续执行流程
	 * @param instance_id
	 * @throws Exception
	 */
	private void runWorkFlow(String instance_id,String param) throws Exception{
		//执行自定义流程
		RunWorkflowReq runReq = new RunWorkflowReq();
		
		runReq.setInstance_id(instance_id);
		runReq.setGoNextManual(false);
		runReq.setWebCondition("");
		runReq.setRemark("更新订单信息");
		runReq.setJson_param(param);
		
        ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
        RunWorkflowRsp flowRsp = client.execute(runReq, RunWorkflowRsp.class);

        if(ConstsCore.ERROR_FAIL.equals(flowRsp.getError_code())){
        	//调用DUBBO异常或者是流程启动失败，抛出异常
        	throw new Exception(flowRsp.getError_msg());
        }
	}
	
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public DepositOrderStandardResp orderDepositStandard(DepositOrderStandardReq req){
		DepositOrderStandardResp resp = new DepositOrderStandardResp();
		String co_id = req.getBase_co_id();

		MarkTime validate_mark = new MarkTime("orderDepositStandard Validate out_order_id="+req.getOut_id());
		
		//2.订单收单本地化校验
		
		try{
			resp = DepositOrderUtil.orderDepositStandardValidate(req);
			//校验失败调用异常单方案
			if(ConstsCore.ERROR_FAIL.equals(resp.getError_code())){
				resp.setError_stack_msg("队列id:"+co_id+resp.getError_msg());
				this.exeExceptionOrd(req.getOut_id(),co_id, resp.getError_msg(),resp.getError_stack_msg());//失败调用异常单
				
				return resp;
			}
		}catch(Exception e){
			logger.error("orderDepositStandard Validate error,out_id="+req.getOut_id()+",error_info:"+e.getMessage(), e);
			
			String error_msg = CommonDataFactory.getInstance().getErrorStactMsg(e);
			resp.setError_msg("订单收单本地化校验失败");
			resp.setError_stack_msg("队列id:"+co_id+error_msg);
			resp.setError_code(ConstsCore.ERROR_FAIL);
			this.exeExceptionOrd(req.getOut_id(),co_id, resp.getError_msg(),resp.getError_stack_msg());//失败调用异常单

			return resp;
		}
		
		validate_mark.markEndTime();
		
		try{
			MarkTime save_mark = new MarkTime("orderDepositStandard saveOrder out_order_id="+req.getOut_id());
			
			List orderIdList =newStdOrderService.saveOrder(resp.getOut_order_List());
			
			save_mark.markEndTime();
			
            resp.setError_code(ConstsCore.ERROR_SUCC);
            resp.setError_msg("标准化成功");
            resp.setOrder_id(orderIdList.get(0).toString());//设置内部单号
    		
        	MarkTime rule_mark = new MarkTime("orderDepositStandard startWorkflow out_order_id="+req.getOut_id());
        	
        	this.startWorkflow(orderIdList,resp.getOut_order_List().get(0).getFlowCfg());
            
            rule_mark.markEndTime();
            
            //写入主机环境日志
            MarkTime param_mark = new MarkTime("orderDepositStandard insertSysParams out_order_id="+req.getOut_id());
            
            this.insertSysParams(orderIdList);
            
            param_mark.markEndTime();
            
		}catch(Exception e){
			logger.error("orderDepositStandard error,out_id="+req.getOut_id()+",error_info:"+e.getMessage(), e);
			
			String error_stack_msg = "队列id:"+co_id+CommonDataFactory.getInstance().getErrorStactMsg(e);
			resp.setError_msg("标准化失败");
			resp.setError_stack_msg(error_stack_msg);
			resp.setError_code(ConstsCore.ERROR_FAIL);
			//4.2 标准化失败调用异常单方案
			this.exeExceptionOrd(req.getOut_id(),co_id, resp.getError_msg(),resp.getError_stack_msg());//失败调用异常单

			return resp;
		}
		
		return resp;
	}
}
