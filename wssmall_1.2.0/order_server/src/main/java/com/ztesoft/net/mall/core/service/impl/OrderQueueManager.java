package com.ztesoft.net.mall.core.service.impl;

import java.util.List;
import java.util.Map;

import params.ZteRequest;
import params.ZteResponse;
import params.orderqueue.req.OrderCollectionReq;
import services.DefaultServiceContext;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.rule.tache.TacheFact;
import zte.net.iservice.consts.InfoConsts;
import zte.net.ord.params.busi.req.OrderQueueBusiRequest;
import zte.net.ord.params.busi.req.OrderWorkBusiRequest;
import zte.net.ord.rule.inf.InfTplFact;
import zte.net.params.req.PlanRuleTreeExeReq;
import zte.net.params.req.RuleTreeExeReq;
import zte.net.params.resp.PlanRuleTreeExeResp;
import zte.net.params.resp.RuleTreeExeResp;
import zte.params.template.req.TemplateAccessConvertReq;
import zte.params.template.resp.TemplateAccessConvertResp;

import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.DefaultZteDubboClient;
import com.ztesoft.api.DefaultZteMqClient;
import com.ztesoft.api.ZteClient;
import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.ibss.common.util.DateUtil;
import com.ztesoft.net.auto.rule.fact.AutoFact;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.model.OpenServiceCfg;
import com.ztesoft.net.mall.core.model.OrderCfgInfo;
import com.ztesoft.net.mall.core.model.OrderHandleLogs;
import com.ztesoft.net.mall.core.model.OrderQueue;
import com.ztesoft.net.mall.core.model.OrderWork;
import com.ztesoft.net.mall.core.service.IDcPublicInfoManager;
import com.ztesoft.net.mall.core.service.IOrderQueueManager;
import com.ztesoft.net.mall.core.service.impl.cache.DcPublicInfoCacheProxy;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.mall.service.IFormatProcess;
import com.ztesoft.rop.common.ServiceMethodHandler;

import consts.ConstsCore;

/**
 * @Description 订单归集处理类
 * @author  zhangJun
 * @date    2015-3-11
 * @version 1.0
 */
public class OrderQueueManager extends BaseSupport implements IOrderQueueManager { 

	@Override
	public String orderCollection(OrderCollectionReq ocReq) {
		//1、参数接收
		String respStr="";//返回的报文xml/json
		String type=ocReq.getType();
		String serv=ocReq.getServ();
		String version=ocReq.getVersion();
		String req=ocReq.getReq();//报文
		
		//2、根据参数处理
		IFormatProcess processor = IFormatProcess.getProcessor(type);
		DefaultServiceContext context = DefaultServiceContext.getInstance();
		if (context.isValidMethodVersion(serv, version)) {//校验服务是否存在
			
			//3、  订单、队列入库操作
			OrderCfgInfo oci;
			try {
				oci = this.OrderColl(serv, version, req);
			} catch (Exception e) {
				e.printStackTrace();
				return processor.getMsg(IFormatProcess.MSG_CODE_ERROR, "报文入库失败");
			}
			OpenServiceCfg cfg=oci.getOpenServiceCfg();
			
			//4、 根据配置信息调用mq/dubbo，无配置则默认用dubbo
			if(EcsOrderConsts.RPC_TYPE_M.equals(cfg.getRpc_type())){//异步-mq
				respStr=this.mqExecute(ocReq, oci.getCo_id(),oci.getOrder_id());
			}else{//同步-dubbo
				respStr=this.dubboExecute(ocReq, oci.getCo_id(),oci.getOrder_id());
			}
		}else{//服务找不到
			return processor.getMsg(IFormatProcess.MSG_CODE_ERROR, IFormatProcess.ERROR_SERV_NOT_FOUND);
		}
		return respStr;
	}

	
	@Override
	public String OrderQueueSave(OrderQueue orderQueue) throws Exception {
		OrderQueueBusiRequest req=new OrderQueueBusiRequest();
		String co_id="";
		co_id=this.daoSupport.getSequences("S_ES_ORDER_QUEUE", "1", 18);
		req.setCo_id(co_id);
		req.setCo_name(orderQueue.getCo_name());
		req.setBatch_id(orderQueue.getBatch_id());
		req.setService_code(orderQueue.getService_code());
		req.setAction_code(orderQueue.getAction_code());
		req.setObject_type(orderQueue.getObject_type());
		req.setObject_id(orderQueue.getObject_id());
		req.setContents(orderQueue.getContents());
		req.setStatus(orderQueue.getStatus());
		req.setSend_date(orderQueue.getSend_date());
		req.setOper_id(orderQueue.getOper_id());
		req.setCreated_date(DateUtil.getTime2());//创建日期
		//req.setDeal_num(orderQueue.getDeal_num());
		req.setSend_date(orderQueue.getSend_date());
		req.setResp_date(orderQueue.getResp_date());
		req.setStatus_date(orderQueue.getStatus_date());
		req.setSource_from(ManagerUtils.getSourceFrom());
		//req.setVersion(orderQueue.getVersion());
		req.setOrder_id(orderQueue.getOrder_id());
		req.store();
		
		return co_id;
	}

	@Override
	public String  OrderMainSave(OrderWork orderWork) throws Exception {
		String order_id="";
		OrderWorkBusiRequest req=new OrderWorkBusiRequest();
		order_id=this.daoSupport.getSequences("S_ES_ORDER_WORK", "1", 18);
		req.setCreate_time(DateUtil.getTime2());//创建日期
		req.setOrder_desc(orderWork.getOrder_desc());
		req.setUpdate_time(orderWork.getUpdate_time());
		req.setOrder_id(orderWork.getOrder_id());
		req.setFlow_id(orderWork.getFlow_id());
		req.setFlow_inst_id(orderWork.getFlow_inst_id());
		req.setFlow_name(orderWork.getFlow_name());
		req.store();
		
		return order_id;
	}

	@Override
	public OpenServiceCfg getOpenServiceCfgByName(String methodName, String version) {
		OpenServiceCfg os=null;
		IDcPublicInfoManager dcPublicInfoManager = SpringContextHolder.getBean("dcPublicInfoManager");
		DcPublicInfoCacheProxy proxy = new DcPublicInfoCacheProxy(dcPublicInfoManager);
		List<Map> OpenServiceCfgList = proxy.getOpenServiceCfgList();
		if(!StringUtil.isEmpty(methodName)&&!StringUtil.isEmpty(version)){
			for(Map m : OpenServiceCfgList){
				if(methodName.equals(Const.getStrValue(m, "method"))&&version.equals(Const.getStrValue(m, "version"))){
					os=new OpenServiceCfg();
					//os.setMethod(Const.getStrValue(m, "method"));
					os.setRemark(Const.getStrValue(m, "remark"));
					os.setRpc_type(Const.getStrValue(m, "rpc_type"));
					os.setService_id(Const.getStrValue(m, "service_id"));
					os.setSource_from(Const.getStrValue(m, "source_from"));
					os.setVersion(Const.getStrValue(m, "version"));
					os.setWrite_logs_flag(Const.getStrValue(m, "write_logs_flag"));
					os.setWrite_order_flag(Const.getStrValue(m, "write_order_flag"));
					os.setWrite_queue_flag(Const.getStrValue(m, "write_queue_flag"));
					break;
				}
			}
		}
		return os;
	}

	@Override
	public OrderCfgInfo OrderColl(String method, String version, String req) throws Exception {//做队列、订单创建实物控制 
		OrderCfgInfo orderCfgInfo=new OrderCfgInfo();
		OpenServiceCfg os=this.getOpenServiceCfgByName(method, version);
		String co_id="";
		//for (int i = 0; i < "d".length(); i++) int i=2+3;
		String order_id="";
		if(os!=null){
			if(EcsOrderConsts.BASE_YES_FLAG_1.equals(os.getWrite_order_flag())){
				OrderWork orderMain=new OrderWork ();
				order_id=this.OrderMainSave(orderMain);
			}
			if(EcsOrderConsts.BASE_YES_FLAG_1.equals(os.getWrite_queue_flag())){
				OrderQueue orderQueue =new OrderQueue();
				orderQueue.setService_code(method);
				orderQueue.setVersion(version);
				orderQueue.setContents(req);
				orderQueue.setOrder_id(order_id);
				orderQueue.setStatus(EcsOrderConsts.CO_QUEUE_STATUS_WFS);
				co_id=this.OrderQueueSave(orderQueue);
			}
			
		}
		orderCfgInfo.setCo_id(co_id);
		orderCfgInfo.setOpenServiceCfg(os);
		orderCfgInfo.setOrder_id(order_id);
		
		return orderCfgInfo;
	}

	@Override
	public void OrderQueueFailSave(String co_id) {
		OrderQueueBusiRequest req=new OrderQueueBusiRequest();
		//1、查询
		//2、插入失败表
		logger.info("插入失败表ES_ORDER_QUEUE_FAIL");
		//3、删除现用表
		logger.info("删除队列表数据ES_ORDER_QUEUE");
	}

	@Override
	public void OrderQueueHisSave(String co_id) {
		OrderQueueBusiRequest req=new OrderQueueBusiRequest();
		//1、查询
		//2、插入历史表
		logger.info("插入历史表ES_ORDER_QUEUE_HIS");
		//3、删除现用表
		logger.info("删除队列表数据ES_ORDER_QUEUE");
	}

	@Override
	public void OrderHandLogSave(OrderHandleLogs log) {
		AdminUser adminUser = ManagerUtils.getAdminUser();
		if(adminUser!=null){
			String  user_id  = adminUser.getUserid();
			String  user_name = adminUser.getUsername();
			log.setOp_id(user_id);
			log.setOp_name(user_name);
		}
		log.setCreate_time("sysdate");
	    this.baseDaoSupport.insert("es_order_handle_logs", log);
	}

	

	@Override
	public String dubboExecute(OrderCollectionReq ocReq,String co_id,String order_id) {
		//1、接收参数
		String type=ocReq.getType();
		String serv=ocReq.getServ();
		String version=ocReq.getVersion();
		String key=ocReq.getKey();
		String sec=ocReq.getSec();
		String req=ocReq.getReq();
		
		//2、获取方法
		ZteRequest zteRequest =null;
		IFormatProcess processor = IFormatProcess.getProcessor(type);
		DefaultServiceContext context = DefaultServiceContext.getInstance();
		ServiceMethodHandler serviceMethodHandler = context.getServiceMethodHandler(serv, version);
		Class zteReqClass =serviceMethodHandler.getRequestType();
		Class zteResClass = serviceMethodHandler.getRespType();
		DefaultZteDubboClient client = new DefaultZteDubboClient(key, sec);//dubbo客户端
		
		//3、请求对象转换
		zteRequest = processor.getZteRequest(req, zteReqClass);
		if(zteRequest==null){//转换出错
			return processor.getMsg(IFormatProcess.MSG_CODE_ERROR, IFormatProcess.ERROR_REQ_FORMAT);
		}
		zteRequest.setBase_co_id(co_id);
		zteRequest.setBase_order_id(order_id);
		zteRequest.setFormat(type);
		
		//4、dubbo调用
		ZteResponse zteResponse =  client.execute(zteRequest, zteResClass);
		
		//5、返回对象转换
		String ret = processor.getZteResponse(zteResponse);
		
		//6、标准化失败/成功情况处理队列表在订单创建里面操作
		return ret;
		
	}

	@Override
	public String mqExecute(OrderCollectionReq ocReq,String co_id,String order_id) {
		//1、接收参数
		String type=ocReq.getType();
		String serv=ocReq.getServ();
		String version=ocReq.getVersion();
		String key=ocReq.getKey();
		String sec=ocReq.getSec();
		String req=ocReq.getReq();
	
		//2、获取方法
		ZteRequest zteRequest =null;
		IFormatProcess processor = IFormatProcess.getProcessor(type);
		DefaultServiceContext context = DefaultServiceContext.getInstance();
		ServiceMethodHandler serviceMethodHandler = context.getServiceMethodHandler(serv, version);
		Class zteReqClass =serviceMethodHandler.getRequestType();
		Class zteResClass = serviceMethodHandler.getRespType();
		DefaultZteMqClient client = new DefaultZteMqClient(key, sec);//mq客户端
		
		//3、请求对象转换
		zteRequest = processor.getZteRequest(req, zteReqClass);
		if(zteRequest==null){//转换出错
			return processor.getMsg(IFormatProcess.MSG_CODE_ERROR, IFormatProcess.ERROR_REQ_FORMAT);
		}
		
		//4、主题计算
		String topic="";
		ZteClient dClient = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		RuleTreeExeReq ruleReq = new RuleTreeExeReq();
		ruleReq.setRule_id(EcsOrderConsts.MQ_TOPIC_RLUE);
		AutoFact fact = new TacheFact();
		RuleTreeExeResp resp = dClient.execute(ruleReq, RuleTreeExeResp.class);
		topic="";
		
		//5、封装参数
		zteRequest.setBase_co_id(co_id);
		zteRequest.setBase_order_id(order_id);
		zteRequest.setFormat(type);
		zteRequest.setMqTopic(topic);
		
		//6、MQ调用
		ZteResponse zteResponse =  client.execute(zteRequest, zteResClass);
		if (!ConstsCore.ERROR_SUCC.equals(zteResponse.getError_code())) {//MQ发送失败
			this.OrderQueueFailSave(co_id);//迁移队列数据到失败表
		}
		
		//7、默认都返回成功
		return processor.getMsg(IFormatProcess.MSG_CODE_SUCCESS, IFormatProcess.SUCCESS_OK);
	}

	@Override
	public String OrderCreate(String co_id, String order_id) {
		logger.info("订单标准化逻辑操作！");
		
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
    	Map<String, Object> infMap = this.getInfMap(order_id);
    	TemplateAccessConvertReq  req = new TemplateAccessConvertReq();
//    	req.setInterfaceInfo(infMap);
//    	OrderQueueBusiRequest ordQueue = new OrderQueueBusiRequest();		//需要改造、load业务对象
//    	
//    	req.setTpl_code(ordQueue.getService_code());
//    	req.setTpl_ver(ordQueue.getVersion());
    	TemplateAccessConvertResp resp = client.execute(req, TemplateAccessConvertResp.class);				//调用模板转换
        
        //6、成功归档队列表、失败则归档数据到失败表
        if(InfoConsts.API_RET_SUCC_FLAG.equals(resp.getError_code())){//标准化成功
        	this.OrderQueueHisSave(co_id);
        }else{//标准化失败
        	this.OrderQueueFailSave(co_id);
        }
        
        //7、MQ调用标准化后的方案
        PlanRuleTreeExeReq planReq = new PlanRuleTreeExeReq();
		planReq.setPlan_id(EcsOrderConsts.CORE_STANDARD_PLAN);
		PlanRuleTreeExeResp planResp = client.execute(planReq, PlanRuleTreeExeResp.class);
        
		return null;
	}

	@Override
	public String OrderUpdate(String order_id, String co_id) {
		//1、业务操作
        logger.info("订单变更");
        
        
        //2、成功归档队列表、失败则归档数据到失败表
        if(true){//标准化成功
        	this.OrderQueueHisSave(co_id);
        }else{//标准化失败
        	this.OrderQueueFailSave(co_id);
        }
        
		//2、MQ调用订单变更方案
        ZteClient client = ClientFactory.getZteMqClient(ManagerUtils.getSourceFrom());
        PlanRuleTreeExeReq planReq = new PlanRuleTreeExeReq();
		//planReq.setFact(fact);
		planReq.setPlan_id(EcsOrderConsts.CORE_ORDER_UPDATE_PLAN);
		PlanRuleTreeExeResp planResp = client.execute(planReq, PlanRuleTreeExeResp.class);
		return null;
	}
	@Override
	public void orderCollectionFailData() {
		//1、读取失败表的列表数据
		logger.info("处理失败");
		String sql="select * from ES_ORDER_QUEUE_FAIL";
		List<Map> list=this.baseDaoSupport.queryForList(sql);
		for (Map map : list) {
			//2、根据co_id获取业务对象
			String co_id=Const.getStrValue(map, "co_id");
			OrderWork orderMain=new OrderWork ();
			String order_id;
			try {
				order_id = this.OrderMainSave(orderMain);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//this.OrderCreate(co_id, order_id);
			//
		}
	}
	
	/**
     * 将报文转换为map结构
     * @param order_id
     * @return
     */
    private Map<String, Object> getInfMap(String order_id){
    	PlanRuleTreeExeReq ruleReq = new PlanRuleTreeExeReq();
    	ruleReq.setPlan_id("");
    	InfTplFact fact = new InfTplFact();
    	fact.setOrder_id(order_id);
    	ruleReq.setFact(fact);
    	CommonDataFactory.getInstance().exePlanRuleTree(ruleReq);
    	return fact.getValueResponse();
    }
}
