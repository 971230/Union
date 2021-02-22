package zte.net.common.rule;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import zte.net.common.annontion.context.action.ZteMethodAnnontion;
import zte.net.common.annontion.context.action.ZteServiceAnnontion;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.base.req.BusiCompRequest;
import zte.net.ecsord.params.base.resp.BusiCompResponse;
import zte.net.ecsord.rule.tache.TacheFact;
import zte.net.params.req.PlanRuleTreeExeReq;
import zte.net.params.resp.PlanRuleTreeExeResp;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.service.IWorkPoolManager;

import consts.ConstsCore;

@ZteServiceAnnontion(trace_name="ZteOrderAutoAllotTraceRule",trace_id="0",version="1.0",desc="订单分配")
public class ZteOrderAutoAllotTraceRule  extends ZteTraceBaseRule{
	private static Logger logger = Logger.getLogger(ZteOrderAutoAllotTraceRule.class);
	
	@Resource
	private IWorkPoolManager workPoolManager;
	
	/**
	 * 订单自动分配匹配工号池入口
	 */
	@ZteMethodAnnontion(name="订单自动分配匹配工号池入口",group_name="订单自动分配匹配工号池入口",order="1",page_show=true,path="ZteOrderAutoAllotTraceRule.matchPool")
	public BusiCompResponse matchPool(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		resp.setError_code(ConstsCore.ERROR_SUCC);
		resp.setError_msg("执行成功");
		
		String order_id = busiCompRequest.getOrder_id();
		//执行订单自动分配方案
		PlanRuleTreeExeReq req = new PlanRuleTreeExeReq();
		TacheFact tacheFact = new TacheFact();
		tacheFact.setOrder_id(busiCompRequest.getOrder_id());
		req.setPlan_id(EcsOrderConsts.ORDER_AUTO_ALLOW);
		req.setFact(tacheFact);
		req.setDeleteLogs(true);
		PlanRuleTreeExeResp planResp = CommonDataFactory.getInstance().exePlanRuleTree(req);
		BusiCompResponse busiResp = CommonDataFactory.getInstance().getRuleTreeresult(planResp);
		if (!StringUtils.equals(busiResp.getError_code(), ConstsCore.ERROR_SUCC)) {
			logger.info(order_id+"    【订单自动分配】执行订单自动分配匹配工号池方案异常！"+busiResp.getError_msg());
			return resp;
		}
		
		return resp;
	}

	/**
	 * 订单自动分配通用组件
	 * @throws ApiBusiException 
	 */
	/*@SuppressWarnings("rawtypes")
	@ZteMethodAnnontion(name="订单自动分配通用组件",group_name="订单分配环节",order="2",page_show=true,path="ZteOrderAutoAllotTraceRule.autoAlloCommon")
	public BusiCompResponse autoAlloCommon(BusiCompRequest busiCompRequest) throws ApiBusiException{
		BusiCompResponse resp = new BusiCompResponse();
		resp.setError_code(ConstsCore.ERROR_SUCC);
		resp.setError_msg("执行成功");
		
		String order_id = busiCompRequest.getOrder_id();
		Map params = busiCompRequest.getQueryParams();
		TacheFact fact = (TacheFact) params.get("fact");
		String pool_id = fact.getWorker_pool_id();
		if(StringUtil.isEmpty(pool_id)) {
			logger.info(order_id+"    找不到对应工号池id！");
			return resp;
		}
		try {
			//根据工号池ID、权重、当前计数得到应分配的处理人工号
			String operator_id = workPoolManager.getOperatorIdByAuto(pool_id);
			if(StringUtil.isEmpty(operator_id)){
				logger.info("【订单自动分配】工号池"+pool_id+"暂无可分配的工号！");
				return resp;
			}
			//调用通用能力进行订单预占，并根据工号池的预占时间指定预占时间修改es_order_ext中的
			WorkPoolRel poolRel =  workPoolManager.getWorkPoolRelByIds(pool_id, operator_id);
			WorkerPool pool = workPoolManager.getWorkPoolById(pool_id);
			String operator_name =  poolRel.getOperator_name();
			String lock_time_value = pool.getLock_time();
			String lock_time = DateUtil.getTime2();
			String lock_end_time = "";
				if(!StringUtil.isEmpty(lock_time_value) && !lock_time_value.equals("0")){
					try {
						lock_end_time = DateUtil.addDate(lock_time,DateUtil.DATE_FORMAT_2,Integer.valueOf(lock_time_value), DateUtil.MINUTE);
					} catch (FrameException e) {
						logger.info(order_id+"    【订单自动分配】锁定结束时间转换异常！");
						return resp;
					}						
			}
			OrderLockBusiRequest busiRequest = new OrderLockBusiRequest();
			busiRequest.setPool_id(pool_id);
			busiRequest.setLock_user_id(operator_id);
			busiRequest.setLock_user_name(operator_name);
			busiRequest.setLock_end_time(lock_end_time);
			busiRequest.setLock_time(lock_time);
			boolean lockFlag = CommonDataFactory.getInstance().lockWorkerPool(order_id, busiRequest);
			if(lockFlag == false) {
				logger.info(order_id+"    【订单自动分配】调用通用能力进行订单预占不成功！");
				return resp;
			}
			//记录派单日志
			OrderDispatchLog dispatchLog = new OrderDispatchLog();
			dispatchLog.setPool_id(pool_id);
			dispatchLog.setOperator_id(operator_id);
			dispatchLog.setOrder_id(order_id);
			workPoolManager.addOrderDispatchLog(dispatchLog);
			//根据pool_id、operator_id修改关系表es_worker_pool_rel，对count进行加一操作
			//workPoolManager.updateWorkerPoolRelCounts(pool_id, operator_id, "ADD");
		} catch (Exception e) {
			resp.setError_code(ConstsCore.ERROR_FAIL);
			resp.setError_msg("失败["+e.getMessage()+"]");
		}
		return resp;
	}*/
	
	/**
	 * 订单释放通用组件
	 * @throws ApiBusiException 
	 */
	@ZteMethodAnnontion(name="订单释放通用组件",group_name="订单分配环节",order="3",page_show=true,path="ZteOrderAutoAllotTraceRule.autoReleaseCommon")
	public BusiCompResponse autoReleaseCommon(BusiCompRequest busiCompRequest) throws ApiBusiException{
		BusiCompResponse resp = new BusiCompResponse();
		resp.setError_code(ConstsCore.ERROR_SUCC);
		resp.setError_msg("执行成功");
		
	    String order_id = busiCompRequest.getOrder_id();
	    /* //根据 order_id，trace_id 查询锁单表es_order_lock，得到pool_id、operator_id
	    OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		OrderExtBusiRequest orderExt = orderTree.getOrderExtBusiRequest();
		String trace_id = orderExt.getFlow_trace_id();
		//根据order_id查询锁单表es_order_lock，得到pool_id、operator_id
		Map orderLockMap = workPoolManager.getOrderLockTempByIds(order_id, trace_id);
	    if(orderLockMap == null){
	    	logger.info(order_id+"订单" +trace_id + "环节，找不到对应锁定单信息！");
			return resp;
	    }
		String pool_id = (String)orderLockMap.get("pool_id");
		String operator_id = (String)orderLockMap.get("lock_user_id");
	  //根据pool_id、operator_id修改关系表es_worker_pool_rel，对count进行减一操作
		workPoolManager.updateWorkerPoolRelCounts(pool_id, operator_id, "CDD");*/
	    try {
			//调用通用能力进行订单解锁
			CommonDataFactory.getInstance().unlockWorkerPool(order_id);
		} catch (Exception e) {
			resp.setError_code(ConstsCore.ERROR_FAIL);
			resp.setError_msg("失败["+e.getMessage()+"]");
		}
		return resp;
	}

	@Override
	public BusiCompResponse engineDo(BusiCompRequest traceRequest) {
		BusiCompResponse resp = new BusiCompResponse();
		resp.setError_code(ConstsCore.ERROR_SUCC);
		resp.setError_msg("执行成功");
		return resp;
	}
}
