package com.ztesoft.net.mall.core.timer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.Task;
import com.ztesoft.api.TaskThreadPool;
import com.ztesoft.api.ThreadPoolFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.common.util.BeanUtils;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.ecsord.params.ecaop.req.OrderInfoUpdateReq;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.model.CoQueue;
import com.ztesoft.net.mall.core.service.ICoQueueManager;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.rule.util.RuleFlowUtil;

import net.sf.json.JSONObject;
import params.ZteRequest;
import params.ZteResponse;
import rule.params.coqueue.req.CoQueueRuleReq;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.SpecConsts;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.ecsord.rule.tache.TacheFact;
import zte.net.params.resp.RuleTreeExeResp;


public class OrderStatusUpdateTimer {
	private static Logger logger = Logger.getLogger(OrderStatusUpdateTimer.class);
	ZteClient client= ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
	@Resource
	private ICoQueueManager coQueueManager;
	private static final int maxNum = 200;  //每次扫描200条
	private static final Object _KEY = new Object();
	public void run(){
		if (!CheckTimerServer.isMatchServer(this.getClass().getName(), "run")) {
  			return;
		}
// 		查询当前server主机环境，当前server为生产主机环境，则查询当前归属主机+未归属主机的订单信息；当前server为非生产主机环境，则查询当前归属主机
 		String local_env_status="";//00A
 		String local_env_code="";//ecsord_ceshi
 		String local_env_type ="";//ecsord_ceshi
 		Map hostEnvMap = BeanUtils.getCurrHostEnv();
 		local_env_status = (String)hostEnvMap.get("env_status");
 		local_env_code = (String)hostEnvMap.get("env_code");
 		local_env_type = (String)hostEnvMap.get("env_type");
 		if ("ecsord".equals(local_env_type)) { // 临时方式先写死去转换
 			local_env_code = "ecsord_a".equals(local_env_code)?"ecsord_server_a":local_env_code;
 			local_env_code = "ecsord_b".equals(local_env_code)?"ecsord_server_b":local_env_code;
 		}
 		synchronized (_KEY) {
 			List<CoQueue> coQueueLst = coQueueManager.getForABGrayJob(
 					new String[]{"CO_WORK_RESULT_BD"}, OrderStatusUpdateTimer.maxNum,local_env_status,local_env_code,true); //修改脚本，生产环境查询es_abgray_ord_env_rel存在的记录，或者不存在的记录，否则非生产环境，则完全匹配。;
 			logger.info("OrderStatusUpdateTimer====count:" + coQueueLst!=null?coQueueLst.size():0);
 			CoQueueRuleReq coQueueRuleReq = null;
 			for (CoQueue coQueue : coQueueLst) {
 				coQueueRuleReq = new CoQueueRuleReq();
 				coQueueRuleReq.setObject_id(coQueue.getObject_id());
 				coQueueRuleReq.setCoQueue(coQueue);
 				logger.info("订单状态查询定时器,订单"+coQueue.getObject_id()+" Job环境为："+local_env_code+"  环境状态"+local_env_status);
 				Map params = new HashMap();
 				coQueueRuleReq.setParams(params);
 				TaskThreadPool taskThreadPool = new TaskThreadPool(new Task(coQueueRuleReq) {
 					@Override
					public ZteResponse execute(ZteRequest zteRequest) {
						CoQueueRuleReq coQueueRuleReq =(CoQueueRuleReq)(zteRequest);
						CoQueue coQueue = coQueueRuleReq.getCoQueue();
 						try {
 							String service_code = coQueue.getService_code();
 							String contents = coQueue.getContents()==null?"":coQueue.getContents();
 							JSONObject json = JSONObject.fromObject(contents);
							String pay_result = json.getString("pay_result")==null?"":json.getString("pay_result");
							String work_result = json.getString("work_result")==null?"":json.getString("work_result");
							String out_order_id = json.getString("out_order_id")==null?"":json.getString("out_order_id");
							String order_id = json.getString("order_id")==null?"":json.getString("order_id");
							String order_from ="";
							String goods_type ="";//商品类型
							OrderTreeBusiRequest tree = null;
							if(StringUtil.isEmpty(order_id)){
								if(!StringUtil.isEmpty(out_order_id)){
									tree = CommonDataFactory.getInstance().getOrderTreeByOutId(out_order_id);
									order_id = tree.getOrder_id();
								} else {
									throw new Exception("外部单号和内部单号不能同时为空");
								}
							} else {
								tree = CommonDataFactory.getInstance().getOrderTree(order_id);
							}
							order_from = tree.getOrderExtBusiRequest().getOrder_from();
							goods_type = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.GOODS_TYPE);
							
							OrderInfoUpdateReq request = (OrderInfoUpdateReq) JSONObject.toBean(json, OrderInfoUpdateReq.class);
							logger.info("====================调用支付组件获取的内部订单号："+order_id);
 							if("CO_PAY_RESULT_BD".equals(service_code)){//支付结果通知
 								if("0".equals(pay_result)){
 	 								//接收支付结果组件
 	 								TacheFact fact = new TacheFact();
 	 								fact.setOrder_id(order_id);
 	 								fact.setRequest(request);
 	 								/*
 	 								RuleTreeExeReq req = new RuleTreeExeReq();
 	 								req.setPlan_id("170361749080000986");
 	 								req.setRule_id("170401032280000416");
 	 								req.setFact(fact);
 	 								req.setCheckAllRelyOnRule(true);
 	 								req.setCheckCurrRelyOnRule(true);
 	 								client.execute(req, RuleTreeExeResp.class);
 	 								*/
 	 								RuleTreeExeResp resp = new RuleTreeExeResp();
 	 								//如果订单来源是行销APP
 	 								if(EcsOrderConsts.PLAT_TYPE_10071.equals(order_from)){
 	 									String rule_id = "";
 	 									//如果商品类型是行销APP-活动受理
 	 									if (EcsOrderConsts.GOODS_TYPE_XXAPP_HDSL.equals(goods_type)||"170401633552001805".equals(goods_type)) {
 	 										rule_id = EcsOrderConsts.RECEIVE_PAY_RESULTS_XXAPP_HDSL_RULE;
 	 									} else if (EcsOrderConsts.GOODS_TYPE_DKD.equals(goods_type)||"170502124302000763".equals(goods_type)||"170502123552000755".equals(goods_type)
 	 											||"170502125012000771".equals(goods_type)||"170502112412000711".equals(goods_type)) {//如果商品类型是行销APP-宽带
 	 										rule_id = EcsOrderConsts.RECEIVE_PAY_RESULTS_XXAPP_KD_RULE;
 	 									} else if(SpecConsts.TYPE_ID_GOODS_BSS.equals(goods_type)){ // 号卡省内 add by xiang.yangbo
 	 										rule_id = EcsOrderConsts.BUSI_CARD_BSS_PAYRESULT_RULE_ID;
 	 									}else if(SpecConsts.TYPE_ID_GOODS_CBSS.equals(goods_type)){ // 号卡总部 add by huang.zhisheng
 	 										rule_id = EcsOrderConsts.BUSI_CARD_AOP_PAYRESULT_RULE_ID;
 	 									}else if(SpecConsts.TYPE_ID_CBSS_ACTIVITY.equals(goods_type)){ // 总部-活动受理 add by huang.zhisheng
 	 										rule_id = EcsOrderConsts.BUSI_CUNFEESONGFEE_AOP_PAY_RULE_ID;
 	 									}else if ("170601900021724272".equals(goods_type)){
 	 										rule_id = "171101558402000694";
 	 									}
 	 									if (!StringUtils.isEmpty(rule_id)) {
// 	 										resp = RuleFlowUtil.executeRuleTree(
// 	 	 	 										EcsOrderConsts.PAY_PLAN_ID, rule_id,
// 	 	 	 										fact, false, false, EcsOrderConsts.DEAL_FROM_INF);
 	 										
 	 										// add by xiang.yangbo begin
 	 										String planId = EcsOrderConsts.PAY_PLAN_ID;
 	 										// 号卡省内
 	 										if(SpecConsts.TYPE_ID_GOODS_BSS.equals(goods_type)){
 	 											planId = EcsOrderConsts.OPEN_ACCOUNT_PLAN_ID;
 	 										}else if(SpecConsts.TYPE_ID_GOODS_CBSS.equals(goods_type)){ // 号卡总部 
 	 											planId = EcsOrderConsts.OPEN_ACCOUNT_PLAN_ID;
 	 	 									}else if ("170601900021724272".equals(goods_type)){
 	 											planId="100";
 	 	 									}
 	 										resp = RuleFlowUtil.executeRuleTree( planId, rule_id,
 	 	 	 										fact, false, false, EcsOrderConsts.DEAL_FROM_INF);
 	 										// add by xiang.yangbo end
 	 									}
 	 								}else{
 	 									resp = RuleFlowUtil.executeRuleTree(
 	 	 										EcsOrderConsts.PAY_PLAN_ID, EcsOrderConsts.RECEIVE_PAY_RESULTS_RULE,
 	 	 										fact, false, false, EcsOrderConsts.DEAL_FROM_INF);
 	 								}
 	 								logger.info("CO_PAY_RESULT_BD:"+resp.getError_code()+","+resp.getError_msg());
 								}
 									coQueueManager.del(coQueue.getCo_id());
 							}else if("CO_WORK_RESULT_BD".equals(service_code)){//施工结果通知
 								if("0".equals(work_result)){
 									//接收施工结果组件 								
 	 								TacheFact fact = new TacheFact();
 	 								fact.setOrder_id(order_id);
 	 								fact.setRequest(request);
 	 								/*
 	 								RuleTreeExeReq req = new RuleTreeExeReq();
 	 								req.setRule_id("170401103540000720");
 	 								req.setFact(fact);
 	 								req.setCheckAllRelyOnRule(true);
 	 								req.setCheckCurrRelyOnRule(true);
 	 								client.execute(req, RuleTreeExeResp.class);
 	 								*/
 	 								RuleTreeExeResp resp = new RuleTreeExeResp();
 	 								if(!StringUtil.isEmpty(order_from)&&StringUtil.equals(order_from, "10071")){
 	 									resp = RuleFlowUtil.executeRuleTree(
 	 	 										EcsOrderConsts.OPEN_ACCOUNT_PLAN_ID, "170201510582001091",
 	 	 										fact, false, false, EcsOrderConsts.DEAL_FROM_INF);
 	 								}else{
 	 									resp = RuleFlowUtil.executeRuleTree(
 	 	 										EcsOrderConsts.OPEN_ACCOUNT_PLAN_ID, EcsOrderConsts.RECEIVE_WORK_RESULTS_RULE,
 	 	 										fact, false, false, EcsOrderConsts.DEAL_FROM_INF);
 	 								}
 	 								
 	 								logger.info("CO_WORK_RESULT_BD:"+resp.getError_code()+","+resp.getError_msg());
 								}
 									coQueueManager.del(coQueue.getCo_id());
 							}
 							
 						} catch (Exception e) {
 							e.printStackTrace();
 							coQueueManager.modifyStatus(coQueue.getCo_id(), "-1", e.getMessage());
 						}
 						return new ZteResponse();
 					}
 				});
 				coQueueManager.lock(coQueue.getCo_id());  //锁单（不要下次再被扫到）
 				ThreadPoolFactory.orderExecute(taskThreadPool); //异步单线程执行
 			}	
 		}
	}
}
