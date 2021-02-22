package com.ztesoft.net.mall.core.timer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import params.req.ZbQueryOrderDetailReq;
import params.resp.ZbQueryOrderDetailResp;

import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.consts.ZjEcsOrderConsts;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.rule.util.RuleFlowUtil;
import consts.ConstsCore;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.LocalCrawlerUtil;
import zte.net.ecsord.params.base.resp.BusiCompResponse;
import zte.net.ecsord.rule.tache.TacheFact;
import zte.net.params.req.RuleTreeExeReq;
import zte.net.params.resp.RuleTreeExeResp;


/**
 * 总商订单状态同步定时器
 * @author Sun
 *
 */
public class ZbOrderStateSynchronization{
	
	private static Logger log = Logger.getLogger(ZbOrderStateSynchronization.class);
	
	private Integer orderStatus;
	
	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	/**
	 * 订单状同步态时器
	 * @throws Exception
	 */
	public void runStateSynchronization() throws Exception {
        try {
    		if (!CheckTimerServer.isMatchServer(this.getClass().getName(), "runStateSynchronization")) {
      			return;
    		}
            log.info("开始[总部系统-订单状态同步]任务:");
            stateSynchronization();
            log.info("结束[总部系统-订单状态同步]任务:");
        } catch (Exception e) {
            log.info("[总部系统-订单状态同步]任务出错", e);
			log.info("ZbOrderStateSynchronization Exception [总部系统-订单状态同步]任务出错");
            e.printStackTrace();
        }
    }
	
	/**
	 * 自动分配
	 * @throws Exception
	 */
	@SuppressWarnings("static-access")
	public void stateSynchronization() throws Exception {
		String sql = "select a.order_id,a.out_tid,a.zb_id,a.status,a.create_time,a.zb_last_modify_time,a.remark from es_order_status_zb a where 1=1 ";
		String updateSql = "update es_order_status_zb set status = ? and zb_last_modify_time = ? where zb_id = ?";
		StringBuffer insertOrderOperation = new StringBuffer("insert into es_order_operation_zb(order_id, operation_time, operation_type, operation_content, operation_user) value");
		List<Object> params = new ArrayList<Object>();
		if(null != orderStatus && orderStatus > 0){
			sql = " and a.status = ?";
			params.add(orderStatus);
		}
		IDaoSupport support = SpringContextHolder.getBean("baseDaoSupport");
		List<Map<String, String>> list = support.queryForList(sql,params.toArray());
		if(null != list && list.size() > 0){
			for (Map<String, String> map : list) {
				String zbId = map.get("zb_id");
				Integer status = Integer.valueOf(map.get("status"));
				String orderId = map.get("order_id");
				String zbLastModifyTime = map.get("zb_last_modify_time");
				ZbQueryOrderDetailReq req = new ZbQueryOrderDetailReq();
				req.setOrderId(zbId);
				
				ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
				ZbQueryOrderDetailResp resp = client.execute(req, ZbQueryOrderDetailResp.class);
				if("0".equals(resp.getError_code())){
					Integer statusZb = LocalCrawlerUtil.ORDER_STATUS_ZB.get(resp.getStepOnArea());
					if(statusZb != null && statusZb != status || StringUtil.isEmpty(zbLastModifyTime)){//如果总商状态变更则同步总商状态到订单系统
						List<Object> updateParams = new ArrayList<Object>();
						updateParams.add(statusZb);
						updateParams.add(resp.getZbLastModifyTime());
						updateParams.add(zbId);
						support.execute(updateSql, updateParams);
						
						for(int i = status;i < statusZb;i++){//当前所在环节的规则不执行
							String ruleId = LocalCrawlerUtil.STATUS_RULE_MAP.get(i);
							if(!StringUtil.isEmpty(ruleId)){
								RuleTreeExeReq rteReq = new RuleTreeExeReq();
								rteReq.setCheckAllRelyOnRule(true);
								rteReq.setCheckCurrRelyOnRule(true);
								
								rteReq.setRule_id(ruleId);
								TacheFact fact = new TacheFact();
								fact.setOrder_id(orderId);
								rteReq.setFact(fact);
								RuleTreeExeResp rteResp = CommonDataFactory.getInstance().exeRuleTree(rteReq);
								BusiCompResponse busiResp = CommonDataFactory.getInstance().getRuleTreeresult(rteResp);
								if(!ConstsCore.ERROR_SUCC.equals(busiResp.getError_code())){
									log.info("订单["+orderId+"]执行"+resp.getStepOnArea()+"规则失败");
								}else{
									log.info("订单["+orderId+"]执行"+resp.getStepOnArea()+"规则成功");
								}
							}
						}
					}
					
					//记录操作路由信息
					List<String> orderOperationParams = new ArrayList<String>();
					orderOperationParams.add(zbId);
					List<Map<String, String>> zbOrderOperationList = resp.getOperationRecord();
					if(StringUtil.isEmpty(zbLastModifyTime) && null != zbOrderOperationList && zbOrderOperationList.size() > 0){//表中没有路由信息直接添加
						List<String> insertOrderOperationParams = new ArrayList<String>();
						for (Map<String, String> map2 : zbOrderOperationList) {
							insertOrderOperation.append("(?,?,?,?,?),");
							insertOrderOperationParams.add(zbId);
							insertOrderOperationParams.add(map2.get("operationTime"));
							insertOrderOperationParams.add(map2.get("operationType"));
							insertOrderOperationParams.add(map2.get("operationInfo"));
							insertOrderOperationParams.add(map2.get("operator"));
						}
						support.execute(insertOrderOperation.toString().substring(0,insertOrderOperation.length()-1), insertOrderOperationParams.toArray());
						
					}else if(!StringUtil.isEmpty(zbLastModifyTime) && null != zbOrderOperationList && zbOrderOperationList.size()>0){
						Map<String, String> zbLastMap = zbOrderOperationList.get(zbOrderOperationList.size()-1);
						String lastTime = zbLastModifyTime;
						String zbLastTime = zbLastMap.get("operationTime");
						int result = lastTime.compareTo(zbLastTime);
						List<String> insertOrderOperationParams = new ArrayList<String>();
						if(result < 0){
							//总部时间大于数据库记录时间，路由信息有新增
							for (Map<String, String> map2 : zbOrderOperationList) {
								if(lastTime.compareTo(map2.get("operationTime")) < 0){
									insertOrderOperation.append("(?,?,?,?,?),");
									insertOrderOperationParams.add(zbId);
									insertOrderOperationParams.add(map2.get("operationTime"));
									insertOrderOperationParams.add(map2.get("operationType"));
									insertOrderOperationParams.add(map2.get("operationInfo"));
									insertOrderOperationParams.add(map2.get("operator"));
								}
							}
							support.execute(insertOrderOperation.toString().substring(0,insertOrderOperation.length()-1), insertOrderOperationParams.toArray());
						}
					}
					
					int cancellationStatus = LocalCrawlerUtil.ORDER_STATUS_ZB.get(resp.getCancellationStatus());
					String dealDesc = resp.getDealDesc();
					for(int i = 8; i<= cancellationStatus;i++){
						String planId = LocalCrawlerUtil.ORDER_PLAN.get(i);
						String ruleId = LocalCrawlerUtil.STATUS_RULE_MAP.get(i);
						TacheFact fact = new TacheFact();
						fact.setOrder_id(orderId);		
						RuleFlowUtil.executeRuleTree(planId,ruleId,fact,true,true,ZjEcsOrderConsts.DEAL_FROM_PAGE,null,dealDesc);
						
					}					
				} else{//异常情况暂不处理
					
				}
			}
		}
    }
}
